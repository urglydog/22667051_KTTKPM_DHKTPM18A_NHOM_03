package iuh.fit.library_system;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private static Library instance;
    private List<Book> books = new ArrayList<>();

    private Library() {} // Private constructor

    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("[Library] Đã thêm sách: " + book.getTitle());
    }

    public List<Book> getBooks() { return books; }
}
