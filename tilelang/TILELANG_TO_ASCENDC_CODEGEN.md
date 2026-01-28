# TileLang 到 AscendC 代码生成详解

## 目录
1. [概述](#概述)
2. [转换流程总览](#转换流程总览)
3. [silu.py 示例分析](#silupy-示例分析)
4. [关键组件的转换详解](#关键组件的转换详解)
5. [完整转换示例](#完整转换示例)
6. [转换映射表](#转换映射表)
7. [总结](#总结)

---

## 概述

TileLang 是一个用于编写高性能算子的 DSL (Domain Specific Language)，它可以编译生成多种后端代码，包括 AscendC (华为昇腾 AI 处理器的编程语言)。

本文档以 `examples/activation/silu.py` 为例，详细讲解 Python DSL 代码是如何一步步转换为 AscendC C++ 代码的。

---

## 转换流程总览

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        Python DSL 代码                                      │
│  (silu.py: T.Kernel, T.alloc_ub, T.tile.sub, etc.)                        │
└─────────────────────────────┬───────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                     TIR (Tensor IR)                                         │
│  通过 TVM 的 TIR Script 解析器将 Python 代码解析为 TIR AST                   │
└─────────────────────────────┬───────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                   Phase 1: LowerAndLegalize                                │
│  - AscendInferBufferScope: 推断 buffer 作用域                               │
│  - HostProcesser: 处理 host 代码                                            │
│  - AscendLowerParallelToVector: 并行循环转向量                              │
│  - LayoutInference: 布局推断                                                │
│  - LowerTileOp: 降低 Tile 操作为 Intrinsic ⭐                                │
│  - LegalizeVectorizedLoop: 合法化向量循环                                   │
│  - LegalizeSafeMemoryAccess: 内存安全检查                                   │
└─────────────────────────────┬───────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                   Phase 2: OptimizeForTarget                                │
│  - CrossCorePipeline: 跨核流水线                                            │
│  - InjectSoftwarePipeline: 软件流水线                                       │
│  - VectorizeLoop: 径向量化                                                  │
│  - AscendStorageRewrite: 存储重写                                           │
│  - AscendMemoryPlanning: 内存规划                                           │
│  - AscendSyncInsert: 插入同步指令                                           │
└─────────────────────────────┬───────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                  代码生成 (CodeGenTileLangAscend)                           │
│  遍历优化后的 TIR，生成 AscendC C++ 代码                                     │
└─────────────────────────────┬───────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                      AscendC C++ 代码                                        │
│  (最终的可执行 kernel 代码)                                                  │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## silu.py 示例分析

### 原始 Python 代码

```python
# examples/activation/silu.py
import tilelang
import tilelang.language as T
import torch

@tilelang.jit(out_idx=[1])
def silu(M, N, block_M, block_N, dtype="float"):
    m_num = T.ceildiv(M, block_M)
    n_num = T.ceildiv(N, block_N)

    VEC_NUM = 2

    @T.prim_func
    def main(
            A: T.Tensor((M, N), dtype),
            B: T.Tensor((M, N), dtype)
    ):
        with T.Kernel(m_num * n_num, is_npu=True) as (cid, vid):
            bx = cid // n_num
            by = cid % n_num

            # 分配 Unified Buffer (UB)
            a_ub = T.alloc_ub((block_M // VEC_NUM, block_N), dtype)
            b_ub = T.alloc_ub((block_M // VEC_NUM, block_N), dtype)
            denom_ub = T.alloc_ub((block_M // VEC_NUM, block_N), dtype)
            zero_ub = T.alloc_ub((block_M // VEC_NUM, block_N), dtype)

            with T.Scope("V"):
                T.copy(A[bx * block_M + vid * block_M // VEC_NUM, by * block_N], a_ub)
                T.tile.fill(zero_ub, 0.0)

                T.barrier_all()
                # SiLU = x * sigmoid(x) = x / (1 + exp(-x))
                T.tile.sub(denom_ub, zero_ub, a_ub)      # denom = 0 - a = -a
                T.barrier_all()
                T.tile.exp(denom_ub, denom_ub)           # denom = exp(-a)
                T.barrier_all()
                T.tile.add(denom_ub, denom_ub, 1.0)      # denom = 1 + exp(-a)
                T.barrier_all()
                T.tile.div(b_ub, a_ub, denom_ub)         # b = a / (1 + exp(-a))
                T.barrier_all()

                T.copy(b_ub, B[bx * block_M + vid * block_M // VEC_NUM, by * block_N])

    return main
```

### SiLU 数学公式

```
SiLU(x) = x * sigmoid(x) = x * (1 / (1 + exp(-x))) = x / (1 + exp(-x))
```

---

## 关键组件的转换详解

### 1. T.Kernel 的转换

#### Python DSL 层

```python
with T.Kernel(m_num * n_num, is_npu=True) as (cid, vid):
    bx = cid // n_num
    by = cid % n_num
    # ... kernel body
```

**实现位置**: `tilelang/language/kernel.py:213-268`

```python
def Kernel(
    *blocks: List[tir.PrimExpr],
    threads: Optional[Union[int, List[int], Tuple]] = None,
    is_cpu: bool = False,
    is_npu: bool = False,
    ...
):
    attrs: dict = {}
    if is_npu:
        assert len(blocks) == 1, "NPU kernel must have exactly one block dimension"
        attrs["tilelang.is_npu_kernel_frame"] = True
        return _ffi_api.KernelLaunch(blocks, threads, attrs)
    # ...
```

#### TIR 表示

`T.Kernel` 通过 TVM 的 TIR Script 解析器生成以下 TIR 结构：

```tir
# 伪代码表示
attr [tilelang.is_npu_kernel_frame = True] {
  for (cid, 0, m_num * n_num) {
    for (vid, 0, 1) {  # NPU 默认 vid 为单线程
      let bx = floordiv(cid, n_num)
      let by = ceilmod(cid, n_num)
      # kernel body
    }
  }
}
```

#### 关键点

1. **is_npu=True**: 标记这是 NPU kernel，生成特殊的 block 结构
2. **返回值 (cid, vid)**:
   - `cid`: core ID (block index)，对应 AICORE 的编号
   - `vid`: vector ID，在当前实现中固定为 0
3. **Frame 管理**: 使用 `KernelLaunchFrame` 管理 kernel 的进入和退出

---

### 2. T.alloc_ub 的转换

#### Python DSL 层

```python
a_ub = T.alloc_ub((block_M // VEC_NUM, block_N), dtype)
```

**实现位置**: `tilelang/language/allocate.py`

```python
def alloc_ub(shape, dtype="float16", scope="local", name="ub"):
    """
    Allocate a Unified Buffer (UB) for NPU
    UB is the on-chip buffer in Ascend AI Core
    """
    return T.alloc_buffer(shape, dtype, scope="local", data=name)
```

#### TIR 表示

```tir
allocate(a_ub, "float", [block_M // VEC_NUM, block_N]) {
  # buffer scope: "local" (Unified Buffer in Ascend)
}
```

#### 内存层次说明

| 作用域 | 说明 | AscendC 对应 |
|--------|------|-------------|
| `"global"` | 全局内存 (Global Memory, GM) | `GM_ADDR` |
| `"local"` | 统一缓冲区 (Unified Buffer, UB) | `LocalTensor` |
| `"local.L0C"` | L0 缓冲区 (C 矩阵) | `LB (Local Buffer)` |
| `"local.L1"` | L1 缓冲区 | `L1 Buffer` |

---

### 3. T.tile.fill 的转换

#### Python DSL 层

```python
T.tile.fill(zero_ub, 0.0)
```

**实现位置**: `tilelang/language/ascend_tile.py:63-82`

```python
def fill(buffer: Buffer, value: PrimExpr):
    """Fill a buffer with a specified value."""
    size = math.prod(buffer.shape)

    return tir.call_intrin(
        "handle",
        tir.op.Op.get("tl.ascend_fill"),  # 注册的 Op
        f"Fill<{_dtype(buffer)}>",        # 模板参数
        buffer.access_ptr("w"),
        value,
        size,
    )
```

#### TIR Intrinsic 表示

```tir
call_extern("handle", "tl.ascend_fill",
    "Fill<float>",           # 模板参数
    zero_ub.access_ptr("w"), # 目标 buffer
    0.0,                     # 填充值
    (block_M // VEC_NUM) * block_N  # size
)
```

#### 代码生成层

**实现位置**: `src/target/codegen_ascend.cc:1210-1213`

```cpp
void CodeGenTileLangAscend::FillCodegen(const CallNode *op) {
  // 提取操作名称
  std::string op_name = "tl::ascend::" + Downcast<StringImm>(op->args[0])->value;

  // 生成 AscendC 代码
  // args[1]: dst buffer pointer
  // args[2]: value
  // args[3]: count
  PrintOpCall(op, op_name, {1, 2}, {2, 4});
}
```

#### 最终 AscendC 代码

```cpp
// 生成的 AscendC 代码
Fill<float>(zero_ub_local.GetValue(0), 0.0, block_M * block_N / VEC_NUM);
```

---

### 4. T.tile.sub 的转换

#### Python DSL 层

```python
T.tile.sub(denom_ub, zero_ub, a_ub)  # denom = 0 - a
```

**实现位置**: `tilelang/language/ascend_tile.py:543-551`

```python
def sub(dst: Buffer, src0: Buffer, src1: Buffer):
    """Element-wise subtraction: dst = src0 - src1"""
    return binary_op(dst, src0, src1, "sub")

def binary_op(dst, src0, src1, op):
    # 生成 intrinsic 调用
    return tir.call_intrin(
        "handle",
        tir.op.Op.get(f"tl.ascend_{op}"),
        dst.access_ptr("w"),
        src0.access_ptr("r"),
        src1.access_ptr("r"),
        size_0,
    )
```

#### TIR Intrinsic 表示

```tir
call_extern("handle", "tl.ascend_sub",
    denom_ub.access_ptr("w"),  // dst
    zero_ub.access_ptr("r"),   // src0
    a_ub.access_ptr("r"),      // src1
    size                       // count
)
```

#### 代码生成层

**实现位置**: `src/target/codegen_ascend.cc:407-410, 1102-1118`

```cpp
void CodeGenTileLangAscend::VisitExpr_(const CallNode *op, std::ostream &os) {
  // ...
  else if (op->op.same_as(tl::ascend_sub())) {
    BinaryVecOpCodegen(op, "AscendC::Sub");
  }
  // ...
}

void CodeGenTileLangAscend::BinaryVecOpCodegen(const CallNode *op,
                                               const std::string &op_name) {
  std::vector<std::string> var_names;
  // 提取 buffer 变量名
  for (int i = 0; i < op->args.size() - 1; i++) {
    auto var_name = PrintBufferOffset(op->args[i].as<CallNode>());
    var_names.push_back(var_name);
  }

  this->PrintIndent();
  // 生成: AscendC::Sub(dst, src0, src1, count);
  this->stream << op_name << "(";
  for (int i = 0; i < var_names.size(); i++) {
    this->stream << var_names[i];
    if (i != var_names.size() - 1) {
      this->stream << ", ";
    }
  }
  this->stream << ", " << PrintExpr(op->args[op->args.size() - 1]) << ");\n";
}
```

#### 最终 AscendC 代码

```cpp
AscendC::Sub(denom_ub_local.GetValue(0), zero_ub_local.GetValue(0),
             a_ub_local.GetValue(0), block_M * block_N / VEC_NUM);
```

---

### 5. T.copy 的转换

#### Python DSL 层

```python
T.copy(A[bx * block_M + vid * block_M // VEC_NUM, by * block_N], a_ub)
T.copy(b_ub, B[bx * block_M + vid * block_M // VEC_NUM, by * block_N])
```

**实现位置**: `tilelang/language/copy.py`

```python
def copy(src, dst, mask=None):
    """
    Copy data between buffers.

    For NPU:
    - GM -> UB: use DataCopy
    - UB -> GM: use DataCopy
    - UB -> UB: use Duplicate
    """
    if scope_in == "global" and scope_out == "local":
        # GM to UB
        return call_extern("handle", "tl::ascend::copy_gm_to_ub",
                          dst_ptr, src_ptr, size)
    # ...
```

#### TIR Intrinsic 表示

```tir
# GM -> UB
call_extern("handle", "tl::ascend::copy_gm_to_ub",
    a_ub.access_ptr("w"),
    A.access_ptr("r", offset=bx * block_M * N + by * block_N),
    block_M * block_N / VEC_NUM
)
```

#### 代码生成层

**实现位置**: `src/target/codegen_ascend.cc:399-400, 1780-1830`

```cpp
void CodeGenTileLangAscend::VisitExpr_(const CallNode *op, std::ostream &os) {
  if (op->op.same_as(builtin::call_extern())) {
    std::string op_name = Downcast<StringImm>(op->args[0])->value;
    if (op_name.find("tl::ascend::copy") != std::string::npos) {
      CopyCodegen(op);
    }
  }
}

void CodeGenTileLangAscend::CopyCodegen(const CallNode *op) {
  std::string op_name = Downcast<StringImm>(op->args[0])->value;
  auto src_var = op->args[1].as<CallNode>()->args[1].as<VarNode>();
  auto dst_var = op->args[2].as<CallNode>()->args[1].as<VarNode>();

  auto src_var_id = var_idmap_[src_var];
  auto dst_var_id = var_idmap_[dst_var];

  auto src_offset = PrintExpr(op->args[1].as<CallNode>()->args[2]);
  auto dst_offset = PrintExpr(op->args[2].as<CallNode>()->args[2]);

  this->PrintIndent();
  // 生成: copy_gm_to_ub(dst[offset], src[offset], size);
  this->stream << op_name << "(" << dst_var_id << "[" << dst_offset << "], "
               << src_var_id << "[" << src_offset << "]);\n";
}
```

#### 最终 AscendC 代码

```cpp
// GM -> UB
LocalTensor<a_ub_float> a_ub_local;
pipe_barrier(MTE3);
copy_gm_to_ub(a_ub_local.GetValue(0), A[bx * block_M * N + by * block_N],
             block_M * block_N / VEC_NUM);
pipe_barrier(V);

// UB -> GM
pipe_barrier(MTE3);
copy_ub_to_gm(B.GetValue(offset), b_ub_local.GetValue(0),
             block_M * block_N / VEC_NUM);
pipe_barrier(V);
```

---

### 6. T.barrier_all 的转换

#### Python DSL 层

```python
T.barrier_all()
```

**实现位置**: `tilelang/language/ascend_tile.py:203-213`

```python
def barrier_all():
    """
    Inserts a barrier for all pipeline stages.

    Ensures all instructions in all pipelines (Scalar, Vector, Cube, MTE)
    issued before this barrier are completed.
    """
    return tir.call_intrin("handle", tir.op.Op.get("tl.ascend_pipe_barrier"), "ALL")
```

#### TIR Intrinsic 表示

```tir
call_extern("handle", "tl.ascend_pipe_barrier", "ALL")
```

#### 最终 AscendC 代码

```cpp
pipe_barrier(ALL);
```

---

### 7. 其他 Tile 操作的转换

#### T.tile.exp

```python
# Python DSL
T.tile.exp(denom_ub, denom_ub)

# TIR Intrinsic
call_extern("handle", "tl.ascend_exp",
    denom_ub.access_ptr("w"),
    denom_ub.access_ptr("r"),
    size
)

# AscendC 代码
AscendC::Exp(denom_ub_local.GetValue(0), denom_ub_local.GetValue(0), size);
```

#### T.tile.add

```python
# Python DSL
T.tile.add(denom_ub, denom_ub, 1.0)

# TIR Intrinsic
call_extern("handle", "tl.ascend_adds",
    denom_ub.access_ptr("w"),
    denom_ub.access_ptr("r"),
    1.0,
    size
)

# AscendC 代码
AscendC::Adds(denom_ub_local.GetValue(0), denom_ub_local.GetValue(0), 1.0, size);
```

#### T.tile.div

```python
# Python DSL
T.tile.div(b_ub, a_ub, denom_ub)

# TIR Intrinsic
call_extern("handle", "tl.ascend_div",
    b_ub.access_ptr("w"),
    a_ub.access_ptr("r"),
    denom_ub.access_ptr("r"),
    size
)

# AscendC 代码
AscendC::Div(b_ub_local.GetValue(0), a_ub_local.GetValue(0),
             denom_ub_local.GetValue(0), size);
```

---

## 完整转换示例

### 输入: Python DSL

```python
@T.prim_func
def main(A: T.Tensor((M, N), "float"), B: T.Tensor((M, N), "float")):
    with T.Kernel(m_num * n_num, is_npu=True) as (cid, vid):
        bx = cid // n_num
        by = cid % n_num

        a_ub = T.alloc_ub((block_M // VEC_NUM, block_N), dtype)
        zero_ub = T.alloc_ub((block_M // VEC_NUM, block_N), dtype)
        denom_ub = T.alloc_ub((block_M // VEC_NUM, block_N), dtype)
        b_ub = T.alloc_ub((block_M // VEC_NUM, block_N), dtype)

        with T.Scope("V"):
            T.copy(A[bx * block_M + vid * block_M // VEC_NUM, by * block_N], a_ub)
            T.tile.fill(zero_ub, 0.0)
            T.barrier_all()
            T.tile.sub(denom_ub, zero_ub, a_ub)
            T.barrier_all()
            T.tile.exp(denom_ub, denom_ub)
            T.barrier_all()
            T.tile.add(denom_ub, denom_ub, 1.0)
            T.barrier_all()
            T.tile.div(b_ub, a_ub, denom_ub)
            T.barrier_all()
            T.copy(b_ub, B[bx * block_M + vid * block_M // VEC_NUM, by * block_N])
```

### 中间表示: TIR (简化)

```tir
@tir.prim_func
def main(A: tir.Buffer[(M, N), "float"], B: tir.Buffer[(M, N), "float"]):
    attr [tilelang.is_npu_kernel_frame = True]
    for (cid, 0, m_num * n_num):
        for (vid, 0, 1):
            bx = floordiv(cid, n_num)
            by = ceilmod(cid, n_num)

            allocate(a_ub, "float", [block_M // VEC_NUM, block_N]) {  # scope: local
                allocate(zero_ub, "float", [block_M // VEC_NUM, block_N]) {
                    allocate(denom_ub, "float", [block_M // VEC_NUM, block_N]) {
                        allocate(b_ub, "float", [block_M // VEC_NUM, block_N]) {

                            # GM -> UB
                            tir.evaluate(call_extern("handle", "tl::ascend::copy_gm_to_ub",
                                a_ub.access_ptr("w"),
                                A.access_ptr("r", offset=bx * block_M * N + by * block_N),
                                block_M * block_N // VEC_NUM))

                            # Fill zero
                            tir.evaluate(call_extern("handle", "tl.ascend_fill",
                                "Fill<float>", zero_ub.access_ptr("w"), 0.0, size))

                            # Barrier
                            tir.evaluate(call_extern("handle", "tl.ascend_pipe_barrier", "ALL"))

                            # Sub: denom = 0 - a
                            tir.evaluate(call_extern("handle", "tl.ascend_sub",
                                denom_ub.access_ptr("w"), zero_ub.access_ptr("r"),
                                a_ub.access_ptr("r"), size))

                            # Barrier
                            tir.evaluate(call_extern("handle", "tl.ascend_pipe_barrier", "ALL"))

                            # Exp: denom = exp(denom)
                            tir.evaluate(call_extern("handle", "tl.ascend_exp",
                                denom_ub.access_ptr("w"), denom_ub.access_ptr("r"), size))

                            # Barrier
                            tir.evaluate(call_extern("handle", "tl.ascend_pipe_barrier", "ALL"))

                            # Add: denom = denom + 1
                            tir.evaluate(call_extern("handle", "tl.ascend_adds",
                                denom_ub.access_ptr("w"), denom_ub.access_ptr("r"),
                                1.0, size))

                            # Barrier
                            tir.evaluate(call_extern("handle", "tl.ascend_pipe_barrier", "ALL"))

                            # Div: b = a / denom
                            tir.evaluate(call_extern("handle", "tl.ascend_div",
                                b_ub.access_ptr("w"), a_ub.access_ptr("r"),
                                denom_ub.access_ptr("r"), size))

                            # Barrier
                            tir.evaluate(call_extern("handle", "tl.ascend_pipe_barrier", "ALL"))

                            # UB -> GM
                            tir.evaluate(call_extern("handle", "tl::ascend::copy_ub_to_gm",
                                B.access_ptr("w", offset=bx * block_M * N + by * block_N),
                                b_ub.access_ptr("r"), block_M * block_N // VEC_NUM))
                        }
                    }
                }
            }
```

### 输出: AscendC C++ 代码 (简化)

```cpp
extern "C" __global__ __aicore__ void main(
    float* __restrict__ A, float* __restrict__ B, int M, int N
) {
    // 变量声明
    LocalTensor<float> a_ub_local;
    LocalTensor<float> zero_ub_local;
    LocalTensor<float> denom_ub_local;
    LocalTensor<float> b_ub_local;

    // 计算 block 索引
    int cid = GetBlockIdx();
    int vid = 0;  // NPU 单线程
    int bx = cid / n_num;
    int by = cid % n_num;

    // 分配 UB 空间
    a_ub_local = alloc_tensor<float>(ub_stack, block_M * block_N / VEC_NUM);
    zero_ub_local = alloc_tensor<float>(ub_stack, block_M * block_N / VEC_NUM);
    denom_ub_local = alloc_tensor<float>(ub_stack, block_M * block_N / VEC_NUM);
    b_ub_local = alloc_tensor<float>(ub_stack, block_M * block_N / VEC_NUM);

    // 复制数据 GM -> UB
    pipe_barrier(MTE3);
    copy_gm_to_ub(a_ub_local.GetValue(0),
                  A.GetValue(bx * block_M * N + by * block_N),
                  block_M * block_N / VEC_NUM);
    pipe_barrier(V);

    // 填充 zero
    Fill<float>(zero_ub_local.GetValue(0), 0.0, block_M * block_N / VEC_NUM);

    // SiLU 计算: denom = 0 - a
    pipe_barrier(ALL);
    AscendC::Sub(denom_ub_local.GetValue(0), zero_ub_local.GetValue(0),
                 a_ub_local.GetValue(0), block_M * block_N / VEC_NUM);

    // SiLU 计算: denom = exp(denom)
    pipe_barrier(ALL);
    AscendC::Exp(denom_ub_local.GetValue(0), denom_ub_local.GetValue(0),
                 block_M * block_N / VEC_NUM);

    // SiLU 计算: denom = denom + 1
    pipe_barrier(ALL);
    AscendC::Adds(denom_ub_local.GetValue(0), denom_ub_local.GetValue(0),
                  1.0, block_M * block_N / VEC_NUM);

    // SiLU 计算: b = a / denom
    pipe_barrier(ALL);
    AscendC::Div(b_ub_local.GetValue(0), a_ub_local.GetValue(0),
                 denom_ub_local.GetValue(0), block_M * block_N / VEC_NUM);
    pipe_barrier(ALL);

    // 复制数据 UB -> GM
    pipe_barrier(MTE3);
    copy_ub_to_gm(B.GetValue(bx * block_M * N + by * block_N),
                  b_ub_local.GetValue(0), block_M * block_N / VEC_NUM);
    pipe_barrier(V);
}
```

---

## 转换映射表

### Python DSL → TIR Intrinsic → AscendC API

| Python DSL | TIR Intrinsic | AscendC API | 说明 |
|------------|---------------|-------------|------|
| `T.tile.add(dst, src0, src1)` | `tl.ascend_add` | `AscendC::Add(dst, src0, src1, count)` | 逐元素加法 |
| `T.tile.sub(dst, src0, src1)` | `tl.ascend_sub` | `AscendC::Sub(dst, src0, src1, count)` | 逐元素减法 |
| `T.tile.mul(dst, src0, src1)` | `tl.ascend_mul` | `AscendC::Mul(dst, src0, src1, count)` | 逐元素乘法 |
| `T.tile.div(dst, src0, src1)` | `tl.ascend_div` | `AscendC::Div(dst, src0, src1, count)` | 逐元素除法 |
| `T.tile.exp(dst, src)` | `tl.ascend_exp` | `AscendC::Exp(dst, src, count)` | 逐元素指数 |
| `T.tile.sqrt(dst, src)` | `tl.ascend_sqrt` | `AscendC::Sqrt(dst, src, count)` | 逐元素平方根 |
| `T.tile.adds(dst, src, scalar)` | `tl.ascend_adds` | `AscendC::Adds(dst, src, scalar, count)` | 逐元素标量加法 |
| `T.tile.muls(dst, src, scalar)` | `tl.ascend_muls` | `AscendC::Muls(dst, src, scalar, count)` | 逐元素标量乘法 |
| `T.tile.fill(buf, val)` | `tl.ascend_fill` | `Fill<T>(buf, val, count)` | 填充 buffer |
| `T.copy(src, dst)` | `tl::ascend::copy_*` | `copy_gm_to_ub/ub_to_gm` | 数据拷贝 |
| `T.barrier_all()` | `tl.ascend_pipe_barrier` | `pipe_barrier(ALL)` | 流水线屏障 |
| `T.Kernel(..., is_npu=True)` | N/A | `__aicore__ kernel` | NPU kernel 入口 |

### 内存分配映射

| Python DSL | 作用域 | AscendC 类型 |
|------------|--------|-------------|
| `T.alloc_ub(shape, dtype)` | `"local"` | `LocalTensor<T>` |
| `T.alloc_L0C(shape, dtype)` | `"local.L0C"` | `LB (Local Buffer)` |
| `T.alloc_L1(shape, dtype)` | `"local.L1"` | `L1 Buffer` |
| 函数参数 Tensor | `"global"` | `GlobalTensor` / `GM_ADDR` |

---

## 总结

TileLang 到 AscendC 的转换流程可以总结为以下步骤：

### 转换步骤

1. **DSL 解析**: Python DSL 通过 TVM TIR Script 解析器生成 TIR AST
2. **IR 降低**: 通过一系列 Pass 将高级 TIR 降低为低级 TIR
3. **Intrinsic 映射**: Tile 操作被映射为特定的 intrinsic (如 `tl.ascend_sub`)
4. **代码生成**: 遍历最终的 TIR，生成对应的 AscendC C++ 代码

### 关键设计理念

1. **分层抽象**: 从高层 DSL 到底层汇编指令的完整抽象层次
2. **硬件感知**: 显式暴露内存层次 (GM/UB/L1/L0C)，允许用户精确控制
3. **自动化优化**: 编译器自动处理流水线、同步、向量化等优化
4. **可验证性**: 生成的代码可读、可调试，便于性能分析

### 相关文件位置

| 组件 | 文件路径 |
|------|----------|
| Python DSL 定义 | `tilelang/language/*.py` |
| Kernel 管理 | `tilelang/language/kernel.py` |
| Tile 操作 | `tilelang/language/ascend_tile.py` |
| 内存分配 | `tilelang/language/allocate.py` |
| TIR Pass | `src/transform/lower_tile_op.cc` |
| 代码生成头文件 | `src/target/codegen_ascend.h` |
| 代码生成实现 | `src/target/codegen_ascend.cc` |
| 示例代码 | `examples/activation/silu.py` |

### 代码生成关键函数

| 函数 | 位置 | 功能 |
|------|------|------|
| `CodeGenTileLangAscend::VisitExpr_` | `codegen_ascend.cc:396` | 表达式访问入口 |
| `CodeGenTileLangAscend::BinaryVecOpCodegen` | `codegen_ascend.cc:1102` | 二元操作代码生成 |
| `CodeGenTileLangAscend::UnaryVecOpCodegen` | `codegen_ascend.cc:1120` | 一元操作代码生成 |
| `CodeGenTileLangAscend::FillCodegen` | `codegen_ascend.cc:1210` | Fill 操作代码生成 |
| `CodeGenTileLangAscend::CopyCodegen` | `codegen_ascend.cc:1780` | Copy 操作代码生成 |

### 扩展阅读

1. **TVM TIR 文档**: 了解 TIR 的基本概念和数据结构
2. **AscendC 编程指南**: 学习 AscendC API 的使用方法
3. **昇腾 AI 处理器架构**: 理解 AI Core 的流水线和内存层次
