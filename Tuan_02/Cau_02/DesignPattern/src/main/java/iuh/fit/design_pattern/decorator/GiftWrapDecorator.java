package iuh.fit.design_pattern.decorator;


import iuh.fit.design_pattern.state.Order;

public class GiftWrapDecorator extends OrderDecorator {

    public GiftWrapDecorator(Order order) {
        super(order);
    }

    // Thêm mô tả mới
    @Override
    public String getDescription() {
        return decoratedOrder.getDescription() + " + Dịch vụ Đóng gói quà";
    }

    // Cộng thêm phí dịch vụ vào giá gốc
    @Override
    public double getCost() {
        // Cộng thêm 5,000đ phí bọc quà
        return decoratedOrder.getCost() + 5000;
    }
}
