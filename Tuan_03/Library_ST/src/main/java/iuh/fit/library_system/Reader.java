package iuh.fit.library_system;

public class Reader implements Observer {
    private String name;
    public Reader(String name) { this.name = name; }
    @Override
    public void update(String message) {
        System.out.println("[Thông báo đến " + name + "]: " + message);
    }
}
