/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : zhaoshang_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-07-20 14:59:21
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
-- Table structure for `t_baoming`
-- ----------------------------
DROP TABLE IF EXISTS `t_baoming`;
CREATE TABLE `t_baoming` (
  `baomingId` int(11) NOT NULL auto_increment COMMENT '报名id',
  `projectObj` int(11) NOT NULL COMMENT '报名的项目',
  `companyObj` varchar(20) NOT NULL COMMENT '报名的企业',
  `baomingTime` varchar(20) default NULL COMMENT '报名时间',
  PRIMARY KEY  (`baomingId`),
  KEY `projectObj` (`projectObj`),
  KEY `companyObj` (`companyObj`),
  CONSTRAINT `t_baoming_ibfk_1` FOREIGN KEY (`projectObj`) REFERENCES `t_project` (`projectId`),
  CONSTRAINT `t_baoming_ibfk_2` FOREIGN KEY (`companyObj`) REFERENCES `t_company` (`companyUserName`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_baoming
-- ----------------------------
INSERT INTO `t_baoming` VALUES ('1', '1', 'qy2', '2018-04-18 23:39:53');

-- ----------------------------
-- Table structure for `t_company`
-- ----------------------------
DROP TABLE IF EXISTS `t_company`;
CREATE TABLE `t_company` (
  `companyUserName` varchar(20) NOT NULL COMMENT 'companyUserName',
  `password` varchar(20) NOT NULL COMMENT '登录密码',
  `companyName` varchar(20) NOT NULL COMMENT '企业名称',
  `gszch` varchar(20) NOT NULL COMMENT '工商注册号',
  `yyzz` varchar(60) NOT NULL COMMENT '营业执照',
  `gsxz` varchar(20) NOT NULL COMMENT '公司性质',
  `gsgm` varchar(20) NOT NULL COMMENT '公司规模',
  `hangyeObj` int(11) NOT NULL COMMENT '公司行业',
  `lxr` varchar(20) NOT NULL COMMENT '联系人',
  `lxdh` varchar(20) NOT NULL COMMENT '联系电话',
  `companyDesc` varchar(8000) NOT NULL COMMENT '公司介绍',
  `shzt` varchar(20) NOT NULL COMMENT '审核状态',
  `regTime` varchar(20) default NULL COMMENT '注册时间',
  PRIMARY KEY  (`companyUserName`),
  KEY `hangyeObj` (`hangyeObj`),
  CONSTRAINT `t_company_ibfk_1` FOREIGN KEY (`hangyeObj`) REFERENCES `t_hangye` (`hangyeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_company
-- ----------------------------
INSERT INTO `t_company` VALUES ('qy1', '123', '成都乐牛科技有限公司', '91510100060092779R', 'upload/bc8d606e-ea1f-4a81-848f-a3a1a354f1fa.jpg', '私营', '50-99人', '1', '高女士', '028-83984083', '<p><span style=\"color: rgb(51, 51, 51); font-family: Arial, 宋体; font-size: 14px;\">本公司是一家集批发、零售|实体洗护为一体的宠物用品电子商务公司。公司目前主要经营来自美国、澳大利亚、加拿大、德国、法国、韩国及日本等国家的高档宠物用品。我们拥有淘宝网宠物用品行业十大金冠店铺——中国宠商在线，目前在淘宝网全国3万5千多家宠物用品店铺中排名第五，西南地区500多家宠物用品店铺排名第一。公司还拥有一家天猫商城——乐牛宠物用品专营店，实体连锁店——麦乐萌宠物洗护。公司现有员工100余人，我们年轻富有梦想、激情富有朝气。现因公司业务飞速增长，需要面向社会招纳英才</span></p>', '已审核', '2018-04-08 00:24:02');
INSERT INTO `t_company` VALUES ('qy2', '123', '盛垣商贸有限公司', '915101056890010298', 'upload/bc8d606e-ea1f-4a81-848f-a3a1a354f1fa.jpg', '私营', '1-49人', '1', '赵凯旋', '028-82834234', '<p>统一社会信用代码：915101056890010298</p><p>成立日期：2009-04-28</p><p>组织机构代码：689001029</p><p>经营期限：2009-04-28至3999-01-01</p><p>企业类型：有限责任公司(自然人投资或控股)</p><p>登记机关：青羊区市场和质量监督管理局</p><p>经营状态：存续（在营、开业、在册）</p><p>注册地址：成都市青羊区腾飞大道51号工业集中发展区5-B幢</p><p>注册资本：100.000000万人民币</p><p>经营范围：销售：纺织、服装及家庭用品，首饰、工艺品及收藏品，建材，五金交电，电脑及配件。（依法须经批准的项目，经相关部门批准后方可开展经营活动）。</p><p><br/></p>', '已审核', '2018-04-08 00:44:57');

-- ----------------------------
-- Table structure for `t_hangye`
-- ----------------------------
DROP TABLE IF EXISTS `t_hangye`;
CREATE TABLE `t_hangye` (
  `hangyeId` int(11) NOT NULL auto_increment COMMENT '行业id',
  `hangyeName` varchar(20) NOT NULL COMMENT '行业名称',
  PRIMARY KEY  (`hangyeId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_hangye
-- ----------------------------
INSERT INTO `t_hangye` VALUES ('1', '科技行业');
INSERT INTO `t_hangye` VALUES ('2', '制造业');
INSERT INTO `t_hangye` VALUES ('3', '农林牧渔');
INSERT INTO `t_hangye` VALUES ('4', '新能源新材料');

-- ----------------------------
-- Table structure for `t_leaveword`
-- ----------------------------
DROP TABLE IF EXISTS `t_leaveword`;
CREATE TABLE `t_leaveword` (
  `leaveWordId` int(11) NOT NULL auto_increment COMMENT '留言id',
  `leaveTitle` varchar(80) NOT NULL COMMENT '留言标题',
  `leaveContent` varchar(2000) NOT NULL COMMENT '留言内容',
  `companyObj` varchar(20) NOT NULL COMMENT '留言单位',
  `leaveTime` varchar(20) default NULL COMMENT '留言时间',
  `replyContent` varchar(1000) default NULL COMMENT '管理回复',
  `replyTime` varchar(20) default NULL COMMENT '回复时间',
  PRIMARY KEY  (`leaveWordId`),
  KEY `companyObj` (`companyObj`),
  CONSTRAINT `t_leaveword_ibfk_1` FOREIGN KEY (`companyObj`) REFERENCES `t_company` (`companyUserName`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_leaveword
-- ----------------------------
INSERT INTO `t_leaveword` VALUES ('1', '我想发布招商项目', '我是帮朋友发布项目能行吗？', 'qy1', '2018-04-08 00:25:02', '只能发布你自己单位的项目哈', '2018-04-08 00:25:05');
INSERT INTO `t_leaveword` VALUES ('2', '11111', '2222', 'qy1', '2018-04-08 00:46:33', '3333333', '2018-04-08 00:46:37');
INSERT INTO `t_leaveword` VALUES ('3', '222222', '33333', 'qy2', '2018-04-18 23:23:18', '--', '--');

-- ----------------------------
-- Table structure for `t_news`
-- ----------------------------
DROP TABLE IF EXISTS `t_news`;
CREATE TABLE `t_news` (
  `newsId` int(11) NOT NULL auto_increment COMMENT '新闻id',
  `newsType` varchar(20) NOT NULL COMMENT '信息类别',
  `title` varchar(80) NOT NULL COMMENT '标题',
  `newsPhoto` varchar(60) NOT NULL COMMENT '新闻图片',
  `content` varchar(5000) NOT NULL COMMENT '内容',
  `publishDate` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`newsId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_news
-- ----------------------------
INSERT INTO `t_news` VALUES ('1', '新闻资讯', '招商网站成立了', 'upload/417f93ce-2210-4492-a876-ebc3b7cb9c0e.jpg', '<p>欢迎各大企业朋友来投资</p>', '2018-04-08 00:24:53');
INSERT INTO `t_news` VALUES ('2', '扶持政策', '关于加强中小企业竞争力项目', 'upload/9ec9335f-250c-4364-91f6-69d9528992aa.jpg', '<p>为了让更多的中小企业能快速发展，政府特加大力度投资新项目的研发！</p>', '2018-04-08 00:41:47');

-- ----------------------------
-- Table structure for `t_project`
-- ----------------------------
DROP TABLE IF EXISTS `t_project`;
CREATE TABLE `t_project` (
  `projectId` int(11) NOT NULL auto_increment COMMENT '项目id',
  `hangyeObj` int(11) NOT NULL COMMENT '所属行业',
  `projectName` varchar(50) NOT NULL COMMENT '项目名称',
  `projectPhoto` varchar(60) NOT NULL COMMENT '项目图片',
  `zsDate` varchar(20) default NULL COMMENT '招商日期',
  `xmAddress` varchar(20) NOT NULL COMMENT '项目地点',
  `zsAddress` varchar(20) NOT NULL COMMENT '招商地点',
  `tzed` varchar(20) NOT NULL COMMENT '投资额度',
  `lxfs` varchar(20) NOT NULL COMMENT '联系方式',
  `projectDesc` varchar(8000) NOT NULL COMMENT '项目介绍',
  `companyObj` varchar(20) NOT NULL COMMENT '招商单位',
  `shzt` varchar(20) NOT NULL COMMENT '审核状态',
  `shhf` varchar(500) NOT NULL COMMENT '审核回复',
  `addTime` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`projectId`),
  KEY `hangyeObj` (`hangyeObj`),
  KEY `companyObj` (`companyObj`),
  CONSTRAINT `t_project_ibfk_1` FOREIGN KEY (`hangyeObj`) REFERENCES `t_hangye` (`hangyeId`),
  CONSTRAINT `t_project_ibfk_2` FOREIGN KEY (`companyObj`) REFERENCES `t_company` (`companyUserName`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_project
-- ----------------------------
INSERT INTO `t_project` VALUES ('1', '1', '智慧式电气火灾云服务平台建设项目', 'upload/decfdeb0-d884-4eef-a4e6-5799797ca9be.jpg', '2018-04-20', '开发区通盛大道188号创业外包服务中心', '江苏省南通市', '1000万以内', '18051669757', '<p><span style=\"color: rgb(51, 51, 51); font-family: 微软雅黑; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp; 智慧式电气火灾云服务系统(简称“智慧云”系统)，是针对我国当前电气火灾事故频发而创新的一套电气火灾预警和预防管理系统及增值服务。该系统是基于物联传感、移动互联、灾情原因分析大数据碰撞等现代信息技术而形成的综合性云服务平台。“智慧云”系统不受时间、地点、环境的限制，应用后，通过本地化“电气火灾综合防治监控中心”（拟建）行使其职能，可远程实时监控全区企事业单位、人员密集场所、高、重危企业、十小场所等事故发生高风险区域涉电系统7×24小时运行情况。功能上实现“多方联动预警、出警、定位隐患具体线路，监控隐患发展趋势、分析隐患成因，形成具体解决方案”。能够协助政府监管层在潜在电气火灾事故面前做到“责任主体风险有预判、监管部门工作有抓手、整改措施施行有依据” 彻底防患全区电气火灾事故发生于未“燃&quot;</span></p>', 'qy1', '已审核', '不错的项目', '2018-04-08 00:24:31');
INSERT INTO `t_project` VALUES ('2', '1', '绿色智慧市政及关联建设项目', 'upload/bf993528-27bb-44aa-aeb3-e71592e2ced9.jpg', '2018-04-18', '舞钢市', '河南省平顶山市舞钢市', '10000万以上', '4009007979', '<p><span style=\"color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp; &nbsp;舞钢市绿色智慧市政及关联项目实施内容包括朱兰片区污水管网工程，景观亮化工程，智慧照明工程，智慧市政综合管理平台及其子系统工程，管线入地工程，尹集镇河道建设工程。项目位于舞钢市城区及尹集镇范围之内。项目选址位置优越、交通便利、环境良好，周围交通、供电、给排水、通讯等基础设施条件十分完善。项目全生命周期为12年，其中，建设期为2年，运营期为10年。估算总投资30000.00万元，其中工程费用25758.75万元，工程建设其它费用1399.19万元，基本预备费1357.90万元，建设期利息1176.00万元，流动资金308.16万元。项目拟采用建设-运营-移交（BOT）的方式进行运作。采用政府与社会资本共同出资的方式建立项目公司（SPV），按照项目资本金组成中的出资比例，政府与社会资本方持股比例为5%：95%。项目估算总投资30000.00万元，其中资本金为6000.00万元（占总投资的20%），资本金的5%（300.00万元）由政府出资，资本金的95%（5700.00万元）由社会资本出资；其余部分24000.00万元（占总投资的80%）由项目公司融资筹措。</span></p>', 'qy1', '已审核', '予以通过', '2018-04-08 00:37:45');
INSERT INTO `t_project` VALUES ('4', '1', '111', 'upload/NoImage.jpg', '2018-04-04', '22', '22', '44', '4', '<p>55</p>', 'qy2', '待审核', '--', '2018-04-18 23:23:35');
