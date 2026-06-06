FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN apk add --no-cache curl

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

COPY --from=build /app/target/SpringMart-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

HEALTHCHECK --interval=30s \
            --timeout=5s \
            --start-period=40s \
            --retries=3 \
CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]