/*
 Navicat Premium Data Transfer

 Source Server         : mysql本机
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : bankb

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 16/07/2020 11:29:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xc_acct_rec_b
-- ----------------------------
DROP TABLE IF EXISTS `xc_acct_rec_b`;
CREATE TABLE `xc_acct_rec_b`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `acct_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '银行卡号（当然主键）',
  `task_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息任务id',
  `acct_number_from` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源银行卡号',
  `trade_money` bigint(20) UNSIGNED NOT NULL COMMENT '交易金额(单位分)',
  `source_type` int(4) UNSIGNED NULL DEFAULT NULL COMMENT '交易类型（转入-1,转出-2）',
  `create_date` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '转账流水' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
