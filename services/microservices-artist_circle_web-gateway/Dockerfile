# -------- BUILD STAGE --------
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /build
COPY pom.xml .
COPY services ./services
COPY common ./common
RUN mvn dependency:go-offline
RUN mvn clean package -pl services/microservices-artist_circle_web-gateway -am -DskipTests

# -------- RUNTIME STAGE --------
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /build/services/microservices-artist_circle_web-gateway/target/*.jar /app/gateway-service.jar
EXPOSE 8890
ENTRYPOINT ["java", "-jar", "gateway-service.jar"]