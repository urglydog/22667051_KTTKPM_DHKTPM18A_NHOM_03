package iuh.fit;


import iuh.fit.library_system.Book;

public abstract class LoanDecorator implements Book {
    protected Book decoratedBook;
    public LoanDecorator(Book book) { this.decoratedBook = book; }
    public String getTitle() { return decoratedBook.getTitle(); }
}


