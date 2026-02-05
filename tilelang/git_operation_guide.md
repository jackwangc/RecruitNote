# Git 操作指南 - 同步 ascendc_pto 分支

本文档记录了将本地 `ascendc_pto` 分支与上游仓库 `tile-ai/tilelang-ascend` 保持同步的操作步骤。

## 操作步骤

### 1. 添加上游远端仓库

```bash
git remote add remote git@github.com:tile-ai/tilelang-ascend.git
```

添加名为 `remote` 的远端仓库，指向上游仓库地址。

### 2. 拉取远端仓库最新代码

```bash
git fetch remote
```

从 `remote` 获取最新的分支和标签信息。

### 3. 同步本地分支与远端分支

使用 rebase 方式将本地分支变基到远端分支：

```bash
git rebase remote/ascendc_pto
```

**注意**: 如果有未提交的修改，需要先暂存：

```bash
git stash && git rebase remote/ascendc_pto && git stash pop
```

### 4. 推送到个人远端仓库

```bash
git push origin ascendc_pto
```

将更新后的本地分支推送到个人仓库 `origin`。

## 验证操作

### 查看远端仓库配置

```bash
git remote -v
```

输出示例：
```
origin  git@github.com:jackwangc/tilelang-ascend.git (fetch)
origin  git@github.com:jackwangc/tilelang-ascend.git (push)
remote  git@github.com:tile-ai/tilelang-ascend.git (fetch)
remote  git@github.com:tile-ai/tilelang-ascend.git (push)
```

### 查看分支状态

```bash
git status
git log --oneline -5
```

## 操作结果

执行本次同步后：
- 本地 `ascendc_pto` 分支从 `bad5921` 更新到 `9e0c055`
- 获取到 160 个新的提交
- 三个仓库的分支保持一致：本地、origin、remote

## 常用同步方式对比

| 方式 | 命令 | 特点 |
|------|------|------|
| merge | `git merge remote/ascendc_pto` | 保留完整历史，会产生合并提交 |
| rebase | `git rebase remote/ascendc_pto` | 线性历史，将本地提交放在远程提交之上 |
| reset | `git reset --hard remote/ascendc_pto` | 完全丢弃本地修改，与远端完全一致 |

**推荐**: 使用 `rebase` 保持提交历史清晰。

## 注意事项

1. 在执行 `rebase` 前，确保已提交或暂存本地修改
2. 如果分支已经推送到远端并被他人使用，避免使用 `rebase`
3. 定期同步上游仓库以获取最新更新
