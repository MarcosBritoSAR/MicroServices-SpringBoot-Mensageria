FROM maven:openjdk as build
WORKDIR /app
COPY . .
RUN mvn clean package -DstipTests


FROM openjdk:17

WORKDIR /app

COPY target/eureka-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]