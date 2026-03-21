package iuh.fit;

import iuh.fit.adapter.JsonService;
import iuh.fit.adapter.JsonToXmlAdapter;
import iuh.fit.adapter.XmlSystem;
import iuh.fit.composite.File;
import iuh.fit.composite.FileSystemComponent;
import iuh.fit.composite.Folder;
import iuh.fit.observer.Investor;
import iuh.fit.observer.Observer;
import iuh.fit.observer.Subject;

public class Main {
    public static void main(String[] args) {

        // --- 1. TEST COMPOSITE PATTERN ---
// Trong hàm main của bạn
        System.out.println("=== TEST VỚI THƯ MỤC THẬT ===");
// Thay đường dẫn bên dưới bằng một thư mục có thật trên máy bạn
//        FileSystemComponent myPCFolder = new Folder("D:\\NAM_TU\\HK2\\KTPM");
//        myPCFolder.showDetails();

        // --- 2. TEST OBSERVER PATTERN ---
        System.out.println("===  OBSERVER PATTERN ===");
        Subject rideEvent = new Subject();
        rideEvent.attach(new Investor("Khách hàng Thien"));
        rideEvent.attach(new Investor("Tài xế Nguyễn"));

// Cập nhật trạng thái thực tế từ tài liệu
        rideEvent.notifyObservers("Trạng thái: RIDE_ASSIGNED - Tài xế đang đến!");

        // --- 3. TEST ADAPTER PATTERN ---
        System.out.println("=== TEST ADAPTER PATTERN: PAYMENT INTEGRATION ===");

        // 1. Hệ thống thanh toán cũ
        XmlSystem legacyBank = new XmlSystem();

        // 2. Bộ điều hợp (Adapter)
        JsonService paymentAdapter = new JsonToXmlAdapter(legacyBank);

        // 3. Dữ liệu thanh toán chuyến đi từ tài liệu (JSON)
        String bookingPayment = "{ rideId:R123, amount:50000, method:E-Wallet }";

        System.out.println("Client gửi JSON: " + bookingPayment);

        // 4. Thực hiện gọi qua Adapter
        paymentAdapter.requestJson(bookingPayment);
    }
}