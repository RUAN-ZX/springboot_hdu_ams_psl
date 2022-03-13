CREATE DATABASE IF NOT EXISTS `teacher_data` DEFAULT CHARACTER SET utf8 ;

USE `teacher_data`;

# teacher 教师 
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `t_id` INT(8) NOT NULL,
  `t_name` VARCHAR(25) NOT NULL,
  `t_mail` VARCHAR(50) DEFAULT NULL,
	`t_phone` CHAR(11) DEFAULT NULL, # 13713524786
  `t_pwd` VARCHAR(20) DEFAULT NULL, 
  `t_ac_id` INT(2) DEFAULT 1, # 学院
	
	`t_team` varchar(30) DEFAULT NULL,
	`t_type` VARCHAR(10) DEFAULT NULL,  # 专职教师
	`t_title` VARCHAR(24) DEFAULT NULL, # 助理研究员（自然科学）
	`t_title_level` TINYINT(1) DEFAULT NULL, # 正高 副高 中级 初级 0 ~ 3
	
  PRIMARY KEY (`t_id`)
--   CONSTRAINT `tdid_did_fk` FOREIGN KEY (`TDid`) REFERENCES `D` (`Did`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;



## -------------课程 理论+实践 专门用于统计主讲课时的!
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `c_id` INT(8) AUTO_INCREMENT NOT NULL, 
  `c_cid` CHAR(31) NOT NULL, # '(2019-2020-1)-B0405450-42119-1
  `c_term` CHAR(11) NOT NULL, # 2019-2020-1
	`c_time` VARCHAR(60) NOT NULL, # 周三第10,11节{第1-17周|单周};周三第10,11节{第2-16周|双周}
  `c_name` VARCHAR(40) NOT NULL, # 物联网技术基础
	`c_addr` VARCHAR(60) NOT NULL, # 第6教研楼中305;第6教研楼中329
	
  `c_t_id` INT(8) NOT NULL, # 董林玺/刘超然 这种应该做拆分！凡是有（多人） 或者带斜杠的 都可以拆分 到时候显示那个老师的就好了 统计的话 按照学时的比例分成工作量！
	# 另外 如果有多行带有“多人” 那意思多个班 我们不用管 反正1、统计下边那些数据的标准学时 归到各个老师头上2、多人那边 第一行作为其他数据的填充！剩下重复的跳过 直到详情获取标准学时
	`c_t_name` VARCHAR(100) NOT NULL, # 记录一下所有的老师名字 仅作为记录而已
	
	
	`c_type` TINYINT(1) NOT NULL, # 根据从哪个表提取出来的 可以区分不同种类 理论 实验 短学期 毕设
	
  `c_capacity` INT(4) NOT NULL,
  `c_cap_factor_1` DOUBLE(10,2) DEFAULT 1.0, # double应该多少为好？ 班级规模系数 根据规模算出来的 不同性质的课 计算方法不同！
  `c_cap_factor_2` DOUBLE(10,2) DEFAULT 1.0, # 实验课才有2系数 理论课默认为1即可！


	`c_hours` DOUBLE(10,2) NOT NULL, # 老师有几几开的 所以会这样！
	`c_hours_thory` DOUBLE(10,2) NOT NULL, # 无论实验 还是讲课 还是标准化的学时 先算 然后几几开就好了！
	`c_hours_exp` DOUBLE(10,2) NOT NULL, # 注意 这里使用理论-实验就好了 
	`c_hours_exp_std` DOUBLE(10,2) NOT NULL, # 理论课标准课时 直接×1
	#实验课 同 标准学时计算方法 这也就是总的标准课时
	`c_hours_theory_std` DOUBLE(10,2) NOT NULL, # 理论课标准课时有公式 注意 不是两个std加起来就是总标准课时！！ 类别系数 × 规模系数 × 课时
  `c_hours_std` DOUBLE(10,2) NOT NULL, # 总标准课时	除了理论学时哪个算法以外 再乘上优课优测！
	
	
  `c_bilingual` VARCHAR(10) DEFAULT NULL, # 匹配到数字就作为系数乘出来计算就好了 但是内容保留 省的还要存对应 status——code对应哪个项目 太麻烦了 存数字主要为了排序 查找方便罢了 不常用的话根本么必要
	`c_reform` VARCHAR(10) DEFAULT NULL, # 同理上边！ 这两个一般来说取高作为类别系数 但不一定 实际计算以类别系数为准
	# 普通课叫课改系数 
	`c_factor` DOUBLE(10,2) DEFAULT 1.0, # 类别系数 直接爬 直接用来算就好了！
	`c_prior` DOUBLE(10,2) DEFAULT 1.0, # 优课优酬 短学期没有
	
	
	`c_hours_op` DOUBLE(10,2) DEFAULT NULL, # 上机学时 记录一下 不参与计算
	`c_points` DOUBLE(10,2) DEFAULT NULL, # 学分 记录一下 不参与计算
	`c_properties` CHAR(1) DEFAULT NULL, # 性质 实验课有
	`c_note_1` VARCHAR(100) DEFAULT NULL, # 限定50字符
  `c_note_2` VARCHAR(100) DEFAULT NULL,
	
	
  PRIMARY KEY (`c_id`)
--   CONSTRAINT `ctid_tid_fk` FOREIGN KEY (`CTid`) REFERENCES `T` (`Tid`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 ;

## -------------课程 理论+实践 专门用于统计主讲课时的!
DROP TABLE IF EXISTS `short_term`;
CREATE TABLE `short_term` (
  `st_id` INT(8) AUTO_INCREMENT NOT NULL, 
  `st_cid` CHAR(31) NOT NULL, # '(2019-2020-1)-B0405450-42119-1
  `st_term` CHAR(11) NOT NULL, # 2019-2020-1
	`st_time` VARCHAR(40) NOT NULL, # 第19周/周五/8:20

  `st_name` VARCHAR(40) NOT NULL, # 项目:电子线路仿真技术与PCB设计
	`st_addr` VARCHAR(60) NOT NULL, # 第8教研楼302（线路实习实验室）

	
  `st_t_id` INT(8) NOT NULL, # 董林玺/刘超然 这种应该做拆分！凡是有（多人） 或者带斜杠的 都可以拆分 到时候显示那个老师的就好了 统计的话 按照学时的比例分成工作量！
	# 另外 如果有多行带有“多人” 那意思多个班 我们不用管 反正1、统计下边那些数据的标准学时 归到嗝嗝老师头上2、多人那边 第一行作为其他数据的填充！剩下重复的跳过 直到详情获取标准学时
	`st_t_name` VARCHAR(100) NOT NULL, # 记录一下所有的老师名字 仅作为记录而已
	
  `st_capacity` INT(4) NOT NULL, # 已选人数
  `st_cap_factor` DOUBLE(10,2) DEFAULT NULL, 
	`st_reform` DOUBLE(10,2) DEFAULT 1.0, # 教改系数 
	`st_factor` DOUBLE(10,2) DEFAULT 1.0, # 类别系数 直接爬 直接用来算就好了！
	
	`st_hours` DOUBLE(10,2) NOT NULL, # 老师有几几开的 所以会这样！
  `st_hours_std` DOUBLE(10,2) NOT NULL, # 总标准课时 cf * re * fa * hr
	
	`st_properties` CHAR(1) DEFAULT NULL, # 短学期的性质
	`st_note_1` VARCHAR(100) DEFAULT NULL, # 限定50字符
  `st_note_2` VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (`st_id`)
--   CONSTRAINT `ctid_tid_fk` FOREIGN KEY (`CTid`) REFERENCES `T` (`Tid`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 ;

# 毕设学生表
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
	stu_id INT(8) NOT NULL, # 毕设学生的id
	stu_name VARCHAR(25) NOT NULL, # 毕设学生名字
	stu_major VARCHAR(60) NOT NULL,#光电信息科学与工程(光电工程方向)
	PRIMARY KEY(`stu_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


# 毕业设计
DROP TABLE IF EXISTS `thesis_design`;
CREATE TABLE `thesis_design` (
	td_id INT(8) AUTO_INCREMENT NOT NULL, 
	td_year INT(4) NOT NULL,# 每年都有一批！
	td_note VARCHAR(100) DEFAULT NULL,# 备注
	td_t_id INT(8) NOT NULL,# 教师id 方便查询。
	td_t_name VARCHAR(25) DEFAULT NULL, # 方便显示！
-- 	td_stu_id INT(8) NOT NULL, # 毕设学生的id
	td_stu_name VARCHAR(25) DEFAULT NULL, # 可能是卓越加点空记录
	
	td_grade TINYINT(1) NOT NULL, # 0 1 2 3 4 优秀 良好 中等 及格 不及格 这个需要统计 所以用数字！
	td_factor_1 DOUBLE(10,2) DEFAULT 12.0, # 基本系数
	td_factor_2 DOUBLE(10,2) DEFAULT 0.0, # 优秀系数
	td_t1 DOUBLE(10,2) DEFAULT 1.0, # T1系数 std = (f1+f2)*t1
	td_std DOUBLE(10,2) NOT NULL, # 标准学时 
	PRIMARY KEY(`td_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


# 研究生的绩点 
-- DROP TABLE IF EXISTS `post_graduate`;
-- CREATE TABLE `post_graduate`( 
-- 	`pg_id` INT(8) AUTO_INCREMENT NOT NULL, 
-- 	`pg_term` INT(4) NOT NULL,
-- 	`pg_t_id` INT(8) NOT NULL, 
-- 	`pg_gpa` DOUBLE(10,2) NOT NULL, # 研究生的绩点 以计算在老师头上的标准学时
-- 	`pg_std` DOUBLE(10,2) NOT NULL,
-- 	PRIMARY KEY(`pg_id`)
-- ) ENGINE=INNODB DEFAULT CHARSET=utf8;
-- 
## ------------- S2 学评教信息 直接有结果 直接用就好了
## 这里记录是学期结算的！只需要总分 按上一年学评教分数的平均值排名
DROP TABLE IF EXISTS `evaluation`;
CREATE TABLE `evaluation`(
  `e_id` INT(8) AUTO_INCREMENT NOT NULL, 
	`e_term` CHAR(11) NOT NULL,
	`e_t_id` INT(8) NOT NULL,
	`e_participate` INT(4) NOT NULL,
	`e_score` DOUBLE(10,3) NOT NULL,
	`e_srank` INT(4) NOT NULL, # 学校排名
	`e_arank` INT(4) NOT NULL, # 学院排名
	`e_result` DOUBLE(10,2) NOT NULL, # 排名占比！ 排名比
	
	PRIMARY KEY(`e_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

## ----------成果类
##  还有附带的 加分效果的录入 教科办老师只需要选择它的性质即可 余下自动加分 
## 针对多人成果 有占比 百分比 100就是单人项目了！ 可以修改这个占比 影响各个老师的分数！
## 然后有个属性name 记录所有登记的老师
DROP TABLE IF EXISTS `achievement`;
CREATE TABLE `achievement`(
  a_id INT(8) AUTO_INCREMENT NOT NULL, 
	a_t_id INT(8) NOT NULL,  # 当前老师！
	a_ratio INT(3) NOT NULL, # 占比 %
	
	a_t_name VARCHAR(200) NOT NULL,
	a_year INT(4) NOT NULL, # 学年成果
	a_name VARCHAR(150) NOT NULL, # 成果名称
	a_type VARCHAR(20) NOT NULL, # 成果类别 教学业绩核心指标目录与分值

	a_category VARCHAR(40) NOT NULL,# “考核项” 直接显示用的 不做计算 
	## 有些写的很长 厅局级教改项目、校教改研究重点项目/校教改研究一般项目
	
	a_evidence_1 VARCHAR(60) NOT NULL, # https:// 开头这个省略 后边的才用！ stea.ryanalexander.cn/psl/hdu1.jpg
	a_evidence_2 VARCHAR(60) DEFAULT NULL,
	a_evidence_3 VARCHAR(60) DEFAULT NULL,
	
	a_points_add VARCHAR(30) DEFAULT NULL, # 记录加分以及具体加了多少 加到哪里 s31_1:13:s1_1:0.5
	# 前端可以读取这玩意 显示在前端 然后改完数据 一并返回后端 形成这个字符串即可
	
	a_level TINYINT(1) DEFAULT NULL, # 获奖等级
	a_score DOUBLE(10,2) NOT NULL, # 最终指标分值
	a_note_1 VARCHAR(30) NOT NULL,# 原始备注 30个字！
	a_note_2 VARCHAR(30) NOT NULL,# 我们教科办备注
	PRIMARY KEY(`a_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;



# academy 学院
DROP TABLE IF EXISTS `acadamy`;
CREATE TABLE `acadamy` (
  `ac_id` INT(2) NOT NULL AUTO_INCREMENT,
  `ac_name` VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (`ac_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

## -- S1 工作量记录 生成表考这个了！
DROP TABLE IF EXISTS `s1`;
CREATE TABLE `s1`(
	`s1_id` INT(8) AUTO_INCREMENT NOT NULL, 
	`s1_t_id` INT(8) DEFAULT NULL,  
	`s1_year` INT(4) DEFAULT NULL,
	`s1_kpi_course` DOUBLE(10,4) DEFAULT NULL, # 通过四个理论 实验 短学期 毕设 搞出来的业绩点
	# 课外实践工作量
	`s1_kpi_practical_1` DOUBLE(10,4) DEFAULT NULL, #学校标志性成果业绩分（本科）	 	
	`s1_kpi_practical_2` DOUBLE(10,4) DEFAULT NULL,# 学校非标志性成果业绩分（本科）
	`s1_kpi_practical_3` DOUBLE(10,4) DEFAULT NULL,# 学院专项（本科）
	`s1_kpi_practical_4`	DOUBLE(10,4) DEFAULT NULL,#	双肩挑
	`s1_kpi_postgraduate` DOUBLE(10,4) DEFAULT NULL,# 研究生绩点 这是另外一张表的
	`s1_kpi` DOUBLE(10,4) DEFAULT NULL, # 所有业绩绩点加起来 *100 就是标准课时 
	`s1_hours_std` DOUBLE(10,2) DEFAULT NULL, # 标准学时
	# 给KPI排名 确定S1
	`s1_score` DOUBLE(10,2) DEFAULT NULL, # S1分数
	
	PRIMARY KEY (`s1_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

## S2 记录完各个学期的学评教evaluation 然后计算
DROP TABLE IF EXISTS `s2`;
CREATE TABLE `s2`(
	`s2_id` INT(8) AUTO_INCREMENT NOT NULL, 
	`s2_t_id` INT(8) DEFAULT NULL,   
	`s2_factor_1` DOUBLE(10,2) DEFAULT NULL,# 上上学期学评教分数
	`s2_factor_2` DOUBLE(10,2) DEFAULT NULL,# 上学期学评教分数
	`s2_factor` DOUBLE(10,2) DEFAULT NULL, # 平均
	`s2_rank` INT(8) DEFAULT NULL, # 平均分的排名
	`s2_score` DOUBLE(10,2) DEFAULT NULL, 
	
PRIMARY KEY (`s2_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

## 立即生效计算 Or 只有下载生成表的时候才会触发计算

## S3
DROP TABLE IF EXISTS `s3`;
CREATE TABLE `s3`(
	`s3_id` INT(8) AUTO_INCREMENT NOT NULL, 
	`s3_t_id` INT(8) DEFAULT NULL,   
	`s31_1` DOUBLE(10,2) DEFAULT NULL,# 学科竞赛
	`s31_2` DOUBLE(10,2) DEFAULT NULL,# 其他省级比赛
	
	`s32_1` DOUBLE(10,2) DEFAULT NULL,# 教学成果奖
	`s32_2` DOUBLE(10,2) DEFAULT NULL,# 教学名师奖
	`s32_3` DOUBLE(10,2) DEFAULT NULL,# 其它教学奖励
	`s32_4` DOUBLE(10,2) DEFAULT NULL,# 教学技能奖
	`s32_5` DOUBLE(10,2) DEFAULT NULL,# 教学事故

	`s3_score` DOUBLE(10,2) DEFAULT NULL, 
	
PRIMARY KEY (`s3_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

## S4 还是尽量手动录入比较好 或者excel写清楚项目 然后字符串匹配！
DROP TABLE IF EXISTS `s4`;
CREATE TABLE `s4`(
	`s4_id` INT(8) AUTO_INCREMENT NOT NULL, 
	`s4_t_id` INT(8) DEFAULT NULL,   
	`s41_1` DOUBLE(10,2) DEFAULT NULL,# 教改项目

	`s41_2` DOUBLE(10,2) DEFAULT NULL,# 实验教学示范中心建设项目

	
	`s41_3` DOUBLE(10,2) DEFAULT NULL,# 教学团队

	`s42_1` DOUBLE(10,2) DEFAULT NULL,# 专业建设

	`s42_2` DOUBLE(10,2) DEFAULT NULL,# 课程建设

	`s42_3` DOUBLE(10,2) DEFAULT NULL,# 教材建设

	`s43_1` DOUBLE(10,2) DEFAULT NULL,# 公开发表论文

	`s4_score` DOUBLE(10,2) DEFAULT NULL, 
	
PRIMARY KEY (`s4_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;



## 最终kpi结果记录
DROP TABLE IF EXISTS `s`;
CREATE TABLE `s`(
	`s_id` INT(8) AUTO_INCREMENT NOT NULL, 
	`s_t_name` VARCHAR(25) NOT NULL, # 教师名字 冗余一下挺好的！
	`s_t_id` INT(8) NOT NULL, 
	`s_score` DOUBLE(10,2) DEFAULT NULL, # 考核分数 s1 + 2 + 3 + 4
	`s_year` INT(4) DEFAULT NULL,
	`s_grade` CHAR(1) DEFAULT NULL, # 考核等级
	`s_title_level` TINYINT(1) DEFAULT NULL, # 当年的职称！
	`s_hours_all` INT(8) DEFAULT NULL,# 承担主讲课程学时数是否不低于64学时 主讲课程学时？
	
	
	PRIMARY KEY (`s_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;



INSERT acadamy (`ac_name`) 
VALUES
  (
    "电子信息学院（微电子学院）"
  ) ;

-- INSERT T(Tid,Tpwd,Tname)
-- VALUES("00001","she_maybe","闪闪兔");
