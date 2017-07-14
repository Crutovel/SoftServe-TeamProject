DROP SCHEMA IF EXISTS caesar;
CREATE SCHEMA caesar CHARACTER SET UTF8;
USE caesar;

CREATE USER 'db_admin'@'localhost' IDENTIFIED BY 'rNT5Vn[j(>R2r6.UXcJwtv];`RWewYH`';
GRANT ALL PRIVILEGES ON caesar.* TO 'db_admin'@'localhost';

CREATE USER 'user_ro'@'localhost' IDENTIFIED BY '12345qwerty';
GRANT SELECT ON caesar.* TO 'user_ro'@'localhost';

FLUSH PRIVILEGES ;


