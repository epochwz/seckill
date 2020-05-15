DROP DATABASE IF EXISTS `seckill`;

CREATE DATABASE `seckill` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `seckill`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `item`
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`
(
    `id`          INT(11)        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title`       VARCHAR(64)    NOT NULL COMMENT '商品标题',
    `price`       DECIMAL(20, 2) NOT NULL COMMENT '商品价格',
    `description` TEXT           NOT NULL COMMENT '商品描述',
    `image_url`   VARCHAR(500)   NOT NULL COMMENT '商品图片',
    `sales`       INT(11)        NOT NULL DEFAULT 0 COMMENT '商品销量',
    `create_time` DATETIME       NOT NULL DEFAULT NOW(),
    `update_time` DATETIME       NOT NULL DEFAULT NOW() ON UPDATE NOW()
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000000
  DEFAULT CHARSET = utf8mb4 COMMENT '商品信息';

-- ----------------------------
--  Table structure for `item_stock`
-- ----------------------------
DROP TABLE IF EXISTS `item_stock`;
CREATE TABLE `item_stock`
(
    `id`          INT(11)  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `item_id`     INT(11)  NOT NULL,
    `stock`       INT(11)  NOT NULL DEFAULT 0 COMMENT '库存数量',
    `create_time` DATETIME NOT NULL DEFAULT NOW(),
    `update_time` DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    UNIQUE KEY `item_stock.uk.item_id` (item_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000000
  DEFAULT CHARSET = utf8mb4 COMMENT '商品库存';

-- ----------------------------
--  Table structure for `order`
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`
(
    `id`          INT(11)        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `order_no`    VARCHAR(16)    NOT NULL COMMENT '订单编号',
    `user_id`     INT(11)        NOT NULL,
    `item_id`     INT(11)        NOT NULL,
    `promo_id`    INT(11),
    `amount`      INT(11)        NOT NULL COMMENT '商品数量',
    `item_price`  DECIMAL(20, 2) NOT NULL COMMENT '商品价格',
    `order_price` DECIMAL(20, 2) NOT NULL COMMENT '订单价格',
    `create_time` DATETIME       NOT NULL DEFAULT NOW(),
    `update_time` DATETIME       NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    UNIQUE KEY `order.uk.orderNo` (order_no),
    UNIQUE KEY `order.uk.user_item_promo` (user_id, item_id, promo_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000000
  DEFAULT CHARSET = utf8mb4 COMMENT '订单信息';

-- ----------------------------
--  Table structure for `promo`
-- ----------------------------
DROP TABLE IF EXISTS `promo`;
CREATE TABLE `promo`
(
    `id`          INT(11)        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `item_id`     INT(11)        NOT NULL COMMENT '活动商品',
    `promo_name`  VARCHAR(255)   NOT NULL COMMENT '活动名称',
    `promo_price` DECIMAL(20, 2) NOT NULL COMMENT '活动优惠价格',
    `start_time`  DATETIME       NOT NULL COMMENT '活动结束时间',
    `end_time`    DATETIME       NOT NULL COMMENT '活动开始时间',
    `create_time` DATETIME       NOT NULL DEFAULT NOW(),
    `update_time` DATETIME       NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    UNIQUE KEY `promo.uk.item_id__promo_name` (item_id, promo_name)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000000
  DEFAULT CHARSET = utf8mb4 COMMENT '促销活动';

-- ----------------------------
--  Table structure for `sequence`
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence`
(
    `id`          INT(11)      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`        VARCHAR(255) NOT NULL COMMENT '序列号池的名称',
    `value`       INT(11)      NOT NULL DEFAULT 0 COMMENT '序列号池的当前值',
    `step`        INT(11)      NOT NULL DEFAULT 1 COMMENT '序列号池增长步长',
    `create_time` DATETIME     NOT NULL DEFAULT NOW(),
    `update_time` DATETIME     NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    UNIQUE KEY `sequence.uk.name` (name)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000000
  DEFAULT CHARSET = utf8mb4 COMMENT '序列号池';

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`             INT(11)          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username`       VARCHAR(64)      NOT NULL COMMENT '账号名称',
    `telphone`       VARCHAR(11)      NOT NULL COMMENT '手机号码',
    `age`            TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '年龄',
    `gender`         TINYINT          NOT NULL DEFAULT 0 COMMENT '性别：0 保密，1 男性，2 女性',
    `register_mode`  VARCHAR(255)     NOT NULL DEFAULT 'telphone' COMMENT '注册方式：telphone, wechat',
    `third_party_id` VARCHAR(64)      NOT NULL DEFAULT '' COMMENT '第三方认证方式',
    `create_time`    DATETIME         NOT NULL DEFAULT NOW(),
    `update_time`    DATETIME         NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    UNIQUE KEY `user.uk.username` (username) USING BTREE,
    UNIQUE KEY `user.uk.telphone` (telphone) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000000
  DEFAULT CHARSET = utf8mb4 COMMENT '用户信息';

-- ----------------------------
--  Table structure for `user_password`
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password`
(
    `id`          INT(11)     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id`     INT(11)     NOT NULL,
    `password`    VARCHAR(32) NOT NULL COMMENT 'MD5 加密过的密码',
    `create_time` DATETIME    NOT NULL DEFAULT NOW(),
    `update_time` DATETIME    NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    UNIQUE KEY `user_password.uk.user_id` (user_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000000
  DEFAULT CHARSET = utf8mb4 COMMENT '用户密码';

SET FOREIGN_KEY_CHECKS = 1;