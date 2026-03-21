package iuh.fit.design_pattern.state;

import iuh.fit.design_pattern.strategy.ShippingStrategy;

public class Order {
    private String description;
    private double cost;
    private OrderState state;
    private ShippingStrategy shippingStrategy;

    public Order(String description, double cost) {
        this.description = description;
        this.cost = cost;
        this.state = new NewOrderState(); // Mặc định là trạng thái mới
    }

    // --- Phương thức cho State Pattern ---
    public void setState(OrderState state) {
        this.state = state;
    }

    public OrderState getState() {
        return state;
    }

    public void process() {
        state.handle(this);
    }

    // --- Phương thức cho Strategy Pattern ---
    public void setShippingStrategy(ShippingStrategy shippingStrategy) {
        this.shippingStrategy = shippingStrategy;
    }

    // ĐÂY LÀ PHƯƠNG THỨC BẠN ĐANG THIẾU
    public ShippingStrategy getShippingStrategy() {
        return shippingStrategy;
    }

    public void performShipping() {
        if (shippingStrategy != null) {
            shippingStrategy.ship();
        } else {
            System.out.println("Lỗi: Chưa chọn hình thức vận chuyển!");
        }
    }

    // --- Phương thức cho Decorator Pattern ---
    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Đơn hàng: " + getDescription() +
                " | Giá: " + getCost() +
                " | Trạng thái: " + state.getClass().getSimpleName();
    }
}
