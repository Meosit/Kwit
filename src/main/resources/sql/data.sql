USE `kwit`;

SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE user;
ALTER TABLE user
  AUTO_INCREMENT = 1;
INSERT INTO user (id, email, password_hash, salary_day) VALUES
  (1, '$young0@fotki.com', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 14),
  (2, '$lee1@toplist.cz', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 15),
  (3, '$hernandez2@globo.com', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 16),
  (4, '$chapman3@chicagotribune.com', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 14),
  (5, '$stone4@unesco.org', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 10),
  (6, '$gibson5@51.la', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 1),
  (7, '$patterson6@odnoklassniki.ru', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 17),
  (8, '$diaz7@ocn.ne.jp', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 18),
  (9, '$sanders8@freewebs.com', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 28),
  (10, '$murray9@squidoo.com', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 6);

TRUNCATE TABLE currency;
ALTER TABLE currency
  AUTO_INCREMENT = 1;
INSERT INTO currency (id, code, symbol, is_prefix) VALUES
  (1, 'BYN', 'Br', FALSE),
  (2, 'RUB', '₽', TRUE),
  (3, 'EUR', '€', TRUE),
  (4, 'USD', '$', TRUE),
  (5, 'PLN', 'zł', FALSE);

TRUNCATE TABLE wallet;
ALTER TABLE wallet
  AUTO_INCREMENT = 1;
INSERT INTO wallet (id, user_id, currency_id, name, balance, is_saving) VALUES
  (1, 5, 4, 'Hangover Relief', 173, FALSE),
  (2, 4, 4, 'Ibuprofen', 817, FALSE),
  (3, 3, 2, 'Softlips Berry', 696, FALSE),
  (4, 7, 5, 'Shopko Antibacterial Foaming Hand Sanitizer', 901, TRUE),
  (5, 5, 1, 'GFA First Aid', 871, TRUE),
  (6, 8, 2, 'Carbon Dioxide-Oxygen-Nitrogen Mixture', 236, FALSE),
  (7, 1, 4, 'Piroxicam', 465, TRUE),
  (8, 2, 1, 'Orajel for Cold Sores', 42, FALSE),
  (9, 8, 3, 'Moisturizing Antibacterial', 369, FALSE),
  (10, 8, 3, 'Degree', 856, TRUE),
  (11, 7, 5, 'Acarbose', 784, TRUE),
  (12, 5, 5, 'TEMAZEPAM', 596, FALSE),
  (13, 1, 1, 'Prochieve', 351, FALSE),
  (14, 9, 3, 'Oxymorphone hydrochloride', 874, TRUE),
  (15, 8, 5, 'Piperacillin and Tazobactam', 787, FALSE);


TRUNCATE TABLE category;
ALTER TABLE category
  AUTO_INCREMENT = 1;
INSERT INTO category (id, user_id, name, type) VALUES
  (1, 8, 'Babblestorm', 'OUTGO'),
  (2, 5, 'Avavee', 'INCOME'),
  (3, 4, 'Agivu', 'OUTGO'),
  (4, 3, 'Fivechat', 'OUTGO'),
  (5, 5, 'Rhynoodle', 'INCOME'),
  (6, 2, 'Jaloo', 'OUTGO'),
  (7, 4, 'Innojam', 'OUTGO'),
  (8, 6, 'Realblab', 'INCOME'),
  (9, 5, 'Voonyx', 'INCOME'),
  (10, 6, 'Yombu', 'OUTGO'),
  (11, 3, 'Aimbo', 'OUTGO'),
  (12, 7, 'Tavu', 'OUTGO'),
  (13, 8, 'Omba', 'INCOME'),
  (14, 1, 'Mycat', 'INCOME'),
  (15, 1, 'Tanoodle', 'INCOME');

TRUNCATE TABLE transaction;
ALTER TABLE transaction
  AUTO_INCREMENT = 1;
INSERT INTO transaction (id, user_id, wallet_id, category_id, sum, date) VALUES
  (1, 7, 6, 13, 20, '2017-02-08 07:56:41'),
  (2, 8, 14, 2, 93, '2017-02-21 11:00:51'),
  (3, 9, 7, 2, 35, '2017-02-27 14:57:23'),
  (4, 5, 10, 6, 89, '2017-02-10 21:40:56'),
  (5, 8, 13, 6, 47, '2017-03-27 07:34:50'),
  (6, 7, 5, 14, 72, '2017-03-26 07:03:15'),
  (7, 5, 11, 2, 74, '2017-02-20 14:24:46'),
  (8, 10, 2, 7, 24, '2017-02-09 01:48:34'),
  (9, 3, 2, 14, 74, '2017-03-21 11:48:39'),
  (10, 3, 3, 9, 72, '2017-01-21 20:03:38'),
  (11, 5, 11, 5, 91, '2017-03-09 16:19:13'),
  (12, 6, 6, 10, 38, '2017-01-10 14:29:56'),
  (13, 8, 5, 2, 21, '2017-02-06 07:45:59'),
  (14, 1, 5, 2, 61, '2017-01-14 23:57:04'),
  (15, 3, 15, 15, 4, '2017-04-07 05:13:55');

SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
