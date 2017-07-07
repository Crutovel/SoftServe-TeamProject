DROP SCHEMA IF EXISTS CAESAR;
CREATE SCHEMA CAESAR CHARACTER SET UTF8;
USE CAESAR;


CREATE USER IF NOT EXISTS 'admin'@'localhost' IDENTIFIED BY '1234%';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'localhost';

CREATE USER IF NOT EXISTS 'db_admin'@'localhost' IDENTIFIED BY '1234%';
GRANT ALL PRIVILEGES ON CAESAR.* TO 'admin'@'localhost';

CREATE USER IF NOT EXISTS 'user_ro'@'localhost' IDENTIFIED BY '1234%';
GRANT SELECT ON CAESAR.* TO 'user_ro'@'localhost';

FLUSH PRIVILEGES ;
