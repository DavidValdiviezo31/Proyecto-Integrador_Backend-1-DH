DROP TABLE IF EXISTS `ODONTOLOGOS`;
DROP TABLE IF EXISTS `PACIENTES`;
DROP TABLE IF EXISTS `TURNOS`;
DROP TABLE IF EXISTS `USUARIOS`;

CREATE TABLE IF NOT EXISTS `ODONTOLOGOS` (
	`matricula` bigint NOT NULL UNIQUE,
	`nombre` varchar(100) NOT NULL,
	`apellido` varchar(100) NOT NULL,
	PRIMARY KEY (`matricula`)
);

CREATE TABLE IF NOT EXISTS `PACIENTES` (
	`dni` bigint NOT NULL UNIQUE,
	`nombre` varchar(100) NOT NULL,
	`apellido` varchar(100) NOT NULL,
	`domicilio` varchar(200) NOT NULL,
	`fecha_alta` date NOT NULL,
	PRIMARY KEY (`dni`)
);

CREATE TABLE IF NOT EXISTS `TURNOS` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
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



ALTER TABLE `TURNOS` ADD CONSTRAINT `TURNOS_fk3` FOREIGN KEY (`dni_paciente`) REFERENCES `PACIENTES`(`dni`);

ALTER TABLE `TURNOS` ADD CONSTRAINT `TURNOS_fk4` FOREIGN KEY (`matricula_odontologo`) REFERENCES `ODONTOLOGOS`(`matricula`);
