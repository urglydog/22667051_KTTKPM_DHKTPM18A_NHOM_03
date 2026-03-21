package iuh.fit;

import iuh.fit.design_pattern.decorator.GiftWrapDecorator;
import iuh.fit.design_pattern.decorator.InsuranceDecorator;
import iuh.fit.design_pattern.state.Order;
import iuh.fit.design_pattern.strategy.ExpressShipping;
import iuh.fit.design_pattern.strategy.PickupShipping;
import iuh.fit.design_pattern.strategy.StandardShipping;

public class Main {
    public static void main(String[] args) {
        Order myOrder = new Order("Laptop Gaming", 25000000);
        System.out.println(myOrder);

        myOrder.process();
        System.out.println(myOrder);

        System.out.println("=== TEST STRATEGY PATTERN ===");

        // 1. Khởi tạo đơn hàng (Giá 10.000.000đ)
        Order order = new Order("Dàn loa Sony", 10000000);
        System.out.println("Sản phẩm: " + order.getDescription());

        // 2. Test chiến lược: Giao hàng tiêu chuẩn
        System.out.println("\n--- TH 1: Khách chọn Giao tiêu chuẩn ---");
        order.setShippingStrategy(new StandardShipping());
        order.performShipping();

        // 3. Test chiến lược: Giao hàng hỏa tốc
        System.out.println("\n--- TH 2: Khách đổi ý chọn Giao hỏa tốc ---");
        order.setShippingStrategy(new ExpressShipping());
        order.performShipping();

        // 4. Test chiến lược: Tự đến lấy
        System.out.println("\n--- TH 3: Khách muốn tiết kiệm, tự đến lấy ---");
        order.setShippingStrategy(new PickupShipping());
        order.performShipping();

        // 5. Test trường hợp quên chọn chiến lược (Safety check)
        System.out.println("\n--- TH 4: Đơn hàng mới chưa chọn vận chuyển ---");
        Order newOrder = new Order("Chuột Gaming", 500000);
        newOrder.performShipping();

        System.out.println("\n=============================");
        System.out.println("--- DEMO DECORATOR PATTERN: Thêm phụ phí 'bọc' quà và bảo hiểm ---");

        // Gói quà (bọc đối tượng 'myOrder' cũ)
        System.out.println("Khách hàng muốn: Đóng gói làm quà tặng");
        Order decoratedOrder = new GiftWrapDecorator(myOrder);
        System.out.println("Đơn hàng sau khi bọc quà: " + decoratedOrder);
        System.out.println("Mô tả mới: " + decoratedOrder.getDescription());
        System.out.println("Giá mới:   " + decoratedOrder.getCost());
        System.out.println();

        // Thêm bảo hiểm (bọc 'decoratedOrder' đã được bọc quà)
        System.out.println("Khách hàng muốn thêm: Bảo hiểm đơn hàng");
        Order superDecoratedOrder = new InsuranceDecorator(decoratedOrder);
        System.out.println("Đơn hàng sau khi thêm bảo hiểm: " + superDecoratedOrder);
        System.out.println("Mô tả cuối: " + superDecoratedOrder.getDescription());
        System.out.println("Giá cuối:   " + superDecoratedOrder.getCost());
        System.out.println();
    }
}