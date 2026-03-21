package iuh.fit.design_pattern.strategy;

public class StandardShipping implements ShippingStrategy {
    @Override
    public void ship() {
        System.out.println("-> [Strategy: Standard] - Giao hàng tiêu chuẩn (3-5 ngày). Phí: 20,000đ");
    }
}
