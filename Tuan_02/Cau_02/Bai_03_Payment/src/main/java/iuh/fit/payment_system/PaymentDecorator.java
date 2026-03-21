package iuh.fit.payment_system;

public abstract class PaymentDecorator {
    protected double amount;
    public PaymentDecorator(double amount) { this.amount = amount; }
    public abstract double getFinalAmount();
}
