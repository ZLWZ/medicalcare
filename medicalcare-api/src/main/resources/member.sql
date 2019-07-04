/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.62-log : Database - medicalcare
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`medicalcare` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `medicalcare`;

/*Table structure for table `apply` */

DROP TABLE IF EXISTS `apply`;

CREATE TABLE `apply` (
  `aid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `aname` varchar(50) NOT NULL COMMENT '药品名',
  `atype` int(11) NOT NULL COMMENT '类别',
  `sid` int(11) NOT NULL COMMENT '规格',
  `num` int(11) NOT NULL COMMENT '申请数量',
  `did` int(11) NOT NULL COMMENT '申请科室',
  `mkdate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '申请时间',
  `outdate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '处理时间',
  `state` int(11) NOT NULL COMMENT '状态',
  `xnum` int(11) DEFAULT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='药物申请表';

/*Data for the table `apply` */

insert  into `apply`(`aid`,`aname`,`atype`,`sid`,`num`,`did`,`mkdate`,`outdate`,`state`,`xnum`) values (1,'复方珍珠暗疮片',1,8,30,2,'2019-07-02 09:23:40','0000-00-00 00:00:00',1,30),(2,'安神补脑液',2,2,30,4,'2019-07-02 14:36:15','0000-00-00 00:00:00',1,30),(3,'调经丸',1,3,0,1,'2019-07-02 17:49:43','2019-07-03 15:20:51',2,50),(6,'银翘解毒颗粒',2,5,0,1,'2019-07-03 17:02:56','2019-07-03 17:21:22',2,5);

/*Table structure for table `company` */

DROP TABLE IF EXISTS `company`;

CREATE TABLE `company` (
  `cid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cname` varchar(100) NOT NULL COMMENT '厂房名称',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='厂家表';

/*Data for the table `company` */

insert  into `company`(`cid`,`cname`) values (1,'北京同仁堂股份有限公司'),(2,'四川德元药业集团有限公司'),(3,'江中药业股份有限公司'),(4,'昆明中药厂有限公司'),(5,'沈阳东新药业有限公司'),(6,'敦化市汇吉参茸制品工坊'),(7,'广州敬修药业股份有限公司'),(8,'广州星群药业股份有限公司');

/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `did` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dname` varchar(50) NOT NULL COMMENT '科室名称',
  PRIMARY KEY (`did`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='科室表';

/*Data for the table `department` */

insert  into `department`(`did`,`dname`) values (1,'妇科'),(2,'儿科'),(3,'急诊科'),(4,'精神科'),(5,'骨科');

/*Table structure for table `details` */

DROP TABLE IF EXISTS `details`;

CREATE TABLE `details` (
  `did` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dname` varchar(50) NOT NULL COMMENT '轮班时段',
  PRIMARY KEY (`did`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='轮班时段表';

/*Data for the table `details` */

insert  into `details`(`did`,`dname`) values (1,'日班 08：00-16：00'),(2,'小夜 16：00-24：00'),(3,'大夜 24：00-08：00');

/*Table structure for table `dosage` */

DROP TABLE IF EXISTS `dosage`;

CREATE TABLE `dosage` (
  `did` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dname` varchar(100) NOT NULL COMMENT '剂型名称',
  PRIMARY KEY (`did`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='剂型表';

/*Data for the table `dosage` */

insert  into `dosage`(`did`,`dname`) values (1,'固体剂型'),(2,'口服液'),(3,'丸剂'),(4,'片剂'),(5,'外用液体剂');

/*Table structure for table `drugs` */

DROP TABLE IF EXISTS `drugs`;

CREATE TABLE `drugs` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dname` varchar(50) NOT NULL COMMENT '药品名',
  `dtype` int(11) NOT NULL COMMENT '类别',
  `num` int(11) NOT NULL COMMENT '药房库存数量',
  `money` double NOT NULL COMMENT '单价',
  `kstate` int(11) NOT NULL COMMENT '出售状态',
  `sid` int(11) NOT NULL COMMENT '规格',
  `did` int(11) NOT NULL COMMENT '剂型',
  `cid` int(11) NOT NULL COMMENT '厂家',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='药房药品表';

/*Data for the table `drugs` */

insert  into `drugs`(`id`,`dname`,`dtype`,`num`,`money`,`kstate`,`sid`,`did`,`cid`) values (1,'五味子',1,48,29.9,1,1,1,3),(2,'益肾乌发口服液',2,80,108.9,1,2,2,1),(3,'调经丸',1,24,15,1,3,3,2),(4,'坤宝丸',2,8,18,1,4,3,4),(5,'复方土槿皮酊',1,20,13,1,8,5,5),(6,'藿香正气水',2,10,3,1,2,2,6),(7,'复方珍珠暗疮片',1,7,4,1,8,5,5),(8,'健胃消食片',2,8,22,1,6,4,6),(9,'金花消痤丸',1,81,45,1,5,3,8),(10,'安神补脑液',2,7,52,1,2,2,7),(11,'小儿麻痹片',2,10,20,1,6,1,5),(16,'银翘解毒颗粒',2,5,30,1,5,1,8);

/*Table structure for table `education` */

DROP TABLE IF EXISTS `education`;

CREATE TABLE `education` (
  `eid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ename` varchar(50) NOT NULL COMMENT '学历名称',
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='学历表';

/*Data for the table `education` */

insert  into `education`(`eid`,`ename`) values (1,'中专学历'),(2,'专科学历'),(3,'本科学历'),(4,'硕士学位'),(5,'博士学位');

/*Table structure for table `information` */

DROP TABLE IF EXISTS `information`;

CREATE TABLE `information` (
  `uid` varchar(50) NOT NULL COMMENT '添加信息',
  `leavedate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '离职日期',
  `details` varchar(200) DEFAULT NULL COMMENT '离职原因'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员流动记录信息表';

/*Data for the table `information` */

insert  into `information`(`uid`,`leavedate`,`details`) values ('060212','0000-00-00 00:00:00',NULL),('060300','0000-00-00 00:00:00',NULL),('060301','0000-00-00 00:00:00',NULL),('060302','0000-00-00 00:00:00',NULL),('060303','0000-00-00 00:00:00',NULL),('060304','0000-00-00 00:00:00',NULL),('060305','0000-00-00 00:00:00',NULL),('060306','0000-00-00 00:00:00',NULL),('060307','0000-00-00 00:00:00',NULL),('060308','0000-00-00 00:00:00',NULL),('060309','0000-00-00 00:00:00',NULL),('060310','0000-00-00 00:00:00',NULL),('060311','0000-00-00 00:00:00',NULL),('060312','0000-00-00 00:00:00',NULL),('060313','0000-00-00 00:00:00',NULL),('116787','0000-00-00 00:00:00',NULL),('475365','0000-00-00 00:00:00',NULL),('694140','2019-07-02 17:46:09','继承地'),('233553','2019-06-25 15:32:56','回家继承几百亩的地');

/*Table structure for table `kcdrugs` */

DROP TABLE IF EXISTS `kcdrugs`;

CREATE TABLE `kcdrugs` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dname` varchar(50) NOT NULL COMMENT '药品名',
  `num` int(11) NOT NULL COMMENT '药库存数量',
  `ktype` int(11) NOT NULL COMMENT '类别',
  `cid` int(11) NOT NULL COMMENT '厂家',
  `sid` int(11) NOT NULL COMMENT '规格',
  `did` int(11) NOT NULL COMMENT '剂型',
  `money` double NOT NULL COMMENT '单价',
  `mkdate` date NOT NULL DEFAULT '0000-00-00' COMMENT '生产日期',
  `joindate` date NOT NULL DEFAULT '0000-00-00' COMMENT '进药日期',
  `leavedate` date NOT NULL DEFAULT '0000-00-00' COMMENT '过期日期',
  `details` varchar(100) DEFAULT NULL COMMENT '描述',
  `uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='药库药品表';

/*Data for the table `kcdrugs` */

insert  into `kcdrugs`(`id`,`dname`,`num`,`ktype`,`cid`,`sid`,`did`,`money`,`mkdate`,`joindate`,`leavedate`,`details`,`uid`) values (1,'头孢克肟片',400,2,5,7,4,41,'2018-07-29','2018-06-29','2019-06-29',NULL,60212),(2,'盐酸二甲酸弧缓释片',200,2,4,6,4,29,'2018-07-29','2018-07-01','2019-06-15',NULL,60212),(3,'五味子',150,1,1,1,1,29.9,'2019-01-23','2019-01-01','2019-07-01',NULL,60212),(4,'益肾乌发口服液',100,2,1,2,2,108.9,'2019-06-05','2019-06-01','2020-06-01',NULL,60212),(5,'调经丸',35,1,2,3,3,15,'2018-07-29','2018-05-30','2019-06-26',NULL,60212),(6,'坤宝丸',50,2,4,4,3,18,'2019-01-23','2019-01-01','2019-06-23',NULL,60212),(7,'复方土槿皮酊',100,1,5,8,5,13,'2019-01-01','2018-12-28','2019-06-28',NULL,60212),(8,'藿香正气水',70,2,6,2,2,3,'2018-07-29','2018-06-29','2019-06-25',NULL,60212),(9,'复方珍珠暗疮片',65,1,5,8,5,4,'2019-02-27','2019-02-15','2019-06-23',NULL,60212),(10,'健胃消食片',20,2,6,6,4,22,'2019-03-15','2019-03-01','2019-06-16',NULL,60212),(11,'金花消痤丸',30,1,8,5,3,45,'2019-04-12','2019-04-10','2019-07-29',NULL,60212),(12,'安神补脑液',20,2,7,2,2,52,'2019-05-16','2019-05-15','2020-06-15',NULL,60212),(27,'小儿麻痹片',10,2,3,6,4,20,'2019-07-02','2019-07-03','2019-07-04',NULL,60300),(28,'银翘解毒颗粒',47,2,5,5,1,30,'2019-07-02','2019-07-03','2019-07-06',NULL,60300);

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `mid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mname` varchar(25) NOT NULL COMMENT '菜单名称',
  `link` varchar(20) DEFAULT NULL,
  `parend` int(11) DEFAULT NULL,
  `icon` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='菜单表';

/*Data for the table `menu` */

insert  into `menu`(`mid`,`mname`,`link`,`parend`,`icon`) values (1,'人员管理',NULL,0,'el-icon-user-solid'),(2,'医生诊断','doctor',0,'el-icon-user'),(3,'前台收银',NULL,0,'el-icon-coin'),(4,'药房管理',NULL,0,'el-icon-first-aid-kit'),(5,'药库管理',NULL,0,'el-icon-s-home'),(6,'员工管理','infoManager',1,NULL),(7,'值班管理','duty',1,NULL),(8,'挂号预约','registerVisi',3,NULL),(9,'挂号查询','registerInfo',3,NULL),(10,'处理药方','prescript',4,NULL),(11,'药品查询','inquiries',4,NULL),(12,'申请补给','replenishment',4,NULL),(13,'药库出药','delivery',5,NULL),(14,'药库查询','storehouse',5,NULL),(15,'进药记录','waste',5,NULL),(16,'系统管理',NULL,0,'el-icon-s-tools'),(17,'角色管理','jurisdiction',16,NULL),(18,'离职记录','resign',1,NULL);

/*Table structure for table `menu_role` */

DROP TABLE IF EXISTS `menu_role`;

CREATE TABLE `menu_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mid` int(11) NOT NULL COMMENT '菜单标识',
  `rid` int(11) NOT NULL COMMENT '角色标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COMMENT='菜单-角色表';

/*Data for the table `menu_role` */

insert  into `menu_role`(`id`,`mid`,`rid`) values (1,1,1),(2,2,1),(3,3,1),(4,4,1),(5,5,1),(9,3,4),(10,4,5),(11,5,6),(15,1,2),(16,2,2),(17,3,2),(18,4,2),(19,5,2),(20,6,2),(21,7,2),(22,8,2),(23,9,2),(24,10,2),(25,11,2),(26,12,2),(27,13,2),(28,14,2),(29,15,2),(33,16,2),(34,17,2),(35,18,2),(36,6,1),(37,7,1),(38,18,1),(39,8,1),(40,9,1),(41,10,1),(42,11,1),(43,12,1),(44,13,1),(45,14,1),(46,15,1),(47,10,5),(48,11,5),(49,12,5),(50,5,5),(51,13,5),(52,14,5),(53,15,5),(54,16,1),(55,17,1);

/*Table structure for table `parti` */

DROP TABLE IF EXISTS `parti`;

CREATE TABLE `parti` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `pname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `parti` */

insert  into `parti`(`pid`,`pname`) values (1,'上班'),(2,'休息'),(3,'请假'),(4,'迟到'),(5,'早退'),(6,'0');

/*Table structure for table `pregdetils` */

DROP TABLE IF EXISTS `pregdetils`;

CREATE TABLE `pregdetils` (
  `rid` varchar(50) DEFAULT NULL,
  `rdname` varchar(50) NOT NULL COMMENT '药品名',
  `number` int(11) NOT NULL COMMENT '药品数量',
  `dmoney` double NOT NULL COMMENT '单价',
  `zmoney` double NOT NULL COMMENT '总价',
  `did` int(11) NOT NULL COMMENT '药品编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预处理药方详情表';

/*Data for the table `pregdetils` */

insert  into `pregdetils`(`rid`,`rdname`,`number`,`dmoney`,`zmoney`,`did`) values ('190602095021','益肾乌发口服液',10,108.9,1089,2),('190602095021','金花消痤丸',10,45,450,9),('190602143025','五味子',1,29.9,29.9,1),('190602143025','坤宝丸',1,18,18,4),('190602143025','藿香正气水',1,3,3,6),('190702174638','五味子',3,29.9,89.7,1),('190702174638','调经丸',1,15,15,3);

/*Table structure for table `procdetils` */

DROP TABLE IF EXISTS `procdetils`;

CREATE TABLE `procdetils` (
  `rid` varchar(50) DEFAULT NULL,
  `rdname` varchar(50) NOT NULL COMMENT '药品名',
  `number` int(11) NOT NULL COMMENT '药品数量',
  `dmoney` double NOT NULL COMMENT '单价',
  `zmoney` double NOT NULL COMMENT '总价',
  `did` int(11) NOT NULL COMMENT '药品编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='已处理药方详情表';

/*Data for the table `procdetils` */

insert  into `procdetils`(`rid`,`rdname`,`number`,`dmoney`,`zmoney`,`did`) values ('190602095021','益肾乌发口服液',10,108.9,1089,2),('190602095021','金花消痤丸',9,45,405,9),('190702174638','五味子',3,29.9,89.7,1),('190702174638','调经丸',1,15,15,3);

/*Table structure for table `register` */

DROP TABLE IF EXISTS `register`;

CREATE TABLE `register` (
  `rid` varchar(50) NOT NULL COMMENT '主键',
  `rname` varchar(50) NOT NULL COMMENT '患者名',
  `idcard` varchar(18) NOT NULL COMMENT '患者身份证号',
  `did` int(11) NOT NULL COMMENT '科室',
  `uid` varchar(50) NOT NULL COMMENT '就诊医生',
  `redate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '就诊时间',
  `rmoney` double NOT NULL COMMENT '挂号费',
  `rprice` double DEFAULT NULL COMMENT '药方费',
  `rstatic` int(11) NOT NULL DEFAULT '1' COMMENT '药方处理状态',
  `pstate` int(11) DEFAULT '0',
  PRIMARY KEY (`rid`),
  UNIQUE KEY `idcard` (`idcard`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='挂号信息表';

/*Data for the table `register` */

insert  into `register`(`rid`,`rname`,`idcard`,`did`,`uid`,`redate`,`rmoney`,`rprice`,`rstatic`,`pstate`) values ('190602095021','王五','130481200908254512',4,'060307','2019-06-02 09:50:21',50,1494,2,1),('190602143025','李四','130481199712125624',5,'060308','2019-06-02 14:30:25',30,50.9,2,0),('190602213319','张三','130481201305258549',3,'060306','2019-06-02 21:33:19',25,0,1,0),('190626122911','赵六','130481199807259239',3,'060306','2019-06-26 12:29:11',35,0,1,0),('190627143256','田七','130481199807254632',3,'060306','2019-06-27 14:32:56',35,0,1,0),('190628215223','dada','130481199807258765',2,'060305','2019-06-28 21:52:23',30,0,3,0),('190630172704','黑八','130481199807254678',3,'060306','2019-06-30 17:27:04',35,0,1,0),('190702174638','的撒大','130481199804254512',2,'060305','2019-07-02 17:46:38',30,104.7,2,1);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `rid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rname` varchar(50) NOT NULL COMMENT '角色名称',
  `creation_time` datetime NOT NULL COMMENT '创建时间',
  `describe` varchar(100) NOT NULL DEFAULT '这个人很懒什么也没有说' COMMENT '描述',
  `change_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `role` */

insert  into `role`(`rid`,`rname`,`creation_time`,`describe`,`change_time`) values (1,'超级管理','2019-06-12 23:22:13','这个人很懒什么也没有说','2019-07-02 17:50:08'),(2,'科室主任','2019-06-12 23:22:13','这个人很懒什么也没有说','2019-06-25 15:14:21'),(3,'前台收银','2019-06-12 23:22:13','这个人很懒什么也没有说','2019-06-25 20:30:35'),(4,'药师','2019-06-12 23:22:13','这个人很懒什么也没有说','2019-06-12 23:22:13'),(5,'药库管理','2019-06-12 23:22:13','这个人很懒什么也没有说','2019-06-25 18:01:15'),(6,'医生','2019-06-12 23:22:13','这个人很懒什么也没有说','2019-06-12 23:22:13'),(7,'护士','2019-06-12 23:22:13','这个人很懒什么也没有说','2019-06-12 23:22:13');

/*Table structure for table `shift` */

DROP TABLE IF EXISTS `shift`;

CREATE TABLE `shift` (
  `sid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sdate` varchar(10) NOT NULL COMMENT '轮班星期',
  `did` int(11) NOT NULL COMMENT '轮班时段',
  `uid` varchar(50) NOT NULL COMMENT '值班人信息',
  `pid` int(11) DEFAULT '6',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='轮班表';

/*Data for the table `shift` */

insert  into `shift`(`sid`,`sdate`,`did`,`uid`,`pid`) values (1,'2019-07-01',1,'060304',1),(2,'2019-07-01',1,'060309',6),(3,'2019-07-01',2,'060305',6),(4,'2019-07-01',2,'060310',6),(5,'2019-07-01',3,'060306',6),(6,'2019-07-01',3,'060311',6),(7,'2019-07-02',1,'060307',6),(8,'2019-07-02',1,'060312',6),(9,'2019-07-02',2,'060308',6),(10,'2019-07-02',2,'060313',6),(11,'2019-07-02',3,'060304',6),(12,'2019-07-02',3,'060309',6),(13,'2019-07-03',1,'060305',6),(14,'2019-07-03',1,'060310',6),(15,'2019-07-03',2,'060306',6),(16,'2019-07-03',2,'060311',6),(17,'2019-07-03',3,'060307',6),(18,'2019-07-03',3,'060312',6),(19,'2019-07-04',1,'060308',6),(20,'2019-07-04',1,'060313',6),(21,'2019-07-04',2,'060304',6),(22,'2019-07-04',2,'060309',6),(23,'2019-07-04',3,'060305',6),(24,'2019-07-04',3,'060310',6),(25,'2019-07-05',1,'060306',6),(26,'2019-07-05',1,'060311',6),(27,'2019-07-05',2,'060307',6),(28,'2019-07-05',2,'060312',6),(29,'2019-07-05',3,'060308',6),(30,'2019-07-05',3,'060313',6),(31,'2019-07-06',1,'060304',6),(32,'2019-07-06',1,'060309',6),(33,'2019-07-06',2,'060305',6),(34,'2019-07-06',2,'060310',6),(35,'2019-07-06',3,'060306',6),(36,'2019-07-06',3,'060311',6),(37,'2019-07-07',1,'060307',6),(38,'2019-07-07',1,'060312',6),(39,'2019-07-07',2,'060308',6),(40,'2019-07-07',2,'060313',6),(41,'2019-07-07',3,'060304',6),(42,'2019-07-07',3,'060309',6);

/*Table structure for table `specifi` */

DROP TABLE IF EXISTS `specifi`;

CREATE TABLE `specifi` (
  `sid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sname` varchar(100) NOT NULL COMMENT '规格名称',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='规格表';

/*Data for the table `specifi` */

insert  into `specifi`(`sid`,`sname`) values (1,'120g/瓶'),(2,'10ml*10支/盒'),(3,'9g*10丸/盒'),(4,'50粒*10袋/盒'),(5,'4g*9袋/盒'),(6,'0.5g*72片/盒'),(7,'0.3g*84片*1瓶/盒'),(8,'15ml*1瓶/盒');

/*Table structure for table `stock` */

DROP TABLE IF EXISTS `stock`;

CREATE TABLE `stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sname` varchar(50) NOT NULL COMMENT '药品名',
  `num` int(11) NOT NULL COMMENT '数量',
  `stype` int(11) NOT NULL COMMENT '类别',
  `mkdate` date NOT NULL DEFAULT '0000-00-00' COMMENT '生产日期',
  `leavedate` date NOT NULL DEFAULT '0000-00-00' COMMENT '过期日期',
  `joindate` date NOT NULL DEFAULT '0000-00-00' COMMENT '进药日期',
  `cid` int(11) NOT NULL COMMENT '厂家',
  `sid` int(11) NOT NULL COMMENT '规格',
  `did` int(11) NOT NULL COMMENT '剂型',
  `dmoney` double NOT NULL COMMENT '单价',
  `zmoney` double NOT NULL COMMENT '总价',
  `uid` varchar(50) NOT NULL COMMENT '仓管',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='药库进货记录表';

/*Data for the table `stock` */

insert  into `stock`(`id`,`sname`,`num`,`stype`,`mkdate`,`leavedate`,`joindate`,`cid`,`sid`,`did`,`dmoney`,`zmoney`,`uid`) values (3,'1',5,1,'2019-06-24','2021-06-20','2019-07-01',1,1,1,1,5,'060300'),(4,'1',1,1,'2019-06-24','2020-06-25','2019-07-01',1,1,1,1,1,'060300'),(5,'cc',1,1,'1970-01-01','1970-01-01','2019-07-02',1,1,1,1,1,'060300'),(6,'撒大大',1,1,'2019-07-01','2019-07-31','2019-07-02',6,6,2,1,1,'060300'),(7,'小儿麻痹片',30,2,'2019-07-02','2020-06-27','2019-07-03',5,6,1,20,600,'060300'),(8,'小儿麻痹片',20,2,'2019-07-02','2019-07-04','2019-07-03',3,6,4,20,400,'060300'),(9,'银翘解毒颗粒',100,2,'2019-07-02','2019-07-06','2019-07-03',5,5,1,30,3000,'060300'),(10,'银翘解毒片',2,2,'2019-07-02','2019-07-06','2019-07-03',8,5,1,30,60,'060300');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` varchar(50) NOT NULL COMMENT '主键唯一标识',
  `uname` varchar(20) NOT NULL COMMENT '名字',
  `acount` varchar(6) NOT NULL COMMENT '用户名',
  `address` text NOT NULL COMMENT '地址',
  `age` int(11) NOT NULL COMMENT '年龄',
  `sex` int(11) NOT NULL COMMENT '性别',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `idcard` varchar(18) NOT NULL COMMENT '身份证号',
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `joindate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '入职日期',
  `detials` varchar(200) DEFAULT NULL COMMENT '专业',
  `did` int(11) NOT NULL COMMENT '科室',
  `eid` int(11) DEFAULT NULL COMMENT '学历',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态',
  `portrait` varchar(255) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `acount` (`acount`),
  UNIQUE KEY `idcard` (`idcard`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `user` */

insert  into `user`(`uid`,`uname`,`acount`,`address`,`age`,`sex`,`password`,`idcard`,`phone`,`joindate`,`detials`,`did`,`eid`,`state`,`portrait`) values ('060212','乔振超','root','河北省邯郸市邯山区',18,1,'b1ba853525d0f30afe59d2d005aad96c','130481199807255365','15231093095','2019-06-02 19:00:00','人事管理专业',3,4,1,'QQ图片20190614234045.jpg'),('060300','赵浩浩','zhaohh','河北省邯郸市永年区',18,1,'61375c6f5888ee71defd2594aae6f3b6','130481199807257654','15263541254','2019-06-03 12:00:00','人事管理专业',3,1,1,'QQ图片20190614234045.jpg'),('060301','楚江涛','chujt','河北省邯郸市肥乡区',19,1,'d9bd28642d80b5c6afd253dfd51d1147','130481199807256359','15219466201','2019-06-04 13:00:00','金融管理',3,2,1,'QQ图片20190614234045.jpg'),('060302','万贵鹏','wangp','河北省邯郸市复兴区',22,1,'349a9ddab437d301ab5b9a2c4ddac4ee','130481199807257415','15895093095','2019-06-05 14:00:00','药学',3,4,1,'QQ图片20190614234045.jpg'),('060303','白雪城','baixc','河北省邯郸市邯山区',20,1,'019d0af1c57aef2431853ebc2fcc1804','130481199807253620','16321093095','2019-06-06 15:00:00','仓库管理专业',3,5,1,'QQ图片20190614234045.jpg'),('060304','吝江涛1','linjt1','河北省邯郸市峰峰矿区',18,1,'2495e1daf8bd49719602146d2adc9ad3','130481199807257561','15285233095','2019-06-07 16:00:00','临床医学',1,4,1,'QQ图片20190614234045.jpg'),('060305','吝江涛2','linjt2','河北省邯郸市峰峰矿区',18,1,'07aa2a98e41a7de6916875fa30709fd7','130481199807257541','15287453095','2019-06-07 16:30:00','临床医学',2,1,1,'QQ图片20190614234045.jpg'),('060306','吝江涛3','linjt3','河北省邯郸市峰峰矿区',18,1,'6fad54c7c6b12b4c26d9fa44f69b1439','130481199807253026','15286543095','2019-06-07 16:22:00','临床医学',3,2,1,'QQ图片20190614234045.jpg'),('060307','吝江涛4','linjt4','河北省邯郸市峰峰矿区',18,1,'be68a73ab08f781163929087e2ce422b','130481199807254515','15236523095','2019-06-07 16:40:00','心理学',4,5,1,'QQ图片20190614234045.jpg'),('060308','吝江涛5','linjt5','河北省邯郸市峰峰矿区',18,1,'1a05f406a976972ed853e2a1a00ab93f','130481199807258653','15285643095','2019-06-07 16:50:00','骨科',5,4,1,'QQ图片20190614234045.jpg'),('060309','罗震1','luoz1','河北省邯郸市永年区',19,1,'e8089154a9bb67ac19a293819ec7eda3','130481199807257450','13165453095','2019-06-08 17:10:00','护理专业',1,2,1,'QQ图片20190614234045.jpg'),('060310','罗震2','luoz2','河北省邯郸市永年区',19,1,'408f4b15bd7f70677db72545b9622337','130481199807256302','15885623095','2019-06-08 17:20:00','护理专业',2,2,1,'QQ图片20190614234045.jpg'),('060311','罗震3','luoz3','河北省邯郸市永年区',19,1,'561112f607af02bc45844bc944c7bbeb','130481199807257894','15645753095','2019-06-08 17:30:00','护理专业',3,2,1,'QQ图片20190614234045.jpg'),('060312','罗震4','luoz4','河北省邯郸市永年区',19,1,'2ef92155090f1f39826a27c746f304ce','130481199807258526','13456213095','2019-06-08 17:40:00','护理专业',4,2,1,'QQ图片20190614234045.jpg'),('060313','赵涵','zhaoh','河北省邯郸市永年区',19,2,'5e6608327ad72e9a7e1a08343263ce1b','130481199807259635','13648783095','2019-06-08 17:56:00','护理专业',5,3,1,'QQ图片20190614234045.jpg'),('116787','乔66','qiao66','河北邯郸磁县',21,1,'af10e6ef1a36b0044f9821660bbe34c3','130481199807258545','15201375625','2019-06-25 15:26:00','心理学',2,5,1,'QQ图片20190614234045.jpg'),('233553','sdaa','dasfa','北京东城区和平路',21,2,'5771759bb5a76bcde4d33e8561c9de72','130481199807258954','15234698524','2019-03-25 15:31:34','护理专业',4,3,3,'QQ图片20190614234045.jpg'),('475365','乔77','qiao77','河北省邯郸市邯山区',21,2,'8ea98fa686c826f20366712b97a8179d','130481199807256354','15878545236','2019-06-25 15:28:25','非人类研究学',4,5,1,'QQ图片20190614234045.jpg'),('694140','乔88','qiao88','河北省邯郸市武安市',21,2,'fcef24429cb4a25d5909de60c9726ec7','130481199807254632','15231469652','2019-06-25 15:29:28','心理学',2,4,3,'QQ图片20190614234045.jpg');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` varchar(50) NOT NULL COMMENT '用户标识',
  `rid` int(11) NOT NULL COMMENT '角色标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='用户-角色表';

/*Data for the table `user_role` */

insert  into `user_role`(`id`,`uid`,`rid`) values (1,'060304',6),(2,'060305',6),(3,'060306',6),(4,'060307',6),(5,'060308',6),(6,'060309',7),(7,'060310',7),(8,'060311',7),(9,'060312',7),(10,'060313',7),(11,'060212',1),(12,'060300',2),(13,'060301',3),(14,'060302',4),(15,'060303',5),(16,'116787',6),(17,'475365',6);

/* Trigger structure for table `user` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `information_trigger_insert` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `information_trigger_insert` AFTER INSERT ON `user` FOR EACH ROW begin
	insert into information(uid) values(new.uid);
end */$$


DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
