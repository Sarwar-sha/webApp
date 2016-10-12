step 0 : create my.ini file and copy and past into it.

[mysqld] basedir = C:/mysql-5.6.23-winx64 datadir = C:/mysql-5.6.23-winx64/data port = 3306 sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES

step 1 : open cmd and run below command
$mysqld.exe --console      

step 2 : open another cmd and run belew command
$mysql.exe -u root         

step 3 : set a password

$UPDATE mysql.user SET Password = PASSWORD('password') WHERE User = 'root';  

step 4 : create database 

$CREATE DATABASE pdata; 
$CREATE USER 'pdata'@'localhost' IDENTIFIED BY 'password'; 
$GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,ALTER,DROP ON pdata.* TO 'pdata'@'localhost';

step 5 : use created database to create person table

$USE pdata;
$CREATE TABLE person (db_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, id BIGINT NOT NULL, first_name VARCHAR(255) NOT NULL, last_name VARCHAR(255), phone_number VARCHAR(255), email_address VARCHAR(255), start_date Date, end_date Date, login VARCHAR(255), password VARCHAR(255), modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);

# it could be test through the link too : http://localhost:8080/index.html