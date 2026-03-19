-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: escaperoom
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `comentarios`
--

DROP TABLE IF EXISTS `comentarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comentarios` (
  `ID_COMENTARIO` int NOT NULL AUTO_INCREMENT,
  `ID_USUARIO` int NOT NULL,
  `NOMBRE_USER` varchar(45) NOT NULL,
  `FECHA_COMENTARIO` datetime NOT NULL,
  `MODO` tinyint NOT NULL,
  `OPINION` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`ID_COMENTARIO`),
  KEY `ID_USUARIO` (`ID_USUARIO`),
  CONSTRAINT `comentarios_ibfk_1` FOREIGN KEY (`ID_USUARIO`) REFERENCES `usuarios` (`ID_USUARIO`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comentarios`
--

LOCK TABLES `comentarios` WRITE;
/*!40000 ALTER TABLE `comentarios` DISABLE KEYS */;
INSERT INTO `comentarios` VALUES (26,2,'Maria','2026-02-24 13:47:42',0,'Muy buenos puzzles'),(27,3,'Carlos','2026-02-24 13:47:42',1,'La ambientacion brutal');
/*!40000 ALTER TABLE `comentarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservas`
--

DROP TABLE IF EXISTS `reservas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservas` (
  `ID_RESERVAS` int NOT NULL AUTO_INCREMENT,
  `NOMBRE_RES` varchar(45) NOT NULL,
  `TELF` int NOT NULL,
  `FECHA_RES` date NOT NULL,
  `EMAIL_RES` varchar(45) NOT NULL,
  `NUM_PLAYER` int NOT NULL,
  `MODE` int NOT NULL,
  `NOTAS` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`ID_RESERVAS`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservas`
--

LOCK TABLES `reservas` WRITE;
/*!40000 ALTER TABLE `reservas` DISABLE KEYS */;
INSERT INTO `reservas` VALUES (1,'Ivan',666123456,'2026-03-10','ivan@gmail.com',4,1,'Cumpleaños sorpresa'),(2,'Maria',612345678,'2026-03-12','maria@gmail.com',3,0,'Primera vez en escape room'),(3,'Carlos',699888777,'2026-03-15','carlos@gmail.com',5,1,'Evento de empresa'),(4,'Laura',622333444,'2026-03-18','laura@gmail.com',2,0,'Pareja'),(5,'Admin',611222333,'2026-03-20','admin@gmail.com',6,1,'Grupo grande');
/*!40000 ALTER TABLE `reservas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `ID_USUARIO` int NOT NULL AUTO_INCREMENT,
  `NOMBRE_USER` varchar(45) NOT NULL,
  `EMAIL_USER` varchar(45) NOT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `ADMIN` tinyint NOT NULL,
  PRIMARY KEY (`ID_USUARIO`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Ivan','ivan@gmail.com','1234',1),(2,'Maria','maria@gmail.com','1234',0),(3,'Carlos','carlos@gmail.com','1234',0),(4,'Ivan','ivan@gmail.com','1234',1),(5,'Maria','maria@gmail.com','1234',0),(6,'Carlos','carlos@gmail.com','1234',0),(7,'Laura','laura@gmail.com','1234',0),(8,'Admin','admin@gmail.com','admin123',1),(9,'Antonio','antrula02@gmail.com','1111',0);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-19 19:20:38
