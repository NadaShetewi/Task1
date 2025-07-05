package Main;

import java.time.LocalDate;

import Customer.Customer;
import Product.ExpirableShippableProduct;
import Product.NonExpirableProduct;
import Product.Product;
import Product.ShippableProduct;
import System.ECommerceSystem;

public class Main {
	public static void main(String [] args) {
		 Customer john = new Customer("John", 500);

         Product cheese = new ExpirableShippableProduct("Cheese", 20, 10, LocalDate.now().plusDays(5), 1.5);
         Product tv = new ShippableProduct("TV", 300, 5, 10);
         Product card = new NonExpirableProduct("Scratch Card", 10, 100);

         john.getCart().addProduct(cheese, 2);
         john.getCart().addProduct(tv, 1);
         john.getCart().addProduct(card, 5);

         ECommerceSystem.checkout(john);
	}

}
