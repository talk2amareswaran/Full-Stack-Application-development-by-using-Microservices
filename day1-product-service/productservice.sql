create database productservice;
use productservice;
create table products (id int primary key auto_increment, name varchar(50), sku varchar(50) unique key, price float, qty int, createddate int(11), createdby varchar(50), lastupdateddate int(11), lastupdatedby varchar(50));