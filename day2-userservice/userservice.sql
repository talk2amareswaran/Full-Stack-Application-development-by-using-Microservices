create database userservice;
use userservice;
create table users (id int primary key auto_increment, name varchar(50), email varchar(50) unique key,  password varchar(255),createddate int(11),  createdby varchar(50), lastupdateddate int(11), lastupdatedby varchar(10));