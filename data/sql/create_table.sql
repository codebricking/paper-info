# 建表脚本

-- 创建库
create database if not exists paper;

-- 切换库
use paper;

-- 用户表
CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                        `userAccount` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
                        `userPassword` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
                        `userName` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
                        `userAvatar` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户头像',
                        `userProfile` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户简介',
                        `userRole` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
                        `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `isDelete` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户'




CREATE TABLE `arxiv_category` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `category` varchar(30) NOT NULL COMMENT '分类-缩写',
                                  `full_name` varchar(100) DEFAULT NULL COMMENT '分类，全称',
                                  `previous_cat` varchar(100) DEFAULT NULL COMMENT '上一级分类',
                                  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
                                  `activated` tinyint NOT NULL DEFAULT '0' COMMENT '是否激活，0-否，1-是',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='arXiv Category Taxonomy';


CREATE TABLE `arxiv_paper` (
                               `id` int NOT NULL AUTO_INCREMENT COMMENT '爬取的数据的id',
                               `arxiv_id` varchar(200) DEFAULT NULL COMMENT 'A url of the form `http://arxiv.org/abs/{id}',
                               `updated` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                               `published` timestamp NULL DEFAULT NULL COMMENT '发布时间',
                               `title` varchar(300) DEFAULT NULL,
                               `summary` text COMMENT 'abstract',
                               `authors` varchar(400) DEFAULT NULL COMMENT '所有作者，使用JSON存储',
                               `comment` varchar(400) DEFAULT NULL COMMENT 'The authors'' comment if present.',
                               `journal_ref` varchar(400) DEFAULT NULL COMMENT 'A journal reference if present.',
                               `doi` varchar(100) DEFAULT NULL COMMENT 'A URL for the resolved DOI to an external resource if present.',
                               `primary_category` varchar(40) DEFAULT NULL COMMENT 'The result''s primary arXiv category',
                               `categories` varchar(200) DEFAULT NULL COMMENT 'All of the result''s categories.',
                               `pdf_url` varchar(200) DEFAULT NULL,
                               `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
                               `update_time` timestamp NULL DEFAULT NULL COMMENT '数据更新时间',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `arxiv_paper_pk` (`arxiv_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='爬取的论文数据';



CREATE TABLE `paper_info` (
                              `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
                              `paper_url` varchar(500) DEFAULT NULL COMMENT '论文的链接',
                              `title` varchar(300) DEFAULT NULL COMMENT '论文的标题原文',
                              `title_cn` varchar(300) DEFAULT NULL COMMENT '论文的中文标题',
                              `summary` text COMMENT '论文的摘要原文',
                              `summary_cn` text COMMENT '论文的摘要中文',
                              `summary_brief` varchar(500) DEFAULT NULL COMMENT '论文的摘要简短总结',
                              `key_words` varchar(300) DEFAULT NULL COMMENT '关键字',
                              `key_words_cn` varchar(300) DEFAULT NULL COMMENT '论文的关键词中文',
                              `pdf_url` varchar(300) DEFAULT NULL COMMENT '论文的PDF下载链接',
                              `conference_name` varchar(30) DEFAULT NULL COMMENT '会议名称，顶会则显示，非顶会默认others',
                              `authors` varchar(400) DEFAULT NULL COMMENT '论文的作者(Json String)',
                              `primary_category` varchar(40) DEFAULT NULL COMMENT '主要分类',
                              `categories` varchar(300) DEFAULT NULL COMMENT '所有分类标签',
                              `published` timestamp NULL DEFAULT NULL,
                              `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` timestamp NULL DEFAULT NULL,
                              `remark` text COMMENT '备注',
                              PRIMARY KEY (`id`),
                              FULLTEXT KEY `FTI_title` (`title`),
                              FULLTEXT KEY `FTI_summary` (`summary`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='论文的中文信息';

