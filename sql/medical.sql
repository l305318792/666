-- ----------------------------
-- 2. 医生信息表
-- ----------------------------
DROP TABLE IF EXISTS med_doctor;
CREATE TABLE med_doctor (
    doctor_id bigint PRIMARY KEY COMMENT '医生ID',
    user_id bigint NOT NULL COMMENT '关联用户ID',
    department_id bigint COMMENT '科室ID',
    professional_title varchar(50) COMMENT '职称',
    expertise varchar(500) COMMENT '专长',
    introduction text COMMENT '个人简介',
    practice_no varchar(50) COMMENT '执业证号',
    status char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
    del_flag char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    create_time datetime COMMENT '创建时间',
    update_time datetime COMMENT '更新时间',
    create_by varchar(64) COMMENT '创建者',
    update_by varchar(64) COMMENT '更新者'
) ENGINE=InnoDB COMMENT '医生信息表';

-- ----------------------------
-- 3. 患者信息表
-- ----------------------------
DROP TABLE IF EXISTS med_patient;
CREATE TABLE med_patient (
    patient_id bigint PRIMARY KEY COMMENT '患者ID',
    user_id bigint NOT NULL COMMENT '关联用户ID',
    gender char(1) COMMENT '性别',
    birth_date date COMMENT '出生日期',
    address varchar(200) COMMENT '居住地址',
    emergency_contact varchar(50) COMMENT '紧急联系人',
    emergency_phone varchar(11) COMMENT '紧急联系电话',
    medical_history text COMMENT '既往病史',
    allergies varchar(500) COMMENT '过敏史',
    del_flag char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    create_time datetime COMMENT '创建时间',
    update_time datetime COMMENT '更新时间',
    create_by varchar(64) COMMENT '创建者',
    update_by varchar(64) COMMENT '更新者'
) ENGINE=InnoDB COMMENT '患者信息表';

-- ----------------------------
-- 4. 排班表
-- ----------------------------
DROP TABLE IF EXISTS med_schedule;
CREATE TABLE med_schedule (
    schedule_id bigint PRIMARY KEY COMMENT '排班ID',
    doctor_id bigint NOT NULL COMMENT '医生ID',
    schedule_date date NOT NULL COMMENT '排班日期',
    period char(1) NOT NULL COMMENT '时段(1上午 2下午)',
    max_patients int DEFAULT 0 COMMENT '最大预约人数',
    available_patients int DEFAULT 0 COMMENT '可预约人数',
    status char(1) DEFAULT '0' COMMENT '状态(0正常 1停诊)',
    del_flag char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    create_time datetime COMMENT '创建时间',
    update_time datetime COMMENT '更新时间',
    create_by varchar(64) COMMENT '创建者',
    update_by varchar(64) COMMENT '更新者'
) ENGINE=InnoDB COMMENT '医生排班表';

-- ----------------------------
-- 5. 预约记录表
-- ----------------------------
DROP TABLE IF EXISTS med_appointment;
CREATE TABLE med_appointment (
    appointment_id bigint PRIMARY KEY COMMENT '预约ID',
    patient_id bigint NOT NULL COMMENT '患者ID',
    doctor_id bigint NOT NULL COMMENT '医生ID',
    schedule_id bigint NOT NULL COMMENT '排班ID',
    appointment_time datetime NOT NULL COMMENT '预约时间',
    status char(1) DEFAULT '0' COMMENT '状态(0待就诊 1已完成 2已取消)',
    symptom text COMMENT '症状描述',
    cancel_reason varchar(500) COMMENT '取消原因',
    del_flag char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    create_time datetime COMMENT '创建时间',
    update_time datetime COMMENT '更新时间',
    create_by varchar(64) COMMENT '创建者',
    update_by varchar(64) COMMENT '更新者'
) ENGINE=InnoDB COMMENT '预约记录表';

-- ----------------------------
-- 6. 问诊记录表
-- ----------------------------
DROP TABLE IF EXISTS med_consultation;
CREATE TABLE med_consultation (
    consultation_id bigint PRIMARY KEY COMMENT '问诊ID',
    appointment_id bigint COMMENT '关联预约ID',
    patient_id bigint NOT NULL COMMENT '患者ID',
    doctor_id bigint NOT NULL COMMENT '医生ID',
    consultation_time datetime COMMENT '问诊时间',
    symptom text COMMENT '症状描述',
    diagnosis text COMMENT '诊断结果',
    treatment text COMMENT '治疗方案',
    prescription_id bigint COMMENT '处方ID',
    status char(1) DEFAULT '0' COMMENT '状态(0待就诊 1进行中 2已完成)',
    del_flag char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    create_time datetime COMMENT '创建时间',
    update_time datetime COMMENT '更新时间',
    create_by varchar(64) COMMENT '创建者',
    update_by varchar(64) COMMENT '更新者'
) ENGINE=InnoDB COMMENT '问诊记录表';

-- ----------------------------
-- 7. 处方表
-- ----------------------------
DROP TABLE IF EXISTS med_prescription;
CREATE TABLE med_prescription (
    prescription_id bigint PRIMARY KEY COMMENT '处方ID',
    consultation_id bigint NOT NULL COMMENT '问诊ID',
    patient_id bigint NOT NULL COMMENT '患者ID',
    doctor_id bigint NOT NULL COMMENT '医生ID',
    diagnosis varchar(500) COMMENT '诊断结果',
    remark varchar(500) COMMENT '医嘱',
    status char(1) DEFAULT '0' COMMENT '状态(0未支付 1已支付 2已失效)',
    total_amount decimal(10,2) COMMENT '总金额',
    del_flag char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    create_time datetime COMMENT '创建时间',
    update_time datetime COMMENT '更新时间',
    create_by varchar(64) COMMENT '创建者',
    update_by varchar(64) COMMENT '更新者'
) ENGINE=InnoDB COMMENT '处方表';

-- ----------------------------
-- 8. 处方详情表
-- ----------------------------
DROP TABLE IF EXISTS med_prescription_detail;
CREATE TABLE med_prescription_detail (
    detail_id bigint PRIMARY KEY COMMENT '详情ID',
    prescription_id bigint NOT NULL COMMENT '处方ID',
    medicine_name varchar(100) NOT NULL COMMENT '药品名称',
    specification varchar(100) COMMENT '规格',
    usage_method varchar(200) COMMENT '用法',
    dosage varchar(50) COMMENT '用量',
    units varchar(20) COMMENT '单位',
    days int COMMENT '天数',
    quantity int COMMENT '数量',
    price decimal(10,2) COMMENT '单价',
    amount decimal(10,2) COMMENT '金额',
    del_flag char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    create_time datetime COMMENT '创建时间',
    update_time datetime COMMENT '更新时间'
) ENGINE=InnoDB COMMENT '处方详情表';

-- ----------------------------
-- 9. 就诊评价表
-- ----------------------------
DROP TABLE IF EXISTS med_evaluation;
CREATE TABLE med_evaluation (
    evaluation_id bigint PRIMARY KEY COMMENT '评价ID',
    consultation_id bigint NOT NULL COMMENT '问诊ID',
    patient_id bigint NOT NULL COMMENT '患者ID',
    doctor_id bigint NOT NULL COMMENT '医生ID',
    rating int NOT NULL COMMENT '评分(1-5)',
    content text COMMENT '评价内容',
    reply text COMMENT '医生回复',
    status char(1) DEFAULT '0' COMMENT '状态(0未回复 1已回复)',
    del_flag char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    create_time datetime COMMENT '创建时间',
    update_time datetime COMMENT '更新时间',
    create_by varchar(64) COMMENT '创建者',
    update_by varchar(64) COMMENT '更新者'
) ENGINE=InnoDB COMMENT '就诊评价表';

-- ----------------------------
-- 10. 支付记录表
-- ----------------------------
DROP TABLE IF EXISTS med_payment;
CREATE TABLE med_payment (
    payment_id bigint PRIMARY KEY COMMENT '支付ID',
    prescription_id bigint NOT NULL COMMENT '处方ID',
    patient_id bigint NOT NULL COMMENT '患者ID',
    payment_amount decimal(10,2) NOT NULL COMMENT '支付金额',
    payment_type char(1) COMMENT '支付方式(1微信 2支付宝)',
    transaction_id varchar(100) COMMENT '交易流水号',
    status char(1) DEFAULT '0' COMMENT '状态(0待支付 1已支付 2已退款)',
    del_flag char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    create_time datetime COMMENT '创建时间',
    update_time datetime COMMENT '更新时间',
    pay_time datetime COMMENT '支付时间'
) ENGINE=InnoDB COMMENT '支付记录表'; 