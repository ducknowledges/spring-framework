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

## Demonstration

### In browser

http://ec2-54-218-121-204.us-west-2.compute.amazonaws.com/

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