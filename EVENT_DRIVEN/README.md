# Movie Ticket System - Event Driven Architecture

Hệ thống đặt vé xem phim được triển khai theo kiến trúc Event-Driven Architecture với RabbitMQ, Eureka và Spring Cloud Gateway.

## Thành phần

- `discovery-server`: Eureka Server
- `api-gateway`: cổng vào duy nhất cho frontend
- `user-service`: đăng ký / đăng nhập, publish `USER_REGISTERED`
- `movie-service`: xem và quản lý danh sách phim
- `booking-service`: tạo booking, publish `BOOKING_CREATED`, nhận kết quả thanh toán để cập nhật trạng thái
- `payment-service`: consume `BOOKING_CREATED`, xử lý giả lập success/fail, publish `PAYMENT_COMPLETED` hoặc `BOOKING_FAILED`
- `notification-service`: consume kết quả thanh toán và in thông báo
- `frontend`: React UI test qua API Gateway

## Luồng event

1. User đăng ký qua `user-service` hoặc qua gateway.
2. User chọn phim và tạo booking qua `booking-service` hoặc qua gateway.
3. `booking-service` phát `BOOKING_CREATED`.
4. `payment-service` nhận event và xử lý thanh toán giả lập.
5. `payment-service` phát `PAYMENT_COMPLETED` hoặc `BOOKING_FAILED`.
6. `booking-service` cập nhật trạng thái booking.
7. `notification-service` hiển thị kết quả.

## Quy ước RabbitMQ

- Exchange: `booking_exchange`
- Routing key:
  - `user.registered`
  - `booking.created`
  - `payment.completed`
  - `booking.failed`

## Chạy toàn bộ trên một máy

Yêu cầu:

- Docker + Docker Compose

Chạy:

```bash
docker compose -f docker-compose.full.yml up --build -d
```

## Cổng dịch vụ

- Eureka: `http://localhost:8761`
- Gateway: `http://localhost:8080`
- Frontend: `http://localhost:8085`
- User service: `http://localhost:8081`
- Movie service: `http://localhost:8082`
- Booking service: `http://localhost:8083`
- Payment service: `http://localhost:8084`
- Notification service: `http://localhost:8086`
- RabbitMQ UI: `http://localhost:15672` (`guest/guest`)

## Endpoint qua Gateway

- `POST /api/users/register`
- `POST /api/users/login`
- `GET /api/movies`
- `POST /api/movies`
- `GET /api/bookings`
- `POST /api/bookings`

## Test nhanh bằng frontend

Mở `http://localhost:8085`, sau đó:

1. Đăng ký hoặc đăng nhập.
2. Kiểm tra danh sách phim.
3. Thêm phim nếu muốn test CRUD.
4. Chọn phim, nhập ghế và số vé, rồi tạo booking.
5. Refresh danh sách booking sau vài giây để thấy `COMPLETED` hoặc `FAILED`.

Frontend gọi toàn bộ API qua cùng origin `/api`, nên không cần cấu hình CORS riêng cho trình duyệt.

## Chạy tách từng service cho từng người

Khi tách service sang máy khác, chỉ cần đổi biến môi trường:

- `EUREKA_DEFAULT_ZONE=http://<ip-eureka>:8761/eureka`
- `RABBITMQ_HOST=<ip-rabbitmq>`
- `RABBITMQ_PORT=5672`
- `RABBITMQ_USERNAME=guest`
- `RABBITMQ_PASSWORD=guest`

## Ghi chú

- Các service nghiệp vụ không gọi HTTP trực tiếp sang nhau.
- Event payload được chuẩn hóa bằng logical type alias để RabbitMQ JSON converter deserialize được giữa các service khác package.
- `payment-service` và `notification-service` đã được gắn Eureka client để có thể đăng ký lên discovery server khi chạy cùng hệ thống.
