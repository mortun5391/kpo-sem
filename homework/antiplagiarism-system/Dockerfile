# Multi-stage build Dockerfile
FROM eclipse-temurin:17-jdk-alpine as builder

# Устанавливаем необходимые пакеты
RUN apk add --no-cache bash

# Рабочая директория
WORKDIR /workspace

# Копируем файлы сборки Kotlin DSL
COPY gradlew .
COPY gradle ./gradle/
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY shared ./shared/
COPY api-gateway ./api-gateway/
COPY file-storing-service ./file-storing-service/
COPY analysis-service ./analysis-service/

# Даем права на выполнение gradlew
RUN chmod +x gradlew

# Скачиваем зависимости и собираем проект
RUN ./gradlew clean build -x test --no-daemon

# Финальный этап для каждого сервиса
FROM eclipse-temurin:17-jre-alpine

# Устанавливаем curl для healthcheck
RUN apk add --no-cache curl

# Аргументы для каждого сервиса
ARG SERVICE_NAME
ARG SERVICE_PORT

WORKDIR /app

# Копируем собранный JAR из builder этапа
COPY --from=builder /workspace/${SERVICE_NAME}/build/libs/*.jar app.jar

# Открываем порт
EXPOSE ${SERVICE_PORT}

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]