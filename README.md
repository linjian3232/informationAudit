# 文件发布两级审核系统

上传者提交待发布文件，一级审核员和二级审核员依次处理。上传者可以查看审核状态和反馈。

## 项目结构

| 目录 | 内容 |
| --- | --- |
| `fileAudit-manage/` | Vue 2 前端，包含角色入口、文件管理和审核页面 |
| `fileAudit_server/` | Spring Boot 后端，启动类为 `com.informationaudit.InformationAuditApplication` |
| `database/` | MySQL 建表脚本和可选的本地演示数据 |

## 本地启动

需要 JDK 8 或 13、Maven、Node.js 和 MySQL 8。以下命令以 Windows PowerShell 为例。

1. 在 MySQL 客户端中依次执行 `database/schema.sql` 和可选的 `database/demo-data.sql`。建表脚本创建 `information_audit` 数据库和缺失的表，不覆盖已有记录。为应用准备具有该库读写权限的 MySQL 账号。

2. 在项目根目录构建前端。已有依赖时可以从 `npm.cmd run build` 开始。

   ```powershell
   Set-Location fileAudit-manage
   npm.cmd install --ignore-scripts
   npm.cmd run build
   Set-Location ..
   ```

3. 在后端目录设置 MySQL 连接信息，构建并启动。

   ```powershell
   Set-Location fileAudit_server
   $env:AUDIT_DB_USER = 'audit'
   $env:AUDIT_DB_PASSWORD = '<你的 MySQL 账号密码>'
   mvn.cmd package
   java -jar target/information-audit-server-0.0.1-SNAPSHOT.jar
   ```

   打开 [http://127.0.0.1:8888](http://127.0.0.1:8888)。后端会从 `../fileAudit-manage/dist/` 提供前端页面。开发前端时可另开终端，在 `fileAudit-manage` 运行 `npm.cmd run dev`，访问 [http://127.0.0.1:8080](http://127.0.0.1:8080)。开发服务器默认代理到 `http://127.0.0.1:8888`，可用 `AUDIT_BACKEND_URL` 指定其他后端地址。修改代码后等待重新编译，再刷新页面。

### 可选配置

| 环境变量 | 用途 |
| --- | --- |
| `AUDIT_DB_URL` | 自定义 JDBC URL，默认使用 `127.0.0.1:3306/information_audit` |
| `AUDIT_DB_USER`、`AUDIT_DB_PASSWORD` | 应用连接 MySQL 的账号和密码 |
| `AUDIT_PORT` | HTTP 端口，默认 `8888` |
| `AUDIT_BIND_ADDRESS` | HTTP 监听地址，默认 `127.0.0.1` |
| `AUDIT_STORAGE_ROOT` | 上传目录根路径，默认以启动后端时的工作目录为根 |
| `AUDIT_FRONTEND_DIR` | 前端构建产物目录，默认 `../fileAudit-manage/dist` |

使用已有数据库时，通过 `AUDIT_DB_URL` 指向原库，并将 `AUDIT_STORAGE_ROOT` 指向原上传目录的根。文件记录中的相对 URL 保持兼容。

### 本地演示账号

执行 `database/demo-data.sql` 后，四个账号共用口令 `AuditDemo2026!`，供本地演示使用。

| 账号 | 角色 |
| --- | --- |
| `demo-uploader` | 上传者 |
| `demo-reviewer` | 一级审核员 |
| `demo-final-reviewer` | 二级审核员 |
| `demo-admin` | 超级管理员 |
