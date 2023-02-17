# Book Store App
[![CI with Maven](https://github.com/ducknowledges/2022-11-otus-spring-kononov/actions/workflows/build.yml/badge.svg)](https://github.com/ducknowledges/2022-11-otus-spring-kononov/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=bookstore-spring-orm-jpa&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=bookstore-spring-orm-jpa)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=bookstore-spring-orm-jpa&metric=coverage)](https://sonarcloud.io/summary/new_code?id=bookstore-spring-orm-jpa)

## Goal
- Fully work with JPA + Hibernate to connect to relational databases through an ORM framework

## Result
- High-level application with JPA entity mapping

## Description
- Use JPA, Hibernate only as a JPA provider.
- Solve the N+1 problem.
- Add a "book comment" entity, implement CRUD for the new entity.
- Cover the repositories with tests using an H2 database and the corresponding H2 Hibernate dialect for tests.
- Use @Transactional only for service methods.

## Demonstration
<a href="https://asciinema.org/a/557891" target="_blank"><img src="https://asciinema.org/a/557891.svg" width="627"  /></a>