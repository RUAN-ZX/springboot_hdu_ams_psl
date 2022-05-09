DROP DATABASE IF EXISTS `sst`;
CREATE DATABASE `sst` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;

USE `sst`;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT, # 与auth不需要相同！每个业务有自己的需要！
--   `user_account_id` int(10) NOT NULL, # 与auth相同！ 先auth登记 auth可行 我们拿auth返回的id制造我们的user！
	
  `user_alias` varchar(12) NOT NULL,
  `user_name` varchar(12) NOT NULL,
  `user_role` tinyint(1) UNSIGNED DEFAULT '0', # 自己的拦截器 自己去鉴权就行了 反正也有userService 检查一下role 然后做限制！ 但是auth不会管这个 每个应用的role不一样！
  `user_status` tinyint(1) UNSIGNED DEFAULT '0', 
  `user_present_class_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

insert  into `user`(`user_id`,`user_alias`,`user_name`,`user_role`) values (1,'sst','沈盛涛',1),(2,'rzx','阮智祥',1),(3,'xwj','谢王杰',1),(4,'adc','射手a',0),(5,'sup','辅助b',0),(6,'saien','亡灵战神',0),(7,'羞涩','SHY',0);

# 进班级的记录
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `record_id` int(10) NOT NULL AUTO_INCREMENT,
  `record_student_id` int(10) NOT NULL,
  `record_class_id` int(10) NOT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

/*Data for the table `record` */

insert  into `record`(`record_id`,`record_student_id`,`record_class_id`) values (1,4,1),(2,5,1),(3,6,1),(4,7,1),(5,4,2),(6,5,2),(7,6,3),(8,7,3);


DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `class_id` int(10) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(12) DEFAULT NULL, # 默认为空的班级名称
  `class_teacher_id` int(10) NOT NULL,
  PRIMARY KEY (`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

/*Data for the table `class` */
insert  into `class`(`class_id`,`class_teacher_id`) values (1,1),(2,1),(3,2),(4,2),(5,3),(6,3);


/*Table structure for table `course` */
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `course_id` int(10) NOT NULL AUTO_INCREMENT,
  `course_user_id` int(10) NOT NULL,
  `course_class1` varchar(12) DEFAULT "",
  `course_class2` varchar(12) DEFAULT "",
  `course_class3` varchar(12) DEFAULT "",
  `course_class4` varchar(12) DEFAULT "",
  `course_class5` varchar(12) DEFAULT "",
  `course_class6` varchar(12) DEFAULT "",
  `course_class7` varchar(12) DEFAULT "",
  `course_class8` varchar(12) DEFAULT "",
  `course_day_week` tinyint(1) unsigned NOT NULL, # 0 - 
	UNIQUE key `uk_userid_day` (`course_user_id`, `course_day_week`),
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `course` */
insert  into `course`(`course_id`,`course_user_id`,`course_class1`,`course_class2`,`course_class3`,`course_class4`,`course_class5`,`course_class6`,`course_class7`,`course_class8`,`course_day_week`) values (1,1,'语文','数学','英语','地理','历史','物理','化学','生物',1),(2,1,'政治','高数','语文','生物','化学','物理','微机原理','模拟电路',2),(3,1,'电路与电子线路','电路与电子线路','电子工艺实习','电子工艺实习','电子工艺实习','概率论与数理统计','概率论与数理统计','概率论与数理统计',3),(4,1,'毛概','毛概','大学物理2','大学物理2','大学物理2','大学物理实验','大学物理实验','大学物理实验',4),(5,1,'电路与电子线路2','电路与电子线路2','影视音乐赏析','影视音乐赏析','','大学生职业发展与就业指导','大学生职业发展与就业指导','大学生职业发展与就业指导',5);



/*Table structure for table `subject` */
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `subject_id` int(10) NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(10) NOT NULL,
	UNIQUE KEY `uk_name` (`subject_name`),
  PRIMARY KEY (`subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `lesson` */

insert  into `subject`(`subject_id`,`subject_name`) values (1,'模电'),(2,'数电'),(3,'计算机网络');

/*Table structure for table `mission` */

DROP TABLE IF EXISTS `mission`;

CREATE TABLE `mission` (
  `mission_id` int(10) NOT NULL AUTO_INCREMENT,
  `mission_teacher_id` int(10) NOT NULL,
  `mission_student_id` int(10) NOT NULL,
  `mission_class_id` int(10) NOT NULL,
  `mission_question_id` int(10) NOT NULL,
	`mission_subject_id` int(10) NOT NULL, # 科目
	
	
  `mission_is_finished` tinyint(1) NOT NULL,
	
  `mission_time_finished` date DEFAULT NULL,
  `mission_time_start` date NOT NULL,
  `mission_time_end` date NOT NULL,
	
  `mission_description` varchar(50) DEFAULT NULL,
  
  PRIMARY KEY (`mission_id`,`mission_question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `mission` */

insert  into `mission`(`mission_id`,`mission_teacher_id`,`mission_student_id`,`mission_class_id`,`mission_question_id`,`mission_is_finished`,`mission_time_finished`,`mission_time_start`,`mission_time_end`,`mission_description`,`mission_subject_id`) values (1,1,4,1,6,0,NULL,'2022-04-27','2022-04-30',NULL,0),(2,1,5,1,6,0,NULL,'2022-04-27','2022-04-30',NULL,0),(3,1,6,1,6,0,NULL,'2022-04-27','2022-04-30',NULL,0),(4,1,7,1,6,0,NULL,'2022-04-27','2022-04-30',NULL,0);

/*Table structure for table `question` */

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `question_id` int(10) NOT NULL AUTO_INCREMENT,
  `question_teacher_id` int(10) NOT NULL,
	`question_subject_id` int(10) NOT NULL,
	
  `question_stem` varchar(100) NOT NULL,
  `question_answer_a` varchar(100) NOT NULL,
  `question_answer_b` varchar(100) NOT NULL,
  `question_answer_c` varchar(100) NOT NULL,
  `question_answer_d` varchar(100) NOT NULL,
  
  `question_true_answer` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

/*Data for the table `question` */

insert  into `question`(`question_id`,`question_teacher_id`,`question_stem`,`question_answer_a`,`question_answer_b`,`question_answer_c`,`question_answer_d`,`question_subject_id`,`question_true_answer`) values (1,3,'某个处于放大状态的电路，当输入电压为10mV ，输出电压为6.5V,输入电压为15mV 时，输出电压为7V （以上均为直流电压），它的电压增益为','700','650','100','-100',1,3),(2,3,' 当输入信号频率为f L 或f H 时，电压增益的幅值约下降为中频时的','0.5','0.7','0.9','1',1,2),(3,3,'当输入信号频率为fL 或fH 时, 电压增系下降了','2dB','3dB','4dB','6dB',1,2),(4,3,'某放大电路在负载开路时的输出电压为4V ，接3K Ω的负载电阻后输出电压降为3V ，这说明放大电路的输出电阻为','10K Ω','2K Ω','1 K Ω','0.5K Ω',1,3),(5,3,'用两个AU 相同的放大电路A 和B 分别对同一个具有相同内阻信号进行放大，测试结果输出电压VOA>VOB ，由此可知A 比B','一样','差','好','无法判别',1,2),(6,1,'已知逻辑函数Y=AB+AC+ BC与其相等的函数为',' AB',' AB+AC','AB+BC','AB+C',2,4),(7,1,'一个数据选择器的地址输入端有3个时，最多可以有几个数据信号输出。','4','6','8','16',2,3),(8,1,'在四变量卡诺图中，逻辑上不相邻的一组最小项为','m1与m3','m4 与m6','m5 与m13','m2 与m8',2,4),(9,1,'L=AB+C 的对偶式为：','A+BC','(A+B) C','A+B+C','ABC',2,2),(10,1,'半加器和的输出端与输入端的逻辑关系是','与非','或非','与或非','异或',2,4),(11,2,'常用的传输介质中，带宽最宽、信号传输衰减最小、抗干扰能力最强的一类传输介质是','光纤','双绞线','同轴电缆','无线信道',3,1),(12,2,'数据解封装的过程是','段—包—帧—流—数据','流—帧—包—段—数据',' 数据—包—段—帧—流','数据—段—包—帧—流',3,2),(13,2,'完成路径选择功能是在OSI模型的','物理层','数据链路层','网络层','运输层',3,3),(14,2,'在OSI中，为实现有效、可靠数据传输，必须对传输操作进行严格的控制和管理，完成这项工作的层次是','物理层','数据链路层','网络层','运输层',3,2),(15,2,'若网络形状是由站点和连接站点的链路组成的一个闭合环,则称这种拓扑结构为','星形拓扑','总线拓扑','环形拓扑','树形拓扑',3,3);


DROP TABLE IF EXISTS `sprite`;
CREATE TABLE `sprite` (
  `sprite_id` int(10) NOT NULL AUTO_INCREMENT,
  `sprite_name` varchar(24) NOT NULL,
	
  `sprite_attack` int(5) NOT NULL,
  `sprite_defence` int(5) NOT NULL,
  `sprite_health` int(5) NOT NULL,
  `sprite_experience` int(5) NOT NULL,
  PRIMARY KEY (`sprite_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `sprite_record` */
DROP TABLE IF EXISTS `sprite_record`;
CREATE TABLE `sprite_record` (
  `sprite_record_id` int(10) NOT NULL AUTO_INCREMENT,
  `sprite_record_user_id` int(10) NOT NULL,
  `sprite_record_sprite_id` int(10) NOT NULL,
	
  `sprite_record_health` int(5) DEFAULT NULL,
  `sprite_record_experience` int(5) NOT NULL,
  PRIMARY KEY (`sprite_record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sprite_record` */

-- insert  into `sprite_record`(`sprite_record_id`,`sprite_record_user_id`,`sprite_record_sprite_name`,`sprite_record_level`,`sprite_record_attack`,`sprite_record_defence`,`sprite_record_full_health`,`sprite_record_true_health`,`sprite_record_full_experience`,`sprite_record_true_experience`) values (1,1,'peashooter',1,1,5,100,50,100,1),(2,6,'peashooter',2,50,10,100,67,100,2);

/*Table structure for table `user` */

/*Table structure for table `item_record` */

DROP TABLE IF EXISTS `item_record`;

CREATE TABLE `item_record` (
  `item_record_id` int(10) NOT NULL AUTO_INCREMENT,
  `item_record_user_id` int(10) NOT NULL,
  `item_record_item_name` varchar(25) NOT NULL,
  `item_record_item_num` int(10) NOT NULL,
  PRIMARY KEY (`item_record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `item_record` */

insert into `item_record`(`item_record_id`,`item_record_user_id`,`item_record_item_name`,`item_record_item_num`) values (1,6,'potion',5),(2,6,'ball',4),(3,1,'potion',3),(4,1,'ball',5),(5,2,'potion',6);


