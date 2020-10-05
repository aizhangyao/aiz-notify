/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 127.0.0.1:3306
 Source Schema         : aiz_notify

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 05/10/2020 16:07:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_email
-- ----------------------------
DROP TABLE IF EXISTS `sys_email`;
CREATE TABLE `sys_email` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `to_address` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收件人邮箱',
  `subject` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮件主题',
  `content` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮件内容',
  `template_id` bigint(11) DEFAULT NULL COMMENT '邮件模版id',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `send_status` tinyint(4) DEFAULT NULL COMMENT '发送状态(1代表成功;0代表失败)',
  `server_response` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '服务返回',
  `is_delete` tinyint(4) DEFAULT '1' COMMENT '1-正常、0-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for sys_email_template
-- ----------------------------
DROP TABLE IF EXISTS `sys_email_template`;
CREATE TABLE `sys_email_template` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮件模版名',
  `subject` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮件模板主题',
  `content` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮件模板内容',
  `type` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模版类别',
  `enable` tinyint(4) DEFAULT NULL COMMENT '启用标记 0否1是',
  `is_delete` tinyint(4) DEFAULT '1' COMMENT '1-正常、0-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for sys_sms
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms`;
CREATE TABLE `sys_sms` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `content` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短信内容',
  `phone_number` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '接收人手机号码',
  `template_id` bigint(11) DEFAULT NULL COMMENT '短信模版id',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `send_status` tinyint(4) DEFAULT NULL COMMENT '发送状态(1代表成功;0代表失败)',
  `server_response` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '服务返回',
  `is_delete` tinyint(4) DEFAULT '1' COMMENT '1-正常、0-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for sys_sms_template
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms_template`;
CREATE TABLE `sys_sms_template` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短信模版名',
  `content` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短信模板内容',
  `type` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模版类别',
  `template_code` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '阿里云模板code',
  `enable` tinyint(4) DEFAULT NULL COMMENT '启用标记 0否1是',
  `is_delete` tinyint(4) DEFAULT '1' COMMENT '1-正常、0-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for sys_template_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_template_param`;
CREATE TABLE `sys_template_param` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '参数名',
  `description` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '参数用处',
  `purpose` varchar(8) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '参数用途(01--短信、邮件模板; )',
	`is_delete` tinyint(4) DEFAULT '1' COMMENT '1-正常、0-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
