CREATE DATABASE IF NOT EXISTS `lution` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `lution`;

CREATE USER IF NOT EXISTS 'lution'@'%' IDENTIFIED BY 'lution';
GRANT SELECT, INSERT, UPDATE, DELETE ON `lution`.* TO 'lution'@'%';

DROP TABLE IF EXISTS `reserva`;
DROP TABLE IF EXISTS `mantenimiento`;
DROP TABLE IF EXISTS `cancha`;
DROP TABLE IF EXISTS `horario`;
DROP TABLE IF EXISTS `tipo_cancha`;
DROP TABLE IF EXISTS `usuario`;

CREATE TABLE `usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `dni` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `rol` varchar(45) NOT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `dni_UNIQUE` (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tipo_cancha` (
  `idTipoCancha` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `cantMaxJugadores` int NOT NULL,
  `precio` double unsigned NOT NULL,
  PRIMARY KEY (`idTipoCancha`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `horario` (
  `idHorario` int NOT NULL AUTO_INCREMENT,
  `horaInicio` int NOT NULL,
  `horaFin` int NOT NULL,
  `intervaloHoras` int NOT NULL,
  PRIMARY KEY (`idHorario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `cancha` (
  `idCancha` int NOT NULL AUTO_INCREMENT,
  `numero` int NOT NULL,
  `idTipoCancha` int NOT NULL,
  `idHorario` int NOT NULL,
  PRIMARY KEY (`idCancha`),
  UNIQUE KEY `numero_UNIQUE` (`numero`),
  KEY `fk_cancha_tipo_cancha` (`idTipoCancha`),
  KEY `fk_cancha_horario` (`idHorario`),
  CONSTRAINT `fk_cancha_tipo_cancha` FOREIGN KEY (`idTipoCancha`) REFERENCES `tipo_cancha` (`idTipoCancha`),
  CONSTRAINT `fk_cancha_horario` FOREIGN KEY (`idHorario`) REFERENCES `horario` (`idHorario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `mantenimiento` (
  `idMantenimiento` int NOT NULL AUTO_INCREMENT,
  `idCancha` int NOT NULL,
  `fecha` date NOT NULL,
  `horaInicio` int NOT NULL,
  `horaFin` int NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  PRIMARY KEY (`idMantenimiento`),
  KEY `fk_mantenimiento_cancha` (`idCancha`),
  CONSTRAINT `fk_mantenimiento_cancha` FOREIGN KEY (`idCancha`) REFERENCES `cancha` (`idCancha`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `reserva` (
  `idReserva` int NOT NULL AUTO_INCREMENT,
  `idUsuario` int NOT NULL,
  `idCancha` int NOT NULL,
  `fecha` date NOT NULL,
  `horaInicio` int NOT NULL,
  `horaFin` int NOT NULL,
  `estado` varchar(45) NOT NULL DEFAULT 'CONFIRMADA',
  `activa` tinyint GENERATED ALWAYS AS (IF(`estado` = 'CANCELADA', NULL, 1)) VIRTUAL,
  PRIMARY KEY (`idReserva`),
  UNIQUE KEY `reserva_UNIQUE` (`idCancha`,`fecha`,`horaInicio`,`activa`),
  KEY `fk_reserva_usuario` (`idUsuario`),
  CONSTRAINT `fk_reserva_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`),
  CONSTRAINT `fk_reserva_cancha` FOREIGN KEY (`idCancha`) REFERENCES `cancha` (`idCancha`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `usuario` (`dni`, `nombre`, `apellido`, `password`, `rol`) VALUES
  ('11111111', 'Admin', 'Lution', 'admin', 'Administrador'),
  ('22222222', 'Cliente', 'Prueba', NULL, 'Cliente');

INSERT INTO `tipo_cancha` (`nombre`, `descripcion`, `cantMaxJugadores`, `precio`) VALUES
  ('Fútbol 5', 'Césped sintético', 10, 30000),
  ('Pádel', 'Paredes de vidrio', 4, 12000);

INSERT INTO `horario` (`horaInicio`, `horaFin`, `intervaloHoras`) VALUES
  (8, 23, 1),
  (10, 22, 2);

INSERT INTO `cancha` (`numero`, `idTipoCancha`, `idHorario`) VALUES
  (1, 1, 1),
  (2, 1, 1),
  (3, 2, 2);

INSERT INTO `mantenimiento` (`idCancha`, `fecha`, `horaInicio`, `horaFin`, `descripcion`) VALUES
  (1, CURDATE() + INTERVAL 1 DAY, 8, 12, 'Cambio de césped');
