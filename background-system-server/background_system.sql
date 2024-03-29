/*
 Navicat Premium Data Transfer

 Source Server         : Local Mysql
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : background_system

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 13/04/2023 17:34:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP database IF EXISTS background_system;
create database background_system;
use background_system;
-- ----------------------------
-- Table structure for certificate
-- ----------------------------
DROP TABLE IF EXISTS `certificate`;
CREATE TABLE `certificate`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `brand` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '品牌',
  `manufacturing_country` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '制造国',
  `vehicle_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '整车型号',
  `vehicle_identification_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车辆识别代号',
  `engine_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发动机型号',
  `engine_capacity` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发动机排量',
  `maximum_net_engine_power` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发动机最大净功率',
  `vintage` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '制造年月',
  `maximum_allowable_total_mass` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最大允许总质量',
  `number_of_passengers` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '乘坐人数',
  `name_of_manufacturer` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产厂名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '合格证表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of certificate
-- ----------------------------

-- ----------------------------
-- Table structure for certificate_check_log
-- ----------------------------
DROP TABLE IF EXISTS `certificate_check_log`;
CREATE TABLE `certificate_check_log`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `certificate_data` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '合格证数据',
  `identifying_data` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '识别数据',
  `contrast_data` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对比数据',
  `type` tinyint(4) NOT NULL COMMENT '类型: 0上线 1下线',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '合格证核对日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of certificate_check_log
-- ----------------------------
INSERT INTO `certificate_check_log` VALUES (843489795400269825, '{\n	\"brand\":\"丰田(TOYOTA)\",\n	\"engineCapacity\":\"2487ML\",\n	\"engineType\":\"A256\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2O7OK6\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFMAJ54K5PSO45277\",\n	\"vehicleType\":\"SCT725O6\",\n	\"vintage\":\"2O23年1月\"\n}', '{\n	\"brand\":\"丰田(TOYOTA)\",\n	\"engineCapacity\":\"2487ML\",\n	\"engineType\":\"A256\",\n	\"manufacturerSpecialNumber\":\"AXXA5OL一JEZ6BC2O9LB2OUB8OE\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2O7OK6\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFAJ54K5PSO45217\",\n	\"vehicleType\":\"SCT725O6\",\n	\"vintage\":\"2O23年O1月\"\n}', '{\n	\"brand\":\"丰田(TOYOTA)\",\n	\"engineCapacity\":\"2487ML\",\n	\"engineType\":\"A256\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2O7OK6\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFMAJ54K5PSO45<font color=\'red\'>277</font>\",\n	\"vehicleType\":\"SCT725O6\",\n	\"vintage\":\"2O23年1<font color=\'red\'>月</font>\"\n}', 0, '2023-04-12 11:28:24');
INSERT INTO `certificate_check_log` VALUES (843496771400237057, '{\n	\"brand\":\"丰田(TOYOTA)\",\n	\"engineCapacity\":\"2487ML\",\n	\"engineType\":\"A256\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2O7OK6\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFMAJ54K5PSO45277\",\n	\"vehicleType\":\"SCT725O6\",\n	\"vintage\":\"2O23年1月\"\n}', '{\n	\"brand\":\"丰田(TOYOIA)\",\n	\"engineCapacity\":\"2487ML\",\n	\"engineType\":\"A256\",\n	\"manufacturerSpecialNumber\":\"AXXA5OL一JEZ6BC2O9LB2OUB8OE\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2O7OK6\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFMAJ54K5PSO45277\",\n	\"vehicleType\":\"SCT725O6\",\n	\"vintage\":\"2O23年O1月\"\n}', '{\n	\"brand\":\"丰田(TOYO<font color=\'red\'>T</font>A)\",\n	\"engineCapacity\":\"2487ML\",\n	\"engineType\":\"A256\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2O7OK6\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFMAJ54K5PSO45277\",\n	\"vehicleType\":\"SCT725O6\",\n	\"vintage\":\"2O23年1<font color=\'red\'>月</font>\"\n}', 0, '2023-04-12 11:56:07');
INSERT INTO `certificate_check_log` VALUES (843542631140032513, '{\n	\"brand\":\"丰田(TOYOTA)\",\n	\"engineCapacity\":\"2487ML\",\n	\"engineType\":\"A256\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2O7OK6\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFMAJ54K5PSO45277\",\n	\"vehicleType\":\"SCT725O6\",\n	\"vintage\":\"2O23年1月\"\n}', '{\n	\"brand\":\"丰田(TOYOTA)\",\n	\"engineCapacity\":\"2487ML\",\n	\"engineType\":\"A256\",\n	\"manufacturerSpecialNumber\":\"AXXA5OL一JEZ6BC2O9LB2OUB8OE\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2O7OK6\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFAJ54K5PSO45277\",\n	\"vehicleType\":\"SCT725O6\",\n	\"vintage\":\"2O23年O1月\"\n}', '{\n	\"brand\":\"丰田(TOYOTA)\",\n	\"engineCapacity\":\"2487ML\",\n	\"engineType\":\"A256\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2O7OK6\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFMAJ54K5PSO45<font color=\'red\'>277</font>\",\n	\"vehicleType\":\"SCT725O6\",\n	\"vintage\":\"2O23年1<font color=\'red\'>月</font>\"\n}', 0, '2023-04-12 14:58:21');
INSERT INTO `certificate_check_log` VALUES (843551400897019905, '{\n	\"brand\":\"丰田(TOYOTA)\",\n	\"engineCapacity\":\"2487ML\",\n	\"engineType\":\"A256\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2O7OK6\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFMAJ54K5PSO45277\",\n	\"vehicleType\":\"SCT725O6\",\n	\"vintage\":\"2O23年1月\"\n}', '{\n	\"brand\":\"丰田(IOYOTA)\",\n	\"engineCapacity\":\"2487ML\",\n	\"engineType\":\"A256\",\n	\"manufacturerSpecialNumber\":\"AXXA5OL一JEZEBCO89LO2OUB8OE\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2O7OK6\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFLAJ54K7PSO45281\",\n	\"vehicleType\":\"SCT725OE\",\n	\"vintage\":\"2O23年O1月\"\n}', '{\n	\"brand\":\"丰田(<font color=\'red\'>T</font>OYOTA)\",\n	\"engineCapacity\":\"2487ML\",\n	\"engineType\":\"A256\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2O7OK6\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LF<font color=\'red\'>M</font>AJ54K<font color=\'red\'>5</font>PSO452<font color=\'red\'>7</font><font color=\'red\'>7</font>\",\n	\"vehicleType\":\"SCT725O<font color=\'red\'>6</font>\",\n	\"vintage\":\"2O23年1<font color=\'red\'>月</font>\"\n}', 0, '2023-04-12 15:33:12');
INSERT INTO `certificate_check_log` VALUES (843553712638001153, '{\n	\"brand\":\"丰田(TOYOTA)\",\n	\"engineCapacity\":\"2487ML\",\n	\"engineType\":\"A256\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2O7OK6\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFMAJ54K5PSO45277\",\n	\"vehicleType\":\"SCT725O6\",\n	\"vintage\":\"2O23年1月\"\n}', '{\n	\"brand\":\"丰田(T0Y0TA)\",\n	\"engineCapacity\":\"2487ML\",\n	\"engineType\":\"A256\",\n	\"manufacturerSpecialNumber\":\"AXXA50L-JEZEBC089L020UB80E\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2070KG\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(咸都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFLAJ54K7PS045281\",\n	\"vehicleType\":\"SCT7250E\",\n	\"vintage\":\"2023年01月\"\n}', '{\n	\"brand\":\"丰田(T<font color=\'red\'>O</font>Y<font color=\'red\'>O</font>TA)\",\n	\"engineCapacity\":\"2487ML\",\n	\"engineType\":\"A256\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2<font color=\'red\'>O</font>7<font color=\'red\'>O</font>K<font color=\'red\'>6</font>\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(<font color=\'red\'>成</font>都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LF<font color=\'red\'>M</font>AJ54K<font color=\'red\'>5</font>PS<font color=\'red\'>O</font>452<font color=\'red\'>7</font><font color=\'red\'>7</font>\",\n	\"vehicleType\":\"SCT725<font color=\'red\'>O</font><font color=\'red\'>6</font>\",\n	\"vintage\":\"2O23年1<font color=\'red\'>月</font>\"\n}', 0, '2023-04-12 15:42:23');
INSERT INTO `certificate_check_log` VALUES (843555540742176769, '{\n	\"brand\":\"丰田(TOYOTA)\",\n	\"engineCapacity\":\"2487mL\",\n	\"engineType\":\"A256\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2O7OKg\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFMAJ54K5PSO45277\",\n	\"vehicleType\":\"SCT725O6\",\n	\"vintage\":\"2O23年1月\"\n}', '{\n	\"brand\":\"丰田(T0YOTA)\",\n	\"engineCapacity\":\"1987ML\",\n	\"engineType\":\"M20E\",\n	\"manufacturerSpecialNumber\":\"MXXA50L-JEXBBC1K0LC40K121\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2070KG\",\n	\"maximumNetEnginePower\":\"130KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFMAP54K2PS092345\",\n	\"vehicleType\":\"SCT7202\",\n	\"vintage\":\"2023年04月\"\n}', '{\n	\"brand\":\"丰田(T<font color=\'red\'>O</font>YOTA)\",\n	\"engineCapacity\":\"<font color=\'red\'>2</font><font color=\'red\'>4</font>87<font color=\'red\'>m</font>L\",\n	\"engineType\":\"<font color=\'red\'>A</font>2<font color=\'red\'>5</font><font color=\'red\'>6</font>\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2<font color=\'red\'>O</font>7<font color=\'red\'>O</font>K<font color=\'red\'>g</font>\",\n	\"maximumNetEnginePower\":\"1<font color=\'red\'>5</font><font color=\'red\'>4</font>KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFMA<font color=\'red\'>J</font>54K<font color=\'red\'>5</font>PS<font color=\'red\'>O</font><font color=\'red\'>4</font><font color=\'red\'>5</font><font color=\'red\'>2</font><font color=\'red\'>7</font><font color=\'red\'>7</font>\",\n	\"vehicleType\":\"SCT725<font color=\'red\'>O6</font>\",\n	\"vintage\":\"2O23年1<font color=\'red\'>月</font>\"\n}', 0, '2023-04-12 15:49:39');
INSERT INTO `certificate_check_log` VALUES (843558687762022401, '{\n	\"brand\":\"丰田(TOYOTA)\",\n	\"engineCapacity\":\"2487mL\",\n	\"engineType\":\"A256\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2070Kg\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFMAJ54K5PS045277\",\n	\"vehicleType\":\"SCT72506\",\n	\"vintage\":\"2O23年1月\"\n}', '{\n	\"brand\":\"丰田(T0Y0TA)\",\n	\"engineCapacity\":\"2487ML\",\n	\"engineType\":\"A256\",\n	\"manufacturerSpecialNumber\":\"AXXA50L-JEZEBC089LC20UB80E\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2070KG\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFLAJ54K7PS045281\",\n	\"vehicleType\":\"SCT7250E\",\n	\"vintage\":\"2023年01月\"\n}', '{\n	\"brand\":\"丰田(T<font color=\'red\'>O</font>Y<font color=\'red\'>O</font>TA)\",\n	\"engineCapacity\":\"2487<font color=\'red\'>m</font>L\",\n	\"engineType\":\"A256\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2070K<font color=\'red\'>g</font>\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LF<font color=\'red\'>M</font>AJ54K<font color=\'red\'>5</font>PS0452<font color=\'red\'>7</font><font color=\'red\'>7</font>\",\n	\"vehicleType\":\"SCT7250<font color=\'red\'>6</font>\",\n	\"vintage\":\"2O23年1<font color=\'red\'>月</font>\"\n}', 0, '2023-04-12 16:02:09');
INSERT INTO `certificate_check_log` VALUES (843565861661835264, '{\n	\"brand\":\"丰田(TOYOTA)\",\n	\"engineCapacity\":\"2487mL\",\n	\"engineType\":\"A256\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2070Kg\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFMAJ54K5PS045277\",\n	\"vehicleType\":\"SCT72506\",\n	\"vintage\":\"2023年1月\"\n}', '{\n	\"brand\":\"丰田(T0Y0TA)\",\n	\"engineCapacity\":\"2487ML\",\n	\"engineType\":\"A25G\",\n	\"manufacturerSpecialNumber\":\"AXXA50L-JEZEBC089LC20UB80E\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2070KG\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFMAJ54K7PS045281\",\n	\"vehicleType\":\"SCT7250E\",\n	\"vintage\":\"2023年01月\"\n}', '{\n	\"brand\":\"丰田(T<font color=\'red\'>O</font>Y<font color=\'red\'>O</font>TA)\",\n	\"engineCapacity\":\"2487<font color=\'red\'>m</font>L\",\n	\"engineType\":\"A25<font color=\'red\'>6</font>\",\n	\"manufacturingCountry\":\"中华人民共和国\",\n	\"maximumAllowableTotalMass\":\"2070K<font color=\'red\'>g</font>\",\n	\"maximumNetEnginePower\":\"154KW\",\n	\"nameOfManufacturer\":\"一汽丰田汽车(成都)有限公司\",\n	\"numberOfPassengers\":\"5\",\n	\"vehicleIdentificationNumber\":\"LFMAJ54K<font color=\'red\'>5</font>PS0452<font color=\'red\'>7</font><font color=\'red\'>7</font>\",\n	\"vehicleType\":\"SCT7250<font color=\'red\'>6</font>\",\n	\"vintage\":\"2023年1<font color=\'red\'>月</font>\"\n}', 0, '2023-04-12 16:30:39');

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `dict_type_id` bigint(20) NOT NULL COMMENT '字典类型id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典名称',
  `value` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典值',
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `order` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (837375398915342336, 837372738753527809, 'POST', 'POST', NULL, 2);
INSERT INTO `sys_dict_data` VALUES (837375444528398337, 837372738753527809, 'GET', 'GET', NULL, 1);
INSERT INTO `sys_dict_data` VALUES (837375484584001537, 837372738753527809, 'PATCH', 'PATCH', '', 3);
INSERT INTO `sys_dict_data` VALUES (837375853091356673, 837372738753527809, 'DELETE', 'DELETE', NULL, 4);
INSERT INTO `sys_dict_data` VALUES (837386318987132929, 837372738753527809, 'PUT', 'PUT', NULL, 5);
INSERT INTO `sys_dict_data` VALUES (837727715346874369, 837727191054680065, 'all', 'all', NULL, 1);
INSERT INTO `sys_dict_data` VALUES (837727744329515009, 837727191054680065, 'warn', 'warn', NULL, 2);
INSERT INTO `sys_dict_data` VALUES (837727769260457985, 837727191054680065, 'error', 'error', NULL, 3);
INSERT INTO `sys_dict_data` VALUES (837727799295868929, 837727191054680065, 'info', 'info', NULL, 4);
INSERT INTO `sys_dict_data` VALUES (837727834834206721, 837727191054680065, 'debug', 'debug', NULL, 5);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典类型名称',
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (837372738753527809, 'Http请求方法', NULL);
INSERT INTO `sys_dict_type` VALUES (837727191054680065, '日志级别', NULL);

-- ----------------------------
-- Table structure for sys_interface_request_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_interface_request_log`;
CREATE TABLE `sys_interface_request_log`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '请求用户',
  `request_time` datetime(0) NOT NULL COMMENT '请求时间',
  `spend_time` int(11) NOT NULL COMMENT '消耗时间（毫秒）',
  `request_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求路径',
  `request_method` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求方法',
  `request_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求IP',
  `request_param` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求参数',
  `response_result` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '响应的结果',
  `status` tinyint(4) NOT NULL COMMENT '状态 0异常 1正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '接口请求日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_interface_request_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父菜单id',
  `router_path` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由路径',
  `router_component` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由组件',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `type` tinyint(4) NOT NULL COMMENT '0菜单1页面2权限',
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `order` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', NULL, '', '', 'el-icon-s-tools', 0, '', 2);
INSERT INTO `sys_menu` VALUES (3, '角色管理', 1, '/home/system/sysRole', '/home/system/SysRole.vue', 'el-icon-s-check', 1, NULL, 2);
INSERT INTO `sys_menu` VALUES (4, '菜单管理', 1, '/home/system/sysMenu', '/home/system/SysMenu.vue', 'el-icon-menu', 1, NULL, 3);
INSERT INTO `sys_menu` VALUES (5, '首页', NULL, '/home/index', '/home/Index.vue', NULL, 1, NULL, 0);
INSERT INTO `sys_menu` VALUES (6, '日志管理', NULL, '', NULL, 'el-icon-s-order', 0, NULL, 3);
INSERT INTO `sys_menu` VALUES (7, '系统运行日志', 6, '/home/log/sysSystemRunLog', '/home/log/SysSystemRunLog.vue', NULL, 1, NULL, 2);
INSERT INTO `sys_menu` VALUES (18, '模板', NULL, NULL, NULL, 'el-icon-s-grid', 0, NULL, 4);
INSERT INTO `sys_menu` VALUES (20, '定时任务', 1, '/home/system/timedTask', '/home/system/SysTimedTask.vue', 'el-icon-alarm-clock', 1, NULL, 4);
INSERT INTO `sys_menu` VALUES (21, '接口请求日志', 6, '/home/log/sysInterfaceRequestLog', '/home/log/SysInterfaceRequestLog.vue', NULL, 1, NULL, 1);
INSERT INTO `sys_menu` VALUES (29, '用户管理', 1, '/home/system/sysUser', '/home/system/SysUser.vue', 'el-icon-user-solid', 1, NULL, 1);
INSERT INTO `sys_menu` VALUES (30, '监控状态', NULL, NULL, NULL, 'el-icon-view', 0, NULL, 1);
INSERT INTO `sys_menu` VALUES (32, '字典管理', 1, '/home/system/sysDict', '/home/system/SysDict.vue', 'el-icon-notebook-2', 1, NULL, 5);
INSERT INTO `sys_menu` VALUES (33, '合格证核对监控', 30, '/monitoring/certificateVerification', '/home/monitoring/CertificateVerification.vue', NULL, 1, NULL, 1);
INSERT INTO `sys_menu` VALUES (34, '合格证核对日志', 6, '/home/log/certificateCheckLog', '/home/log/CertificateCheckLog.vue', NULL, 1, NULL, 3);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', '最高权限角色');
INSERT INTO `sys_role` VALUES (2, '普通会员', '普通权限');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `menu_id` int(11) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 295 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (273, 1, 5);
INSERT INTO `sys_role_menu` VALUES (274, 1, 30);
INSERT INTO `sys_role_menu` VALUES (275, 1, 33);
INSERT INTO `sys_role_menu` VALUES (276, 1, 1);
INSERT INTO `sys_role_menu` VALUES (277, 1, 29);
INSERT INTO `sys_role_menu` VALUES (278, 1, 3);
INSERT INTO `sys_role_menu` VALUES (279, 1, 4);
INSERT INTO `sys_role_menu` VALUES (280, 1, 20);
INSERT INTO `sys_role_menu` VALUES (281, 1, 32);
INSERT INTO `sys_role_menu` VALUES (282, 1, 6);
INSERT INTO `sys_role_menu` VALUES (283, 1, 21);
INSERT INTO `sys_role_menu` VALUES (284, 1, 7);
INSERT INTO `sys_role_menu` VALUES (285, 1, 34);
INSERT INTO `sys_role_menu` VALUES (286, 1, 18);
INSERT INTO `sys_role_menu` VALUES (287, 2, 5);
INSERT INTO `sys_role_menu` VALUES (288, 2, 1);
INSERT INTO `sys_role_menu` VALUES (289, 2, 29);
INSERT INTO `sys_role_menu` VALUES (290, 2, 3);
INSERT INTO `sys_role_menu` VALUES (291, 2, 4);
INSERT INTO `sys_role_menu` VALUES (292, 2, 6);
INSERT INTO `sys_role_menu` VALUES (293, 2, 21);
INSERT INTO `sys_role_menu` VALUES (294, 2, 34);

-- ----------------------------
-- Table structure for sys_timed_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_timed_task`;
CREATE TABLE `sys_timed_task`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `class_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务类名',
  `cron` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'cron表达式',
  `param` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_timed_task
-- ----------------------------
INSERT INTO `sys_timed_task` VALUES (4, 'com.chenyudaima.task.DeleteSystemLogTimeTask', '0 55 17 * * ?', '{\nsys_timed_task_log_the_other_day:7,\nsys_interface_request_log_the_other_day:7,\ncertificate_check_log_the_other_day:7\n}', 1, '下午5点55执行\n\n定时任务日志\nsys_timed_task_log_the_other_day \n\n接口请求日志\nsys_interface_request_log_the_other_day\n\n合格证核对日志\ncertificate_check_log_the_other_day');

-- ----------------------------
-- Table structure for sys_timed_task_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_timed_task_log`;
CREATE TABLE `sys_timed_task_log`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `timed_task_id` int(11) NOT NULL COMMENT '定时任务id',
  `execute_param` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行参数',
  `execute_result` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行结果 | 异常信息',
  `start_execute_time` datetime(0) NOT NULL COMMENT '开始执行时间',
  `execute_status` tinyint(4) NOT NULL COMMENT '执行状态 0异常1正常',
  `elapsed_time` int(11) NOT NULL COMMENT '耗时(毫秒)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_timed_task_log
-- ----------------------------
INSERT INTO `sys_timed_task_log` VALUES (841775142362152960, 4, '{\nsys_timed_task_log_the_other_day:7,\nsys_interface_request_log_the_other_day:7\n}', NULL, '2023-04-07 17:54:58', 1, 162);
INSERT INTO `sys_timed_task_log` VALUES (842499922417680384, 4, '{\nsys_timed_task_log_the_other_day:7,\nsys_interface_request_log_the_other_day:7\n}', NULL, '2023-04-09 17:55:00', 1, 70);
INSERT INTO `sys_timed_task_log` VALUES (842862310291668992, 4, '{\nsys_timed_task_log_the_other_day:7,\nsys_interface_request_log_the_other_day:7\n}', NULL, '2023-04-10 17:55:00', 1, 192);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `age` tinyint(4) NULL DEFAULT NULL COMMENT '年龄',
  `sex` tinyint(1) NULL DEFAULT NULL COMMENT '性别 0男 1女',
  `status` tinyint(1) NOT NULL COMMENT '状态 0冻结 1正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '系统管理员', 'admin', '123456', '11', 21, 0, 1);
INSERT INTO `sys_user` VALUES (97, '质检管理员', 'root', '123456', '17607088007', 22, 0, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (22, 97, 2);
INSERT INTO `sys_user_role` VALUES (38, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
