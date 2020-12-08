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

 Date: 08/12/2020 22:52:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单权限名称',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：sys:admin:add,sys:admin:edit)',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '访问地址URL',
  `target` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'a target属性:_self _blank',
  `pid` int DEFAULT NULL COMMENT '父级菜单权限名称',
  `order_num` int DEFAULT NULL COMMENT '排序',
  `type` tinyint DEFAULT NULL COMMENT '菜单权限类型(1:目录;2:菜单;3:按钮)',
  `status` tinyint DEFAULT NULL COMMENT '状态1:正常 0：禁用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint DEFAULT NULL COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统权限';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES (10, '系统管理', NULL, NULL, '/', '_self', 0, 1, 1, 1, '2020-11-10 21:19:58', '2020-12-24 21:20:04', NULL);
INSERT INTO `sys_permission` VALUES (11, '人员管理', '', '', '/', '_self', 0, 2, 1, 1, '2020-03-19 13:29:40', NULL, 0);
INSERT INTO `sys_permission` VALUES (12, '预约管理', NULL, NULL, '/', '_self', 0, 100, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 0);
INSERT INTO `sys_permission` VALUES (13, '考勤管理', NULL, NULL, '/', '_self', 0, 99, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 0);
INSERT INTO `sys_permission` VALUES (14, '楼层管理', NULL, NULL, '/', '_self', 0, 99, 1, 1, '2020-03-19 13:29:40', '2020-03-19 13:29:40', 0);
INSERT INTO `sys_permission` VALUES (21, '学生管理', '', '', '/index/student', '_self', 11, 1, 2, 1, '2020-11-26 09:59:11', NULL, 0);
INSERT INTO `sys_permission` VALUES (22, '教师管理', '', '', '/index/teacher', '_self', 11, 2, 2, 1, '2020-11-26 09:59:49', NULL, 0);
INSERT INTO `sys_permission` VALUES (23, '角色设置', NULL, NULL, '/index/roles', '_self', 10, 2, 2, 1, '2020-11-26 09:59:49', '2020-11-26 09:59:55', 0);
INSERT INTO `sys_permission` VALUES (24, '菜单管理', NULL, NULL, '/index/menus', '_self', 10, 2, 2, 1, '2020-11-21 21:15:11', NULL, 0);
INSERT INTO `sys_permission` VALUES (25, '考勤记录查询', NULL, '', '/index/record', '_self', 13, 1, 2, 1, '2020-11-26 21:46:01', '2020-11-26 21:46:01', 0);
INSERT INTO `sys_permission` VALUES (55, '字典管理', '', 'layui-icon-cellphone', '/index/sysDict', '_self', 10, 1, 2, 1, '2020-11-26 21:46:01', '2020-11-26 21:46:01', 1);
INSERT INTO `sys_permission` VALUES (57, '工资核算', '', '', '', '_self', 13, 3, 1, 1, '2020-12-02 21:39:21', NULL, 1);
INSERT INTO `sys_permission` VALUES (58, '用户管理', '', '', '/index/admins', '_self', 10, 4, 2, 1, '2020-12-02 21:51:09', '2020-12-02 21:51:09', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;


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

 Date: 08/12/2020 22:52:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int DEFAULT NULL COMMENT '角色id',
  `permission_id` int DEFAULT NULL COMMENT '菜单权限id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` VALUES (48, 1, 10, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (49, 1, 23, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (50, 1, 24, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (51, 1, 58, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (52, 1, 11, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (53, 1, 21, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (54, 1, 22, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (55, 1, 13, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (56, 1, 25, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (57, 1, 57, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (58, 1, 14, '2020-12-02 22:30:03', NULL);
INSERT INTO `sys_role_permission` VALUES (59, 1, 12, '2020-12-02 22:30:03', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;


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

 Date: 08/12/2020 22:52:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
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

SET FOREIGN_KEY_CHECKS = 1;


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

 Date: 08/12/2020 22:53:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_role`;
CREATE TABLE `sys_admin_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `admin_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统用户角色';

-- ----------------------------
-- Records of sys_admin_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_admin_role` VALUES (5, 1, 1, '2020-12-02 22:29:37', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;


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

 Date: 08/12/2020 22:53:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `adminname` varchar(50) NOT NULL COMMENT '账户名称',
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
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_admin
-- ----------------------------
BEGIN;
INSERT INTO `sys_admin` VALUES (1, 'admin', 'eb36b', 'd791cc493bb93f62e2eb58e3935b378b', '', NULL, NULL, NULL, NULL, 1, 1, 0, NULL, NULL, NULL, NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
