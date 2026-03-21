package iuh.fit;


import iuh.fit.library_system.Book;

public abstract class LoanDecorator implements Book {
    protected Book decoratedBook;
    public LoanDecorator(Book book) { this.decoratedBook = book; }
    public String getTitle() { return decoratedBook.getTitle(); }
    public double getBasePrice() { return decoratedBook.getBasePrice(); }
}

class ExtensionDecorator extends LoanDecorator {
    public ExtensionDecorator(Book book) { super(book); }
    @Override
    public String getTitle() { return super.getTitle() + " + Gia hạn thêm 7 ngày"; }
    @Override
    public double getBasePrice() { return super.getBasePrice() + 2000; }
}
