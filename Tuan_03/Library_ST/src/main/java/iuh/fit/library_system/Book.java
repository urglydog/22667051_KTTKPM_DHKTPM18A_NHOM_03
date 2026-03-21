package iuh.fit.library_system;

public interface Book {
    String getTitle();
    double getBasePrice(); // Phí mượn gốc
}

class PaperBook implements Book {
    private String title;
    public PaperBook(String title) { this.title = title; }
    public String getTitle() { return title + " (Sách giấy)"; }
    public double getBasePrice() { return 10000; }
}

class EBook implements Book {
    private String title;
    public EBook(String title) { this.title = title; }
    public String getTitle() { return title + " (E-Book)"; }
    public double getBasePrice() { return 5000; }
}
