# Etapa de construcción (build)
FROM maven:3.9.3-eclipse-temurin-17 AS build

# Crea el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo pom.xml y descarga las dependencias (para aprovechar cache de Docker)
COPY pom.xml .

# Descarga dependencias sin compilar aún
RUN mvn dependency:go-offline

# Luego copiamos el resto del proyecto
COPY src ./src

# Compila y empaqueta la aplicación sin ejecutar los tests
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jdk

# Directorio de trabajo en el contenedor final
WORKDIR /app

# Copiamos el JAR desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto que usará la app (por defecto Spring usa 8080)
EXPOSE 8080

# Ejecutamos el JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
