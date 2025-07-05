package Product;
public class ShippableProduct extends Product implements Shippable {
    private double weight;

    public ShippableProduct(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        this.weight = weight;
    }

    @Override
    public boolean isExpired() {
        return false; // override in subclass if needed
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
