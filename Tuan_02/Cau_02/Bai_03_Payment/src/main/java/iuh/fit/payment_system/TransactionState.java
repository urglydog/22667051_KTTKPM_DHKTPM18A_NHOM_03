package iuh.fit.payment_system;

public interface TransactionState {
    void handle(Transaction transaction);
}
