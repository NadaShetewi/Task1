package System;

import java.util.ArrayList;
import java.util.List;

import Cart.Cart;
import Cart.CartItem;
import Customer.Customer;
import Product.ExpirableShippableProduct;
import Product.NonExpirableProduct;
import Product.Product;
import Product.Shippable;
import Product.ShippableProduct;
import Shipping.ShippingService;

public class ECommerceSystem {

    public static void checkout(Customer customer) {
        Cart cart = customer.getCart();
        if (cart.isEmpty()) throw new IllegalStateException("Cart is empty.");

        double subtotal = 0;
        List<Shippable> itemsToShip = new ArrayList<>();

        for (CartItem item : cart.getItems()) {
            Product product = item.product;
            int qty = item.quantity;

            if (!product.isAvailable(qty)) {
                throw new IllegalStateException("Product out of stock or expired: " + product.getName());
            }

            subtotal += product.getPrice() * qty;

            if (product instanceof Shippable) {
                itemsToShip.add((Shippable) product);
            }
        }

        double shippingFees = itemsToShip.stream().mapToDouble(Shippable::getWeight).sum() * 2.5;
        double total = subtotal + shippingFees;

        if (customer.getBalance() < total) {
            throw new IllegalStateException("Insufficient balance.");
        }

        for (CartItem item : cart.getItems()) {
            item.product.reduceQuantity(item.quantity);
        }

        customer.deduct(total);

        // Summary
        System.out.printf("Subtotal: $%.2f%n", subtotal);
        System.out.printf("Shipping: $%.2f%n", shippingFees);
        System.out.printf("Total Paid: $%.2f%n", total);
        System.out.printf("Customer Remaining Balance: $%.2f%n", customer.getBalance());

        if (!itemsToShip.isEmpty()) {
            ShippingService.ship(itemsToShip);
        }

        cart.clear();
    }
    

}
