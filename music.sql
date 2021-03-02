-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: music
-- ------------------------------------------------------
-- Server version	8.0.22

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
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL,
  `privilege_level` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='后台管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','123',3),(2,'scalpel','122500',2),(3,'mzj','123',1);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL COMMENT '收藏类型（0歌曲1歌单）',
  `song_id` int DEFAULT NULL,
  `song_list_id` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `up` int DEFAULT '0' COMMENT '评论点赞数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consumer`
--

DROP TABLE IF EXISTS `consumer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consumer` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '逐渐',
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别（1男0女）',
  `phone_number` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` char(30) DEFAULT NULL,
  `birth` datetime DEFAULT NULL COMMENT '生日',
  `introduction` varchar(255) DEFAULT NULL COMMENT '签名',
  `location` varchar(255) DEFAULT NULL,
  `avator` varchar(255) DEFAULT NULL COMMENT '头像',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='前端用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consumer`
--

LOCK TABLES `consumer` WRITE;
/*!40000 ALTER TABLE `consumer` DISABLE KEYS */;
INSERT INTO `consumer` VALUES (2,'undefined','sadfasdfasdf',1,'12341521341','23421342134@qq.com','2021-02-01 00:00:00','ewrq','rewqr','/avatorImg/default.jpg','2021-02-25 22:22:46','2021-02-25 22:22:46');
/*!40000 ALTER TABLE `consumer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '逐渐',
  `uploader_id` int DEFAULT NULL COMMENT '上传者id',
  `name` varchar(255) DEFAULT NULL,
  `introduction` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上传文件原因简介',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` int DEFAULT NULL COMMENT '文件审核状态（0未审核，1：一级审核通过，-1：一级审核未通过，2：二级审核通过，-2：二级审核未通过）',
  `first_reason` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '一级审核通过或者未通过原因',
  `second_reason` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '二级审核者审核结果原因',
  `begin_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `end_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `begin_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='歌曲\r\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
INSERT INTO `file` VALUES (37,19,'mzj-1.txt','没啥事','2021-03-02 14:02:54','2021-03-02 14:20:58',0,'暂无','暂无',NULL,NULL,NULL,NULL,'/publicFile/1614665838083ajfif.jpg'),(43,24,'mzj-songDefault.jpg','试验一下','2021-03-02 20:22:57','2021-03-02 20:22:57',0,'暂无','暂无','14:00','16:00','2021-03-04 00:00:00','2021-03-12 00:00:00','/publicFile/1614687776518songDefault.jpg'),(44,24,'mzj-二叉树的基本操作和遍历操作.txt','再试一下','2021-03-02 21:05:37','2021-03-02 21:36:11',0,'暂无','暂无','13：00','15：00','2021-03-04 00:00:00','2021-03-06 00:00:00','/publicFile/1614692170191二叉树的基本操作和遍历操作.txt');
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uploader`
--

DROP TABLE IF EXISTS `uploader`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `uploader` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上传者名字',
  `study_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '学号',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别（1男0女）',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像',
  `academy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '歌手简介',
  `major` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='歌手';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uploader`
--

LOCK TABLES `uploader` WRITE;
/*!40000 ALTER TABLE `uploader` DISABLE KEYS */;
INSERT INTO `uploader` VALUES (14,'林间','24320182203232',1,'/img/uploaderPic/1614576412554songListDefault.jpg','信息学院','软件工程'),(17,'马致静','24320182203232',0,'/img/uploaderPic/1614576409308u=380507774,2154400897&fm=26&gp=0.jpg','信息学院','软件工程'),(23,'admin','dfs',1,'/img/uploaderPic/default.jpg','dsaf','dsf'),(24,'mzj','dsf',0,'/img/uploaderPic/default.jpg','dsf','sdaf');
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

-- Dump completed on 2021-03-02 21:52:12
