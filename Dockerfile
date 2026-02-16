# ==============================
# Stage 1: Build React Frontend
# ==============================
FROM node:20-alpine AS frontend-build
WORKDIR /frontend

COPY frontend/package*.json ./
RUN npm install

COPY frontend/ .
RUN npm run build


# ==============================
# Stage 2: Build Spring Boot
# ==============================
FROM eclipse-temurin:17-jdk-alpine AS backend-build
WORKDIR /backend

COPY backend/mvnw .
COPY backend/.mvn .mvn
COPY backend/pom.xml .

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY backend/src src

# Copy React build into Spring Boot static folder
COPY --from=frontend-build /frontend/build src/main/resources/static

RUN ./mvnw clean package -DskipTests


# ==============================
# Stage 3: Run Application
# ==============================
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY --from=backend-build /backend/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
