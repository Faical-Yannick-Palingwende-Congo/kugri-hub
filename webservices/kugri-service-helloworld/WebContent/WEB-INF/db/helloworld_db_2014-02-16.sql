# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.13)
# Database: helloworld_db
# Generation Time: 2014-02-16 05:57:16 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` varchar(50) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `scope` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;

INSERT INTO `account` (`id`, `email`, `password`, `scope`)
VALUES
	('1','congo_bf@yahoo.fr','65a426fa1f3e0b9e2df42425135490c56ad9b7fcbb9b23847130181703199aba','2'),
	('2','yannick.congo@gmail.com','113334c8296bf57de7ecfa40cc1b1b5d329976f6f48d1b3bd6c779708dd9b51a','3'),
	('3','faical.congo@gmail.com','113334c8296bf57de7ecfa40cc1b1b5d329976f6f48d1b3bd6c779708dd9b51a','3');

/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table indexer
# ------------------------------------------------------------

DROP TABLE IF EXISTS `indexer`;

CREATE TABLE `indexer` (
  `id` varchar(50) DEFAULT NULL,
  `table` varchar(50) DEFAULT NULL,
  `next` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `indexer` WRITE;
/*!40000 ALTER TABLE `indexer` DISABLE KEYS */;

INSERT INTO `indexer` (`id`, `table`, `next`)
VALUES
	('0','account','4'),
	('1','message','1'),
	('2','status','5'),
	('3','session','4'),
	('4','scope','4');

/*!40000 ALTER TABLE `indexer` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table message
# ------------------------------------------------------------

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` varchar(50) DEFAULT NULL,
  `sender` varchar(50) DEFAULT NULL,
  `receiver` varchar(50) DEFAULT NULL,
  `content` varchar(512) DEFAULT NULL,
  `stamp` varchar(512) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table scope
# ------------------------------------------------------------

DROP TABLE IF EXISTS `scope`;

CREATE TABLE `scope` (
  `id` varchar(50) DEFAULT NULL,
  `value` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `scope` WRITE;
/*!40000 ALTER TABLE `scope` DISABLE KEYS */;

INSERT INTO `scope` (`id`, `value`)
VALUES
	('1','PUBLIC'),
	('2','USER'),
	('3','ADMIN');

/*!40000 ALTER TABLE `scope` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table session
# ------------------------------------------------------------

DROP TABLE IF EXISTS `session`;

CREATE TABLE `session` (
  `id` varchar(50) DEFAULT NULL,
  `account` varchar(50) DEFAULT NULL,
  `stamp` varchar(128) DEFAULT NULL,
  `access` varchar(256) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;

INSERT INTO `session` (`id`, `account`, `stamp`, `access`, `status`)
VALUES
	('1','1','11022014123740552','ccff2e26ab906cd9a350ffcc2653d94302f99ac9fcfda0ef87d3ede405f3b27c','4'),
	('2','2','12022014122553173','8291ad53a8d422417c6a748768672391a3d407b746e62d2558c539e2328ddc96','3'),
	('3','3','12022014113526807','2561bb438860fe68d2c14af526fe9200fd2d036585f590c831c219d81beff207','1');

/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table status
# ------------------------------------------------------------

DROP TABLE IF EXISTS `status`;

CREATE TABLE `status` (
  `id` varchar(50) DEFAULT NULL,
  `value` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;

INSERT INTO `status` (`id`, `value`)
VALUES
	('1','REGISTER'),
	('2','RECOVER'),
	('3','LOGIN'),
	('4','LOGOUT');

/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
