FROM maven:3.8.4-amazoncorretto-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn -N install

COPY /charity-microservice/. .

RUN mvn install -DskipTests

FROM openjdk:17-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]