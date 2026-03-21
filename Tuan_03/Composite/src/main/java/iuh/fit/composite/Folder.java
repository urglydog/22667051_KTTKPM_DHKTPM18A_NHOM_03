package iuh.fit.composite;


public class Folder implements FileSystemComponent {
    private String path;

    public Folder(String path) {
        this.path = path;
    }

    @Override
    public void showDetails() {
        // Dùng tên đầy đủ để tránh xung đột với class File trong cùng package
        java.io.File realNode = new java.io.File(path);

        if (!realNode.exists()) {
            System.out.println("Đường dẫn không tồn tại: " + path);
            return;
        }

        System.out.println("\n[Directory] " + realNode.getName() + " (Path: " + path + ")");

        java.io.File[] contents = realNode.listFiles();
        if (contents != null) {
            for (java.io.File item : contents) {
                if (item.isDirectory()) {
                    // Đệ quy để quét thư mục con
                    new Folder(item.getAbsolutePath()).showDetails();
                } else {
                    // Hiển thị thông tin file thật
                    System.out.println("  |_ [File] " + item.getName() + " - " + item.length() + " bytes");
                }
            }
        }
    }
}
