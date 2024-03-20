CREATE DATABASE IF NOT EXISTS itms_database;

USE itms_database;

CREATE TABLE IF NOT EXISTS role (
  `id` bigint(20) NOT NULL PRIMARY KEY,
  `name` varchar(255) NOT NULL
);

DELETE FROM role;

ALTER TABLE role AUTO_INCREMENT = 0;

INSERT INTO role (`id`, `name`) VALUES
(1, 'Admin'),
(2, 'Manager'),
(3, 'Warehouseman'),
(4, 'Printer');


CREATE TABLE IF NOT EXISTS type (
  `id` bigint NOT NULL PRIMARY KEY,
  `name` varchar(255) NOT NULL
);

DELETE FROM type;

ALTER TABLE type AUTO_INCREMENT = 0;

INSERT INTO type (`id`, `name`) VALUES
(1, 'Import'),
(2, 'Shipment'),
(3, 'Move'),
(4, 'Print'),
(5, 'Order product'),
(6, 'Administration changes');
