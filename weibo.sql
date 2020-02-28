/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : weibo

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2019-12-25 00:02:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_collection
-- ----------------------------
DROP TABLE IF EXISTS `tb_collection`;
CREATE TABLE `tb_collection` (
  `collection_id` int(100) NOT NULL AUTO_INCREMENT,
  `user_id` int(100) NOT NULL,
  `state` int(2) NOT NULL,
  `create_time` datetime NOT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `weibo_id` int(100) NOT NULL,
  PRIMARY KEY (`collection_id`),
  KEY `fk_collection_user` (`user_id`),
  KEY `fk_collection_weibo` (`weibo_id`),
  CONSTRAINT `fk_collection_user` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_collection_weibo` FOREIGN KEY (`weibo_id`) REFERENCES `tb_weibo` (`weibo_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_collection
-- ----------------------------
INSERT INTO `tb_collection` VALUES ('13', '24', '1', '2019-12-19 19:11:00', '2019-12-19 19:11:00', '69');
INSERT INTO `tb_collection` VALUES ('23', '31', '1', '2019-12-20 21:09:02', '2019-12-20 21:09:02', '68');
INSERT INTO `tb_collection` VALUES ('25', '31', '1', '2019-12-20 21:10:53', '2019-12-20 21:10:53', '70');
INSERT INTO `tb_collection` VALUES ('26', '31', '1', '2019-12-20 21:12:33', '2019-12-20 21:12:33', '72');
INSERT INTO `tb_collection` VALUES ('27', '31', '1', '2019-12-20 21:12:46', '2019-12-20 21:12:46', '71');
INSERT INTO `tb_collection` VALUES ('35', '35', '1', '2019-12-24 10:01:39', '2019-12-24 10:01:39', '69');
INSERT INTO `tb_collection` VALUES ('36', '26', '1', '2019-12-24 10:16:10', '2019-12-24 10:16:10', '65');
INSERT INTO `tb_collection` VALUES ('49', '23', '1', '2019-12-24 19:46:06', '2019-12-24 19:46:06', '73');
INSERT INTO `tb_collection` VALUES ('50', '23', '1', '2019-12-24 19:46:08', '2019-12-24 19:46:08', '72');
INSERT INTO `tb_collection` VALUES ('58', '40', '1', '2019-12-24 23:00:46', '2019-12-24 23:00:46', '73');

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `comment_id` int(200) NOT NULL AUTO_INCREMENT,
  `weibo_id` int(200) NOT NULL,
  `content` varchar(256) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `user_id` int(200) NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `fk_commen_weibo` (`weibo_id`),
  KEY `fk_commen_user` (`user_id`),
  CONSTRAINT `fk_commen_user` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------
INSERT INTO `tb_comment` VALUES ('4', '63', '我是小号哒', '2019-12-09 14:17:48', '26');
INSERT INTO `tb_comment` VALUES ('5', '65', '没收到', '2019-12-20 22:28:25', '23');
INSERT INTO `tb_comment` VALUES ('6', '69', '比你好看', '2019-12-20 23:22:40', '24');
INSERT INTO `tb_comment` VALUES ('9', '65', '我喜欢', '2019-12-21 22:43:17', '23');
INSERT INTO `tb_comment` VALUES ('10', '69', '我女票', '2019-12-22 14:12:03', '23');
INSERT INTO `tb_comment` VALUES ('11', '69', '删掉，让我讲!', '2019-12-22 14:41:35', '23');
INSERT INTO `tb_comment` VALUES ('12', '64', '大佬你好', '2019-12-22 14:46:03', '23');
INSERT INTO `tb_comment` VALUES ('13', '64', '你好吖', '2019-12-22 14:48:52', '23');
INSERT INTO `tb_comment` VALUES ('22', '69', 'fffffffffffffffffffffffffffffffffffffffffffffffffff', '2019-12-24 20:37:23', '41');
INSERT INTO `tb_comment` VALUES ('32', '63', 'df', '2019-12-24 23:33:16', '23');

-- ----------------------------
-- Table structure for tb_forward
-- ----------------------------
DROP TABLE IF EXISTS `tb_forward`;
CREATE TABLE `tb_forward` (
  `forward_id` int(100) NOT NULL AUTO_INCREMENT,
  `user_id` int(100) NOT NULL,
  `weibo_id` int(100) NOT NULL,
  `forward_msg` varchar(256) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`forward_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_forward
-- ----------------------------

-- ----------------------------
-- Table structure for tb_notify
-- ----------------------------
DROP TABLE IF EXISTS `tb_notify`;
CREATE TABLE `tb_notify` (
  `notify_id` int(100) NOT NULL AUTO_INCREMENT,
  `content` varchar(2028) DEFAULT NULL,
  `type` varchar(2) NOT NULL,
  `target_id` int(100) NOT NULL,
  `action` int(2) DEFAULT NULL,
  `sender_id` int(100) NOT NULL,
  `sender_type` int(2) DEFAULT NULL,
  `user_id` int(100) DEFAULT NULL,
  `is_read` int(2) unsigned zerofill DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `nick_name` varchar(1024) DEFAULT NULL,
  `head_img` varchar(1024) DEFAULT NULL,
  `comment_id` int(100) DEFAULT NULL,
  `comment_reply_id` int(100) DEFAULT NULL,
  `weibo_id` int(100) DEFAULT NULL,
  `state` int(2) DEFAULT NULL COMMENT '0:无效通知，1：有效通知',
  PRIMARY KEY (`notify_id`)
) ENGINE=InnoDB AUTO_INCREMENT=279 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_notify
-- ----------------------------
INSERT INTO `tb_notify` VALUES ('32', null, '1', '23', '1', '26', '1', null, '01', '2019-12-04 17:55:32', '昵称吖', '\\upload\\user\\headimg\\26\\2019112619194981755.jpg', null, null, '60', '1');
INSERT INTO `tb_notify` VALUES ('39', '@honay 我也这样咯', '1', '26', '5', '23', '1', null, '01', '2019-12-24 19:23:56', 'honay', '\\upload\\user\\headimg\\23\\2019120221582786556.jpg', null, null, '60', '0');
INSERT INTO `tb_notify` VALUES ('40', '@honay 那就这样吧', '1', '26', '5', '23', '1', null, '01', '2019-12-20 20:39:35', 'honay', '\\upload\\user\\headimg\\23\\2019120221582786556.jpg', null, null, '58', '1');
INSERT INTO `tb_notify` VALUES ('41', '@昵称吖 《琅琊榜》中的梅长苏跟《红色》里的徐天，这两个角色有异曲同工之妙： 看似与世无争实则运筹帷幄之中； 旁人觉得手无缚鸡之力但身体里却蕴含着无比强大的能量； 俩人都有惊世骇俗之才但深藏不露', '1', '23', '5', '24', '1', null, '01', '2019-12-19 14:15:17', '就这样', '\\upload\\user\\headimg\\24\\2019111916040334326.jpg', null, null, '57', '1');
INSERT INTO `tb_notify` VALUES ('42', '很多人都说，胡歌跟张鲁一越来越像了。\n\n昨晚《南方车站的聚会》首映礼。\n\n张鲁一说，最近胡歌跑路演太辛苦了，如果有需要的话，自己可以替胡歌跑（反正观众也认不出来）[doge]。\n\n这两位不仅外形像，我觉得气质也挺像，都属于内敛不张扬的类型。\n\n甚至之前两个人塑造的经典角色我都觉得很像。\n\n《琅琊榜》中的梅长苏跟《红色》里的徐天，这两个角色有异曲同工之妙：\n\n看似与世无争实则运筹帷幄之中；\n\n旁人觉得手无缚鸡之力但身体里却蕴含着无比强大的能量；\n\n俩人都有惊世骇俗之才但深藏不露...\n\n像不像？ ', '1', '23', '5', '24', '1', null, '01', '2019-12-05 00:22:14', '就这样', '\\upload\\user\\headimg\\24\\2019111916040334326.jpg', null, null, '56', '1');
INSERT INTO `tb_notify` VALUES ('43', '存在许多在', '1', '25', '5', '23', '1', null, '01', '2019-12-06 00:32:53', 'honay', '\\upload\\user\\headimg\\23\\2019120221582786556.jpg', null, null, '40', '1');
INSERT INTO `tb_notify` VALUES ('44', '这波狗粮送给你们啦 ~~', '1', '25', '5', '23', '1', null, '01', '2019-12-19 14:17:44', 'honay', '\\upload\\user\\headimg\\23\\2019120221582786556.jpg', null, null, '24', '0');
INSERT INTO `tb_notify` VALUES ('45', '@昵称吖 《琅琊榜》中的梅长苏跟《红色》里的徐天，这两个角色有异曲同工之妙： 看似与世无争实则运筹帷幄之中； 旁人觉得手无缚鸡之力但身体里却蕴含着无比强大的能量； 俩人都有惊世骇俗之才但深藏不露', '1', '23', '5', '25', '1', null, '01', '2019-12-19 14:15:17', '陈大靓仔', '\\upload\\user\\headimg\\25\\2019111916584412221.jpg', null, null, '57', '1');
INSERT INTO `tb_notify` VALUES ('46', '要头像不？都给你们哒~', '1', '23', '5', '24', '1', null, '01', '2019-12-19 14:15:16', '就这样', '\\upload\\user\\headimg\\24\\2019111916040334326.jpg', null, null, '23', '1');
INSERT INTO `tb_notify` VALUES ('48', '千年吊一回 \n           -- double', '1', '25', '5', '23', '1', null, '01', '2019-12-05 00:43:52', 'honay', '\\upload\\user\\headimg\\23\\2019120221582786556.jpg', null, null, '17', '1');
INSERT INTO `tb_notify` VALUES ('70', null, '1', '23', '1', '26', '1', null, '01', '2019-12-05 18:54:52', '昵称吖', '\\upload\\user\\headimg\\26\\2019112619194981755.jpg', null, null, '63', '1');
INSERT INTO `tb_notify` VALUES ('71', '@honay 《琅琊榜》中的梅长苏跟《红色》里的徐天，这两个角色有异曲同工之妙： 看似与世无争实则运筹帷幄之中； 旁人觉得手无缚鸡之力但身体里却蕴含着无比强大的能量； 俩人都有惊世骇俗之才但深藏不露 ', '1', '26', '5', '23', '1', null, '01', '2019-12-21 00:01:12', 'honay', '\\upload\\user\\headimg\\23\\2019120515015134687.jpg', null, null, '63', '0');
INSERT INTO `tb_notify` VALUES ('80', '今天最新是我哒', '1', '26', '5', '23', '1', null, '01', '2019-12-06 00:29:42', 'honay', '\\upload\\user\\headimg\\23\\2019120519450515620.jpg', null, null, '49', '1');
INSERT INTO `tb_notify` VALUES ('81', '士大夫', '1', '25', '5', '23', '1', null, '01', '2019-12-06 00:32:57', 'honay', '\\upload\\user\\headimg\\23\\2019120519450515620.jpg', null, null, '41', '1');
INSERT INTO `tb_notify` VALUES ('82', '@honay 我也这样咯', '1', '24', '5', '23', '1', null, '01', '2019-12-19 14:17:42', 'honay', '\\upload\\user\\headimg\\23\\2019120519450515620.jpg', null, null, '59', '1');
INSERT INTO `tb_notify` VALUES ('101', null, '1', '30', '1', '26', '1', null, '01', '2019-12-12 15:01:48', '昵称吖', '\\upload\\user\\headimg\\26\\2019112619194981755.jpg', null, null, '64', '1');
INSERT INTO `tb_notify` VALUES ('102', null, '1', '25', '1', '26', '1', null, '00', '2019-12-12 15:12:30', '昵称吖', '\\upload\\user\\headimg\\26\\2019112619194981755.jpg', null, null, '65', '1');
INSERT INTO `tb_notify` VALUES ('122', null, '1', '30', '4', '24', '1', null, '00', '2019-12-13 13:28:50', '就这样', '\\upload\\user\\headimg\\24\\2019120914463475023.jpg', null, null, null, '1');
INSERT INTO `tb_notify` VALUES ('130', '分享图片', '1', '23', '5', '30', '1', null, '01', '2019-12-24 19:42:28', '用户46484987489', null, null, null, '69', '1');
INSERT INTO `tb_notify` VALUES ('131', '分享图片', '1', '23', '5', '30', '1', null, '01', '2019-12-24 19:42:28', '用户46484987489', null, null, null, '68', '1');
INSERT INTO `tb_notify` VALUES ('132', '@昵称吖 的士大夫首发式', '1', '23', '5', '30', '1', null, '01', '2019-12-23 18:32:04', '用户46484987489', null, null, null, '61', '1');
INSERT INTO `tb_notify` VALUES ('133', '@昵称吖 《琅琊榜》中的梅长苏跟《红色》里的徐天，这两个角色有异曲同工之妙： 看似与世无争实则运筹帷幄之中； 旁人觉得手无缚鸡之力但身体里却蕴含着无比强大的能量； 俩人都有惊世骇俗之才但深藏不露', '1', '23', '5', '30', '1', null, '01', '2019-12-19 14:15:17', '用户46484987489', null, null, null, '57', '1');
INSERT INTO `tb_notify` VALUES ('136', null, '1', '30', '4', '25', '1', null, '00', '2019-12-13 16:25:25', '陈大靓仔', '\\upload\\user\\headimg\\25\\2019111916584412221.jpg', null, null, null, '1');
INSERT INTO `tb_notify` VALUES ('140', '@陈大靓仔 收到', '1', '26', '5', '23', '1', null, '01', '2019-12-24 19:20:13', 'honay', '\\upload\\user\\headimg\\23\\2019121119055923077.jpg', null, null, '65', '0');
INSERT INTO `tb_notify` VALUES ('141', '往事清零', '1', '26', '5', '23', '1', null, '01', '2019-12-24 19:43:43', 'honay', '\\upload\\user\\headimg\\23\\2019121119055923077.jpg', null, null, '48', '0');
INSERT INTO `tb_notify` VALUES ('143', '@用户46484987489 hello，微博大佬。。', '1', '26', '5', '23', '1', null, '01', '2019-12-19 14:05:40', 'honay', '\\upload\\user\\headimg\\23\\2019121119055923077.jpg', null, null, '64', '1');
INSERT INTO `tb_notify` VALUES ('144', '@陈大靓仔 收到', '1', '26', '5', '24', '1', null, '01', '2019-12-24 19:20:13', '就这样', '\\upload\\user\\headimg\\24\\2019120914463475023.jpg', null, null, '65', '0');
INSERT INTO `tb_notify` VALUES ('172', '分享图片', '1', '23', '5', '31', '1', null, '01', '2019-12-24 19:42:28', '用户590065088', '\\upload\\user\\headimg\\31\\2019122021073634147.jpg', null, null, '68', '1');
INSERT INTO `tb_notify` VALUES ('173', '@陈大靓仔 收到', '1', '26', '5', '31', '1', null, '01', '2019-12-24 19:20:13', '用户590065088', '\\upload\\user\\headimg\\31\\2019122021073634147.jpg', null, null, '65', '0');
INSERT INTO `tb_notify` VALUES ('174', '@honay 我也这样咯', '1', '24', '5', '31', '1', null, '01', '2019-12-20 21:11:06', '用户590065088', '\\upload\\user\\headimg\\31\\2019122021073634147.jpg', null, null, '59', '1');
INSERT INTO `tb_notify` VALUES ('179', null, '1', '23', '4', '24', '1', null, '01', '2019-12-20 23:19:08', '就这样', '\\upload\\user\\headimg\\24\\2019120914463475023.jpg', null, null, null, '1');
INSERT INTO `tb_notify` VALUES ('180', '分享图片', '1', '23', '5', '24', '1', null, '01', '2019-12-24 19:42:28', '就这样', '\\upload\\user\\headimg\\24\\2019120914463475023.jpg', null, null, '69', '1');
INSERT INTO `tb_notify` VALUES ('182', null, '1', '24', '3', '23', '1', null, '00', '2019-12-20 23:36:06', 'honay', '\\upload\\user\\headimg\\23\\2019121919124816393.jpg', '6', null, null, '1');
INSERT INTO `tb_notify` VALUES ('183', null, '1', '24', '3', '23', '1', null, '00', '2019-12-20 23:44:45', 'honay', '\\upload\\user\\headimg\\23\\2019121919124816393.jpg', '6', null, null, '1');
INSERT INTO `tb_notify` VALUES ('184', '@honay 《琅琊榜》中的梅长苏跟《红色》里的徐天，这两个角色有异曲同工之妙： 看似与世无争实则运筹帷幄之中； 旁人觉得手无缚鸡之力但身体里却蕴含着无比强大的能量； 俩人都有惊世骇俗之才但深藏不露 ', '1', '26', '5', '23', '1', null, '00', '2019-12-21 00:01:12', 'honay', '\\upload\\user\\headimg\\23\\2019121919124816393.jpg', null, null, '63', '0');
INSERT INTO `tb_notify` VALUES ('185', null, '1', '24', '3', '26', '1', null, '00', '2019-12-21 17:38:40', '昵称吖', '\\upload\\user\\headimg\\26\\2019112619194981755.jpg', '6', null, null, '1');
INSERT INTO `tb_notify` VALUES ('187', null, '1', '24', '3', '25', '1', null, '00', '2019-12-21 20:44:56', '陈大靓仔', '\\upload\\user\\headimg\\25\\2019111916584412221.jpg', '6', null, null, '1');
INSERT INTO `tb_notify` VALUES ('188', null, '1', '26', '3', '23', '1', null, '01', '2019-12-21 20:56:35', 'honay', '\\upload\\user\\headimg\\23\\2019121919124816393.jpg', '6', null, null, '1');
INSERT INTO `tb_notify` VALUES ('189', null, '1', '26', '3', '23', '1', null, '01', '2019-12-21 22:38:28', 'honay', '\\upload\\user\\headimg\\23\\2019121919124816393.jpg', '6', null, null, '1');
INSERT INTO `tb_notify` VALUES ('190', null, '1', '25', '3', '23', '1', null, '00', '2019-12-21 22:40:47', 'honay', '\\upload\\user\\headimg\\23\\2019121919124816393.jpg', '6', null, null, '1');
INSERT INTO `tb_notify` VALUES ('199', '妙啊', '1', '31', '5', '23', '1', null, '00', '2019-12-24 19:46:20', 'honay', '\\upload\\user\\headimg\\23\\2019122214360727056.jpg', null, null, '72', '0');
INSERT INTO `tb_notify` VALUES ('200', null, '1', '23', '4', '35', '1', null, '01', '2019-12-24 10:01:33', '用户624355565', '../resources/images/user.jpg', null, null, null, '1');
INSERT INTO `tb_notify` VALUES ('202', null, '1', '30', '4', '35', '1', null, '00', '2019-12-24 10:01:54', '用户624355565', '../resources/images/user.jpg', null, null, null, '1');
INSERT INTO `tb_notify` VALUES ('203', null, '1', '25', '4', '35', '1', null, '00', '2019-12-24 10:01:57', '用户624355565', '../resources/images/user.jpg', null, null, null, '1');
INSERT INTO `tb_notify` VALUES ('205', '分享图片', '1', '23', '5', '35', '1', null, '01', '2019-12-24 19:42:28', '用户624355565', '../resources/images/user.jpg', null, null, '73', '1');
INSERT INTO `tb_notify` VALUES ('206', null, '1', '23', '3', '35', '1', null, '01', '2019-12-24 10:02:37', '用户624355565', '../resources/images/user.jpg', '11', null, null, '1');
INSERT INTO `tb_notify` VALUES ('209', null, '1', '35', '4', '26', '1', null, '00', '2019-12-24 10:16:25', '昵称吖', '\\upload\\user\\headimg\\26\\2019112619194981755.jpg', null, null, null, '1');
INSERT INTO `tb_notify` VALUES ('213', '我有2条春', '1', '31', '5', '23', '1', null, '00', '2019-12-24 19:45:17', 'honay', '\\upload\\user\\headimg\\23\\2019122410043767073.jpg', null, null, '70', '0');
INSERT INTO `tb_notify` VALUES ('214', '第一条', '1', '31', '5', '23', '1', null, '00', '2019-12-24 19:46:16', 'honay', '\\upload\\user\\headimg\\23\\2019122410043767073.jpg', null, null, '71', '0');
INSERT INTO `tb_notify` VALUES ('218', null, '1', '25', '4', '41', '1', null, '00', '2019-12-24 20:39:04', '用户293494327', '../resources/images/user.jpg', null, null, null, '1');
INSERT INTO `tb_notify` VALUES ('221', '分享图片', '1', '40', '5', '42', '1', null, '01', '2019-12-24 22:06:41', '用户400686915', '../resources/images/user.jpg', null, null, '80', '1');
INSERT INTO `tb_notify` VALUES ('222', '@昵称吖 对方的', '1', '23', '5', '42', '1', null, '01', '2019-12-24 22:17:10', '用户400686915', '../resources/images/user.jpg', null, null, '76', '1');
INSERT INTO `tb_notify` VALUES ('251', null, '1', '23', '3', '40', '1', null, '01', '2019-12-24 23:00:32', '用户237728038', '../resources/images/user.jpg', '20', null, null, '1');
INSERT INTO `tb_notify` VALUES ('252', null, '1', '23', '3', '40', '1', null, '01', '2019-12-24 23:00:35', '用户237728038', '../resources/images/user.jpg', '20', null, null, '1');
INSERT INTO `tb_notify` VALUES ('253', '分享图片', '1', '23', '5', '40', '1', null, '01', '2019-12-24 23:00:48', '用户237728038', '../resources/images/user.jpg', null, null, '73', '1');
INSERT INTO `tb_notify` VALUES ('255', null, '1', '41', '3', '23', '1', null, '00', '2019-12-24 23:04:22', 'honay', '\\upload\\user\\headimg\\23\\2019122410043767073.jpg', '22', null, null, '1');
INSERT INTO `tb_notify` VALUES ('256', null, '1', '41', '3', '23', '1', null, '00', '2019-12-24 23:33:11', 'honay', '\\upload\\user\\headimg\\23\\2019122410043767073.jpg', '22', null, null, '1');
INSERT INTO `tb_notify` VALUES ('258', null, '1', '26', '3', '23', '1', null, '00', '2019-12-24 23:33:22', 'honay', '\\upload\\user\\headimg\\23\\2019122410043767073.jpg', '4', null, null, '1');
INSERT INTO `tb_notify` VALUES ('273', null, '1', '26', '4', '40', '1', null, '00', '2019-12-24 23:54:28', '用户237728038', '../resources/images/user.jpg', null, null, null, '1');
INSERT INTO `tb_notify` VALUES ('276', null, '1', '35', '4', '23', '1', null, '00', '2019-12-25 00:00:07', 'honay', '\\upload\\user\\headimg\\23\\2019122410043767073.jpg', null, null, null, '1');
INSERT INTO `tb_notify` VALUES ('278', null, '1', '24', '4', '23', '1', null, '00', '2019-12-25 00:00:22', 'honay', '\\upload\\user\\headimg\\23\\2019122410043767073.jpg', null, null, null, '1');

-- ----------------------------
-- Table structure for tb_praise
-- ----------------------------
DROP TABLE IF EXISTS `tb_praise`;
CREATE TABLE `tb_praise` (
  `praise_id` int(200) NOT NULL AUTO_INCREMENT,
  `user_id` int(100) NOT NULL,
  `state` int(2) NOT NULL,
  `create_time` datetime NOT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `weibo_id` int(200) NOT NULL,
  PRIMARY KEY (`praise_id`),
  KEY `fk_praise_weibo` (`weibo_id`),
  CONSTRAINT `fk_praise_weibo` FOREIGN KEY (`weibo_id`) REFERENCES `tb_weibo` (`weibo_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_praise
-- ----------------------------
INSERT INTO `tb_praise` VALUES ('22', '23', '0', '2019-12-04 21:07:29', '2019-12-24 19:23:56', '60');
INSERT INTO `tb_praise` VALUES ('23', '23', '0', '2019-12-04 21:07:32', '2019-12-20 20:39:35', '58');
INSERT INTO `tb_praise` VALUES ('24', '24', '0', '2019-12-04 21:16:56', '2019-12-19 14:15:17', '57');
INSERT INTO `tb_praise` VALUES ('26', '24', '1', '2019-12-04 21:16:59', '2019-12-04 21:16:59', '37');
INSERT INTO `tb_praise` VALUES ('27', '24', '1', '2019-12-04 21:17:01', '2019-12-04 21:17:01', '59');
INSERT INTO `tb_praise` VALUES ('28', '23', '0', '2019-12-04 21:40:29', '2019-12-06 00:32:53', '40');
INSERT INTO `tb_praise` VALUES ('29', '23', '0', '2019-12-04 21:40:33', '2019-12-19 14:17:44', '24');
INSERT INTO `tb_praise` VALUES ('30', '25', '1', '2019-12-04 21:40:50', '2019-12-04 21:40:50', '57');
INSERT INTO `tb_praise` VALUES ('31', '23', '1', '2019-12-05 00:21:24', '2019-12-19 13:55:47', '57');
INSERT INTO `tb_praise` VALUES ('33', '24', '0', '2019-12-05 00:22:17', '2019-12-19 14:15:16', '23');
INSERT INTO `tb_praise` VALUES ('34', '24', '1', '2019-12-05 00:22:18', '2019-12-05 00:22:18', '22');
INSERT INTO `tb_praise` VALUES ('35', '23', '1', '2019-12-05 00:43:50', '2019-12-05 00:43:50', '23');
INSERT INTO `tb_praise` VALUES ('37', '23', '0', '2019-12-05 00:43:54', '2019-12-19 13:58:51', '16');
INSERT INTO `tb_praise` VALUES ('39', '23', '0', '2019-12-05 19:03:14', '2019-12-21 00:01:12', '63');
INSERT INTO `tb_praise` VALUES ('41', '23', '0', '2019-12-06 00:29:41', '2019-12-06 00:29:42', '49');
INSERT INTO `tb_praise` VALUES ('42', '23', '0', '2019-12-06 00:32:44', '2019-12-06 00:32:57', '41');
INSERT INTO `tb_praise` VALUES ('43', '23', '0', '2019-12-09 14:35:09', '2019-12-19 14:17:42', '59');
INSERT INTO `tb_praise` VALUES ('45', '23', '1', '2019-12-13 15:29:49', '2019-12-24 19:46:11', '69');
INSERT INTO `tb_praise` VALUES ('46', '30', '1', '2019-12-13 16:02:13', '2019-12-13 16:02:49', '69');
INSERT INTO `tb_praise` VALUES ('47', '30', '0', '2019-12-13 16:02:24', '2019-12-13 16:02:24', '68');
INSERT INTO `tb_praise` VALUES ('49', '30', '0', '2019-12-13 16:02:38', '2019-12-13 16:02:38', '57');
INSERT INTO `tb_praise` VALUES ('50', '23', '1', '2019-12-13 16:04:21', '2019-12-23 18:32:03', '68');
INSERT INTO `tb_praise` VALUES ('51', '23', '0', '2019-12-19 13:08:36', '2019-12-24 19:20:13', '65');
INSERT INTO `tb_praise` VALUES ('52', '23', '0', '2019-12-19 13:08:55', '2019-12-24 19:43:43', '48');
INSERT INTO `tb_praise` VALUES ('53', '23', '0', '2019-12-19 14:05:40', '2019-12-19 14:05:40', '64');
INSERT INTO `tb_praise` VALUES ('54', '24', '1', '2019-12-19 14:15:24', '2019-12-19 14:15:24', '65');
INSERT INTO `tb_praise` VALUES ('55', '31', '1', '2019-12-20 21:09:09', '2019-12-20 21:11:11', '68');
INSERT INTO `tb_praise` VALUES ('56', '31', '1', '2019-12-20 21:11:04', '2019-12-20 21:11:04', '65');
INSERT INTO `tb_praise` VALUES ('57', '31', '1', '2019-12-20 21:11:06', '2019-12-20 21:11:06', '59');
INSERT INTO `tb_praise` VALUES ('58', '31', '0', '2019-12-20 21:11:12', '2019-12-20 21:11:13', '70');
INSERT INTO `tb_praise` VALUES ('59', '24', '1', '2019-12-20 23:19:17', '2019-12-20 23:19:17', '69');
INSERT INTO `tb_praise` VALUES ('60', '25', '1', '2019-12-23 20:04:25', '2019-12-23 20:04:25', '40');
INSERT INTO `tb_praise` VALUES ('61', '23', '0', '2019-12-23 21:54:47', '2019-12-24 19:46:20', '72');
INSERT INTO `tb_praise` VALUES ('62', '35', '1', '2019-12-24 10:02:08', '2019-12-24 10:02:08', '73');
INSERT INTO `tb_praise` VALUES ('64', '23', '1', '2019-12-24 19:23:24', '2019-12-24 19:42:29', '73');
INSERT INTO `tb_praise` VALUES ('65', '23', '0', '2019-12-24 19:44:58', '2019-12-24 19:45:17', '70');
INSERT INTO `tb_praise` VALUES ('66', '23', '0', '2019-12-24 19:45:00', '2019-12-24 19:46:16', '71');
INSERT INTO `tb_praise` VALUES ('74', '40', '1', '2019-12-24 23:00:48', '2019-12-24 23:00:48', '73');

-- ----------------------------
-- Table structure for tb_reply
-- ----------------------------
DROP TABLE IF EXISTS `tb_reply`;
CREATE TABLE `tb_reply` (
  `reply_id` int(200) NOT NULL AUTO_INCREMENT,
  `comment_id` int(200) NOT NULL,
  `from_id` int(200) NOT NULL,
  `from_nick_name` varchar(256) DEFAULT NULL,
  `from_head_img` varchar(256) DEFAULT NULL,
  `to_id` int(200) NOT NULL,
  `to_nick_name` varchar(256) DEFAULT NULL,
  `to_head_img` varchar(256) DEFAULT NULL,
  `content` varchar(256) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`reply_id`),
  KEY `fk_reply_comment` (`comment_id`),
  KEY `fk_reply_from` (`from_id`),
  KEY `fk_reply_to` (`to_id`),
  CONSTRAINT `fk_reply_comment` FOREIGN KEY (`comment_id`) REFERENCES `tb_comment` (`comment_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_reply
-- ----------------------------
INSERT INTO `tb_reply` VALUES ('40', '4', '26', '昵称吖', '\\upload\\user\\headimg\\26\\2019112619194981755.jpg', '26', '昵称吖', '\\upload\\user\\headimg\\26\\2019112619194981755.jpg', '沙雕爱上你', '2019-12-09 14:22:01');
INSERT INTO `tb_reply` VALUES ('45', '6', '26', null, null, '24', '就这样', '\\upload\\user\\headimg\\24\\2019120914463475023.jpg', '唱征服', '2019-12-21 17:38:40');
INSERT INTO `tb_reply` VALUES ('46', '6', '25', null, null, '24', '就这样', '\\upload\\user\\headimg\\24\\2019120914463475023.jpg', 'chen回复就这样', '2019-12-21 20:44:56');
INSERT INTO `tb_reply` VALUES ('48', '6', '23', null, null, '26', '昵称吖', '\\upload\\user\\headimg\\26\\2019112619194981755.jpg', '就这样把你征服。。', '2019-12-21 22:38:28');
INSERT INTO `tb_reply` VALUES ('50', '5', '23', null, null, '23', 'honay', '\\upload\\user\\headimg\\23\\2019121919124816393.jpg', '独秀', '2019-12-21 22:42:10');
INSERT INTO `tb_reply` VALUES ('51', '11', '23', null, null, '23', 'honay', '\\upload\\user\\headimg\\23\\2019122214360727056.jpg', '别删啊，给你讲好把', '2019-12-23 11:39:43');
INSERT INTO `tb_reply` VALUES ('52', '11', '23', null, null, '23', 'honay', '\\upload\\user\\headimg\\23\\2019122214360727056.jpg', '不，我不要', '2019-12-23 11:47:17');
INSERT INTO `tb_reply` VALUES ('53', '11', '23', null, null, '23', 'honay', '\\upload\\user\\headimg\\23\\2019122214360727056.jpg', '好哈奥让你', '2019-12-23 11:55:06');
INSERT INTO `tb_reply` VALUES ('54', '11', '35', null, null, '23', 'honay', '\\upload\\user\\headimg\\23\\2019122323122336166.jpg', 'test', '2019-12-24 10:02:37');
INSERT INTO `tb_reply` VALUES ('70', '22', '23', null, null, '41', 'fuck', '../resources/images/user.jpg', 'df', '2019-12-24 23:04:22');
INSERT INTO `tb_reply` VALUES ('78', '22', '23', null, null, '41', 'fuck', '../resources/images/user.jpg', 'df', '2019-12-24 23:33:11');
INSERT INTO `tb_reply` VALUES ('79', '4', '23', null, null, '26', '昵称吖', '\\upload\\user\\headimg\\26\\2019112619194981755.jpg', 'df', '2019-12-24 23:33:21');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `head_img` varchar(255) DEFAULT NULL,
  `secret_answer` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `profile` varchar(1024) DEFAULT NULL COMMENT '个人简介',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('23', 'you', '123456', 'honay', '1', '上海市', '卢湾区', '1999-01-20', '\\upload\\user\\headimg\\23\\2019122410043767073.jpg', null, null, '2019-12-24 10:04:38', '你看，努力多好');
INSERT INTO `tb_user` VALUES ('24', 'hua', '123456', '就这样', '1', '北京市', '崇文区', '2019-11-05', '\\upload\\user\\headimg\\24\\2019120914463475023.jpg', null, null, '2019-12-09 14:46:35', null);
INSERT INTO `tb_user` VALUES ('25', 'chen', '123456', '陈大靓仔', '1', '天津市', '河东区', '2019-11-17', '\\upload\\user\\headimg\\25\\2019111916584412221.jpg', null, null, '2019-12-23 20:15:24', '');
INSERT INTO `tb_user` VALUES ('26', 'test', '123456', '昵称吖', '1', '天津市', '河东区', '2019-11-26', '\\upload\\user\\headimg\\26\\2019112619194981755.jpg', null, '2019-11-21 16:57:08', '2019-11-26 19:19:50', null);
INSERT INTO `tb_user` VALUES ('30', 'test2', '123456', '用户46484987489', '0', null, null, '2019-12-11', '../resources/images/user.jpg', null, '2019-12-05 00:34:14', '2019-12-05 19:37:49', '');
INSERT INTO `tb_user` VALUES ('31', '2条春', '12345', '用户590065088', '0', '北京市', '东城区', '2021-02-01', '\\upload\\user\\headimg\\31\\2019122021073634147.jpg', null, '2019-12-20 21:06:07', '2019-12-20 21:13:27', '');
INSERT INTO `tb_user` VALUES ('32', 'test3', '123456', '用户126217664', null, null, null, '2019-12-18', '../resources/images/user.jpg', null, '2019-12-24 09:59:53', null, null);
INSERT INTO `tb_user` VALUES ('33', 'test4', '123456', '用户465333592', null, null, null, '2019-12-18', '../resources/images/user.jpg', null, '2019-12-24 10:00:10', null, null);
INSERT INTO `tb_user` VALUES ('34', 'chenhua', '123456', '用户402309794', null, null, null, '2019-12-18', '../resources/images/user.jpg', null, '2019-12-24 10:00:31', null, null);
INSERT INTO `tb_user` VALUES ('35', 'huayou', '123456', '用户624355565', null, null, null, '2019-12-18', '../resources/images/user.jpg', null, '2019-12-24 10:01:05', null, null);
INSERT INTO `tb_user` VALUES ('36', 'abc', '123456', '用户406045554', null, null, null, '2019-12-10', '../resources/images/user.jpg', null, '2019-12-24 18:46:47', null, null);
INSERT INTO `tb_user` VALUES ('37', '111', '123456', '用户930197690', null, null, null, '2019-12-12', '../resources/images/user.jpg', null, '2019-12-24 18:48:48', null, null);
INSERT INTO `tb_user` VALUES ('38', 'b', '123456', '用户307596383', null, null, null, '2019-12-04', '\\upload\\user\\headimg\\38\\2019122418535843931.jpg', null, '2019-12-24 18:50:13', '2019-12-24 18:53:58', null);
INSERT INTO `tb_user` VALUES ('39', 'ww', '123456', '用户364613619', null, null, null, '2019-12-12', '../resources/images/user.jpg', null, '2019-12-24 18:54:45', null, null);
INSERT INTO `tb_user` VALUES ('40', 'c', '123456', '用户237728038', '0', '上海市', '徐汇区', '2019-12-18', '../resources/images/user.jpg', null, '2019-12-24 18:57:56', '2019-12-24 22:03:55', '');
INSERT INTO `tb_user` VALUES ('41', '叼你', '123456789', 'fuck', '1', '重庆市', '涪陵区', '2019-12-03', '../resources/images/user.jpg', null, '2019-12-24 20:36:12', '2019-12-24 20:39:52', 'iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii');
INSERT INTO `tb_user` VALUES ('42', 'abcd', '123456', '用户400686915', null, null, null, '2019-11-27', '../resources/images/user.jpg', null, '2019-12-24 22:06:21', null, null);

-- ----------------------------
-- Table structure for tb_user_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_relation`;
CREATE TABLE `tb_user_relation` (
  `user_relation_id` int(100) NOT NULL AUTO_INCREMENT,
  `fans_id` int(100) NOT NULL,
  `stars_id` int(100) NOT NULL,
  `state` int(2) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_relation_id`),
  KEY `fk_user_relation_fans` (`fans_id`),
  KEY `fk_user_relation_stars` (`stars_id`),
  CONSTRAINT `fk_user_relation_fans` FOREIGN KEY (`fans_id`) REFERENCES `tb_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_relation_stars` FOREIGN KEY (`stars_id`) REFERENCES `tb_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_relation
-- ----------------------------
INSERT INTO `tb_user_relation` VALUES ('21', '23', '24', '1', '2019-11-26 15:58:05', '2019-12-25 00:00:22');
INSERT INTO `tb_user_relation` VALUES ('44', '24', '30', '0', '2019-12-13 13:28:50', '2019-12-24 23:34:29');
INSERT INTO `tb_user_relation` VALUES ('48', '25', '30', '0', '2019-12-13 16:25:24', '2019-12-13 16:25:24');
INSERT INTO `tb_user_relation` VALUES ('65', '35', '23', '1', '2019-12-24 10:01:33', '2019-12-25 00:00:07');
INSERT INTO `tb_user_relation` VALUES ('66', '35', '26', '-1', '2019-12-24 10:01:50', '2019-12-24 18:58:33');
INSERT INTO `tb_user_relation` VALUES ('67', '35', '30', '0', '2019-12-24 10:01:54', null);
INSERT INTO `tb_user_relation` VALUES ('68', '35', '25', '0', '2019-12-24 10:01:57', null);
INSERT INTO `tb_user_relation` VALUES ('74', '41', '25', '0', '2019-12-24 20:39:03', null);
INSERT INTO `tb_user_relation` VALUES ('108', '40', '26', '0', '2019-12-24 23:54:28', null);

-- ----------------------------
-- Table structure for tb_weibo
-- ----------------------------
DROP TABLE IF EXISTS `tb_weibo`;
CREATE TABLE `tb_weibo` (
  `weibo_id` int(200) NOT NULL AUTO_INCREMENT,
  `user_id` int(255) NOT NULL,
  `content` varchar(3000) DEFAULT NULL,
  `publish_time` datetime NOT NULL,
  `comment_count` int(10) DEFAULT NULL,
  `praise_count` int(10) DEFAULT NULL,
  `foward_count` int(10) DEFAULT NULL,
  PRIMARY KEY (`weibo_id`),
  KEY `fk_weibo_user` (`user_id`),
  CONSTRAINT `fk_weibo_user` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_weibo
-- ----------------------------
INSERT INTO `tb_weibo` VALUES ('2', '24', '今晚夜色真美', '2019-11-16 21:45:30', null, null, null);
INSERT INTO `tb_weibo` VALUES ('4', '24', '今天熱搜沙发我坐定哒~', '2019-11-18 20:02:50', null, null, null);
INSERT INTO `tb_weibo` VALUES ('15', '23', '最新的微博呀', '2019-11-18 21:14:19', null, null, null);
INSERT INTO `tb_weibo` VALUES ('16', '23', '我才是最新的微博好吗', '2019-11-18 21:16:59', null, null, null);
INSERT INTO `tb_weibo` VALUES ('22', '24', '沙发是我的好吧，还抢什么', '2019-11-19 15:56:26', null, null, null);
INSERT INTO `tb_weibo` VALUES ('23', '23', '要头像不？都给你们哒~', '2019-11-19 17:46:29', null, null, null);
INSERT INTO `tb_weibo` VALUES ('24', '25', '这波狗粮送给你们啦 ~~', '2019-11-19 20:06:51', null, null, null);
INSERT INTO `tb_weibo` VALUES ('37', '24', '我是今天最新的微博哒`', '2019-11-21 12:37:46', null, null, null);
INSERT INTO `tb_weibo` VALUES ('38', '25', '我在测试发布微博呢', '2019-11-25 11:34:57', null, null, null);
INSERT INTO `tb_weibo` VALUES ('40', '25', '存在许多在', '2019-11-25 11:37:15', null, '1', null);
INSERT INTO `tb_weibo` VALUES ('41', '25', '士大夫', '2019-11-25 11:38:32', null, null, null);
INSERT INTO `tb_weibo` VALUES ('48', '26', '往事清零', '2019-11-26 19:10:56', null, '0', null);
INSERT INTO `tb_weibo` VALUES ('49', '26', '今天最新是我哒', '2019-11-26 19:14:42', null, null, null);
INSERT INTO `tb_weibo` VALUES ('57', '23', '@昵称吖 《琅琊榜》中的梅长苏跟《红色》里的徐天，这两个角色有异曲同工之妙： 看似与世无争实则运筹帷幄之中； 旁人觉得手无缚鸡之力但身体里却蕴含着无比强大的能量； 俩人都有惊世骇俗之才但深藏不露', '2019-12-02 15:18:45', null, null, null);
INSERT INTO `tb_weibo` VALUES ('58', '26', '@honay 那就这样吧', '2019-12-03 14:27:57', null, null, null);
INSERT INTO `tb_weibo` VALUES ('59', '24', '@honay 我也这样咯', '2019-12-03 14:39:46', null, null, null);
INSERT INTO `tb_weibo` VALUES ('60', '26', '@honay 我也这样咯', '2019-12-04 17:55:32', null, '0', null);
INSERT INTO `tb_weibo` VALUES ('63', '26', '@honay 《琅琊榜》中的梅长苏跟《红色》里的徐天，这两个角色有异曲同工之妙： 看似与世无争实则运筹帷幄之中； 旁人觉得手无缚鸡之力但身体里却蕴含着无比强大的能量； 俩人都有惊世骇俗之才但深藏不露 ', '2019-12-05 18:54:48', null, null, null);
INSERT INTO `tb_weibo` VALUES ('64', '26', '@用户46484987489 hello，微博大佬。。', '2019-12-12 15:01:46', null, null, null);
INSERT INTO `tb_weibo` VALUES ('65', '26', '@陈大靓仔 收到', '2019-12-12 15:12:30', null, '2', null);
INSERT INTO `tb_weibo` VALUES ('68', '23', '分享图片', '2019-12-13 12:16:01', null, '2', null);
INSERT INTO `tb_weibo` VALUES ('69', '23', '分享图片', '2019-12-13 15:00:25', null, '3', null);
INSERT INTO `tb_weibo` VALUES ('70', '31', '我有2条春', '2019-12-20 21:06:56', null, '0', null);
INSERT INTO `tb_weibo` VALUES ('71', '31', '第一条', '2019-12-20 21:07:19', null, '0', null);
INSERT INTO `tb_weibo` VALUES ('72', '31', '妙啊', '2019-12-20 21:12:31', null, '0', null);
INSERT INTO `tb_weibo` VALUES ('73', '23', '分享图片', '2019-12-23 23:13:58', null, '3', null);

-- ----------------------------
-- Table structure for tb_weibo_img
-- ----------------------------
DROP TABLE IF EXISTS `tb_weibo_img`;
CREATE TABLE `tb_weibo_img` (
  `weibo_img_id` int(20) NOT NULL AUTO_INCREMENT,
  `img_addr` varchar(2000) NOT NULL,
  `priority` int(3) DEFAULT NULL,
  `weibo_id` int(100) DEFAULT NULL,
  PRIMARY KEY (`weibo_img_id`),
  KEY `fk_weiboimg_weibo` (`weibo_id`),
  CONSTRAINT `fk_weiimg_weibo` FOREIGN KEY (`weibo_id`) REFERENCES `tb_weibo` (`weibo_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_weibo_img
-- ----------------------------
INSERT INTO `tb_weibo_img` VALUES ('2', '\\upload\\weibo\\author25\\2\\2019111621453161032.jpg', '1', '22');
INSERT INTO `tb_weibo_img` VALUES ('3', '\\upload\\weibo\\author25\\2\\1.jpg', '30', '22');
INSERT INTO `tb_weibo_img` VALUES ('5', '\\upload\\weibo\\author25\\2\\3.jpg', '50', '16');
INSERT INTO `tb_weibo_img` VALUES ('6', '\\upload\\weibo\\author25\\2\\4.jpg', '60', '15');
INSERT INTO `tb_weibo_img` VALUES ('7', '\\upload\\weibo\\author25\\2\\5.jpg', '70', '15');
INSERT INTO `tb_weibo_img` VALUES ('8', '\\upload\\weibo\\author25\\2\\6.jpg', '80', '15');
INSERT INTO `tb_weibo_img` VALUES ('9', '\\upload\\weibo\\author25\\2\\7.jpg', '90', '15');
INSERT INTO `tb_weibo_img` VALUES ('10', '\\upload\\weibo\\author25\\2\\8.jpg', '100', '15');
INSERT INTO `tb_weibo_img` VALUES ('11', '\\upload\\weibo\\author25\\2\\9.jpg', '110', '16');
INSERT INTO `tb_weibo_img` VALUES ('12', '\\upload\\weibo\\author25\\2\\10.jpg', '120', '16');
INSERT INTO `tb_weibo_img` VALUES ('52', '\\upload\\weibo\\author23\\4\\2019111820025018970.jpg', null, '4');
INSERT INTO `tb_weibo_img` VALUES ('53', '\\upload\\weibo\\author23\\4\\2019111820025256789.jpg', null, '4');
INSERT INTO `tb_weibo_img` VALUES ('56', '\\upload\\weibo\\author23\\23\\2019111917462869159.jpg', null, '23');
INSERT INTO `tb_weibo_img` VALUES ('57', '\\upload\\weibo\\author23\\23\\2019111917463134931.jpg', null, '23');
INSERT INTO `tb_weibo_img` VALUES ('58', '\\upload\\weibo\\author23\\23\\2019111917463155615.jpg', null, '23');
INSERT INTO `tb_weibo_img` VALUES ('59', '\\upload\\weibo\\author23\\23\\2019111917463255809.jpg', null, '23');
INSERT INTO `tb_weibo_img` VALUES ('60', '\\upload\\weibo\\author23\\23\\2019111917463257892.jpg', null, '23');
INSERT INTO `tb_weibo_img` VALUES ('61', '\\upload\\weibo\\author23\\23\\2019111917463380044.jpg', null, '23');
INSERT INTO `tb_weibo_img` VALUES ('62', '\\upload\\weibo\\author23\\23\\2019111917463333815.jpg', null, '23');
INSERT INTO `tb_weibo_img` VALUES ('63', '\\upload\\weibo\\author23\\23\\2019111917463450179.jpg', null, '58');
INSERT INTO `tb_weibo_img` VALUES ('64', '\\upload\\weibo\\author23\\23\\2019111917463548771.jpg', null, '23');
INSERT INTO `tb_weibo_img` VALUES ('65', '\\upload\\weibo\\author25\\24\\2019111920065164479.jpg', null, '24');
INSERT INTO `tb_weibo_img` VALUES ('66', '\\upload\\weibo\\author25\\24\\2019111920065368423.jpg', null, '24');
INSERT INTO `tb_weibo_img` VALUES ('67', '\\upload\\weibo\\author25\\38\\2019112511345787306.jpg', null, '38');
INSERT INTO `tb_weibo_img` VALUES ('69', '\\upload\\weibo\\author25\\40\\2019112511371524492.jpg', null, '40');
INSERT INTO `tb_weibo_img` VALUES ('70', '\\upload\\weibo\\author25\\41\\2019112511383128697.jpg', null, '41');
INSERT INTO `tb_weibo_img` VALUES ('78', '\\upload\\weibo\\author26\\48\\2019112619105669176.jpg', null, '48');
INSERT INTO `tb_weibo_img` VALUES ('83', '\\upload\\weibo\\author23\\57\\2019120215184538210.jpg', null, '57');
INSERT INTO `tb_weibo_img` VALUES ('84', '\\upload\\weibo\\author26\\63\\2019120518544844455.jpg', null, '63');
INSERT INTO `tb_weibo_img` VALUES ('85', '\\upload\\weibo\\author26\\64\\2019121215014647624.jpg', null, '64');
INSERT INTO `tb_weibo_img` VALUES ('86', '\\upload\\weibo\\author26\\64\\2019121215014790743.jpg', null, '64');
INSERT INTO `tb_weibo_img` VALUES ('87', '\\upload\\weibo\\author26\\64\\2019121215014793118.jpg', null, '64');
INSERT INTO `tb_weibo_img` VALUES ('88', '\\upload\\weibo\\author26\\65\\2019121215123049264.jpg', null, '65');
INSERT INTO `tb_weibo_img` VALUES ('89', '\\upload\\weibo\\author26\\65\\2019121215123048842.jpg', null, '65');
INSERT INTO `tb_weibo_img` VALUES ('92', '\\upload\\weibo\\author23\\68\\2019121312160074434.jpg', null, '68');
INSERT INTO `tb_weibo_img` VALUES ('93', '\\upload\\weibo\\author23\\69\\2019121315002588075.jpg', null, '69');
INSERT INTO `tb_weibo_img` VALUES ('94', '\\upload\\weibo\\author23\\69\\2019121315002532986.jpg', null, '69');
INSERT INTO `tb_weibo_img` VALUES ('95', '\\upload\\weibo\\author23\\69\\2019121315002557423.jpg', null, '69');
INSERT INTO `tb_weibo_img` VALUES ('96', '\\upload\\weibo\\author23\\69\\2019121315002535357.jpg', null, '69');
INSERT INTO `tb_weibo_img` VALUES ('97', '\\upload\\weibo\\author31\\71\\2019122021071993311.jpg', null, '71');
INSERT INTO `tb_weibo_img` VALUES ('98', '\\upload\\weibo\\author23\\73\\2019122323135873984.jpg', null, '73');
