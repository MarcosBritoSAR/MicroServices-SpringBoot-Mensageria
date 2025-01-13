FROM maven:openjdk as build
WORKDIR /app
COPY . .


FROM openjdk:17
WORKDIR /app
COPY --from=build ./app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]