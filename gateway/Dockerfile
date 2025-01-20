FROM openjdk:17
WORKDIR /app
COPY ./target/*.jar app.jar
EXPOSE 8080
ENV EUREKA_SERVER=eureka
ENTRYPOINT ["java","-jar","app.jar"]