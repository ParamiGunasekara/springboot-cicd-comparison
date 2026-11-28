
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

## CI/CD Implementation Progress

| CI/CD Platform | Status |
|---|---|
| GitHub Actions | Completed |
| GitLab CI/CD | Planned |
| Jenkins | Planned |
| Azure DevOps | Planned |

## GitHub Actions CI/CD Pipeline

[![Spring Boot CI](https://github.com/ParamiGunasekara/springboot-cicd-comparison/actions/workflows/ci.yml/badge.svg)](https://github.com/ParamiGunasekara/springboot-cicd-comparison/actions/workflows/ci.yml)

The automated pipeline includes:

- Maven build and automated testing
- Checkstyle code quality validation
- JAR and test report artifact generation
- Optimized Docker image building
- Container health checks and API smoke testing
- Docker image publishing to GHCR
- AWS authentication using GitHub Secrets
- Automated EC2 deployment using AWS SSM
- Post-deployment health verification

## Pipeline Architecture

Developer → GitHub → GitHub Actions CI → GHCR → GitHub Actions CD → AWS SSM → AWS EC2

## Baseline Performance Results

| Metric | Result |
|---|---|
| Total Pipeline Duration | 78 seconds |
| Continuous Integration | 43 seconds |
| Continuous Deployment | 29 seconds |
| Automated Tests | 4 passed |
| Deployment Status | Successful |
| Application Health | UP |

These results represent one successful baseline execution.

## Docker Container Registry

The application image is available through GitHub Container Registry.

    docker pull ghcr.io/paramigunasekara/incident-reporting-system:latest

## Cloud Deployment

The application is automatically deployed to an AWS EC2 instance using AWS Systems Manager Run Command.

The deployed API is bound to the server's loopback interface for security.

## Documentation

- [GitHub Actions Implementation and Performance Results](docs/github-actions-results.md)

## Current Limitations

- H2 in-memory database
- No automatic deployment rollback
- Long-term IAM credentials due to AWS account restrictions
- Single baseline performance measurement
- No public API access

## Next Steps

- Implement GitLab CI/CD
- Implement Jenkins pipeline
- Implement Azure DevOps pipeline
- Collect and compare performance measurements
- Evaluate security, usability and maintenance requirements
