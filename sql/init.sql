CREATE DATABASE `daxiang` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE daxiang;

CREATE TABLE `dict_city` (
  `id` int(10) unsigned NOT NULL DEFAULT '0',
  `name` varchar(30) NOT NULL,
  `pid` int(10) unsigned NOT NULL,
  `state` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `dict_province` (
  `id` int(10) unsigned NOT NULL DEFAULT '0',
  `name` varchar(30) NOT NULL,
  `type` varchar(1) DEFAULT NULL ,
  `state` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_areas` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '1--有效 0--失效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='省份城市表';

CREATE TABLE `t_channels` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `QRCode` varchar(45) DEFAULT NULL COMMENT '微信二维码参数',
  `QRImage` blob COMMENT '微信二维码图片',
  `status` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` date DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL COMMENT '代码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='渠道表';

CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openId` varchar(45) DEFAULT NULL COMMENT '用户微信OPENID',
  `buy` varchar(45) DEFAULT NULL,
  `sell` varchar(45) DEFAULT NULL,
  `takeDate` date DEFAULT NULL,
  `cityId` int(11) DEFAULT NULL,
  `sum` int(11) DEFAULT NULL,
  `changer` varchar(45) DEFAULT NULL,
  `IDType` varchar(45) DEFAULT NULL,
  `IDNO` varchar(45) DEFAULT NULL,
  `referenceID` varchar(45) DEFAULT NULL,
  `moblieNo` varchar(45) DEFAULT NULL,
  `storeId` int(11) DEFAULT NULL,
  `rate` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '1--待审核 2--已受理 3--拒绝 4--取消 5--完成',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户订单表';

CREATE TABLE `t_store_rate` (
  `id` int(11) NOT NULL,
  `buy` varchar(10) DEFAULT NULL COMMENT '买入币种',
  `sell` varchar(10) DEFAULT NULL COMMENT '卖出币种',
  `rate` int(11) DEFAULT NULL COMMENT '汇率',
  `s_id` int(11) DEFAULT NULL COMMENT '所属门店',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='门店汇率表';

CREATE TABLE `t_stores` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `s_id` int(11) DEFAULT NULL COMMENT '所属供应商',
  `name` varchar(45) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `opentime` varchar(45) DEFAULT NULL,
  `max` int(11) DEFAULT NULL COMMENT '最大兑换金额限制',
  `min` int(11) DEFAULT NULL COMMENT '最小兑换金额限制',
  `linkman` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `city` int(11) DEFAULT NULL,
  `province` int(11) DEFAULT NULL,
  `area` int(11) DEFAULT NULL COMMENT '1--市中心 2--机场 3--火车站 4--其它',
  `ahead` int(11) DEFAULT NULL COMMENT '提前预约天数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_suppliers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL COMMENT '简称',
  `status` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `fullname` varchar(100) DEFAULT NULL COMMENT '全称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

