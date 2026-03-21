package iuh.fit.tax_system;

public class Product {
    private String name;
    private double basePrice;
    private TaxStrategy taxStrategy;

    public Product(String name, double price) { this.name = name; this.basePrice = price; }
    public void setTaxStrategy(TaxStrategy ts) { this.taxStrategy = ts; }
    public double getPriceWithTax() {
        return basePrice + (taxStrategy != null ? taxStrategy.calculateTax(basePrice) : 0);
    }
    public String getName() { return name; }
}
