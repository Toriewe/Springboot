# Imagen base con Maven y Java
FROM maven:3.9.3-eclipse-temurin-17 AS build

# Crea directorio de trabajo
WORKDIR /app

# Copia los archivos al contenedor
COPY pom.xml .
COPY src ./src

# Empaqueta la app (esto genera el .jar)
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copia el .jar desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto
EXPOSE 8080

# Ejecuta la app
ENTRYPOINT ["java", "-jar", "app.jar"]
