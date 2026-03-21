package iuh.fit;



// Import đúng các class trong package library_system của bạn
import iuh.fit.library_system.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
// 1. Khởi tạo Thư viện (Singleton)
        Library myLibrary = Library.getInstance();

        // 2. Đăng ký người theo dõi (Observer)
        Reader thien = new Reader("Thien");

        // 3. Thêm sách mới (Factory)
        Book b1 = BookFactory.createBook("paper", "Lập trình Java");
        Book b2 = BookFactory.createBook("ebook", "Design Patterns");

        myLibrary.addBook(b1);
        myLibrary.addBook(b2);
        thien.update("Có sách mới: " + b1.getTitle());

        // 4. Tìm kiếm sách (Strategy)
        SearchStrategy strategy = new SearchByTitle();
        strategy.search("Java", myLibrary.getBooks());

        // 5. Mượn sách và Gia hạn (Decorator)
        System.out.println("\n--- Thực hiện mượn sách ---");
        Book loanWithExtension = new ExtensionDecorator(b1);
        loanWithExtension.display();
    }
}