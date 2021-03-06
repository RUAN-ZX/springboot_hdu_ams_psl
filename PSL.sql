CREATE DATABASE IF NOT EXISTS `teacherData` DEFAULT CHARACTER SET utf8;

USE `teacherData`;

CREATE TABLE `D` (
  `Did` INT(2) NOT NULL AUTO_INCREMENT,
  `Dname` VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (`Did`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `T` (
  `Tid` CHAR(5) NOT NULL,
  `Tname` VARCHAR(25) DEFAULT NULL,
  `Tmail` VARCHAR(50) DEFAULT NULL,
  `Tpwd` VARCHAR(20) DEFAULT NULL,
  `TDid` INT(2) DEFAULT NULL,
  PRIMARY KEY (`Tid`),
  CONSTRAINT `tdid_did_fk` FOREIGN KEY (`TDid`) REFERENCES `D` (`Did`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


CREATE TABLE `C` (
  `Cid` INT(8) AUTO_INCREMENT NOT NULL,
  `CCid` CHAR(31) NOT NULL,
  `Cname` VARCHAR (20) DEFAULT NULL,
  `Cscore` INT (2) DEFAULT NULL,
  `Cparticipate` INT (4) DEFAULT NULL,
  `CTid` CHAR(5) NOT NULL,
  #  `Cname_1` VARCHAR(20) DEFAULT NULL,
  `Cscore_1` INT (2) DEFAULT NULL,
  #  `Cname_2` VARCHAR(20) DEFAULT NULL,
  `Cscore_2` INT (2) DEFAULT NULL,
  #  `Cname_3` VARCHAR(20) DEFAULT NULL,
  `Cscore_3` INT (2) DEFAULT NULL,
  #  `Cname_4` VARCHAR(20) DEFAULT NULL,
  `Cscore_4` INT (2) DEFAULT NULL,
  PRIMARY KEY (`Cid`),
  CONSTRAINT `ctid_tid_fk` FOREIGN KEY (`CTid`) REFERENCES `T` (`Tid`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 ;

CREATE TABLE `E` (
  `Eid` INT(8) AUTO_INCREMENT NOT NULL,

  `ETid` CHAR(5) DEFAULT NULL,

  `Etime` CHAR(6) DEFAULT NULL,
  `Eparticipate` INT(4) DEFAULT NULL,
  
  `Escore` INT(3) DEFAULT NULL,
  `Esrank` INT(3) DEFAULT NULL,
  `Eprank` INT(3) DEFAULT NULL,
  
  PRIMARY KEY (`Eid`),
  CONSTRAINT `etid_tid_fk` FOREIGN KEY (`ETid`) REFERENCES `T` (`Tid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `A` (
  `Aid` INT(8) AUTO_INCREMENT NOT NULL,

  `ATid` CHAR(5) DEFAULT NULL,

  `Atime` INT(4) DEFAULT NULL,
  `Aaccident` CHAR(1) DEFAULT "否",
  `Ahour` INT(3) DEFAULT NULL,
  
  `Ascore` VARCHAR(8) DEFAULT NULL,
  `Agrade` CHAR(1) DEFAULT NULL,
  `Ainfo` VARCHAR(40) DEFAULT NULL,  
  
  PRIMARY KEY (`Aid`),
  CONSTRAINT `atid_tid_fk` FOREIGN KEY (`ATid`) REFERENCES `T` (`Tid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


INSERT D(`Did`,`Dname`) VALUES("01", "电子信息学院（微电子学院）");

# select count(*) from c where `Cid`="(2019-2020-1)-B0405450-42119-1";

SELECT * FROM t WHERE `Tname`="WANG NINGNING（王宁宁）";

#select count(*) from a;