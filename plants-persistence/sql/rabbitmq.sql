CREATE TABLE `paymnt_proc_msg` (
  `idPAYMNT_PROC_MSG` int(11) NOT NULL,
  `PAYMNT_MSG` varchar(45) DEFAULT NULL COMMENT 'Payment Process Message String to be persisted',
  PRIMARY KEY (`idPAYMNT_PROC_MSG`)
);


INSERT INTO `app`.`idgenerator`
(`IDNAME`,
`IDVALUE`)
VALUES
(
'PAYMNTPROCMSG',	'6');