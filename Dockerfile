#FROM mcr.microsoft.com/playwright/java:v1.58.0-jammy
#RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*
#WORKDIR /app
#COPY . .
#RUN mvn dependency:go-offline
#RUN mvn test-compile
#ENTRYPOINT ["mvn", "test", "-Dtest=SampleTest", "-Dheadless=true"]

# --- Stage 1: Build Stage ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
# Copy only pom first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
# Build the project (we'll run tests in the next stage)
RUN mvn clean compile test-compile

# --- Stage 2: Runtime Stage ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# 1. Install ONLY the system dependencies for Chromium
# This avoids installing Firefox/WebKit bloat
RUN apt-get update && apt-get install -y --no-install-recommends \
    libnss3 libnspr4 libasound2 libatk1.0-0 libc6 libdbus-1-3 \
    libexpat1 libfontconfig1 libgbm1 libgcc1 libgconf-2-4 \
    libgdk-pixbuf2.0-0 libglib2.0-0 libgtk-3-0 libpango-1.0-0 \
    libx11-6 libx11-xcb1 libxcb1 libxcomposite1 libxcursor1 \
    libxdamage1 libxext6 libxfixes3 libxi6 libxrandr2 libxrender1 \
    libxss1 libxtst6 wget ca-certificates \
    && rm -rf /var/lib/apt/lists/*

# 2. Install Maven (needed to run tests from src/test)
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# 3. Copy compiled code and dependencies from build stage
COPY --from=build /root/.m2 /root/.m2
COPY --from=build /app /app

# 4. Tell Playwright to ONLY download Chromium
ENV PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD=1
RUN mvn exec:java -e -Dexec.mainClass="com.microsoft.playwright.CLI" -Dexec.args="install chromium"

# Run the test
ENTRYPOINT ["mvn", "test", "-Dtest=SampleTest", "-Dheadless=true"]