DROP SCHEMA IF EXISTS caesar;
CREATE SCHEMA caesar CHARACTER SET UTF8;
USE caesar;

CREATE USER IF NOT EXISTS 'db_admin'@'localhost' IDENTIFIED BY 'rNT5Vn[j(>R2r6.UXcJwtv];`RWewYH`';
GRANT ALL PRIVILEGES ON caesar.* TO 'db_admin'@'localhost';

CREATE USER IF NOT EXISTS 'user_ro'@'localhost' IDENTIFIED BY '12345qwerty';
GRANT SELECT ON caesar.* TO 'user_ro'@'localhost';

FLUSH PRIVILEGES ;

set @@sql_mode = 'IGNORE_SPACE,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION'
