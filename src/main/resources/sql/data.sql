USE `kwit`;

SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE user;
ALTER TABLE user
  AUTO_INCREMENT = 1;
INSERT INTO user (email, password_hash, salary_day, role) VALUES
  ('mksn13@gmail.com', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 24, 'ADMIN'),
  ('young0@fotki.com', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 14, 'USER'),
  ('lee1@toplist.cz', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 15, 'USER'),
  ('hernandez2@globo.com', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 16, 'USER'),
  ('chapman3@chicagotribune.com', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 14, 'USER'),
  ('stone4@unesco.org', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 10, 'USER'),
  ('gibson5@51.la', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 1, 'USER'),
  ('patterson6@odnoklassniki.ru', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 17, 'USER'),
  ('diaz7@ocn.ne.jp', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 18, 'USER'),
  ('sanders8@freewebs.com', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 28, 'USER'),
  ('murray9@squidoo.com', '$2a$12$5EeGW2rSJVdrg4g8bp9g0exNjaGsjHNAq0BkR6UTU5baFr8Y3i522', 6, 'USER');

TRUNCATE TABLE currency;
ALTER TABLE currency
  AUTO_INCREMENT = 1;
INSERT INTO currency (code, symbol, is_prefix) VALUES
  ('BYN', 'Br', FALSE),
  ('RUB', '₽', TRUE),
  ('EUR', '€', TRUE),
  ('USD', '$', TRUE),
  ('PLN', 'zł', FALSE);

TRUNCATE TABLE wallet;
ALTER TABLE wallet
  AUTO_INCREMENT = 1;
INSERT INTO wallet (user_id, currency_id, name, balance, type) VALUES
  (1, 4, 'Hangover Relief', 173, 'NORMAL'),
  (1, 4, 'Ibuprofen', 817, 'NORMAL'),
  (1, 2, 'Softlips Berry', 696, 'NORMAL'),
  (1, 5, 'Shopko Antibacterial Foaming Hand Sanitizer', 901, 'SAVING'),
  (1, 1, 'GFA First Aid', 871, 'SAVING'),
  (1, 2, 'Carbon Dioxide-Oxygen-Nitrogen Mixture', 236, 'NORMAL'),
  (1, 4, 'Piroxicam', 465, 'SAVING'),
  (1, 1, 'Orajel for Cold Sores', 42, 'NORMAL'),
  (1, 3, 'Moisturizing Antibacterial', 369, 'NORMAL'),
  (1, 3, 'Degree', 856, 'SAVING'),
  (1, 5, 'Acarbose', 784, 'SAVING'),
  (1, 5, 'TEMAZEPAM', 596, 'NORMAL'),
  (1, 1, 'Prochieve', 351, 'NORMAL'),
  (1, 3, 'Oxymorphone hydrochloride', 874, 'SAVING'),
  (1, 5, 'Piperacillin and Tazobactam', 787, 'NORMAL');


TRUNCATE TABLE category;
ALTER TABLE category
  AUTO_INCREMENT = 1;
INSERT INTO category (user_id, name, type) VALUES
  (1, 'Babblestorm', 'OUTGO'),
  (1, 'Avavee', 'INCOME'),
  (1, 'Agivu', 'OUTGO'),
  (1, 'Fivechat', 'OUTGO'),
  (1, 'Rhynoodle', 'INCOME'),
  (1, 'Jaloo', 'OUTGO'),
  (1, 'Innojam', 'OUTGO'),
  (1, 'Realblab', 'INCOME'),
  (1, 'Voonyx', 'INCOME'),
  (1, 'Yombu', 'OUTGO'),
  (1, 'Aimbo', 'OUTGO'),
  (1, 'Tavu', 'OUTGO'),
  (1, 'Omba', 'INCOME'),
  (1, 'Mycat', 'INCOME'),
  (1, 'Tanoodle', 'INCOME');

TRUNCATE TABLE transaction;
ALTER TABLE transaction
  AUTO_INCREMENT = 1;
INSERT INTO transaction (user_id, wallet_id, category_id, sum, date) VALUES
  (1, 6, 13, 20, '2017-02-08 07:56:41'),
  (1, 14, 2, 93, '2017-02-21 11:00:51'),
  (1, 7, 2, 35, '2017-02-27 14:57:23'),
  (1, 10, 6, 89, '2017-02-10 21:40:56'),
  (1, 13, 6, 47, '2017-03-27 07:34:50'),
  (1, 5, 14, 72, '2017-03-26 07:03:15'),
  (1, 11, 2, 74, '2017-02-20 14:24:46'),
  (1, 2, 7, 24, '2017-02-09 01:48:34'),
  (1, 2, 14, 74, '2017-03-21 11:48:39'),
  (1, 3, 9, 72, '2017-01-21 20:03:38'),
  (1, 11, 5, 91, '2017-03-09 16:19:13'),
  (1, 6, 10, 38, '2017-01-10 14:29:56'),
  (1, 5, 2, 21, '2017-02-06 07:45:59'),
  (1, 5, 2, 61, '2017-01-14 23:57:04'),
  (1, 15, 15, 4, '2017-04-07 05:13:55');

SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
