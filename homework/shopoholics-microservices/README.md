# Shopoholics Microservices

Микросервисная архитектура для интернет-магазина с асинхронным взаимодействием между сервисами.

## Структура проекта

- `payments-service` - Сервис управления счетами пользователей
- `orders-service` - Сервис управления заказами
- `docker-compose.yml` - Файл для запуска всех сервисов

## Требования

- Docker
- Docker Compose

## Запуск

```bash
docker-compose up -d
```

## Сервисы

### Payments Service (порт 8081)

- Создание счета: `POST /api/payments/accounts?userId={userId}`
- Пополнение счета: `POST /api/payments/accounts/{userId}/deposit?amount={amount}`
- Просмотр баланса: `GET /api/payments/accounts/{userId}/balance`

### Orders Service (порт 8082)

- Создание заказа: `POST /api/orders?userId={userId}&amount={amount}`
- Просмотр заказов пользователя: `GET /api/orders/user/{userId}`
- Просмотр заказа по ID: `GET /api/orders/{orderId}`

## Асинхронное взаимодействие

При создании заказа в Orders Service автоматически инициируется процесс оплаты через Kafka.