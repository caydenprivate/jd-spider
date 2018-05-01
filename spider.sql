/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : spider

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 01/05/2018 22:37:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for jd_cat
-- ----------------------------
DROP TABLE IF EXISTS `jd_cat`;
CREATE TABLE `jd_cat`  (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `cat_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '京东分类id',
  `cat_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类首页url',
  `cat_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  `cat_stage` int(4) NOT NULL COMMENT '分类层级1，2，3',
  `sort` int(6) NULL DEFAULT 0 COMMENT '爬取顺序',
  `status` int(4) NULL DEFAULT 1 COMMENT '状态1有效，2无效',
  `create_at` int(11) NULL DEFAULT NULL COMMENT '创建时间',
  `update_at` int(11) NULL DEFAULT 0 COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1424 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for jd_sku
-- ----------------------------
DROP TABLE IF EXISTS `jd_sku`;
CREATE TABLE `jd_sku`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `sku_id` bigint(11) NOT NULL COMMENT 'sku_id',
  `cat_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类ID',
  `brand_id` bigint(11) NULL DEFAULT NULL COMMENT '品牌ID',
  `brand_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '品牌名称',
  `vender_id` bigint(11) NULL DEFAULT NULL COMMENT '店铺id',
  `jdzy_shop_id` bigint(11) NULL DEFAULT NULL COMMENT '京东自营店铺id',
  `shop_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '店铺名称',
  `product_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `product_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品标题',
  `product_desc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品描述',
  `sale_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '促销价',
  `shop_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '销售价',
  `original_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '原价',
  `img_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态 1:上线,2:下线,3:缺货,4:过期',
  `created_at` int(11) NOT NULL DEFAULT 0 COMMENT '创建时间',
  `updated_at` int(11) NOT NULL DEFAULT 0 COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sku`(`sku_id`) USING BTREE COMMENT '唯一值'
) ENGINE = InnoDB AUTO_INCREMENT = 141779 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '产品表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
