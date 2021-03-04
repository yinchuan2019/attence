/*
 Navicat Premium Data Transfer

 Source Server         : 112.126.60.148
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : 112.126.60.148:3306
 Source Schema         : attence

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 01/03/2021 22:17:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for att_appointment
-- ----------------------------
DROP TABLE IF EXISTS `att_appointment`;
CREATE TABLE `att_appointment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stu_no` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `stu_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tea_no` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tea_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `begin_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `class_room` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `class_type` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `att_type` tinyint(1) DEFAULT NULL,
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of att_appointment
-- ----------------------------
BEGIN;
INSERT INTO `att_appointment` VALUES (1, 'S1', '錢二', 'T1', '3', '2021-01-26 13:00:00', '2021-01-26 13:15:00', '4', 'VIP作品集研究计划', 1, NULL, NULL, '2021-01-24 19:38:38', NULL);
INSERT INTO `att_appointment` VALUES (2, 'S1', '錢二', NULL, NULL, '2021-01-28 15:15:00', '2021-01-28 16:15:00', '4', '课题制作', 2, NULL, NULL, '2021-01-24 19:39:31', NULL);
INSERT INTO `att_appointment` VALUES (3, 'S1', '錢二', NULL, NULL, '2021-01-28 10:30:00', '2021-01-28 19:30:00', '3', '课题制作', 2, NULL, NULL, '2021-01-24 19:48:01', NULL);
INSERT INTO `att_appointment` VALUES (4, 'S1', '錢二', 'T1', '3', '2021-01-29 14:30:00', '2021-01-29 11:45:00', '4', 'VIP作品集研究计划', 1, NULL, NULL, '2021-01-24 20:02:10', NULL);
INSERT INTO `att_appointment` VALUES (5, 'S1', '錢二', NULL, NULL, '2021-02-17 09:15:00', '2021-02-17 09:30:00', '6', '课题制作', 1, NULL, NULL, '2021-02-08 09:51:37', NULL);
INSERT INTO `att_appointment` VALUES (6, 'S1', '錢二', 'T1', '3', '2021-02-09 13:15:00', '2021-02-09 13:15:00', '5', 'VIP作品集研究计划', 1, NULL, NULL, '2021-02-08 09:54:11', NULL);
INSERT INTO `att_appointment` VALUES (7, 'S1', '錢二', NULL, NULL, '2021-02-10 09:15:00', '2021-02-10 09:15:00', '5', '课题制作', 2, NULL, NULL, '2021-02-08 11:05:56', NULL);
INSERT INTO `att_appointment` VALUES (8, 'S1', '錢二', 'T1', '3', '2021-02-17 13:00:00', '2021-02-17 13:00:00', '6', 'VIP作品集研究计划', 1, NULL, NULL, '2021-02-08 11:33:24', NULL);
INSERT INTO `att_appointment` VALUES (9, 'S1', '錢二', 'T1', '3', '2021-02-17 09:00:00', '2021-02-17 18:15:00', '1', 'VIP作品集研究计划', 1, NULL, NULL, '2021-02-08 11:54:57', NULL);
INSERT INTO `att_appointment` VALUES (10, 'S1', '錢二', 'T1', '3', '2021-02-10 14:15:00', '2021-02-10 14:30:00', '4', 'VIP作品集研究计划', 1, NULL, NULL, '2021-02-08 13:57:49', NULL);
INSERT INTO `att_appointment` VALUES (11, 'S1', '錢二', NULL, NULL, '2021-02-25 13:00:00', '2021-02-25 17:45:00', '4', '课题制作', 2, NULL, NULL, '2021-02-24 23:59:19', NULL);
INSERT INTO `att_appointment` VALUES (12, 'S1', '錢二', NULL, NULL, '2021-03-01 10:30:00', '2021-03-01 15:45:00', '6', '课题制作', 2, NULL, NULL, '2021-02-28 23:59:37', NULL);
INSERT INTO `att_appointment` VALUES (13, 'S1', '錢二', NULL, NULL, '2021-03-02 09:30:00', '2021-03-02 10:30:00', '5', '课题制作', 2, NULL, NULL, '2021-03-01 01:31:58', NULL);
INSERT INTO `att_appointment` VALUES (17, 'S1', '錢二', NULL, NULL, '2021-03-06 13:00:00', '2021-03-06 16:00:00', '20', '一对多课程预约', 2, NULL, NULL, '2021-03-01 11:11:38', NULL);
INSERT INTO `att_appointment` VALUES (18, 'S1', '錢二', NULL, NULL, '2021-03-14 13:00:00', '2021-03-14 16:00:00', '21', '一对多课程预约', 2, NULL, NULL, '2021-03-01 11:11:39', NULL);
INSERT INTO `att_appointment` VALUES (19, 'S1', '錢二', NULL, NULL, '2021-03-21 13:00:00', '2021-03-21 16:00:00', '21', '一对多课程预约', 2, NULL, NULL, '2021-03-01 11:11:39', NULL);
INSERT INTO `att_appointment` VALUES (20, 'S1', '錢二', NULL, NULL, '2021-03-07 13:00:00', '2021-03-07 16:00:00', '21', '一对多课程预约', 2, NULL, NULL, '2021-03-01 11:13:54', NULL);
INSERT INTO `att_appointment` VALUES (21, 'S1', '錢二', NULL, NULL, '2021-03-28 13:00:00', '2021-03-28 16:00:00', '21', '一对多课程预约', 2, NULL, NULL, '2021-03-01 11:20:44', NULL);
INSERT INTO `att_appointment` VALUES (22, 'S1', '錢二', NULL, NULL, '2021-03-28 17:00:00', '2021-03-28 20:00:00', '21', '一对多课程预约', 2, NULL, NULL, '2021-03-01 11:20:45', NULL);
INSERT INTO `att_appointment` VALUES (23, 'S1', '錢二', NULL, NULL, '2021-03-21 17:00:00', '2021-03-21 20:00:00', '21', '一对多课程预约', 2, NULL, NULL, '2021-03-01 11:22:50', NULL);
INSERT INTO `att_appointment` VALUES (24, 'S1', '錢二', NULL, NULL, '2021-03-14 17:00:00', '2021-03-14 20:00:00', '21', '一对多课程预约', 2, NULL, NULL, '2021-03-01 11:23:52', NULL);
INSERT INTO `att_appointment` VALUES (25, 'S1', '錢二', 'T1', '3', '2021-03-03 09:15:00', '2021-03-03 13:45:00', '4', 'VIP作品集研究计划', 1, NULL, NULL, '2021-03-01 11:29:18', NULL);
INSERT INTO `att_appointment` VALUES (26, 'S1', '錢二', NULL, NULL, '2021-03-10 12:00:00', '2021-03-10 14:15:00', '5', '课题制作', 2, NULL, NULL, '2021-03-01 11:29:57', NULL);
INSERT INTO `att_appointment` VALUES (27, 'S1', '錢二', 'T1', '3', '2021-03-09 13:15:00', '2021-03-09 13:15:00', '4', 'VIP作品集研究计划', 1, NULL, NULL, '2021-03-01 11:41:48', NULL);
INSERT INTO `att_appointment` VALUES (28, 'S1', '錢二', NULL, NULL, '2021-03-03 09:15:00', '2021-03-03 09:15:00', '4', '课题制作', 2, NULL, NULL, '2021-03-01 19:44:48', NULL);
INSERT INTO `att_appointment` VALUES (29, 'S1', '錢二', NULL, NULL, '2021-03-02 09:00:00', '2021-03-02 09:45:00', '4', '课题制作', 2, NULL, NULL, '2021-03-01 19:59:34', NULL);
INSERT INTO `att_appointment` VALUES (30, 'S1', '錢二', 'T1', '3', '2021-03-02 09:15:00', '2021-03-02 10:15:00', '2', 'VIP作品集研究计划', 1, NULL, NULL, '2021-03-01 20:02:05', NULL);
INSERT INTO `att_appointment` VALUES (31, 'S1', '錢二', 'T1', '3', '2021-03-11 13:15:00', '2021-03-11 09:15:00', '3', 'VIP作品集研究计划', 1, NULL, NULL, '2021-03-01 20:10:18', NULL);
INSERT INTO `att_appointment` VALUES (32, 'S1', '錢二', 'T1', '3', '2021-03-04 10:45:00', '2021-03-04 09:30:00', '3', 'VIP作品集研究计划', 1, NULL, NULL, '2021-03-01 20:14:33', NULL);
INSERT INTO `att_appointment` VALUES (33, 'S1', '錢二', NULL, NULL, '2021-03-10 09:15:00', '2021-03-10 13:15:00', '2', '课题制作', 2, NULL, NULL, '2021-03-01 20:30:54', NULL);
INSERT INTO `att_appointment` VALUES (34, 'S1', '錢二', 'T1', '3', '2021-03-02 09:30:00', '2021-03-02 14:30:00', '1', 'VIP作品集研究计划', 1, NULL, NULL, '2021-03-01 21:46:37', NULL);
INSERT INTO `att_appointment` VALUES (35, 'S1', '錢二', NULL, NULL, '2021-03-02 09:30:00', '2021-03-02 10:30:00', '3', '课题制作', 2, NULL, NULL, '2021-03-01 21:53:04', NULL);
INSERT INTO `att_appointment` VALUES (36, 'S1', '錢二', NULL, NULL, '2021-03-02 09:15:00', '2021-03-02 09:45:00', '3', '课题制作', 2, NULL, NULL, '2021-03-01 22:35:48', NULL);
COMMIT;

-- ----------------------------
-- Table structure for att_record
-- ----------------------------
DROP TABLE IF EXISTS `att_record`;
CREATE TABLE `att_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tea_no` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tea_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `stu_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `stu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `begin_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `work_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `att_type` tinyint(1) DEFAULT NULL,
  `salary` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of att_record
-- ----------------------------
BEGIN;
INSERT INTO `att_record` VALUES (11, 'T1', '3', 'S1', '錢二', '2021-02-08 09:54:36', '2021-02-08 10:08:47', 'VIP作品集研究计划', 1, '22', NULL, NULL, '2021-02-08 09:54:36', '2021-02-10 03:35:54');
INSERT INTO `att_record` VALUES (12, 'T1', '3', '', '', '2021-02-08 11:24:19', '2021-02-08 11:35:18', '事务工作', 2, '4', NULL, NULL, '2021-02-08 11:24:20', '2021-02-10 06:30:27');
INSERT INTO `att_record` VALUES (13, 'T1', '3', NULL, NULL, '2021-02-06 11:25:44', '2021-02-08 11:31:55', '事务工作', 1, '4', NULL, NULL, '2021-02-08 11:25:44', '2021-02-10 03:19:33');
INSERT INTO `att_record` VALUES (14, 'T1', '3', 'S1', '錢二', '2021-02-08 11:31:46', '2021-02-08 11:31:53', 'VIP作品集研究计划', 1, '4', NULL, NULL, '2021-02-08 11:31:47', '2021-02-10 03:35:53');
INSERT INTO `att_record` VALUES (15, 'T1', '3', NULL, NULL, '2021-02-08 11:32:37', '2021-02-08 11:38:49', '事务工作', 1, '4', NULL, NULL, '2021-02-08 11:32:37', '2021-02-10 03:19:34');
INSERT INTO `att_record` VALUES (16, 'T1', '3', '', '', '2021-02-08 13:56:01', '2021-02-08 13:59:07', '其他', 1, '22', NULL, NULL, '2021-02-08 13:56:01', '2021-02-10 06:30:26');
INSERT INTO `att_record` VALUES (18, 'T1', '3', '', '', '2021-02-08 14:19:32', '2021-02-08 14:33:47', '其他', 1, '22', NULL, NULL, '2021-02-08 14:19:32', '2021-02-10 03:19:28');
INSERT INTO `att_record` VALUES (19, 'T1', '3', 'S1', '錢二', '2021-02-10 10:02:12', '2021-02-10 10:07:14', 'VIP作品集研究计划', 1, '22', NULL, NULL, '2021-02-10 10:02:12', '2021-02-10 03:35:52');
INSERT INTO `att_record` VALUES (20, 'T1', '3', NULL, NULL, '2021-02-10 10:09:06', '2021-02-10 10:09:28', '事务工作', 1, '4', NULL, NULL, '2021-02-10 10:09:07', NULL);
INSERT INTO `att_record` VALUES (27, 'T1', '3', NULL, NULL, '2021-02-09 00:00:00', '2021-02-26 00:00:00', '一对多课程预约', 1, '2', NULL, NULL, '2021-02-10 12:03:19', '2021-02-10 04:03:48');
INSERT INTO `att_record` VALUES (30, 'T1', '3', 'S1', '錢二', '2021-02-10 14:12:50', '2021-03-01 20:08:09', 'VIP作品集研究计划', 1, '3', NULL, NULL, '2021-02-10 14:12:50', '2021-02-10 06:29:57');
INSERT INTO `att_record` VALUES (32, 'T1', '3', NULL, NULL, '2021-02-08 00:00:00', '2021-02-19 00:00:00', '事务工作', 2, '44', NULL, NULL, '2021-02-10 14:22:13', '2021-02-10 06:30:09');
INSERT INTO `att_record` VALUES (33, 'T1', '3', NULL, NULL, '2021-02-14 20:24:43', '2021-02-14 20:29:24', '其他', 1, '1000', 'shiwu', NULL, '2021-02-14 20:24:43', NULL);
INSERT INTO `att_record` VALUES (34, 'T1', '3', NULL, NULL, '2021-03-01 11:41:13', '2021-03-01 11:41:23', '其他', 1, '1', '11', NULL, '2021-03-01 11:41:14', NULL);
INSERT INTO `att_record` VALUES (35, 'T1', '3', 'S1', '錢二', '2021-03-01 20:01:19', '2021-03-01 20:01:29', 'VIP作品集研究计划', 1, '3', NULL, NULL, '2021-03-01 20:01:19', NULL);
INSERT INTO `att_record` VALUES (36, 'T1', '3', 'S1', '錢二', '2021-03-01 20:09:29', '2021-03-01 20:09:38', 'VIP作品集研究计划', 2, '3', NULL, NULL, '2021-03-01 20:09:29', NULL);
INSERT INTO `att_record` VALUES (37, 'T1', '3', 'S1', '錢二', '2021-03-01 20:12:28', '2021-03-01 20:12:35', 'VIP作品集研究计划', 1, '3', NULL, NULL, '2021-03-01 20:12:28', NULL);
INSERT INTO `att_record` VALUES (38, 'T1', '3', NULL, NULL, '2021-03-01 20:16:09', '2021-03-01 21:44:20', '其他', 1, '1000', 'shiwu', NULL, '2021-03-01 20:16:09', NULL);
INSERT INTO `att_record` VALUES (39, 'T1', '3', 'S1', '錢二', '2021-03-01 20:19:59', '2021-03-01 21:44:04', 'VIP作品集研究计划', 1, '3', NULL, NULL, '2021-03-01 20:19:59', NULL);
INSERT INTO `att_record` VALUES (40, 'T1', '3', NULL, NULL, '2021-03-01 20:33:44', '2021-03-01 20:33:51', '事务工作', 1, '4', NULL, NULL, '2021-03-01 20:33:44', NULL);
INSERT INTO `att_record` VALUES (41, 'T1', '3', '', NULL, '2021-03-01 20:40:50', '2021-03-01 21:43:57', 'VIP作品集研究计划', 1, '3', NULL, NULL, '2021-03-01 20:40:50', NULL);
INSERT INTO `att_record` VALUES (42, 'T1', '3', 'S1', '錢二', '2021-03-01 21:36:27', '2021-03-01 21:39:32', 'VIP作品集研究计划', 1, '3', NULL, NULL, '2021-03-01 21:36:27', NULL);
INSERT INTO `att_record` VALUES (43, 'T1', '3', NULL, NULL, '2021-03-01 21:36:40', '2021-03-01 21:39:39', '事务工作', 2, '4', NULL, NULL, '2021-03-01 21:36:40', NULL);
INSERT INTO `att_record` VALUES (44, 'T1', '3', NULL, NULL, '2021-03-01 21:37:03', '2021-03-01 21:39:45', '其他', 2, '1000', 'qqqq', NULL, '2021-03-01 21:37:03', NULL);
INSERT INTO `att_record` VALUES (45, 'T1', '3', 'S1', '錢二', '2021-03-01 21:40:32', '2021-03-01 21:42:59', 'VIP作品集研究计划', 1, '3', NULL, NULL, '2021-03-01 21:40:32', NULL);
INSERT INTO `att_record` VALUES (46, 'T1', '3', NULL, NULL, '2021-03-01 21:40:46', '2021-03-01 21:43:15', '事务工作', 1, '4', NULL, NULL, '2021-03-01 21:40:46', NULL);
INSERT INTO `att_record` VALUES (47, 'T1', '3', NULL, NULL, '2021-03-01 21:41:15', '2021-03-01 21:43:25', '其他', 1, '1000', '事务工作', NULL, '2021-03-01 21:41:15', NULL);
COMMIT;

-- ----------------------------
-- Table structure for att_stu_tea_relation
-- ----------------------------
DROP TABLE IF EXISTS `att_stu_tea_relation`;
CREATE TABLE `att_stu_tea_relation` (
  `stu_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '登録ID/登陆ID',
  `stu_nm_kanji` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学生名（漢字）/学生姓名（汉字）',
  `stu_nm_roma` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学生名（ローマ字）/学生姓名（罗马字）',
  `tea_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '登録ID/登陆ID',
  `tea_nm_kanji` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '教师名（漢字）/老师姓名（汉字）',
  `tea_nm_roma` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '教师名（ローマ字）/老手姓名（罗马字）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint DEFAULT NULL,
  PRIMARY KEY (`stu_id`,`tea_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of att_stu_tea_relation
-- ----------------------------
BEGIN;
INSERT INTO `att_stu_tea_relation` VALUES ('I2020001', '趙一', 'ZHAO YI', 'G2020001', '河上一二', 'KAWAGAMI ICHINI', '2020-05-09 01:20:10', NULL, NULL);
INSERT INTO `att_stu_tea_relation` VALUES ('I2020002', '錢二', 'QIAN ER', 'G2020002', '山下三四', 'YAMASHITA SANYON', '2020-05-09 01:20:10', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for att_student
-- ----------------------------
DROP TABLE IF EXISTS `att_student`;
CREATE TABLE `att_student` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '番号/学号',
  `login_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登録ID/登陆ID',
  `stu_pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'パスワード/密码',
  `stu_nm_kanji` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学生名（漢字）/学生姓名（汉字）',
  `stu_nm_roma` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学生名（ローマ字）/学生姓名（罗马字）',
  `stu_kokuseki` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '国籍/国籍',
  `stu_sex` tinyint DEFAULT NULL COMMENT '性别/性别',
  `stu_birthday` date DEFAULT NULL COMMENT '生年月日/生年月日',
  `stu_course` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '套餐',
  `stu_course0` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '小论文',
  `stu_course1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'コース/课程',
  `stu_course2` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `stu_major` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '専攻/专业',
  `stu_university` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '大学・院入学/进入大学时间',
  `stu_decision` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '進学決定/决定上学',
  `stu_contract` tinyint DEFAULT NULL COMMENT '契約書/契约书',
  `stu_interview` tinyint DEFAULT NULL COMMENT '面談シート/面试表',
  `stu_gakuhi` tinyint DEFAULT NULL COMMENT '学费/学费',
  `stu_keiyakubi` date DEFAULT NULL COMMENT '契約日/契约开始日',
  `stu_start` date DEFAULT NULL COMMENT '授業開始/开始上课时间',
  `stu_end` date DEFAULT NULL COMMENT '退学日/退学日',
  `stu_shang_end` date DEFAULT NULL COMMENT '尚藝舎卒業/本机构毕业日期',
  `stu_jp_end` date DEFAULT NULL COMMENT '日本語学校卒業/语言学校毕业日期',
  `stu_status` tinyint DEFAULT NULL COMMENT '区分/区分',
  `stu_doing_jp_school` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '在籍日本語学校/正在上的日语学校',
  `stu_home_school` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '母国出身校/母国学校',
  `stu_n2` tinyint DEFAULT NULL COMMENT 'N2/N2是否合格',
  `stu_n1` tinyint DEFAULT NULL COMMENT 'N1/N1是否合格',
  `stu_eju` tinyint DEFAULT NULL COMMENT 'EJU/EJU是否合格',
  `stu_when_jp` date DEFAULT NULL COMMENT '来日時期/来日本的时间',
  `stu_phone_num` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '電話番号/电话号',
  `stu_wechat` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Wechat/微信号',
  `stu_background` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '経緯/背景',
  `stu_broker` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '仲介会社名/中介公司名',
  `stu_school1` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '受験校1/考试学校1',
  `stu_school_time1` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日程/考试时间1',
  `stu_pass1` tinyint DEFAULT NULL COMMENT '合否/是否合格1',
  `stu_school2` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '受験校1/考试学校2',
  `stu_school_time2` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日程/考试时间2',
  `stu_pass2` tinyint DEFAULT NULL COMMENT '合否/是否合格2',
  `stu_school3` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '受験校1/考试学校3',
  `stu_school_time3` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日程/考试时间3',
  `stu_pass3` tinyint DEFAULT NULL COMMENT '合否/是否合格3',
  `stu_school4` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '受験校1/考试学校4',
  `stu_school_time4` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日程/考试时间4',
  `stu_pass4` tinyint DEFAULT NULL COMMENT '合否/是否合格4',
  `stu_school5` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '受験校1/考试学校5',
  `stu_school_time5` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日程/考试时间5',
  `stu_pass5` tinyint DEFAULT NULL COMMENT '合否/是否合格5',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_att_student_login_id` (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of att_student
-- ----------------------------
BEGIN;
INSERT INTO `att_student` VALUES (1, 'I2020001', 'I2020001', '趙一', 'ZHAO YI', '中国', 1, '2020-12-10', NULL, '100', '60vip', NULL, 'グラフィック', '大学院', '京都精华大学', 1, 0, 1, '2020-12-10', '2020-12-10', '2020-12-10', '2020-12-10', '2020-12-10', 0, '美羅斯國際日本語学院', '四川大学', 1, 0, 1, '2020-12-10', '080-9345-6789', 'asjhjb', '友人', '京都', '京都精华大学', 'B', 1, '多摩美術大学', 'A', 1, '', '', NULL, '', '', NULL, '', '', NULL, NULL, NULL, NULL);
INSERT INTO `att_student` VALUES (2, 'S1', '1', '錢二', 'QIAN ER', '中国', 0, '1993-12-09', NULL, '1', '126', NULL, '工藝', '学部', '女子美術大学', 0, 1, 0, '2020-12-06', '2020-12-17', '2022-12-09', '2021-12-09', '2021-12-03', 0, '千代田國際日本語学院', '山東大学', 0, 1, 0, '2020-12-02', '080-9765-4567', '167899nihao', '微博', NULL, '京都精华大学', 'A', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-02-28 16:03:53', NULL);
INSERT INTO `att_student` VALUES (14, 'S2', '2', '', '', '', 1, NULL, NULL, NULL, '', NULL, '', '', '', 0, 0, 1, NULL, NULL, NULL, NULL, NULL, 0, '', '', 1, 1, NULL, NULL, '', '', '', '', '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '2020-12-10 21:26:57', NULL, NULL);
INSERT INTO `att_student` VALUES (15, '', '111111', '', '', '', 1, NULL, NULL, NULL, '', NULL, '', '', '', 1, 1, 1, NULL, NULL, NULL, NULL, NULL, 1, '', '', 1, 1, NULL, NULL, '', '', '', '', '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '2021-01-16 20:03:40', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for att_teacher
-- ----------------------------
DROP TABLE IF EXISTS `att_teacher`;
CREATE TABLE `att_teacher` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '番号/学号',
  `login_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登録ID/登陆ID',
  `tea_pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'パスワード/密码',
  `tea_nm_kanji` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '教师名（漢字）/老师姓名（汉字）',
  `tea_nm_roma` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '教师名（ローマ字）/老手姓名（罗马字）',
  `tea_email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'メール/Mail',
  `tea_kokuseki` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '国籍/国籍',
  `tea_sex` tinyint DEFAULT NULL COMMENT '性别/性别',
  `tea_education` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学歴/学历',
  `tea_major` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '指導領域/指导学科',
  `tea_wage` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '授業時給/教课时薪',
  `tea_other_wage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '面談·ミーティング·特別·事務給/其他时薪',
  `tea_commuting_cost` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '交通費/通勤费',
  `tea_bank` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '銀行口座·支店名/银行信息',
  `tea_status` tinyint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`,`login_id`) USING BTREE,
  UNIQUE KEY `uk_att_teacher_login_id` (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of att_teacher
-- ----------------------------
BEGIN;
INSERT INTO `att_teacher` VALUES (4, 'T1', '1', '3', '4', '5', '6', 0, '7', '2', '3', '4', '11', '22', 1, '2020-12-10 22:34:06', NULL, NULL);
INSERT INTO `att_teacher` VALUES (6, 'T2', '111111', '2', '2', '2', '2', 1, '2', '2', '2', '2', '2', '2', 1, '2021-03-01 23:00:05', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) NOT NULL COMMENT '账户名称',
  `salt` varchar(20) DEFAULT NULL COMMENT '加密盐值',
  `password` varchar(200) NOT NULL COMMENT '用户密码密文',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `dept_id` varchar(64) DEFAULT NULL COMMENT '部门id',
  `real_name` varchar(60) DEFAULT NULL COMMENT '真实名称',
  `nick_name` varchar(60) DEFAULT NULL COMMENT '昵称',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱(唯一)',
  `status` tinyint DEFAULT NULL COMMENT '账户状态(1.正常 2.锁定 )',
  `sex` tinyint DEFAULT NULL COMMENT '性别(1.男 2.女)',
  `deleted` tinyint DEFAULT NULL COMMENT '是否删除(1未删除；0已删除)',
  `create_id` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_id` varchar(64) DEFAULT NULL COMMENT '更新人',
  `create_where` tinyint DEFAULT NULL COMMENT '创建来源(1.web 2.android 3.ios )',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_admin
-- ----------------------------
BEGIN;
INSERT INTO `sys_admin` VALUES (1, 'admin', 'eb36b', 'd791cc493bb93f62e2eb58e3935b378b', '', NULL, NULL, NULL, NULL, 1, 1, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_admin` VALUES (9, 'gao', '11c55', 'f7174f24d005a88597b07aa36d388adc', '11', NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 1, '2021-03-01 22:45:52', NULL);
INSERT INTO `sys_admin` VALUES (10, '1', 'a39e2', 'e10adc3949ba59abbe56e057f20f883e', '', NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 1, '2021-03-01 21:54:11', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_role`;
CREATE TABLE `sys_admin_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统用户角色';

-- ----------------------------
-- Records of sys_admin_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_admin_role` VALUES (5, 1, 1, '2020-12-02 22:29:37', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典名称',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典表';

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单权限名称',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '访问地址URL',
  `target` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'a target属性:_self _blank',
  `pid` int DEFAULT NULL COMMENT '父级菜单权限名称',
  `order_num` int DEFAULT NULL COMMENT '排序',
  `type` tinyint DEFAULT NULL COMMENT '菜单权限类型(1:目录;2:菜单;3:按钮)',
  `status` tinyint DEFAULT NULL COMMENT '状态1:正常 0：禁用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint DEFAULT NULL COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统权限';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES (10, '系统管理', NULL, NULL, '/', '_self', 0, 1, 1, 1, '2020-11-10 21:19:58', '2020-12-24 21:20:04', NULL);
INSERT INTO `sys_permission` VALUES (11, '人员管理', '', '', '/', '_self', 0, 2, 1, 1, '2020-03-19 13:29:40', NULL, 0);
INSERT INTO `sys_permission` VALUES (12, '预约管理', NULL, NULL, '/', '_self', 0, 98, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 0);
INSERT INTO `sys_permission` VALUES (13, '考勤管理', NULL, NULL, '/', '_self', 0, 99, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 0);
INSERT INTO `sys_permission` VALUES (14, '薪资管理', '', '', '/', '_self', 0, 99, 1, 1, '2020-03-19 13:29:40', NULL, 0);
INSERT INTO `sys_permission` VALUES (21, '学生管理', '', '', '/index/student', '_self', 11, 1, 2, 1, '2020-11-26 09:59:11', NULL, 0);
INSERT INTO `sys_permission` VALUES (22, '教师管理', '', '', '/index/teacher', '_self', 11, 2, 2, 1, '2020-11-26 09:59:49', NULL, 0);
INSERT INTO `sys_permission` VALUES (23, '角色设置', NULL, NULL, '/index/roles', '_self', 10, 2, 2, 1, '2020-11-26 09:59:49', '2020-11-26 09:59:55', 0);
INSERT INTO `sys_permission` VALUES (24, '菜单管理', NULL, NULL, '/index/menus', '_self', 10, 2, 2, 1, '2020-11-21 21:15:11', NULL, 0);
INSERT INTO `sys_permission` VALUES (25, '考勤记录查询', NULL, '', '/index/record', '_self', 13, 1, 2, 1, '2020-11-26 21:46:01', '2020-11-26 21:46:01', 0);
INSERT INTO `sys_permission` VALUES (26, '预约记录查询', NULL, NULL, '/index/appointment', '_self', 12, 1, 2, 1, '2020-11-26 21:46:01', '2020-11-26 21:46:01', 0);
INSERT INTO `sys_permission` VALUES (55, '字典管理', '', 'layui-icon-cellphone', '/index/sysDict', '_self', 10, 1, 2, 1, '2020-11-26 21:46:01', '2020-11-26 21:46:01', 1);
INSERT INTO `sys_permission` VALUES (58, '用户管理', '', '', '/index/admin', '_self', 10, 4, 2, 1, '2020-12-02 21:51:09', NULL, 1);
INSERT INTO `sys_permission` VALUES (59, '薪资查询', NULL, NULL, '/index/salary', '_self', 14, 1, 2, 1, '2020-11-26 21:46:01', '2021-02-10 02:36:30', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(300) DEFAULT NULL,
  `status` tinyint DEFAULT NULL COMMENT '状态(1:正常0:弃用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint DEFAULT NULL COMMENT '是否删除(1未删除；0已删除)',
  `data_scope` int DEFAULT NULL COMMENT '数据范围（1：所有 2：自定义 3： 本部门及以下部门 4：仅本部门 5:自己）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='系统角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '超级管理员', '拥有所有权限-不能删除', 1, '2019-11-01 19:26:29', NULL, 0, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int DEFAULT NULL COMMENT '角色id',
  `permission_id` int DEFAULT NULL COMMENT '菜单权限id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` VALUES (26, 1, 26, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (48, 1, 10, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (49, 1, 23, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (50, 1, 24, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (51, 1, 58, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (52, 1, 11, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (53, 1, 21, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (54, 1, 22, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (55, 1, 13, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (56, 1, 25, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (58, 1, 14, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (59, 1, 12, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (60, 1, 59, NULL, '2021-02-10 02:37:14');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
