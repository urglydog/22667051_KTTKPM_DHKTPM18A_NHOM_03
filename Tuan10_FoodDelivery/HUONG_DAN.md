# Hướng dẫn triển khai Food Delivery System (Hybrid Architecture)

Hệ thống được chia làm 4 máy trong mạng LAN.

## 1. Chuẩn bị (Máy 1)
- Chạy Kafka: `cd infra && docker-compose up -d`
- Đảm bảo Kafka đang lắng nghe ở IP của máy 1 (ví dụ: `192.168.1.100`).

## 2. Phân công chạy Service
- **Máy 1 (IP .10):** Chạy `api-gateway` và `frontend`. Nguyễn Chí Thiện
- **Máy 2 (IP .11):** Chạy `user-food-service`. Đặng Hoàng Việt
- **Máy 3 (IP .12):** Chạy `order-service`. Trần Thành Tài
- **Máy 4 (IP .13):** Chạy `payment-notification-service`. Lương Minh Tân

## 3. Luồng kiểm tra (Demo)
1. Mở Frontend, thực hiện Đăng nhập.
2. Xem danh sách món ăn (lấy từ Máy 2).
3. Nhấn "Đặt hàng" (gửi tới Máy 3).
4. Quan sát Console của **Máy 4**: Bạn sẽ thấy dòng chữ thông báo thanh toán thành công hiện lên sau vài giây (xử lý không đồng bộ qua Kafka).

---
*Chúc nhóm bạn làm bài tốt!*
