FROM maven:openjdk AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app
COPY --from=build ./app/target/*.jar app.jar
EXPOSE 8081

ENV EUREKA_SERVER=eureka

ENV RABBITMQ_SERVER=rabbitmq
ENV RABBITMQ_PORT=5672
ENV RABBITMQ_SERVER=uptade-response-email

ENV POSTGRES_SERVER=postgres
ENV POSTGRES_PORT=5432
ENV POSTGRES_USER=brito
ENV POSTGRES_PASSWORD=1234567


ENTRYPOINT ["java","-jar","app.jar"]