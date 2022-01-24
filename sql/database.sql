CREATE DATABASE  IF NOT EXISTS `hotelspringdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `hotelspringdb`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: hotelspringdb
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `order_statuses`
--

DROP TABLE IF EXISTS `order_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_statuses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_statuses`
--

LOCK TABLES `order_statuses` WRITE;
/*!40000 ALTER TABLE `order_statuses` DISABLE KEYS */;
INSERT INTO `order_statuses` (`id`, `status`) VALUES (1,'new'),(2,'confirmed'),(3,'paid'),(4,'expired'),(5,'closed');
/*!40000 ALTER TABLE `order_statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `check_in_date` date NOT NULL,
  `check_out_date` date NOT NULL,
  `order_date` datetime(6) NOT NULL,
  `total_cost` int NOT NULL,
  `order_statuses_id` int NOT NULL,
  `rooms_id` bigint NOT NULL,
  `users_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh7cl9hyrfb8cquvinn959ceax` (`order_statuses_id`),
  KEY `FKe6k45xxoin4fylnwg2jkehwjf` (`users_id`),
  KEY `FKqcuvghitapebgcgc04e4wp2a6` (`rooms_id`),
  CONSTRAINT `FKe6k45xxoin4fylnwg2jkehwjf` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKh7cl9hyrfb8cquvinn959ceax` FOREIGN KEY (`order_statuses_id`) REFERENCES `order_statuses` (`id`),
  CONSTRAINT `FKqcuvghitapebgcgc04e4wp2a6` FOREIGN KEY (`rooms_id`) REFERENCES `rooms` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` (`id`, `check_in_date`, `check_out_date`, `order_date`, `total_cost`, `order_statuses_id`, `rooms_id`, `users_id`) VALUES (3,'2022-01-23','2022-01-25','2022-01-22 14:26:03.605000',300,4,1,10),(4,'2022-01-23','2022-01-25','2022-01-22 15:57:24.469000',450,4,2,10),(5,'2022-01-23','2022-01-25','2022-01-22 16:12:49.294000',450,4,2,10),(6,'2022-01-23','2022-01-25','2022-01-22 16:16:05.237000',450,4,2,10),(8,'2022-01-23','2022-01-25','2022-01-22 16:30:23.248000',450,3,2,10),(9,'2022-01-22','2022-01-22','2022-01-22 16:36:31.527000',100,4,1,10),(10,'2022-01-23','2022-01-23','2022-01-23 14:18:22.637000',100,4,1,10),(11,'2022-01-23','2022-01-25','2022-01-23 14:19:56.240000',450,4,2,10),(12,'2022-01-23','2022-01-23','2022-01-23 14:23:46.347000',100,3,1,10),(13,'2022-01-24','2022-01-26','2022-01-23 16:57:40.375000',450,4,2,10);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requests`
--

DROP TABLE IF EXISTS `requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `requests` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `check_in_date` date NOT NULL,
  `check_out_date` date NOT NULL,
  `request_date` datetime(6) NOT NULL,
  `seats_number` int NOT NULL,
  `room_types_id` int NOT NULL,
  `is_proceed` bit(1) NOT NULL,
  `users_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhadpkfcci004y602of2n9cpu5` (`users_id`),
  KEY `FKiapqxfbkcxdao96taltfrc7aw` (`room_types_id`),
  CONSTRAINT `FKhadpkfcci004y602of2n9cpu5` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKiapqxfbkcxdao96taltfrc7aw` FOREIGN KEY (`room_types_id`) REFERENCES `room_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requests`
--

LOCK TABLES `requests` WRITE;
/*!40000 ALTER TABLE `requests` DISABLE KEYS */;
/*!40000 ALTER TABLE `requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_statuses`
--

DROP TABLE IF EXISTS `room_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_statuses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_statuses`
--

LOCK TABLES `room_statuses` WRITE;
/*!40000 ALTER TABLE `room_statuses` DISABLE KEYS */;
INSERT INTO `room_statuses` (`id`, `status`) VALUES (1,'free'),(2,'booked'),(3,'busy'),(4,'unavailable');
/*!40000 ALTER TABLE `room_statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_types`
--

DROP TABLE IF EXISTS `room_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_types` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_types`
--

LOCK TABLES `room_types` WRITE;
/*!40000 ALTER TABLE `room_types` DISABLE KEYS */;
INSERT INTO `room_types` (`id`, `type`) VALUES (1,'standart'),(2,'family'),(3,'duplex'),(4,'president'),(5,'triple');
/*!40000 ALTER TABLE `room_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rooms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) NOT NULL,
  `number` int NOT NULL,
  `perday_cost` int NOT NULL,
  `seats_amount` int NOT NULL,
  `room_statuses_id` int NOT NULL,
  `room_types_id` int NOT NULL,
  `file_name` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK620tufg8l2mnn4fgi40dxt2fu` (`room_statuses_id`),
  KEY `FK98fvuwdwmabnriyef02fsdsu0` (`room_types_id`),
  CONSTRAINT `FK620tufg8l2mnn4fgi40dxt2fu` FOREIGN KEY (`room_statuses_id`) REFERENCES `room_statuses` (`id`),
  CONSTRAINT `FK98fvuwdwmabnriyef02fsdsu0` FOREIGN KEY (`room_types_id`) REFERENCES `room_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` (`id`, `description`, `number`, `perday_cost`, `seats_amount`, `room_statuses_id`, `room_types_id`, `file_name`) VALUES (1,'Some description',1,100,1,1,1,''),(2,'Description for room 2',2,150,2,1,1,''),(3,'President room',101,300,2,1,4,''),(5,'Room number3',3,350,3,1,3,''),(6,'Room nuber 4',4,250,2,1,2,''),(7,'Standart room',5,150,1,1,1,''),(8,'Room number 6',6,120,1,1,1,''),(22,'Two seats room',123,125,2,1,1,'');
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_c519w0l613l023tayy895chpd` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` (`id`, `role`) VALUES (1,'admin'),(3,'user'),(2,'waiter');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(60) NOT NULL,
  `is_enable` bit(1) NOT NULL,
  `login` varchar(30) NOT NULL,
  `name` varchar(60) NOT NULL,
  `password` varchar(60) NOT NULL,
  `surname` varchar(60) NOT NULL,
  `user_role_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_ow0gan20590jrb00upg3va2fn` (`login`),
  KEY `FKsy1luwgtc2qas77si4xlrkjtl` (`user_role_id`),
  CONSTRAINT `FKsy1luwgtc2qas77si4xlrkjtl` FOREIGN KEY (`user_role_id`) REFERENCES `user_roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `email`, `is_enable`, `login`, `name`, `password`, `surname`, `user_role_id`) VALUES (2,'userone@gmail.con',_binary '','userone','userone','$2a$10$kq8GzFzQqu7xD/KTGJ0R7.7Y2qIYNTfX9R655wH8N/BwimWyCxw3W','userone',3),(3,'dkoroliuk88@gmail.com',_binary '','dkoroliuk','Dima','$2a$12$Jw/nU09duMMBXb8JdGvTB.J/zqAXcKZTYP6Hxh0gLBdMoeOVv4eUG','koroliuk',1),(4,'waiter@gmail.com',_binary '','waiter','waiter','$2a$10$ns4ZH2fDs8eM.jJbappFUuVzS9oPTxTbyvDgxFwz.d15VRBmlI..2','waiter',2),(6,'usertwo@meta.ua',_binary '\0','usertwo','usertwo','$2a$10$ITyAhfjvKB0VMfUUhOAfeODOMP7mYSaaPz6dEPQGPHxKXJcRyqqNu','usertwo',3),(7,'user3@gmail.com',_binary '\0','userthree','userthree','$2a$10$3BtgvML5xb8bqxdVf8.X/.8eATs/XWktRLkbnRj3L7cr5ujrzprOi','user3',3),(10,'newuser@gmail.com',_binary '','newuser','newuser','$2a$10$zA/jaAE4pbXFJlwkiXtr3eovktKzs4V3.h/KZEC90.EydgHB1qieu','newuser',3),(12,'user@mail.com',_binary '','user','user','$2a$10$Xa0gAmxDwRht/Q/M8wm9C.7fcXVxKYSYVYyIEXeWQKZHCISl45./i','user',3),(201,'ipetrov@gmail.com',_binary '','ipetrov','Ivan','$2a$10$Y31W2kDfs03inH/FECJ8B.cgO972e9aW3bb5dEfaPrnAzh8EMv6Be','Petrov',3);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-23 19:47:06
