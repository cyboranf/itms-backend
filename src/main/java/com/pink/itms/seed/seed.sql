CREATE DATABASE IF NOT EXISTS itms;

USE itms;

CREATE TABLE IF NOT EXISTS role (
  `id` bigint(20) NOT NULL,
  `name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

DELETE FROM role;

ALTER TABLE role AUTO_INCREMENT = 0;

INSERT INTO role (`id`, `name`) VALUES
(1, 'Admin'),
(2, 'Manager'),
(3, 'Warehouseman'),
(4, 'Printer');
