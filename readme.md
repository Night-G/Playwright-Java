# Playwright Java Test Automation Framework

This project is a Java-based Playwright test automation framework.  
It supports multiple execution methods: **local Maven**, **Jenkins CI**, **Docker**, and **Docker in Jenkins (CI/CD best practice)**.

---

## 🔧 Prerequisites

### Local Maven Execution
- Java 17+
- Maven 3.9+
- Node.js 18+
- Playwright browsers installed:
  - npx:
    ```bash
    npx playwright install
  - mvn:
    ```bash
    mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install --with-deps"
### Local Docker Execution
- docker desktop running
---
## Executions: 
- local maven (can `-DHEADLESS=false/true` can be added, check `default-values.properties`) :
    ```bash
    mvn clean test
- local docker (powershell 7):
    ```bash
  docker build -t playwright-temp . && docker run --rm playwright-temp; docker rmi playwright-temp
- local docker (powershell 5.1):
    ```bash
    docker build -t playwright-temp . ; if ($?) { docker run --rm playwright-temp };  if ($?) {docker rmi playwright-temp}
- jenkins maven|docker can use the same or similar commands in the `Build Steps`