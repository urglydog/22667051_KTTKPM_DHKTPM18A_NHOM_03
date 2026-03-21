package iuh.fit.payment_system;

public class SuccessState implements TransactionState {
    @Override
    public void handle(Transaction transaction) {
        System.out.println("Giao dịch đã HOÀN TẤT thành công!");
    }
}
