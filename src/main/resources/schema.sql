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
-- Table `specialization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `specialization`;
CREATE TABLE `specialization` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `education_group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `education_group`;
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
  PRIMARY KEY (`id`),
  INDEX `fk_group_status1_idx` (`status_id` ASC),
  INDEX `fk_group_location1_idx` (`location_id` ASC),
  INDEX `fk_group_spetialization1_idx` (`specialization_id` ASC),
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
  INDEX `fk_group_teacher_education_group1_idx` (`group_id` ASC),
  INDEX `fk_group_teacher_user1_idx` (`teacher_id` ASC),
  CONSTRAINT `fk_group_teacher_education_group1`
    FOREIGN KEY (`group_id`)
    REFERENCES `educational_group` (`id`),
  CONSTRAINT `fk_group_teacher_user1`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `user` (`id`))
ENGINE = InnoDB;


DROP TABLE IF EXISTS `expert` ;
CREATE TABLE `expert` (
  `edu_group_id` INT NOT NULL,
  `expert_name` VARCHAR(100) NOT NULL,
  INDEX `fk_expert_education_group1_idx` (`edu_group_id` ASC),
  CONSTRAINT `fk_expert_education_group1`
    FOREIGN KEY (`edu_group_id`)
    REFERENCES `educational_group` (`id`))
ENGINE = InnoDB;

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
  `room_id` INT NOT NULL,
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

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;