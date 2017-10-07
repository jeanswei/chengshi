/*
Navicat MySQL Data Transfer

Source Server         : 192.168.2.150
Source Server Version : 50711
Source Host           : 192.168.2.150:3306
Source Database       : chengshi

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2017-10-06 08:30:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cs_admin_log`
-- ----------------------------
DROP TABLE IF EXISTS `cs_admin_log`;
CREATE TABLE `cs_admin_log` (
  `log_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '时间',
  `log_info` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '信息',
  `ip_address` varchar(15) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT 'ip地址',
  PRIMARY KEY (`log_id`),
  KEY `log_time` (`create_time`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台操作日志表';

-- ----------------------------
-- Records of cs_admin_log
-- ----------------------------

-- ----------------------------
-- Table structure for `cs_admin_menu`
-- ----------------------------
DROP TABLE IF EXISTS `cs_admin_menu`;
CREATE TABLE `cs_admin_menu` (
  `menu_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `pid` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '父节点',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '菜单名',
  `menu_url` varchar(50) NOT NULL DEFAULT '' COMMENT '链接',
  `icon` varchar(20) NOT NULL DEFAULT '' COMMENT '图标',
  `sort_no` int(10) NOT NULL DEFAULT '255' COMMENT '排序',
  PRIMARY KEY (`menu_id`),
  KEY `parent_id` (`pid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COMMENT='后台菜单表';

-- ----------------------------
-- Records of cs_admin_menu
-- ----------------------------
INSERT INTO `cs_admin_menu` VALUES ('1', '2', '用户管理', '/admin/user', '', '2');
INSERT INTO `cs_admin_menu` VALUES ('2', '0', '系统管理', '#', '', '10');
INSERT INTO `cs_admin_menu` VALUES ('3', '2', '菜单管理', '/admin/menu', '', '3');
INSERT INTO `cs_admin_menu` VALUES ('7', '0', '商品管理', '#', '', '2');
INSERT INTO `cs_admin_menu` VALUES ('8', '0', '商城页面', '#', '', '3');
INSERT INTO `cs_admin_menu` VALUES ('9', '8', '商城首页', '/admin/page/index', '', '1');
INSERT INTO `cs_admin_menu` VALUES ('11', '7', '商品列表', '/admin/goodsList', '', '1');
INSERT INTO `cs_admin_menu` VALUES ('13', '8', '商品详情页', '/admin/page/goodsDetail', '', '2');
INSERT INTO `cs_admin_menu` VALUES ('14', '0', '营销管理', '', '', '3');
INSERT INTO `cs_admin_menu` VALUES ('15', '14', '优惠券', '/admin/couponList', '', '1');

-- ----------------------------
-- Table structure for `cs_admin_user`
-- ----------------------------
DROP TABLE IF EXISTS `cs_admin_user`;
CREATE TABLE `cs_admin_user` (
  `user_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(60) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '密码',
  `mobile` varchar(20) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '联系电话',
  `email` varchar(60) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '邮箱',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `last_login` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '上次登录时间',
  `last_ip` varchar(15) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '上次登录ip',
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '1.有效2.禁用',
  PRIMARY KEY (`user_id`),
  KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='后台用户表';

-- ----------------------------
-- Records of cs_admin_user
-- ----------------------------
INSERT INTO `cs_admin_user` VALUES ('1', 'admin', '21218CCA77804D2BA1922C33E0151105', '18305113589', '1023544696@qq.com', '2017-08-18 08:09:49', '2017-10-05 09:04:02', '192.168.2.161', '1');
INSERT INTO `cs_admin_user` VALUES ('12', 'xuxinlong', '21218CCA77804D2BA1922C33E0151105', '18305113589', '1023544696@qq.com', '2017-08-18 00:29:43', '2017-08-19 15:25:33', '192.168.2.161', '1');
INSERT INTO `cs_admin_user` VALUES ('13', 'fvwee', '', '', '', '2017-08-18 00:33:53', '0000-00-00 00:00:00', '', '2');
INSERT INTO `cs_admin_user` VALUES ('14', '贩夫贩妇e、11111', '888888', '11111111111111111111', '111111111111111111111111111111', '2017-08-18 08:41:31', '0000-00-00 00:00:00', '', '2');

-- ----------------------------
-- Table structure for `cs_admin_user_menu`
-- ----------------------------
DROP TABLE IF EXISTS `cs_admin_user_menu`;
CREATE TABLE `cs_admin_user_menu` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` smallint(5) NOT NULL DEFAULT '0' COMMENT '用户id',
  `menu_id` smallint(5) NOT NULL DEFAULT '0' COMMENT '菜单id',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='后台用户拥有菜单表';

-- ----------------------------
-- Records of cs_admin_user_menu
-- ----------------------------
INSERT INTO `cs_admin_user_menu` VALUES ('1', '1', '1');
INSERT INTO `cs_admin_user_menu` VALUES ('2', '1', '2');
INSERT INTO `cs_admin_user_menu` VALUES ('3', '1', '3');
INSERT INTO `cs_admin_user_menu` VALUES ('4', '1', '8');
INSERT INTO `cs_admin_user_menu` VALUES ('5', '1', '9');
INSERT INTO `cs_admin_user_menu` VALUES ('6', '1', '13');
INSERT INTO `cs_admin_user_menu` VALUES ('9', '1', '7');
INSERT INTO `cs_admin_user_menu` VALUES ('10', '1', '11');
INSERT INTO `cs_admin_user_menu` VALUES ('11', '1', '10');
INSERT INTO `cs_admin_user_menu` VALUES ('12', '1', '14');
INSERT INTO `cs_admin_user_menu` VALUES ('13', '1', '15');

-- ----------------------------
-- Table structure for `cs_album_folder`
-- ----------------------------
DROP TABLE IF EXISTS `cs_album_folder`;
CREATE TABLE `cs_album_folder` (
  `album_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '相册id',
  `album_name` varchar(100) NOT NULL DEFAULT '' COMMENT '相册名称',
  `pic_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '图片数量',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `is_default` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否为默认相册1.是0.否',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`album_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 AVG_ROW_LENGTH=16384 COMMENT='相册表';

-- ----------------------------
-- Records of cs_album_folder
-- ----------------------------
INSERT INTO `cs_album_folder` VALUES ('30', '默认相册', '6', '1', '1', '2017-06-10 11:20:31');
INSERT INTO `cs_album_folder` VALUES ('31', '产品图片', '0', '0', '0', '2017-09-06 14:26:14');
INSERT INTO `cs_album_folder` VALUES ('34', '广告图片', '0', '0', '0', '2017-09-06 16:59:15');
INSERT INTO `cs_album_folder` VALUES ('35', '详情图片', '0', '3', '0', '2017-09-06 16:59:53');
INSERT INTO `cs_album_folder` VALUES ('36', '活动图片', '0', '0', '0', '2017-09-06 17:02:02');
INSERT INTO `cs_album_folder` VALUES ('37', '会员图片', '0', '0', '0', '2017-09-07 14:22:55');
INSERT INTO `cs_album_folder` VALUES ('38', '二维码', '0', '0', '0', '2017-09-07 14:23:03');
INSERT INTO `cs_album_folder` VALUES ('39', '优惠券', '0', '0', '0', '2017-09-07 14:23:09');
INSERT INTO `cs_album_folder` VALUES ('40', '商城截图', '0', '0', '0', '2017-09-07 14:23:14');
INSERT INTO `cs_album_folder` VALUES ('41', '公共图片', '0', '0', '0', '2017-09-07 14:23:19');
INSERT INTO `cs_album_folder` VALUES ('43', '回收站', '0', '0', '0', '2017-09-07 14:23:29');

-- ----------------------------
-- Table structure for `cs_album_picture`
-- ----------------------------
DROP TABLE IF EXISTS `cs_album_picture`;
CREATE TABLE `cs_album_picture` (
  `pic_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '相册图片表id',
  `album_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '相册id',
  `pic_name` varchar(100) NOT NULL DEFAULT '' COMMENT '图片名称',
  `pic_url` varchar(255) NOT NULL DEFAULT '' COMMENT '原图图片路径',
  `pic_size` int(11) NOT NULL DEFAULT '0' COMMENT '原图大小kb',
  `pic_type` varchar(50) NOT NULL DEFAULT '' COMMENT '图片类型后缀',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '图片上传时间',
  PRIMARY KEY (`pic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8mb4 AVG_ROW_LENGTH=204 COMMENT='相册图片表';

-- ----------------------------
-- Records of cs_album_picture
-- ----------------------------
INSERT INTO `cs_album_picture` VALUES ('133', '30', '41499333696.jpg', '/images/1504763248BQxd8cPxPr.jpg', '356097', 'jpg', '2017-09-07 13:47:28');
INSERT INTO `cs_album_picture` VALUES ('134', '30', 'img1.jpg', '/images/1504771005jk3dZpsnXr.jpg', '15209', 'jpg', '2017-09-07 15:56:46');
INSERT INTO `cs_album_picture` VALUES ('135', '30', '感谢有你陪伴20元抵用劵_看图王.jpg', '/images/1504773754PA4fhN4T2S.jpg', '16729', 'jpg', '2017-09-07 16:42:34');
INSERT INTO `cs_album_picture` VALUES ('145', '30', '5779cff1N32c81820.jpg!q70.jpg', '/images/1504921782AXeZmSXe4w.jpg', '33572', 'jpg', '2017-09-09 09:49:42');
INSERT INTO `cs_album_picture` VALUES ('148', '30', '5779cff0N0b1a701a.jpg!q70.jpg', '/images/15049218245Ysm5rzdXm.jpg', '38113', 'jpg', '2017-09-09 09:50:25');
INSERT INTO `cs_album_picture` VALUES ('153', '30', 'img5.jpg', '/images/15050991982JRr6ymsjF.jpg', '45672', 'jpg', '2017-09-11 11:06:38');

-- ----------------------------
-- Table structure for `cs_brand`
-- ----------------------------
DROP TABLE IF EXISTS `cs_brand`;
CREATE TABLE `cs_brand` (
  `brand_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(60) NOT NULL DEFAULT '' COMMENT '品牌名',
  `brand_logo` varchar(80) NOT NULL DEFAULT '' COMMENT 'logo',
  `brand_desc` varchar(255) NOT NULL DEFAULT '' COMMENT '品牌描述',
  `sort_no` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `is_show` tinyint(1) NOT NULL DEFAULT '1' COMMENT '前台是否显示1.是0.否',
  PRIMARY KEY (`brand_id`),
  KEY `is_show` (`is_show`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COMMENT='品牌表';

-- ----------------------------
-- Records of cs_brand
-- ----------------------------
INSERT INTO `cs_brand` VALUES ('4', '飞利浦', '1240803247838195732.gif', '官方咨询电话：4008800008\n售后网点：http://www.philips.com.cn/service/mustservice/index.page ', '50', '1');
INSERT INTO `cs_brand` VALUES ('5', '夏新', '1240803352280856940.gif', '官方咨询电话：4008875777\n售后网点：http://www.amobile.com.cn/service_fwyzc.asp ', '50', '1');
INSERT INTO `cs_brand` VALUES ('15', '仓品', '', '', '50', '1');

-- ----------------------------
-- Table structure for `cs_category`
-- ----------------------------
DROP TABLE IF EXISTS `cs_category`;
CREATE TABLE `cs_category` (
  `cat_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '父级节点',
  `cat_name` varchar(90) NOT NULL DEFAULT '' COMMENT '分类名称',
  `sort_no` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `cat_img` varchar(255) NOT NULL DEFAULT '' COMMENT '分类图',
  `is_show` tinyint(1) NOT NULL DEFAULT '1' COMMENT '前台是否显示1.是0.否',
  PRIMARY KEY (`cat_id`),
  KEY `parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COMMENT='分类表';

-- ----------------------------
-- Records of cs_category
-- ----------------------------
INSERT INTO `cs_category` VALUES ('1', '0', '手机类型', '50', '', '1');
INSERT INTO `cs_category` VALUES ('3', '1', '小型手机', '50', '', '1');
INSERT INTO `cs_category` VALUES ('4', '1', '3G手机', '50', '', '1');
INSERT INTO `cs_category` VALUES ('6', '0', '手机', '50', '', '1');
INSERT INTO `cs_category` VALUES ('8', '6', '耳机', '50', '', '1');
INSERT INTO `cs_category` VALUES ('9', '6', '电池', '50', '', '1');
INSERT INTO `cs_category` VALUES ('12', '0', '充值卡', '50', '', '1');
INSERT INTO `cs_category` VALUES ('16', '0', '服装', '50', '', '1');
INSERT INTO `cs_category` VALUES ('18', '0', '智能硬件', '3', '', '1');
INSERT INTO `cs_category` VALUES ('19', '0', '配件', '50', '', '1');
INSERT INTO `cs_category` VALUES ('20', '19', '保护壳', '50', '', '1');
INSERT INTO `cs_category` VALUES ('22', '0', '移动电源', '6', '', '1');
INSERT INTO `cs_category` VALUES ('24', '19', '数码时尚', '7', '', '1');
INSERT INTO `cs_category` VALUES ('25', '0', '数码时尚', '2', '', '1');
INSERT INTO `cs_category` VALUES ('26', '0', '家用电器', '1', '', '1');
INSERT INTO `cs_category` VALUES ('27', '26', '大家电', '50', '', '1');
INSERT INTO `cs_category` VALUES ('28', '27', '平板电脑', '50', '', '1');
INSERT INTO `cs_category` VALUES ('29', '27', '家用空调', '50', '', '1');
INSERT INTO `cs_category` VALUES ('30', '27', '家电配件', '50', '', '1');
INSERT INTO `cs_category` VALUES ('31', '27', '洗衣机', '50', '', '1');
INSERT INTO `cs_category` VALUES ('32', '27', '冰箱', '50', '', '1');

-- ----------------------------
-- Table structure for `cs_coupon`
-- ----------------------------
DROP TABLE IF EXISTS `cs_coupon`;
CREATE TABLE `cs_coupon` (
  `coupon_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '优惠券id',
  `coupon_name` varchar(100) NOT NULL DEFAULT '' COMMENT '优惠券名称',
  `coupon_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '适用范围1全部商品2指定商品',
  `money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '优惠券面值',
  `need_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '满多少使用',
  `total_count` smallint(6) NOT NULL DEFAULT '1' COMMENT '总数量',
  `get_count` smallint(6) NOT NULL DEFAULT '0' COMMENT '已经被领取的数量',
  `use_start` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '生效时间',
  `use_end` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '过期时间',
  `content` varchar(100) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '使用说明',
  `limit_grade` mediumint(9) NOT NULL DEFAULT '0' COMMENT '限领会员等级(0表示不限制)',
  `limit_num` smallint(6) NOT NULL DEFAULT '0' COMMENT '限领数量(0表示不限制)',
  `is_show` tinyint(1) NOT NULL DEFAULT '1' COMMENT '前台是否显示1是0否',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '1.有效2.已失效3.已删除',
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='优惠券规则表';

-- ----------------------------
-- Records of cs_coupon
-- ----------------------------
INSERT INTO `cs_coupon` VALUES ('1', '通用优惠券', '1', '5.00', '20.00', '100', '0', '2017-09-12 14:05:00', '2017-09-14 12:05:00', '全场通用', '0', '1', '1', '2017-09-12 14:04:11', '0');

-- ----------------------------
-- Table structure for `cs_coupon_goods`
-- ----------------------------
DROP TABLE IF EXISTS `cs_coupon_goods`;
CREATE TABLE `cs_coupon_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) NOT NULL COMMENT '优惠券id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  PRIMARY KEY (`id`),
  KEY `coupon_id` (`coupon_id`) USING BTREE,
  KEY `goods_id` (`goods_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=606 COMMENT='优惠券使用商品表';

-- ----------------------------
-- Records of cs_coupon_goods
-- ----------------------------

-- ----------------------------
-- Table structure for `cs_express`
-- ----------------------------
DROP TABLE IF EXISTS `cs_express`;
CREATE TABLE `cs_express` (
  `express_id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT COMMENT '快递id',
  `express_code` varchar(20) NOT NULL DEFAULT '' COMMENT '快递编码',
  `express_name` varchar(120) NOT NULL DEFAULT '' COMMENT '快递公司',
  PRIMARY KEY (`express_id`),
  UNIQUE KEY `express_code` (`express_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='快递公司表';

-- ----------------------------
-- Records of cs_express
-- ----------------------------
INSERT INTO `cs_express` VALUES ('1', 'post_express', '邮政快递包裹');
INSERT INTO `cs_express` VALUES ('2', 'yto', '圆通速递');
INSERT INTO `cs_express` VALUES ('4', 'flat', '市内快递');
INSERT INTO `cs_express` VALUES ('5', 'sto_express', '申通快递');
INSERT INTO `cs_express` VALUES ('6', 'post_mail', '邮局平邮');

-- ----------------------------
-- Table structure for `cs_goods`
-- ----------------------------
DROP TABLE IF EXISTS `cs_goods`;
CREATE TABLE `cs_goods` (
  `goods_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `cat_id` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '分类id',
  `goods_name` varchar(120) NOT NULL DEFAULT '' COMMENT '商品名称',
  `click_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '点击数量',
  `evaluate_count` int(10) NOT NULL DEFAULT '0' COMMENT '评价数量',
  `sale_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '销量',
  `keywords` varchar(255) NOT NULL DEFAULT '' COMMENT '关键字',
  `goods_brief` varchar(255) NOT NULL DEFAULT '' COMMENT '商品概述',
  `goods_desc` text NOT NULL COMMENT '商品详情',
  `goods_img` varchar(255) NOT NULL DEFAULT '' COMMENT '商品图片',
  `is_on_sale` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否在售',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '录入时间',
  `is_recommend` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否爆款',
  `is_new` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否新品',
  `is_hot` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否热卖',
  `last_update` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`goods_id`),
  KEY `cat_id` (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Records of cs_goods
-- ----------------------------
INSERT INTO `cs_goods` VALUES ('74', '0', '8H乳胶枕 小米生态链企业产品Z2 泰国直采天然乳胶双层枕套 科学曲线舒适护颈透气乳胶枕头 白色 一对装', '0', '0', '0', '8H乳胶枕护颈透气', '【家装节大促】 美好的礼物 羽泉推荐 好材料造就好枕头三曲线护颈', '<p>\n	<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3157/92/3821141334/17188/7f99c47e/57f899e4N35d164f8.jpg\" alt=\"\" id=\"1c511eee45d74d1ba626e0677fe7df2d\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3163/228/3785663404/19166/3bd00836/57f899e5N92bb5ae3.jpg\" alt=\"\" id=\"8b9203831df24367bbb83e2a19594445\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3052/317/3738720440/29808/4a108f43/57f899e5N35f00299.jpg\" alt=\"\" id=\"1588e082b4124a94a5f386edc70328cc\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3259/235/3739929447/26045/a540883a/57f899e5N3cbc0093.jpg\" alt=\"\" id=\"5b956a1a5d2844a8b15432405ef281a3\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3145/215/3810119828/39314/30abeae0/57f899e7N0f03d023.jpg\" alt=\"\" id=\"26bbe9cce2394b9fb24f3ec8211fe335\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3139/212/3752524337/19762/6481963d/57f899e7Nc095bb01.jpg\" alt=\"\" id=\"0146688c2ce343fcbc3305a8d251e3a2\"><br>\n<br>\n<img src=\"https://img30.360buyimg.com/popWaterMark/jfs/t5671/102/2879315530/86542/c1d967b2/5934fd98N4b0d012c.jpg\" alt=\"\" id=\"425b6d42f237420fae23d99aaa91a04d \"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3175/177/3775678806/43194/957644f4/57f899e8N51328892.jpg\" alt=\"\" id=\"bd740d58f5c344d4b9ade043a184b818\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3166/137/3703643698/32812/3d00e943/57f899e8N0231ab8a.jpg\" alt=\"\" id=\"b3cab443c0d841469365daca464f6671\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3193/243/3786260555/27655/7cc79b44/57f899e8N2f9bb9ca.jpg\" alt=\"\" id=\"57318b24c95e456091179c999f014c32\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3118/188/3796076919/37817/d587643f/57f899e9N49f000de.jpg\" alt=\"\" id=\"fb0914f25a4f4a05b71fc975555e9481\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3307/21/3644027284/17087/37a1d84c/57f899e9N2759281f.jpg\" alt=\"\" id=\"462f459c61e44bc8bf3b66ce9c60f087\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3250/146/3764496881/40417/13d4d186/57f899e8Na6c61afe.jpg\" alt=\"\" id=\"a6c2880a00c94590a8c0bbc197d7e71a\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3172/75/3692418012/57979/7a40aae5/57f899e9Ne40a3a2c.jpg\" alt=\"\" id=\"e582f0e391ea49c885be2ec6a0d9c1b5\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3049/269/3777292220/30059/aa510a1c/57f899f7N0435b86b.jpg\" alt=\"\" id=\"fcf2b57ec52043678a4faa3119fbfee4\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3175/16/3783979502/33051/3d2b2ace/57f899f9N107e4a5e.jpg\" alt=\"\" id=\"6c6bf563219b4edfb756a8e58a0c55f0\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3160/301/3786390647/59125/3a2c50f4/57f899f8N996bba7e.jpg\" alt=\"\" id=\"9b4d6c4747454e5d8c20bcdcd43092ae\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3094/188/3721306475/27166/38c4c871/57f899f9Nbeee2e2f.jpg\" alt=\"\" id=\"dd59804ccc064a33990b159516e08ec3\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3067/181/3787216827/40935/c0fc2b2f/57f899f9Nefb632bb.jpg\" alt=\"\" id=\"236ce301278f4359b2b668826109f7e6\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3184/223/3758681672/48677/7220326/57f899faN2bc1bc0e.jpg\" alt=\"\" id=\"94253f28c3124b689e21b10023764496\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3229/298/3745643485/16521/6938c399/57f899faN48fa1314.jpg\" alt=\"\" id=\"830e985bd6a944d194ac381d1c2d5725\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3064/304/3752649258/15853/355307e9/57f899faN39743303.jpg\" alt=\"\" id=\"2388751bce6d4daa9f0ba88c6a4ab918\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3067/300/3924030714/14624/710f8237/57f899faN29fead2f.jpg\" alt=\"\" id=\"f83dc7fab4844788b643d92e1a9e6e9f\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3067/300/3924030714/14624/710f8237/57f899faN29fead2f.jpg\" alt=\"\" id=\"f83dc7fab4844788b643d92e1a9e6e9f\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3250/270/3772525117/42619/111836c1/57f899fbN33a3ad97.jpg\" alt=\"\" id=\"24b962a26df94d359cf62f26af35e835\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3076/32/3782923034/21214/d84ef35c/57f899fbN25076c0a.jpg\" alt=\"\" id=\"685004a8dd074729855ca0c80a845651\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3064/6/3817921805/32899/21ab140c/57f899fbNa7a2a085.jpg\" alt=\"\" id=\"e98c9f1f84824fd8aec0a3ef743d90d7\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3238/171/3715357302/24197/35c3e0ed/57f899faN77bde756.jpg\" alt=\"\" id=\"9c59db9245de4d4e95461e869f63a5f9\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3244/16/3668028328/26978/bdaa5d88/57f899fbN18338d46.jpg\" alt=\"\" id=\"8956161c031a4f3e8e79cf94b86bb4b2\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3163/328/3772673423/35708/55585001/57f899fbN0834cd1e.jpg\" alt=\"\" id=\"38531d62929d4b1bbaa140fe3dd101d1\"><br>\n<img src=\"https://img30.360buyimg.com/popWareDetail/jfs/t3184/233/3732671919/40389/87350e66/57f899fbN18e8cc3a.jpg\" alt=\"\" id=\"fd9bc6e814b447aab334d637b4978730\"> \n</p><p><br></p>', '/images/1504921782AXeZmSXe4w.jpg', '1', '2017-09-09 09:00:14', '0', '0', '0', '2017-09-27 09:23:35', '0');
INSERT INTO `cs_goods` VALUES ('75', '0', '测试商品一', '0', '0', '0', '', '', '<blockquote>图片详情</blockquote><img src=\"http://imagestest.d6td.com/images/15050991982JRr6ymsjF.jpg\">', '/images/1504763248BQxd8cPxPr.jpg', '1', '2017-09-13 21:10:09', '0', '0', '0', '2017-09-27 08:55:18', '0');

-- ----------------------------
-- Table structure for `cs_goods_evaluate`
-- ----------------------------
DROP TABLE IF EXISTS `cs_goods_evaluate`;
CREATE TABLE `cs_goods_evaluate` (
  `evaluate_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `order_item_id` int(10) unsigned NOT NULL COMMENT '订单明细ID',
  `member_id` int(11) unsigned NOT NULL COMMENT '会员ID',
  `goods_id` int(11) unsigned NOT NULL COMMENT '商品ID',
  `score` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '评价结果（1好评，2中评，3差评）',
  `content` varchar(500) NOT NULL DEFAULT '' COMMENT '评价内容',
  `images` varchar(500) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '图片',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '评价日期',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1.有效2.无效',
  `is_reply` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否回复1.是0.否',
  `reply_time` datetime(2) NOT NULL DEFAULT '0000-00-00 00:00:00.00' COMMENT '回复时间',
  `reply_content` varchar(500) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '回复内容',
  `op_name` varchar(60) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '操作人',
  PRIMARY KEY (`evaluate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='商品评价表';

-- ----------------------------
-- Records of cs_goods_evaluate
-- ----------------------------

-- ----------------------------
-- Table structure for `cs_goods_image`
-- ----------------------------
DROP TABLE IF EXISTS `cs_goods_image`;
CREATE TABLE `cs_goods_image` (
  `img_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `img_url` varchar(255) NOT NULL DEFAULT '',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`img_id`),
  KEY `goods_id` (`goods_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='商品图片表';

-- ----------------------------
-- Records of cs_goods_image
-- ----------------------------
INSERT INTO `cs_goods_image` VALUES ('48', '74', '/images/1504921782AXeZmSXe4w.jpg', '0');
INSERT INTO `cs_goods_image` VALUES ('54', '74', '/images/15049218245Ysm5rzdXm.jpg', '0');
INSERT INTO `cs_goods_image` VALUES ('55', '75', '/images/1504763248BQxd8cPxPr.jpg', '0');

-- ----------------------------
-- Table structure for `cs_goods_product`
-- ----------------------------
DROP TABLE IF EXISTS `cs_goods_product`;
CREATE TABLE `cs_goods_product` (
  `product_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品ID',
  `spec_image` varchar(255) NOT NULL DEFAULT '' COMMENT '规格图片',
  `spec_view` varchar(255) NOT NULL DEFAULT '' COMMENT '规格显示字段',
  `markt_price` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '市场价',
  `price` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '销售价',
  `store` int(11) NOT NULL DEFAULT '0' COMMENT '库存',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用0不禁用1.禁用',
  PRIMARY KEY (`product_id`),
  KEY `IDX_GOODS_ID` (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16234 DEFAULT CHARSET=utf8 COMMENT='货品表';

-- ----------------------------
-- Records of cs_goods_product
-- ----------------------------
INSERT INTO `cs_goods_product` VALUES ('16226', '75', '', '颜色:绿色 容量:32g', '99.00', '90.00', '1', '0');
INSERT INTO `cs_goods_product` VALUES ('16227', '75', '', '颜色:绿色 容量:16g', '78.00', '70.00', '1', '0');
INSERT INTO `cs_goods_product` VALUES ('16228', '75', '', '颜色:蓝色 容量:32g', '99.00', '90.00', '1', '0');
INSERT INTO `cs_goods_product` VALUES ('16229', '75', '', '颜色:蓝色 容量:16g', '7899.88', '70.00', '1', '0');
INSERT INTO `cs_goods_product` VALUES ('16232', '74', '', '', '99.00', '50.00', '9999', '1');
INSERT INTO `cs_goods_product` VALUES ('16233', '74', '', '颜色:蓝色', '99.00', '66.00', '77', '0');

-- ----------------------------
-- Table structure for `cs_goods_product_spec`
-- ----------------------------
DROP TABLE IF EXISTS `cs_goods_product_spec`;
CREATE TABLE `cs_goods_product_spec` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `spec_id` int(11) unsigned NOT NULL COMMENT '规格ID',
  `spec_name` varchar(255) NOT NULL DEFAULT '' COMMENT '规格',
  `spec_value_id` int(11) unsigned NOT NULL COMMENT '规格值ID',
  `spec_value` varchar(255) NOT NULL DEFAULT '' COMMENT '规格值',
  `goods_id` int(11) unsigned NOT NULL COMMENT '商品ID',
  `product_id` int(11) unsigned NOT NULL COMMENT '货品ID',
  `spec_image` varchar(250) NOT NULL DEFAULT '' COMMENT '规格图片',
  PRIMARY KEY (`id`),
  KEY `spec_id` (`spec_id`),
  KEY `spec_value_id` (`spec_value_id`),
  KEY `goods_id` (`goods_id`),
  KEY `product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=439 DEFAULT CHARSET=utf8 COMMENT='商品规格表';

-- ----------------------------
-- Records of cs_goods_product_spec
-- ----------------------------
INSERT INTO `cs_goods_product_spec` VALUES ('430', '481', '颜色', '3094', '绿色', '75', '16226', '');
INSERT INTO `cs_goods_product_spec` VALUES ('431', '490', '容量', '3102', '32g', '75', '16226', '');
INSERT INTO `cs_goods_product_spec` VALUES ('432', '481', '颜色', '3094', '绿色', '75', '16227', '');
INSERT INTO `cs_goods_product_spec` VALUES ('433', '490', '容量', '3100', '16g', '75', '16227', '');
INSERT INTO `cs_goods_product_spec` VALUES ('434', '481', '颜色', '3089', '蓝色', '75', '16228', '');
INSERT INTO `cs_goods_product_spec` VALUES ('435', '490', '容量', '3102', '32g', '75', '16228', '');
INSERT INTO `cs_goods_product_spec` VALUES ('436', '481', '颜色', '3089', '蓝色', '75', '16229', '');
INSERT INTO `cs_goods_product_spec` VALUES ('437', '490', '容量', '3100', '16g', '75', '16229', '');
INSERT INTO `cs_goods_product_spec` VALUES ('438', '481', '颜色', '3089', '蓝色', '74', '16233', '');

-- ----------------------------
-- Table structure for `cs_goods_spec`
-- ----------------------------
DROP TABLE IF EXISTS `cs_goods_spec`;
CREATE TABLE `cs_goods_spec` (
  `spec_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '规格ID',
  `spec_name` varchar(20) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '规格名称',
  PRIMARY KEY (`spec_id`)
) ENGINE=InnoDB AUTO_INCREMENT=491 DEFAULT CHARSET=utf8mb4 COMMENT='商品规格表';

-- ----------------------------
-- Records of cs_goods_spec
-- ----------------------------
INSERT INTO `cs_goods_spec` VALUES ('481', '颜色');
INSERT INTO `cs_goods_spec` VALUES ('482', 'chicu');
INSERT INTO `cs_goods_spec` VALUES ('483', 'chicun');
INSERT INTO `cs_goods_spec` VALUES ('484', '尺寸');
INSERT INTO `cs_goods_spec` VALUES ('485', 'rongy');
INSERT INTO `cs_goods_spec` VALUES ('486', '荣耀了');
INSERT INTO `cs_goods_spec` VALUES ('487', '');
INSERT INTO `cs_goods_spec` VALUES ('488', 't');
INSERT INTO `cs_goods_spec` VALUES ('489', 'rongliang');
INSERT INTO `cs_goods_spec` VALUES ('490', '容量');

-- ----------------------------
-- Table structure for `cs_goods_spec_value`
-- ----------------------------
DROP TABLE IF EXISTS `cs_goods_spec_value`;
CREATE TABLE `cs_goods_spec_value` (
  `spec_value_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '规格值主键',
  `spec_id` int(11) unsigned NOT NULL COMMENT '规格ID',
  `spec_value` varchar(30) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '规格值名称',
  PRIMARY KEY (`spec_value_id`),
  KEY `spec_id` (`spec_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3103 DEFAULT CHARSET=utf8mb4 COMMENT='商品规格值表';

-- ----------------------------
-- Records of cs_goods_spec_value
-- ----------------------------
INSERT INTO `cs_goods_spec_value` VALUES ('3089', '481', '蓝色');
INSERT INTO `cs_goods_spec_value` VALUES ('3090', '481', 'la');
INSERT INTO `cs_goods_spec_value` VALUES ('3091', '481', '');
INSERT INTO `cs_goods_spec_value` VALUES ('3092', '481', 'lans');
INSERT INTO `cs_goods_spec_value` VALUES ('3093', '481', 'l');
INSERT INTO `cs_goods_spec_value` VALUES ('3094', '481', '绿色');
INSERT INTO `cs_goods_spec_value` VALUES ('3095', '484', '21');
INSERT INTO `cs_goods_spec_value` VALUES ('3096', '484', '22');
INSERT INTO `cs_goods_spec_value` VALUES ('3097', '484', '23');
INSERT INTO `cs_goods_spec_value` VALUES ('3098', '490', '1');
INSERT INTO `cs_goods_spec_value` VALUES ('3099', '490', '16');
INSERT INTO `cs_goods_spec_value` VALUES ('3100', '490', '16g');
INSERT INTO `cs_goods_spec_value` VALUES ('3101', '490', '32');
INSERT INTO `cs_goods_spec_value` VALUES ('3102', '490', '32g');

-- ----------------------------
-- Table structure for `cs_member`
-- ----------------------------
DROP TABLE IF EXISTS `cs_member`;
CREATE TABLE `cs_member` (
  `member_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '会员id',
  `name` varchar(60) NOT NULL DEFAULT '' COMMENT '用户名',
  `sex` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '性别1.男2.女',
  `birthday` date NOT NULL DEFAULT '0000-00-00' COMMENT '生日',
  `member_points` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '会员积分',
  `member_balance` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '会员余额',
  `member_rank` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '会员等级',
  `rank_points` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '等级经验值',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号',
  `open_id` varchar(50) NOT NULL DEFAULT '' COMMENT '微信openId',
  `head_img` varchar(255) NOT NULL DEFAULT '' COMMENT '会员头像',
  `country` varchar(30) NOT NULL DEFAULT '' COMMENT '国家',
  `province` varchar(30) NOT NULL DEFAULT '' COMMENT '省',
  `city` varchar(30) NOT NULL DEFAULT '' COMMENT '市',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '注册时间',
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `openId` (`open_id`) USING BTREE,
  UNIQUE KEY `mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='会员表';

-- ----------------------------
-- Records of cs_member
-- ----------------------------

-- ----------------------------
-- Table structure for `cs_member_account_log`
-- ----------------------------
DROP TABLE IF EXISTS `cs_member_account_log`;
CREATE TABLE `cs_member_account_log` (
  `log_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `member_id` mediumint(8) unsigned NOT NULL COMMENT '会员id',
  `account_type` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '类型1.会员积分2.等级经验3.会员余额',
  `from_type` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '产生方式1.商城订单2.订单退还3.兑换4.充值5.签到6.分享7.注册8.提现9.提现退还',
  `number` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '数值',
  `content` varchar(255) NOT NULL DEFAULT '' COMMENT '描述内容',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '操作时间',
  PRIMARY KEY (`log_id`),
  KEY `member_id` (`member_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COMMENT='会员账户历史记录表';

-- ----------------------------
-- Records of cs_member_account_log
-- ----------------------------
INSERT INTO `cs_member_account_log` VALUES ('1', '5', '2', '0', '1100000.00', '11', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('2', '3', '2', '0', '400000.00', '21312', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('3', '2', '2', '0', '300000.00', '300000', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('4', '1', '2', '0', '50000.00', '50', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('5', '5', '2', '0', '0.00', '32', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('6', '1', '99', '0', '-400.00', '支付订单 2009051298180', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('7', '1', '99', '0', '-975.00', '支付订单 2009051255518', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('8', '1', '99', '0', '0.00', '订单 2009051255518 赠送的积分', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('9', '1', '99', '0', '0.00', '订单 2009051298180 赠送的积分', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('10', '1', '99', '0', '-2310.00', '支付订单 2009051267570', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('11', '1', '99', '0', '0.00', '订单 2009051267570 赠送的积分', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('12', '1', '99', '0', '-5989.00', '支付订单 2009051230249', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('13', '1', '99', '0', '-8610.00', '支付订单 2009051276258', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('14', '1', '99', '0', '0.00', '参加夺宝奇兵夺宝奇兵之夏新N7 ', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('15', '1', '99', '0', '0.00', '参加夺宝奇兵夺宝奇兵之诺基亚N96 ', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('16', '1', '2', '0', '0.00', '奖励', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('17', '1', '99', '0', '-10.00', '支付订单 2009051268194', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('18', '1', '99', '0', '0.00', '支付订单 2009051268194', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('19', '1', '99', '0', '0.00', '由于退货或未发货操作，退回订单 2009051255518 赠送的积分', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('20', '1', '99', '0', '975.00', '由于取消、无效或退货操作，退回支付订单 2009051255518 时使用的预付款', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('21', '1', '99', '0', '0.00', '订单 2009051719232 赠送的积分', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('22', '3', '99', '0', '-1000.00', '追加使用余额支付订单：2009051227085', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('23', '1', '99', '0', '-13806.60', '支付订单 2009052224892', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('24', '1', '99', '0', '0.00', '订单 2009052224892 赠送的积分', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('25', '1', '99', '0', '0.00', '由于退货或未发货操作，退回订单 2009051267570 赠送的积分', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('26', '1', '99', '0', '2310.00', '由于取消、无效或退货操作，退回支付订单 2009051267570 时使用的预付款', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('27', '1', '99', '0', '0.00', '订单 2009061585887 赠送的积分', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('28', '1', '99', '0', '17054.00', '1', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('29', '1', '99', '0', '0.00', '由于退货或未发货操作，退回订单 2009061585887 赠送的积分', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('30', '1', '99', '0', '-3196.30', '支付订单 2009061525429', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('31', '1', '99', '0', '-1910.00', '支付订单 2009061503335', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('32', '1', '99', '0', '0.00', '订单 2009061503335 赠送的积分', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('33', '1', '99', '0', '0.00', '由于退货或未发货操作，退回订单 2009061503335 赠送的积分', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('34', '1', '99', '0', '1910.00', '由于取消、无效或退货操作，退回支付订单 2009061503335 时使用的预付款', '0000-00-00 00:00:00');
INSERT INTO `cs_member_account_log` VALUES ('35', '1', '99', '0', '-500.00', '支付订单 2009061510313', '0000-00-00 00:00:00');

-- ----------------------------
-- Table structure for `cs_member_address`
-- ----------------------------
DROP TABLE IF EXISTS `cs_member_address`;
CREATE TABLE `cs_member_address` (
  `address_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `member_id` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '联系人',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '联系电话',
  `province` varchar(10) NOT NULL DEFAULT '' COMMENT '省',
  `city` varchar(10) NOT NULL DEFAULT '' COMMENT '市',
  `district` varchar(10) NOT NULL DEFAULT '' COMMENT '区',
  `detail_address` varchar(200) NOT NULL DEFAULT '' COMMENT '详细地址',
  `address` varchar(200) NOT NULL DEFAULT '' COMMENT '收货地址完整描述',
  `is_default` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否是默认收货地址 1.是 0.否',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标志位1.是0.否',
  PRIMARY KEY (`address_id`),
  KEY `user_id` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='会员收货地址表';

-- ----------------------------
-- Records of cs_member_address
-- ----------------------------
INSERT INTO `cs_member_address` VALUES ('1', '1', '', '13986765412', '2', '52', '502', '', '海兴大厦', '2', '0000-00-00 00:00:00', '2');
INSERT INTO `cs_member_address` VALUES ('2', '3', '', '', '2', '52', '510', '', '通州区旗舰凯旋小区', '2', '0000-00-00 00:00:00', '2');

-- ----------------------------
-- Table structure for `cs_member_coupon`
-- ----------------------------
DROP TABLE IF EXISTS `cs_member_coupon`;
CREATE TABLE `cs_member_coupon` (
  `member_coupon_id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) NOT NULL COMMENT '优惠券规则id',
  `member_id` int(11) NOT NULL COMMENT '会员Id',
  `get_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '获取途径1.自领2.促销赠送3.后台发放',
  `coupon_name` varchar(100) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '优惠券名称',
  `coupon_type` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '适用范围1全部商品2指定商品',
  `money` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '优惠券金额',
  `need_money` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '满多少使用',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态 1.未使用2.已使用',
  `use_start` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '生效时间',
  `use_end` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '过期时间',
  `content` varchar(100) NOT NULL DEFAULT '' COMMENT '使用说明',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '获取时间',
  PRIMARY KEY (`member_coupon_id`),
  KEY `member_id` (`member_id`) USING BTREE,
  KEY `coupon_id` (`coupon_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员优惠券表';

-- ----------------------------
-- Records of cs_member_coupon
-- ----------------------------

-- ----------------------------
-- Table structure for `cs_member_rank`
-- ----------------------------
DROP TABLE IF EXISTS `cs_member_rank`;
CREATE TABLE `cs_member_rank` (
  `rank_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '等级id',
  `rank_name` varchar(30) NOT NULL DEFAULT '' COMMENT '等级名称',
  `min_points` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最小经验值',
  `max_points` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大经验值',
  `discount` decimal(3,2) unsigned NOT NULL DEFAULT '1.00' COMMENT '等级折扣',
  PRIMARY KEY (`rank_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='会员等级表';

-- ----------------------------
-- Records of cs_member_rank
-- ----------------------------
INSERT INTO `cs_member_rank` VALUES ('1', '普通会员', '0', '10000', '0.99');
INSERT INTO `cs_member_rank` VALUES ('2', 'vip', '10000', '10000000', '0.99');
INSERT INTO `cs_member_rank` VALUES ('3', '代销用户', '0', '0', '0.99');

-- ----------------------------
-- Table structure for `cs_order`
-- ----------------------------
DROP TABLE IF EXISTS `cs_order`;
CREATE TABLE `cs_order` (
  `order_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_num` varchar(50) NOT NULL COMMENT '订单编号',
  `member_id` int(11) unsigned NOT NULL COMMENT '会员id',
  `total_amount` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '商品总金额',
  `fare` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '邮费',
  `payed` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '订单已付金额',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `auto_close_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '未付款自动关闭订单时间',
  `pay_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '订单支付时间',
  `auto_finish_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '自动确认收货时间',
  `finish_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '订单交易完成时间',
  `mark_text` varchar(200) NOT NULL DEFAULT '' COMMENT '订单备注',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '待付款(1),待发货(3),待收货(4),已退款(9),交易成功(10), 交易关闭 (11);',
  `is_evaluate` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否评价1.是0.否',
  `consignee` varchar(32) NOT NULL DEFAULT '' COMMENT '收货人',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '收货人手机',
  `address` varchar(200) NOT NULL DEFAULT '' COMMENT '收货地址',
  `express_code` varchar(50) NOT NULL DEFAULT '' COMMENT '快递代码',
  `express_num` varchar(50) NOT NULL DEFAULT '' COMMENT '快递/物流号',
  `is_sign` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否签收:1.是0.否',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除1.是0.否',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `pk_order_num` (`order_num`) USING BTREE,
  KEY `IDX_MEMBER_ID` (`member_id`),
  KEY `IDX_SHOP_ID_ID` (`order_id`) USING BTREE,
  KEY `IDX_STATUS` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- Records of cs_order
-- ----------------------------

-- ----------------------------
-- Table structure for `cs_order_coupon`
-- ----------------------------
DROP TABLE IF EXISTS `cs_order_coupon`;
CREATE TABLE `cs_order_coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL COMMENT '订单id',
  `coupon_id` int(11) NOT NULL COMMENT '优惠券id',
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='订单赠送优惠券表';

-- ----------------------------
-- Records of cs_order_coupon
-- ----------------------------

-- ----------------------------
-- Table structure for `cs_order_gift`
-- ----------------------------
DROP TABLE IF EXISTS `cs_order_gift`;
CREATE TABLE `cs_order_gift` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL COMMENT '订单id',
  `gift` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '赠品',
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='订单赠品表';

-- ----------------------------
-- Records of cs_order_gift
-- ----------------------------

-- ----------------------------
-- Table structure for `cs_order_history`
-- ----------------------------
DROP TABLE IF EXISTS `cs_order_history`;
CREATE TABLE `cs_order_history` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '发货包裹id',
  `content` varchar(500) NOT NULL DEFAULT '' COMMENT '操作详细描述',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `op_name` varchar(50) NOT NULL DEFAULT '' COMMENT '操作人员',
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='订单物流信息表';

-- ----------------------------
-- Records of cs_order_history
-- ----------------------------

-- ----------------------------
-- Table structure for `cs_order_item`
-- ----------------------------
DROP TABLE IF EXISTS `cs_order_item`;
CREATE TABLE `cs_order_item` (
  `order_item_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单明细ID',
  `order_id` int(11) unsigned NOT NULL COMMENT '订单ID',
  `goods_id` int(11) unsigned NOT NULL COMMENT '商品ID',
  `product_id` int(11) NOT NULL COMMENT '货品id',
  `goods_name` varchar(200) NOT NULL DEFAULT '' COMMENT '商品名称',
  `goods_img` varchar(200) NOT NULL DEFAULT '' COMMENT '商品图片',
  `spec_view` varchar(255) NOT NULL DEFAULT '' COMMENT '规格',
  `total_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '商品总金额',
  `price` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '商品的价格',
  `product_num` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '购买数量',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '未发货(1), 已发货(2), 退款中(3), 确认收货(4), 已退款(5)',
  PRIMARY KEY (`order_item_id`),
  UNIQUE KEY `INDEX_ITEM_ID` (`order_item_id`),
  KEY `hm_order_items_index` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';

-- ----------------------------
-- Records of cs_order_item
-- ----------------------------

-- ----------------------------
-- Table structure for `cs_order_payment`
-- ----------------------------
DROP TABLE IF EXISTS `cs_order_payment`;
CREATE TABLE `cs_order_payment` (
  `order_payment_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` int(11) unsigned NOT NULL COMMENT '订单ID',
  `money` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '付款金额',
  `payment_method` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT ' 支付方式 微信(1), 支付宝(2), 积分(3), 优惠券(4), 虚拟账户(5), 会员折扣(9), 活动优惠(10);',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `content` varchar(200) NOT NULL DEFAULT '' COMMENT '支付说明',
  `trade_no` varchar(30) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '交易号',
  PRIMARY KEY (`order_payment_id`),
  KEY `IDX_PAYMENT_METHOD_ORDER_ID` (`payment_method`,`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COMMENT='订单支付表';

-- ----------------------------
-- Records of cs_order_payment
-- ----------------------------

-- ----------------------------
-- Table structure for `cs_order_return`
-- ----------------------------
DROP TABLE IF EXISTS `cs_order_return`;
CREATE TABLE `cs_order_return` (
  `order_return_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` int(11) unsigned NOT NULL COMMENT '订单号',
  `member_id` int(11) NOT NULL COMMENT '会员id',
  `order_item_id` int(11) NOT NULL COMMENT '订单明细id',
  `return_type` tinyint(3) NOT NULL DEFAULT '1' COMMENT '类型换货(1),退货(2)',
  `from_type` tinyint(3) NOT NULL DEFAULT '1' COMMENT '来源1.会员发起2.商家发起',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '申请时间',
  `content` varchar(500) NOT NULL DEFAULT '' COMMENT '问题描述',
  `image` varchar(500) NOT NULL DEFAULT '' COMMENT '图片（多张逗号隔开）',
  `quantity` mediumint(8) NOT NULL DEFAULT '0' COMMENT '申请数量',
  `return_money` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '退款总金额',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态1.未审核2.审核通过3.审核不通过',
  `reply` varchar(500) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '审核回复',
  PRIMARY KEY (`order_return_id`),
  KEY `IDX_ORDER_ID` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='售后服务申请表';

-- ----------------------------
-- Records of cs_order_return
-- ----------------------------

-- ----------------------------
-- Table structure for `cs_order_return_history`
-- ----------------------------
DROP TABLE IF EXISTS `cs_order_return_history`;
CREATE TABLE `cs_order_return_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_return_id` int(11) unsigned NOT NULL COMMENT '退货id',
  `order_item_id` int(11) unsigned NOT NULL COMMENT '订单明细id',
  `content` varchar(256) NOT NULL DEFAULT '' COMMENT '内容',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '时间',
  `op_name` varchar(50) NOT NULL DEFAULT '' COMMENT '操作人',
  PRIMARY KEY (`id`),
  KEY `order_return_id` (`order_return_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='售后服务跟踪表';

-- ----------------------------
-- Records of cs_order_return_history
-- ----------------------------

-- ----------------------------
-- Table structure for `cs_promotion`
-- ----------------------------
DROP TABLE IF EXISTS `cs_promotion`;
CREATE TABLE `cs_promotion` (
  `promotion_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '促销活动id',
  `promotion_name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '活动名称',
  `start_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '活动开始时间',
  `end_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '活动结束时间',
  `content` varchar(200) NOT NULL DEFAULT '' COMMENT '活动说明',
  `promotion_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '活动范围1.全部商品 2.指定商品',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态1.有效2.已失效3.已删除',
  PRIMARY KEY (`promotion_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 AVG_ROW_LENGTH=1638 COMMENT='促销活动表';

-- ----------------------------
-- Records of cs_promotion
-- ----------------------------
INSERT INTO `cs_promotion` VALUES ('1', '中秋大促销', '2017-09-22 13:51:45', '2017-09-29 13:51:46', '中秋大促销好礼买就送', '1', '2017-09-09 13:52:59', '3');
INSERT INTO `cs_promotion` VALUES ('11', '元旦', '2017-12-01 00:00:00', '2018-01-04 00:00:00', '', '2', '2017-09-14 14:02:44', '3');
INSERT INTO `cs_promotion` VALUES ('12', '周年庆', '2017-09-06 00:00:00', '2017-09-23 00:00:00', '', '2', '2017-09-15 08:23:21', '2');
INSERT INTO `cs_promotion` VALUES ('13', '国庆', '2017-09-08 00:00:00', '2017-10-08 00:00:00', '', '1', '2017-09-15 11:29:14', '2');
INSERT INTO `cs_promotion` VALUES ('14', '中秋送月饼', '2017-09-07 00:00:00', '2017-10-10 00:00:00', '', '1', '2017-09-18 08:05:51', '2');
INSERT INTO `cs_promotion` VALUES ('15', '迎中秋，庆国庆', '2017-09-18 00:00:00', '2017-09-30 00:00:00', '', '1', '2017-09-18 14:24:29', '3');
INSERT INTO `cs_promotion` VALUES ('16', '1111', '2017-09-04 00:00:00', '2017-09-20 00:00:00', '', '1', '2017-09-18 14:24:45', '3');
INSERT INTO `cs_promotion` VALUES ('17', '迎中秋，庆国庆', '2017-09-01 00:00:00', '2017-10-07 00:00:00', '', '1', '2017-09-18 14:27:04', '2');
INSERT INTO `cs_promotion` VALUES ('18', '111', '2017-09-05 00:00:00', '2017-09-28 00:00:00', '', '1', '2017-09-18 14:54:59', '3');
INSERT INTO `cs_promotion` VALUES ('19', '234234', '2017-09-04 00:00:00', '2017-09-19 00:00:00', '', '1', '2017-09-21 14:00:44', '1');
INSERT INTO `cs_promotion` VALUES ('20', '国庆送好礼', '2017-09-27 00:00:00', '2017-10-07 00:00:00', '', '2', '2017-09-27 11:00:00', '1');
INSERT INTO `cs_promotion` VALUES ('21', '111', '2017-09-27 00:00:00', '2017-09-30 00:00:00', '', '2', '2017-09-27 11:21:41', '1');

-- ----------------------------
-- Table structure for `cs_promotion_goods`
-- ----------------------------
DROP TABLE IF EXISTS `cs_promotion_goods`;
CREATE TABLE `cs_promotion_goods` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `promotion_id` int(11) unsigned NOT NULL COMMENT '促销活动id',
  `goods_id` int(11) unsigned NOT NULL COMMENT '商品id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 AVG_ROW_LENGTH=1489 COMMENT='促销活动参与商品';

-- ----------------------------
-- Records of cs_promotion_goods
-- ----------------------------
INSERT INTO `cs_promotion_goods` VALUES ('46', '11', '15');
INSERT INTO `cs_promotion_goods` VALUES ('47', '11', '17');
INSERT INTO `cs_promotion_goods` VALUES ('48', '12', '15');
INSERT INTO `cs_promotion_goods` VALUES ('49', '12', '16');
INSERT INTO `cs_promotion_goods` VALUES ('50', '12', '17');
INSERT INTO `cs_promotion_goods` VALUES ('51', '20', '45');
INSERT INTO `cs_promotion_goods` VALUES ('52', '21', '16');

-- ----------------------------
-- Table structure for `cs_promotion_rule`
-- ----------------------------
DROP TABLE IF EXISTS `cs_promotion_rule`;
CREATE TABLE `cs_promotion_rule` (
  `rule_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '规则编号',
  `promotion_id` int(10) unsigned NOT NULL COMMENT '促销活动id',
  `need_money` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '级别价格(满多少)',
  `discount` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '减现金优惠金额（减多少金额）',
  `give_coupon` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '送优惠券的id（0表示不送）',
  `is_give_gift` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有赠品1.是0.否',
  `gift` varchar(500) NOT NULL DEFAULT '' COMMENT '赠品描述',
  PRIMARY KEY (`rule_id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8mb4 AVG_ROW_LENGTH=1170 COMMENT='促销活动规则表';

-- ----------------------------
-- Records of cs_promotion_rule
-- ----------------------------
INSERT INTO `cs_promotion_rule` VALUES ('109', '1', '1.00', '0.00', '5', '1', '111');
INSERT INTO `cs_promotion_rule` VALUES ('113', '11', '100.00', '20.00', '0', '0', '');
INSERT INTO `cs_promotion_rule` VALUES ('115', '11', '500.00', '200.00', '0', '1', '三鹿奶粉10箱');
INSERT INTO `cs_promotion_rule` VALUES ('116', '11', '300.00', '0.00', '6', '0', '');
INSERT INTO `cs_promotion_rule` VALUES ('117', '12', '100.00', '20.00', '0', '0', '');
INSERT INTO `cs_promotion_rule` VALUES ('118', '12', '200.00', '50.00', '6', '1', '一箱牛奶');
INSERT INTO `cs_promotion_rule` VALUES ('119', '12', '300.00', '150.00', '0', '0', '');
INSERT INTO `cs_promotion_rule` VALUES ('120', '13', '100.00', '90.00', '0', '1', '111');
INSERT INTO `cs_promotion_rule` VALUES ('121', '14', '100.00', '1.00', '12', '1', '一盒老周月饼');
INSERT INTO `cs_promotion_rule` VALUES ('122', '14', '200.00', '2.00', '12', '0', '');
INSERT INTO `cs_promotion_rule` VALUES ('123', '14', '500.00', '150.00', '12', '1', '一盒皇家礼盒');
INSERT INTO `cs_promotion_rule` VALUES ('124', '15', '200.00', '20.00', '0', '1', '一盒月饼');
INSERT INTO `cs_promotion_rule` VALUES ('125', '15', '500.00', '100.00', '8', '0', '');
INSERT INTO `cs_promotion_rule` VALUES ('126', '16', '111.00', '0.00', '0', '1', '');
INSERT INTO `cs_promotion_rule` VALUES ('129', '17', '111.00', '0.00', '11', '1', '212121');
INSERT INTO `cs_promotion_rule` VALUES ('130', '18', '11.00', '11.00', '9', '1', '11');
INSERT INTO `cs_promotion_rule` VALUES ('131', '19', '12.00', '1.00', '0', '0', '');
INSERT INTO `cs_promotion_rule` VALUES ('132', '20', '20.00', '5.00', '5', '1', '精美包装月饼一盒');
INSERT INTO `cs_promotion_rule` VALUES ('133', '20', '50.00', '10.00', '5', '1', '精美包装月饼两盒');
INSERT INTO `cs_promotion_rule` VALUES ('134', '20', '200.00', '50.00', '5', '1', '精美包装月饼四盒');
INSERT INTO `cs_promotion_rule` VALUES ('135', '21', '11.00', '1.00', '0', '0', '');

-- ----------------------------
-- Table structure for `cs_search_history`
-- ----------------------------
DROP TABLE IF EXISTS `cs_search_history`;
CREATE TABLE `cs_search_history` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `keyword` char(50) NOT NULL,
  `count` int(11) NOT NULL,
  `type` enum('goods','store') NOT NULL,
  `store_id` int(11) NOT NULL,
  `updated` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='搜索历史记录';

-- ----------------------------
-- Records of cs_search_history
-- ----------------------------

-- ----------------------------
-- Table structure for `cs_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `cs_sequence`;
CREATE TABLE `cs_sequence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8mb4 COMMENT='递增计数表（用于生成订单号）';

-- ----------------------------
-- Records of cs_sequence
-- ----------------------------
INSERT INTO `cs_sequence` VALUES ('1');
INSERT INTO `cs_sequence` VALUES ('2');
INSERT INTO `cs_sequence` VALUES ('3');
INSERT INTO `cs_sequence` VALUES ('4');
INSERT INTO `cs_sequence` VALUES ('5');
INSERT INTO `cs_sequence` VALUES ('6');
INSERT INTO `cs_sequence` VALUES ('7');
INSERT INTO `cs_sequence` VALUES ('8');
INSERT INTO `cs_sequence` VALUES ('9');
INSERT INTO `cs_sequence` VALUES ('10');
INSERT INTO `cs_sequence` VALUES ('11');
INSERT INTO `cs_sequence` VALUES ('12');
INSERT INTO `cs_sequence` VALUES ('13');
INSERT INTO `cs_sequence` VALUES ('14');
INSERT INTO `cs_sequence` VALUES ('15');
INSERT INTO `cs_sequence` VALUES ('16');
INSERT INTO `cs_sequence` VALUES ('17');
INSERT INTO `cs_sequence` VALUES ('18');
INSERT INTO `cs_sequence` VALUES ('19');
INSERT INTO `cs_sequence` VALUES ('20');
INSERT INTO `cs_sequence` VALUES ('21');
INSERT INTO `cs_sequence` VALUES ('22');
INSERT INTO `cs_sequence` VALUES ('23');
INSERT INTO `cs_sequence` VALUES ('24');
INSERT INTO `cs_sequence` VALUES ('25');
INSERT INTO `cs_sequence` VALUES ('26');
INSERT INTO `cs_sequence` VALUES ('27');
INSERT INTO `cs_sequence` VALUES ('28');
INSERT INTO `cs_sequence` VALUES ('29');
INSERT INTO `cs_sequence` VALUES ('30');
INSERT INTO `cs_sequence` VALUES ('31');
INSERT INTO `cs_sequence` VALUES ('32');
INSERT INTO `cs_sequence` VALUES ('33');
INSERT INTO `cs_sequence` VALUES ('34');
INSERT INTO `cs_sequence` VALUES ('35');
INSERT INTO `cs_sequence` VALUES ('36');
INSERT INTO `cs_sequence` VALUES ('37');
INSERT INTO `cs_sequence` VALUES ('38');
INSERT INTO `cs_sequence` VALUES ('39');
INSERT INTO `cs_sequence` VALUES ('40');
INSERT INTO `cs_sequence` VALUES ('41');
INSERT INTO `cs_sequence` VALUES ('42');
INSERT INTO `cs_sequence` VALUES ('43');
INSERT INTO `cs_sequence` VALUES ('44');
INSERT INTO `cs_sequence` VALUES ('45');
INSERT INTO `cs_sequence` VALUES ('46');
INSERT INTO `cs_sequence` VALUES ('47');
INSERT INTO `cs_sequence` VALUES ('48');
INSERT INTO `cs_sequence` VALUES ('49');
INSERT INTO `cs_sequence` VALUES ('50');
INSERT INTO `cs_sequence` VALUES ('51');
INSERT INTO `cs_sequence` VALUES ('52');
INSERT INTO `cs_sequence` VALUES ('53');
INSERT INTO `cs_sequence` VALUES ('54');
INSERT INTO `cs_sequence` VALUES ('55');
INSERT INTO `cs_sequence` VALUES ('56');
INSERT INTO `cs_sequence` VALUES ('57');
INSERT INTO `cs_sequence` VALUES ('58');
INSERT INTO `cs_sequence` VALUES ('59');
INSERT INTO `cs_sequence` VALUES ('60');
INSERT INTO `cs_sequence` VALUES ('61');
INSERT INTO `cs_sequence` VALUES ('62');
INSERT INTO `cs_sequence` VALUES ('63');
INSERT INTO `cs_sequence` VALUES ('64');
INSERT INTO `cs_sequence` VALUES ('65');
INSERT INTO `cs_sequence` VALUES ('66');
INSERT INTO `cs_sequence` VALUES ('67');
INSERT INTO `cs_sequence` VALUES ('68');
INSERT INTO `cs_sequence` VALUES ('69');
INSERT INTO `cs_sequence` VALUES ('70');
INSERT INTO `cs_sequence` VALUES ('71');
INSERT INTO `cs_sequence` VALUES ('72');
INSERT INTO `cs_sequence` VALUES ('73');
INSERT INTO `cs_sequence` VALUES ('74');
INSERT INTO `cs_sequence` VALUES ('75');
INSERT INTO `cs_sequence` VALUES ('76');
INSERT INTO `cs_sequence` VALUES ('77');
INSERT INTO `cs_sequence` VALUES ('78');
INSERT INTO `cs_sequence` VALUES ('79');
INSERT INTO `cs_sequence` VALUES ('80');
INSERT INTO `cs_sequence` VALUES ('81');
INSERT INTO `cs_sequence` VALUES ('82');
INSERT INTO `cs_sequence` VALUES ('83');
INSERT INTO `cs_sequence` VALUES ('84');
INSERT INTO `cs_sequence` VALUES ('85');
INSERT INTO `cs_sequence` VALUES ('86');
INSERT INTO `cs_sequence` VALUES ('87');
INSERT INTO `cs_sequence` VALUES ('88');
INSERT INTO `cs_sequence` VALUES ('89');
INSERT INTO `cs_sequence` VALUES ('90');
INSERT INTO `cs_sequence` VALUES ('91');
INSERT INTO `cs_sequence` VALUES ('92');
INSERT INTO `cs_sequence` VALUES ('93');
INSERT INTO `cs_sequence` VALUES ('94');
INSERT INTO `cs_sequence` VALUES ('95');
INSERT INTO `cs_sequence` VALUES ('96');
INSERT INTO `cs_sequence` VALUES ('97');
INSERT INTO `cs_sequence` VALUES ('98');
INSERT INTO `cs_sequence` VALUES ('99');
INSERT INTO `cs_sequence` VALUES ('100');
INSERT INTO `cs_sequence` VALUES ('101');
INSERT INTO `cs_sequence` VALUES ('102');
INSERT INTO `cs_sequence` VALUES ('103');
INSERT INTO `cs_sequence` VALUES ('104');
INSERT INTO `cs_sequence` VALUES ('105');
INSERT INTO `cs_sequence` VALUES ('106');
INSERT INTO `cs_sequence` VALUES ('107');
INSERT INTO `cs_sequence` VALUES ('108');
INSERT INTO `cs_sequence` VALUES ('109');
INSERT INTO `cs_sequence` VALUES ('110');
INSERT INTO `cs_sequence` VALUES ('111');
INSERT INTO `cs_sequence` VALUES ('112');
INSERT INTO `cs_sequence` VALUES ('113');
INSERT INTO `cs_sequence` VALUES ('114');
INSERT INTO `cs_sequence` VALUES ('115');
INSERT INTO `cs_sequence` VALUES ('116');
INSERT INTO `cs_sequence` VALUES ('117');
INSERT INTO `cs_sequence` VALUES ('118');
INSERT INTO `cs_sequence` VALUES ('119');
INSERT INTO `cs_sequence` VALUES ('135');
INSERT INTO `cs_sequence` VALUES ('136');
INSERT INTO `cs_sequence` VALUES ('137');
INSERT INTO `cs_sequence` VALUES ('141');
INSERT INTO `cs_sequence` VALUES ('142');
INSERT INTO `cs_sequence` VALUES ('143');
INSERT INTO `cs_sequence` VALUES ('144');
INSERT INTO `cs_sequence` VALUES ('145');
INSERT INTO `cs_sequence` VALUES ('146');
INSERT INTO `cs_sequence` VALUES ('147');
INSERT INTO `cs_sequence` VALUES ('148');
INSERT INTO `cs_sequence` VALUES ('149');
INSERT INTO `cs_sequence` VALUES ('150');
INSERT INTO `cs_sequence` VALUES ('151');
INSERT INTO `cs_sequence` VALUES ('152');
INSERT INTO `cs_sequence` VALUES ('153');
INSERT INTO `cs_sequence` VALUES ('154');
INSERT INTO `cs_sequence` VALUES ('155');
INSERT INTO `cs_sequence` VALUES ('156');

-- ----------------------------
-- Table structure for `cs_shop_config`
-- ----------------------------
DROP TABLE IF EXISTS `cs_shop_config`;
CREATE TABLE `cs_shop_config` (
  `config_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `key` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT 'key值',
  `value` varchar(1000) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT 'value值',
  `content` varchar(500) NOT NULL DEFAULT '' COMMENT '描述',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`config_id`),
  UNIQUE KEY `key` (`key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='商城配置表';

-- ----------------------------
-- Records of cs_shop_config
-- ----------------------------
INSERT INTO `cs_shop_config` VALUES ('5', 'ORDER_AUTO_CLOSE_TIME', '120', '订单自动关闭时间（分钟）', '2017-09-30 09:51:42');
INSERT INTO `cs_shop_config` VALUES ('6', 'ORDER_AUTO_FINISH_TIME', '7', '发货后自动确认收货时间 (天)', '2017-09-30 09:55:46');
INSERT INTO `cs_shop_config` VALUES ('7', 'POSTAGE', '6', '邮费', '2017-09-30 09:58:24');
INSERT INTO `cs_shop_config` VALUES ('8', 'PACKAGE_POSTAGE', '58', '满多少包邮', '2017-09-30 09:58:21');
INSERT INTO `cs_shop_config` VALUES ('9', 'POINTS_RATIO', '100', '会员积分比例', '0000-00-00 00:00:00');
INSERT INTO `cs_shop_config` VALUES ('10', 'SHOP_NAME', '橙意', '商城名称', '0000-00-00 00:00:00');

-- ----------------------------
-- Procedure structure for `initsequence`
-- ----------------------------
DROP PROCEDURE IF EXISTS `initsequence`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `initsequence`()
BEGIN
	TRUNCATE TABLE sys_sequence;
	alter table sys_sequence auto_increment  =  1;
END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `course_listener`
-- ----------------------------
DROP EVENT IF EXISTS `course_listener`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `course_listener` ON SCHEDULE EVERY 1 DAY STARTS '2016-10-12 00:00:00' ON COMPLETION PRESERVE ENABLE DO call initsequence()
;;
DELIMITER ;
