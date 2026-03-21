package iuh.fit.payment_system;

public class DiscountCode extends PaymentDecorator {
    public DiscountCode(double a) { super(a); }
    public double getFinalAmount() { return amount - 10000; } // Giảm 10k (Decorator)
}