# 消息通知模块
本项目为消息通知管理平台，旨在帮助更多开发者可以把更多的精力放在业务部分，减少这类常用的第三方接口调用和调试所花费的时间。也可以提供给没接触过消息通知API的开发者朋友一个参考，目标是做到代码移植能力强、通过查看本文档的说明拿来即用。
目前阶段基本技术栈采用SpringBoot+MySQL+Mybatis-plus。

# 功能方面
- [x] 阿里云短信Sms
- [x] 邮件Email
- [ ] 站内信IM：APP内的聊天
- [ ] 通知栏PUSH：系统弹窗消息
- [ ] 微信公众号消息
- [ ] 微信小程序(服务通知)

# 即将引入技术栈
- [ ] 引入MQ消息队列
- [ ] 邮件PDF附件、HTML

# 版本选择
- Java Version 1.8
- MySQL 5.7
- SpringBoot 2.3.0.RELEASE
- Maven 3.6.0
- Mybatis-plus 3.2.0

# 快速上手
## 数据库设计
为了在发送消息时更加方便，所以涉及短信邮件模板、模板参数的维护。因此设计了`sys_email`、`sys_email_template`、`sys_sms`、`sys_sms_template`、`sys_template_param`这五张表设计。下面我把建表脚本全部贴在下面。
```sql
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
```

## 配置项目
配置`src/main/resources/application.yml`，把其中的`access-key-id`、`access-key-secret`、`username`、`password`配置成你自己的。具体申请步骤将在[这里](#)给出。
```yml
aliyun:
  access-key-id: 你的16位key id
  access-key-secret: 你的30位key secret
  sms:
    sign-name: 你的短信签名
mail:
  smtp:
    host: smtp.263.net
    port: 25
  send:
    username: 你的发送方邮箱
    password: 密码
```

## 接口示例
- 邮件发送：主要接口`EmailService`。

| 方法名                                                 | 介绍           | 参数说明                                                     |
| ------------------------------------------------------ | -------------- | ------------------------------------------------------------ |
| `bool sendMail(Email email);`                          | 发送普通邮件   | 其中Email中的`收件人邮箱toAddress`、`邮件主题subject`、`邮件内容content`为必须发的 |
| `String getEmailContent(Long templateId, Map params);` | 获取邮件的内容 | 邮件模板Id,邮件内容参数                                      |

- 短信发送：主要接口`SmsService`。

| 方法名                                                       | 介绍             | 参数说明                                                     |
| ------------------------------------------------------------ | ---------------- | ------------------------------------------------------------ |
| `boolean sendSms(String phoneNumber, String templateCode, Map params);` | 发送短信         | `phoneNumber 手机号码 `<br />`templateCode 阿里云Code`<br />`params 短信内容` |
| `String getSmsContent(String templateCode, Map params);`     | 获取短信发送内容 | `templateCode 模板Code` <br />`params 短信k-v内容`           |



# Other

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [MyBatis Framework](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [MyBatis Quick Start](https://github.com/mybatis/spring-boot-starter/wiki/Quick-Start)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)