# Imagen base: Tomcat 10.1.36 con JDK 21
FROM tomcat:10.1.36-jdk21-openjdk

# Establecemos directorio de trabajo
WORKDIR /usr/local/tomcat/webapps/

# Copiamos tu WAR generado por Maven/NetBeans al directorio ROOT
COPY target/*.war ROOT.war

# Exponemos el puerto 8080 (Render lo mapeará automáticamente)
EXPOSE 8080

# Comando de inicio: Tomcat arranca solo
CMD ["catalina.sh", "run"]
