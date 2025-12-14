# Система проверки работ на плагиат (Antiplagiarism System)

## 📋 Описание проекта

Микросервисная система для проверки студенческих работ на плагиат. Система позволяет:
- Загружать работы студентов с метаданными
- Автоматически проверять работы на наличие заимствований
- Генерировать облака слов для визуализации содержания работ
- Получать детальные отчеты о проверке

## 🏗️ Архитектура системы

Система построена по микросервисной архитектуре с четким разделением ответственности.

### Компоненты системы

1. **API Gateway (Порт: 8080)** — единая точка входа для всех клиентов
    - Маршрутизация запросов к соответствующим сервисам
    - Агрегация данных из нескольких сервисов
    - Circuit Breaker для обработки отказов

2. **File Storing Service (Порт: 8081)** — сервис хранения файлов
    - Прием и хранение файлов работ
    - Управление метаданными работ
    - Валидация файлов (размер, тип)

3. **Analysis Service (Порт: 8082)** — сервис анализа
    - Проверка текста на плагиат
    - Генерация отчетов
    - Создание облаков слов
    - Хранение результатов анализа

4. **База данных** — PostgreSQL 15
    - Хранение метаданных работ
    - Хранение отчетов анализа

5. **Кэш** — Redis 7
    - Кэширование часто запрашиваемых данных
    - Хранение URL облаков слов

### Технологический стек

- Java 17, Spring Boot 3.1.5
- Spring Cloud Gateway
- PostgreSQL 15, Redis 7
- Docker, Docker Compose
- Flyway (миграции БД)
- Swagger / OpenAPI

## 📊 Алгоритм обнаружения плагиата

### Метод шинглов (Shingle-based approach)

**Принцип работы:**

1. **Препроцессинг текста**
    - Приведение к нижнему регистру
    - Удаление пунктуации и специальных символов
    - Удаление стоп-слов (предлогов, союзов)
    - Нормализация пробелов

2. **Генерация шинглов**
    - Текст разбивается на последовательные N-граммы (шинглы)
    - Размер шингла по умолчанию: 3 слова
    - Пример: `The quick brown fox` → `["the quick brown", "quick brown fox"]`

3. **Сравнение с предыдущими работами**
    - Для каждой предыдущей работы вычисляется множество шинглов
    - Текущая работа сравнивается со всеми предыдущими
    - Используется мера Жаккара

4. **Определение плагиата**
    - Пороговое значение: **0.7 (70%)**
    - Если схожесть ≥ 0.7 → работа помечается как плагиат
    - Формула Жаккара:
      ```text
      J(A, B) = |A ∩ B| / |A ∪ B|
      ```

**Преимущества алгоритма:**
- Устойчив к незначительным изменениям текста
- Учитывает последовательность слов
- Быстро работает на больших объемах данных

## 🚀 Быстрый старт

### Предварительные требования

- Docker 20.10+
- Docker Compose 2.0+
- 4 GB свободной оперативной памяти

### Запуск системы

1. **Клонирование репозитория**

```bash
git clone <repository-url>
cd antiplagiarism-system
```

2. **Запуск всех сервисов**

```bash
docker-compose up --build
```

3. **Проверка работоспособности**

```bash
# API Gateway
curl http://localhost:8080/actuator/health

# File Service
curl http://localhost:8081/actuator/health

# Analysis Service
curl http://localhost:8082/actuator/health
```

### Swagger UI

- API Gateway: http://localhost:8080/swagger-ui.html
- File Service: http://localhost:8081/swagger-ui.html
- Analysis Service: http://localhost:8082/swagger-ui.html

## 📡 API Endpoints

### Основные эндпоинты (через API Gateway)

#### 1. Загрузка работы

```http
POST /api/files/upload
Content-Type: multipart/form-data
```

Параметры:
- `file` — файл работы (txt, pdf, docx)
- `studentId` — идентификатор студента
- `assignmentId` — идентификатор задания

#### 2. Получение информации о работе

```http
GET /api/works/{workId}
```

#### 3. Запуск анализа

```http
POST /api/analyze
Content-Type: application/json

{
  "workId": 1
}
```

#### 4. Получение отчетов

```http
GET /api/works/{workId}/reports
```

#### 5. Генерация облака слов

```http
POST /api/wordcloud
GET  /api/wordcloud/{workId}
```

#### 6. Получение отчета

```http
GET /api/reports/{reportId}
```

## 🔧 Сценарии взаимодействия

### Сценарий 1: Сдача работы студентом

1. Клиент → API Gateway: `POST /api/files/upload`
2. API Gateway → File Service: `POST /api/v1/files/upload`
3. File Service:
    - Сохраняет файл
    - Записывает метаданные в БД
    - Возвращает `workId`
4. File Service → Analysis Service: `POST /api/v1/analyze` (асинхронно)
5. Analysis Service:
    - Создает отчет
    - Анализирует текст
    - Сохраняет результат

### Сценарий 2: Получение отчетов преподавателем

1. Клиент → API Gateway: `GET /api/works/{id}/reports`
2. API Gateway:
    - Запрашивает данные у File Service
    - Запрашивает отчеты у Analysis Service
3. API Gateway агрегирует и возвращает ответ

### Сценарий 3: Генерация облака слов

1. Клиент → API Gateway: `POST /api/wordcloud`
2. API Gateway → Analysis Service
3. Analysis Service:
    - Извлекает текст
    - Генерирует облако слов (QuickChart)
    - Кэширует URL в Redis
    - Возвращает ссылку

## 🐳 Docker контейнеры

### Порты

- 8080 — API Gateway
- 8081 — File Storing Service
- 8082 — Analysis Service
- 5432 — PostgreSQL
- 6379 — Redis

### Docker volumes

- `postgres_data` — данные PostgreSQL
- `file_storage` — загруженные файлы

### Управление контейнерами

```bash
# Запуск в фоне
docker-compose up -d

# Логи
docker-compose logs -f

# Остановка
docker-compose down

# Остановка с удалением данных
docker-compose down -v
```

## 🧪 Тестирование

### Postman

Импортируйте файл `AntiPlagiarism.postman_collection.json` в Postman.

### Основные проверки

- Загрузка файла и получение `workId`
- Проверка статуса анализа
- Получение отчетов
- Проверка флага плагиата
- Генерация облака слов

### Ручное тестирование

```bash
curl -X POST http://localhost:8080/api/files/upload \
  -F "file=@document.pdf" \
  -F "studentId=student123" \
  -F "assignmentId=assignment-1"
```

## ⚠️ Обработка ошибок

- Circuit Breaker (Resilience4j)
- Fallback endpoints:
    - `/fallback/works`
    - `/fallback/reports`
    - `/fallback/aggregate`

### Коды ошибок

- 400 — Некорректный запрос
- 404 — Ресурс не найден
- 413 — Превышен размер файла
- 415 — Неподдерживаемый тип файла
- 503 — Сервис недоступен

## 📈 Мониторинг и логирование

- `/actuator/health` для всех сервисов
- Docker healthchecks
- Централизованные логи через `docker-compose logs`

## 🔄 Миграции базы данных

- Flyway миграции применяются автоматически
- Валидация схемы при запуске

### Основные таблицы

- `works` — метаданные и статусы работ
- `reports` — результаты анализа (JSON)

## 🎯 Критерии выполнения

Реализовано:
- 3 микросервиса + API Gateway
- Docker Compose
- Circuit Breaker
- Алгоритм плагиата
- Облака слов
- Swagger документация
- Полный пользовательский сценарий