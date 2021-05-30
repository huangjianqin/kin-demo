DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    `id`       int(11) NOT NULL auto_increment COMMENT 'id',
    `account`  VARCHAR(100) NOT NULL COMMENT '账号',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `role`     tinyint      NOT NULL COMMENT '0-普通用户、1-管理员',
    `name`     VARCHAR(150) NOT NULL COMMENT '用户名',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;