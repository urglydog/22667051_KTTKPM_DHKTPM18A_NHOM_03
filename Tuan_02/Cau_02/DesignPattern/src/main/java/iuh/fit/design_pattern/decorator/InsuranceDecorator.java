package iuh.fit.design_pattern.decorator;

import iuh.fit.design_pattern.state.Order;

public class InsuranceDecorator extends OrderDecorator {

    public InsuranceDecorator(Order order) {
        super(order);
    }

    // Thêm mô tả mới
    @Override
    public String getDescription() {
        return decoratedOrder.getDescription() + " + Bảo hiểm đơn hàng (Bảo hiểm 100% giá trị)";
    }

    // Cộng thêm phí bảo hiểm vào giá gốc
    @Override
    public double getCost() {
        // Cộng thêm 20,000đ phí bảo hiểm
        return decoratedOrder.getCost() + 20000;
    }
}
