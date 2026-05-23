# Sistema de Gestión de Tickets de Soporte

Sistema web desarrollado en Java utilizando arquitectura REST para la gestión de tickets de soporte técnico.

El proyecto permite registrar incidencias, asignarlas automáticamente a técnicos, administrar estados, adjuntar archivos y mantener un timeline completo de eventos para garantizar trazabilidad del soporte.

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
- Neon PostgreSQL Cloud

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
└── com.apirest.util

src/main/webapp/
├── login.jsp
├── menu.jsp
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
- Subida de archivos
- Descarga de archivos
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

# Modelo de Base de Datos

## Tabla usuarios

Almacena los usuarios del sistema.

| Campo | Tipo |
|---|---|
| id | SERIAL |
| nombre | VARCHAR |
| correo | VARCHAR |
| departamento | VARCHAR |
| rol | VARCHAR |
| password | VARCHAR |

---

## Tabla ticket

Almacena los tickets de soporte.

| Campo | Tipo |
|---|---|
| id | SERIAL |
| codigo | VARCHAR |
| descripcion | TEXT |
| departamento | VARCHAR |
| estado | VARCHAR |
| creadoPor | INT |
| asignadoA | INT |
| fechaCreacion | TIMESTAMP |
| fechaCierre | TIMESTAMP |

---

## Tabla ticket_adjuntos

Archivos asociados a tickets.

| Campo | Tipo |
|---|---|
| id | SERIAL |
| ticket | INT |
| nombreOriginal | VARCHAR |
| rutaArchivo | TEXT |
| tipoMime | VARCHAR |
| tamanio | BIGINT |

---

## Tabla timeline_ticket

Historial de eventos del ticket.

| Campo | Tipo |
|---|---|
| id | SERIAL |
| codigo_ticket | VARCHAR |
| fecha_hora | TIMESTAMP |
| estado | VARCHAR |
| actor | VARCHAR |
| observacion | TEXT |

---

# Automatización de Código de Ticket

El sistema genera automáticamente códigos únicos mediante:

- PostgreSQL Sequence
- Trigger
- Function PL/pgSQL

Formato generado:

```txt
TKT-0001
TKT-0002
TKT-0003
```

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
| PUT | /api/tickets/{codigo}/devolver |
| PUT | /api/tickets/{codigo}/finalizar |
| DELETE | /api/tickets/{codigo} |

---

## Usuarios

| Método | Endpoint |
|---|---|
| GET | /api/usuarios |
| POST | /api/usuarios |
| POST | /api/usuarios/login |
| PUT | /api/usuarios/{id} |
| PUT | /api/usuarios/password/{correo} |
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
| DELETE | /api/adjuntos/{id} |

---

# Deploy

## Aplicación desplegada en Render

https://sistematickets.onrender.com

---

# Base de Datos Cloud

Neon PostgreSQL Cloud

---

# Docker

El proyecto utiliza Docker para compilar automáticamente el WAR y desplegarlo en Tomcat.

## Dockerfile

```dockerfile
# Imagen base con Maven y JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Compilar y generar WAR
RUN mvn clean package -DskipTests

# Imagen final con Tomcat
FROM tomcat:10.1.36-jdk21

WORKDIR /usr/local/tomcat/webapps/

# Copiar WAR generado
COPY --from=build /app/target/*.war ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
```

---

# Ejecución Local

## Compilar Proyecto

```bash
mvn clean package -DskipTests
```

---

# Configuración Base de Datos

El proyecto utiliza variables de entorno para la conexión PostgreSQL en Neon.

Ejemplo:

```properties
DB_URL=jdbc:postgresql://host/database
DB_USER=usuario
DB_PASSWORD=password
```

---

# Buenas Prácticas Implementadas

- Arquitectura DAO
- API REST
- Validación de estados
- Autoasignación de técnicos
- Timeline de eventos
- Docker multistage build
- Deploy cloud
- Manejo de archivos adjuntos
- Control de sesiones
- Separación frontend/backend
- Trigger automático SQL
- Restricciones de integridad en PostgreSQL

---

# Autor

Proyecto académico desarrollado para gestión de tickets de soporte técnico.

## Integrantes del Grupo

- Gilma Areli Carrera Samayoa — Carnet: 2490-24-21186
- Heidy Asenet Hernández García — Carnet: 2490-24-17166
- Ana Sofia Hernández Martínez — Carnet: 2490-24-20899
- Roselin Anyeli Bolvito Ortiz — Carnet: 2490-24-8458
- Jordy Andres Luna Flores — Carnet: 2490-24-16009
