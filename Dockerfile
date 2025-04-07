FROM maven:3.9.9-amazoncorretto-23-alpine AS build
LABEL authors="dmazurev"
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:23-jre-ubi9-minimal
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]