# Usa una imagen con Java 17
FROM eclipse-temurin:17-jdk

# Crea un directorio en el contenedor
WORKDIR /app

# Copia el archivo .jar al contenedor
COPY target/*.jar app.jar

# Expone el puerto (debe coincidir con el de tu app)
EXPOSE 8080

# Comando para ejecutar tu aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
