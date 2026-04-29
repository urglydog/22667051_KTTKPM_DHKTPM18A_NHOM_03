# Skill: Dev5 Payment + Notification (EDA)

## Mission

You are implementing only Dev 5 scope in Movie Ticket System:

- payment-service
- notification-service

Do NOT implement User/Movie/Booking/Gateway/Eureka unless explicitly asked.

## Golden Rules

1. No direct HTTP call between core services.
2. Inter-service communication must use RabbitMQ publish/subscribe.
3. Keep Payment and Notification independently deployable with Docker.

## Current Ports

- RabbitMQ AMQP: 5672
- RabbitMQ UI: 15672
- payment-service: 8084
- notification-service: 8086

## RabbitMQ Contract

- Exchange: booking_exchange (topic)
- payment consume:
  - routing key: booking.created
  - queue: payment_queue
- payment publish:
  - payment.completed
  - booking.failed
- notification consume:
  - queue: notification_queue
  - bind keys: payment.completed, booking.failed

## Payloads Used

BOOKING_CREATED:
{
"eventId": "EVT-B-001",
"eventType": "BOOKING_CREATED",
"timestamp": "2026-04-25T18:35:00",
"data": {
"bookingId": "BK-100704",
"userId": "U-22677941",
"cinema": "Galaxy Cinema",
"movieId": "M-AVENGERS",
"seatNumber": "H10",
"amount": 120000.0,
"status": "PENDING"
}
}

PAYMENT_COMPLETED:
{
"eventId": "EVT-P-001",
"eventType": "PAYMENT_COMPLETED",
"timestamp": "2026-04-25T18:35:05",
"data": {
"bookingId": "BK-100704",
"transactionId": "TXN-998877",
"paymentMethod": "MB eBanking",
"amount": 120000.0,
"status": "SUCCESS"
}
}

BOOKING_FAILED:
{
"eventId": "EVT-P-002",
"eventType": "BOOKING_FAILED",
"timestamp": "2026-04-25T18:35:05",
"data": {
"bookingId": "BK-100704",
"reason": "Payment timeout or Insufficient balance",
"status": "FAILED"
}
}

## Runtime Behavior

- Payment consumes BOOKING_CREATED.
- Payment randomly decides SUCCESS/FAILED (70/30).
- Payment publishes PAYMENT_COMPLETED or BOOKING_FAILED.
- Notification listens to both and logs final result.

## Reliability

- Payment listener retry: 3 attempts.
- Backoff: 2 seconds.
- On repeated failure, dead-letter to booking_events_dlq.

## Run Commands

Use compose (foreground):

- docker compose -f docker-compose.payment-notification.yml up --build

Stop:

- Ctrl+C
- docker compose -f docker-compose.payment-notification.yml down

## Demo Checklist

1. Show only RabbitMQ + 2 services running.
2. Publish BOOKING_CREATED to booking_exchange with routing key booking.created.
3. Show payment log (consume + publish).
4. Show notification log (success or fail message).
5. Publish invalid payload (amount <= 0) and show DLQ queue receives message.

## Troubleshooting

If Docker fails with EOF or input/output error:

1. Restart Docker Desktop.
2. Run wsl --shutdown.
3. Ensure enough free space on C drive.
4. Run docker builder prune -af and docker system prune -af --volumes.
