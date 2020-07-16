/*
 Navicat Premium Data Transfer

 Source Server         : mysql本机
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : banka

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 16/07/2020 11:27:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xc_acct_a
-- ----------------------------
DROP TABLE IF EXISTS `xc_acct_a`;
CREATE TABLE `xc_acct_a`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `acct_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '银行卡号（当然主键）',
  `balance_money` bigint(20) UNSIGNED NOT NULL COMMENT '剩余金额（单位分）',
  `freeze_money` bigint(20) UNSIGNED NOT NULL COMMENT '冻结金额（单位分）',
  `version` int(8) UNSIGNED ZEROFILL NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '银行账户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xc_acct_a
-- ----------------------------
INSERT INTO `xc_acct_a` VALUES ('23323232323', '6221891151767145812', 9130000, 0, 00000001);

SET FOREIGN_KEY_CHECKS = 1;
