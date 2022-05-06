DROP DATABASE IF EXISTS `auth`;
CREATE DATABASE `auth` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;

USE `auth`;

# 让应用决定应该怎么认证 比如教务查就不会有用户名+密码的方式认证
# 而我auth只提供对应的注册 登录需要的接口！
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `account_id` int(10) NOT NULL AUTO_INCREMENT,
  `account_mail` varchar(36) DEFAULT NULL, # 邮箱+密码
	
	`account_pwd` varchar(50) DEFAULT NULL,
  `account_name` varchar(24) DEFAULT NULL, # 用户名+密码
  `account_phone` CHAR(11) DEFAULT NULL, # 手机号+密码 预留字段 其实应该用不到 需要公司注册！
	
  `account_status` tinyint(1) UNSIGNED DEFAULT 0, # 账户状态 账户相关状态 0 为正常！
	
	`account_user_id` int(10) NOT NULL,
  `account_app` tinyint(1) UNSIGNED NOT NULL, # app代号 0 为HDU查教务 | 1为 组队啦 | 2为 SST
  PRIMARY KEY (`account_id`),
	UNIQUE KEY `uk_id_app` (`account_user_id`,`account_app`) #  这个能够唯一区分！ 其他都扯淡
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
# 实际上 HDU教务查不需要这个的MYSQL 除非哪天需要密码登录 再说 纯验证码太麻烦