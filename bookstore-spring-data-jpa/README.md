# Book Store App
[![CI with Maven](https://github.com/ducknowledges/2022-11-otus-spring-kononov/actions/workflows/build.yml/badge.svg)](https://github.com/ducknowledges/2022-11-otus-spring-kononov/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=bookstore-spring-data-jpa&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=bookstore-spring-data-jpa)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=bookstore-spring-data-jpa&metric=coverage)](https://sonarcloud.io/summary/new_code?id=bookstore-spring-data-jpa)

## Goal
- Fully work with Spring Data JPA, it is as simple as possible to write a layer of repositories using modern approaches

## Result
- Application with a layered Spring Data JPA repository

## Description
- Rewrite all repositories for working with books on Spring Data JPA repositories.
- Custom methods of repositories (or with tricky @Query) to cover with tests using H2.
- Use @Transactional only for service methods.

## Demonstration
<a href="https://asciinema.org/a/558711" target="_blank"><img src="https://asciinema.org/a/558711.svg" width="627" /></a>