package iuh.fit.library_system;

import java.util.List;

public interface SearchStrategy {
    void search(String keyword, List<Book> books);
}


