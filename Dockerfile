FROM openjdk:21-jdk-apline
VOLUME /tmp
ARG jarfile=target/*.jar
COPY ${jarfile} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
