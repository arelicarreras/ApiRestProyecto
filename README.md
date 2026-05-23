# Sistema de Gestión de Tickets de Soporte

Sistema web desarrollado en Java utilizando arquitectura REST para la gestión de tickets de soporte técnico.

El proyecto permite registrar incidencias, asignarlas automáticamente a técnicos, administrar estados, adjuntar archivos y mantener un timeline completo de eventos para garantizar trazabilidad.

---

# Tecnologías Utilizadas

## Backend
- Java JDK 21
- Jersey 3.1 (JAX-RS)
- JDBC
- Maven

## Frontend
- JSP
- HTML5
- CSS3
- JavaScript

## Base de Datos
- PostgreSQL 16
- Neon Database (Cloud)

## Servidor y Deploy
- Apache Tomcat 10.1
- Docker
- Render

## Control de Versiones
- Git
- GitHub

---

# Arquitectura del Proyecto

```plaintext
src/main/java/
├── com.apirest.db
├── com.apirest.modelo
├── com.apirest.recurso
├── com.apirest.util

src/main/webapp/
├── Login.jsp
├── Menu.jsp
├── Tickets.jsp
├── Timeline.jsp
├── Usuarios.jsp
└── TicketAdjunto.jsp
```

---

# Funcionalidades

- Login de usuarios
- Gestión completa de tickets
- Cambio de estados
- Autoasignación de técnicos
- Timeline de eventos
- Subida y descarga de archivos
- Gestión de usuarios
- Filtro por estado
- Registro de observaciones
- Validación y cierre de tickets

---

# Flujo del Sistema

1. El usuario crea un ticket
2. El técnico acepta el ticket
3. El sistema autoasigna el técnico logueado
4. El técnico trabaja el ticket
5. El ticket pasa a validación
6. El solicitante aprueba o rechaza
7. El ticket se finaliza

---

# Estados del Ticket

- CREADO
- ASIGNADO
- VALIDACION
- DEVUELTO
- RECHAZADO
- FINALIZADO

---

# Base de Datos

El sistema utiliza PostgreSQL 16 en Neon.

Tablas principales:

- usuarios
- ticket
- ticket_adjuntos
- timeline_ticket

---

# API REST

## Tickets

| Método | Endpoint |
|---|---|
| GET | /api/tickets |
| POST | /api/tickets |
| PUT | /api/tickets/{codigo}/aceptar |
| PUT | /api/tickets/{codigo}/validacion |
| PUT | /api/tickets/{codigo}/rechazar |
| PUT | /api/tickets/{codigo}/finalizar |
| DELETE | /api/tickets/{codigo} |

---

## Usuarios

| Método | Endpoint |
|---|---|
| GET | /api/usuarios |
| PUT | /api/usuarios/{id} |
| DELETE | /api/usuarios/{id} |

---

## Timeline

| Método | Endpoint |
|---|---|
| GET | /api/timeline/{codigo} |
| DELETE | /api/timeline/{codigo} |

---

## Adjuntos

| Método | Endpoint |
|---|---|
| GET | /api/adjuntos |
| POST | /api/adjuntos/subir |
| GET | /api/adjuntos/descargar/{id} |

---

# Deploy

## Aplicación desplegada en Render

https://sistematickets.onrender.com

---

# Base de Datos Cloud

Neon PostgreSQL

---

# Docker

El proyecto utiliza Docker para compilar automáticamente el WAR y desplegarlo en Tomcat.

## Dockerfile

```dockerfile
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM tomcat:10.1.36-jdk21

WORKDIR /usr/local/tomcat/webapps/

COPY --from=build /app/target/*.war ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
```

---

# Ejecución Local

```bash
mvn clean package -DskipTests
```

---

# Autor

Proyecto académico desarrollado para gestión de tickets de soporte técnico.
