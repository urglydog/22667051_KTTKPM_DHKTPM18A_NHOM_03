package iuh.fit.design_pattern.state;

public class NewOrderState implements OrderState {
    @Override
    public void handle(Order order) {
        System.out.println("---- State: Mới tạo ----");
        System.out.println("Hành vi: Kiểm tra thông tin đơn hàng.");
        // Sau khi xử lý xong, chuyển sang trạng thái "Đang xử lý"
        order.setState(new ProcessingState());
    }

    @Override
    public String getStateName() {
        return "Mới tạo";
    }
}
