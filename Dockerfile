FROM openjdk:11-jdk
MAINTAINER Kwak, Dohyeon <kwakdh25@gmail.com>

COPY ./build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]