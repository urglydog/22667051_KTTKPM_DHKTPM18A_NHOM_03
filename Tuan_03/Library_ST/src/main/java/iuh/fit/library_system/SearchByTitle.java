package iuh.fit.library_system;

import java.util.List;

public class SearchByTitle implements SearchStrategy {
    public void search(String keyword, List<Book> books) {
        System.out.println("Đang tìm theo tiêu đề: " + keyword);
        books.stream().filter(b -> b.getTitle().contains(keyword))
                .forEach(b -> System.out.println("- Found: " + b.getTitle()));
    }
}
