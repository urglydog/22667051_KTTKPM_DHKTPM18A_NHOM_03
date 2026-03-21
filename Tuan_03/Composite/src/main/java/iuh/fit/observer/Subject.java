package iuh.fit.observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> observers = new ArrayList<>();

    // Thêm phương thức attach (thay cho subscribe hoặc dùng song song)
    public void attach(Observer observer) {
        observers.add(observer);
    }

    // Thêm phương thức detach để hủy đăng ký
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }
}
