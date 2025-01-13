
FROM maven:openjdk AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app
COPY --from=build ./app/target/*.jar app.jar
EXPOSE 8085

ARG RABBITMQ_SERVER=rabbitmq
ARG RABBITMQ_PORT=5672
ARG RABBITMQ_NAME=uptade-response-email

ENTRYPOINT ["java", "-jar", "app.jar"]