package iuh.fit.payment_system;

public class ProcessingFee extends PaymentDecorator {
    public ProcessingFee(double a) { super(a); }
    public double getFinalAmount() { return amount + 2000; } // Thêm 2k phí (Decorator)
}
