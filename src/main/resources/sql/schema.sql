SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema kwit
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `kwit`;

CREATE SCHEMA IF NOT EXISTS `kwit`
  CHARACTER SET utf8
  COLLATE utf8_general_ci;

USE `kwit`;
-- -----------------------------------------------------
-- Table `kwit`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
  `id`            INT UNSIGNED           NOT NULL  AUTO_INCREMENT,
  `email`         VARCHAR(255)           NOT NULL,
  `password_hash` CHAR(60)               NOT NULL,
  `salary_day`    TINYINT UNSIGNED       NULL      DEFAULT NULL,
  `role`          ENUM ('USER', 'ADMIN') NOT NULL  DEFAULT 'USER',
  `created_at`    DATETIME               NULL      DEFAULT NULL,
  `is_deleted`    BOOL                   NOT NULL  DEFAULT FALSE,
  PRIMARY KEY (`id` ASC)
)
  ENGINE = InnoDB;

CREATE UNIQUE INDEX `IXUQ_user_email`
  ON `user` (`email` ASC);

-- -----------------------------------------------------
-- Table `kwit`.`currency`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `currency`;

CREATE TABLE `currency`
(
  `id`        INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `code`      CHAR(3)          NOT NULL,
  `symbol`    VARCHAR(5)       NOT NULL,
  `is_prefix` BOOL             NOT NULL,
  PRIMARY KEY (`id` ASC)
)
  ENGINE = InnoDB;

CREATE UNIQUE INDEX `IXUQ_currency_code`
  ON `currency` (`code` ASC);

-- -----------------------------------------------------
-- Table `kwit`.`wallet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wallet`;

CREATE TABLE `wallet`
(
  `id`          INTEGER UNSIGNED          NOT NULL AUTO_INCREMENT,
  `user_id`     INTEGER UNSIGNED          NOT NULL,
  `currency_id` INTEGER UNSIGNED          NOT NULL,
  `name`        VARCHAR(100)              NOT NULL,
  `balance`     DECIMAL(19, 4)            NOT NULL,
  `type`        ENUM ('NORMAL', 'SAVING') NOT NULL,
  `is_deleted`  BOOL                      NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`id` ASC),
  CONSTRAINT `FK_wallet_currency`
  FOREIGN KEY (`currency_id`)
  REFERENCES `currency` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_wallet_user`
  FOREIGN KEY (`user_id`)
  REFERENCES `user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `kwit`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `category`;

CREATE TABLE `category`
(
  `id`      INTEGER UNSIGNED         NOT NULL AUTO_INCREMENT,
  `user_id` INTEGER UNSIGNED         NOT NULL,
  `name`    VARCHAR(100)             NOT NULL,
  `type`    ENUM ('INCOME', 'OUTGO') NOT NULL,
  PRIMARY KEY (`id` ASC),
  CONSTRAINT `FK_category_user`
  FOREIGN KEY (`user_id`)
  REFERENCES `user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `kwit`.`remittance`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `remittance`;

CREATE TABLE `remittance`
(
  `id`                 INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id`            INTEGER UNSIGNED NOT NULL,
  `wallet_donor_id`    INTEGER UNSIGNED NOT NULL,
  `wallet_acceptor_id` INTEGER UNSIGNED NOT NULL,
  `donor_sum`          DECIMAL(19, 4)   NOT NULL,
  `conversion`         DECIMAL(10, 4)   NOT NULL DEFAULT 1.0000,
  `date`               DATETIME         NOT NULL,
  PRIMARY KEY (`id` ASC),
  CONSTRAINT `FK_remittance_user`
  FOREIGN KEY (`user_id`)
  REFERENCES `user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_remittance_wallet_acceptor`
  FOREIGN KEY (`wallet_acceptor_id`)
  REFERENCES `wallet` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_remittance_wallet_donor`
  FOREIGN KEY (`wallet_donor_id`)
  REFERENCES `wallet` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
)
  ENGINE = InnoDB;

/*
-- -----------------------------------------------------
-- Table `kwit`.`debt`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `debt` CASCADE;

CREATE TABLE `debt`
(
  `id`          INTEGER UNSIGNED                    NOT NULL AUTO_INCREMENT,
  `user_id`     INTEGER UNSIGNED                    NOT NULL,
  `wallet_id`   INTEGER UNSIGNED                    NOT NULL,
  `description` VARCHAR(300)                        NOT NULL,
  `sum`         INTEGER                             NOT NULL,
  `date`        DATETIME                            NOT NULL,
  `is_income`   BOOL                                NOT NULL,
  `status`      ENUM ('active', 'forgiven', 'paid') NOT NULL DEFAULT 'active',
  PRIMARY KEY (`id` ASC),
  CONSTRAINT `FK_debt_wallet`
  FOREIGN KEY (`wallet_id`)
  REFERENCES `wallet` (`id`)
    ON DELETE NO ACTION
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_debt_user`
  FOREIGN KEY (`user_id`)
  REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT

)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `kwit`.`debt_repayment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `debt_repayment` CASCADE;

CREATE TABLE `debt_repayment`
(
  `id`        INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `debt_id`   INTEGER UNSIGNED NOT NULL,
  `wallet_id` INTEGER UNSIGNED NOT NULL,
  `sum`       INTEGER          NOT NULL,
  `date`      DATETIME         NOT NULL,
  PRIMARY KEY (`id` ASC),
  CONSTRAINT `FK_debt_repayment_debt`
  FOREIGN KEY (`debt_id`)
  REFERENCES `debt` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_debt_repayment_wallet`
  FOREIGN KEY (`id`)
  REFERENCES `wallet` (`id`)
    ON DELETE NO ACTION
    ON UPDATE RESTRICT
)
  ENGINE = InnoDB;
*/
-- -----------------------------------------------------
-- Table `kwit`.`transaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `transaction` CASCADE;
CREATE TABLE `transaction`
(
  `id`          INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id`     INTEGER UNSIGNED NOT NULL,
  `wallet_id`   INTEGER UNSIGNED NOT NULL,
  `category_id` INTEGER UNSIGNED NOT NULL,
  `sum`         DECIMAL(19, 4)   NOT NULL,
  `date`        DATETIME         NOT NULL,
  `note`        VARCHAR(300)     NULL     DEFAULT NULL,
  PRIMARY KEY (`id` ASC),
  CONSTRAINT `FK_transaction_category`
  FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_transaction_wallet`
  FOREIGN KEY (`wallet_id`) REFERENCES `wallet` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `FK_transaction_user`
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
)
  ENGINE = InnoDB;

CREATE INDEX `IX_transaction_date`
  ON `transaction` (`date` ASC);

-- -----------------------------------------------------
-- OAuth2 required tables
-- -----------------------------------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_access_token` (
  `token_id`          VARCHAR(256) DEFAULT NULL,
  `token`             BLOB,
  `authentication_id` VARCHAR(256) DEFAULT NULL,
  `user_name`         VARCHAR(256) DEFAULT NULL,
  `client_id`         VARCHAR(256) DEFAULT NULL,
  `authentication`    BLOB,
  `refresh_token`     VARCHAR(256) DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id`               VARCHAR(256) NOT NULL,
  `resource_ids`            VARCHAR(256)  DEFAULT NULL,
  `client_secret`           VARCHAR(256)  DEFAULT NULL,
  `scope`                   VARCHAR(256)  DEFAULT NULL,
  `authorized_grant_types`  VARCHAR(256)  DEFAULT NULL,
  `web_server_redirect_uri` VARCHAR(256)  DEFAULT NULL,
  `authorities`             VARCHAR(256)  DEFAULT NULL,
  `access_token_validity`   INT(11)       DEFAULT NULL,
  `refresh_token_validity`  INT(11)       DEFAULT NULL,
  `additional_information`  VARCHAR(4096) DEFAULT NULL,
  `autoapprove`             VARCHAR(256)  DEFAULT NULL,
  PRIMARY KEY (`client_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `code`           VARCHAR(256) DEFAULT NULL,
  `authentication` BLOB
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `token_id`       VARCHAR(256) DEFAULT NULL,
  `token`          BLOB,
  `authentication` BLOB
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `oauth_client_details` VALUES
  ('web_client',
    NULL,
    'kwit_web_client_secret',
    'read,write,trust',
    'password,authorization_code,refresh_token,implicit',
    '',
    'ROLE_USER',
    NULL,
    NULL,
    NULL,
    NULL);

DELIMITER $$

DROP TRIGGER IF EXISTS `kwit`.`user_BEFORE_INSERT` $$

CREATE TRIGGER `kwit`.`user_BEFORE_INSERT`
BEFORE INSERT ON `user`
FOR EACH ROW
  BEGIN
    SET NEW.created_at = NOW();
  END$$


DROP TRIGGER IF EXISTS `kwit`.`transaction_BEFORE_INSERT` $$

CREATE TRIGGER `kwit`.`transaction_BEFORE_INSERT`
BEFORE INSERT ON `transaction`
FOR EACH ROW
  BEGIN
    DECLARE is_income BOOL;
    SET is_income = (SELECT `is_income`
                     FROM `category`
                     WHERE `id` = NEW.`category_id`);
    UPDATE `wallet`
    SET `balance` = `balance` + IF(is_income, NEW.`sum`, -NEW.`sum`)
    WHERE `id` = NEW.`wallet_id`;
  END$$


DROP TRIGGER IF EXISTS `kwit`.`transaction_BEFORE_UPDATE` $$

CREATE TRIGGER `kwit`.`transaction_BEFORE_UPDATE`
BEFORE UPDATE ON `transaction`
FOR EACH ROW
  BEGIN
    DECLARE old_type ENUM ('INCOME', 'OUTGO');
    DECLARE new_type ENUM ('INCOME', 'OUTGO');

    SET old_type = (SELECT `type`
                    FROM `category`
                    WHERE `id` = OLD.`category_id`);
    UPDATE `wallet`
    SET `balance` = `balance` + IF(old_type = 'INCOME', -OLD.`sum`, OLD.`sum`)
    WHERE `id` = OLD.`wallet_id`;

    SET new_type = (SELECT `type`
                    FROM `category`
                    WHERE `id` = NEW.`category_id`);
    UPDATE `wallet`
    SET `balance` = `balance` + IF(new_type = 'INCOME', NEW.`sum`, -NEW.`sum`)
    WHERE `id` = NEW.`wallet_id`;
  END$$

DROP TRIGGER IF EXISTS `kwit`.`transaction_BEFORE_DELETE` $$

CREATE TRIGGER `kwit`.`transaction_BEFORE_DELETE`
BEFORE DELETE ON `transaction`
FOR EACH ROW
  BEGIN
    DECLARE type ENUM ('INCOME', 'OUTGO');
    SET type = (SELECT `type`
                FROM `category`
                WHERE `id` = OLD.`category_id`);
    UPDATE `wallet`
    SET `balance` = `balance` + IF(type = 'INCOME', -OLD.`sum`, OLD.`sum`)
    WHERE `id` = OLD.`wallet_id`;
  END $$


DROP PROCEDURE IF EXISTS `kwit`.`createRemittance` $$

CREATE PROCEDURE createRemittance(
  IN _user_id            INTEGER UNSIGNED,
  IN _wallet_donor_id    INTEGER UNSIGNED,
  IN _wallet_acceptor_id INTEGER UNSIGNED,
  IN _donor_sum          DECIMAL(19, 4),
  IN _conversion         DECIMAL(10, 4),
  IN _date               DATETIME
)
  BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
      ROLLBACK;
    END;
    START TRANSACTION;
    INSERT INTO `remittance` (`user_id`, `wallet_donor_id`, `wallet_acceptor_id`, `donor_sum`, `conversion`, `date`)
      VALUE (_user_id, _wallet_donor_id, _wallet_acceptor_id, _donor_sum, _conversion, _date);

    UPDATE `wallet`
    SET `balance` = `balance` - _donor_sum
    WHERE `id` = _wallet_donor_id;

    UPDATE `wallet`
    SET `balance` = `balance` + (_donor_sum * _conversion)
    WHERE `id` = _wallet_donor_id;

    COMMIT;
  END $$

DELIMITER ;

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;