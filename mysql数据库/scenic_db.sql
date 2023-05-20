/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : scenic_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2019-11-27 16:34:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL default '',
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_leaveword`
-- ----------------------------
DROP TABLE IF EXISTS `t_leaveword`;
CREATE TABLE `t_leaveword` (
  `leavewordId` int(11) NOT NULL auto_increment COMMENT '留言id',
  `title` varchar(60) NOT NULL COMMENT '留言标题',
  `content` varchar(2000) NOT NULL COMMENT '留言内容',
  `userObj` varchar(20) NOT NULL COMMENT '留言人',
  `leaveTime` varchar(20) default NULL COMMENT '留言时间',
  `replyContent` varchar(20) default NULL COMMENT '回复内容',
  `replyTime` varchar(20) default NULL COMMENT '回复时间',
  PRIMARY KEY  (`leavewordId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_leaveword_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_leaveword
-- ----------------------------
INSERT INTO `t_leaveword` VALUES ('1', '反映下食堂问题', '第一食堂的荤菜感觉肉太少了！', 'user1', '2019-11-08 19:15:08', '收到', '2019-11-22 00:43:29');
INSERT INTO `t_leaveword` VALUES ('2', '1111', '2222', 'user1', '2019-11-07 19:05:08', '很好', '2019-11-07 19:06:02');
INSERT INTO `t_leaveword` VALUES ('3', '很好的网站', '我可以找到好地方去玩了！', 'user1', '2019-11-25 17:16:38', '嗯  \r\n\r\n谢谢支持', '2019-11-25 17:16:52');

-- ----------------------------
-- Table structure for `t_route`
-- ----------------------------
DROP TABLE IF EXISTS `t_route`;
CREATE TABLE `t_route` (
  `routeId` int(11) NOT NULL auto_increment COMMENT '路径id',
  `startScenic` int(11) NOT NULL COMMENT '起始景点',
  `endScenic` int(11) NOT NULL COMMENT '结束景点',
  PRIMARY KEY  (`routeId`),
  KEY `startScenic` (`startScenic`),
  KEY `endScenic` (`endScenic`),
  CONSTRAINT `t_route_ibfk_1` FOREIGN KEY (`startScenic`) REFERENCES `t_scenic` (`scenicId`),
  CONSTRAINT `t_route_ibfk_2` FOREIGN KEY (`endScenic`) REFERENCES `t_scenic` (`scenicId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_route
-- ----------------------------

-- ----------------------------
-- Table structure for `t_scenic`
-- ----------------------------
DROP TABLE IF EXISTS `t_scenic`;
CREATE TABLE `t_scenic` (
  `scenicId` int(11) NOT NULL auto_increment COMMENT '景点id',
  `scenicTypeObj` int(11) NOT NULL COMMENT '景点类型',
  `scenicGrade` varchar(20) NOT NULL COMMENT '景区等级 ',
  `scenicName` varchar(40) NOT NULL COMMENT '景点名称',
  `scenicDate` varchar(20) NOT NULL,
  `scenicPhoto` varchar(60) NOT NULL COMMENT '景点照片',
  `scenicDesc` varchar(2000) NOT NULL COMMENT '景点介绍',
  `latitude` double NOT NULL COMMENT '纬度',
  `longitude` double NOT NULL COMMENT '经度',
  PRIMARY KEY  (`scenicId`),
  KEY `scenicTypeObj` (`scenicTypeObj`),
  CONSTRAINT `t_scenic_ibfk_1` FOREIGN KEY (`scenicTypeObj`) REFERENCES `t_scenictype` (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_scenic
-- ----------------------------
INSERT INTO `t_scenic` VALUES ('1', '3', '一级', '南海校区', '2019-05-24', 'upload/a6b61854-eaee-42b1-b8e1-88028c866b42.jpg', '佛山市南海区南新三路2号', '23.03364', '113.14741');
INSERT INTO `t_scenic` VALUES ('2', '2', '一级', '敬业阁', '2019-11-02', 'upload/3a215d3b-510b-457a-889f-4dec14f1b3aa.jpg', '理工学院敬业阁', '23.011368', '112.48731');
INSERT INTO `t_scenic` VALUES ('3', '2', '一级', '广东理工学院图书馆', '2019-11-06', 'upload/18aa4633-d275-4f83-911e-01c3ce285cf9.jpg', '理工学院图书馆', '23.00809', '112.4856');

-- ----------------------------
-- Table structure for `t_scenictype`
-- ----------------------------
DROP TABLE IF EXISTS `t_scenictype`;
CREATE TABLE `t_scenictype` (
  `typeId` int(11) NOT NULL auto_increment COMMENT '类型id',
  `typeName` varchar(20) NOT NULL COMMENT '类别名称',
  PRIMARY KEY  (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_scenictype
-- ----------------------------
INSERT INTO `t_scenictype` VALUES ('1', '食堂');
INSERT INTO `t_scenictype` VALUES ('2', '教学楼');
INSERT INTO `t_scenictype` VALUES ('3', '校门');
INSERT INTO `t_scenictype` VALUES ('4', '运动类');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `user_name` varchar(20) NOT NULL COMMENT 'user_name',
  `password` varchar(20) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `sex` varchar(4) NOT NULL COMMENT '性别',
  `birthday` varchar(20) default NULL COMMENT '出生日期',
  `userPhoto` varchar(60) NOT NULL COMMENT '用户照片',
  `telephone` varchar(20) default NULL COMMENT '联系电话',
  PRIMARY KEY  (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('user1', '123', '李明翠', '女', '2019-11-06', 'upload/efb61b56-3700-46db-9f99-\r\n\r\n610970404731.jpg', '13958342983');
INSERT INTO `t_userinfo` VALUES ('user2', '123', '王倩', '女', '2019-02-01', 'upload/1657fd44-209d-4650-87f5-\r\n\r\nd91160acdf46.jpg', '13573598343');
