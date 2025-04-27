CREATE TABLE `account` (
                           `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增生成',
                           `account_id` VARCHAR(36) NOT NULL COMMENT '账户ID，业务主键',
                           `account_name` VARCHAR(255) NOT NULL COMMENT '账户名称',
                           `balance` DECIMAL(18,2) NOT NULL COMMENT '当前余额',
                           `created` TIMESTAMP COMMENT '创建时间',
                           `modified` TIMESTAMP COMMENT '更新时间',
                           `is_deleted` SMALLINT DEFAULT 0 COMMENT '删除标记 1',
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `UK_account_id` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `transaction` (
                               `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增生成',
                               `transaction_id` VARCHAR(36) NOT NULL COMMENT '交易ID，业务主键',
                               `source_account_id` VARCHAR(36) NOT NULL COMMENT '源账户ID',
                               `target_account_id` VARCHAR(36) NOT NULL COMMENT '目标账户ID',
                               `amount` DECIMAL(18,2) NOT NULL COMMENT '交易金额',
                               `status` INT DEFAULT 0 COMMENT '状态: 0已提交,1成功,2处理中,3失败',
                               `failed_reason` VARCHAR(255) COMMENT '如果交易失败,失败原因;成功则为空',
                               `created` TIMESTAMP COMMENT '创建时间',
                               `modified` TIMESTAMP COMMENT '更新时间',
                               `is_deleted` SMALLINT DEFAULT 0 COMMENT '删除标记 1',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `UK_transaction_id` (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `account_record` (
                                  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增生成',
                                  `record_id` VARCHAR(36) NOT NULL COMMENT '记录ID，业务主键',
                                  `account_id` VARCHAR(36) NOT NULL COMMENT '账户ID',
                                  `balance` DECIMAL(18,2) NOT NULL COMMENT '快照时的余额',
                                  `version` BIGINT DEFAULT 0 COMMENT '版本号',
                                  `created` TIMESTAMP COMMENT '创建时间',
                                  `is_deleted` SMALLINT DEFAULT 0 COMMENT '删除标记 1',
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `UK_record_id` (`record_id`),
                                  UNIQUE KEY `UK_account_id_version` (`account_id`,`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;