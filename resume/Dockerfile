# Use an official Maven + JDK image as the build environment
FROM eclipse-temurin:17-jdk-alpine AS build

# Set working directory inside the container
WORKDIR /app

# Copy Maven wrapper and pom.xml first for caching dependencies
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Give execute permission to Maven wrapper
RUN chmod +x ./mvnw

# Download dependencies (optional, speeds up rebuilds)
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the project
COPY src ./src

# Build the project and skip tests
RUN ./mvnw clean package -DskipTests

# ----------------------------
# Run stage
# ----------------------------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","app.jar"]
