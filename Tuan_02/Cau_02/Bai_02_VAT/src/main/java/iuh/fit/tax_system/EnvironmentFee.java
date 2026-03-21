package iuh.fit.tax_system;

public class EnvironmentFee extends TaxDecorator {
    public EnvironmentFee(Product p) { super(p); }
    public double getPriceWithTax() { return product.getPriceWithTax() + 5000; }
}
