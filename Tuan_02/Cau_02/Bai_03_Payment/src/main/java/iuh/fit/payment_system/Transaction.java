package iuh.fit.payment_system;

public class Transaction {
    private TransactionState state = new PendingState(); // (State)
    private PaymentStrategy method;

    public void setMethod(PaymentStrategy m) { this.method = m; }
    public void setState(TransactionState s) { this.state = s; }

    public void execute(double amount) {
        if (method != null) {
            method.pay(amount);
            state.handle(this); // Chuyển trạng thái sau khi thanh toán
        }
    }
}
