
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `spring_assignment` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema spring_assignment
-- -----------------------------------------------------
USE `spring_assignment` ;

-- -----------------------------------------------------
-- Table `mydb`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `spring_assignment`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `age` INT NOT NULL,
  `gender` VARCHAR(4) NOT NULL,
  `enabled` TINYINT(4) NULL DEFAULT NULL,
  `branch` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `spring_assignment`.`roles` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `spring_assignment`.`user_role` (
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  INDEX `fk_user_role_1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_user_role_2_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_role_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `spring_assignment`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_2`
    FOREIGN KEY (`role_id`)
    REFERENCES `spring_assignment`.`roles` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`events`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `spring_assignment`.`events` (
  `event_id` INT NOT NULL AUTO_INCREMENT,
  `event_name` VARCHAR(45) NOT NULL,
  `event_venue` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`event_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`user_event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `spring_assignment`.`user_event` (
  `user_id` INT NOT NULL,
  `event_id` INT NOT NULL,
  INDEX `fk_user_event_1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_user_event_2_idx` (`event_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_event_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `spring_assignment`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_event_2`
    FOREIGN KEY (`event_id`)
    REFERENCES `spring_assignment`.`events` (`event_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`speakers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `spring_assignment`.`speakers` (
  `speaker_id` INT NOT NULL AUTO_INCREMENT,
  `speaker_name` VARCHAR(45) NOT NULL,
  `speaker_designation` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`speaker_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`event_speaker`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `spring_assignment`.`event_speaker` (
  `event_id` INT NOT NULL,
  `speaker_id` INT NOT NULL,
  INDEX `fk_event_speaker_1_idx` (`event_id` ASC) VISIBLE,
  INDEX `fk_event_speaker_2_idx` (`speaker_id` ASC) VISIBLE,
  CONSTRAINT `fk_event_speaker_1`
    FOREIGN KEY (`event_id`)
    REFERENCES `spring_assignment`.`events` (`event_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_speaker_2`
    FOREIGN KEY (`speaker_id`)
    REFERENCES `spring_assignment`.`speakers` (`speaker_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
