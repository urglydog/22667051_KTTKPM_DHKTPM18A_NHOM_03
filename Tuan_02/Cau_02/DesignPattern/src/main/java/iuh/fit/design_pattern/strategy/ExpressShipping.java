package iuh.fit.design_pattern.strategy;

public class ExpressShipping implements ShippingStrategy {
    @Override
    public void ship() {
        System.out.println("-> [Strategy: Express] - Giao hàng hỏa tốc (1-2 ngày). Phí: 50,000đ");
    }
}
