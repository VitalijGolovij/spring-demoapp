FROM openjdk:17-jdk-alpine
MAINTAINER Vitaliy Goloviy
COPY target/demoapp-0.0.1-SNAPSHOT.jar demoapp.jar
ENTRYPOINT ["java","-jar","/demoapp.jar"]