
# create new user
DROP USER 'topolev'@'localhost';

CREATE USER 'topolev'@'localhost' IDENTIFIED BY 'topolev';
GRANT ALL PRIVILEGES ON * . * TO 'topolev'@'localhost';


# create scheme
CREATE DATABASE IF NOT EXISTS `topolev`;
USE `topolev_test`;


# Table contact
DROP TABLE IF EXISTS `contact`;

CREATE TABLE `contact` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `middle_name` varchar(45) DEFAULT NULL,
  `sex` enum('Male','Female') DEFAULT NULL,
  `nationality` varchar(45) DEFAULT NULL,
  `marital_status` enum('Single','Married') DEFAULT NULL,
  `website` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `work_place` varchar(45) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `photo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


# Table address
DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `country` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `street` varchar(45) DEFAULT NULL,
  `build` int(5) DEFAULT NULL,
  `flat` int(5) DEFAULT NULL,
  `ind` int(11) DEFAULT NULL,
  `contact_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


# Table phone
DROP TABLE IF EXISTS `phone`;

CREATE TABLE `phone` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `country_code` int(11) DEFAULT NULL,
  `operator_code` int(11) DEFAULT NULL,
  `phone_number` int(11) DEFAULT NULL,
  `type_phone` enum('Home','Mobile') DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `contact_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;


# Table attachment
DROP TABLE IF EXISTS `attachment`;

CREATE TABLE `attachment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `attachmentcol` varchar(45) DEFAULT NULL,
  `name_file` varchar(45) DEFAULT NULL,
  `comment_file` varchar(45) DEFAULT NULL,
  `date_file` date DEFAULT NULL,
  `name_file_in_system` varchar(45) DEFAULT NULL,
  `contact_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;


