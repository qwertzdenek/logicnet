-- MySQL dump 10.16  Distrib 10.1.10-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: janecekz
-- ------------------------------------------------------
-- Server version	10.1.10-MariaDB-log

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
-- Table structure for table `account_likes`
--

DROP TABLE IF EXISTS `account_likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_likes` (
  `account_id` varchar(255) NOT NULL,
  `post_id` bigint(20) NOT NULL,
  PRIMARY KEY (`account_id`,`post_id`),
  KEY `FKeyj7srbpjcs0r2k4wp3u7ar2f` (`post_id`),
  CONSTRAINT `FKd0uvpjltjegnk9o13224ixupa` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `FKeyj7srbpjcs0r2k4wp3u7ar2f` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `account_roles`
--

DROP TABLE IF EXISTS `account_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_roles` (
  `account_id` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  KEY `FK61h48dsir3h82pxbq3cwgp0ce` (`account_id`),
  CONSTRAINT `FK61h48dsir3h82pxbq3cwgp0ce` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts` (
  `account_id` varchar(255) NOT NULL,
  `birthday` date NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `profile_picture` varchar(255) NOT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `friend_requests`
--

DROP TABLE IF EXISTS `friend_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friend_requests` (
  `account` varchar(255) NOT NULL,
  `target` varchar(255) NOT NULL,
  PRIMARY KEY (`account`,`target`),
  KEY `FKpgcypxv5wo4ogrydhvvrx5rqy` (`target`),
  CONSTRAINT `FKh3dx8nh49coeqojfw5ysn0g5m` FOREIGN KEY (`account`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `FKpgcypxv5wo4ogrydhvvrx5rqy` FOREIGN KEY (`target`) REFERENCES `accounts` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `friendship_pairs`
--

DROP TABLE IF EXISTS `friendship_pairs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friendship_pairs` (
  `pair_id` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `first` varchar(255) NOT NULL,
  `second` varchar(255) NOT NULL,
  PRIMARY KEY (`pair_id`),
  KEY `FKeuu96bydj7x1esplrbi7xg1rh` (`first`),
  KEY `FKcp9wpspovrh2qcbf5nduqfqys` (`second`),
  CONSTRAINT `FKcp9wpspovrh2qcbf5nduqfqys` FOREIGN KEY (`second`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `FKeuu96bydj7x1esplrbi7xg1rh` FOREIGN KEY (`first`) REFERENCES `accounts` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_hides`
--

DROP TABLE IF EXISTS `post_hides`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_hides` (
  `account_id` varchar(255) NOT NULL,
  `post_id` bigint(20) NOT NULL,
  PRIMARY KEY (`account_id`,`post_id`),
  KEY `FKnfrklinau6tkxr69cbgrep915` (`post_id`),
  CONSTRAINT `FKnfrklinau6tkxr69cbgrep915` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `FKsvl7txukrip576alci8j3ch67` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `posts` (
  `id` bigint(20) NOT NULL,
  `content` text NOT NULL,
  `date_time` datetime NOT NULL,
  `likes_count` int(11) NOT NULL,
  `writer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq1qlw911en8v8774wfko34sbt` (`writer`),
  CONSTRAINT `FKq1qlw911en8v8774wfko34sbt` FOREIGN KEY (`writer`) REFERENCES `accounts` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-13 11:32:50
