package iuh.fit.observer;

public class Investor implements Observer {
    private String name;
    public Investor(String name) { this.name = name; }

    @Override
    public void update(String message) {
        // Hiển thị thông báo thực tế lên Console (giả lập thông báo Mobile)
        System.out.println("[Notification to " + name + "]: " + message);
    }
}
