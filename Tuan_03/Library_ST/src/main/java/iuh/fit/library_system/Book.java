package iuh.fit.library_system;

// Interface Book
public interface Book {
    String getTitle();
    void display();
}

// Concrete Product: PaperBook
class PaperBook implements Book {
    private String title;
    public PaperBook(String title) { this.title = title; }
    public String getTitle() { return title; }
    public void display() { System.out.println("Sách giấy: " + title); }
}

// Concrete Product: EBook
class EBook implements Book {
    private String title;
    public EBook(String title) { this.title = title; }
    public String getTitle() { return title; }
    public void display() { System.out.println("Sách điện tử: " + title); }
}

// Factory

