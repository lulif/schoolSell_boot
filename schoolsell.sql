-- MySQL dump 10.13  Distrib 5.5.37, for Win64 (x86)
--
-- Host: localhost    Database: schoolsell
-- ------------------------------------------------------
-- Server version	5.5.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `area` (
  `area_id` int(11) NOT NULL AUTO_INCREMENT,
  `area_name` varchar(45) NOT NULL,
  `priority` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`area_id`),
  UNIQUE KEY `area_name_UNIQUE` (`area_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
INSERT INTO `area` VALUES (1,'食堂一楼',2,NULL,NULL),(2,'食堂二楼',1,NULL,NULL),(3,'商业街',3,NULL,NULL);
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `award`
--

DROP TABLE IF EXISTS `award`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `award` (
  `award_id` int(11) NOT NULL AUTO_INCREMENT,
  `award_name` varchar(256) NOT NULL,
  `award_desc` varchar(1024) DEFAULT NULL,
  `award_img` varchar(1024) DEFAULT NULL,
  `point` int(11) NOT NULL,
  `priority` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(11) NOT NULL DEFAULT '0',
  `shop_id` int(11) DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  PRIMARY KEY (`award_id`),
  KEY `fk_award_shop_idx_idx` (`shop_id`),
  CONSTRAINT `fk_award_shop_idx` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `award`
--

LOCK TABLES `award` WRITE;
/*!40000 ALTER TABLE `award` DISABLE KEYS */;
INSERT INTO `award` VALUES (1,'茉香奶茶','香喷喷','\\upload\\item\\shop\\1\\2019-01-23 15471856357.png',10,6,'2018-12-31 00:00:00','2019-01-23 07:47:18',1,1,'2019-10-24 00:00:00'),(2,'拿铁咖啡','提神醒脑','\\upload\\item\\shop\\1\\2019-01-23 15462242932.png',15,5,'2018-12-31 00:00:00','2019-01-23 07:46:22',1,1,'2020-01-11 00:00:00'),(3,'百事可乐','好喝','\\upload\\item\\shop\\1\\2019-01-23 15490043042.png',6,2,'2018-12-31 00:00:00','2019-01-23 07:49:00',1,1,'2019-01-18 00:00:00'),(4,'洽洽瓜子','好吃','\\upload\\item\\shop\\1\\2019-01-23 15505610686.png',6,6,'2019-01-23 06:26:13','2019-01-23 07:50:56',1,1,'2020-01-08 00:00:00'),(5,'牛轧糖','香醇浓厚','\\upload\\item\\shop\\1\\2019-01-25 13113577763.png',8,3,'2019-01-25 05:11:35','2019-01-25 05:11:35',1,1,'2020-01-18 00:00:00'),(6,'金华酥饼','好吃 不解释','\\upload\\item\\shop\\1\\2019-01-25 13165490613.png',4,6,'2019-01-25 05:16:54','2019-03-07 11:42:39',1,1,'2020-01-25 00:00:00');
/*!40000 ALTER TABLE `award` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_comment`
--

DROP TABLE IF EXISTS `customer_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_comment` (
  `customer_comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `shop_id` int(11) NOT NULL,
  `comment_point` int(11) DEFAULT NULL,
  `comment_content` text,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_product_id` int(11) NOT NULL,
  PRIMARY KEY (`customer_comment_id`),
  UNIQUE KEY `user_product` (`user_product_id`),
  KEY `fk_customer_idx` (`customer_id`),
  KEY `fk_shop_idx` (`shop_id`),
  KEY `fk_product_idx` (`product_id`),
  CONSTRAINT `fk_comment_customer` FOREIGN KEY (`customer_id`) REFERENCES `person_info` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_shop` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_product` FOREIGN KEY (`user_product_id`) REFERENCES `user_product_map` (`user_product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_comment`
--

LOCK TABLES `customer_comment` WRITE;
/*!40000 ALTER TABLE `customer_comment` DISABLE KEYS */;
INSERT INTO `customer_comment` VALUES (2,1742319,16,1,9,'令人回味无穷~','2019-01-31 23:43:57',16),(3,1742319,17,1,7,'略咸，味道还可以吧','2019-01-31 23:55:40',38),(4,1742319,18,1,10,'鸡腿很大，很饱满，好评','2019-01-31 23:57:21',12),(5,1742319,15,1,9,'面条筋道很足，肉丝很香！','2019-01-31 23:58:19',8),(6,1742319,19,2,7,'略咸，饭有点少。。','2019-02-01 00:00:25',36),(7,1742319,20,1,8,'还可以噢，下次再来买！','2019-02-01 00:01:15',13),(8,1742319,16,1,5,'量太少了，5分 中规中矩','2019-02-01 06:03:31',2),(9,1742319,17,1,8,'茄子皮有点硬','2019-02-01 06:03:52',37),(10,1742319,20,1,8,'香气扑鼻~','2019-02-01 06:04:12',15),(11,1742319,18,1,9,'鸡腿分量很足，不错','2019-02-01 06:04:49',14),(12,1742319,15,1,7,'太油了','2019-02-01 06:05:22',7),(13,1742319,17,1,6,'今天量有点多','2019-02-01 06:05:56',6),(14,1742319,15,1,8,'肉丝很多 良心卖家','2019-02-01 06:06:21',1);
/*!40000 ALTER TABLE `customer_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `head_line`
--

DROP TABLE IF EXISTS `head_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `head_line` (
  `line_id` int(11) NOT NULL AUTO_INCREMENT,
  `line_name` varchar(1024) DEFAULT NULL,
  `line_link` varchar(2048) NOT NULL,
  `line_img` varchar(2048) NOT NULL,
  `priority` int(11) DEFAULT NULL,
  `enable_status` int(11) NOT NULL DEFAULT '0' COMMENT '0不可用 1可用',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `head_line`
--

LOCK TABLES `head_line` WRITE;
/*!40000 ALTER TABLE `head_line` DISABLE KEYS */;
INSERT INTO `head_line` VALUES (1,'长春理工大学光电信息学院','http://www.csoei.com/','\\upload\\item\\frontend\\index\\gdxx1.jpg',100,1,NULL,NULL),(2,'长春理工大学光电信息学院','http://www.csoei.com/','\\upload\\item\\frontend\\index\\gdxx2.jpg',99,1,NULL,NULL),(3,'长春理工大学光电信息学院','http://www.csoei.com/','\\upload\\item\\frontend\\index\\gdxx3.jpg',98,1,NULL,NULL),(4,'长春理工大学光电信息学院','http://www.csoei.com/','\\upload\\item\\frontend\\index\\gdxx4.jpg',97,1,NULL,NULL);
/*!40000 ALTER TABLE `head_line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `local_auth`
--

DROP TABLE IF EXISTS `local_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `local_auth` (
  `local_auth_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`local_auth_id`),
  UNIQUE KEY `username_UNIQUE` (`user_name`),
  KEY `fk_localauth_profile_idx` (`user_id`),
  CONSTRAINT `fk_localauth_profile` FOREIGN KEY (`user_id`) REFERENCES `person_info` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `local_auth`
--

LOCK TABLES `local_auth` WRITE;
/*!40000 ALTER TABLE `local_auth` DISABLE KEYS */;
INSERT INTO `local_auth` VALUES (1,'lulif','55566952955l2ls22665e6sl555ly965','2019-01-10 08:18:08','2019-01-10 11:22:46',1742317),(2,'admin','50565y5q2b92b9b2265q2b5s2b550ye6','2019-03-12 05:52:18','2019-01-12 05:52:18',1742316),(4,'lele','95y6q52sy50sss2226562l5y26s522s9','2019-01-10 13:02:28','2019-01-10 13:02:28',1742318),(5,'customer','q0se0yq652296525e55q6s566b6q2y56','2019-01-12 05:52:18','2019-01-12 05:52:18',1742319);
/*!40000 ALTER TABLE `local_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_detail` (
  `order_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(45) NOT NULL,
  `product_id` int(11) NOT NULL,
  `product_number` varchar(45) NOT NULL,
  `shop_id` int(11) NOT NULL,
  PRIMARY KEY (`order_detail_id`),
  KEY `fuck_idx` (`product_id`),
  KEY `fk_order_shop_idx` (`shop_id`),
  KEY `fk_order_id_idx` (`order_id`),
  CONSTRAINT `fk_order_id` FOREIGN KEY (`order_id`) REFERENCES `order_master` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_shop` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (23,'201903152112405261742319',20,'2',1),(24,'201903152112405261742319',17,'1',1),(25,'201903152124180731742319',19,'2',2),(26,'201903152127562391742319',20,'1',1),(27,'201903152231576081742319',16,'2',1),(28,'201903152305105251742319',15,'1',1);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_master`
--

DROP TABLE IF EXISTS `order_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_master` (
  `order_id` varchar(45) NOT NULL,
  `receiving_address_id` int(11) NOT NULL,
  `order_status` int(11) NOT NULL DEFAULT '0' COMMENT '0表示无效(被删除的订单) \n1表示待付款(又取消了订单)\n2表示待发货(已支付) \n3待收货(卖家已发货)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `money_account` decimal(8,2) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `expire_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `order_id_UNIQUE` (`order_id`),
  KEY `fk_rece_address_idx` (`receiving_address_id`),
  CONSTRAINT `fk_rece_address` FOREIGN KEY (`receiving_address_id`) REFERENCES `receiving_address` (`address_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_master`
--

LOCK TABLES `order_master` WRITE;
/*!40000 ALTER TABLE `order_master` DISABLE KEYS */;
INSERT INTO `order_master` VALUES ('201903152112405261742319',16,2,'2019-03-15 05:12:40','0000-00-00 00:00:00',36.00,1742319,NULL),('201903152124180731742319',16,2,'2019-03-15 05:24:18','0000-00-00 00:00:00',22.00,1742319,NULL),('201903152127562391742319',16,0,'2019-03-16 06:10:42','2019-03-15 18:10:42',13.00,1742319,NULL),('201903152231576081742319',16,0,'2019-03-16 02:24:00','2019-03-16 02:24:00',12.00,1742319,'2019-03-16 14:54:38'),('201903152305105251742319',16,2,'2019-03-16 07:16:37','2019-03-15 19:16:37',8.00,1742319,'2019-03-16 15:05:10');
/*!40000 ALTER TABLE `order_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_info`
--

DROP TABLE IF EXISTS `person_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person_info` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) DEFAULT NULL,
  `profile_img` varchar(2048) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `enable_status` int(11) NOT NULL DEFAULT '0' COMMENT '0 禁止使用本商城 1允許使用本商城',
  `user_type` int(11) NOT NULL DEFAULT '1' COMMENT '1.顾客 2.店家 3.超级管理员',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1742321 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_info`
--

LOCK TABLES `person_info` WRITE;
/*!40000 ALTER TABLE `person_info` DISABLE KEYS */;
INSERT INTO `person_info` VALUES (1,'兑换但未领取',NULL,NULL,1,2,NULL,NULL,'1'),(2,'NULL',NULL,NULL,1,2,NULL,NULL,'1'),(1742316,'超级管理员',NULL,'123456789@qq.com',1,3,'2019-03-09 08:26:24','2019-03-09 08:26:24','1'),(1742317,'小狗狗想回家','','1259462572@qq.com',1,2,NULL,NULL,'1'),(1742318,'Family peace','http://thirdwx.qlogo.cn/mmopen/vi_32/1TH46TfPC1KDLnP8UqQuuoagT8rYC9MFSYZV1micKakU6xxOIcGGpFbp5QYAnO4P2XdoYeSa82QlSVUygj8oSyQ/132',NULL,1,2,'2019-01-07 14:25:57','2019-01-07 14:25:57','1'),(1742319,'wang',NULL,'1259462572@qq.com',1,1,'2019-01-12 05:52:18','2019-01-12 05:52:18','0'),(1742320,'妈妈','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqzC2eZlApicXBXQXc3N4sXR9mUl4yxWeRqB8IFBo42X3jjjNXksyts05qohW6MicjNsdmVSicrfUf5Q/132',NULL,1,2,'2019-01-25 08:26:24','2019-01-25 08:26:24','0');
/*!40000 ALTER TABLE `person_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) NOT NULL,
  `product_desc` varchar(2000) DEFAULT NULL,
  `img_addr` varchar(2000) DEFAULT '',
  `normal_price` varchar(100) DEFAULT NULL,
  `promotion_price` varchar(100) DEFAULT NULL,
  `priority` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(11) NOT NULL DEFAULT '0',
  `product_category_id` int(11) DEFAULT NULL,
  `shop_id` int(20) NOT NULL DEFAULT '0',
  `point` int(11) DEFAULT '0',
  PRIMARY KEY (`product_id`),
  KEY `fk_product_procate_idx` (`product_category_id`),
  KEY `fk_product_shop_idx` (`shop_id`),
  CONSTRAINT `fk_product_procate` FOREIGN KEY (`product_category_id`) REFERENCES `product_category` (`product_category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_shop` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (15,'肉丝炒面','味道还可以','\\upload\\item\\shop\\1\\2018-12-29 20105391161.png','11','8',3,'2018-12-29 20:10:53','2018-12-30 23:02:13',1,2,1,11),(16,'清蒸水饺','饺子很入味','\\upload\\item\\shop\\1\\2018-12-31 11531853535.png','9','6',3,'2018-12-31 11:53:18','2018-12-31 11:53:18',1,4,1,13),(17,'茄子盖浇饭','真香','\\upload\\item\\shop\\1\\2018-12-31 11543759285.png','12','10',5,'2018-12-31 11:54:37','2018-12-31 11:54:37',1,1,1,9),(18,'鸡腿饭','不好吃','\\upload\\item\\shop\\1\\2018-12-31 23291658170.png','100','96',10,'2018-12-31 23:15:43','2019-03-07 11:41:17',1,1,1,10),(19,'番茄烤肉饭','还可以','\\upload\\item\\shop\\2\\2019-01-25 20144795939.png','15','11',11,'2019-01-25 12:14:47','2019-02-02 15:11:15',1,11,2,9),(20,'鱼香肉丝','真香','\\upload\\item\\shop\\1\\2019-01-26 15323315634.png','15','13',2,'2019-01-26 07:32:33','2019-01-26 07:33:11',1,1,1,8);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_category`
--

DROP TABLE IF EXISTS `product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_category` (
  `product_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_category_name` varchar(100) NOT NULL,
  `priority` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `shop_id` int(20) NOT NULL DEFAULT '0',
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`product_category_id`),
  KEY `fk_procate_shop_idx` (`shop_id`),
  CONSTRAINT `fk_procate_shop` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_category`
--

LOCK TABLES `product_category` WRITE;
/*!40000 ALTER TABLE `product_category` DISABLE KEYS */;
INSERT INTO `product_category` VALUES (1,'盖浇饭',7,NULL,1,NULL),(2,'炒面',2,NULL,1,NULL),(4,'饺子',3,NULL,1,NULL),(6,'混沌',6,NULL,1,NULL),(9,'盒饭',5,NULL,1,NULL),(10,'汤面',6,'2019-02-02 23:06:51',1,'2019-02-02 23:06:51'),(11,'盖浇饭',6,'2019-02-02 15:10:51',2,'2019-02-02 15:10:51');
/*!40000 ALTER TABLE `product_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_img`
--

DROP TABLE IF EXISTS `product_img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_img` (
  `product_img_id` int(11) NOT NULL AUTO_INCREMENT,
  `img_addr` varchar(2000) NOT NULL,
  `img_desc` varchar(2000) DEFAULT NULL,
  `priority` int(11) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`product_img_id`),
  KEY `fk_proimg_product_idx` (`product_id`),
  CONSTRAINT `fk_proimg_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_img`
--

LOCK TABLES `product_img` WRITE;
/*!40000 ALTER TABLE `product_img` DISABLE KEYS */;
INSERT INTO `product_img` VALUES (5,'\\upload\\item\\shop\\1\\2018-12-29 20105325798.png',NULL,NULL,'2018-12-29 12:10:53',15),(6,'\\upload\\item\\shop\\1\\2018-12-29 20105322449.png',NULL,NULL,'2018-12-29 12:10:53',15),(7,'\\upload\\item\\shop\\1\\2018-12-31 11531893686.png',NULL,NULL,'2018-12-31 03:53:18',16),(8,'\\upload\\item\\shop\\1\\2018-12-31 11531886236.png',NULL,NULL,'2018-12-31 03:53:18',16),(9,'\\upload\\item\\shop\\1\\2018-12-31 11543771890.png',NULL,NULL,'2018-12-31 03:54:37',17),(10,'\\upload\\item\\shop\\1\\2018-12-31 11543736295.png',NULL,NULL,'2018-12-31 03:54:37',17),(13,'\\upload\\item\\shop\\1\\2018-12-31 23291636039.png',NULL,NULL,'2018-12-31 15:29:16',18),(14,'\\upload\\item\\shop\\1\\2018-12-31 23291642105.png',NULL,NULL,'2018-12-31 15:29:16',18),(15,'\\upload\\item\\shop\\2\\2019-01-25 20144727329.png',NULL,NULL,'2019-01-25 04:14:47',19),(16,'\\upload\\item\\shop\\2\\2019-01-25 20144715259.png',NULL,NULL,'2019-01-25 04:14:47',19),(17,'\\upload\\item\\shop\\1\\2019-01-26 15323382435.png',NULL,NULL,'2019-01-25 23:32:33',20),(18,'\\upload\\item\\shop\\1\\2019-01-26 15323392939.png',NULL,NULL,'2019-01-25 23:32:33',20),(19,'\\upload\\item\\shop\\1\\2019-01-26 15323399789.png',NULL,NULL,'2019-01-25 23:32:33',20);
/*!40000 ALTER TABLE `product_img` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_sell_daily`
--

DROP TABLE IF EXISTS `product_sell_daily`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_sell_daily` (
  `product_id` int(11) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `product_sell_daily_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`product_sell_daily_id`),
  UNIQUE KEY `product_id_2` (`product_id`,`shop_id`,`create_time`),
  KEY `fk_product_sell_product_idx` (`product_id`),
  KEY `fk_product_sell_shop_idx` (`shop_id`),
  CONSTRAINT `fk_product_sell_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_sell_shop` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_sell_daily`
--

LOCK TABLES `product_sell_daily` WRITE;
/*!40000 ALTER TABLE `product_sell_daily` DISABLE KEYS */;
INSERT INTO `product_sell_daily` VALUES (15,1,'2018-12-28 16:00:00',1,23),(16,1,'2018-12-27 16:00:00',1,24),(17,1,'2018-12-29 16:00:00',1,25),(18,1,'2018-12-28 16:00:00',1,26),(15,1,'2018-12-30 16:00:00',1,27),(15,1,'2018-12-27 16:00:00',0,28),(17,1,'2018-12-27 16:00:00',0,29),(18,1,'2018-12-27 16:00:00',0,30),(16,1,'2018-12-28 16:00:00',0,32),(17,1,'2018-12-28 16:00:00',0,33),(15,1,'2018-12-29 16:00:00',0,34),(16,1,'2018-12-29 16:00:00',0,35),(18,1,'2018-12-29 16:00:00',0,36),(16,1,'2018-12-30 16:00:00',0,37),(17,1,'2018-12-30 16:00:00',0,38),(18,1,'2018-12-30 16:00:00',0,39),(15,1,'2019-01-23 16:00:00',2,40),(17,1,'2019-01-23 16:00:00',1,41),(18,1,'2019-01-23 16:00:00',1,42),(16,1,'2019-01-23 16:00:00',0,43),(16,1,'2019-01-24 16:00:00',1,44),(17,1,'2019-01-24 16:00:00',1,45),(18,1,'2019-01-25 16:00:00',1,47),(15,1,'2019-01-24 16:00:00',0,48),(18,1,'2019-01-24 16:00:00',0,49),(20,1,'2019-01-24 16:00:00',0,50),(19,2,'2019-01-24 16:00:00',0,51),(20,1,'2019-01-23 16:00:00',0,61),(15,1,'2019-01-25 16:00:00',0,62),(16,1,'2019-01-25 16:00:00',0,63),(17,1,'2019-01-25 16:00:00',0,64),(20,1,'2019-01-25 16:00:00',0,65),(16,1,'2019-01-26 16:00:00',1,66),(18,1,'2019-01-26 16:00:00',1,67),(20,1,'2019-01-26 16:00:00',2,68),(15,1,'2019-01-26 16:00:00',0,74),(17,1,'2019-01-26 16:00:00',0,75),(19,2,'2019-01-26 16:00:00',0,76),(15,1,'2019-01-27 16:00:00',0,77),(16,1,'2019-01-27 16:00:00',0,78),(17,1,'2019-01-27 16:00:00',0,79),(18,1,'2019-01-27 16:00:00',0,80),(20,1,'2019-01-27 16:00:00',0,81),(19,2,'2019-01-27 16:00:00',0,82);
/*!40000 ALTER TABLE `product_sell_daily` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receiving_address`
--

DROP TABLE IF EXISTS `receiving_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `receiving_address` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(120) NOT NULL,
  `location_details` varchar(45) NOT NULL,
  `contact_name` varchar(45) NOT NULL,
  `contact_phone` varchar(45) NOT NULL,
  `priority` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  KEY `fk_rec_address_idx` (`user_id`),
  CONSTRAINT `fk_rec_address` FOREIGN KEY (`user_id`) REFERENCES `person_info` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receiving_address`
--

LOCK TABLES `receiving_address` WRITE;
/*!40000 ALTER TABLE `receiving_address` DISABLE KEYS */;
INSERT INTO `receiving_address` VALUES (3,'杭州市大江东医院','义隆路98号','陆阳阳','12345678910',0,1742319,'2019-03-11 14:30:47',NULL),(4,'杭州市申发电器向东左转150米','放小店里即可','陈桂仙','98765432110',0,1742319,'2019-03-11 09:23:33',NULL),(15,'杭州市福恩纺织有限公司','放传达室即可','陆国良','1223151849',0,1742319,'2019-03-13 07:16:13','2019-03-12 03:21:50'),(16,'长春理工大学光电信息学院','13栋229','陆利锋','15304414925',1,1742319,'2019-03-13 07:16:13',NULL);
/*!40000 ALTER TABLE `receiving_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shop` (
  `shop_id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) NOT NULL,
  `area_id` int(11) DEFAULT NULL,
  `shop_category_id` int(11) DEFAULT NULL,
  `shop_name` varchar(256) NOT NULL,
  `shop_desc` varchar(1024) DEFAULT NULL,
  `shop_addr` varchar(200) DEFAULT NULL,
  `phone` varchar(128) DEFAULT NULL,
  `shop_img` varchar(1024) DEFAULT NULL,
  `priority` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(11) NOT NULL DEFAULT '0',
  `advice` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`shop_id`),
  KEY `fk_shop_area_idx` (`area_id`),
  KEY `fk_shop_profile_idx` (`owner_id`),
  KEY `fk_shop_shopcate_idx` (`shop_category_id`),
  CONSTRAINT `fk_shop_area` FOREIGN KEY (`area_id`) REFERENCES `area` (`area_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_shop_profile` FOREIGN KEY (`owner_id`) REFERENCES `person_info` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_shop_shopcate` FOREIGN KEY (`shop_category_id`) REFERENCES `shop_category` (`shop_category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
INSERT INTO `shop` VALUES (1,1742317,3,9,'小汪快餐店','不错','食堂一楼正大门右侧边','15304414925','\\upload\\item\\shop\\1\\2018-12-27 13180118705.jpg',0,NULL,'2019-03-07 11:39:37',1,NULL),(2,1742317,3,1,'香旺招牌饭','经常吃','商业街XXX号','18811012138','\\upload\\item\\shop\\2\\2018-12-27 13180118705.jpg',0,NULL,NULL,1,NULL),(3,1742317,1,1,'小宾哥快餐','BB','食堂一楼正对大门','15658110392','\\upload\\item\\shop\\3\\2018-12-21 17320825235.png',0,NULL,NULL,1,NULL),(8,1742317,1,1,'好味道快餐','好吃的','食堂一楼正对大门','13175115943','\\upload\\item\\shop\\8\\2018-12-21 17320825236.png',0,'2018-12-21 17:28:27','2018-12-21 17:28:27',1,NULL),(9,1742317,2,10,'港式快餐','还可以','食堂二楼左侧','12345312131','\\upload\\item\\shop\\9\\2018-12-21 17320825235.png',0,'2018-12-21 17:32:08','2018-12-21 17:32:08',1,NULL);
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop_auth_map`
--

DROP TABLE IF EXISTS `shop_auth_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shop_auth_map` (
  `shop_auth_id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` int(11) NOT NULL,
  `shop_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `title_flag` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`shop_auth_id`),
  UNIQUE KEY `employee_id` (`employee_id`,`shop_id`),
  KEY `fk_shop_auth_map_shop_idx` (`shop_id`),
  KEY `fk_shop_auth_map_employee_idx` (`employee_id`),
  CONSTRAINT `fk_shop_auth_map_employee` FOREIGN KEY (`employee_id`) REFERENCES `person_info` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_shop_auth_map_shop` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop_auth_map`
--

LOCK TABLES `shop_auth_map` WRITE;
/*!40000 ALTER TABLE `shop_auth_map` DISABLE KEYS */;
INSERT INTO `shop_auth_map` VALUES (1,1742317,1,'老板',0,NULL,NULL,1),(4,1742318,1,'员工',1,'2019-01-21 04:50:19','2019-01-21 04:50:19',1),(5,1742320,1,'妈妈',1,'2019-01-25 08:27:08','2019-01-25 08:28:20',1),(6,1742318,2,'员工',1,'2019-01-26 07:15:44','2019-01-26 07:15:44',1),(7,1742320,2,'员工',1,'2019-01-27 11:52:12','2019-01-27 11:52:12',1);
/*!40000 ALTER TABLE `shop_auth_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop_category`
--

DROP TABLE IF EXISTS `shop_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shop_category` (
  `shop_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_category_name` varchar(100) NOT NULL DEFAULT '',
  `shop_category_desc` varchar(2000) DEFAULT '',
  `shop_category_img` varchar(2000) DEFAULT NULL,
  `priority` int(11) NOT NULL DEFAULT '0',
  `create_time` varchar(45) DEFAULT NULL,
  `last_edit_time` varchar(45) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`shop_category_id`),
  KEY `fk_shop_category_id_idx` (`parent_id`),
  CONSTRAINT `fk_shop_category_id` FOREIGN KEY (`parent_id`) REFERENCES `shop_category` (`shop_category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop_category`
--

LOCK TABLES `shop_category` WRITE;
/*!40000 ALTER TABLE `shop_category` DISABLE KEYS */;
INSERT INTO `shop_category` VALUES (1,'美食饮品','光电美味','\\upload\\item\\frontend\\index\\meishi.png',9,NULL,NULL,NULL),(2,'二手交易','二手交易','\\upload\\item\\frontend\\index\\ershou.png',8,NULL,NULL,NULL),(3,'培训教育','CET/考研','\\upload\\item\\frontend\\index\\peixun.png',7,NULL,NULL,NULL),(4,'美发美甲','焕然一新','\\upload\\item\\frontend\\index\\meifa.png',6,NULL,NULL,NULL),(5,'娱乐健身','强身健体','\\upload\\item\\frontend\\index\\jianshen.png',5,NULL,NULL,NULL),(6,'驾校报考','江湖车神','\\upload\\item\\frontend\\index\\jiaxiao.png',4,NULL,NULL,NULL),(7,'超市水果','甘甜适口','\\upload\\item\\frontend\\index\\shuiguo.png',3,NULL,NULL,NULL),(8,'维修保养','物品修理','\\upload\\item\\frontend\\index\\weixiu.png',2,NULL,NULL,NULL),(9,'南方风味','','',6,NULL,NULL,1),(10,'港式风味','',NULL,1,NULL,NULL,1);
/*!40000 ALTER TABLE `shop_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_award_map`
--

DROP TABLE IF EXISTS `user_award_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_award_map` (
  `user_award_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `award_id` int(11) NOT NULL,
  `shop_id` int(11) NOT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `used_status` int(11) NOT NULL DEFAULT '0',
  `point` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_award_id`),
  KEY `fk_user_award_map_profile_idx` (`user_id`),
  KEY `fk_user_award_map_award_idx` (`award_id`),
  KEY `fk_user_award_map_shop_idx` (`shop_id`),
  KEY `fk_user_award_map_operator_idx` (`operator_id`),
  CONSTRAINT `fk_user_award_map_award` FOREIGN KEY (`award_id`) REFERENCES `award` (`award_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_award_map_operator` FOREIGN KEY (`operator_id`) REFERENCES `person_info` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_award_map_person` FOREIGN KEY (`user_id`) REFERENCES `person_info` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_award_map_shop` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_award_map`
--

LOCK TABLES `user_award_map` WRITE;
/*!40000 ALTER TABLE `user_award_map` DISABLE KEYS */;
INSERT INTO `user_award_map` VALUES (1,1742319,1,1,1742318,'2019-01-01 00:00:00',1,10),(3,1742319,4,1,1742318,'2019-01-24 23:55:56',1,6),(5,1742319,2,1,1742318,'2019-01-25 04:16:11',1,15),(7,1742319,5,1,1742318,'2019-01-25 05:13:01',1,8),(8,1742319,6,1,1742318,'2019-01-25 08:19:13',1,4),(9,1742319,2,1,1742318,'2019-01-25 05:36:11',1,15),(10,1742319,1,1,1742318,'2019-01-25 07:16:25',1,10),(11,1742319,5,1,1742318,'2019-01-26 14:17:57',1,8),(12,1742319,6,1,1742318,'2019-01-26 14:22:11',1,4),(14,1742319,6,1,1742318,'2019-01-26 14:48:37',1,4),(15,1742319,4,1,1742318,'2019-01-26 15:07:12',1,6),(16,1742319,4,1,1742318,'2019-01-26 15:07:12',1,6),(17,1742319,4,1,1742318,'2019-01-26 15:07:12',1,6),(18,1742319,6,1,1742318,'2019-01-27 11:34:25',1,4),(19,1742319,6,1,1742318,'2019-01-27 06:42:37',1,4),(20,1742319,6,1,1742318,'2019-01-27 06:41:23',1,4),(22,1742319,4,1,1742318,'2019-01-27 11:45:15',1,6),(23,1742319,6,1,1742318,'2019-01-27 11:35:40',1,4),(24,1742319,5,1,1742318,'2019-01-27 12:08:19',1,8),(25,1742319,2,1,1742318,'2019-01-27 12:13:21',1,15),(26,1742319,1,1,1742318,'2019-01-27 14:07:43',1,10),(28,1742319,6,1,1742318,'2019-01-27 14:24:03',1,4),(30,1742319,5,1,1742318,'2019-01-27 14:34:37',1,8),(31,1742319,6,1,1,'2019-01-29 13:12:04',0,4),(32,1742319,6,1,1,'2019-01-29 13:12:04',0,4),(33,1742319,3,1,1,'2019-01-11 13:46:36',0,6);
/*!40000 ALTER TABLE `user_award_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_collection_map`
--

DROP TABLE IF EXISTS `user_collection_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_collection_map` (
  `user_collection_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_collection_id`),
  UNIQUE KEY `index5` (`user_id`,`shop_id`,`product_id`),
  KEY `fk_user_idx` (`user_id`),
  KEY `fk_shop_idx` (`shop_id`),
  KEY `fk_product_idx` (`product_id`),
  CONSTRAINT `fk_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_shop` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `person_info` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_collection_map`
--

LOCK TABLES `user_collection_map` WRITE;
/*!40000 ALTER TABLE `user_collection_map` DISABLE KEYS */;
INSERT INTO `user_collection_map` VALUES (1,1742319,1,18,'2019-01-28 06:28:52'),(2,1742319,1,17,'2019-01-28 06:31:06'),(3,1742319,2,19,'2019-01-28 06:31:22'),(5,1742319,1,15,'2019-03-08 12:25:54');
/*!40000 ALTER TABLE `user_collection_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_product_map`
--

DROP TABLE IF EXISTS `user_product_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_product_map` (
  `user_product_id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `point` int(11) DEFAULT '0',
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `operator_id` int(11) DEFAULT '2',
  `shop_id` int(11) DEFAULT NULL,
  `is_comment` int(11) DEFAULT '0',
  PRIMARY KEY (`user_product_id`),
  KEY `fk_user_product_map_person_idx` (`user_id`),
  KEY `fk_user_product_map_product_idx` (`product_id`),
  KEY `fk_user_product_map_shop_idx` (`shop_id`),
  CONSTRAINT `fk_user_product_map_operator` FOREIGN KEY (`user_id`) REFERENCES `person_info` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_product_map_person` FOREIGN KEY (`user_id`) REFERENCES `person_info` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_product_map_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_product_map_shop` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_product_map`
--

LOCK TABLES `user_product_map` WRITE;
/*!40000 ALTER TABLE `user_product_map` DISABLE KEYS */;
INSERT INTO `user_product_map` VALUES (1,'2018-12-29 00:00:00',11,1742319,15,1742318,1,1),(2,'2018-12-28 00:00:00',13,1742319,16,1742318,1,1),(3,'2018-12-30 00:00:00',9,1742319,17,1742318,1,0),(4,'2018-12-29 00:00:00',10,1742319,18,1742318,1,0),(5,'2018-12-31 00:00:00',11,1742319,15,1742318,1,0),(6,'2019-01-24 09:00:23',9,1742319,17,1742318,1,1),(7,'2019-01-24 09:02:35',11,1742319,15,1742318,1,1),(8,'2019-01-24 09:03:49',11,1742319,15,1742318,1,1),(9,'2019-01-24 09:23:21',10,1742319,18,1742318,1,0),(10,'2019-01-25 08:38:49',13,1742319,16,1742320,1,0),(11,'2019-01-25 08:47:33',9,1742319,17,1742320,1,0),(12,'2019-01-26 07:12:17',10,1742319,18,1742318,1,1),(13,'2019-01-27 04:38:48',8,1742319,20,1742318,1,1),(14,'2019-01-27 04:40:10',10,1742319,18,1742318,1,1),(15,'2019-01-27 04:41:16',8,1742319,20,1742318,1,1),(16,'2019-01-27 04:42:03',13,1742319,16,1742318,1,1),(36,'2019-01-27 08:09:52',9,1742319,19,1742318,2,1),(37,'2019-01-27 15:04:35',9,1742319,17,1742318,1,1),(38,'2019-01-27 15:04:35',9,1742319,17,1742318,1,1),(42,'2019-01-27 15:04:39',9,1742319,17,1742318,1,1),(67,'2019-03-15 13:12:40',8,1742319,20,2,1,0),(68,'2019-03-15 13:12:40',8,1742319,20,2,1,0),(69,'2019-03-15 13:12:40',9,1742319,17,2,1,0),(70,'2019-03-15 13:24:18',9,1742319,19,2,2,0),(71,'2019-03-15 13:24:18',9,1742319,19,2,2,0),(72,'2019-03-15 13:27:56',8,1742319,20,2,1,0),(73,'2019-03-16 07:16:37',11,1742319,15,2,1,0);
/*!40000 ALTER TABLE `user_product_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_shop_map`
--

DROP TABLE IF EXISTS `user_shop_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_shop_map` (
  `user_shop_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `shop_id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `point` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_shop_id`),
  UNIQUE KEY `user_shop_unique` (`user_id`,`shop_id`),
  KEY `fk_user_person_info_idx` (`user_id`),
  KEY `fk_user_shop_info_idx` (`shop_id`),
  CONSTRAINT `fk_user_person_info` FOREIGN KEY (`user_id`) REFERENCES `person_info` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_shop_info` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_shop_map`
--

LOCK TABLES `user_shop_map` WRITE;
/*!40000 ALTER TABLE `user_shop_map` DISABLE KEYS */;
INSERT INTO `user_shop_map` VALUES (1,1742319,1,'2018-12-31 23:09:00',76),(4,1742319,2,'2019-01-27 08:09:52',27);
/*!40000 ALTER TABLE `user_shop_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_shopping_cart`
--

DROP TABLE IF EXISTS `user_shopping_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_shopping_cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `shop_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index2` (`user_id`,`shop_id`,`product_id`),
  KEY `fk_shop_id_idx` (`shop_id`),
  KEY `fk_product_id_idx` (`product_id`),
  CONSTRAINT `fk_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_shop_id` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `person_info` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_shopping_cart`
--

LOCK TABLES `user_shopping_cart` WRITE;
/*!40000 ALTER TABLE `user_shopping_cart` DISABLE KEYS */;
INSERT INTO `user_shopping_cart` VALUES (1,1742319,1,18,'2019-03-08 17:50:40'),(6,1742319,2,19,'2019-03-09 00:19:22'),(7,1742319,1,17,'2019-03-09 00:19:34'),(8,1742319,1,20,'2019-03-09 00:19:41');
/*!40000 ALTER TABLE `user_shopping_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_auth`
--

DROP TABLE IF EXISTS `wechat_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_auth` (
  `wechat_auth_id` int(11) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(45) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`wechat_auth_id`),
  UNIQUE KEY `open_id_UNIQUE` (`open_id`),
  KEY `fk_wechatauth_idx` (`user_id`),
  CONSTRAINT `fk_wechatauth` FOREIGN KEY (`user_id`) REFERENCES `person_info` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_auth`
--

LOCK TABLES `wechat_auth` WRITE;
/*!40000 ALTER TABLE `wechat_auth` DISABLE KEYS */;
INSERT INTO `wechat_auth` VALUES (1,'oZeG75v14nR9-PvKUckstAGE_8ck','2019-01-07 14:25:57',1742318),(2,'oZeG75nMBchnrhV9PUP0Pvs1Tw7Y','2019-01-25 08:26:24',1742320);
/*!40000 ALTER TABLE `wechat_auth` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-16 17:15:56
