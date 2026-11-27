CREATE DATABASE IF NOT EXISTS `information_audit`
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `information_audit`;

CREATE TABLE IF NOT EXISTS `admin` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) DEFAULT NULL COMMENT '登录账号',
    `password` VARCHAR(255) DEFAULT NULL,
    `privilege_level` INT DEFAULT NULL COMMENT '1 上传者，2 一级审核员，3 二级审核员，4 超级管理员',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号与角色';

CREATE TABLE IF NOT EXISTS `uploader` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) DEFAULT NULL,
    `study_number` VARCHAR(255) DEFAULT NULL COMMENT '与上传者登录账号对应',
    `gender` TINYINT(1) DEFAULT NULL,
    `pic` VARCHAR(255) NOT NULL,
    `academy` VARCHAR(255) DEFAULT NULL,
    `major` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='上传者档案';

CREATE TABLE IF NOT EXISTS `file` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `uploader_id` INT DEFAULT NULL,
    `name` VARCHAR(255) DEFAULT NULL,
    `introduction` VARCHAR(255) DEFAULT NULL,
    `create_time` DATETIME DEFAULT NULL,
    `update_time` DATETIME DEFAULT NULL,
    `status` INT DEFAULT NULL COMMENT '0 待初审，1 初审通过，-1 初审驳回，2 终审通过，-2 终审驳回',
    `first_reason` TEXT,
    `second_reason` TEXT,
    `begin_time` VARCHAR(255) DEFAULT NULL,
    `end_time` VARCHAR(255) DEFAULT NULL,
    `begin_date` DATETIME DEFAULT NULL,
    `end_date` DATETIME DEFAULT NULL,
    `url` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_file_uploader_status` (`uploader_id`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='送审文件与审核结果';
