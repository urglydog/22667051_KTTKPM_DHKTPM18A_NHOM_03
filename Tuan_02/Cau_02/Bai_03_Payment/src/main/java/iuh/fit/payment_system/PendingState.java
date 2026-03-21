package iuh.fit.payment_system;

public class PendingState implements TransactionState {
    @Override
    public void handle(Transaction transaction) {
        System.out.println("Giao dịch đang chờ... Hệ thống đang xác nhận thanh toán.");
        // Sau khi xử lý xong (giả lập), chuyển sang trạng thái thành công
        transaction.setState(new SuccessState());
    }
}
