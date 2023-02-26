FROM eclipse-temurin:11.0.18_10-jdk-focal
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/bookstore-spring-mvc-rest-3.0-SNAPSHOT.jar
ADD ${JAR_FILE} bookstore.jar
ENTRYPOINT ["java","-jar","bookstore.jar"]



