-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        10.4.12-MariaDB - mariadb.org binary distribution
-- 服务器OS:                        Win64
-- HeidiSQL 版本:                  10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for funnytools
CREATE DATABASE IF NOT EXISTS `funnytools` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `funnytools`;

-- Dumping structure for table funnytools.config
CREATE TABLE IF NOT EXISTS `config` (
  `id` varchar(64) NOT NULL,
  `type` int(1) unsigned NOT NULL,
  `ini` text NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- Dumping data for table funnytools.config: ~0 rows (大约)
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
/*!40000 ALTER TABLE `config` ENABLE KEYS */;

-- Dumping structure for table funnytools.domain
CREATE TABLE IF NOT EXISTS `domain` (
  `id` varchar(64) NOT NULL,
  `domain` text NOT NULL,
  `ip` text DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- Dumping data for table funnytools.domain: ~0 rows (大约)
/*!40000 ALTER TABLE `domain` DISABLE KEYS */;
/*!40000 ALTER TABLE `domain` ENABLE KEYS */;

-- Dumping structure for table funnytools.task
CREATE TABLE IF NOT EXISTS `task` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `target` text NOT NULL,
  `state` int(1) NOT NULL DEFAULT 0,
  `type` int(1) NOT NULL DEFAULT 0,
  `create_time` datetime DEFAULT NULL ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=724 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- Dumping data for table funnytools.task: ~0 rows (大约)
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
/*!40000 ALTER TABLE `task` ENABLE KEYS */;

-- Dumping structure for table funnytools.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` varchar(64) NOT NULL,
  `avatar` varchar(255) DEFAULT 'default.jpg',
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(128) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- Dumping data for table funnytools.user: ~1 rows (大约)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `avatar`, `email`, `phone`, `username`, `password`) VALUES
	('1', 'default.jpg', NULL, NULL, 'admin', 'e10adc3949ba59abbe56e057f20f883e');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
