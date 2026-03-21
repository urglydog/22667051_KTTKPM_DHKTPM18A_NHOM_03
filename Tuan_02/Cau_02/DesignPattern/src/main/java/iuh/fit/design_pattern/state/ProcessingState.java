package iuh.fit.design_pattern.state;

public class ProcessingState implements OrderState {
    @Override
    public void handle(Order order) {
        System.out.println("---- State: Đang xử lý ----");
        System.out.println("Hành vi: Đóng gói hàng.");
        // Ở trạng thái này, có thể chọn hình thức vận chuyển (Strategy)
        // Trong demo, ta sẽ chuyển thẳng sang "Đã giao"
        order.setState(new DeliveredState());
    }

    @Override
    public String getStateName() {
        return "Đang xử lý";
    }
}
