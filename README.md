# SISTEMA DE AGENDAMIENTO DE TURNOS ODONTOLÓGICOS - PROYECTO INTEGRADOR DE BACKEND I [DIGITAL HOUSE]

## **DESCRIPCIÓN:**

Este proyecto Java, desarrollado utilizando el framework Spring Boot,
tiene como objetivo crear un backend para un sistema de gestión de turnos en una clínica dental.
Permite programar citas, administrar pacientes y odontólogos.

## **INTEGRANTES**
1. Jenny Alexandra Munera Serna.
2. Andrés David Intriago Valdiviezo.

## **TRABAJO INTEGRADOR**

**Sistema de reserva de turnos**
Se desea implementar un sistema que permita administrar la reserva de turnos para una clínica odontológica. Este debe cumplir con los siguientes requerimientos:
* **_Administración de datos de odontólogos:_** listar, agregar, modiﬁcar y eliminar odontólogos. Registrar apellido, nombre y matrícula de los mismos. 
* **_Administración de datos de los pacientes:_** listar, agregar, modiﬁcar y eliminar pacientes. De cada uno se almacenan: nombre, apellido, domicilio, DNI y fecha de alta. 
* **_Registrar turno:_** se tiene que poder permitir asignar a un paciente un turno con un odontólogo a una determinada fecha y hora.
* **_Login:_** validar el ingreso al sistema mediante un login con usuario y password. Se debe permitir a cualquier usuario logueado (_ROLE_USER_) registrar un turno, pero solo a quienes tengan un rol de administración (_ROLE_ADMIN_) poder gestionar odontólogos y pacientes. Un usuario podrá tener un único rol y los mismos se ingresarán directamente en la base de datos. 

## **REQUERIMIENTOS TÉCNICOS**
La aplicación debe ser desarrollada en capas:
* **_Capa de entidades de negocio:_** son las clases Java de nuestro negocio modelado a través del paradigma orientado a objetos.
* **_Capa de acceso a datos (Repository):_** son las clases que se encargarán de acceder a la base de datos.
* **_Capa de datos (base de datos):_** es la base de datos de nuestro sistema modelado a través de un modelo entidad-relación. Utilizaremos la base H2 por su practicidad.
* **_Capa de negocio:_** son las clases service que se encargan de desacoplar el acceso a datos de la vista.
* **_Capa de presentación:_** son las pantallas web que tendremos que desarrollar utilizando el framework de **Spring Boot MVC** con los controladores y alguna de estas dos opciones: _HTML + JavaScript_ para la vista.

### **TECNOLOGÍAS UTILIZADAS:**

* **Java:** Lenguaje de programación principal.
* **Spring Boot:** Framework para desarrollo de aplicaciones Java.
* **H2 DATABASE:** Para almacenar información de pacientes, odontólogos, turnos y usuarios.
* **SLF4J:** Para tener registro de lo que sucede dentro de la aplicación.

### **ESTRUCTURA DEL PROYECTO:**

* **src/main/java:** Contiene el código fuente de la aplicación.
* **src/test/java:** Contiene los tests unitarios.
* **src/main/resources:** Contiene los archivos de configuración del proyecto.