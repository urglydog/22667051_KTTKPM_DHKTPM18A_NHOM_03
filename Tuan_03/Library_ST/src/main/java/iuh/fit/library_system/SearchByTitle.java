package iuh.fit.library_system;

import java.util.List;

public class SearchByTitle implements SearchStrategy {
    @Override
    public void search(String keyword, List<Book> books) {
        System.out.println("--- Kết quả tìm kiếm theo tiêu đề: " + keyword + " ---");
        for (Book b : books) {
            if (b.getTitle().contains(keyword)) b.display();
        }
    }
}
