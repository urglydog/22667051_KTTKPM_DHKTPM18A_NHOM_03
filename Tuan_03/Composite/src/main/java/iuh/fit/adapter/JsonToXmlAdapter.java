package iuh.fit.adapter;

public class JsonToXmlAdapter implements JsonService {
    private XmlSystem xmlSystem;

    public JsonToXmlAdapter(XmlSystem xmlSystem) {
        this.xmlSystem = xmlSystem;
    }

    @Override
    public void requestJson(String jsonData) {
        // Logic chuyển đổi chuỗi JSON sang XML đơn giản
        // Ví dụ: {"amount":50000} -> <amount>50000</amount>
        String convertedXml = jsonData
                .replace("{", "<Request>")
                .replace("}", "</Request>")
                .replace("\"", "")
                .replace(":", ">")
                .replace(",", "</");

        // Gửi dữ liệu đã chuyển đổi sang hệ thống XML
        xmlSystem.sendXml(convertedXml);
    }
}