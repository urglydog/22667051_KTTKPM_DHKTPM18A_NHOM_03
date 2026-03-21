package iuh.fit;

import iuh.fit.tax_system.EnvironmentFee;
import iuh.fit.tax_system.LuxuryTax;
import iuh.fit.tax_system.Product;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Product car = new Product("Siêu xe", 1000000); // State: trạng thái ban đầu chỉ có giá gốc
        car.setTaxStrategy(new LuxuryTax()); // Áp thuế xa xỉ (Strategy) // Strategy: thêm loại thuế
        Product finalCar = new EnvironmentFee(car); // Thêm phí môi trường (Decorator) // Decorator: bọc lấy Product
        System.out.println("Tổng tiền (VAT + Thuế hàng xa xỉ + Phí môi trường: ): " + finalCar.getPriceWithTax());
    }
}