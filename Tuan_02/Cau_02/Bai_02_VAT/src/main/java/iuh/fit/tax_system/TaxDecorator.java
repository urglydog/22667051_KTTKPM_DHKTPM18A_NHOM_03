package iuh.fit.tax_system;

public abstract class TaxDecorator extends Product {
    protected Product product;
    public TaxDecorator(Product p) { super(p.getName(), 0); this.product = p; }
}

