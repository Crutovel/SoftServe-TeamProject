SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP TABLE IF EXISTS `role_category`;
CREATE TABLE `role_category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_role_category1_idx` (`category_id` ASC),
  CONSTRAINT `fk_role_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `caesar`.`role_category` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `location`;
CREATE TABLE `location` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `country_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_location_country1_idx` (`country_id` ASC),
  CONSTRAINT `fk_location_country1`
    FOREIGN KEY (`country_id`)
    REFERENCES `caesar`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `role_id` INT NOT NULL,
  `date_of_birth` DATE NULL,
  `nick_name` VARCHAR(45) NOT NULL,
  `password_hash_code` VARCHAR(45) NOT NULL,
  `self_info` VARCHAR(255) NULL,
  `image` LONGBLOB NULL,
  `location_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_role1_idx` (`role_id` ASC),
  INDEX `fk_user_location1_idx` (`location_id` ASC),
  CONSTRAINT `fk_user_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `caesar`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `caesar`.`location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `email`;
CREATE TABLE `email` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(45) NOT NULL,
  `is_it_primary` TINYINT(1) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_email_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_email_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `caesar`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `phone`;
CREATE TABLE `phone` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(45) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_phone_user_idx` (`user_id` ASC),
  CONSTRAINT `fk_phone_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `caesar`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `contact_link`;
CREATE TABLE `contact_link` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(45) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_contact_link_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_contact_link_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `caesar`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `status_category`;
CREATE TABLE `status_category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `status`;
CREATE TABLE `status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_status_status_category1_idx` (`category_id` ASC),
  CONSTRAINT `fk_status_status_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `caesar`.`status_category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `specialization`;
CREATE TABLE `specialization` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `educational_group`;
CREATE TABLE `educational_group` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `teacher_id` INT NOT NULL,
  `location_id` INT NOT NULL,
  `start_date` DATE NOT NULL,
  `finish_date` DATE NOT NULL,
  `status_id` INT NOT NULL,
  `specialization_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_group_user1_idx` (`teacher_id` ASC),
  INDEX `fk_group_status1_idx` (`status_id` ASC),
  INDEX `fk_group_location1_idx` (`location_id` ASC),
  INDEX `fk_group_spetialization1_idx` (`specialization_id` ASC),
  CONSTRAINT `fk_group_user1`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `caesar`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `caesar`.`status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `caesar`.`location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_spetialization1`
    FOREIGN KEY (`specialization_id`)
    REFERENCES `caesar`.`specialization` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `student_group`;
CREATE TABLE `student_group` (
  `group_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  INDEX `fk_student_group_group1_idx` (`group_id` ASC),
  INDEX `fk_student_group_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_student_group_group1`
    FOREIGN KEY (`group_id`)
    REFERENCES `caesar`.`educational_group` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_group_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `caesar`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
