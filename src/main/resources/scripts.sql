CREATE TABLE `T_NTFN_CHN_TYPE` (
  `NCHNTYPE_ID` varchar(35) NOT NULL COMMENT 'Record ID',
  `NCHNTYPE_SEQ` int(11) NOT NULL COMMENT 'Sequence of the notific channel type',
  `NCHNTYPE_DESC` varchar(1024) NOT NULL COMMENT 'Description of the notific channel type',
  PRIMARY KEY (`NCHNTYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table for Notif channel type';


CREATE TABLE `T_NTFN_CNT_TYPE` (
  `NCNTTYPE_ID` varchar(35) NOT NULL COMMENT 'Record ID',
  `NCNTTYPE_SEQ` int(11) NOT NULL COMMENT 'Sequence of the notif content type',
  `NCNTTYPE_DESC` varchar(1024) NOT NULL COMMENT 'Description of the notif content type',
  PRIMARY KEY (`NCNTTYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table for notif content type';


CREATE TABLE `T_NTFN_DEVICE` (
  `NDEV_ID` varchar(35) NOT NULL COMMENT 'Record ID',
  `NDEV_CHANNEL_TYPE` varchar(35) NOT NULL COMMENT 'Channel type for the device',
  `NDEV_CONFIG` varchar(8192) NOT NULL COMMENT 'XML configuration of the device e.g. SMTP settings',
  `NDEV_SEQ` int(11) NOT NULL COMMENT 'Sequence of the notif device, for display purpose',
  `NDEV_DESC` varchar(1024) NOT NULL COMMENT 'Description of the notif device',
  PRIMARY KEY (`NDEV_ID`),
  KEY `FK_NDEV_CHANNEL_TYPE` (`NDEV_CHANNEL_TYPE`),
  CONSTRAINT `FK_NDEV_CHANNEL_TYPE` FOREIGN KEY (`NDEV_CHANNEL_TYPE`) REFERENCES `T_NTFN_CHN_TYPE` (`NCHNTYPE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table for notif device';


CREATE TABLE `T_NTFN_TEMPLATE` (
  `NTPL_ID` varchar(35) NOT NULL COMMENT 'Record ID',
  `NTPL_CHANNEL_TYPE` varchar(35) NOT NULL COMMENT 'Channel type for the template',
  `NTPL_CONTENT_TYPE` varchar(35) NOT NULL COMMENT 'Content type for the template',
  `NTPL_SUBJECT` varchar(4096) DEFAULT NULL COMMENT 'Optional subject',
  `NTPL_TEMPALTE` text COMMENT 'Template content',
  `NTPL_SEQ` int(11) NOT NULL COMMENT 'Sequence of the template.',
  `NTPL_DESC` varchar(1024) NOT NULL COMMENT 'Description Of The Template Type',
  PRIMARY KEY (`NTPL_ID`),
  KEY `FK_NTPL_CHANNEL_TYPE` (`NTPL_CHANNEL_TYPE`),
  KEY `FK_NTPL_CONTENT_TYPE` (`NTPL_CONTENT_TYPE`),
  CONSTRAINT `FK_NTPL_CHANNEL_TYPE` FOREIGN KEY (`NTPL_CHANNEL_TYPE`) REFERENCES `T_NTFN_CHN_TYPE` (`NCHNTYPE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_NTPL_CONTENT_TYPE` FOREIGN KEY (`NTPL_CONTENT_TYPE`) REFERENCES `T_NTFN_CNT_TYPE` (`NCNTTYPE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table for notif templates';


---- Dump Data ---
 
INSERT INTO `T_NTFN_CHN_TYPE` (`NCHNTYPE_ID`,`NCHNTYPE_SEQ`,`NCHNTYPE_DESC`) VALUES ('CHN_TYPE_EMAIL',1,'Email Type');

INSERT INTO `T_NTFN_CNT_TYPE` (`NCNTTYPE_ID`,`NCNTTYPE_SEQ`,`NCNTTYPE_DESC`) VALUES ('CNT_TYPE_HTML',1,'Html Type');
 
INSERT INTO `T_NTFN_DEVICE` (`NDEV_ID`,`NDEV_CHANNEL_TYPE`,`NDEV_CONFIG`,`NDEV_SEQ`,`NDEV_DESC`) VALUES ('MP_DEV_EMAIL','CHN_TYPE_EMAIL','{\"PORT\":\"587\",\"PASSWORD\":\"test123\",\"HOST\":\"smtp.gmail.com\",\"USERNAME\":\"admin@gmail.com\",\"SSL\":\"true\", \"TLS_REQ\":\"true\", \"TLS\":\"true\"}',1,'Email Device');

INSERT INTO `T_NTFN_TEMPLATE` (`NTPL_ID`,`NTPL_CHANNEL_TYPE`,`NTPL_CONTENT_TYPE`,`NTPL_SUBJECT`,`NTPL_TEMPALTE`,`NTPL_SEQ`,`NTPL_DESC`) VALUES ('TEMPLATE1','CHN_TYPE_EMAIL','CNT_TYPE_HTML','Welcome to Email Notification','UPDATE `notification`.`t_ntfn_template` SET `NTPL_TEMPALTE`=\'\\r  \\r \\r <html>\\r 	<head> \\r 	<meta http-equiv=Content-Type content=\\\"text/html; charset=windows-1252\\\">\\r 	<meta name=Generator content=\\\"Microsoft Word 15 (filtered)\\\">\\r    <style>\\r    \\r   </style>\\r    </head>\\r 	 <body lang=EN-SG link=\\\"#0563C1\\\" vlink=\\\"#954F72\\\">\\r 	<div class=WordSection1>\\r 	<p class=MsoNormal><span style=\\\"font-family:\\\"Arial\\\",sans-serif\\\">Dear :userName, </span></p>\\r 			   <p class=MsoNormal><span style=\\\"font-family:\\\"Arial\\\",sans-serif\\\">&nbsp;</span></p>\\r 	<p class=MsoNormal><span style=\\\"font-family:\\\"Arial\\\",sans-serif\\\">Welcome to Email Notification by Java. \\r \\r <p class=MsoNormal><span style=\\\"font-family:\\\"Arial\\\",sans-serif\\\">&nbsp;</span></p>\\r <p class=MsoNormal><span style=\\\"font-family:\\\"Arial\\\",sans-serif\\\"> \\r Best regards,\\r <br>\\r <b>Admin</b>\\r </span>\\r </p>	 	  \\r </div>\\r </div>\\r </body>\\r </html> \' WHERE `NTPL_ID`=\'TEMPLATE1\';',1,'Basic Template');


