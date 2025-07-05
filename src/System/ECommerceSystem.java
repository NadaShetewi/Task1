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
        List<CartItem> itemsToShip = new ArrayList<>();

        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            int qty = item.getQuantity();

            if (!product.isAvailable(qty)) {
                throw new IllegalStateException("Product out of stock or expired: " + product.getName());
            }

            subtotal += product.getPrice() * qty;

            if (product instanceof Shippable) {
                itemsToShip.add(item);
            }
        }

        double shippingFees = 0.0;
        double totalWeight = 0;
        for(CartItem item: itemsToShip){
           int q = item.getQuantity();
           double weight = ((Shippable)item.getProduct()).getWeight();
           totalWeight += (q*weight); 
        }
        shippingFees = totalWeight * 5;
        double total = subtotal + shippingFees;

        if (customer.getBalance() < total) {
            throw new IllegalStateException("Insufficient balance.");
        }
        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            item.getProduct().reduceQuantity(item.getQuantity());
            System.out.printf(" - %d X  %s  %.2f%n", item.getQuantity(),item.getProduct().getName(), item.getProduct().getPrice());
        }
        if (!itemsToShip.isEmpty()) {
            ShippingService.ship(itemsToShip);
        }
        customer.deduct(total);
         System.out.println("----------------------");
        System.out.printf("Subtotal: $%.2f%n", subtotal);
        System.out.printf("Shipping: $%.2f%n", shippingFees);
        System.out.printf("Total Paid: $%.2f%n", total);
        System.out.printf("Customer Remaining Balance: $%.2f%n", customer.getBalance());

        

        cart.clear();
    }
    

}
