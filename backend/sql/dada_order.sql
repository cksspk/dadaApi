/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50727
Source Host           : localhost:3306
Source Database       : vantmall

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-07-06 11:30:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dada_order
-- ----------------------------
DROP TABLE IF EXISTS `dada_order`;
CREATE TABLE `dada_order` (
  `id` varchar(50) NOT NULL COMMENT 'ding dan hao',
  `client_id` varchar(50) DEFAULT NULL COMMENT '达达运单号',
  `od_price` decimal(20,2) DEFAULT NULL COMMENT '订单价格',
  `receiver_name` varchar(15) DEFAULT NULL COMMENT '收货人名字',
  `receiver_address` varchar(50) DEFAULT NULL COMMENT '收货地址经度',
  `receiver_lng` float DEFAULT NULL COMMENT '收货地址经度',
  `receiver_lat` float DEFAULT NULL COMMENT '收货地址纬度',
  `receiver_phone` varchar(20) DEFAULT NULL COMMENT '收货人号码',
  `status_code` int(11) DEFAULT NULL COMMENT '达达订单状态',
  `fee` float DEFAULT NULL COMMENT '达达订单实际运费',
  `dm_id` int(11) DEFAULT NULL COMMENT '达达配送员id',
  `dm_name` varchar(20) DEFAULT NULL COMMENT '配送员姓名',
  `dm_mobile` varchar(20) DEFAULT NULL COMMENT '配送员手机号',
  `cancel_from` int(11) DEFAULT NULL COMMENT '订单取消原因来源(1:达达配送员取消；2:商家主动取消；3:系统或客服取消；0:默认值)',
  `cancel_reason` varchar(255) DEFAULT NULL COMMENT '订单取消原因,',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL COMMENT '逻辑删除字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dada_order
-- ----------------------------
INSERT INTO `dada_order` VALUES ('6820564702466048', null, '18.00', '张三', '浙江省杭州市西湖区五联西苑', '120.096', '30.2776', '13285891995', '1', '12', null, null, null, null, null, '2020-07-06 11:14:27', null, '1');
