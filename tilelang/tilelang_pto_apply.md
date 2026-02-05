# BinaryVecOpsCodegen 算术表达式支持修复

## 问题描述

在 `src/target/codegen_ascend_pto.cc` 的 `BinaryVecOpsCodegen` 方法中（第 1359 行），当传入的参数是算术表达式时，无法被正确识别。

### 示例场景

```python
# 用户代码
T.tile.add(dst_ub, src_ub, prev_max[n_idx] + prev_sum[n_idx])
```

### 问题表现

- `prev_max[n_idx] + prev_sum[n_idx]` 是一个 **AddNode**，而不是 **CallNode**
- 原代码只检查 `op->args[2].as<CallNode>()`，导致算术表达式被当作简单分支处理
- 这可能生成的代码不正确或不符合预期

## 根本原因

在 TVM 的 TIR IR 中，表达式有不同的节点类型：

| 节点类型 | 示例 | 说明 |
|----------|------|------|
| **CallNode** | `buffer.GetValue(index)` | 函数调用 |
| **AddNode** | `a + b` | 加法运算 |
| **SubNode** | `a - b` | 减法运算 |
| **MulNode** | `a * b` | 乘法运算 |
| **DivNode** | `a / b` | 除法运算 |
| **ModNode** | `a % b` | 取模运算 |
| **MaxNode** | `max(a, b)` | 最大值 |
| **MinNode** | `min(a, b)` | 最小值 |

原代码只检测 `CallNode`，忽略了所有算术运算节点类型。

## 解决方案

### 1. 添加辅助函数 `IsComplexExpression`

在 `BinaryVecOpsCodegen` 函数之前添加：

```cpp
/*!
 * \brief Check if an expression is a "complex expression" that requires special handling
 * \param expr The expression to check
 * \return true if the expression is a CallNode or an arithmetic operation (Add/Sub/Mul/Div)
 */
bool IsComplexExpression(const PrimExpr& expr) {
    // Check if it's a CallNode (e.g., buffer.GetValue(index))
    if (expr.as<CallNode>()) {
        return true;
    }
    // Check if it's an arithmetic operation (Add/Sub/Mul/Div/Mod/etc.)
    if (expr.as<AddNode>() || expr.as<SubNode>() ||
        expr.as<MulNode>() || expr.as<DivNode>() ||
        expr.as<ModNode>() || expr.as<FloorDivNode>() ||
        expr.as<FloorModNode>() || expr.as<MaxNode>() ||
        expr.as<MinNode>()) {
        return true;
    }
    return false;
}
```

### 2. 修改判断逻辑

将：

```cpp
if (op->args[2].as<CallNode>()) {
```

改为：

```cpp
bool is_complex = IsComplexExpression(op->args[2]);

if (is_complex) {
```

### 3. 分别处理 CallNode 和算术表达式

在复杂分支中：

```cpp
if (is_complex) {
    std::string scalar_expr;

    // Check if it's a CallNode (e.g., buffer.GetValue(index))
    if (auto call_node = op->args[2].as<CallNode>()) {
        // ... 原有的 CallNode 处理逻辑 ...
    } else {
        // It's an arithmetic expression (Add/Sub/Mul/Div), print it directly
        this->PrintIndent();
        scalar_expr = PrintExpr(op->args[2]);

        if (operation == "TSUBS") {
            // For subtraction, negate the arithmetic expression
            this->stream << "TADDS" << "(";
            // ... 输出变量名 ...
            // 输出取负的算术表达式
            this->stream << ", -(" << scalar_expr << ")";
        } else {
            // ... 其他操作 ...
            // 输出算术表达式
            this->stream << ", " << scalar_expr;
        }
    }
}
```

## 修改后的行为

### CallNode 情况（原有逻辑）

**输入**：`T.tile.add(dst, src, buffer.GetValue(idx))`

**输出**：
```cpp
pipe_barrier(PIPE_ALL);
auto scalar_name = buffer.GetValue(idx);
pipe_barrier(PIPE_ALL);
// ... 创建临时 UB ...
TADDS(var_name_temp, var_name_temp, scalar_name);
```

### 算术表达式情况（新增支持）

**输入**：`T.tile.add(dst, src, prev_max[idx] + prev_sum[idx])`

**输出**：
```cpp
TADDS(var_names[0], var_names[1], prev_max[idx] + prev_sum[idx]);
```

**TSUBS 特殊处理**：

**输入**：`T.tile.sub(dst, src, prev_max[idx] + prev_sum[idx])`

**输出**：
```cpp
TADDS(var_names[0], var_names[1], -(prev_max[idx] + prev_sum[idx]));
```

## 测试文件

创建了测试文件：`testing/python/language/test_binary_vec_ops_arithmetic_expr.py`

### 测试用例

1. **test_arithmetic_expression_scalar**: 测试所有算术运算（加、减、乘）
2. **test_addition_expression_detection**: 专门测试加法表达式
3. **test_subtraction_expression_detection**: 专门测试减法表达式
4. **test_multiplication_expression_detection**: 专门测试乘法表达式

### 运行测试

```bash
# 使用 pytest
pytest testing/python/language/test_binary_vec_ops_arithmetic_expr.py -v

# 手动运行
python testing/python/language/test_binary_vec_ops_arithmetic_expr.py
```

## 代码变更位置

**文件**：`src/target/codegen_ascend_pto.cc`

**变更位置**：
- 第 9 行：添加 `#include <tvm/tir/expr.h>`
- 第 1351-1371 行：新增 `IsComplexExpression` 辅助函数
- 第 1381-1428 行：修改 `BinaryVecOpsCodegen` 的判断和处理逻辑

## 兼容性

- ✅ 向后兼容：原有的 CallNode 处理逻辑保持不变
- ✅ 新增功能：支持算术表达式（AddNode, SubNode, MulNode, DivNode 等）
- ✅ TSUBS 特殊处理：算术表达式在 TSUBS 中正确取负

## 注意事项

1. **表达式求值顺序**：算术表达式会被直接打印，不会创建临时变量
2. **TSUBS 转换**：当操作是 TSUBS 且标量是算术表达式时，整个表达式会被取负
3. **性能影响**：最小，只增加了一次表达式类型检查

## 相关文件

- **源文件**：`src/target/codegen_ascend_pto.cc`
- **头文件**：`src/target/codegen_ascend_pto.h`
- **测试文件**：`testing/python/language/test_binary_vec_ops_arithmetic_expr.py`
- **分析文档**：`BinaryVecOpsCodegen分析文档.md`

## 修改方案

```c++
```c++
/*!
 * \brief Check if an expression is a "complex expression" that requires special handling
 * \param expr The expression to check
 * \return true if the expression is a CallNode or an arithmetic operation (Add/Sub/Mul/Div)
 */
bool IsComplexExpression(const PrimExpr& expr) {
    // Check if it's a CallNode (e.g., buffer.GetValue(index))
    if (expr.as<CallNode>()) {
        return true;
    }
    // Check if it's an arithmetic operation (Add/Sub/Mul/Div/Mod/etc.)
    if (expr.as<AddNode>() || expr.as<SubNode>() ||
        expr.as<MulNode>() || expr.as<DivNode>() ||
        expr.as<ModNode>() || expr.as<FloorDivNode>() ||
        expr.as<FloorModNode>() || expr.as<MaxNode>() ||
        expr.as<MinNode>()) {
        return true;
    }
    return false;
}
```
```

## 更新日志

- **2025-02-05**: 初始版本，添加算术表达式支持
