/*
 Navicat Premium Data Transfer

 Source Server         : zucc
 Source Server Type    : MySQL
 Source Server Version : 100419
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 100419
 File Encoding         : 65001

 Date: 10/07/2021 15:24:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_admin
-- ----------------------------
DROP TABLE IF EXISTS `base_admin`;
CREATE TABLE `base_admin`
(
    `admin_id`    varchar(32) NOT NULL COMMENT '管理员id',
    `password`    varchar(32) NOT NULL COMMENT '管理员密码',
    `admin_type`  char(2)     NOT NULL COMMENT '管理员类型，1超级管理员，2普通管理员',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `delete_mark` boolean DEFAULT FALSE COMMENT '软删除标记',
    PRIMARY KEY (`admin_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='管理员表';

-- ----------------------------
-- Records of base_account
-- ----------------------------
BEGIN;
INSERT INTO `base_admin`
VALUES ('root', 'e10adc3949ba59abbe56e057f20f883e', '1', NOW(), FALSE);
COMMIT;

-- ----------------------------
-- Table structure for base_account
-- ----------------------------
DROP TABLE IF EXISTS `base_account`;
CREATE TABLE `base_account`
(
    `user_id`      int(11)     NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `nick_name`    varchar(32) NOT NULL COMMENT '用户昵称',
    `avatar_url`   varchar(168) DEFAULT NULL COMMENT '用户头像',
    `user_type`    char(2)     NOT NULL COMMENT '用户类型，1居民，2医生',
    `mini_open_id` varchar(36) NOT NULL COMMENT '微信小程序openid',
    `phone_no`     varchar(16)  DEFAULT NULL COMMENT '手机号码',
    `create_time`  datetime    NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

-- ----------------------------
-- Records of base_account
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_org
-- ----------------------------
DROP TABLE IF EXISTS `base_org`;
CREATE TABLE `base_org`
(
    `org_id`      int(11)     NOT NULL AUTO_INCREMENT COMMENT '机构id',
    `org_name`    varchar(64) NOT NULL COMMENT '机构名称',
    `creator_id`  varchar(32) NOT NULL COMMENT '创建者ID',
    `delete_mark` boolean DEFAULT FALSE COMMENT '软删除标记',
    PRIMARY KEY (`org_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4 COMMENT ='科室表';

-- ----------------------------
-- Records of base_org
-- ----------------------------
BEGIN;
INSERT INTO `base_org`
VALUES (1, '创业惠康医院', 'root', FALSE);
COMMIT;

-- ----------------------------
-- Table structure for base_dept
-- ----------------------------
DROP TABLE IF EXISTS `base_dept`;
CREATE TABLE `base_dept`
(
    `dept_id`     int(11)     NOT NULL AUTO_INCREMENT COMMENT '科室id',
    `dept_name`   varchar(64) NOT NULL COMMENT '科室名称',
    `org_id`      int(64)     NOT NULL COMMENT '机构id',
    `creator_id`  varchar(32) NOT NULL COMMENT '创建者ID',
    `delete_mark` boolean DEFAULT FALSE COMMENT '软删除标记',
    PRIMARY KEY (`dept_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  DEFAULT CHARSET = utf8mb4 COMMENT ='科室表';

-- ----------------------------
-- Records of base_dept
-- ----------------------------
BEGIN;
INSERT INTO `base_dept`
VALUES (1, '急诊内科', 1, 'root', FALSE);
INSERT INTO `base_dept`
VALUES (2, '急诊外科', 1, 'root', FALSE);
INSERT INTO `base_dept`
VALUES (4, '呼吸科', 1, 'root', FALSE);
INSERT INTO `base_dept`
VALUES (5, '消化科', 1, 'root', FALSE);
INSERT INTO `base_dept`
VALUES (6, '内分泌科', 1, 'root', FALSE);
INSERT INTO `base_dept`
VALUES (7, '风湿免疫科', 1, 'root', FALSE);
INSERT INTO `base_dept`
VALUES (8, '皮肤科', 1, 'root', FALSE);
INSERT INTO `base_dept`
VALUES (9, '肿瘤科', 1, 'root', FALSE);
INSERT INTO `base_dept`
VALUES (10, '神经内科', 1, 'root', FALSE);
INSERT INTO `base_dept`
VALUES (11, '神经外科', 1, 'root', FALSE);
INSERT INTO `base_dept`
VALUES (12, '儿科', 1, 'root', FALSE);
INSERT INTO `base_dept`
VALUES (13, '检验科', 1, 'root', FALSE);
INSERT INTO `base_dept`
VALUES (14, '影像科', 1, 'root', FALSE);
INSERT INTO `base_dept`
VALUES (15, '超声科', 1, 'root', FALSE);
COMMIT;

-- ----------------------------
-- View structure for org_dept
-- ----------------------------
DROP VIEW IF EXISTS `org_dept`;
CREATE VIEW `org_dept` AS
SELECT base_dept.org_id, `org_name`, `dept_id`, `dept_name`
FROM base_dept
         JOIN base_org ON
    base_dept.org_id = base_org.org_id
WHERE base_dept.delete_mark = FALSE
  AND base_org.delete_mark = FALSE;

-- ----------------------------
-- Table structure for base_doctor
-- ----------------------------
DROP TABLE IF EXISTS `base_doctor`;
CREATE TABLE `base_doctor`
(
    `doctor_id`   int(11)     NOT NULL AUTO_INCREMENT COMMENT '医生id',
    `doctor_name` varchar(64) NOT NULL COMMENT '医生姓名',
    `avatar_url`  varchar(255) DEFAULT NULL COMMENT '医生头像链接',
    `level_code`  char(2)      DEFAULT NULL COMMENT '医生职称，1主任医师，2副主任医师，3主治医师，4医师，5医士',
    `level_name`  varchar(32)  DEFAULT NULL COMMENT '医生职称，1主任医师，2副主任医师，3主治医师，4医师，5医士',
    `phone_no`    varchar(16)  DEFAULT NULL COMMENT '手机号码',
    `dept_id`     int(11)     NOT NULL COMMENT '科室id',
    `delete_mark` boolean      DEFAULT FALSE COMMENT '软删除标记',
    PRIMARY KEY (`doctor_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  DEFAULT CHARSET = utf8mb4 COMMENT ='医生表';

-- ----------------------------
-- Records of base_doctor
-- ----------------------------
BEGIN;
INSERT INTO `base_doctor`
VALUES (1, '贺新语', 'https://profile.csdnimg.cn/6/5/0/3_qq_34912507', '1', '主任医师', 13565774836, 1, FALSE);
INSERT INTO `base_doctor`
VALUES (2, '温冷珍', 'https://profile.csdnimg.cn/C/6/9/3_weixin_48444868', '2', '副主任医师', 13656654771, 1, FALSE);
INSERT INTO `base_doctor`
VALUES (3, '孔访旋', 'https://profile.csdnimg.cn/6/8/2/3_liyuanjinglyj', '2', '副主任医师', 13256644771, 1, FALSE);
INSERT INTO `base_doctor`
VALUES (4, '杨思洁', NULL, '3', '主治医师', 13656644771, 1, FALSE);
INSERT INTO `base_doctor`
VALUES (5, '松琳怡', NULL, '3', '主治医师', 13656644771, 1, FALSE);
INSERT INTO `base_doctor`
VALUES (6, '芮午瑶', NULL, '3', '主治医师', 13656684771, 1, FALSE);
INSERT INTO `base_doctor`
VALUES (7, '羿怜阳', NULL, '3', '主治医师', 12656644771, 1, FALSE);
INSERT INTO `base_doctor`
VALUES (8, '桂吉玟', NULL, '4', '医师', 13656649771, 1, FALSE);
INSERT INTO `base_doctor`
VALUES (9, '燕乐蓉', NULL, '4', '医师', 13656644731, 1, FALSE);
INSERT INTO `base_doctor`
VALUES (10, '于忻慕', NULL, '4', '医师', 13656464771, 1, FALSE);
INSERT INTO `base_doctor`
VALUES (11, '冷代芹', NULL, '4', '医师', 13646644771, 1, FALSE);
INSERT INTO `base_doctor`
VALUES (12, '朱含海', NULL, '4', '医师', 13656544771, 1, FALSE);
INSERT INTO `base_doctor`
VALUES (13, '侯映菡', NULL, '3', '主治医师', 18656444771, 2, FALSE);
COMMIT;

-- ----------------------------
-- View structure for dept_doctor
-- ----------------------------
DROP VIEW IF EXISTS `dept_doctor`;
CREATE VIEW `dept_doctor` AS
SELECT `doctor_id`,
       `doctor_name`,
       `avatar_url`,
       `level_code`,
       `level_name`,
       `org_id`,
       `org_name`,
       base_doctor.dept_id,
       `dept_name`,
       `phone_no`
FROM base_doctor
         JOIN org_dept ON
    base_doctor.dept_id = org_dept.dept_id
WHERE base_doctor.delete_mark = FALSE;

-- ----------------------------
-- Table structure for base_patient
-- ----------------------------
DROP TABLE IF EXISTS `base_patient`;
CREATE TABLE `base_patient`
(
    `patient_id`         int(11)     NOT NULL AUTO_INCREMENT COMMENT '问诊人id',
    `patient_name`       varchar(64) NOT NULL COMMENT '问诊人姓名',
    `patient_card_type`  char(4)     NOT NULL COMMENT '问诊人证件类型，1居民身份证 2居民户口簿 3护照 4军官证 5驾驶证 6港澳居民来往内地通行证
7台湾居民来往内地通行证 11出生证明 12港澳居民身份证 13港澳居民居住证 99其他法定有效证件',
    `patient_card_id`    varchar(64) NOT NULL COMMENT '问诊人证件号码',
    `patient_gender`     char(2)     NOT NULL COMMENT '问诊人性别，1男，2女',
    `patient_birth_date` datetime    NOT NULL COMMENT '问诊人出生日期',
    `patient_birth_age`  tinyint(4)  NOT NULL COMMENT '问诊人年龄',
    `patient_phone_no`   varchar(16) NOT NULL COMMENT '问诊人手机号码',
    `creator_id`         int(11)     NOT NULL COMMENT '创建用户id',
    `delete_mark`        boolean DEFAULT FALSE COMMENT '软删除标记',
    PRIMARY KEY (`patient_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='问诊人表';

-- ----------------------------
-- Records of base_patient
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_diagnosis
-- ----------------------------
DROP TABLE IF EXISTS `base_diagnosis`;
CREATE TABLE `base_diagnosis`
(
    `diagnosis_id`   int(11)     NOT NULL AUTO_INCREMENT COMMENT '诊断标识',
    `diagnosis_type` char(2)     NOT NULL COMMENT '诊断类型，1西医，2中医',
    `diseases_code`  varchar(36) NOT NULL COMMENT '诊断类型为西医时为疾病代码，为中医时为中医疾病代码',
    `diseases_name`  varchar(36) NOT NULL COMMENT '诊断类型为西医时为疾病名称，为中医时为中医疾病名称',
    `pinyin_code`    varchar(36) NOT NULL COMMENT '拼音码',
    `delete_mark`    boolean DEFAULT FALSE COMMENT '软删除标记',
    PRIMARY KEY (`diagnosis_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 52
  DEFAULT CHARSET = utf8mb4 COMMENT ='基础诊断表';

-- ----------------------------
-- Records of base_diagnosis
-- ----------------------------
BEGIN;
INSERT INTO `base_diagnosis`
VALUES (1, '1', 'J11.101', '流行性感冒', 'LXXGM', FALSE);
INSERT INTO `base_diagnosis`
VALUES (2, '1', 'J10.151', '流行性感冒性咽炎', 'LXXGMXYY', FALSE);
INSERT INTO `base_diagnosis`
VALUES (3, '1', 'J98.802', '呼吸道感染', 'HXDGR', FALSE);
INSERT INTO `base_diagnosis`
VALUES (4, '1', 'J06.903', '上呼吸道感染', 'SHXDGR', FALSE);
INSERT INTO `base_diagnosis`
VALUES (5, '1', 'E14.900', '糖尿病', 'TNB', FALSE);
INSERT INTO `base_diagnosis`
VALUES (6, '1', 'E13.904', '医源性糖尿病', 'YYXTNB', FALSE);
INSERT INTO `base_diagnosis`
VALUES (7, '1', 'I10.X02', '高血压', 'GXY', FALSE);
INSERT INTO `base_diagnosis`
VALUES (8, '1', 'H35.001', '高血压性视网膜病', 'GXYXSWMB', FALSE);
INSERT INTO `base_diagnosis`
VALUES (9, '1', 'A08.401', '病毒性肠炎', 'BDXCY', FALSE);
INSERT INTO `base_diagnosis`
VALUES (10, '1', 'A01.001', '伤寒', 'SH', FALSE);
INSERT INTO `base_diagnosis`
VALUES (11, '1', 'A01.052', '伤寒腹膜炎', 'SHFMY', FALSE);
INSERT INTO `base_diagnosis`
VALUES (12, '1', 'K27.503', '消化道穿孔', 'SHDCK', FALSE);
INSERT INTO `base_diagnosis`
VALUES (13, '1', 'K27.451', '消化性溃疡伴出血', 'XHDKYBCX', FALSE);
INSERT INTO `base_diagnosis`
VALUES (14, '1', 'K30', '消化不良', 'XHBL', FALSE);
INSERT INTO `base_diagnosis`
VALUES (15, '1', 'A16.305', '支气管淋巴结结核', 'ZQGLBJJH', FALSE);
INSERT INTO `base_diagnosis`
VALUES (16, '1', 'B37.805', '支气管念珠菌病', 'ZQGNZJB', FALSE);
INSERT INTO `base_diagnosis`
VALUES (17, '1', 'C34.905', '支气管恶性肿瘤', 'ZQGEXZL', FALSE);
INSERT INTO `base_diagnosis`
VALUES (18, '1', 'R22.951', '表浅(局限)肿块', 'QBJXZK', FALSE);
INSERT INTO `base_diagnosis`
VALUES (19, '1', 'R22.451', '腿部炎性肿块', 'TBYXZK', FALSE);
INSERT INTO `base_diagnosis`
VALUES (20, '1', 'A18.405', '结核性皮肤脓肿', 'JHXPFNZ', FALSE);
INSERT INTO `base_diagnosis`
VALUES (21, '1', 'A18.407', '皮肤和皮下组织结核', 'PFHPXZZJH', FALSE);
INSERT INTO `base_diagnosis`
VALUES (22, '1', 'A18.408', '皮肤结核', 'PFJH', FALSE);
INSERT INTO `base_diagnosis`
VALUES (23, '1', 'A36.301', '皮肤白喉', 'PFBH', FALSE);
INSERT INTO `base_diagnosis`
VALUES (24, '1', 'E51.101', '脚气病', 'JQB', FALSE);
INSERT INTO `base_diagnosis`
VALUES (25, '1', 'E51.152', '干性脚气病', 'GXJQB', FALSE);
INSERT INTO `base_diagnosis`
VALUES (26, '1', 'S90.351', '脚挫伤', 'JCS', FALSE);
INSERT INTO `base_diagnosis`
VALUES (27, '1', 'M54.475', '腰腿痛', 'YTT', FALSE);
INSERT INTO `base_diagnosis`
VALUES (28, '1', 'L03.104', '腿蜂窝织炎', 'TFWZY', FALSE);
INSERT INTO `base_diagnosis`
VALUES (29, '1', 'Q68.551', '腿弓形', 'TGX', FALSE);
INSERT INTO `base_diagnosis`
VALUES (30, '1', 'R22.451', '腿部炎性肿块', 'TBYXZK', FALSE);
INSERT INTO `base_diagnosis`
VALUES (31, '1', 'R60.051', '遗传性腿部水肿', 'YCXTBSZ', FALSE);
INSERT INTO `base_diagnosis`
VALUES (32, '1', 'S70.901', '大腿血肿', 'DTXZ', FALSE);
INSERT INTO `base_diagnosis`
VALUES (33, '1', 'S71.101', '大腿撕脱伤', 'DTSTS', FALSE);
INSERT INTO `base_diagnosis`
VALUES (34, '1', 'S71.102', '大腿开放性伤口', 'DTKFXSK', FALSE);
INSERT INTO `base_diagnosis`
VALUES (35, '1', 'C76.502', '小腿恶性肿瘤', 'XTEXZL', FALSE);
INSERT INTO `base_diagnosis`
VALUES (36, '1', 'B08.401', '手足口病', 'SZKB', FALSE);
INSERT INTO `base_diagnosis`
VALUES (37, '1', 'B35.201', '手癣', 'SX', FALSE);
INSERT INTO `base_diagnosis`
VALUES (38, '1', 'B35.251', '手皮真菌病', 'SPZJB', FALSE);
INSERT INTO `base_diagnosis`
VALUES (39, '1', 'B35.851', '手足癣', 'SZX', FALSE);
INSERT INTO `base_diagnosis`
VALUES (40, '1', 'E89.002', '手术后甲状腺机能减退', 'SSHJZXJNJT', FALSE);
INSERT INTO `base_diagnosis`
VALUES (41, '1', 'E89.101', '手术后低胰岛素血症', 'SSHDYDSXZ', FALSE);
INSERT INTO `base_diagnosis`
VALUES (42, '1', 'E89.301', '手术后垂体机能减退', 'SSHCTJNJT', FALSE);
INSERT INTO `base_diagnosis`
VALUES (43, '1', 'B15.902', '传染性肝炎', 'CRXGY', FALSE);
INSERT INTO `base_diagnosis`
VALUES (44, '1', 'B15.901', '甲型病毒性肝炎', 'JXBDXGY', FALSE);
INSERT INTO `base_diagnosis`
VALUES (45, '1', 'B15.907', '甲型病毒性重型肝炎', 'JXBDXZXGY', FALSE);
INSERT INTO `base_diagnosis`
VALUES (46, '1', 'A02.006', '沙门氏菌胃肠炎', 'SMSJWCY', FALSE);
INSERT INTO `base_diagnosis`
VALUES (47, '1', 'A02.004', '沙门氏菌性肠炎', 'SMSJXCY', FALSE);
INSERT INTO `base_diagnosis`
VALUES (48, '1', 'A01.057', '伤寒样小肠炎', 'SHXXCY', FALSE);
INSERT INTO `base_diagnosis`
VALUES (49, '1', 'A01.056', '肠伤寒', 'CSH', FALSE);
INSERT INTO `base_diagnosis`
VALUES (50, '1', 'A05.952', '胃肠型食物中毒', 'WCXSWZD', FALSE);
INSERT INTO `base_diagnosis`
VALUES (51, '1', 'A01.055', '伤寒性肠穿孔', 'SHXCCK', FALSE);
COMMIT;

-- ----------------------------
-- Table structure for base_dic_drug_frequency
-- ----------------------------
DROP TABLE IF EXISTS `base_dic_drug_frequency`;
CREATE TABLE `base_dic_drug_frequency`
(
    `drug_frequency_id`        int(11)     NOT NULL AUTO_INCREMENT COMMENT '药品频次id',
    `drug_frequency_name`      varchar(32) NOT NULL COMMENT '药品频次名称',
    `drug_frequency_name_abbr` varchar(32) NOT NULL COMMENT '药品频次名称缩写',
    PRIMARY KEY (`drug_frequency_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4 COMMENT ='药品频次表';

-- ----------------------------
-- Records of base_dic_drug_frequency
-- ----------------------------
BEGIN;
INSERT INTO `base_dic_drug_frequency`
VALUES (1, '每日一次', 'qd');
INSERT INTO `base_dic_drug_frequency`
VALUES (2, '每日两次', 'bid');
INSERT INTO `base_dic_drug_frequency`
VALUES (3, '每日三次', 'tid');
INSERT INTO `base_dic_drug_frequency`
VALUES (4, '每日四次', 'qid');
INSERT INTO `base_dic_drug_frequency`
VALUES (5, '隔日一次', 'qod');
INSERT INTO `base_dic_drug_frequency`
VALUES (6, '每周一次', 'qw');
COMMIT;

-- ----------------------------
-- Table structure for base_dic_drug_usage
-- ----------------------------
DROP TABLE IF EXISTS `base_dic_drug_usage`;
CREATE TABLE `base_dic_drug_usage`
(
    `drug_usage_id`   int(11)     NOT NULL AUTO_INCREMENT COMMENT '药品用法id',
    `drug_usage_name` varchar(32) NOT NULL COMMENT '药品用法名称',
    PRIMARY KEY (`drug_usage_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4 COMMENT ='药品用法表';

-- ----------------------------
-- Records of base_dic_drug_usage
-- ----------------------------
BEGIN;
INSERT INTO `base_dic_drug_usage`
VALUES (1, '口服');
INSERT INTO `base_dic_drug_usage`
VALUES (2, '涂抹');
INSERT INTO `base_dic_drug_usage`
VALUES (3, '静脉注射');
INSERT INTO `base_dic_drug_usage`
VALUES (4, '皮下注射');
COMMIT;

-- ----------------------------
-- Table structure for base_drug
-- ----------------------------
DROP TABLE IF EXISTS `base_drug`;
CREATE TABLE `base_drug`
(
    `drug_id`         int(11)        NOT NULL AUTO_INCREMENT COMMENT '药品id',
    `drug_name`       varchar(255)   NOT NULL COMMENT '药品通用名称',
    `trade_name`      varchar(255)   NOT NULL COMMENT '商品名',
    `pinyin_code`     varchar(64)    NOT NULL DEFAULT '''''''NULL''''''' COMMENT '拼音码',
    `specification`   varchar(255)   NOT NULL COMMENT '药品规格',
    `pack_unit`       char(4)        NOT NULL DEFAULT '''''' COMMENT '包装单位',
    `price`           decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '药品价格',
    `dose`            decimal(10, 2) NOT NULL COMMENT '剂量',
    `dose_unit`       varchar(4)     NOT NULL COMMENT '剂量单位',
    `factory_name`    varchar(255)            DEFAULT NULL COMMENT '产地',
    `approval_number` varchar(64)             DEFAULT NULL COMMENT '批准文号',
    `delete_mark`     boolean                 DEFAULT FALSE COMMENT '软删除标记',
    PRIMARY KEY (`drug_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8 COMMENT ='基础药品表';

-- ----------------------------
-- Records of base_drug
-- ----------------------------
BEGIN;
INSERT INTO `base_drug`
VALUES (1, '肠炎宁片', '[康恩贝]肠炎宁片', 'cynp', '0.42g*48片', '盒', 30.00, 0.42, 'g', '江西康恩贝中药有限公司', '国药准字Z36020518', FALSE);
INSERT INTO `base_drug`
VALUES (2, '连花清瘟胶囊', '[以岭]连花清瘟胶囊', 'lhqwjn', '0.35g*36粒', '盒', 29.10, 0.35, 'g', '石家庄以岭药业股份有限公司', '国药准字Z20040063',
        FALSE);
INSERT INTO `base_drug`
VALUES (3, '小柴胡颗粒', '[999]小柴胡颗粒', 'xchkl', '10g*9袋', '盒', 18.20, 10.00, 'g', '华润三九医药股份有限公司', '国药准字Z44020709', FALSE);
INSERT INTO `base_drug`
VALUES (4, '布洛芬缓释胶囊', '[芬必得]布洛芬缓释胶囊', 'blfysjn', '0.3g*20粒', '盒', 14.20, 0.30, 'g', '中美天津史克制药有限公司', '国药准字H20013062',
        FALSE);
INSERT INTO `base_drug`
VALUES (5, '清凉油(白色)', '[龙虎]清凉油(白色)', 'qly', '18.4g', '瓶', 23.00, 0.10, 'g', '上海中华药业有限公司', '国药准字Z20026718', FALSE);
INSERT INTO `base_drug`
VALUES (6, '曲安奈德益康唑乳膏', '[派瑞松]曲安奈德益康唑乳膏', 'qatdykzlg', '15g', '支', 21.00, 0.20, 'g', '西安杨森制药有限公司', '国药准字H20000454',
        FALSE);
INSERT INTO `base_drug`
VALUES (7, '卡托普利片', '[开博通]卡托普利片', 'ktplp', '12.5mg*20片', '盒', 22.00, 12.50, 'mg', '中美上海施贵宝制药有限公司', '国药准字H31022986',
        FALSE);
INSERT INTO `base_drug`
VALUES (8, '磷酸西格列汀片', '[捷诺维]磷酸西格列汀片', 'lsxgltp', '100mg*14片', '盒', 115.00, 100.00, 'mg', '杭州默沙东制药有限公司',
        '国药准字J20140095', FALSE);
INSERT INTO `base_drug`
VALUES (9, '盐酸二甲双胍片', '[格华止]盐酸二甲双胍片', 'ysejsgp', '0.85g*20片', '盒', 32.00, 0.85, 'g', '中美上海施贵宝制药有限公司', '国药准字H20023371',
        FALSE);
INSERT INTO `base_drug`
VALUES (10, '小儿氨酚烷胺颗粒', '[优卡丹]小儿氨酚烷胺颗粒', 'xeafwakl', '6g*16袋', '盒', 18.00, 8.00, 'g', '江西铜鼓仁和制药有限公司', '国药准字H20068170',
        FALSE);
INSERT INTO `base_drug`
VALUES (11, '小儿感冒口服液', '[同仁堂]小儿感冒口服液', 'xegmkfy', '10ml*10支', '盒', 19.80, 5.00, 'ml', '北京同仁堂科技发展股份有限公司制药厂',
        '国药准字Z10940020', FALSE);
INSERT INTO `base_drug`
VALUES (12, '炉甘石洗剂', '[信龙]炉甘石洗剂', 'lgsxj', '100ml', '瓶', 11.00, 10.00, 'ml', '上海运佳黄浦制药有限公司', '国药准字H31022790', FALSE);
INSERT INTO `base_drug`
VALUES (13, '红霉素眼膏', '[云植]红霉素眼膏', 'hmsyg', '2g', '支', 4.50, 0.01, 'g', '云南植物药业有限公司', '国药准字H53020376', FALSE);
INSERT INTO `base_drug`
VALUES (14, '呋塞米片', '[三才]呋塞米片', 'fsmp', '20mg*100片', '瓶', 8.70, 20.00, 'mg', '三才石岐制药股份有限公司', '国药准字H44023242', FALSE);
COMMIT;

-- ----------------------------
-- Table structure for photo
-- ----------------------------
DROP TABLE IF EXISTS `photo`;
CREATE TABLE `photo`
(
    `photo_id`  int(11)      NOT NULL AUTO_INCREMENT COMMENT '照片id',
    `photo_url` varchar(128) NOT NULL COMMENT '照片地址',
    PRIMARY KEY (`photo_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='照片表';

-- ----------------------------
-- Records of base_photo
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for consult_ask
-- ----------------------------
DROP TABLE IF EXISTS `consult_ask`;
CREATE TABLE `consult_ask`
(
    `consult_id`     int(11)       NOT NULL AUTO_INCREMENT COMMENT '问诊id',
    `creator_id`     int(11)       NOT NULL COMMENT '操作用户id',
    `doctor_id`      int(11)       NOT NULL COMMENT '医生id',
    `patient_id`     int(11)       NOT NULL COMMENT '配药人id',
    `diagnosis_ids`  varchar(128)  NOT NULL COMMENT '确认诊断id，用英文逗号分隔',
    `drug_ids`       varchar(128)  NOT NULL COMMENT '复诊提交药物id，用英文逗号分隔',
    `question`       varchar(1000) NOT NULL COMMENT '问题描述',
    `photo_ids`      varchar(128) DEFAULT NULL COMMENT '问诊照片id，用英文逗号分隔',
    `consult_status` tinyint(4)   DEFAULT NULL COMMENT '复诊配药状态，1待接诊，2进行中，3已完成',
    `create_time`    datetime     DEFAULT NULL COMMENT '创建时间',
    `accept_time`    datetime     DEFAULT NULL COMMENT '接诊时间',
    `finish_time`    datetime     DEFAULT NULL COMMENT '完成时间',
    PRIMARY KEY (`consult_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='问诊记录表';

-- ----------------------------
-- Records of consult_ask
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- View structure for consult_record_user
-- ----------------------------
DROP VIEW IF EXISTS `consult_record_user`;
CREATE VIEW `consult_record_user` AS
SELECT consult_id,
       creator_id,
       patient_id,
       consult_ask.doctor_id,
       doctor_name,
       avatar_url,
       dept_id,
       dept_name,
       question,
       consult_status,
       create_time,
       accept_time,
       finish_time
FROM consult_ask
         JOIN dept_doctor ON consult_ask.doctor_id = dept_doctor.doctor_id;

-- ----------------------------
-- View structure for consult_record_doctor
-- ----------------------------
DROP VIEW IF EXISTS `consult_record_doctor`;
CREATE VIEW `consult_record_doctor` AS
SELECT consult_id,
       doctor_id,
       consult_ask.patient_id,
       patient_name,
       patient_gender,
       patient_birth_age,
       drug_ids,
       consult_status,
       create_time,
       accept_time,
       finish_time
FROM consult_ask
         JOIN base_patient ON consult_ask.patient_id = base_patient.patient_id
WHERE delete_mark = FALSE;

-- ----------------------------
-- Table structure for prescription_info
-- ----------------------------
DROP TABLE IF EXISTS `prescription_info`;
CREATE TABLE `prescription_info`
(
    `prescription_id`       int(11)      NOT NULL AUTO_INCREMENT COMMENT '处方id',
    `org_id`                int(11)      NOT NULL COMMENT '机构id',
    `consult_id`            int(11)      NOT NULL COMMENT '问诊id',
    `prescription_type`     char(2)      NOT NULL COMMENT '处方类型，1西药，2中成药，3中草药',
    `doctor_id`             int(11)      NOT NULL COMMENT '开方医生id',
    `prescription_drug_ids` varchar(128) NOT NULL COMMENT '处方药品id，用英文逗号分隔',
    `create_time`           datetime     NOT NULL COMMENT '开方时间',
    `prescription_status`   char(2)      NOT NULL COMMENT '处方提交状态，0未提交 ，1已提交 ',
    PRIMARY KEY (`prescription_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='处方表';

-- ----------------------------
-- Records of prescription_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for prescription_drug
-- ----------------------------
DROP TABLE IF EXISTS `prescription_drug`;
CREATE TABLE `prescription_drug`
(
    `prescription_drug_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '处方药品标识',
    `drug_id`              int(11)        DEFAULT NULL COMMENT '药品id',
    `drug_frequency_id`    int(11)        DEFAULT NULL COMMENT '用药频次id',
    `drug_usage_id`        int(11)        DEFAULT NULL COMMENT '药品用法id',
    `take_days`            int(11)        DEFAULT NULL COMMENT '用药天数',
    `quantity`             decimal(10, 2) DEFAULT NULL COMMENT '药品数量',
    `group_number`         int(11)        DEFAULT NULL COMMENT '组号',
    `sort_number`          int(11)        DEFAULT NULL COMMENT '顺序号',
    `count`                int(6)         DEFAULT 1 COMMENT '数量',
    `remark`               varchar(100)   DEFAULT NULL COMMENT '嘱托',
    `creator_id`           int(11) NOT NULL COMMENT '操作用户id',
    PRIMARY KEY (`prescription_drug_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='处方药品表';

-- ----------------------------
-- Records of prescription_drug
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- View structure for prescription_drug_detail
-- ----------------------------
DROP VIEW IF EXISTS `prescription_drug_detail`;
CREATE VIEW `prescription_drug_detail` AS
SELECT prescription_drug_id,
       prescription_drug.drug_id,
       drug_name,
       specification,
       pack_unit,
       price,
       dose,
       dose_unit,
       drug_frequency_id,
       drug_usage_id,
       take_days,
       quantity,
       group_number,
       sort_number,
       remark,
       count
FROM prescription_drug
         JOIN base_drug ON prescription_drug.drug_id = base_drug.drug_id
where delete_mark = false;

-- ----------------------------
-- Procedure structure for AddGeometryColumn
-- ----------------------------
DROP PROCEDURE IF EXISTS `AddGeometryColumn`;
delimiter ;;
CREATE PROCEDURE `AddGeometryColumn`(catalog varchar(64), t_schema varchar(64),
                                     t_name varchar(64), geometry_column varchar(64), t_srid int)
begin
    set @qwe =
            concat('ALTER TABLE ', t_schema, '.', t_name, ' ADD ', geometry_column, ' GEOMETRY REF_SYSTEM_ID=', t_srid);
    PREPARE ls from @qwe; execute ls; deallocate prepare ls;
end
;;
delimiter ;

-- ----------------------------
-- Procedure structure for DropGeometryColumn
-- ----------------------------
DROP PROCEDURE IF EXISTS `DropGeometryColumn`;
delimiter ;;
CREATE PROCEDURE `DropGeometryColumn`(catalog varchar(64), t_schema varchar(64),
                                      t_name varchar(64), geometry_column varchar(64))
begin
    set @qwe = concat('ALTER TABLE ', t_schema, '.', t_name, ' DROP ', geometry_column); PREPARE ls from @qwe;
    execute ls; deallocate prepare ls;
end
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
