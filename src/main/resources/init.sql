DROP SCHEMA IF EXISTS caesar;
CREATE SCHEMA caesar CHARACTER SET UTF8;
USE caesar;

CREATE USER IF NOT EXISTS 'db_admin'@'localhost' IDENTIFIED BY 'rNT5Vn[j(>R2r6.UXcJwtv];`RWewYH`';
GRANT ALL PRIVILEGES ON caesar.* TO 'db_admin'@'localhost';

CREATE USER IF NOT EXISTS 'user_ro'@'localhost' IDENTIFIED BY '12345qwerty';
GRANT SELECT ON caesar.* TO 'user_ro'@'localhost';

FLUSH PRIVILEGES ;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Table `role_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `role_category` ;
CREATE TABLE `role_category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `role` ;
CREATE TABLE `role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_role_category1_idx` (`category_id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  CONSTRAINT `fk_role_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `role_category` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `location` ;
CREATE TABLE `location` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `coordinator_id` INT,
  PRIMARY KEY (`id`),
  INDEX `fk_location_user1_idx` (`coordinator_id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  CONSTRAINT `fk_location_user1`
    FOREIGN KEY (`coordinator_id`)
    REFERENCES `user` (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;
CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `role_id` INT NOT NULL,
  `nick_name` VARCHAR(45) NOT NULL,
  `password_hash_code` VARCHAR(45) NOT NULL,
  `image` LONGBLOB NULL,
  `location_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_role1_idx` (`role_id` ASC),
  UNIQUE INDEX `nick_name_UNIQUE` (`nick_name` ASC),
  INDEX `fk_user_location1_idx` (`location_id` ASC),
  CONSTRAINT `fk_user_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `role` (`id`),
  CONSTRAINT `fk_user_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `location` (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `status_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `status_category` ;
CREATE TABLE `status_category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `status` ;
CREATE TABLE `status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_status_status_category1_idx` (`category_id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  CONSTRAINT `fk_status_status_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `status_category` (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `strategy`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `strategy` ;
CREATE TABLE `strategy` (
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `specialization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `specialization`;
CREATE TABLE `specialization` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `strategy_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_specialization_strategy_idx` (`strategy_id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  CONSTRAINT `fk_specialization_strategy`
    FOREIGN KEY (`strategy_id`)
    REFERENCES `strategy` (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `education_group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educational_group`;
CREATE TABLE `educational_group` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `location_id` INT NOT NULL,
  `start_date` DATE NOT NULL,
  `finish_date` DATE NOT NULL,
  `status_id` INT NOT NULL,
  `specialization_id` INT NOT NULL,
  `budget_owner_id` INT NOT NULL,
  `is_deleted` bit(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_group_status1_idx` (`status_id` ASC),
  INDEX `fk_group_location1_idx` (`location_id` ASC),
  INDEX `fk_group_specialization1_idx` (`specialization_id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  CONSTRAINT `fk_edu_group_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `status` (`id`),
  CONSTRAINT `fk_edu_group_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `location` (`id`),
  CONSTRAINT `fk_edu_group_specialization1`
    FOREIGN KEY (`specialization_id`)
    REFERENCES `specialization` (`id`),
  CONSTRAINT `fk_edu_group_budget_owner1`
    FOREIGN KEY (`budget_owner_id`)
    REFERENCES `budget_owner` (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `group_teacher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `group_teacher` ;
CREATE TABLE `group_teacher` (
  `teacher_id` INT NOT NULL,
  `group_id` INT NOT NULL,
  INDEX `fk_group_teacher_educational_group1_idx` (`group_id` ASC),
  INDEX `fk_group_teacher_user1_idx` (`teacher_id` ASC),
  CONSTRAINT `fk_group_teacher_educational_group1`
    FOREIGN KEY (`group_id`)
    REFERENCES `educational_group` (`id`),
  CONSTRAINT `fk_group_teacher_user1`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `user` (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `expert`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `expert` ;
CREATE TABLE `expert` (
  `edu_group_id` INT NOT NULL,
  `expert_name` VARCHAR(100) NOT NULL,
  INDEX `fk_expert_education_group1_idx` (`edu_group_id` ASC),
  CONSTRAINT `fk_expert_education_group1`
    FOREIGN KEY (`edu_group_id`)
    REFERENCES `educational_group` (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `budget_owner`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `budget_owner`;
CREATE TABLE `budget_owner` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `event_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `event_type` ;
CREATE TABLE `event_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `is_key_date` BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `room`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `room` ;
CREATE TABLE `room` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(45) NOT NULL,
  `location_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_room_location1_idx` (`location_id` ASC),
  CONSTRAINT `fk_room_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `location` (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ceasar`.`event`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `event` ;
CREATE TABLE `event` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `datetime` TIMESTAMP NOT NULL,
  `duration` INT NOT NULL,
  `group_id` INT NOT NULL,
  `event_type_id` INT NOT NULL,
  `room_id` INT DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_event_event_type1_idx` (`event_type_id` ASC),
  INDEX `fk_event_room1_idx` (`room_id` ASC),
  INDEX `fk_event_educational_group1_idx` (`group_id` ASC),
  CONSTRAINT `fk_event_event_type1`
    FOREIGN KEY (`event_type_id`)
    REFERENCES `event_type` (`id`),
  CONSTRAINT `fk_event_room1`
    FOREIGN KEY (`room_id`)
    REFERENCES `room` (`id`),
  CONSTRAINT `fk_event_educational_group1`
    FOREIGN KEY (`group_id`)
    REFERENCES `educational_group` (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `template`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `template` ;
CREATE TABLE `template` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `event_type_id` INT NOT NULL,
  `strategy_id` INT NULL,
  `duration` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_template_strategy_idx` (`strategy_id` ASC),
  INDEX `fk_template_event_type_idx` (`event_type_id` ASC),
  CONSTRAINT `fk_template_strategy`
    FOREIGN KEY (`strategy_id`)
    REFERENCES `strategy`(`id`),
  CONSTRAINT `fk_template_event_type`
    FOREIGN KEY (`event_type_id`)
    REFERENCES `event_type` (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `student`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `student` ;
CREATE TABLE `student` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `image` LONGBLOB NULL,
  `group_id` INT NOT NULL,
  `english_level_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_student_educational_group1_idx` (`group_id` ASC),
  INDEX `fk_student_english_level1_idx` (`group_id` ASC),
  CONSTRAINT `fk_student_educational_group1`
      FOREIGN KEY (`group_id`)
      REFERENCES `educational_group` (`id`),
  CONSTRAINT `fk_student_english_level1`
      FOREIGN KEY (`english_level_id`)
      REFERENCES `english_level` (`id`))

ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `english_level`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `english_level` ;
CREATE TABLE `english_level` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `level` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  UNIQUE INDEX `level_UNIQUE` (`level` ASC))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

use caesar;
insert into role_category (`name`) values ('watcher');
insert into role_category (`name`) values ('itacademy');
insert into role_category (`name`) values ('tes');
insert into role (`name`,category_id) values ('recrouter',1);
insert into role (`name`,category_id) values ('guest',1);
insert into role (`name`,category_id) values ('teacher',2);
insert into role (`name`,category_id) values ('coordinator',2);
insert into role (`name`,category_id) values ('admin',2);
insert into role (`name`,category_id) values ('tes',3);
insert into status_category (`name`) values ('future');
insert into status_category (`name`) values ('current');
insert into status_category (`name`) values ('finished');
insert into `status` (`name`,category_id) values ('planned',1);
insert into `status` (`name`,category_id) values ('boarding',1);
insert into `status` (`name`,category_id) values ('before start',2);
insert into `status` (`name`,category_id) values ('in-process',2);
insert into `status` (`name`,category_id) values ('offering',2);
insert into `status` (`name`,category_id) values ('graduated',3);
insert into strategy () values ();
insert into strategy () values ();
insert into specialization (`name`,strategy_id) values ('oop_java_core',1);
insert into specialization (`name`,strategy_id) values ('js_core',2);
insert into specialization (`name`,strategy_id) values ('web_C#_.net',1);
insert into specialization (`name`,strategy_id) values ('testing_ISTQB',1);
insert into specialization (`name`,strategy_id) values ('HTML5_CSS3_JS_foundamentals',1);
insert into specialization (`name`,strategy_id) values ('iOS',1);
insert into specialization (`name`,strategy_id) values ('JAVA',1);
insert into specialization (`name`,strategy_id) values ('java_for_non_it',1);
insert into specialization (`name`,strategy_id) values ('.NET',1);
insert into specialization (`name`,strategy_id) values ('Database',1);
insert into specialization (`name`,strategy_id) values ('manual_quality_control',2);
insert into specialization (`name`,strategy_id) values ('ua_c++',1);
insert into specialization (`name`,strategy_id) values ('automation_testing_quality_control',1);
insert into specialization (`name`,strategy_id) values ('devops',1);
insert into specialization (`name`,strategy_id) values ('lamp',1);
insert into specialization (`name`,strategy_id) values ('web_ui',1);
insert into specialization (`name`,strategy_id) values ('web_ui_full_stack',1);
insert into specialization (`name`,strategy_id) values ('UX',1);
insert into specialization (`name`,strategy_id) values ('ua_web_ui_js',1);
insert into specialization (`name`,strategy_id) values ('quality_control_for_cloud_computing',2);
insert into specialization (`name`,strategy_id) values ('angular_boot_camp',1);
insert into specialization (`name`,strategy_id) values ('ua_go_boot_camp',1);
insert into specialization (`name`,strategy_id) values ('ua_test_automation_boot_camp_for_mobile_with_python',1);
insert into specialization (`name`,strategy_id) values ('ua_software_engineering_with_testing',1);
insert into event_type (`name`,`is_key_date`) values ('Demo 1',TRUE);
insert into event_type (`name`,`is_key_date`) values ('Demo 2',TRUE);
insert into event_type (`name`,`is_key_date`) values ('Offerring Demo',TRUE);
insert into event_type (`name`,`is_key_date`) values ('Final Demo',TRUE);
insert into event_type (`name`) values ('lesson');
insert into event_type (`name`) values ('weekly report');
insert into event_type (`name`) values ('lecture');
insert into event_type (`name`) values ('consultation');
insert into event_type (`name`) values ('practice');
insert into event_type (`name`) values ('work with expert');
insert into template (event_type_id,strategy_id, duration) values (1,1,21);
insert into template (event_type_id,strategy_id, duration) values (1,2,28);
insert into template (event_type_id,strategy_id, duration) values (2,1,21);
insert into template (event_type_id,strategy_id, duration) values (3,1,21);
insert into template (event_type_id,strategy_id, duration) values (4,1,0);
insert into template (event_type_id,strategy_id, duration) values (4,2,0);
insert into location (`name`,coordinator_id) values ('Dnipro',NULL);
insert into location (`name`,coordinator_id) values ('Sofia', NULL);
insert into location (`name`) values ('Lviv');
insert into location (`name`) values ('Chernivtsi');
insert into location (`name`) values ('Kyiv');
insert into budget_owner (`name`) values ('SOFTSERVE');
insert into budget_owner (`name`) values ('OPEN GROUP');
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Oleg','Shvets',3,'OlegShvets','ghd22df',1);
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Dmytro','Petin',4,'DmytroPetin','fgdfg24sd',1);
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Lucas','Lukichich',5,'LukasLukichich','fjgf24sd',2);
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Stefan','Vendera',4,'SlavaUkr','asfsdag2',3);
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Kozak','Kozachenko',4,'Kozachok','dfgdsg33s',4);
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Petro','Poroshenko',4,'Roshenneprodam','zxvfdsg3sa',5);
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Mykhaylo','Zadornoff',3,'AmericansAreStupid','asdfsda33d',1);
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Volodymyr','Sharapov',3,'IveSadGorbatyi','fdgsdfg4',1);
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Ole','Lukoe',3,'OnionRing','dsgagbb42',2);
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Milko','Trinichich',3,'MilkoTrilko','cvbxnmdsdf2d',2);
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Yulia','Timoshenko',3,'Kosa','dsgacccbbc',5);
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Viktor','Fedorovich',3,'Shapka','safsdghds',5);
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Renat','Ahmetov',3,'UkrDonaldTrump','asg33w4wfs',5);
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Taras','Shevchenko',3,'IveCriedForUkr','gdsfg3fgcccc',3);
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Bogdan','Khmelniskiy',3,'SonKiller','sdfgvbcvb32dsf',3);
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Bill','Gates',3,'HonoredCitizen',',mnbvcgfd3',4);
insert into educational_group (`name`,location_id,start_date,finish_date,status_id,specialization_id,budget_owner_id) values ('DP-115',1,'2017-04-29', '2017-08-18',4,7,1);
insert into educational_group (`name`,location_id,start_date,finish_date,status_id,specialization_id,budget_owner_id,is_deleted) values ('DP-116',1,'2017-04-29', '2017-08-18',6,6,1, TRUE);
insert into educational_group (`name`,location_id,start_date,finish_date,status_id,specialization_id,budget_owner_id) values ('DP-111-net',1,'2016-04-30','2016-08-01',6,9,1);
insert into educational_group (`name`,location_id,start_date,finish_date,status_id,specialization_id,budget_owner_id) values ('SF-120-js',2,'2018-01-10','2018-04-10',1,2,2);
insert into educational_group (`name`,location_id,start_date,finish_date,status_id,specialization_id,budget_owner_id) values ('CH-116-csh',1,'2017-08-05','2017-11-06',3,3,1);
insert into educational_group (`name`,location_id,start_date,finish_date,status_id,specialization_id,budget_owner_id) values ('KV-99-db',5,'2017-08-25','2017-12-01',2,10,1);
insert into educational_group (`name`,location_id,start_date,finish_date,status_id,specialization_id,budget_owner_id) values ('LV-143-ios',3,'2017-04-28','2017-08-18',5,6,1);
insert into expert (edu_group_id, expert_name) values (1, 'Sergey');
insert into expert (edu_group_id,expert_name) values (2,'kristina agilera');
insert into expert (edu_group_id,expert_name) values (4,'Justin Bieber');
insert into expert (edu_group_id,expert_name) values (4,'Selena Gomez');
insert into expert (edu_group_id,expert_name) values (3,'Lyolik');
insert into expert (edu_group_id,expert_name) values (3,'Bolik');
insert into expert (edu_group_id,expert_name) values (5,'Igor Kolomoiskiy');
insert into expert (edu_group_id,expert_name) values (5,'Dmytro Parubiy');
insert into expert (edu_group_id,expert_name) values (6,'Lev Troitskiy');
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Lev','Bukhanets',2,'Myst1c','sdfhs332w',1);
insert into user (first_name,last_name,role_id,nick_name,password_hash_code,location_id) values ('Dmytro','Kholod',2,'DimaKh','sdasdfdew',1);
insert into group_teacher (teacher_id,group_id) values (1,1);
insert into group_teacher (teacher_id,group_id) values (1,2);
insert into group_teacher (teacher_id,group_id) values (11,3);
insert into group_teacher (teacher_id,group_id) values (12,3);
insert into group_teacher (teacher_id,group_id) values (10,4);
insert into group_teacher (teacher_id,group_id) values (18,4);
insert into group_teacher (teacher_id,group_id) values (8,5);
insert into group_teacher (teacher_id,group_id) values (13,5);
insert into group_teacher (teacher_id,group_id) values (14,5);
insert into group_teacher (teacher_id,group_id) values (17,6);
insert into group_teacher (teacher_id,group_id) values (17,6);
insert into room (`number`,location_id) values ('705',1);
insert into room (`number`,location_id) values ('703',1);
insert into room (`number`,location_id) values ('611',2);
insert into room (`number`,location_id) values ('612',2);
insert into room (`number`,location_id) values ('613',2);
insert into room (`number`,location_id) values ('705',3);
insert into room (`number`,location_id) values ('706-a',3);
insert into room (`number`,location_id) values ('706-b',3);
insert into room (`number`,location_id) values ('505',4);
insert into room (`number`,location_id) values ('502',4);
insert into room (`number`,location_id) values ('501',4);
insert into room (`number`,location_id) values ('311',5);
insert into room (`number`,location_id) values ('312',5);
insert into `event` (`datetime`,duration,group_id,event_type_id,room_id) values ('2017-07-24 18:00:00',120,1,2,1);
insert into `event` (`datetime`,duration,group_id,event_type_id,room_id) values ('2017-07-25 18:00:00',120,1,1,1);
insert into `event` (`datetime`,duration,group_id,event_type_id,room_id) values ('2017-07-24 18:00:00',120,2,2,1);
insert into `event` (`datetime`,duration,group_id,event_type_id,room_id) values ('2017-07-25 18:00:00',120,2,1,1);
insert into `event` (`datetime`,duration,group_id,event_type_id,room_id) values ('2017-07-27 18:30:00',30,1,3,1);
insert into `event` (`datetime`,duration,group_id,event_type_id,room_id) values ('2017-07-24 18:00:00',120,6,2,8);
insert into `event` (`datetime`,duration,group_id,event_type_id,room_id) values ('2017-07-28 10:00:00',120,6,4,6);
insert into `event` (`datetime`,duration,group_id,event_type_id,room_id) values ('2017-07-29 12:30:00',90,6,5,7);
insert into `event` (`datetime`,duration,group_id,event_type_id,room_id) values ('2017-07-24 18:00:00',120,1,5,1);
insert into `event` (`datetime`,duration,group_id,event_type_id,room_id) values ('2017-07-25 18:00:00',120,1,7,1);
insert into `event` (`datetime`,duration,group_id,event_type_id,room_id) values ('2017-07-26 18:00:00',120,1,9,1);
insert into `event` (`datetime`,duration,group_id,event_type_id,room_id) values ('2017-07-27 18:00:00',120,1,10,1);
insert into english_level (name,`level`) values ('elementary',1);
insert into english_level (name,`level`) values ('pre-intermediate',2);
insert into english_level (name,`level`) values ('intermediate',3);
insert into english_level (name,`level`) values ('upper-intermediate',4);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Lev','Bukhanets',2,4, 600, 4, 1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Maksym','Kameniev',2,4, 600, 4, 2);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Dmytro','Kholod',2,4, 600, 4, 2);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Konstantin','Musienko',2,4,  600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Andrii','Serkes',2,4, 600, 4, 2);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Vladyslav','Vorontsov',2,4, 600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Anastasiia','Ilina',2,4, 600, 4,2);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Roy','Batty',3,3, 600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Motoko','Kusanagi',3,2,  600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Daisuke','Aramaki',3,1,  600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Thomas','Anderson',3,4,  600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Hans','Landa',3,2, 600, 4,2);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('King','Schultz',3,3, 600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Ace','Ventura',3,4, 600, 4,2);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Forrest','Gump',5,3, 600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Hannibal','Lecter',5,3, 600, 4,2);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Jack','Sparrow',5,3, 600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Marty','McFly',5,3, 600, 4, 2);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Darth','Vader',5,3, 600, 4, 2);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Tyler','Durden',6,2, 600, 4, 1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('John','McClane',6,1, 600, 4, 1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Ellen','Ripley',6,4,  600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Han','Solo',6,2, 600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('James','Bond',6,3, 600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Indiana','Jones',6,2, 600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Bruce','Wayne',6,4, 600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Andy','Dufresne',6,2, 600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Edward','Scissorhands',7,3, 600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Stanley','Ipkiss',7,4, 600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Freddy','Krueger',7,2, 600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('William','Wallace',7,3, 600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Bruce','Nolan',7,4, 600, 4,1);
insert into student (first_name,last_name,group_id,english_level_id, incoming_test, entry_score, approved_by_expert_id) values ('Carl','Allen',7,2, 600, 4,1);
update location set coordinator_id = 2 where `name` = 'Dnipro';
update location set coordinator_id = 3 where `name` = 'Sofia';
update location set coordinator_id = 6 where `name` = 'Lviv';
update location set coordinator_id = 7 where `name` = 'Chernivtsi';
update location set coordinator_id = 8 where `name` = 'Kyiv';

