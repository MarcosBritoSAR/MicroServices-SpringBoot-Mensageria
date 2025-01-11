FROM maven:3.9.9-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DstipTests



FROM openjdk:17
WORKDIR /app
COPY --from=build ./app/target*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]