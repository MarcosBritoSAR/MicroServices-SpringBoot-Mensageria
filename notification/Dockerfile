FROM openjdk:17
WORKDIR /app
COPY ./target/*.jar app.jar
EXPOSE 8085

ENV RABBITMQ_SERVER=rabbitmq
ENV RABBITMQ_PORT=5672
ENV RABBITMQ_NAME=uptade-response-email


ENTRYPOINT ["java", "-jar", "app.jar"]