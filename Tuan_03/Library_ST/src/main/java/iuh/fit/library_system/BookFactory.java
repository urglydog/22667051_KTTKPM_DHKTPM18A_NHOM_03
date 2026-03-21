package iuh.fit.library_system;

public class BookFactory {
    public static Book createBook(String type, String title) {
        if (type.equalsIgnoreCase("paper")) return new PaperBook(title);
        if (type.equalsIgnoreCase("ebook")) return new EBook(title);
        return null;
    }
}
