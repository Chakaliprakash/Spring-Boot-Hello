# Stage 1: Build the Spring Boot JAR using Maven
FROM maven:3.9.2-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the JAR (skip tests for faster build)
RUN mvn clean package -DskipTests

# Stage 2: Run the app using lightweight Java image
FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copy the built JAR from the Maven stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 for Render
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","app.jar"]
