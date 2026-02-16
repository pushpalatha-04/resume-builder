# ---------- FRONTEND BUILD ----------
FROM node:20-alpine AS frontend-build

WORKDIR /frontend
COPY resume-frontend-vite/package*.json ./
RUN npm install
COPY resume-frontend-vite/ .
RUN npm run build


# ---------- BACKEND BUILD ----------
FROM eclipse-temurin:17-jdk-alpine AS backend-build

WORKDIR /backend
COPY resume/mvnw .
COPY resume/.mvn .mvn
COPY resume/pom.xml .
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY resume/src src
RUN ./mvnw clean package -DskipTests


# ---------- FINAL STAGE ----------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy backend jar
COPY --from=backend-build /backend/target/*.jar app.jar

# Copy frontend build into Spring Boot static folder
COPY --from=frontend-build /frontend/dist /app/public

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
