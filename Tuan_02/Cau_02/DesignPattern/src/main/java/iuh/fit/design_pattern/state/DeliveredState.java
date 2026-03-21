package iuh.fit.design_pattern.state;

public class DeliveredState implements OrderState {
    @Override
    public void handle(Order order) {
        System.out.println("---- State: Đã giao ----");
        System.out.println("Hành vi: Cập nhật trạng thái đơn hàng là đã giao.");
        // Đây là trạng thái kết thúc, không chuyển sang trạng thái khác
    }

    @Override
    public String getStateName() {
        return "Đã giao";
    }
}
