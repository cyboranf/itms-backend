CREATE DATABASE IF NOT EXISTS itms_database;

USE itms_database;

CREATE TABLE IF NOT EXISTS role (
  id bigint(20) NOT NULL PRIMARY KEY,
  name varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
  id bigint(20) NOT NULL PRIMARY KEY,
  user_name text NOT NULL,
  password text NOT NULL,
  name text NOT NULL,
  last_name text NOT NULL,
  pesel text NOT NULL,
  email text NOT NULL,
  phone_number text NOT NULL
);

CREATE TABLE IF NOT EXISTS user_role (
  user_id bigint(20) NOT NULL,
  role_id bigint(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS type (
  id bigint NOT NULL PRIMARY KEY,
  name varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS task (
  id bigint(20) NOT NULL PRIMARY KEY,
  name text NOT NULL,
  description text NOT NULL,
  state int(11) NOT NULL,
  priority int(11) NOT NULL,
  type_id tinyint(4) NOT NULL,
  creation_date datetime NOT NULL,
  start_date datetime NOT NULL,
  end_date datetime NOT NULL
);

CREATE TABLE IF NOT EXISTS user_task (
  task_id bigint NOT NULL,
  user_id bigint NOT NULL
 );

CREATE TABLE IF NOT EXISTS product (
    id bigint(20) NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL,
    code varchar(255) NOT NULL,
    width double NOT NULL,
    height double NOT NULL,
    length double NOT NULL,
    weight double NOT NULL
);

CREATE TABLE IF NOT EXISTS warehouse (
  id bigint(20) NOT NULL PRIMARY KEY,
  building varchar(255) NOT NULL,
  zone varchar(255) NOT NULL,
  space_id bigint NOT NULL,
  space_height double DEFAULT NULL,
  space_width double DEFAULT NULL,
  space_length double DEFAULT NULL,
  product_id bigint
);

CREATE TABLE IF NOT EXISTS task_product (
  task_id bigint(20) NOT NULL,
  product_id bigint(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS task_warehouse (
  warehouse_id bigint(20) NOT NULL,
  task_id bigint(20) NOT NULL
);


DELETE FROM user_role;
DELETE FROM user_task;
DELETE FROM task_product;
DELETE FROM task_warehouse;
DELETE FROM role;
DELETE FROM users;
DELETE FROM task;
DELETE FROM type;
DELETE FROM warehouse;
DELETE FROM product;


ALTER TABLE user_role AUTO_INCREMENT = 0;
ALTER TABLE role AUTO_INCREMENT = 0;
ALTER TABLE users AUTO_INCREMENT = 0;
ALTER TABLE type AUTO_INCREMENT = 0;
ALTER TABLE task AUTO_INCREMENT = 0;
ALTER TABLE user_task AUTO_INCREMENT = 0;
ALTER TABLE product AUTO_INCREMENT = 0;
ALTER TABLE warehouse AUTO_INCREMENT = 0;
ALTER TABLE task_product AUTO_INCREMENT = 0;
ALTER TABLE task_warehouse AUTO_INCREMENT = 0;


INSERT INTO role (id, name) VALUES
(1, 'Admin'),
(2, 'Manager'),
(3, 'Warehouseman'),
(4, 'Printer');

INSERT INTO users (id, user_name, password, name, last_name, pesel, email, phone_number) VALUES
(1, 'JKowalski', '$2b$12$vDdaQqZUOgs//0WnON2lPuHsnDqpqRZBOHW1rCyjlexzZZPA3k10i', 'Jan', 'Kowalski', 'MTIzNDU2Nzg5MTIz', 'jKowalski@gmail.com', '123123123'),
(2, 'ARogalska', '$2b$12$vDdaQqZUOgs//0WnON2lPuQU4V6OqGsR3IZ57ORPC3aupwf88RYt6', 'Anna', 'Rogalska', 'MDEyMzIxMDQ5MTI=', 'aRogalska@gmail.com', '234234234'),
(3, 'RKaczmarski', '$2b$12$vDdaQqZUOgs//0WnON2lPu84ejDeoUOdJeM0SpjMziEqpnS67ZseC', 'Robert', 'Kaczmarski', 'MDEyMzIxMTIzNDU=', 'rKaczmarksi@gmail.com', '345345345'),
(4, 'SStołeczny', '$2b$12$vDdaQqZUOgs//0WnON2lPuI.PACzHlzVkCRayhXkQg9y0KTTrNaRG', 'Stanisław', 'Stołeczny', 'MDEyMzIxNTQzMjE=', 'sStoleczny@gmail.com', '456456456');

INSERT INTO user_role (user_id, role_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);

INSERT INTO type (id, name) VALUES
(1, 'Import'),
(2, 'Shipment'),
(3, 'Move'),
(4, 'Print'),
(5, 'Order product'),
(6, 'Administrative changes'),
(7, 'others');

INSERT INTO task (id, name, description, state, priority, type_id, creation_date, start_date, end_date) VALUES
(1, 'Prepare products for shipment', 'prepare products attached to this task and deliver them to gate B', 0, 5, 2, '2024-01-14 10:10:00 ', NULL, NULL),
(2, 'Move products', 'move products from between two spaces attached to this task', 0, 4, 3, '2024-01-10 10:09:00', NULL, NULL),
(3, 'Prepare products for shipment', 'prepare products attached to this task and deliver them to gate A', 1, 6, 2, '2024-01-09 10:12:00', '2024-01-10 09:10:00', NULL),
(4, 'Unload transport', 'Unload transport from gate C to spot attached to this task', 2, 10, 1, '2024-01-01 08:00:00', '2024-01-01 08:01:00', '2024-01-01 09:01:00'),
(5, 'Print T-shirts pattern', 'Print flower pattern on products from space attached to this task', 0, 3, 4, '2024-01-14 10:09:00', NULL, NULL),
(6, 'Print T-shirts pattern', 'Print flame pattern on T-shirts from space attached to this task', 1, 5, 4, '2024-01-10 10:08:00', '2024-01-11 08:08:00', NULL),
(7, 'Print T-Shirts pattern', 'Print water pattern on T-shirts from space attached to this task', 2, 7, 4, '2024-01-08 08:08:00','2024-01-09 09:08:00', '2024-01-10 10:09:08'),
(8, 'Remove user', 'Remove user Jkowalski', 0, 10, 6, '2024-02-01 08:08:08', NULL, NULL),
(9, 'Order T-shirts', 'Order T-shirts, size XXL, black color, quantity: 300 from Ebay', 0, 10, 5, '2024-01-08 10:21:00', NULL, NULL);

INSERT INTO user_task (user_id, task_id) VALUES
(3, 3),
(3, 4),
(4, 6),
(4, 7);

INSERT INTO product (id, name, code, height, width, length, weight) VALUES
(1, 'T-shirt_pink_Lx300', 'T-spin1', 50, 90, 70, 11),
(2, 'Hoodie_pink_Sx200', 'Hoopin2', 70, 70, 50, 8),
(3, 'Hoodie_black_XSx300', 'Hoobla3', 50, 60, 70, 12),
(4, 'T-shirt_black_Sx200', 'T-sbla4', 90, 50, 70, 14),
(5, 'Hoodie_blue_XLx200', 'Hooblu5', 60, 50, 70, 9),
(6, 'T-shirt_blue_Lx400', 'T-sblu6', 70, 60, 80, 13),
(7, 'Hoodie_black_XXLx400', 'Hoobla7', 80, 70, 90, 11),
(8, 'T-shirt_blue_Sx400', 'T-sblu8', 50, 90, 50, 5),
(9, 'T-shirt_black_Mx100', 'T-sbla9', 80, 50, 70, 5),
(10, 'Hoodie_black_Sx100', 'Hoobla10', 80, 70, 90, 14),
(11, 'T-shirt_pink_Lx300', 'T-spin11', 80, 60, 90, 14),
(12, 'T-shirt_pink_XXLx100', 'T-spin12', 50, 50, 60, 11),
(13, 'Hoodie_blue_Lx300', 'Hooblu13', 90, 50, 70, 11),
(14, 'T-shirt_blue_XSx100', 'T-sblu14', 70, 80, 90, 14),
(15, 'T-shirt_blue_XXLx200', 'T-sblu15', 80, 80, 70, 13),
(16, 'T-shirt_black_XLx400', 'T-sbla16', 90, 90, 90, 8),
(17, 'Hoodie_blue_XLx400', 'Hooblu17', 90, 80, 50, 12),
(18, 'Hoodie_pink_XSx400', 'Hoopin18', 70, 60, 70, 6),
(19, 'Hoodie_black_XXLx200', 'Hoobla19', 50, 60, 50, 7),
(20, 'T-shirt_blue_Lx100', 'T-sblu20', 70, 60, 50, 5),
(21, 'Hoodie_pink_XSx300', 'Hoopin21', 70, 50, 60, 12),
(22, 'Hoodie_black_Lx100', 'Hoobla22', 60, 80, 60, 5),
(23, 'Hoodie_black_XLx300', 'Hoobla23', 90, 60, 80, 14),
(24, 'T-shirt_pink_Mx100', 'T-spin24', 50, 90, 70, 7),
(25, 'Hoodie_pink_Sx300', 'Hoopin25', 50, 60, 60, 9),
(26, 'Hoodie_black_Sx300', 'Hoobla26', 50, 90, 70, 8),
(27, 'Hoodie_pink_Lx100', 'Hoopin27', 80, 60, 50, 5),
(28, 'Hoodie_black_XXLx200', 'Hoobla28', 60, 80, 70, 8),
(29, 'T-shirt_pink_XSx400', 'T-spin29', 60, 50, 70, 10),
(30, 'Hoodie_blue_XSx300', 'Hooblu30', 50, 60, 60, 7),
(31, 'T-shirt_blue_Mx200', 'T-sblu31', 60, 70, 90, 7),
(32, 'T-shirt_black_Mx300', 'T-sbla32', 90, 60, 50, 6),
(33, 'Hoodie_black_Lx400', 'Hoobla33', 90, 80, 50, 11),
(34, 'Hoodie_black_Mx100', 'Hoobla34', 80, 80, 90, 14),
(35, 'Hoodie_blue_XSx200', 'Hooblu35', 90, 80, 70, 13),
(36, 'Hoodie_blue_Mx100', 'Hooblu36', 70, 50, 70, 5),
(37, 'T-shirt_blue_XSx200', 'T-sblu37', 90, 80, 50, 10),
(38, 'T-shirt_pink_Lx400', 'T-spin38', 70, 70, 50, 11),
(39, 'T-shirt_blue_Mx100', 'T-sblu39', 80, 60, 50, 5),
(40, 'T-shirt_blue_Mx200', 'T-sblu40', 50, 70, 80, 9),
(41, 'T-shirt_blue_XLx400', 'T-sblu41', 90, 90, 90, 10),
(42, 'Hoodie_blue_XLx200', 'Hooblu42', 50, 70, 60, 10),
(43, 'Hoodie_black_XXLx400', 'Hoobla43', 50, 80, 70, 9),
(44, 'Hoodie_blue_Sx100', 'Hooblu44', 50, 50, 70, 12),
(45, 'T-shirt_pink_XXLx400', 'T-spin45', 50, 70, 50, 10),
(46, 'T-shirt_black_Lx200', 'T-sbla46', 70, 50, 70, 12),
(47, 'T-shirt_blue_Lx200', 'T-sblu47', 50, 50, 90, 14),
(48, 'Hoodie_black_XXLx200', 'Hoobla48', 70, 90, 50, 12),
(49, 'Hoodie_black_Mx100', 'Hoobla49', 70, 50, 80, 10),
(50, 'T-shirt_blue_XXLx300', 'T-sblu50', 70, 90, 70, 6);

INSERT INTO warehouse (id, building, zone, space_id, space_height, space_width, space_length, product_id) VALUES
(1, 'A', 'a', 0, 110, 120, 120, 43),
(2, 'A', 'a', 1, 130, 130, 110, 3),
(3, 'A', 'a', 2, 120, 100, 130, 46),
(4, 'A', 'a', 3, 100, 100, 140, 6),
(5, 'A', 'a', 4, 130, 140, 130, 24),
(6, 'A', 'a', 5, 130, 110, 120, 5),
(7, 'A', 'a', 6, 120, 110, 110, 31),
(8, 'A', 'a', 7, 120, 130, 120, 17),
(9, 'A', 'a', 8, 130, 110, 100, 42),
(10, 'A', 'a', 9, 130, 120, 120, 29),
(11, 'A', 'a', 10, 140, 140, 120, 15),
(12, 'A', 'a', 11, 140, 120, 100, 12),
(13, 'A', 'a', 12, 100, 110, 130, 10),
(14, 'A', 'a', 13, 100, 130, 110, 21),
(15, 'A', 'a', 14, 140, 110, 140, 41),
(16, 'A', 'a', 15, 140, 130, 110, 26),
(17, 'A', 'a', 16, 120, 100, 140, 11),
(18, 'A', 'a', 17, 100, 130, 140, 9),
(19, 'A', 'a', 18, 120, 120, 130, 4),
(20, 'A', 'a', 19, 140, 140, 100, 30),
(21, 'A', 'a', 20, 140, 110, 130, 49),
(22, 'A', 'a', 21, 120, 110, 100, 37),
(23, 'A', 'a', 22, 110, 120, 100, 7),
(24, 'A', 'a', 23, 120, 140, 100, 39),
(25, 'A', 'a', 24, 110, 140, 140, 27),
(26, 'A', 'a', 25, 130, 140, 130, 1),
(27, 'A', 'a', 26, 100, 110, 140, 2),
(28, 'A', 'a', 27, 140, 140, 140, 8),
(29, 'A', 'a', 28, 120, 100, 110, 28),
(30, 'A', 'a', 29, 120, 130, 140, 19),
(31, 'A', 'a', 30, 140, 100, 100, 40),
(32, 'A', 'a', 31, 100, 120, 130, 23),
(33, 'A', 'a', 32, 140, 110, 120, 20),
(34, 'A', 'a', 33, 140, 110, 100, 34),
(35, 'A', 'a', 34, 120, 140, 130, 38),
(36, 'A', 'a', 35, 120, 100, 120, 36),
(37, 'A', 'a', 36, 130, 140, 110, 18),
(38, 'A', 'a', 37, 140, 130, 120, 25),
(39, 'A', 'a', 38, 100, 140, 140, 33),
(40, 'A', 'a', 39, 120, 110, 110, 47),
(41, 'A', 'a', 40, 140, 130, 100, 16),
(42, 'A', 'a', 41, 130, 130, 110, 48),
(43, 'A', 'a', 42, 100, 100, 110, 13),
(44, 'A', 'a', 43, 120, 130, 100, 35),
(45, 'A', 'a', 44, 110, 140, 110, 45),
(46, 'A', 'a', 45, 140, 130, 100, 44),
(47, 'A', 'a', 46, 110, 110, 130, 14),
(48, 'A', 'a', 47, 120, 120, 130, 22),
(49, 'A', 'a', 48, 130, 140, 130, 32),
(50, 'A', 'a', 49, 130, 110, 100, NULL),
(51, 'A', 'b', 0, 110, 130, 130, NULL),
(52, 'A', 'b', 1, 130, 110, 100, NULL),
(53, 'A', 'b', 2, 130, 130, 140, NULL),
(54, 'A', 'b', 3, 130, 140, 110, NULL),
(55, 'A', 'b', 4, 120, 100, 140, NULL),
(56, 'A', 'b', 5, 120, 110, 100, NULL),
(57, 'A', 'b', 6, 130, 100, 140, NULL),
(58, 'A', 'b', 7, 130, 110, 120, NULL),
(59, 'A', 'b', 8, 100, 140, 110, NULL),
(60, 'A', 'b', 9, 140, 140, 130, NULL),
(61, 'A', 'b', 10, 140, 110, 110, NULL),
(62, 'A', 'b', 11, 130, 130, 100, NULL),
(63, 'A', 'b', 12, 110, 110, 130, NULL),
(64, 'A', 'b', 13, 140, 100, 120, NULL),
(65, 'A', 'b', 14, 100, 100, 120, NULL),
(66, 'A', 'b', 15, 110, 100, 140, NULL),
(67, 'A', 'b', 16, 110, 100, 120, NULL),
(68, 'A', 'b', 17, 110, 140, 110, NULL),
(69, 'A', 'b', 18, 130, 130, 130, NULL),
(70, 'A', 'b', 19, 140, 110, 100, NULL),
(71, 'A', 'b', 20, 140, 110, 130, NULL),
(72, 'A', 'b', 21, 140, 120, 130, NULL),
(73, 'A', 'b', 22, 130, 110, 110, NULL),
(74, 'A', 'b', 23, 100, 110, 130, NULL),
(75, 'A', 'b', 24, 100, 110, 120, NULL),
(76, 'A', 'b', 25, 100, 110, 140, NULL),
(77, 'A', 'b', 26, 130, 120, 110, NULL),
(78, 'A', 'b', 27, 130, 120, 140, NULL),
(79, 'A', 'b', 28, 100, 120, 130, NULL),
(80, 'A', 'b', 29, 100, 130, 110, NULL),
(81, 'A', 'b', 30, 130, 130, 110, NULL),
(82, 'A', 'b', 31, 140, 100, 120, NULL),
(83, 'A', 'b', 32, 130, 120, 130, NULL),
(84, 'A', 'b', 33, 130, 120, 100, NULL),
(85, 'A', 'b', 34, 120, 130, 100, NULL),
(86, 'A', 'b', 35, 130, 100, 100, NULL),
(87, 'A', 'b', 36, 110, 120, 120, NULL),
(88, 'A', 'b', 37, 110, 130, 140, NULL),
(89, 'A', 'b', 38, 120, 140, 130, NULL),
(90, 'A', 'b', 39, 120, 110, 100, NULL),
(91, 'A', 'b', 40, 130, 100, 120, NULL),
(92, 'A', 'b', 41, 120, 120, 110, NULL),
(93, 'A', 'b', 42, 140, 120, 100, NULL),
(94, 'A', 'b', 43, 130, 120, 100, NULL),
(95, 'A', 'b', 44, 130, 140, 110, NULL),
(96, 'A', 'b', 45, 130, 110, 120, NULL),
(97, 'A', 'b', 46, 130, 120, 120, NULL),
(98, 'A', 'b', 47, 110, 100, 100, NULL),
(99, 'A', 'b', 48, 120, 110, 110, NULL),
(100, 'A', 'b', 49, 110, 120, 130, NULL),
(101, 'A', 'c', 0, 110, 130, 110, NULL),
(102, 'A', 'c', 1, 100, 100, 120, NULL),
(103, 'A', 'c', 2, 120, 120, 140, NULL),
(104, 'A', 'c', 3, 140, 100, 130, NULL),
(105, 'A', 'c', 4, 110, 110, 130, NULL),
(106, 'A', 'c', 5, 130, 110, 140, NULL),
(107, 'A', 'c', 6, 130, 140, 140, NULL),
(108, 'A', 'c', 7, 120, 130, 120, NULL),
(109, 'A', 'c', 8, 140, 140, 110, NULL),
(110, 'A', 'c', 9, 130, 110, 140, NULL),
(111, 'A', 'c', 10, 140, 120, 130, NULL),
(112, 'A', 'c', 11, 120, 130, 140, NULL),
(113, 'A', 'c', 12, 130, 130, 100, NULL),
(114, 'A', 'c', 13, 120, 140, 120, NULL),
(115, 'A', 'c', 14, 130, 110, 140, NULL),
(116, 'A', 'c', 15, 130, 110, 120, NULL),
(117, 'A', 'c', 16, 120, 120, 120, NULL),
(118, 'A', 'c', 17, 120, 110, 140, NULL),
(119, 'A', 'c', 18, 100, 100, 110, NULL),
(120, 'A', 'c', 19, 120, 120, 140, NULL),
(121, 'A', 'c', 20, 140, 110, 140, NULL),
(122, 'A', 'c', 21, 140, 110, 110, NULL),
(123, 'A', 'c', 22, 120, 140, 130, NULL),
(124, 'A', 'c', 23, 140, 140, 100, NULL),
(125, 'A', 'c', 24, 100, 100, 120, NULL),
(126, 'A', 'c', 25, 140, 130, 100, NULL),
(127, 'A', 'c', 26, 100, 110, 110, NULL),
(128, 'A', 'c', 27, 130, 100, 120, NULL),
(129, 'A', 'c', 28, 100, 120, 100, NULL),
(130, 'A', 'c', 29, 140, 140, 140, NULL),
(131, 'A', 'c', 30, 140, 140, 140, NULL),
(132, 'A', 'c', 31, 100, 140, 110, NULL),
(133, 'A', 'c', 32, 100, 110, 140, NULL),
(134, 'A', 'c', 33, 100, 120, 140, NULL),
(135, 'A', 'c', 34, 140, 110, 120, NULL),
(136, 'A', 'c', 35, 110, 130, 110, NULL),
(137, 'A', 'c', 36, 140, 110, 100, NULL),
(138, 'A', 'c', 37, 120, 110, 100, NULL),
(139, 'A', 'c', 38, 130, 110, 130, NULL),
(140, 'A', 'c', 39, 110, 140, 100, NULL),
(141, 'A', 'c', 40, 130, 130, 120, NULL),
(142, 'A', 'c', 41, 120, 100, 130, NULL),
(143, 'A', 'c', 42, 100, 120, 100, NULL),
(144, 'A', 'c', 43, 110, 120, 130, NULL),
(145, 'A', 'c', 44, 120, 100, 100, NULL),
(146, 'A', 'c', 45, 130, 100, 110, NULL),
(147, 'A', 'c', 46, 120, 140, 100, NULL),
(148, 'A', 'c', 47, 110, 140, 140, NULL),
(149, 'A', 'c', 48, 100, 130, 100, NULL),
(150, 'A', 'c', 49, 130, 140, 120, NULL),
(151, 'B', 'a', 0, 110, 140, 140, NULL),
(152, 'B', 'a', 1, 100, 140, 130, NULL),
(153, 'B', 'a', 2, 110, 120, 110, NULL),
(154, 'B', 'a', 3, 110, 110, 120, NULL),
(155, 'B', 'a', 4, 130, 140, 100, NULL),
(156, 'B', 'a', 5, 130, 100, 140, NULL),
(157, 'B', 'a', 6, 140, 140, 130, NULL),
(158, 'B', 'a', 7, 110, 130, 130, NULL),
(159, 'B', 'a', 8, 140, 110, 130, NULL),
(160, 'B', 'a', 9, 140, 110, 100, NULL),
(161, 'B', 'a', 10, 140, 140, 110, NULL),
(162, 'B', 'a', 11, 120, 110, 110, NULL),
(163, 'B', 'a', 12, 110, 130, 100, NULL),
(164, 'B', 'a', 13, 130, 120, 120, NULL),
(165, 'B', 'a', 14, 140, 140, 140, NULL),
(166, 'B', 'a', 15, 100, 130, 110, NULL),
(167, 'B', 'a', 16, 100, 120, 110, NULL),
(168, 'B', 'a', 17, 110, 120, 140, NULL),
(169, 'B', 'a', 18, 110, 120, 130, NULL),
(170, 'B', 'a', 19, 140, 130, 120, NULL),
(171, 'B', 'a', 20, 120, 140, 140, NULL),
(172, 'B', 'a', 21, 140, 120, 100, NULL),
(173, 'B', 'a', 22, 130, 140, 120, NULL),
(174, 'B', 'a', 23, 110, 140, 140, NULL),
(175, 'B', 'a', 24, 120, 130, 140, NULL),
(176, 'B', 'a', 25, 100, 130, 110, NULL),
(177, 'B', 'a', 26, 110, 140, 100, NULL),
(178, 'B', 'a', 27, 130, 140, 110, NULL),
(179, 'B', 'a', 28, 120, 120, 120, NULL),
(180, 'B', 'a', 29, 120, 140, 140, NULL),
(181, 'B', 'a', 30, 110, 120, 140, NULL),
(182, 'B', 'a', 31, 100, 130, 120, NULL),
(183, 'B', 'a', 32, 140, 130, 110, NULL),
(184, 'B', 'a', 33, 110, 110, 130, NULL),
(185, 'B', 'a', 34, 100, 100, 100, NULL),
(186, 'B', 'a', 35, 120, 130, 140, NULL),
(187, 'B', 'a', 36, 130, 140, 100, NULL),
(188, 'B', 'a', 37, 100, 130, 100, NULL),
(189, 'B', 'a', 38, 110, 130, 100, NULL),
(190, 'B', 'a', 39, 110, 100, 100, NULL),
(191, 'B', 'a', 40, 110, 140, 130, NULL),
(192, 'B', 'a', 41, 140, 120, 110, NULL),
(193, 'B', 'a', 42, 100, 110, 140, NULL),
(194, 'B', 'a', 43, 130, 130, 120, NULL),
(195, 'B', 'a', 44, 110, 100, 110, NULL),
(196, 'B', 'a', 45, 140, 120, 100, NULL),
(197, 'B', 'a', 46, 140, 100, 120, NULL),
(198, 'B', 'a', 47, 100, 100, 130, NULL),
(199, 'B', 'a', 48, 130, 120, 100, NULL),
(200, 'B', 'a', 49, 130, 100, 110, NULL),
(201, 'B', 'b', 0, 110, 110, 120, NULL),
(202, 'B', 'b', 1, 110, 120, 120, NULL),
(203, 'B', 'b', 2, 140, 100, 110, NULL),
(204, 'B', 'b', 3, 110, 130, 110, NULL),
(205, 'B', 'b', 4, 140, 130, 130, NULL),
(206, 'B', 'b', 5, 100, 120, 140, NULL),
(207, 'B', 'b', 6, 140, 100, 120, NULL),
(208, 'B', 'b', 7, 130, 100, 110, NULL),
(209, 'B', 'b', 8, 120, 110, 100, NULL),
(210, 'B', 'b', 9, 110, 140, 100, NULL),
(211, 'B', 'b', 10, 100, 100, 140, NULL),
(212, 'B', 'b', 11, 120, 130, 140, NULL),
(213, 'B', 'b', 12, 130, 120, 110, NULL),
(214, 'B', 'b', 13, 140, 130, 100, NULL),
(215, 'B', 'b', 14, 140, 130, 100, NULL),
(216, 'B', 'b', 15, 110, 120, 110, NULL),
(217, 'B', 'b', 16, 130, 140, 140, NULL),
(218, 'B', 'b', 17, 110, 140, 120, NULL),
(219, 'B', 'b', 18, 140, 130, 130, NULL),
(220, 'B', 'b', 19, 110, 100, 110, NULL),
(221, 'B', 'b', 20, 110, 130, 100, NULL),
(222, 'B', 'b', 21, 110, 130, 140, NULL),
(223, 'B', 'b', 22, 100, 130, 130, NULL),
(224, 'B', 'b', 23, 130, 130, 110, NULL),
(225, 'B', 'b', 24, 140, 100, 130, NULL),
(226, 'B', 'b', 25, 110, 140, 100, NULL),
(227, 'B', 'b', 26, 120, 100, 120, NULL),
(228, 'B', 'b', 27, 110, 130, 120, NULL),
(229, 'B', 'b', 28, 130, 140, 110, NULL),
(230, 'B', 'b', 29, 140, 130, 100, NULL),
(231, 'B', 'b', 30, 120, 110, 100, NULL),
(232, 'B', 'b', 31, 140, 130, 110, NULL),
(233, 'B', 'b', 32, 120, 110, 140, NULL),
(234, 'B', 'b', 33, 140, 120, 140, NULL),
(235, 'B', 'b', 34, 120, 110, 100, NULL),
(236, 'B', 'b', 35, 100, 110, 140, NULL),
(237, 'B', 'b', 36, 130, 120, 110, NULL),
(238, 'B', 'b', 37, 140, 140, 100, NULL),
(239, 'B', 'b', 38, 140, 100, 110, NULL),
(240, 'B', 'b', 39, 100, 130, 130, NULL),
(241, 'B', 'b', 40, 130, 120, 130, NULL),
(242, 'B', 'b', 41, 120, 100, 100, NULL),
(243, 'B', 'b', 42, 110, 100, 130, NULL),
(244, 'B', 'b', 43, 120, 120, 130, NULL),
(245, 'B', 'b', 44, 110, 100, 120, NULL),
(246, 'B', 'b', 45, 100, 110, 110, NULL),
(247, 'B', 'b', 46, 140, 110, 140, NULL),
(248, 'B', 'b', 47, 100, 110, 120, NULL),
(249, 'B', 'b', 48, 130, 140, 100, NULL),
(250, 'B', 'b', 49, 140, 100, 110, NULL),
(251, 'B', 'c', 0, 100, 130, 140, NULL),
(252, 'B', 'c', 1, 100, 120, 140, NULL),
(253, 'B', 'c', 2, 140, 100, 100, NULL),
(254, 'B', 'c', 3, 130, 110, 120, NULL),
(255, 'B', 'c', 4, 130, 110, 130, NULL),
(256, 'B', 'c', 5, 110, 130, 110, NULL),
(257, 'B', 'c', 6, 110, 120, 130, NULL),
(258, 'B', 'c', 7, 100, 130, 120, NULL),
(259, 'B', 'c', 8, 120, 130, 110, NULL),
(260, 'B', 'c', 9, 140, 130, 130, NULL),
(261, 'B', 'c', 10, 100, 140, 100, NULL),
(262, 'B', 'c', 11, 130, 110, 120, NULL),
(263, 'B', 'c', 12, 140, 140, 140, NULL),
(264, 'B', 'c', 13, 100, 140, 140, NULL),
(265, 'B', 'c', 14, 100, 120, 130, NULL),
(266, 'B', 'c', 15, 100, 130, 100, NULL),
(267, 'B', 'c', 16, 100, 140, 110, NULL),
(268, 'B', 'c', 17, 100, 130, 110, NULL),
(269, 'B', 'c', 18, 120, 130, 130, NULL),
(270, 'B', 'c', 19, 130, 100, 110, NULL),
(271, 'B', 'c', 20, 130, 110, 130, NULL),
(272, 'B', 'c', 21, 100, 140, 130, NULL),
(273, 'B', 'c', 22, 120, 100, 110, NULL),
(274, 'B', 'c', 23, 120, 110, 140, NULL),
(275, 'B', 'c', 24, 130, 100, 120, NULL),
(276, 'B', 'c', 25, 140, 120, 110, NULL),
(277, 'B', 'c', 26, 110, 120, 130, NULL),
(278, 'B', 'c', 27, 120, 100, 140, NULL),
(279, 'B', 'c', 28, 110, 110, 100, NULL),
(280, 'B', 'c', 29, 140, 140, 120, NULL),
(281, 'B', 'c', 30, 140, 110, 120, NULL),
(282, 'B', 'c', 31, 110, 100, 130, NULL),
(283, 'B', 'c', 32, 120, 100, 130, NULL),
(284, 'B', 'c', 33, 110, 100, 100, NULL),
(285, 'B', 'c', 34, 130, 110, 110, NULL),
(286, 'B', 'c', 35, 130, 120, 130, NULL),
(287, 'B', 'c', 36, 140, 120, 140, NULL),
(288, 'B', 'c', 37, 130, 140, 100, NULL),
(289, 'B', 'c', 38, 130, 110, 130, NULL),
(290, 'B', 'c', 39, 100, 130, 140, NULL),
(291, 'B', 'c', 40, 140, 100, 110, NULL),
(292, 'B', 'c', 41, 140, 100, 140, NULL),
(293, 'B', 'c', 42, 130, 120, 110, NULL),
(294, 'B', 'c', 43, 110, 130, 130, NULL),
(295, 'B', 'c', 44, 100, 130, 120, NULL),
(296, 'B', 'c', 45, 140, 130, 110, NULL),
(297, 'B', 'c', 46, 140, 110, 110, NULL),
(298, 'B', 'c', 47, 130, 120, 100, NULL),
(299, 'B', 'c', 48, 100, 130, 130, NULL),
(300, 'B', 'c', 49, 100, 140, 100, NULL);

INSERT INTO task_product (task_id, product_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(3, 5),
(3, 6),
(5, 7),
(6, 8),
(7, 8);

INSERT INTO task_warehouse (task_id, warehouse_id) VALUES
(2, 1),
(2, 200),
(3, 201);
