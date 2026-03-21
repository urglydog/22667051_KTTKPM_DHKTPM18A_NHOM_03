package iuh.fit.design_pattern.decorator;


import iuh.fit.design_pattern.state.Order;

public abstract class OrderDecorator extends Order {

    // Đối tượng Order được bọc bên trong
    protected Order decoratedOrder;

    public OrderDecorator(Order order) {
        // Sao chép các thuộc tính cơ bản để khởi tạo OrderDecorator
        super(order.getDescription(), order.getCost());
        this.decoratedOrder = order;
        // Giữ nguyên trạng thái và chiến lược từ đơn hàng gốc
        this.setState(order.getState());
        this.setShippingStrategy(order.getShippingStrategy());
    }

    // Các phương thức được bọc lại để đảm bảo tính nhất quán
    @Override
    public void process() { decoratedOrder.process(); }

    @Override
    public void performShipping() { decoratedOrder.performShipping(); }

    // Hai phương thức quan trọng nhất cần được Decorator "trang trí"
    @Override
    public abstract String getDescription(); // Mô tả đơn hàng

    @Override
    public abstract double getCost();       // Giá trị đơn hàng
}