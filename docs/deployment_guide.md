# RuoYi-Vue-Plus 部署指南

## 一、环境要求

### 基础环境
- JDK >= 21
- MySQL >= 8.0.33
- Maven >= 3.8.x
- Node.js >= 16.x
- Redis >= 6.x

### 开发工具
- IDEA / Eclipse
- VSCode
- Navicat / DataGrip
- Redis Desktop Manager

## 二、后端部署步骤

### 1. 数据库配置
1. 创建数据库
```sql
create database `ry-vue` character set 'utf8mb4' collate 'utf8mb4_general_ci';
```

2. 导入数据库文件（按顺序执行）
- script/sql/ry_vue_5.X.sql
- script/sql/ry_job.sql（可选，若需要定时任务模块）
- script/sql/ry_workflow.sql（可选，若需要工作流模块）

### 2. Redis 配置
1. 修改 redis.conf 配置文件
```conf
# 密码配置
requirepass 123456
```

### 3. 项目配置
1. 修改数据库连接信息
   - 路径：ruoyi-admin/src/main/resources/application-*.yml
```yaml
spring:
  datasource:
    dynamic:
      primary: master
      datasource:
        master:
          url: jdbc:mysql://localhost:3306/ry-vue?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&autoReconnect=true&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true
          username: root
          password: 123456
```

2. 修改 Redis 连接信息
```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password: 123456
```

### 4. 项目编译打包
```bash
# 进入项目根目录
cd ruoyi-vue-plus

# 编译打包
mvn clean package -Dmaven.test.skip=true
```

### 5. 运行项目
```bash
# 运行项目
java -jar ruoyi-admin/target/ruoyi-admin.jar

# 或者指定配置文件运行
java -jar ruoyi-admin/target/ruoyi-admin.jar --spring.profiles.active=prod
```

## 三、前端部署步骤

### 1. 安装依赖
```bash
# 进入前端项目目录
cd ruoyi-ui

# 安装依赖
npm install --registry=https://registry.npmmirror.com
```

### 2. 修改配置
1. 修改后端接口地址
   - 路径：.env.development 或 .env.production
```
# 后端接口地址
VUE_APP_BASE_API = 'http://localhost:8080'
```

### 3. 开发环境运行
```bash
# 启动服务
npm run dev
```

### 4. 生产环境部署
```bash
# 构建生产环境
npm run build:prod

# 将 dist 目录下的文件部署到 Nginx 或其他 Web 服务器
```

## 四、注意事项

### 1. 端口占用检查
- 后端默认端口：8080
- 前端开发端口：80
- Redis 默认端口：6379
- MySQL 默认端口：3306

### 2. 常见问题处理
1. 数据库连接问题
   - 检查数据库服务是否启动
   - 检查用户名密码是否正确
   - 检查数据库字符集是否为 utf8mb4

2. Redis 连接问题
   - 检查 Redis 服务是否启动
   - 检查密码是否正确
   - 检查防火墙设置

3. 项目启动问题
   - 检查 JDK 版本是否符合要求
   - 检查 Maven 仓库依赖是否完整
   - 检查配置文件中的路径是否正确

### 3. 安全建议
1. 生产环境部署
   - 修改默认密码
   - 关闭不必要的端口
   - 配置 SSL 证书
   - 启用 Redis 密码认证
   - 定期备份数据库

2. 性能优化
   - 配置合适的 JVM 参数
   - 优化数据库连接池
   - 配置 Redis 缓存策略
   - 使用 CDN 加速静态资源

## 五、维护建议

### 1. 日常维护
- 定期检查日志文件
- 监控系统资源使用情况
- 定期清理临时文件
- 备份重要数据

### 2. 更新升级
- 关注官方更新公告
- 制定升级计划
- 备份重要数据
- 测试环境验证
- 分步骤实施升级 