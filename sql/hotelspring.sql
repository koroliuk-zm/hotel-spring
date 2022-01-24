-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema hotelspringdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema hotelspringdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hotelspringdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `hotelspringdb` ;

-- -----------------------------------------------------
-- Table `hotelspringdb`.`order_statuses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotelspringdb`.`order_statuses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hotelspringdb`.`user_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotelspringdb`.`user_roles` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_c519w0l613l023tayy895chpd` (`role` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hotelspringdb`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotelspringdb`.`users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(60) NOT NULL,
  `is_enable` BIT(1) NOT NULL,
  `login` VARCHAR(30) NOT NULL,
  `name` VARCHAR(60) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `surname` VARCHAR(60) NOT NULL,
  `user_role_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_6dotkott2kjsp8vw4d0m25fb7` (`email` ASC) VISIBLE,
  UNIQUE INDEX `UK_ow0gan20590jrb00upg3va2fn` (`login` ASC) VISIBLE,
  INDEX `FKsy1luwgtc2qas77si4xlrkjtl` (`user_role_id` ASC) VISIBLE,
  CONSTRAINT `FKsy1luwgtc2qas77si4xlrkjtl`
    FOREIGN KEY (`user_role_id`)
    REFERENCES `hotelspringdb`.`user_roles` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 206
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hotelspringdb`.`room_statuses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotelspringdb`.`room_statuses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hotelspringdb`.`room_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotelspringdb`.`room_types` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hotelspringdb`.`rooms`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotelspringdb`.`rooms` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(1000) NOT NULL,
  `number` INT NOT NULL,
  `perday_cost` INT NOT NULL,
  `seats_amount` INT NOT NULL,
  `room_statuses_id` INT NOT NULL,
  `room_types_id` INT NOT NULL,
  `file_name` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK620tufg8l2mnn4fgi40dxt2fu` (`room_statuses_id` ASC) VISIBLE,
  INDEX `FK98fvuwdwmabnriyef02fsdsu0` (`room_types_id` ASC) VISIBLE,
  CONSTRAINT `FK620tufg8l2mnn4fgi40dxt2fu`
    FOREIGN KEY (`room_statuses_id`)
    REFERENCES `hotelspringdb`.`room_statuses` (`id`),
  CONSTRAINT `FK98fvuwdwmabnriyef02fsdsu0`
    FOREIGN KEY (`room_types_id`)
    REFERENCES `hotelspringdb`.`room_types` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 29
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hotelspringdb`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotelspringdb`.`orders` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `check_in_date` DATE NOT NULL,
  `check_out_date` DATE NOT NULL,
  `order_date` DATETIME(6) NOT NULL,
  `total_cost` INT NOT NULL,
  `order_statuses_id` INT NOT NULL,
  `rooms_id` BIGINT NOT NULL,
  `users_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKh7cl9hyrfb8cquvinn959ceax` (`order_statuses_id` ASC) VISIBLE,
  INDEX `FKe6k45xxoin4fylnwg2jkehwjf` (`users_id` ASC) VISIBLE,
  INDEX `FKqcuvghitapebgcgc04e4wp2a6` (`rooms_id` ASC) VISIBLE,
  CONSTRAINT `FKe6k45xxoin4fylnwg2jkehwjf`
    FOREIGN KEY (`users_id`)
    REFERENCES `hotelspringdb`.`users` (`id`),
  CONSTRAINT `FKh7cl9hyrfb8cquvinn959ceax`
    FOREIGN KEY (`order_statuses_id`)
    REFERENCES `hotelspringdb`.`order_statuses` (`id`),
  CONSTRAINT `FKqcuvghitapebgcgc04e4wp2a6`
    FOREIGN KEY (`rooms_id`)
    REFERENCES `hotelspringdb`.`rooms` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hotelspringdb`.`requests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotelspringdb`.`requests` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `check_in_date` DATE NOT NULL,
  `check_out_date` DATE NOT NULL,
  `request_date` DATETIME(6) NOT NULL,
  `seats_number` INT NOT NULL,
  `room_types_id` INT NOT NULL,
  `is_proceed` BIT(1) NOT NULL,
  `users_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKhadpkfcci004y602of2n9cpu5` (`users_id` ASC) VISIBLE,
  INDEX `FKiapqxfbkcxdao96taltfrc7aw` (`room_types_id` ASC) VISIBLE,
  CONSTRAINT `FKhadpkfcci004y602of2n9cpu5`
    FOREIGN KEY (`users_id`)
    REFERENCES `hotelspringdb`.`users` (`id`),
  CONSTRAINT `FKiapqxfbkcxdao96taltfrc7aw`
    FOREIGN KEY (`room_types_id`)
    REFERENCES `hotelspringdb`.`room_types` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
