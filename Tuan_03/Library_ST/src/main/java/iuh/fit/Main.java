package iuh.fit;



// Import đúng các class trong package library_system của bạn
import iuh.fit.library_system.Book;
import iuh.fit.library_system.BookFactory;
import iuh.fit.library_system.Library;
import iuh.fit.library_system.SearchByTitle;
import iuh.fit.library_system.SearchStrategy;
import iuh.fit.library_system.Reader; // Import Reader của bạn ở đây

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // 1. Khởi tạo Thư viện (Singleton)
        Library myLib = Library.getInstance();

        // 2. Đăng ký người nhận thông báo (Observer)
        myLib.addObserver(new Reader("Thiện"));
        myLib.addObserver(new Reader("Thủ thư A"));

        // 3. Nhập sách mới (Factory + Observer trigger)
        System.out.println("--- Nhập sách mới ---");
        Book b1 = BookFactory.createBook("GIAY", "Design Patterns");
        myLib.addBook(b1);
        myLib.addBook(BookFactory.createBook("DIENTU", "Java nâng cao"));

        // 4. Tìm kiếm sách (Strategy)
        System.out.println("\n--- Tìm kiếm sách ---");
        SearchStrategy searcher = new SearchByTitle();
        searcher.search("Java", myLib.getBooks());

        // 5. Mượn sách với tính năng bổ sung (Decorator)
        System.out.println("\n--- Mượn sách & Dịch vụ ---");
        Book myLoan = new ExtensionDecorator(b1); // Mượn sách giấy + Gia hạn
        System.out.println("Đơn mượn: " + myLoan.getTitle());
        System.out.println("Tổng phí mượn: " + myLoan.getBasePrice() + " VNĐ");
    }
}