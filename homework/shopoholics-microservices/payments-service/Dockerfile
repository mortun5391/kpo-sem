# Stage 1: Build the application
FROM gradle:8.5-jdk17-alpine AS build

WORKDIR /app

# Copy gradle files
COPY gradlew .
COPY gradle gradle
COPY settings.gradle.kts .
COPY gradle.properties .
COPY build.gradle.kts .
COPY payments-service/build.gradle.kts payments-service/build.gradle.kts

# Copy source code
COPY payments-service/src payments-service/src

# Build the application using gradle command directly
RUN gradle :payments-service:bootJar --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the jar file from build stage
COPY --from=build /app/payments-service/build/libs/*.jar app.jar

# Expose port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]