FROM  openjdk:8u191-jre-alpine
ARG JAR_FILE=target/MicroService1-0.1.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]