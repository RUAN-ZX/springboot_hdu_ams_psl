CREATE DATABASE `auth` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;

USE `auth`;

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `account_id` int(10) NOT NULL AUTO_INCREMENT,
  `account_pwd` varchar(50) NOT NULL,
  `account_name` varchar(12) NOT NULL, # 用户名+密码
  `account_mail` varchar(36) NOT NULL, # 邮箱+密码
  `account_status` tinyint(1) NOT NULL, # 账户状态 账户相关状态
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;


