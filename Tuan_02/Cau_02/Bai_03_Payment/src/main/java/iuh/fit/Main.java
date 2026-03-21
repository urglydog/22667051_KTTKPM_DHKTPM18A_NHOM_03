package iuh.fit;

import iuh.fit.payment_system.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        double base_amount = 1000000;
        // phí giao dịch trên tiền gốc
        PaymentDecorator d = new ProcessingFee(base_amount);
        // discount sau khi đã thêm phí giao dịch
        d = new DiscountCode(d.getFinalAmount());
        System.out.println("Số tiền gốc: " + base_amount);
        System.out.println("Số tiền sau khi tính phí và discount: " + d.getFinalAmount());
        // khoi tao giao dich o trang thai cho
        Transaction t = new Transaction();
        // thanh toan bang the
        t.setMethod(new CreditCardPayment());
        t.execute(d.getFinalAmount());

    }
}