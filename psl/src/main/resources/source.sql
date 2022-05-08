CREATE DATABASE IF NOT EXISTS `teacher_temp` DEFAULT CHARACTER SET utf8mb4 ;

USE `teacher_temp`;
# 设定几种表 source表 源数据 derived表 导出表 派生表

# account 教师 
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `account_id` INT(8) NOT NULL, # teacher id
  `account_name` VARCHAR(24) NOT NULL, # Hadi Barzegar Bafrooei

  `account_mail` VARCHAR(50) DEFAULT NULL, # 保持最新状态就行了！
	`account_phone` CHAR(11) DEFAULT NULL, # 13713524786
  `account_pwd` VARCHAR(20) DEFAULT NULL, 
	`account_role` TINYINT(1) UNSIGNED DEFAULT NULL, # 0 1 2 teacher manager root
  PRIMARY KEY (`account_id`),
	UNIQUE KEY `uk_account_id_name` (`account_id`,`account_name`)
--   CONSTRAINT `tdid_did_fk` FOREIGN KEY (`TDid`) REFERENCES `D` (`Did`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

# 不一定所有的账户都有teacher title！！！
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `teacher_title_id` INT(8) AUTO_INCREMENT NOT NULL,  # 老师的职称是可能会变的！
	`teacher_id` INT(8) NOT NULL, # 职工号信息来源可靠 可以顺便导入到Account里边去！
	`teacher_name` VARCHAR(24) NOT NULL, # 名字也很重要！
	
-- 	`title_academy_id` TINYINT(1) UNSIGNED DEFAULT 0, # 学院
	`teacher_team` varchar(30) DEFAULT NULL,
	`teacher_type` VARCHAR(10) DEFAULT NULL,  # 专职教师 系列
	`teacher_title_name` VARCHAR(24) DEFAULT NULL, # 助理研究员（自然科学） 职称
	`teacher_level` TINYINT(1) UNSIGNED DEFAULT NULL, # 正高 副高 中级 初级 0 ~ 3 每年都有数据 直接查就行啦
	`teacher_title_year` SMALLINT(4) UNSIGNED NOT NULL,
-- 	`create_time` DATETIME NOT NULL, # 这个要管 创建的时候 其他时候他也不会变
-- 	`update_time` DATETIME DEFAULT NOW(), # 不用管这个数据 每次更新即可
	# 前后端去实现时区转换
	
  PRIMARY KEY (`teacher_title_id`),
	UNIQUE KEY `uk_teacher_id_name` (`teacher_id`,`teacher_name`,`teacher_title_year`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;


## -------------三大课合一！
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `course_id` INT(8) AUTO_INCREMENT NOT NULL, 
  `course_num` CHAR(34) NOT NULL, # '(2019-2020-1)-B0405450-42119-1 (2013-2014-2)-20110105-S0403070-1
  `course_term` CHAR(12) NOT NULL, # 2019-2020-1 
	
	`course_time` VARCHAR(64) DEFAULT NULL, # 60 周三第10,11节{第1-17周|单周};周三第10,11节{第2-16周|双周}
  `course_name` VARCHAR(24) NOT NULL, # MATLAB及在电子信息课程中的应用 18
	`course_address` VARCHAR(64) DEFAULT NULL, # 第7教研楼中2021;第7教研楼中3021 21
	`course_points` DOUBLE(10,2) DEFAULT NULL, # 学分 记录一下 不参与计算 也不会分配！
	`course_class` VARCHAR(200) DEFAULT NULL, # 最长有170的。。。恐怖 
	
  `course_teacher_id` INT(8) UNSIGNED DEFAULT NULL, 
	# 因为可能名字暂时没登记在案 这个记录还是留着为好 后面找很困难 所以default null、
	# 董林玺/刘超然 这种应该做拆分！凡是有（多人） 或者带斜杠的 都可以拆分 到时候显示那个老师的就好了 统计的话 按照学时的比例分成工作量！
	# 另外 如果有多行带有“多人” 那意思多个班 我们不用管 反正1、统计下边那些数据的标准学时 归到各个老师头上2、多人那边 第一行作为其他数据的填充！剩下重复的跳过 直到详情获取标准学时
	`course_teacher_name` CHAR(3) NOT NULL, # 记录一下老师名字 毕竟有些老师id是找不到的！
	
	
-- 	`course_type` TINYINT(1) UNSIGNED NOT NULL, # 根据从哪个表提取出来的 可以区分不同种类 理论 实验 短学期 毕设
	
  `course_capacity` SMALLINT(4) NOT NULL,
  `course_capacity_factor1` DOUBLE(10,2) DEFAULT 1.0, # double应该多少为好？ 班级规模系数 根据规模算出来的 不同性质的课 计算方法不同！
  `course_capacity_factor2` DOUBLE(10,2) DEFAULT 1.0, # 实验课才有2系数 理论课默认为1即可！


	`course_hours` DOUBLE(10,2) NOT NULL, # 老师有几几开的 所以会这样！ 
	`course_hours_theory` DOUBLE(10,2) DEFAULT 0.00, # 无论实验 还是讲课 还是标准化的学时 先算 然后几几开就好了！
	`course_hours_exp` DOUBLE(10,2) DEFAULT 0.00, # 注意 这里使用理论-实验就好了 
	`course_hours_exp_std` TINYINT(1) UNSIGNED NOT NULL, # 理论课标准课时 直接×1
	#实验课 同 标准学时计算方法 这也就是总的标准课时
	`course_hours_theory_std` TINYINT(1) UNSIGNED NOT NULL, # 理论课标准课时有公式 注意 不是两个std加起来就是总标准课时！！ 类别系数 × 规模系数 × 课时
  `course_hours_std` TINYINT(1) UNSIGNED NOT NULL, # 总标准课时	除了理论学时哪个算法以外 再乘上优课优测！
	
	
  `course_bilingual` VARCHAR(10) DEFAULT NULL, # 匹配到数字就作为系数乘出来计算就好了 但是内容保留 省的还要存对应 status——code对应哪个项目 太麻烦了 存数字主要为了排序 查找方便罢了 不常用的话根本么必要
	`course_reform` VARCHAR(16) DEFAULT NULL, # 翻转1.4；卓越1.3 这两个一般来说取高作为类别系数 但不一定 实际计算以类别系数为准
	# 普通课叫课改系数 
	`course_factor` DOUBLE(10,2) DEFAULT 1.0, # 类别系数 直接爬 直接用来算就好了！
	`course_prior` DOUBLE(10,2) DEFAULT 1.0, # 优课优酬 短学期没有
	
	
	`course_hours_op` DOUBLE(10,2) DEFAULT 0.00, # 上机学时 记录一下 不参与计算
	
	`course_properties` CHAR(1) DEFAULT NULL, # 性质 I ABJ
	
	`course_note1` VARCHAR(64) DEFAULT NULL, 
	# 检查这个属性 string.length 调用快的 超过则截断！检查string的长度！
	# 应该在循环里边顺手检查一下！
  `course_note2` VARCHAR(64) DEFAULT NULL,
	
	
  PRIMARY KEY (`course_id`),
	UNIQUE KEY `uk_cnum_ctname` (`course_num`,`course_teacher_name`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

## -------------课程 理论+实践 专门用于统计主讲课时的!
DROP TABLE IF EXISTS `short_term`;
CREATE TABLE `short_term` (
  `short_term_id` INT(8) AUTO_INCREMENT NOT NULL, 
  `short_term_num` CHAR(34) DEFAULT NULL, # '(2019-2020-1)-B0405450-42119-1 有些竞赛算在这里 故没课号！
  `short_term_term` CHAR(11) NOT NULL, # 2019-2020-1
	`short_term_time` VARCHAR(64) DEFAULT NULL, # 第19周/周五/8:20

  `short_term_name` VARCHAR(24) NOT NULL, # 项目:电子线路仿真技术与PCB设计
	`short_term_address` VARCHAR(64) DEFAULT NULL, # 第8教研楼302（线路实习实验室）

	
  `short_term_teacher_id` INT(8) NOT NULL, # 董林玺/刘超然 这种应该做拆分！凡是有（多人） 或者带斜杠的 都可以拆分 到时候显示那个老师的就好了 统计的话 按照学时的比例分成工作量！
	# 另外 如果有多行带有“多人” 那意思多个班 我们不用管 反正1、统计下边那些数据的标准学时 归到嗝嗝老师头上2、多人那边 第一行作为其他数据的填充！剩下重复的跳过 直到详情获取标准学时
	`short_term_teacher_name` CHAR(3) NOT NULL, # 记录一下所有的老师名字 仅作为记录而已
	
  `short_term_capacity` SMALLINT(4) UNSIGNED DEFAULT NULL, # 0-65535
  `short_term_capacity_factor` DOUBLE(10,2) DEFAULT NULL, 
	`short_term_reform` DOUBLE(10,2) DEFAULT 1.0, # 教改系数 
	`short_term_factor` DOUBLE(10,2) DEFAULT 1.0, # 类别系数 直接爬 直接用来算就好了！
	
	`short_term_hours` DOUBLE(10,2) DEFAULT NULL, # 这个学时 直接复制粘贴就好了 没用的
  `short_term_hours_std` SMALLINT(4) UNSIGNED NOT NULL, # 总标准课时 cf * re * fa * hr
	
	`short_term_properties` CHAR(1) DEFAULT NULL, # 短学期的性质
	`short_term_note1` VARCHAR(64) DEFAULT NULL, # 限定50字符
  `short_term_note2` VARCHAR(64) DEFAULT NULL,
	# 如果别人的share转给他了 会导致课号-名字 都没办法限制！
-- 	UNIQUE KEY `uk_num_tname` (`short_term_num`,`short_term_teacher_name`),
  PRIMARY KEY (`short_term_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

# 毕设学生表
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
	student_id INT(8) UNSIGNED NOT NULL, # 毕设学生的id
	student_name VARCHAR(25) NOT NULL, # 毕设学生名字
	student_major VARCHAR(60) NOT NULL,#光电信息科学与工程(光电工程方向)
	student_graduate_year SMALLINT(4) UNSIGNED NOT NULL,# 每年都有一批！
	PRIMARY KEY(`student_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;


# 毕业设计
DROP TABLE IF EXISTS `thesis_design`;
CREATE TABLE `thesis_design` (
	thesis_design_id INT(8) AUTO_INCREMENT NOT NULL, 
	thesis_design_year SMALLINT(4) UNSIGNED NOT NULL,# 每年都有一批！
	thesis_design_note VARCHAR(24) DEFAULT NULL,# 备注
	thesis_design_teacher_id INT(8) NOT NULL,# 教师id 方便查询。
	thesis_design_teacher_name CHAR(3) DEFAULT NULL, # 方便显示！
-- 	td_stu_id INT(8) NOT NULL, # 毕设学生的id
	thesis_design_student_name CHAR(3) DEFAULT NULL, # 可能是卓越加点空记录
	thesis_design_student_id INT(8) UNSIGNED NOT NULL, # 毕设学生的id 18042328
	
	thesis_design_grade TINYINT(1) UNSIGNED NOT NULL, # 0 1 2 3 4 优秀 良好 中等 及格 不及格 这个需要统计 所以用数字！
	thesis_design_factor1 DOUBLE(10,2) DEFAULT 12.0, # 基本系数
	thesis_design_factor2 DOUBLE(10,2) DEFAULT 0.0, # 优秀系数
	thesis_design_t1 DOUBLE(10,2) DEFAULT 1.0, # T1系数 std = (f1+f2)*t1
	thesis_design_std TINYINT(1) UNSIGNED NOT NULL, # 标准学时 
	UNIQUE KEY `uk_stu_id` (`thesis_design_student_id`), # 学号 得天独厚的区分条件
	PRIMARY KEY(`thesis_design_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

## ----------成果类
##  还有附带的 加分效果的录入 教科办老师只需要选择它的性质即可 余下自动加分 
## 针对多人成果 有占比 百分比 100就是单人项目了！ 可以修改这个占比 影响各个老师的分数！
## 然后有个属性name 记录所有登记的老师
DROP TABLE IF EXISTS `achievement`;
CREATE TABLE `achievement`(
  achievement_id INT(8) AUTO_INCREMENT NOT NULL, 
	achievement_teacher_id INT(8) NOT NULL,  # 当前老师！
	
	achievement_teacher_name CHAR(3) NOT NULL,
	achievement_year SMALLINT(4) UNSIGNED NOT NULL, # 学年成果
	achievement_name VARCHAR(150) NOT NULL, # 成果名称
	
	# 好像用不到 他老是变需求。。
	achievement_type TINYINT(1) UNSIGNED DEFAULT NULL, # 0 标志性 1 非标志性 2 院级 只对S3S4有影响的！
	achievement_level VARCHAR(48) NOT NULL, 	## SCI收录的TOP期刊论文，计算机科学与技术、软件工程学科的A类会议论文（论文页数≥12页）
	
	achievement_category VARCHAR(24) NOT NULL,# 教学业绩核心指标目录与分值
	
	achievement_evidence1 VARCHAR(60) DEFAULT NULL, # https:// 开头这个省略 后边的才用！ stea.ryanalexander.cn/psl/hdu1.jpg
	achievement_evidence2 VARCHAR(60) DEFAULT NULL,
	achievement_evidence3 VARCHAR(60) DEFAULT NULL,
	
	achievement_kpi_s34 DOUBLE(10,2) DEFAULT NULL, 
	achievement_kpi_category VARCHAR(18) DEFAULT NULL, 
	# 记录加分以及具体加了多少 加到哪里 教改项目 10

-- 	# 前端可以读取这玩意 显示在前端 然后改完数据 一并返回后端 形成这个字符串即可
	
	achievement_rank TINYINT(1) UNSIGNED DEFAULT NULL, # 获奖等级 1 2 3
	achievement_kpi DOUBLE(10,2) NOT NULL, # 最终指标分值
	achievement_note VARCHAR(32) DEFAULT NULL,# 我们教科办备注
  # 有完全一摸一样的 没办法。。
	PRIMARY KEY(`achievement_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

# 研究生的绩点 也作为源数据！ 
DROP TABLE IF EXISTS `post_graduate`;
CREATE TABLE `post_graduate`( 
	`post_graduate_id` INT(8) AUTO_INCREMENT NOT NULL, 
	`post_graduate_year` SMALLINT(4) UNSIGNED NOT NULL,
	`post_graduate_teacher_id` INT(8) NOT NULL, 
	`post_graduate_teacher_name` CHAR(3) NOT NULL, # 
	`post_graduate_kpi` DOUBLE(10,5) NOT NULL, # 标准学时 统一使用标准学时！
	UNIQUE KEY `uk_post_graduate_year_tid` (`post_graduate_year`,`post_graduate_teacher_name`),
	PRIMARY KEY(`post_graduate_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

# 研究生的绩点 也作为源数据！ 
DROP TABLE IF EXISTS `special_assignment`;
CREATE TABLE `special_assignment`( 
	`special_assignment_id` INT(8) AUTO_INCREMENT NOT NULL, 
	`special_assignment_year` SMALLINT(4) UNSIGNED NOT NULL,
	
	`special_assignment_teacher_id` INT(8) DEFAULT NULL, 
	`special_assignment_teacher_name` CHAR(3) NOT NULL, # 
	
	`special_assignment_kpi` DOUBLE(10,2) NOT NULL, # 研究生的绩点 以计算在老师头上的标准学时
	
	`special_assignment_name` VARCHAR(24) NOT NULL, # 信号系统与信号处理教研组
	`special_assignment_project` VARCHAR(36) NOT NULL, # 卓越工程师专业系主任
  UNIQUE KEY `uk_year_tname_project` (`special_assignment_year`,`special_assignment_teacher_name`, 		  `special_assignment_project`),
	PRIMARY KEY(`special_assignment_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `shoulder_both`;
CREATE TABLE `shoulder_both`( 
	`shoulder_both_id` INT(8) AUTO_INCREMENT NOT NULL, 
	`shoulder_both_year` SMALLINT(4) UNSIGNED NOT NULL,
	
	`shoulder_both_teacher_id` INT(8) DEFAULT NULL, 
	`shoulder_both_teacher_name` CHAR(3) NOT NULL, # 
	
	`shoulder_both_hours` TINYINT(1) UNSIGNED DEFAULT 0, # 研究生的绩点 以计算在老师头上的标准学时
	UNIQUE KEY `uk_shoulder_both_year_tname` (`shoulder_both_year`,`shoulder_both_teacher_name`),
	PRIMARY KEY(`shoulder_both_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

## ------------- S2 学评教信息 直接有结果 直接用就好了
## 这里记录是学期结算的！只需要总分 按上一年学评教分数的平均值排名
DROP TABLE IF EXISTS `evaluation`;
CREATE TABLE `evaluation`(
  `evaluation_id` INT(8) AUTO_INCREMENT NOT NULL, 
	`evaluation_term` CHAR(11) NOT NULL,
	`evaluation_teacher_id` INT(8) NOT NULL,
	`evaluation_teacher_name` CHAR(3) NOT NULL,
	
	`evaluation_participate` SMALLINT(4) UNSIGNED NOT NULL, # 参与评价人数
	`evaluation_score` DOUBLE(10,3) NOT NULL, # 92.925
	`evaluation_school_rank` SMALLINT(4) UNSIGNED NOT NULL, # 学校排名
	`evaluation_school_attend` SMALLINT(4) UNSIGNED NOT NULL, # 学校参与排名的老师人数
	
	`evaluation_academy_rank` SMALLINT(4) UNSIGNED NOT NULL, # 学院排名
-- 	`evaluation_result` DOUBLE(10,2) NOT NULL, # 排名占比！ 排名比 按照分数平均之后的 排名比 所以学期内排名比没意义！分数是唯一有用的！
	
	PRIMARY KEY(`evaluation_id`),
	UNIQUE KEY `uk_term_name` (`evaluation_term`,`evaluation_teacher_name`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

# 作为导出表 而不是用于历史数据导入表的！注意 导入的表不一定在一起 所以一个excel表 一个数据表！
DROP TABLE IF EXISTS `s1_detail`;
CREATE TABLE `s1_detail`(
  `s1_detail_id` INT(8) AUTO_INCREMENT NOT NULL, 
  `s1_detail_teacher_id` INT(8) DEFAULT NULL,  
	`s1_detail_teacher_name` VARCHAR(24) NOT NULL, # 怪怪的老师名字都来了。。有些没有id 暂时 还是先登记在案吧
	`s1_detail_year` SMALLINT(4) UNSIGNED DEFAULT NULL,

	# 历史数据可以 获取到足够数据 调用计算过程计算 完成统计！
	`s1_kpi_course_theory` DOUBLE(10,4) DEFAULT NULL, # 课程的总业绩
	`s1_kpi_course_experiment` DOUBLE(10,4) DEFAULT NULL, # 实验
	`s1_kpi_short_term` DOUBLE(10,4) DEFAULT NULL, # 短学期
	`s1_kpi_thesis_design` DOUBLE(10,4) DEFAULT NULL, # 毕设
	
	
	# 课外实践工作量
	`s1_kpi_achievement1` DOUBLE(10,4) DEFAULT NULL, #学校标志性成果业绩分（本科）	 	
	`s1_kpi_achievement2` DOUBLE(10,4) DEFAULT NULL,# 学校非标志性成果业绩分（本科）
	`s1_kpi_special_assignment` DOUBLE(10,4) DEFAULT NULL,# 学院专项（本科）
	`s1_kpi_shoulder_both`	DOUBLE(10,4) DEFAULT NULL,#	双肩挑
	`s1_kpi_postgraduate` DOUBLE(10,4) DEFAULT NULL,# 研究生绩点 这是另外一张表的
	
	`s1_kpi` DOUBLE(10,4) NOT NULL, # 标准课时
	`s1_score` DOUBLE(10,2) NOT NULL, # S1分数
	
	PRIMARY KEY (`s1_detail_id`),
	UNIQUE KEY `uk_s1_detail_tid_year` (`s1_detail_teacher_name`,`s1_detail_year`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

## -- S1 工作量记录 所有表一率存储标准课时 而不是什么业绩点 原表是 你也存课时
DROP TABLE IF EXISTS `s_detail`;
CREATE TABLE `s_detail`(
	`s_detail_id` INT(8) AUTO_INCREMENT NOT NULL, 
	`s_detail_teacher_id` INT(8) DEFAULT NULL,  
	`s_detail_teacher_name` VARCHAR(24) NOT NULL, # 怪怪的老师名字都来了。。有些没有id 暂时 还是先登记在案吧
	`s_detail_year` SMALLINT(4) UNSIGNED NOT NULL,
	
	`s1_kpi` DOUBLE(10,4) NOT NULL, # 标准课时
	`s1_score` DOUBLE(10,2) NOT NULL, # S1分数
	
	`s2_score1` DOUBLE(10,2) NOT NULL,# 上上学期学评教分数
	`s2_score2` DOUBLE(10,2) NOT NULL,# 上学期学评教分数
	`s2_score_avg` DOUBLE(10,2) NOT NULL, # 平均 注意 如果只有一个有的 就认为这个为avg
	`s2_rank` SMALLINT(4) UNSIGNED NOT NULL, # 平均分的排名 调两个学期的所有数据 算出老师平均分 然后再排名 说白了之前表里边的排名没有意义 单学期有啥用
	`s2_score` DOUBLE(10,2) NOT NULL, 
	
-- 	JSON name:value 311:10
-- 导出的时候 将detail转为两个形式 
-- 用于导出表List<List<String>> 只有数据 表头List<string>另外从rule导入
-- 用于前端 利用index相同的特性 把rule数组和数据结合使用！拼接为detail!
	
	`s3_data` VARCHAR(100) DEFAULT NULL,
	`s4_data` VARCHAR(100) DEFAULT NULL,
	
	`s3_score` DOUBLE(10,2) NOT NULL, 
	`s4_score` DOUBLE(10,2) NOT NULL, 

	`s_score` DOUBLE(10,2) NOT NULL, 
	
	`s_note` VARCHAR(100) DEFAULT NULL,
	
	PRIMARY KEY (`s_detail_id`),
	UNIQUE KEY `uk_s_detail_tid_year` (`s_detail_teacher_name`,`s_detail_year`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;


## 计算后结果按照排名顺序存储 导出自然也是那个顺序！
DROP TABLE IF EXISTS `s_final`;
CREATE TABLE `s_final`(
	`s_final_id` INT(8) AUTO_INCREMENT NOT NULL, 
  `s_final_teacher_name` VARCHAR(25) NOT NULL, # 教师名字
	`s_final_teacher_id` INT(8) NOT NULL, 
	`s_final_score` DOUBLE(10,2) DEFAULT NULL, # 考核总分 历史数据自然按历史数据来！
	`s_final_year` INT(4) NOT NULL,
	`s_final_grade` CHAR(1) DEFAULT NULL, # 考核等级
	`s_final_title_level` TINYINT(1) UNSIGNED DEFAULT NULL, # 当年的职称！
	`s_final_course_main` INT(8) DEFAULT NULL,# 承担主讲课程学时数是否不低于64学时 主讲课程学时？
	`s_final_note` VARCHAR(32) DEFAULT NULL, # 教科办说明情况
-- 	`s_rank` SMALLINT(4) UNSIGNED DEFAULT NULL, # 不一定参与评价 所以这个rank没意义 我们只能提供参考罢了
	PRIMARY KEY (`s_final_id`), # 这里的teacherId值得相信！
	UNIQUE KEY `uk_s_final_tid_year` (`s_final_year`,`s_final_teacher_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

# academy 学院
DROP TABLE IF EXISTS `academy`;
CREATE TABLE `academy` (
  `academy_id` TINYINT(1) UNSIGNED NOT NULL AUTO_INCREMENT,
  `academy_name` VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (`academy_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;



INSERT academy (`academy_name`) 
VALUES
  (
    "电子信息学院（微电子学院）"
  ) ;



-- 先找当年的规则有没有 没有就找最近的规则
-- 到时候统计 聚合起来作为一个json扔到s3Details
-- 读取 显示的时候 则是 311:教学成果奖
-- DROP TABLE IF EXISTS `rule_s3s4`;
-- CREATE TABLE `rule_s3s4`(
-- 	`rule_s3s4_id` TINYINT(4) AUTO_INCREMENT NOT NULL, 
-- 	`rule_s3s4_year` SMALLINT(4) UNSIGNED DEFAULT NULL,
-- 	`rule_s3s4_json` VARCHAR(200) NOT NULL,
-- 	PRIMARY KEY (`rule_s3s4_id`)
-- )ENGINE=INNODB DEFAULT CHARSET=utf8mb4;


-- INSERT T(Tid,Tpwd,Tname)
-- VALUES("00001","she_maybe","闪闪兔");

-- select ifnull((select account_id from account where account_name = 'ff'), 'fuck')
-- 
-- INSERT INTO course ( course_num, course_term, course_time, course_name, course_address, course_teacher_id, course_teacher_name, course_type, course_capacity, course_capacity_factor1, course_hours, course_hours_theory, course_hours_exp, course_hours_exp_std, course_hours_theory_std, course_hours_std, course_reform, course_factor, course_prior, course_note1, course_note2 ) VALUES (
-- (2018-2019-2)-A1804020-40136-1, 2018-2019-2, 周二第3,4,5节{第1-16周}, 模拟电子电路, 第7教研楼南205, 40136, 刘圆圆, 1, 19, 1.0, 64.0, 48.0, 16.0, 22, 67, 90, 翻转1.4；卓越1.3, 1.4, 1.4, 卓越单独，翻转课堂，翻转核算高，依照翻转课堂进行核算, 系数不叠加，按最高计算)