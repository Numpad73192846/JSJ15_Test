-- Active: 1769461880033@@127.0.0.1@3306@aloha


SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS product;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE product (
    id          VARCHAR(36)     PRIMARY KEY,
    name        VARCHAR(100)    NOT NULL,
    price       INT             NOT NULL,
    stock       INT             NOT NULL,
    created_at  DATETIME
);