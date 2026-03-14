package singleton;

public class Logger {

    private static Logger instance;

    // Constructor private để không tạo object bên ngoài
    private Logger() {
    }

    // Method lấy instance duy nhất
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("Log: " + message);
    }
}