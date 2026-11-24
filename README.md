
# Spring Boot CI/CD Pipeline Comparison

## Project Overview

A hands-on DevOps project comparing four CI/CD platforms
using a Spring Boot Incident Reporting System.

## CI/CD Platforms

- GitHub Actions
- GitLab CI/CD
- Jenkins
- Azure DevOps

## Technology Stack

- Java 25
- Spring Boot
- Maven Wrapper
- Spring Data JPA
- H2 Database
- REST API
- JUnit
- Docker (planned)

## Application Features

- Create and retrieve incidents
- Update incident status
- Update incident severity
- Input validation
- Application health monitoring

## Local Build

Windows:

    cd incident-reporting-system
    .\mvnw.cmd -B clean verify

## Run Application

    java -jar target/incident-reporting-system-0.0.1-SNAPSHOT.jar

Health endpoint:

    http://localhost:8080/actuator/health

## Verified Test Results

- Total tests: 4
- Failures: 0
- Errors: 0
- Success rate: 100%

## Project Objectives

- Implement automated CI/CD pipelines
- Build and test a Spring Boot application
- Containerize the application
- Automate application deployment
- Compare pipeline performance and usability

## Project Status

Work in progress.
