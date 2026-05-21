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

# Copiar el WAR generado en la etapa anterior
COPY --from=build /app/target/*.war ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
