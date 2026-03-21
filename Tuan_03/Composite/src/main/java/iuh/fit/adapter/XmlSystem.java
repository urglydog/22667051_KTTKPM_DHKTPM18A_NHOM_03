package iuh.fit.adapter;

public class XmlSystem {
    public void sendXml(String xmlData) {
        System.out.println("\n[Legacy Payment System] Đang xử lý giao dịch...");
        System.out.println("Dữ liệu nhận được (XML): " + xmlData);
        System.out.println("Trạng thái: THÀNH CÔNG");
    }
}