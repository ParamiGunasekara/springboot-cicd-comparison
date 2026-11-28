
# GitHub Actions CI/CD Implementation

## 1. Project Overview

The Incident Reporting System is a Spring Boot REST API developed to evaluate and compare different CI/CD platforms using a common application and deployment environment.

GitHub Actions is the first CI/CD platform implemented in this project.

## 2. Technology Stack

| Component | Technology |
|---|---|
| Programming Language | Java 25 |
| Application Framework | Spring Boot |
| Build Tool | Maven Wrapper |
| Database | H2 In-Memory Database |
| Automated Testing | JUnit |
| Code Quality | Checkstyle |
| Containerization | Docker |
| CI/CD Platform | GitHub Actions |
| Container Registry | GitHub Container Registry (GHCR) |
| Cloud Infrastructure | AWS EC2 |
| Remote Deployment | AWS Systems Manager (SSM) |
| Authentication | AWS IAM |

## 3. CI/CD Architecture

Developer → GitHub Repository → GitHub Actions CI → GHCR → GitHub Actions CD → AWS SSM → AWS EC2 → Application Verification

## 4. Continuous Integration Pipeline

The CI pipeline performs the following operations:

1. Checkout application source code.
2. Configure Java 25.
3. Restore cached Maven dependencies.
4. Configure Maven Wrapper execution permissions.
5. Perform Checkstyle code quality validation.
6. Build the Spring Boot application.
7. Execute automated tests.
8. Generate the executable JAR file.
9. Upload JAR and test report artifacts.
10. Build the Docker image using the verified JAR.
11. Inspect the Docker image.
12. Start a temporary application container.
13. Verify application health.
14. Execute REST API smoke tests.
15. Collect container logs and clean up.
16. Publish Docker images to GHCR.

## 5. Continuous Deployment Pipeline

The deployment pipeline performs the following operations:

1. Wait for successful CI completion.
2. Authenticate with AWS using GitHub Secrets.
3. Select the Docker image using the Git commit SHA.
4. Send deployment commands through AWS SSM.
5. Pull the selected Docker image on EC2.
6. Remove the previous application container if present.
7. Start the updated application container.
8. Perform application readiness verification.
9. Retrieve deployment results from AWS SSM.
10. Fail the pipeline if deployment is unsuccessful.

Deployment is restricted to the main branch and does not execute for pull request events.

## 6. AWS Deployment Environment

| Configuration | Value |
|---|---|
| Cloud Provider | AWS |
| Instance Type | t3.micro |
| Operating System | Amazon Linux |
| AWS Region | ap-southeast-2 |
| Deployment Method | AWS SSM Run Command |
| Container Name | incident-reporting-app |
| Host Port | 8081 |
| Container Port | 8080 |
| Network Binding | 127.0.0.1 |
| Container Memory Limit | 384 MB |
| JVM Initial Heap | 64 MB |
| JVM Maximum Heap | 192 MB |
| Restart Policy | unless-stopped |

The application is accessible locally on the EC2 instance without exposing its REST API directly to the public internet.

## 7. Performance Measurements

### Successful Baseline Execution

- Date: November 28, 2026
- Commit: 2cccf67
- Trigger: Push to main branch
- Workflow Status: Success

| Performance Metric | Observed Result |
|---|---|
| Total Workflow Duration | 78 seconds |
| CI Job Duration | 43 seconds |
| CD Job Duration | 29 seconds |
| Automated Maven Tests | 4 passed |
| Test Failures | 0 |
| Generated Artifacts | 2 |
| Docker Image Publishing | Successful |
| AWS EC2 Deployment | Successful |
| Application Health | UP |
| REST API Verification | Successful |

These measurements represent one successful baseline execution. Additional executions are required to calculate reliable average performance.

## 8. Deployment Verification

The following commands were executed on AWS EC2.

### Verify Running Container

    sudo docker ps

Result: The incident-reporting-app container was running successfully using a commit SHA-tagged Docker image.

### Verify Application Health

    curl http://localhost:8081/actuator/health

Response:

    {"groups":["liveness","readiness"],"status":"UP"}

### Verify Incident API

    curl http://localhost:8081/api/incidents

Response:

    []

The empty JSON array confirms that the API responds successfully and currently contains no incident records.

## 9. Security Practices

- AWS credentials are stored in GitHub Secrets.
- A dedicated IAM user is used for deployment.
- A custom IAM policy limits deployment commands to the selected EC2 instance and SSM document.
- Docker containers run using a non-root application user.
- Application deployment uses AWS SSM without requiring additional inbound SSH access for GitHub Actions.
- Docker images are tagged using Git commit SHA values for traceability.
- Deployment is restricted to the main branch.
- The application port is bound to the EC2 loopback interface.
- Container memory limits are configured to reduce resource exhaustion risks.

## 10. Troubleshooting

| Issue | Cause | Resolution |
|---|---|---|
| Maven build failure | Running Java process locked the JAR file on Windows | Stopped the Java process and rebuilt the application |
| Local port conflict | Port 8080 was occupied by another application | Used host port 8081 |
| Checkstyle failure | Wildcard imports violated code quality rules | Replaced wildcard imports with explicit imports |
| Container health check failure | Application was not ready when the initial health request was made | Implemented a retry-based readiness check |
| GitHub Actions deprecation warnings | Actions targeted deprecated Node.js versions | Upgraded the affected actions |
| AWS OIDC unavailable | AWS account feature restrictions | Used a dedicated IAM user with restricted permissions |
| Limited EC2 memory | Small EC2 instance capacity | Configured container and JVM memory limits |

## 11. Pipeline Optimization

A dedicated Dockerfile.ci was introduced to reuse the JAR generated during the Maven build.

This approach avoids rebuilding and retesting the application inside the Docker image build stage.

Additional optimizations include:

- Maven dependency caching.
- Restricted Docker build context.
- Runtime-only Java container image.
- Automated temporary container cleanup.
- Commit SHA-based image selection.

## 12. Current Limitations

- Long-term IAM access keys are used because OIDC is unavailable under the current AWS account configuration.
- The AWS-RunShellScript document permits shell command execution on the selected instance and requires careful credential protection.
- Automatic deployment rollback is not implemented.
- The H2 in-memory database does not preserve incident records across application restarts.
- The application does not currently implement production-grade authentication.
- The container image is publicly available through GHCR.
- Deployment requires the EC2 instance to be running.
- The recorded performance measurements represent a single baseline execution.
- Production-grade monitoring and alerting are not implemented.

## 13. Future Improvements

- Replace IAM access keys with OIDC when supported.
- Implement automatic deployment rollback.
- Introduce persistent database storage.
- Add container vulnerability scanning.
- Implement monitoring and alerting.
- Collect multiple pipeline execution measurements.
- Compare equivalent implementations across CI/CD platforms.

## 14. CI/CD Platform Comparison Progress

| Platform | Implementation Status |
|---|---|
| GitHub Actions | Completed |
| GitLab CI/CD | Pending |
| Jenkins | Pending |
| Azure DevOps | Pending |

## 15. Conclusion

The GitHub Actions implementation successfully demonstrates automated application building, testing, code quality validation, containerization, image publishing, cloud deployment and application health verification.

This implementation establishes the initial baseline for comparing CI/CD platforms using the same Spring Boot application.
