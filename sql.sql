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
-- Table structure for base_account
-- ----------------------------
DROP TABLE IF EXISTS `base_account`;
CREATE TABLE `base_account` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `nick_name` varchar(32) NOT NULL COMMENT '用户昵称',
  `avatar_url` varchar(168) NOT NULL COMMENT '用户头像',
  `user_type` char(2) NOT NULL COMMENT '用户类型，1居民，2医生',
  `mini_open_id` varchar(36) NOT NULL COMMENT '微信小程序openid',
  `phone_no` varchar(16) DEFAULT NULL COMMENT '手机号码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of base_account
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_diagnosis
-- ----------------------------
DROP TABLE IF EXISTS `base_diagnosis`;
CREATE TABLE `base_diagnosis` (
  `diagnosis_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '诊断标识',
  `diagnosis_type` char(2) NOT NULL COMMENT '诊断类型，1西医，2中医',
  `diseases_code` varchar(36) NOT NULL COMMENT '诊断类型为西医时为疾病代码，为中医时为中医疾病代码',
  `diseases_id` int(8) NOT NULL COMMENT '诊断类型为西医时为疾病标识，为中医时为中医疾病标识',
  `diseases_name` varchar(36) NOT NULL COMMENT '诊断类型为西医时为疾病名称，为中医时为中医疾病名称',
  `pinyin_code` varchar(36) NOT NULL COMMENT '拼音码',
  PRIMARY KEY (`diagnosis_id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COMMENT='基础诊断表';

-- ----------------------------
-- Records of base_diagnosis
-- ----------------------------
BEGIN;
INSERT INTO `base_diagnosis` VALUES (1, '1', 'J11.101', 10001, '流行性感冒', 'LXXGM');
INSERT INTO `base_diagnosis` VALUES (2, '1', 'J10.151', 10002, '流行性感冒性咽炎', 'LXXGMXYY');
INSERT INTO `base_diagnosis` VALUES (3, '1', 'J98.802', 10003, '呼吸道感染', 'HXDGR');
INSERT INTO `base_diagnosis` VALUES (4, '1', 'J06.903', 10004, '上呼吸道感染', 'SHXDGR');
INSERT INTO `base_diagnosis` VALUES (5, '1', 'E14.900', 10005, '糖尿病', 'TNB');
INSERT INTO `base_diagnosis` VALUES (6, '1', 'E13.904', 10006, '医源性糖尿病', 'YYXTNB');
INSERT INTO `base_diagnosis` VALUES (7, '1', 'I10.X02', 10007, '高血压', 'GXY');
INSERT INTO `base_diagnosis` VALUES (8, '1', 'H35.001', 10008, '高血压性视网膜病', 'GXYXSWMB');
INSERT INTO `base_diagnosis` VALUES (9, '1', 'A08.401', 10009, '病毒性肠炎', 'BDXCY');
INSERT INTO `base_diagnosis` VALUES (10, '1', 'A01.001', 10010, '伤寒', 'SH');
INSERT INTO `base_diagnosis` VALUES (11, '1', 'A01.052', 10011, '伤寒腹膜炎', 'SHFMY');
INSERT INTO `base_diagnosis` VALUES (12, '1', 'K27.503', 10012, '消化道穿孔', 'SHDCK');
INSERT INTO `base_diagnosis` VALUES (13, '1', 'K27.451', 10013, '消化性溃疡伴出血', 'XHDKYBCX');
INSERT INTO `base_diagnosis` VALUES (14, '1', 'K30', 10014, '消化不良', 'XHBL');
INSERT INTO `base_diagnosis` VALUES (15, '1', 'A16.305', 10015, '支气管淋巴结结核', 'ZQGLBJJH');
INSERT INTO `base_diagnosis` VALUES (16, '1', 'B37.805', 10016, '支气管念珠菌病', 'ZQGNZJB');
INSERT INTO `base_diagnosis` VALUES (17, '1', 'C34.905', 10017, '支气管恶性肿瘤', 'ZQGEXZL');
INSERT INTO `base_diagnosis` VALUES (18, '1', 'R22.951', 10018, '表浅(局限)肿块', 'QBJXZK');
INSERT INTO `base_diagnosis` VALUES (19, '1', 'R22.451', 10019, '腿部炎性肿块', 'TBYXZK');
INSERT INTO `base_diagnosis` VALUES (20, '1', 'A18.405', 10020, '结核性皮肤脓肿', 'JHXPFNZ');
INSERT INTO `base_diagnosis` VALUES (21, '1', 'A18.407', 10021, '皮肤和皮下组织结核', 'PFHPXZZJH');
INSERT INTO `base_diagnosis` VALUES (22, '1', 'A18.408', 10022, '皮肤结核', 'PFJH');
INSERT INTO `base_diagnosis` VALUES (23, '1', 'A36.301', 10023, '皮肤白喉', 'PFBH');
INSERT INTO `base_diagnosis` VALUES (24, '1', 'E51.101', 10024, '脚气病', 'JQB');
INSERT INTO `base_diagnosis` VALUES (25, '1', 'E51.152', 10025, '干性脚气病', 'GXJQB');
INSERT INTO `base_diagnosis` VALUES (26, '1', 'S90.351', 10026, '脚挫伤', 'JCS');
INSERT INTO `base_diagnosis` VALUES (27, '1', 'M54.475', 10027, '腰腿痛', 'YTT');
INSERT INTO `base_diagnosis` VALUES (28, '1', 'L03.104', 10028, '腿蜂窝织炎', 'TFWZY');
INSERT INTO `base_diagnosis` VALUES (29, '1', 'Q68.551', 10029, '腿弓形', 'TGX');
INSERT INTO `base_diagnosis` VALUES (30, '1', 'R22.451', 10030, '腿部炎性肿块', 'TBYXZK');
INSERT INTO `base_diagnosis` VALUES (31, '1', 'R60.051', 10031, '遗传性腿部水肿', 'YCXTBSZ');
INSERT INTO `base_diagnosis` VALUES (32, '1', 'S70.901', 10032, '大腿血肿', 'DTXZ');
INSERT INTO `base_diagnosis` VALUES (33, '1', 'S71.101', 10033, '大腿撕脱伤', 'DTSTS');
INSERT INTO `base_diagnosis` VALUES (34, '1', 'S71.102', 10034, '大腿开放性伤口', 'DTKFXSK');
INSERT INTO `base_diagnosis` VALUES (35, '1', 'C76.502', 10035, '小腿恶性肿瘤', 'XTEXZL');
INSERT INTO `base_diagnosis` VALUES (36, '1', 'B08.401', 10036, '手足口病', 'SZKB');
INSERT INTO `base_diagnosis` VALUES (37, '1', 'B35.201', 10037, '手癣', 'SX');
INSERT INTO `base_diagnosis` VALUES (38, '1', 'B35.251', 10038, '手皮真菌病', 'SPZJB');
INSERT INTO `base_diagnosis` VALUES (39, '1', 'B35.851', 10039, '手足癣', 'SZX');
INSERT INTO `base_diagnosis` VALUES (40, '1', 'E89.002', 10040, '手术后甲状腺机能减退', 'SSHJZXJNJT');
INSERT INTO `base_diagnosis` VALUES (41, '1', 'E89.101', 10041, '手术后低胰岛素血症', 'SSHDYDSXZ');
INSERT INTO `base_diagnosis` VALUES (42, '1', 'E89.301', 10042, '手术后垂体机能减退', 'SSHCTJNJT');
INSERT INTO `base_diagnosis` VALUES (43, '1', 'B15.902', 10043, '传染性肝炎', 'CRXGY');
INSERT INTO `base_diagnosis` VALUES (44, '1', 'B15.901', 10044, '甲型病毒性肝炎', 'JXBDXGY');
INSERT INTO `base_diagnosis` VALUES (45, '1', 'B15.907', 10045, '甲型病毒性重型肝炎', 'JXBDXZXGY');
INSERT INTO `base_diagnosis` VALUES (46, '1', 'A02.006', 10046, '沙门氏菌胃肠炎', 'SMSJWCY');
INSERT INTO `base_diagnosis` VALUES (47, '1', 'A02.004', 10047, '沙门氏菌性肠炎', 'SMSJXCY');
INSERT INTO `base_diagnosis` VALUES (48, '1', 'A01.057', 10048, '伤寒样小肠炎', 'SHXXCY');
INSERT INTO `base_diagnosis` VALUES (49, '1', 'A01.056', 10049, '肠伤寒', 'CSH');
INSERT INTO `base_diagnosis` VALUES (50, '1', 'A05.952', 10050, '胃肠型食物中毒', 'WCXSWZD');
INSERT INTO `base_diagnosis` VALUES (51, '1', 'A01.055', 10051, '伤寒性肠穿孔', 'SHXCCK');
COMMIT;

-- ----------------------------
-- Table structure for base_dic_drug_frequency
-- ----------------------------
DROP TABLE IF EXISTS `base_dic_drug_frequency`;
CREATE TABLE `base_dic_drug_frequency` (
  `item_code` int(11) NOT NULL COMMENT '项目代码',
  `item_name` varchar(32) NOT NULL COMMENT '项目名称',
  `item_name_abbr` varchar(32) NOT NULL COMMENT '项目名称缩写',
  PRIMARY KEY (`item_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='药品频次表';

-- ----------------------------
-- Records of base_dic_drug_frequency
-- ----------------------------
BEGIN;
INSERT INTO `base_dic_drug_frequency` VALUES (1, '每日一次', 'qd');
INSERT INTO `base_dic_drug_frequency` VALUES (2, '每日两次', 'bid');
INSERT INTO `base_dic_drug_frequency` VALUES (3, '每日三次', 'tid');
INSERT INTO `base_dic_drug_frequency` VALUES (4, '每日四次', 'qid');
INSERT INTO `base_dic_drug_frequency` VALUES (5, '隔日一次', 'qod');
INSERT INTO `base_dic_drug_frequency` VALUES (6, '每周一次', 'qw');
COMMIT;

-- ----------------------------
-- Table structure for base_dic_drug_usage
-- ----------------------------
DROP TABLE IF EXISTS `base_dic_drug_usage`;
CREATE TABLE `base_dic_drug_usage` (
  `item_code` int(11) NOT NULL COMMENT '项目代码',
  `item_name` varchar(32) NOT NULL COMMENT '项目名称',
  PRIMARY KEY (`item_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='药品用法表';

-- ----------------------------
-- Records of base_dic_drug_usage
-- ----------------------------
BEGIN;
INSERT INTO `base_dic_drug_usage` VALUES (1, '口服');
INSERT INTO `base_dic_drug_usage` VALUES (2, '涂抹');
INSERT INTO `base_dic_drug_usage` VALUES (3, '静脉注射');
INSERT INTO `base_dic_drug_usage` VALUES (4, '皮下注射');
COMMIT;

-- ----------------------------
-- Table structure for base_doctor
-- ----------------------------
DROP TABLE IF EXISTS `base_doctor`;
CREATE TABLE `base_doctor` (
  `doctor_id` varchar(36) NOT NULL COMMENT '医生id',
  `doctor_name` varchar(64) NOT NULL COMMENT '医生姓名',
  `org_id` varchar(36) NOT NULL COMMENT '机构id',
  `org_name` varchar(64) NOT NULL COMMENT '机构名称',
  `dept_id` varchar(36) NOT NULL COMMENT '科室id',
  `dept_name` varchar(64) NOT NULL COMMENT '科室名称',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '医生头像链接',
  `level_code` char(2) DEFAULT NULL COMMENT '医生职称，1主任医师，2副主任医师，3主治医师，4医师，5医士',
  `level_name` varchar(32) DEFAULT NULL COMMENT '医生职称，1主任医师，2副主任医师，3主治医师，4医师，5医士',
  PRIMARY KEY (`doctor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医生表';

-- ----------------------------
-- Records of base_doctor
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_drug
-- ----------------------------
DROP TABLE IF EXISTS `base_drug`;
CREATE TABLE `base_drug` (
  `drug_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '药品id',
  `drug_name` varchar(255) NOT NULL COMMENT '药品通用名称',
  `trade_name` varchar(255) NOT NULL COMMENT '商品名',
  `pinyin_code` varchar(64) NOT NULL DEFAULT '''''''NULL''''''' COMMENT '拼音码',
  `specification` varchar(255) NOT NULL COMMENT '药品规格',
  `pack_unit` char(4) NOT NULL DEFAULT '''''' COMMENT '包装单位',
  `price` decimal(10,2) NOT NULL DEFAULT 0.00 COMMENT '药品价格',
  `dose` decimal(10,2) NOT NULL COMMENT '剂量',
  `dose_unit` varchar(4) NOT NULL COMMENT '剂量单位',
  `factory_name` varchar(255) DEFAULT NULL COMMENT '产地',
  `approval_number` varchar(64) DEFAULT NULL COMMENT '批准文号',
  PRIMARY KEY (`drug_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='基础药品表';

-- ----------------------------
-- Records of base_drug
-- ----------------------------
BEGIN;
INSERT INTO `base_drug` VALUES (1, '肠炎宁片', '[康恩贝]肠炎宁片', 'cynp', '0.42g*48片', '盒', 30.00, 0.42, 'g', '江西康恩贝中药有限公司', '国药准字Z36020518');
INSERT INTO `base_drug` VALUES (2, '连花清瘟胶囊', '[以岭]连花清瘟胶囊', 'lhqwjn', '0.35g*36粒', '盒', 29.10, 0.35, 'g', '石家庄以岭药业股份有限公司', '国药准字Z20040063');
INSERT INTO `base_drug` VALUES (3, '小柴胡颗粒', '[999]小柴胡颗粒', 'xchkl', '10g*9袋', '盒', 18.20, 10.00, 'g', '华润三九医药股份有限公司', '国药准字Z44020709');
INSERT INTO `base_drug` VALUES (4, '布洛芬缓释胶囊', '[芬必得]布洛芬缓释胶囊', 'blfysjn', '0.3g*20粒', '盒', 14.20, 0.30, 'g', '中美天津史克制药有限公司', '国药准字H20013062');
INSERT INTO `base_drug` VALUES (5, '清凉油(白色)', '[龙虎]清凉油(白色)', 'qly', '18.4g', '瓶', 23.00, 0.10, 'g', '上海中华药业有限公司', '国药准字Z20026718');
INSERT INTO `base_drug` VALUES (6, '曲安奈德益康唑乳膏', '[派瑞松]曲安奈德益康唑乳膏', 'qatdykzlg', '15g', '支', 21.00, 0.20, 'g', '西安杨森制药有限公司', '国药准字H20000454');
INSERT INTO `base_drug` VALUES (7, '卡托普利片', '[开博通]卡托普利片', 'ktplp', '12.5mg*20片', '盒', 22.00, 12.50, 'mg', '中美上海施贵宝制药有限公司', '国药准字H31022986');
INSERT INTO `base_drug` VALUES (8, '磷酸西格列汀片', '[捷诺维]磷酸西格列汀片', 'lsxgltp', '100mg*14片', '盒', 115.00, 100.00, 'mg', '杭州默沙东制药有限公司', '国药准字J20140095');
INSERT INTO `base_drug` VALUES (9, '盐酸二甲双胍片', '[格华止]盐酸二甲双胍片', 'ysejsgp', '0.85g*20片', '盒', 32.00, 0.85, 'g', '中美上海施贵宝制药有限公司', '国药准字H20023371');
INSERT INTO `base_drug` VALUES (10, '小儿氨酚烷胺颗粒', '[优卡丹]小儿氨酚烷胺颗粒', 'xeafwakl', '6g*16袋', '盒', 18.00, 8.00, 'g', '江西铜鼓仁和制药有限公司', '国药准字H20068170');
INSERT INTO `base_drug` VALUES (11, '小儿感冒口服液', '[同仁堂]小儿感冒口服液', 'xegmkfy', '10ml*10支', '盒', 19.80, 5.00, 'ml', '北京同仁堂科技发展股份有限公司制药厂', '国药准字Z10940020');
INSERT INTO `base_drug` VALUES (12, '炉甘石洗剂', '[信龙]炉甘石洗剂', 'lgsxj', '100ml', '瓶', 11.00, 10.00, 'ml', '上海运佳黄浦制药有限公司', '国药准字H31022790');
INSERT INTO `base_drug` VALUES (13, '红霉素眼膏', '[云植]红霉素眼膏', 'hmsyg', '2g', '支', 4.50, 0.01, 'g', '云南植物药业有限公司', '国药准字H53020376');
INSERT INTO `base_drug` VALUES (14, '呋塞米片', '[三才]呋塞米片', 'fsmp', '20mg*100片', '瓶', 8.70, 20.00, 'mg', '三才石岐制药股份有限公司', '国药准字H44023242');
COMMIT;

-- ----------------------------
-- Table structure for consult_ask
-- ----------------------------
DROP TABLE IF EXISTS `consult_ask`;
CREATE TABLE `consult_ask` (
  `consult_id` varchar(36) NOT NULL DEFAULT '' COMMENT '问诊id',
  `org_id` varchar(36) NOT NULL COMMENT '机构id',
  `org_name` varchar(64) NOT NULL COMMENT '机构名称',
  `dept_id` varchar(36) NOT NULL COMMENT '科室id',
  `dept_name` varchar(64) NOT NULL COMMENT '科室名称',
  `doctor_id` varchar(36) NOT NULL COMMENT '医生id',
  `doctor_name` varchar(64) NOT NULL COMMENT '医生姓名',
  `doctor_level_code` char(2) DEFAULT NULL COMMENT '医生职称代码',
  `doctor_level_name` varchar(32) DEFAULT NULL COMMENT '医生职称',
  `create_user_id` varchar(36) NOT NULL COMMENT '操作用户id',
  `person_name` varchar(64) NOT NULL COMMENT '配药人姓名',
  `person_card_type` char(4) NOT NULL COMMENT '配药人证件类型，01居民身份证 02居民户口簿 03护照 04军官证 05驾驶证 06港澳居民来往内地通行证 07台湾居民来往内地通行证 11出生证明 12港澳居民身份证 13港澳居民居住证 99其他法定有效证件',
  `person_card_id` varchar(64) NOT NULL COMMENT '配药人证件号码',
  `person_gender_code` char(2) NOT NULL COMMENT '配药人性别，1男，2女',
  `person_gender_name` varchar(16) NOT NULL COMMENT '配药人性别，1男，2女',
  `person_birth_date` datetime NOT NULL COMMENT '配药人出生日期',
  `person_age` tinyint(4) NOT NULL COMMENT '配药人年龄',
  `person_phone_no` varchar(16) NOT NULL COMMENT '配药人手机号码',
  `question` varchar(1000) NOT NULL COMMENT '问题描述',
  `diagnosis` varchar(1000) NOT NULL COMMENT '诊断小结',
  `drug_ids` varchar(255) NOT NULL COMMENT '复诊提交药物id，用英文逗号分隔',
  `drug_names` varchar(100) NOT NULL COMMENT '复诊配药提交药物名称，用英文逗号分隔',
  `photo_ids` varchar(512) DEFAULT NULL COMMENT '问诊照片id，用英文逗号分隔',
  `consult_status` tinyint(4) DEFAULT NULL COMMENT '复诊配药状态，1待接诊，2进行中，3已完成',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `accept_time` datetime DEFAULT NULL COMMENT '接诊时间',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  PRIMARY KEY (`consult_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='问诊记录表';

-- ----------------------------
-- Records of consult_ask
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for prescription_drug
-- ----------------------------
DROP TABLE IF EXISTS `prescription_drug`;
CREATE TABLE `prescription_drug` (
  `prescription_drug_id` varchar(36) NOT NULL COMMENT '处方药品标识',
  `org_id` varchar(36) NOT NULL COMMENT '机构id',
  `prescription_id` varchar(36) NOT NULL COMMENT '处方id',
  `drug_id` int(11) DEFAULT NULL COMMENT '药品id',
  `drug_name` varchar(64) DEFAULT NULL COMMENT '药品名称',
  `specification` varchar(100) DEFAULT NULL COMMENT '药品规格',
  `dose` decimal(10,2) DEFAULT NULL COMMENT '一次剂量',
  `dose_unit` varchar(4) DEFAULT NULL COMMENT '剂量单位',
  `frequency_code` int(11) DEFAULT NULL COMMENT '用药频次代码',
  `frequency_name` varchar(32) DEFAULT NULL COMMENT '用药频次',
  `usage_code` int(11) DEFAULT NULL COMMENT '药品用法代码',
  `usage_name` varchar(32) DEFAULT NULL COMMENT '药品用法',
  `take_days` int(11) DEFAULT NULL COMMENT '用药天数',
  `quantity` decimal(10,2) DEFAULT NULL COMMENT '药品数量',
  `price` decimal(10,2) DEFAULT NULL COMMENT '单价',
  `pack_unit` varchar(20) DEFAULT NULL COMMENT '包装单位',
  `group_number` int(11) DEFAULT NULL COMMENT '组号',
  `sort_number` int(11) DEFAULT NULL COMMENT '顺序号',
  `remark` varchar(100) DEFAULT NULL COMMENT '嘱托',
  PRIMARY KEY (`prescription_drug_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='处方药品表';

-- ----------------------------
-- Records of prescription_drug
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for prescription_info
-- ----------------------------
DROP TABLE IF EXISTS `prescription_info`;
CREATE TABLE `prescription_info` (
  `prescription_id` varchar(36) NOT NULL COMMENT '处方id',
  `org_id` varchar(36) NOT NULL COMMENT '机构id',
  `user_id` varchar(36) NOT NULL COMMENT '患者id',
  `consult_id` varchar(36) NOT NULL COMMENT '问诊id',
  `prescription_type` char(2) NOT NULL COMMENT '处方类型，1西药，2中成药，3中草药',
  `doctor_id` varchar(36) NOT NULL COMMENT '开方医生id',
  `doctor_name` char(10) NOT NULL COMMENT '开方医生姓名',
  `create_time` datetime NOT NULL COMMENT '开方时间',
  `prescription_status` char(2) NOT NULL COMMENT '处方提交状态，0未提交 ，1已提交 ',
  PRIMARY KEY (`prescription_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='处方表';

-- ----------------------------
-- Records of prescription_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Procedure structure for AddGeometryColumn
-- ----------------------------
DROP PROCEDURE IF EXISTS `AddGeometryColumn`;
delimiter ;;
CREATE PROCEDURE `AddGeometryColumn`(catalog varchar(64), t_schema varchar(64),
   t_name varchar(64), geometry_column varchar(64), t_srid int)
begin
  set @qwe= concat('ALTER TABLE ', t_schema, '.', t_name, ' ADD ', geometry_column,' GEOMETRY REF_SYSTEM_ID=', t_srid); PREPARE ls from @qwe; execute ls; deallocate prepare ls; end
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
  set @qwe= concat('ALTER TABLE ', t_schema, '.', t_name, ' DROP ', geometry_column); PREPARE ls from @qwe; execute ls; deallocate prepare ls; end
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
