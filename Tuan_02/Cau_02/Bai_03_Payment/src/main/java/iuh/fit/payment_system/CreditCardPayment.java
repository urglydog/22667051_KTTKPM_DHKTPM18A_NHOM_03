package iuh.fit.payment_system;

public class CreditCardPayment implements PaymentStrategy {
    public void pay(double a) { System.out.println("Thanh toán " + a + " qua Thẻ tín dụng"); }
}
