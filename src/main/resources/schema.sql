DROP TABLE IF EXISTS `ODONTOLOGOS`;
DROP TABLE IF EXISTS `PACIENTES`;
DROP TABLE IF EXISTS `TURNOS`;
DROP TABLE IF EXISTS `USUARIOS`;

CREATE TABLE IF NOT EXISTS `ODONTOLOGOS` (
    `id` bigint AUTO_INCREMENT NOT NULL UNIQUE,
	`matricula` bigint NOT NULL UNIQUE,
	`nombre` varchar(100) NOT NULL,
	`apellido` varchar(100) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `PACIENTES` (
    `id` bigint AUTO_INCREMENT NOT NULL UNIQUE,
	`dni` bigint NOT NULL UNIQUE,
	`nombre` varchar(100) NOT NULL,
	`apellido` varchar(100) NOT NULL,
	`domicilio` varchar(200) NOT NULL,
	`fecha_alta` date NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `TURNOS` (
	`id` bigint AUTO_INCREMENT NOT NULL UNIQUE,
	`fecha` date NOT NULL,
	`hora` time NOT NULL,
	`dni_paciente` bigint NOT NULL,
	`matricula_odontologo` bigint NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `USUARIOS` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
	`usuario` varchar(100) NOT NULL UNIQUE,
	`contrasena` varchar(255) NOT NULL,
	`role` varchar(25) NOT NULL,
	PRIMARY KEY (`id`)
);
