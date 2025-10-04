# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (caches if unchanged)
RUN ./mvnw dependency:go-offline -B

# Copy the project source
COPY src ./src

# Build the application
RUN ./mvnw package -DskipTests

# Expose the port Spring Boot will run on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
