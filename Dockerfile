FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean compile test-compile


FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

RUN apt-get update && apt-get install -y --no-install-recommends \
    libnss3 libnspr4 libasound2 libatk1.0-0 libc6 libdbus-1-3 \
    libexpat1 libfontconfig1 libgbm1 libgcc1 libgconf-2-4 \
    libgdk-pixbuf2.0-0 libglib2.0-0 libgtk-3-0 libpango-1.0-0 \
    libx11-6 libx11-xcb1 libxcb1 libxcomposite1 libxcursor1 \
    libxdamage1 libxext6 libxfixes3 libxi6 libxrandr2 libxrender1 \
    libxss1 libxtst6 wget ca-certificates \
    && rm -rf /var/lib/apt/lists/* && apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

COPY --from=build /root/.m2 /root/.m2
COPY --from=build /app /app

ENV PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD=1
RUN mvn exec:java -e -Dexec.mainClass="com.microsoft.playwright.CLI" -Dexec.args=" install --only-shell chromium"

ENTRYPOINT ["mvn", "test", "-Dheadless=true"]