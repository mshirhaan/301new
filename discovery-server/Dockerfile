FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/discovery-server-0.0.1-SNAPSHOT app.jar
ENTRYPOINT ["java","-jar","/app.jar"]