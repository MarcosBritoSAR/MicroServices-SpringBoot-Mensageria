FROM maven:openjdk as build
WORKDIR /app
COPY . .
RUN mvn clean package -DstipTests

FROM openjdk:17
WORKDIR /app
COPY --from=build ./app/target/*.jar app.jar
EXPOSE 8085

ARG RABBITMQ_SERVER=rabbitmq

ENTRYPOINT ["java", "-jar", "app.jar"]