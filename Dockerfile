FROM ubuntu:latest AS build
LABEL authors="1376694"

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:21-jdk-slim

EXPOSE 8080

COPY --from=build target/to-do-list-0.0.1-SNAPSHOT.jar app.jar
COPY --from=build src/main/resources/application.properties application.properties

ENTRYPOINT ["java", "-jar", "app.jar"]