FROM eclipse-temurin:11.0.18_10-jdk-focal
COPY .mvn .mvn
COPY src src
COPY ui ui
COPY pom.xml pom.xml
COPY mvnw mvnw
RUN ./mvnw package
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/bookstore-spring-mvc-rest-3.0-SNAPSHOT.jar
ADD ${JAR_FILE} bookstore.jar
ENTRYPOINT ["java","-jar","bookstore.jar"]



