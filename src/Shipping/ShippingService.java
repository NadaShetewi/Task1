package Shipping;
import java.util.List;

import Cart.CartItem;
import Product.Shippable;

public class ShippingService {
    public static void ship(List<CartItem> items) {
        System.out.println("** Shipping notice **");
         double totalWeight = 0;
        for (CartItem item : items) {
            int q = item.getQuantity();
           double weight = ((Shippable)item.getProduct()).getWeight();
           totalWeight += (q*weight);
          System.out.printf(" - %d X  %s, weight: %.2fkg%n", item.getQuantity(), ((Shippable)item.getProduct()).getName(), ((Shippable)item.getProduct()).getWeight());
        }
       System.out.printf("Total package weight %.2fkg%n", totalWeight);

    }
}

