package Shipping;
import java.util.List;

import Product.Shippable;

public class ShippingService {
    public static void ship(List<Shippable> items) {
        System.out.println("Shipping items:");
        for (Shippable item : items) {
            System.out.printf(" - %s, weight: %.2fkg%n", item.getName(), item.getWeight());
        }
    }
}

