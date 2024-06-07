-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `orden`
--

DROP TABLE IF EXISTS `orden`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orden` (
  `idOrden` int NOT NULL AUTO_INCREMENT,
  `fecha` varchar(45) DEFAULT NULL,
  `cedulaCliente` varchar(45) NOT NULL,
  `matricula` varchar(45) DEFAULT NULL,
  `modeloAuto` varchar(45) DEFAULT NULL,
  `color` varchar(45) DEFAULT NULL,
  `numeroChasis` varchar(45) DEFAULT NULL,
  `marca` varchar(45) DEFAULT NULL,
  `medidorGasolina` int DEFAULT NULL,
  `descripcion` longtext,
  `valorInsumos` int DEFAULT NULL,
  `valorServicio` int DEFAULT NULL,
  `valorTotal` int DEFAULT NULL,
  PRIMARY KEY (`idOrden`,`cedulaCliente`),
  KEY `fk_Orden_Cliente_idx` (`cedulaCliente`),
  CONSTRAINT `fk_Orden_Cliente` FOREIGN KEY (`cedulaCliente`) REFERENCES `cliente` (`cedula`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orden`
--

LOCK TABLES `orden` WRITE;
/*!40000 ALTER TABLE `orden` DISABLE KEYS */;
INSERT INTO `orden` VALUES (29,'03/28/2024','72122385','72122385','2020','Blanco Alpin','11111111111111111','BMW',37,'Latoneria: Capo, Puerta trasera derecha, baul y bomper delantero\nPintura: Capo, Puerta trasera derecha, baul, bomper delantero, techo, bomper trasero\npuerta delantera derecha\n ',40000,50000,90000),(32,'03/28/2024','72122385','72122385','2020','Blanco Alpin','11111111111111111','BMW',100,'Latoneria: Capo, Puerta trasera derecha, baul y bomper delantero\nPintura: Capo, Puerta trasera derecha, baul, bomper delantero, techo, bomper trasero\npuerta delantera derecha\n ',50000,40000,90000),(33,'03/28/2024','72122385','72122385','2020','Blanco Alpin','11111111111111111','BMW',57,'Latoneria: Capo, Puerta trasera derecha, baul y bomper delantero\n',40000,50000,90000),(34,'03/28/2022','72122385','72122385','2020','Blanco Alpin','11111111111111111','BMW',100,'Latoneria: Capo, Puerta trasera derecha, baul y bomper delantero\nPintura: Capo, Puerta trasera derecha, baul, bomper delantero, techo, bomper trasero\n',40000,50000,90000);
/*!40000 ALTER TABLE `orden` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-30 21:12:01
