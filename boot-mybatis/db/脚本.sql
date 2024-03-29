DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `cid` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `cname` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('c001', '家电');
INSERT INTO `category` VALUES ('c002', '鞋服');
INSERT INTO `category` VALUES ('c003', '化妆品');
INSERT INTO `category` VALUES ('c004', '汽车');

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
  `pid` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `pname` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `price` int NULL DEFAULT NULL,
  `flag` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `category_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`pid`) USING BTREE,
  INDEX `category_id`(`category_id`) USING BTREE,
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`cid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES ('p001', '小\r\n米电视 机', 5000, '1', 'c001');
INSERT INTO `products` VALUES ('p002', '格\r\n力空调', 3000, '1', 'c001');
INSERT INTO `products` VALUES ('p003', '美\r\n的冰箱', 4500, '1', 'c001');
INSERT INTO `products` VALUES ('p004', '篮\r\n球鞋', 800, '1', 'c002');
INSERT INTO `products` VALUES ('p005', '运\r\n动裤', 200, '1', 'c002');
INSERT INTO `products` VALUES ('p006', 'T\r\n恤', 300, '1', 'c002');
INSERT INTO `products` VALUES ('p009', '篮球', 188, '1', 'c002');

