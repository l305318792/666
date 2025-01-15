DROP DATABASE IF EXISTS `ry-vue`;
CREATE DATABASE `ry-vue` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `ry-vue`;

create table sys_user (
    user_id           bigint(20)      not null                   comment '用户ID',
    tenant_id         varchar(20)     default '000000'           comment '租户编号',
    dept_id           bigint(20)      default null               comment '部门ID',
    user_name         varchar(30)     not null                   comment '用户账号',
    nick_name         varchar(30)     not null                   comment '用户昵称',
    user_type         varchar(10)     default 'sys_user'         comment '用户类型（sys_user系统用户）',
    email             varchar(50)     default ''                 comment '用户邮箱',
    phonenumber       varchar(11)     default ''                 comment '手机号码',
    sex               char(1)         default '0'                comment '用户性别（0男 1女 2未知）',
    avatar            bigint(20)                                 comment '头像地址',
    password          varchar(100)    default ''                 comment '密码',
    status            char(1)         default '0'                comment '帐号状态（0正常 1停用）',
    del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
    login_ip          varchar(128)    default ''                 comment '最后登录IP',
    login_date        datetime                                   comment '最后登录时间',
    create_dept       bigint(20)      default null               comment '创建部门',
    create_by         bigint(20)      default null               comment '创建者',
    create_time       datetime                                   comment '创建时间',
    update_by         bigint(20)      default null               comment '更新者',
    update_time       datetime                                   comment '更新时间',
    remark            varchar(1000)   default null               comment '备注',
    primary key (user_id)
) engine=innodb comment = '用户信息表';

insert into sys_user (user_id, tenant_id, dept_id, user_name, nick_name, user_type, email, phonenumber, sex, avatar, password, status, del_flag, login_ip, login_date, create_dept, create_by, create_time) 
values (1, '000000', 103, 'admin', '若依', 'sys_user', 'ry@163.com', '15888888888', '0', null, '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', now(), 103, 1, now()); 

create table sys_role (
    role_id              bigint(20)      not null                   comment '角色ID',
    tenant_id            varchar(20)     default '000000'           comment '租户编号',
    role_name            varchar(500)    not null                   comment '角色名称',
    role_key             varchar(100)    not null                   comment '角色权限字符串',
    role_sort            int(4)          not null                   comment '显示顺序',
    data_scope           char(1)         default '1'                comment '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    menu_check_strictly  tinyint(1)      default 1                  comment '菜单树选择项是否关联显示',
    dept_check_strictly  tinyint(1)      default 1                  comment '部门树选择项是否关联显示',
    status               char(1)         not null                   comment '角色状态（0正常 1停用）',
    del_flag             char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
    create_dept          bigint(20)      default null               comment '创建部门',
    create_by            bigint(20)      default null               comment '创建者',
    create_time          datetime                                   comment '创建时间',
    update_by            bigint(20)      default null               comment '更新者',
    update_time          datetime                                   comment '更新时间',
    remark               varchar(1000)    default null               comment '备注',
    primary key (role_id)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_general_ci comment = '角色信息表';

insert into sys_role (role_id, tenant_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_dept, create_by, create_time, update_by, update_time) 
values ('1', '000000', '超级管理员', 'admin',  1, '1', 1, 1, '0', '0', 103, 1, now(), null, null);

insert into sys_role (role_id, tenant_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_dept, create_by, create_time, update_by, update_time)
values ('2', '000000', '普通角色',   'common', 2, '2', 1, 1, '0', '0', 103, 1, now(), null, null);

insert into sys_role (role_id, tenant_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_dept, create_by, create_time, update_by, update_time)
values ('3', '000000', '本部门及以下', 'test',   3, '4', 1, 1, '0', '0', 103, 1, now(), null, null);

insert into sys_role (role_id, tenant_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_dept, create_by, create_time, update_by, update_time)
values ('4', '000000', '仅本人',      'test1',  4, '5', 1, 1, '0', '0', 103, 1, now(), null, null); 