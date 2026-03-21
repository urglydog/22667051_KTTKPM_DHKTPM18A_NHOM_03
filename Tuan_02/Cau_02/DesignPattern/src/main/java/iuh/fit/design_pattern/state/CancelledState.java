package iuh.fit.design_pattern.state;

public class CancelledState implements OrderState {
    @Override
    public void handle(Order order) {
        System.out.println("---- State: Đã hủy ----");
        System.out.println("Hành vi: Đơn hàng bị hủy. Hoàn tiền nếu cần.");
        // Trạng thái kết thúc
    }

    @Override
    public String getStateName() {
        return "Đã hủy";
    }
}
