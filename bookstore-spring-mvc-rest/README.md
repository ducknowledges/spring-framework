# Book Store App
[![CI with Maven](https://github.com/ducknowledges/2022-11-otus-spring-kononov/actions/workflows/build.yml/badge.svg)](https://github.com/ducknowledges/2022-11-otus-spring-kononov/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=bookstore-spring-mvc-rest&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=bookstore-spring-mvc-rest)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=bookstore-spring-mvc-rest&metric=coverage)](https://sonarcloud.io/summary/new_code?id=bookstore-spring-mvc-rest)

## Goal
- Develop modern AJAX/SPA applications with Spring MVC

## Result
- Modern application on the Spring stack + Vue.js SPA

## Description
- Frontend based on SPA architecture
- Backend based on REST controllers
- FlyWay DB Migration
- H2 Data base
- Docker containerization

## Stack
- Back-end: Java 11, Spring Boot, DB H2, FlyWay migration
- Front-end: Vue.js, Bootstrap
- Code quality: Checkstyle, Jacoco Test Coverage, SonarQube

## Demonstration

### Deployed
- [Bookstore](http://ec2-34-217-66-66.us-west-2.compute.amazonaws.com/)
- [Swagger-UI](http://ec2-34-217-66-66.us-west-2.compute.amazonaws.com/swagger-ui/index.html)

### Local

#### Clone
```bash
#ssh
git clone git@github.com:ducknowledges/2022-11-otus-spring-kononov.git
```
OR

```bash
#https
git clone https://github.com/ducknowledges/2022-11-otus-spring-kononov.git
```

#### Build and Run with JVM
```bash
cd 2022-11-otus-spring-kononov/bookstore-spring-mvc-rest
./mvnw clean package
./target/bookstore-spring-mvc-rest-3.0-SNAPSHOT.jar
```

#### Run with PODMAN
```bash
podman build -t bookstore-image .
podman run --rm --name bookstore-container -dp 8080:8080 bookstore-image
```

OR

#### Run with DOCKER
```bash
docker build -t bookstore-image .
docker run --rm --name bookstore-container -dp 8080:8080 bookstore-image
```

#### Open
http://localhost:8080/
