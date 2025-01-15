# RuoYi-Vue-Plus 医疗咨询系统开发规划

## 一、环境要求

### 1. 开发环境
- JDK：21
- MySQL：9.1.0
- Redis：6.0+
- Maven：3.8+
- Node.js：16+

### 2. 配置信息
- 数据库配置：
  ```yaml
  数据库名：ry-vue
  用户名：root
  密码：123456
  端口：3306
  ```
- Redis配置：
  ```yaml
  主机：localhost
  端口：6379
  密码：ruoyi123
  ```

## 二、技术栈

### 1. 后端技术
- 基础框架：Spring Boot 3.2.11
- 安全框架：Sa-Token 1.39.0
- 数据库操作：MyBatis-Plus 3.5.8
- 数据库连接池：HikariCP
- 缓存框架：Redis + Redisson
- 工作流引擎：Flowable 7.0.1
- 任务调度：SnailJob 1.1.2
- 权限认证：JWT + Sa-Token
- 数据库版本：MySQL 9.1.0

### 2. 前端技术
- 核心框架：Vue.js
- UI 框架：Element Plus
- 状态管理：Vuex
- 路由管理：Vue Router
- HTTP 客户端：Axios

## 三、部署步骤

### 1. 数据库部署
1. 创建数据库
   ```sql
   CREATE DATABASE `ry-vue` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
   ```
2. 执行SQL脚本（按顺序）
   - ry_vue_5.X.sql（基础脚本）
   - ry_job.sql（任务调度）
   - ry_workflow.sql（工作流）

### 2. Redis部署
1. 安装Redis 6.0+
2. 配置密码：ruoyi123
3. 确保Redis服务正常运行

### 3. 后端部署
1. 修改配置文件
   - application-dev.yml（开发环境）
   - application-prod.yml（生产环境）
2. 编译打包
   ```bash
   mvn clean package -Dmaven.test.skip=true
   ```
3. 运行服务
   ```bash
   java -jar ruoyi-admin/target/ruoyi-admin.jar
   ```

### 4. 前端部署（待补充前端仓库地址）
1. 安装依赖
   ```bash
   npm install
   ```
2. 开发环境运行
   ```bash
   npm run dev
   ```
3. 生产环境打包
   ```bash
   npm run build:prod
   ```

## 四、医疗咨询系统开发计划

### 1. 数据库设计
- 患者信息表（patient_info）
- 医生信息表（doctor_info）
- 科室信息表（department_info）
- 预约记录表（appointment_record）
- 问诊记录表（consultation_record）
- 处方信息表（prescription_info）
- 药品信息表（medicine_info）
- 检查项目表（examination_item）
- 检查结果表（examination_result）
- 病历档案表（medical_record）

### 2. 功能模块规划

#### 2.1 基础管理模块
- 用户管理（继承原框架）
- 角色权限（继承原框架）
- 菜单管理（继承原框架）
- 部门管理（扩展为科室管理）
- 字典管理（继承原框架）

#### 2.2 医生管理模块
- 医生信息管理
- 排班管理
- 接诊管理
- 处方管理
- 病历管理

#### 2.3 患者管理模块
- 患者信息管理
- 就诊记录管理
- 检查记录管理
- 处方记录管理
- 病历档案管理

#### 2.4 预约挂号模块
- 预约挂号
- 取消预约
- 预约查询
- 预约提醒

#### 2.5 在线问诊模块
- 在线咨询
- 图文问诊
- 视频问诊
- 问诊记录

#### 2.6 处方管理模块
- 处方开具
- 处方审核
- 处方查询
- 药品管理

#### 2.7 检查管理模块
- 检查项目管理
- 检查预约
- 检查结果管理
- 检查报告管理

### 3. 开发流程

#### 3.1 前期准备（2周）
- [x] 环境搭建
- [ ] 数据库设计
- [ ] 接口文档设计
- [ ] 前端页面原型设计

#### 3.2 基础功能开发（3周）
- [ ] 数据库表创建
- [ ] 代码生成器生成基础代码
- [ ] 基础管理模块调整
- [ ] 科室管理功能开发

#### 3.3 核心功能开发（8周）
- [ ] 医生管理模块
- [ ] 患者管理模块
- [ ] 预约挂号模块
- [ ] 在线问诊模块
- [ ] 处方管理模块
- [ ] 检查管理模块

#### 3.4 系统优化（3周）
- [ ] 性能优化
- [ ] 安全加固
- [ ] 功能测试
- [ ] Bug修复

#### 3.5 部署上线（1周）
- [ ] 系统部署
- [ ] 数据迁移
- [ ] 系统测试
- [ ] 用户培训

### 4. 注意事项

#### 4.1 安全性
- 医疗数据加密存储
- 数据访问权限控制
- 操作日志完整记录
- 敏感信息脱敏处理

#### 4.2 性能
- 大数据量性能优化
- 图片存储优化
- 缓存策略优化
- 数据库索引优化

#### 4.3 规范
- 代码规范遵循阿里巴巴Java开发规范
- 注释完整，使用JSDoc规范
- 提交规范遵循Angular提交规范
- 接口遵循RESTful规范

## 五、代码生成器使用说明

### 1. 基础使用
1. 创建数据库表
2. 使用代码生成器生成代码
3. 调整生成的代码
4. 集成到系统中

### 2. 生成规则
- 表名规范：模块名_业务名
- 字段规范：下划线命名法
- 注释规范：中文说明
- 必备字段：create_time, update_time, create_by, update_by, del_flag

### 3. 举例
```sql
CREATE TABLE `medical_patient` (
  `patient_id` bigint(20) NOT NULL COMMENT '患者ID',
  `patient_name` varchar(50) NOT NULL COMMENT '患者姓名',
  `gender` char(1) NOT NULL COMMENT '性别（0男 1女）',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `phone` varchar(11) NOT NULL COMMENT '手机号码',
  `id_card` varchar(18) NOT NULL COMMENT '身份证号',
  `address` varchar(200) DEFAULT NULL COMMENT '住址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='患者信息表';
``` 

## 六、开发规则

### 1. 代码规范
#### 1.1 Java代码规范
- 遵循阿里巴巴Java开发手册规范
- 类名：大驼峰命名，如：MedicalPatient
- 方法名：小驼峰命名，如：getPatientInfo
- 变量名：小驼峰命名，如：patientName
- 常量名：全大写下划线分隔，如：MAX_PATIENT_COUNT
- 包名：全小写，如：org.dromara.medical.patient

#### 1.2 注释规范
- 类注释：使用JSDoc规范
```java
/**
 * 患者信息服务类
 *
 * @author 作者
 * @version 1.0
 * @date 2024-01-14
 */
```
- 方法注释：
```java
/**
 * 获取患者信息
 *
 * @param patientId 患者ID
 * @return 患者信息对象
 * @throws ServiceException 业务异常
 */
```
- 变量注释：关键变量添加行注释
```java
// 患者年龄（岁）
private Integer patientAge;
```

#### 1.3 前端代码规范
- Vue文件名：大驼峰命名，如：PatientList.vue
- 组件名：大驼峰命名，如：PatientInfoCard
- CSS类名：kebab-case命名，如：patient-info-card
- 事件名：kebab-case命名，如：@patient-selected

### 2. Git提交规范
#### 2.1 分支管理
- master：主分支，用于生产环境
- develop：开发分支，用于开发环境
- feature/*：功能分支，如：feature/patient-management
- hotfix/*：紧急修复分支，如：hotfix/login-bug
- release/*：发布分支，如：release/v1.0.0

#### 2.2 提交信息规范
遵循Angular提交规范：
```
<type>(<scope>): <subject>

<body>

<footer>
```
- type类型：
  - feat：新功能
  - fix：修复
  - docs：文档
  - style：格式（不影响代码运行的变动）
  - refactor：重构
  - test：增加测试
  - chore：构建过程或辅助工具的变动

- scope范围：
  - patient：患者模块
  - doctor：医生模块
  - appointment：预约模块
  - prescription：处方模块
  - common：公共模块

- subject：简短描述，不超过50个字符
- body：详细描述
- footer：不兼容变动、关闭issue等

示例：
```
feat(patient): 添加患者信息管理功能

1. 新增患者信息CRUD接口
2. 添加患者信息验证
3. 实现患者信息导入导出

Close #123
```

### 3. 接口规范
#### 3.1 RESTful API规范
- URI使用名词复数形式：/api/patients
- 使用HTTP方法表示操作：
  - GET：查询
  - POST：创建
  - PUT：更新
  - DELETE：删除
- 状态码规范：
  - 200：成功
  - 201：创建成功
  - 400：请求错误
  - 401：未授权
  - 403：禁止访问
  - 404：资源不存在
  - 500：服务器错误

#### 3.2 响应格式规范
```json
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "patientId": "1",
        "patientName": "张三"
    }
}
```

### 4. 数据库规范
#### 4.1 命名规范
- 表名：小写下划线分隔，模块名_业务名
- 字段名：小写下划线分隔
- 主键：表名_id
- 外键：关联表名_id
- 索引：idx_字段名

#### 4.2 字段规范
- 必备字段：
  - create_time：创建时间
  - create_by：创建者
  - update_time：更新时间
  - update_by：更新者
  - del_flag：删除标志
- 字段类型规范：
  - 字符串：varchar(n)
  - 大文本：text
  - 整数：int/bigint
  - 小数：decimal(p,s)
  - 日期时间：datetime
  - 布尔：char(1)

### 5. 安全规范
#### 5.1 数据安全
- 敏感数据加密存储（如身份证、手机号）
- 传输数据使用HTTPS
- 关键操作需要二次验证
- 数据库备份策略

#### 5.2 访问控制
- 基于RBAC的权限控制
- 操作日志记录
- 敏感操作审计
- 登录失败次数限制

#### 5.3 漏洞防护
- SQL注入防护
- XSS防护
- CSRF防护
- 文件上传限制

### 6. 性能规范
#### 6.1 代码层面
- 使用缓存减少数据库访问
- 批量操作优化
- 大数据量分页查询
- 异步处理耗时操作

#### 6.2 数据库层面
- 合理使用索引
- 避免全表扫描
- 优化SQL语句
- 适当冗余设计

#### 6.3 缓存策略
- 多级缓存设计
- 缓存预热
- 缓存更新策略
- 防止缓存穿透和雪崩 