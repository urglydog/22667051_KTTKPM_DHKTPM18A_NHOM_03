# Service Handoff - Payment + Notification

Owner: Luong Minh Tan (MSSV 22677941)
Scope: Dev 5 only

## 1) What is included

- payment-service
- notification-service
- docker-compose.payment-notification.yml

Optional/legacy folders may exist, but integration only needs the 3 items above.

## 2) Architecture summary

Flow:
Booking Service -> publish BOOKING_CREATED -> RabbitMQ -> Payment Service -> publish PAYMENT_COMPLETED or BOOKING_FAILED -> RabbitMQ -> Notification Service

No direct HTTP call between Booking/Payment/Notification.

## 3) Technical contracts

Exchange and keys:

- exchange: booking_exchange
- booking.created -> payment_queue
- payment.completed -> notification_queue
- booking.failed -> notification_queue

DLQ:

- dlx: booking_dlx
- dlq: booking_events_dlq
- retry: 3 attempts, backoff 2s

## 4) Where to look in code

Payment config and listener:

- payment-service/src/main/resources/application.yml
- payment-service/src/main/java/com/movieticket/paymentservice/config/RabbitMQConfig.java
- payment-service/src/main/java/com/movieticket/paymentservice/listener/BookingCreatedListener.java
- payment-service/src/main/java/com/movieticket/paymentservice/service/PaymentProcessingService.java

Notification listener:

- notification-service/src/main/resources/application.yml
- notification-service/src/main/java/com/movieticket/notificationservice/config/RabbitMQConfig.java
- notification-service/src/main/java/com/movieticket/notificationservice/listener/PaymentResultListener.java

## 5) Run on another machine

Prerequisites:

- Docker Desktop
- free disk space on C drive
- ports free: 5672, 15672, 8084, 8086

Run (foreground):

- docker compose -f docker-compose.payment-notification.yml up --build

Run (background):

- docker compose -f docker-compose.payment-notification.yml up --build -d

Stop:

- docker compose -f docker-compose.payment-notification.yml down

## 6) How to test quickly

1. Open RabbitMQ UI: http://localhost:15672 (guest/guest)
2. Go to Exchanges -> booking_exchange
3. Publish with routing key booking.created
4. Use JSON payload BOOKING_CREATED
5. Check logs:
   - payment-service logs consume and processing result
   - notification-service logs success/fail message

## 7) DLQ test

Publish invalid BOOKING_CREATED where data.amount = -1.
Expected:

- payment throws validation error
- retries 3 times
- message goes to booking_events_dlq

## 8) Common issues and fixes

Issue: container name conflict (rabbitmq already exists)
Fix: do not hard-code container_name in compose.

Issue: Docker EOF / input-output / cannot start daemon
Fix:

1. Restart Docker Desktop
2. wsl --shutdown
3. prune docker cache
4. check disk space and retry

Issue: services exit immediately
Fix: inspect logs with:

- docker compose -f docker-compose.payment-notification.yml logs --tail=200 payment-service
- docker compose -f docker-compose.payment-notification.yml logs --tail=200 notification-service

## 9) Integration notes for team

- Booking team must publish exactly to booking_exchange with booking.created.
- Payload field names must match contract keys.
- Notification does not call external APIs now; it logs to stdout for demo.
