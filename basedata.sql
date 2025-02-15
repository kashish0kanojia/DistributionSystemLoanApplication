# SQL configs
SET SQL_MODE ='IGNORE_SPACE,ERROR_FOR_DIVISION_BY_ZERO';

# create database and use it
CREATE DATABASE IF NOT EXISTS policyBazzar;
USE policyBazzar;

# create the category table
CREATE TABLE IF NOT EXISTS CATEGORY(
category_id int unique key not null auto_increment primary key,
name        varchar(255) null
);

# insert default categories
INSERT INTO CATEGORY(name) VALUES
('Personal Loan'),
('Home Loan'),
('Education Loan'),
('Motor Loan');

# create the customer table
CREATE TABLE IF NOT EXISTS CUSTOMER(
id       int unique key not null auto_increment primary key,
gender  varchar(1) null,
email    varchar(255) null,
password varchar(255) null,
role     varchar(255) null,
username varchar(255) null,
age varchar(255) null,
salary varchar(255) null,
creditScore varchar(255) null,
UNIQUE (username)
);

-- # insert default customers
 INSERT INTO CUSTOMER(address, email, password, role, username) VALUES
                                                                   ('123, Albany Street', 'admin@yahoo.ie', '123', 'ROLE_ADMIN', 'admin');
# create the product table
CREATE TABLE IF NOT EXISTS PRODUCT(
product_id  int unique key not null auto_increment primary key,
description varchar(1023) null,
image       varchar(255) null,
name        varchar(255) null,
creditScores_weight      int null,
gender_weight      int null,
salary_weight int null,
category_id int null,
customer_id int null
);

# insert default products
-- INSERT INTO PRODUCT(description, image, name, price, quantity, weight, category_id) VALUES
--                                                                                         ('Fresh and juicy', 'https://freepngimg.com/save/9557-apple-fruit-transparent/744x744', 'Apple', 3, 40, 76, 1),
--                                                                                         ('Woops! There goes the eggs...', 'https://www.nicepng.com/png/full/813-8132637_poiata-bunicii-cracked-egg.png', 'Cracked Eggs', 1, 90, 43, 9);

# create indexes
CREATE INDEX FK7u438kvwr308xcwr4wbx36uiw
    ON PRODUCT (category_id);

CREATE INDEX FKt23apo8r9s2hse1dkt95ig0w5
    ON PRODUCT (customer_id);