-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: music
-- ------------------------------------------------------
-- Server version	5.7.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL,
  `privilege_level` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='后台管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','123',3),(2,'scalpel','122500',2),(3,'mzj','123',1),(4,'super','123',4);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '逐渐',
  `uploader_id` int(11) DEFAULT NULL COMMENT '上传者id',
  `name` varchar(255) DEFAULT NULL,
  `introduction` varchar(255) DEFAULT NULL COMMENT '上传文件原因简介',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '文件审核状态（0未审核，1：一级审核通过，-1：一级审核未通过，2：二级审核通过，-2：二级审核未通过）',
  `first_reason` text COMMENT '一级审核通过或者未通过原因',
  `second_reason` text COMMENT '二级审核者审核结果原因',
  `begin_time` varchar(255) DEFAULT NULL,
  `end_time` varchar(255) DEFAULT NULL,
  `begin_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL COMMENT '文件地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='歌曲\r\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
INSERT INTO `file` VALUES (37,19,'mzj-1.txt','没啥事','2021-03-02 14:02:54','2021-03-02 14:20:58',0,'暂无','暂无',NULL,NULL,NULL,NULL,'/publicFile/1614665838083ajfif.jpg'),(43,24,'mzj-songDefault.jpg','试验一下','2021-03-02 20:22:57','2021-03-02 20:22:57',0,'暂无','暂无','14:00','16:00','2021-03-04 00:00:00','2021-03-12 00:00:00','/publicFile/1614687776518songDefault.jpg'),(44,24,'mzj-二叉树的基本操作和遍历操作.txt','再试一下','2021-03-02 21:05:37','2021-03-02 21:36:11',0,'暂无','暂无','13：00','15：00','2021-03-04 00:00:00','2021-03-06 00:00:00','/publicFile/1614692170191二叉树的基本操作和遍历操作.txt'),(45,14,'林坚-src=http___c-ssl.duitang.com_uploads_item_201905_20_20190520072336_EXfjS.thumb.700_0.jpeg&refer=http___c-ssl.duitang.jpg','svbadas','2021-03-11 14:43:31','2021-03-11 14:43:31',0,'暂无','暂无','dasdas','dasdas','2021-02-15 00:00:00','2020-11-29 00:00:00','/publicFile/1615445011305src=http___c-ssl.duitang.com_uploads_item_201905_20_20190520072336_EXfjS.thumb.700_0.jpeg&refer=http___c-ssl.duitang.jpg');
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uploader`
--

DROP TABLE IF EXISTS `uploader`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `uploader` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '上传者名字',
  `study_number` varchar(255) DEFAULT NULL COMMENT '学号',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别（1男0女）',
  `pic` varchar(255) NOT NULL COMMENT '头像',
  `academy` varchar(255) DEFAULT NULL COMMENT '歌手简介',
  `major` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='歌手';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uploader`
--

LOCK TABLES `uploader` WRITE;
/*!40000 ALTER TABLE `uploader` DISABLE KEYS */;
INSERT INTO `uploader` VALUES (14,'林坚','24320182203232',1,'/img/uploaderPic/1614576412554songListDefault.jpg','信息学院','undefined'),(17,'马致静','24320182203232',0,'/img/uploaderPic/1614576409308u=380507774,2154400897&fm=26&gp=0.jpg','信息学院','软件工程'),(23,'admin','dfs',1,'/img/uploaderPic/default.jpg','dsaf','dsf'),(24,'mzj','dsf',0,'/img/uploaderPic/default.jpg','dsf','sdaf');
/*!40000 ALTER TABLE `uploader` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-11 14:47:19
