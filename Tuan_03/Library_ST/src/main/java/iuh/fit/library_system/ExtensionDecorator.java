package iuh.fit.library_system;

import iuh.fit.LoanDecorator;

public class ExtensionDecorator extends LoanDecorator {
    public ExtensionDecorator(Book book) { super(book); }
    @Override
    public void display() {
        decoratedBook.display();
        System.out.println("   => Tính năng bổ sung: Đã gia hạn thêm 7 ngày mượn.");
    }
}
