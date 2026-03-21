package iuh.fit.design_pattern.strategy;

public class PickupShipping implements ShippingStrategy {
    @Override
    public void ship() {
        System.out.println("-> [Strategy: Pickup] - Khách hàng sẽ tự đến cửa hàng lấy hàng.");
    }
}
