package iuh.fit.library_system;

public class BookFactory {
    public static Book createBook(String type, String title) {
        if (type.equalsIgnoreCase("GIAY")) return new PaperBook(title);
        if (type.equalsIgnoreCase("DIENTU")) return new EBook(title);
        return null;
    }
}
