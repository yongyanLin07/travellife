/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : travellife

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 03/06/2020 18:00:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blist
-- ----------------------------
DROP TABLE IF EXISTS `blist`;
CREATE TABLE `blist`  (
  `id` bigint(20) NOT NULL,
  `ipaddress` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` datetime(6) NULL DEFAULT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blist
-- ----------------------------

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` bigint(20) NOT NULL,
  `cart_valid` int(11) NOT NULL,
  `num` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `product_id` bigint(20) NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK3d704slv66tw6x5hmbm6p2x3u`(`product_id`) USING BTREE,
  INDEX `FKl70asp4l4w0jmbm1tqyofho4o`(`user_id`) USING BTREE,
  CONSTRAINT `FK3d704slv66tw6x5hmbm6p2x3u` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKl70asp4l4w0jmbm1tqyofho4o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (123, 0, 1, 5899, 61, 122);

-- ----------------------------
-- Table structure for example
-- ----------------------------
DROP TABLE IF EXISTS `example`;
CREATE TABLE `example`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of example
-- ----------------------------
INSERT INTO `example` VALUES (1, '2020-02-27 16:07:00');

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (245);
INSERT INTO `hibernate_sequence` VALUES (245);
INSERT INTO `hibernate_sequence` VALUES (245);
INSERT INTO `hibernate_sequence` VALUES (245);
INSERT INTO `hibernate_sequence` VALUES (245);

-- ----------------------------
-- Table structure for pagesec
-- ----------------------------
DROP TABLE IF EXISTS `pagesec`;
CREATE TABLE `pagesec`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求url',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源文件路径',
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '关闭' COMMENT '状态 开启  关闭',
  `md5str` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MD5加密字符串',
  PRIMARY KEY (`id`, `url`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 128 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pagesec
-- ----------------------------
INSERT INTO `pagesec` VALUES (107, '/admin/pagesecurity', '/admin/page_index.html', '开启', '06815e63d53af7dd19bba47a5dfcc9cf');
INSERT INTO `pagesec` VALUES (108, '/TypeDetails', '/TypeDetails.html', '关闭', NULL);
INSERT INTO `pagesec` VALUES (127, '/ProductDetails', '/ProductDetails.html', '开启', '021758732208ccc5fbf76193dc6d2849');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  `type_id` int(11) NULL DEFAULT NULL,
  `recommend` int(11) NOT NULL,
  `sale` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKq3fvcsydiaotwy3iqn1erqsfd`(`type_id`) USING BTREE,
  CONSTRAINT `FKq3fvcsydiaotwy3iqn1erqsfd` FOREIGN KEY (`type_id`) REFERENCES `type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (45, 'As the largest and best-preserved ancient royal building complex in the world, the Palace Museum is a reflection of the highest architecture level in ancient China.', '北京.jpg', 'Beijing', 1699, 18, 39, 0, 0);
INSERT INTO `product` VALUES (53, 'The United States is truly a diverse nation made up of dynamic people. ', '美国2.jpg', 'America', 17099, 40, 43, 0, 0);
INSERT INTO `product` VALUES (54, 'Country is divided into 24 administrative units. From 22 provinces, a region', '阿根廷.jpg', 'Argentina', 7399, 21, 43, 0, 0);
INSERT INTO `product` VALUES (55, 'Bali Island is a province and an island in Indonesia. It has an area of 5,620 square kilometers and a population of 3.15 million.', '巴厘岛.jpg', 'Bali', 5399, 30, 40, 0, 0);
INSERT INTO `product` VALUES (56, 'Iguazu Falls are waterfalls of the Iguazu River located on the border of the Brazilian State of Paraná and the Argentine Province of Misiones. ', '巴西.jpg', 'Brazil', 5499, 20, 43, 0, 0);
INSERT INTO `product` VALUES (57, 'Canada reaches the Pacific Ocean in the west, the Atlantic ocean in the east, and the arctic ocean in the north. ', '加拿大.jpg', 'Canada', 10999, 30, 43, 0, 0);
INSERT INTO `product` VALUES (58, 'More than four thousand years ago, the prehistorical Bronze Age culture of Jīnshā (金沙) established itself in this region. ', '成都.jpg', 'Chengdu', 1200, 22, 39, 1, 1);
INSERT INTO `product` VALUES (59, 'In the west of South America, a north-south strip of desert stretches from the shores of the Pacific to the western foothills of the andes.', '智利.jpg', 'Chile', 6400, 10, 43, 0, 0);
INSERT INTO `product` VALUES (60, 'Cartagena, Santa marta, Bogota, SAN andres and providencia, medellin, guajira peninsula, boyaka, etc.Colombia is famous for its historical sites.', '哥伦比亚.jpg', 'Columbia', 5599, 20, 43, 0, 0);
INSERT INTO `product` VALUES (61, 'Helsinki is the political and cultural center of Finland.Helsinki, the capital, is dotted with lakes and alleys', '芬兰.jpg', 'Finland', 5899, 28, 44, 0, 0);
INSERT INTO `product` VALUES (62, 'The city famous: Elephant Trunk Hill Seven Star Park, Reed Flute Cave Park Diecai Park Fubo Park, there are two rivers and four lakes night', '桂林.jpg', 'Guilin', 1705, 20, 39, 0, 0);
INSERT INTO `product` VALUES (63, 'The famous West Lake is like a brilliant pearl embedded in the beautiful and fertile shores of the East China Sea near the mouth of the Hangzhou Bay.', '杭州.jpg', 'Hanzhou', 1899, 29, 39, 1, 1);
INSERT INTO `product` VALUES (64, 'Keukenhof park has a variety of tulips, quantity, quality are very high, the layout is very unique.The park is surrounded by a profusion of fields of tulips, daffodils, hyacinths, and bulbous flowers.', '荷兰.jpg', 'Holland', 4699, 30, 44, 0, 0);
INSERT INTO `product` VALUES (65, 'The Peak is Hong Kong\'s number one tourist destination, playing host to more than 6 million visitors each year. ', 'hon2.jpg', 'Hongkong', 2499, 30, 40, 0, 1);
INSERT INTO `product` VALUES (66, 'The Correll Museum (Museo Correr) provided an exciting course to discover Venice\'s art and the history.', '意大利.jpg', 'Italy', 7400, 30, 44, 0, 0);
INSERT INTO `product` VALUES (67, 'Tenryū-ji, the main temple of the Rinzai school, one of the two main sects of Zen Buddhism in Japan.', '日本.jpg', 'Japan', 5400, 33, 39, 1, 1);
INSERT INTO `product` VALUES (68, 'In the suburbs of Seoul, gyeonggi province, which is full of leisure atmosphere, has the only comprehensive amusement place in the world -', '韩国.jpg', 'Korea', 3999, 30, 40, 0, 0);
INSERT INTO `product` VALUES (69, 'A cow sausage, maybe you eat much of sausage many, but estimates moose sausage didn\'t you hear, but here it was a special product', '挪威.jpg', 'Norway', 5100, 20, 44, 0, 1);
INSERT INTO `product` VALUES (70, 'There is a Night Zoo, team with all of kinds of lovely animals, such as spleey koala and peachful dolphin.', '新加坡.jpg', 'Singapore', 5599, 20, 40, 0, 0);
INSERT INTO `product` VALUES (71, 'Spain\'s capital Madrid as well as economic, cultural and political center. The city is also one of Europe for the Arts Center every year to attract a large number of tourists. ', '西班牙.jpg', 'Spain', 5499, 15, 44, 1, 1);
INSERT INTO `product` VALUES (72, 'The country comprises 76 provinces that are further divided into districts, sub-districts and villages. ', '泰国.jpg', 'Thailand', 3999, 27, 40, 1, 1);
INSERT INTO `product` VALUES (73, 'Sigh bridge was built in 1603, because on the bridge of sighs condemned the name. It is said that lovers kissing under Bridges can last forever, the movie \"the emotion for sunset bridge\"', '威尼斯.jpg', 'Venice', 5499, 30, 44, 0, 0);
INSERT INTO `product` VALUES (74, 'The city is located in the subtropical zone, because close to the ocean, pleasant climate, four seasons like spring, abundant rainfall, lush flowers, flowers in full bloom, known as the \"city of spring flowers\".', '越南.jpg', 'Vietnam', 2099, 40, 40, 1, 1);
INSERT INTO `product` VALUES (75, 'Yunnan is located about the southwest part of China. This place has beautiful mountains, and rivers and full with many local minority races.', '云南.jpg', 'Yunnan', 2100, 30, 39, 0, 1);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` int(11) NOT NULL,
  `time` datetime(6) NULL DEFAULT NULL,
  `user_action` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (218, '2020-06-01 22:52:13.177000', '查看黑名单', 1);
INSERT INTO `sys_log` VALUES (219, '2020-06-02 09:51:37.962000', '查看黑名单', 1);
INSERT INTO `sys_log` VALUES (222, '2020-06-02 21:23:27.086000', '查看黑名单', 1);
INSERT INTO `sys_log` VALUES (223, '2020-06-02 21:27:40.043000', '查看黑名单', 1);
INSERT INTO `sys_log` VALUES (224, '2020-06-02 21:27:42.928000', '删除黑名单', 1);
INSERT INTO `sys_log` VALUES (225, '2020-06-02 21:27:42.972000', '查看黑名单', 1);
INSERT INTO `sys_log` VALUES (227, '2020-06-02 21:43:42.505000', '查看黑名单', 1);
INSERT INTO `sys_log` VALUES (228, '2020-06-02 21:43:44.537000', '删除黑名单', 1);
INSERT INTO `sys_log` VALUES (229, '2020-06-02 21:43:44.579000', '查看黑名单', 1);
INSERT INTO `sys_log` VALUES (231, '2020-06-02 21:46:19.688000', '查看黑名单', 1);
INSERT INTO `sys_log` VALUES (232, '2020-06-02 21:46:25.533000', '删除黑名单', 1);
INSERT INTO `sys_log` VALUES (233, '2020-06-02 21:46:25.581000', '查看黑名单', 1);
INSERT INTO `sys_log` VALUES (234, '2020-06-02 21:47:51.252000', '查看黑名单', 1);
INSERT INTO `sys_log` VALUES (235, '2020-06-02 22:33:28.830000', '查看黑名单', 1);
INSERT INTO `sys_log` VALUES (237, '2020-06-03 10:00:48.254000', '查看黑名单', 1);
INSERT INTO `sys_log` VALUES (238, '2020-06-03 10:00:49.992000', '删除黑名单', 1);
INSERT INTO `sys_log` VALUES (239, '2020-06-03 10:00:50.046000', '查看黑名单', 1);
INSERT INTO `sys_log` VALUES (240, '2020-06-03 10:01:36.942000', '查看黑名单', 1);
INSERT INTO `sys_log` VALUES (241, '2020-06-03 10:16:42.543000', '查看黑名单', 1);
INSERT INTO `sys_log` VALUES (242, '2020-06-03 12:49:09.838000', '查看黑名单', 1);
INSERT INTO `sys_log` VALUES (243, '2020-06-03 16:49:30.196000', '查看黑名单', 1);
INSERT INTO `sys_log` VALUES (244, '2020-06-03 17:12:22.148000', '查看黑名单', 1);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `perms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` bigint(20) NULL DEFAULT NULL,
  `pids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` tinyint(4) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_by` bigint(20) NULL DEFAULT NULL,
  `update_by` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKsa7ydqr5faqs6wh2o18vtgpex`(`create_by`) USING BTREE,
  INDEX `FKt3l6knbl7jvgunv0smchlcmoj`(`update_by`) USING BTREE,
  CONSTRAINT `FKsa7ydqr5faqs6wh2o18vtgpex` FOREIGN KEY (`create_by`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKt3l6knbl7jvgunv0smchlcmoj` FOREIGN KEY (`update_by`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 175 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '2020-03-29 11:02:10', '', 'system:menu:index', 2, '[0],[2]', '菜单', 3, 1, '权限管理', 2, '2020-04-23 16:10:40', '/system/menu/index', 1, 1);
INSERT INTO `sys_menu` VALUES (2, '2020-03-29 11:02:10', 'fa fa-cog', '#', 0, '[0]', '目录', 2, 1, '系统安全维护管理', 1, '2020-04-20 21:34:56', '#', 1, 1);
INSERT INTO `sys_menu` VALUES (3, '2020-03-29 11:02:10', '', 'system:menu:add', 1, '[0],[2],[1]', '按钮', 1, 1, '添加', 3, '2020-04-21 16:12:59', '/system/menu/add', 1, 1);
INSERT INTO `sys_menu` VALUES (4, '2020-03-29 11:02:10', '', 'system:role:index', 2, '[0],[2]', '菜单', 2, 1, '角色管理', 2, '2020-04-14 16:10:34', '/system/role/index', 1, 1);
INSERT INTO `sys_menu` VALUES (5, '2020-03-29 11:02:10', '', 'system:role:add', 4, '[0],[2],[4]', '按钮', 1, 1, '添加', 3, '2020-04-08 16:12:04', '/system/role/add', 1, 1);
INSERT INTO `sys_menu` VALUES (6, '2020-03-29 11:02:10', 'layui-icon layui-icon-home', 'index', 0, '[0]', '目录', 1, 1, '主页', 1, '2020-04-22 21:34:56', '/admin/adminPage', 1, 1);
INSERT INTO `sys_menu` VALUES (7, '2020-03-29 11:02:10', '', 'system:user:index', 2, '[0],[2]', '菜单', 1, 1, '用户管理', 2, '2020-04-23 17:43:25', '/admin/UserManage', 1, 1);
INSERT INTO `sys_menu` VALUES (8, '2020-04-09 10:57:33', '', 'system:menu:edit', 1, '[0],[2],[1]', '按钮', 2, 1, '编辑', 3, '2020-04-15 16:13:05', '/system/menu/edit', 1, 1);
INSERT INTO `sys_menu` VALUES (9, '2020-04-09 10:57:33', '', 'system:menu:detail', 1, '[0],[2],[1]', '按钮', 3, 1, '详细', 3, '2020-04-18 16:13:12', '/system/menu/detail', 1, 1);
INSERT INTO `sys_menu` VALUES (10, '2020-04-09 10:57:33', '', 'system:menu:status', 1, '[0],[2],[1]', '按钮', 4, 1, '修改状态', 3, '2020-04-15 16:13:21', '/system/menu/status', 1, 1);
INSERT INTO `sys_menu` VALUES (11, '2020-04-09 10:57:33', '', 'system:role:edit', 4, '[0],[2],[4]', '按钮', 2, 1, '编辑', 3, '2020-04-29 16:12:10', '/system/role/edit', 1, 1);
INSERT INTO `sys_menu` VALUES (12, '2020-04-09 10:57:33', '', 'system:role:auth', 4, '[0],[2],[4]', '按钮', 3, 1, '授权', 3, '2020-04-29 16:12:17', '/system/role/auth', 1, 1);
INSERT INTO `sys_menu` VALUES (13, '2020-04-09 10:57:33', '', 'system:role:detail', 4, '[0],[2],[4]', '按钮', 4, 1, '详细', 3, '2020-04-29 16:12:24', '/system/role/detail', 1, 1);
INSERT INTO `sys_menu` VALUES (14, '2020-04-09 10:57:33', '', 'system:role:status', 4, '[0],[2],[4]', '按钮', 5, 1, '修改状态', 3, '2020-04-28 16:12:43', '/system/role/status', 1, 1);
INSERT INTO `sys_menu` VALUES (15, '2020-04-09 10:57:33', '', 'system:user:edit', 7, '[0],[2],[7]', '按钮', 2, 1, '编辑', 3, '2020-04-29 16:11:03', '/system/user/edit', 1, 1);
INSERT INTO `sys_menu` VALUES (16, '2020-04-09 10:57:33', '', 'system:user:add', 7, '[0],[2],[7]', '按钮', 1, 1, '添加', 3, '2020-04-29 16:10:28', '/system/user/add', 1, 1);
INSERT INTO `sys_menu` VALUES (17, '2020-04-06 21:19:40', '', 'system:user:pwd', 7, '[0],[2],[7]', '按钮', 3, 1, '修改密码', 3, '2020-04-29 16:11:11', '/system/user/pwd', 1, 1);
INSERT INTO `sys_menu` VALUES (18, '2020-04-06 21:19:40', '', 'system:user:role', 7, '[0],[2],[7]', '按钮', 4, 1, '权限分配', 3, '2020-04-29 16:11:18', '/system/user/role', 1, 1);
INSERT INTO `sys_menu` VALUES (19, '2020-04-06 21:19:40', '', 'system:user:detail', 7, '[0],[2],[7]', '按钮', 5, 1, '详细', 3, '2020-04-14 16:11:26', '/system/user/detail', 1, 1);
INSERT INTO `sys_menu` VALUES (20, '2020-04-06 21:19:40', '', 'system:user:status', 7, '[0],[2],[7]', '按钮', 6, 1, '修改状态', 3, '2020-05-07 16:11:35', '/system/user/status', 1, 1);
INSERT INTO `sys_menu` VALUES (147, '2020-04-06 21:19:40', '', '#', 0, '[0]', '目录', 3, 1, '业务管理', 1, '2020-05-01 14:45:24', '#', 1, 1);
INSERT INTO `sys_menu` VALUES (148, '2020-04-06 21:19:40', '', 'admin:CateManage', 147, '[0],[147]', '菜单', 1, 1, '商品类别管理', 2, '2020-04-23 14:45:30', '/admin/CateManage', 1, 1);
INSERT INTO `sys_menu` VALUES (149, '2020-05-23 16:44:48', '', 'system:blist:index', 2, '[0],[2]', '目录', 5, 1, '黑名单管理', 1, '2020-04-23 14:45:42', '/admin/BlistManage', 1, 1);
INSERT INTO `sys_menu` VALUES (150, '2020-04-06 21:19:40', '', 'admin:ProductManage', 147, '[0],[147]', '菜单', 2, 1, '商品管理', 2, '2020-05-15 14:45:49', '/admin/ProductManage', 1, 1);
INSERT INTO `sys_menu` VALUES (151, '2020-05-23 17:20:33', '', 'admin:pagesecurity:index', 2, '[0],[2]', '目录', 4, 1, '网页安全管理', 1, '2020-04-23 14:45:54', '/admin/pagesecurity', 1, 1);
INSERT INTO `sys_menu` VALUES (152, '2020-04-06 21:19:40', '', 'admin:pagesecurity:add', 151, '[0],[2],[151]', '按钮', 1, 1, '添加', 3, '2020-04-22 14:46:00', '/admin/pagesecurity/add', 1, 1);
INSERT INTO `sys_menu` VALUES (153, '2020-05-13 22:03:40', '', 'admin:pagesecurity:update', 151, '[0],[2],[151]', '按钮', 2, 1, '修改', 3, '2020-04-14 14:46:06', '/admin/pagesecurity/edit', 1, 1);
INSERT INTO `sys_menu` VALUES (154, '2020-04-06 21:19:40', '', 'admin:pagesecurity:delete', 151, '[0],[2],[151]', '按钮', 3, 1, '删除', 3, '2020-04-14 14:46:12', '/admin/pagesecurity/delete', 1, 1);
INSERT INTO `sys_menu` VALUES (155, '2020-05-13 22:03:06', '', 'system:blist:delete', 149, '[0],[2],[149]', '按钮', 1, 1, '删除', 3, '2020-04-14 14:46:12', '/admin/BlistManage/delete', 1, 1);
INSERT INTO `sys_menu` VALUES (156, '2020-04-06 21:19:40', NULL, 'admin:product:add', 150, '[0],[147],[150]', '按钮', 1, 1, '添加', 3, '2020-04-14 14:46:12', '/admin/admin_ProductAdd', 1, 1);
INSERT INTO `sys_menu` VALUES (157, '2020-05-13 16:06:04', '', 'ProductDetails', 2, '[0],[2]', '', 6, 3, '店长', 1, NULL, '/ProductDetails', 1, NULL);
INSERT INTO `sys_menu` VALUES (158, '2020-04-06 21:19:40', NULL, 'admin:product:update', 150, '[0],[147],[150]', '按钮', 2, 1, '修改', 3, '2020-04-14 14:46:12', '/admin/admin_ProductModify', 1, 1);
INSERT INTO `sys_menu` VALUES (159, '2020-05-13 17:43:35', '', 'admin:product:delete', 150, '[0],[147],[150]', '按钮', 3, 1, '删除', 3, NULL, '/admin/admin_ProductDelete', 1, NULL);
INSERT INTO `sys_menu` VALUES (160, '2020-05-13 17:57:21', '', 'admin:cate:add', 148, '[0],[147],[148]', '按钮', 1, 1, '添加', 3, NULL, '/admin/admin_cateAdd', 1, NULL);
INSERT INTO `sys_menu` VALUES (161, '2020-05-13 17:57:56', '', 'admin:cate:update', 148, '[0],[147],[148]', '按钮', 2, 1, '修改', 3, NULL, '/admin/admin_cateModify', 1, NULL);
INSERT INTO `sys_menu` VALUES (162, '2020-05-13 18:43:29', '', 'admin:cate:delete', 148, '[0],[147],[148]', '按钮', 3, 1, '删除', 3, NULL, '/admin/admin_cateDelete', 1, NULL);
INSERT INTO `sys_menu` VALUES (163, '2020-05-22 11:58:44', NULL, 'admin:CartManage', 147, '[0],[147]', NULL, 3, 3, '订单管理', 2, NULL, '/admin/CartManage', 1, 1);
INSERT INTO `sys_menu` VALUES (164, '2020-05-22 14:36:05', '', 'admin:cart:delete', 163, '[0],[147],[163]', '', 1, 3, '删除', 3, NULL, '/admin/CartDelete', 1, NULL);
INSERT INTO `sys_menu` VALUES (165, '2020-05-23 20:11:39', '', 'system:log:index', 2, '[0],[2]', '目录', 6, 1, '操作日志管理', 1, NULL, '/admin/LogManage', 1, NULL);
INSERT INTO `sys_menu` VALUES (166, '2020-05-23 16:41:02', '', 'admin:log:delete', 165, '[0],[2],[165]', '按钮', 1, 1, '删除', 3, NULL, '/admin/LogDelete', 1, NULL);
INSERT INTO `sys_menu` VALUES (167, '2020-05-28 22:21:53', '', 'admin:CartManage', 147, '[0],[147]', '', 3, 3, '订单管理', 2, NULL, '/admin/CartManage', 1, NULL);
INSERT INTO `sys_menu` VALUES (168, '2020-05-28 22:22:24', '', 'admin:cart:delete', 167, '[0],[147],[167]', '', 1, 3, '取消', 3, NULL, '/admin/admin_CartDelete', 1, NULL);
INSERT INTO `sys_menu` VALUES (169, '2020-05-28 23:37:21', '', 'admin:cart:delete', 167, '[0],[147],[167]', '', 1, 3, '删除', 2, NULL, '/admin/admin_CartDelete', 1, NULL);
INSERT INTO `sys_menu` VALUES (170, '2020-05-29 10:35:03', '', 'admin:CartManage', 147, '[0],[147]', '菜单', 4, 1, '订单管理', 2, NULL, '/admin/CartManage', 1, NULL);
INSERT INTO `sys_menu` VALUES (171, '2020-05-29 10:35:24', '', 'admin:cart:delete', 170, '[0],[147],[170]', '按钮', 2, 1, '删除', 3, NULL, '/admin/admin_CartDelete', 1, NULL);
INSERT INTO `sys_menu` VALUES (172, '2020-05-31 11:17:13', '', 'ProductDetails', 170, '[0],[147],[170]', '按钮', 1, 3, '新增', 3, NULL, '/ProductDetails', 1, NULL);
INSERT INTO `sys_menu` VALUES (173, '2020-05-31 22:45:54', '', 'ProductDetails', 147, '[0],[147]', '目录', 4, 3, '商品访问管理', 1, NULL, '/ProductDetails', 1, NULL);
INSERT INTO `sys_menu` VALUES (174, '2020-06-02 21:47:49', '', 'ProductDetail', 147, '[0],[147]', '目录', 4, 3, '商品访问管理', 1, NULL, '/ProductDetails', 1, NULL);
INSERT INTO `sys_menu` VALUES (175, '2020-06-03 12:54:26', '', 'ProductDetails', 147, '[0],[147]', '', 3, 3, '商品访问管理', 1, NULL, '/ProductDetails', 1, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `create_by` bigint(20) NULL DEFAULT NULL,
  `update_by` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKbruxnomdnt5glfdh1wd99tj2r`(`create_by`) USING BTREE,
  INDEX `FKrgx8ca2tgkscnvw5uxqgb4j8s`(`update_by`) USING BTREE,
  CONSTRAINT `FKbruxnomdnt5glfdh1wd99tj2r` FOREIGN KEY (`create_by`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKrgx8ca2tgkscnvw5uxqgb4j8s` FOREIGN KEY (`update_by`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '2020-05-31 13:10:43', 'admin', '', 1, '管理员', '2020-05-31 13:10:43', 1, 1);
INSERT INTO `sys_role` VALUES (5, '2020-05-31 13:08:16', 'shoper', '', 1, '店长', '2020-05-31 13:08:16', 1, NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE,
  INDEX `FKf3mud4qoc7ayew8nml4plkevo`(`menu_id`) USING BTREE,
  CONSTRAINT `FKf3mud4qoc7ayew8nml4plkevo` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKkeitxsgxwayackgqllio4ohn5` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (5, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (5, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (5, 5);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (1, 7);
INSERT INTO `sys_role_menu` VALUES (1, 8);
INSERT INTO `sys_role_menu` VALUES (1, 9);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 11);
INSERT INTO `sys_role_menu` VALUES (5, 11);
INSERT INTO `sys_role_menu` VALUES (1, 12);
INSERT INTO `sys_role_menu` VALUES (1, 13);
INSERT INTO `sys_role_menu` VALUES (5, 13);
INSERT INTO `sys_role_menu` VALUES (1, 14);
INSERT INTO `sys_role_menu` VALUES (5, 14);
INSERT INTO `sys_role_menu` VALUES (1, 15);
INSERT INTO `sys_role_menu` VALUES (1, 16);
INSERT INTO `sys_role_menu` VALUES (1, 17);
INSERT INTO `sys_role_menu` VALUES (1, 18);
INSERT INTO `sys_role_menu` VALUES (1, 19);
INSERT INTO `sys_role_menu` VALUES (1, 20);
INSERT INTO `sys_role_menu` VALUES (1, 147);
INSERT INTO `sys_role_menu` VALUES (5, 147);
INSERT INTO `sys_role_menu` VALUES (1, 148);
INSERT INTO `sys_role_menu` VALUES (1, 149);
INSERT INTO `sys_role_menu` VALUES (1, 150);
INSERT INTO `sys_role_menu` VALUES (1, 151);
INSERT INTO `sys_role_menu` VALUES (1, 152);
INSERT INTO `sys_role_menu` VALUES (1, 153);
INSERT INTO `sys_role_menu` VALUES (1, 154);
INSERT INTO `sys_role_menu` VALUES (1, 155);
INSERT INTO `sys_role_menu` VALUES (1, 156);
INSERT INTO `sys_role_menu` VALUES (1, 158);
INSERT INTO `sys_role_menu` VALUES (1, 159);
INSERT INTO `sys_role_menu` VALUES (1, 160);
INSERT INTO `sys_role_menu` VALUES (1, 161);
INSERT INTO `sys_role_menu` VALUES (1, 162);
INSERT INTO `sys_role_menu` VALUES (1, 165);
INSERT INTO `sys_role_menu` VALUES (1, 166);
INSERT INTO `sys_role_menu` VALUES (1, 170);
INSERT INTO `sys_role_menu` VALUES (5, 170);
INSERT INTO `sys_role_menu` VALUES (1, 171);
INSERT INTO `sys_role_menu` VALUES (5, 171);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `FKhh52n8vd4ny9ff4x9fb8v65qx`(`role_id`) USING BTREE,
  CONSTRAINT `FKhh52n8vd4ny9ff4x9fb8v65qx` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKjyhkvwgvnte8yr010vwsey66h` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (102, 1);
INSERT INTO `sys_user_role` VALUES (185, 5);
INSERT INTO `sys_user_role` VALUES (195, 5);

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (39, 'Mainland');
INSERT INTO `type` VALUES (40, 'Asia');
INSERT INTO `type` VALUES (43, 'America');
INSERT INTO `type` VALUES (44, 'Europe');
INSERT INTO `type` VALUES (217, '&amp lt;&amp gt;alert&amp #40;&amp #39;aa&amp #39;&amp #41;;&amp lt;/&amp gt;');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NOT NULL,
  `retry_time` datetime(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'wuGi09FB/sYk1FzUEHRaZS8tGhlzsCEARSA37uV43bc=', 'admin', '5f4b84847ab05acd519cfc254ef234008ee4ac9b43c0e1a80e38d3c4c2f5d8f8', '1e21fb', '女', 0, NULL);
INSERT INTO `user` VALUES (122, 'wuGi09FB/sYk1FzUEHRaZS8tGhlzsCEARSA37uV43bc=', 'BBB', 'ef1b9e112e74f6276393321b2bf6abd064cca2887b59fc4d716be2cecb01950e', '75cc43', '女', 0, NULL);
INSERT INTO `user` VALUES (185, '4vHuJawn7DdgO7esy48WkprNqzXhHHL2pItLeAV5FhaJkokZGM2GYzcluC0l4A14fhBpxOEqnaC/0X3GJP3u1Dse0G0XWk19mntjbVTX/ng=', 'test1', '3de78f1506da1da8012ee2ebbc7f2352ccb7652da46e200bb491bdb50ae84814', 'b7a4ca', '女', 1, '2020-06-02 15:35:02.464000');
INSERT INTO `user` VALUES (186, 'wuGi09FB/sYk1FzUEHRaZS8tGhlzsCEARSA37uV43bc=', 'test2', '738440fded90eef85bf78098aab57841801acbbec7c5ed1e7d01e50ba27a881a', 'fcd4e5', '女', 0, NULL);
INSERT INTO `user` VALUES (195, 'wuGi09FB/sYk1FzUEHRaZS8tGhlzsCEARSA37uV43bc=', 'test', '27cb983d17bb34e49c6997031bafe05ad6ce4a12201a087c5d36d676f0d90ebc', 'd153d7', '女', 0, NULL);

SET FOREIGN_KEY_CHECKS = 1;
