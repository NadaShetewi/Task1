package Cart;
import java.util.ArrayList;
import java.util.List;

import Product.Product;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

     public void addProduct(Product product, int quantity) {
        if (!product.isAvailable(quantity)) {
            throw new IllegalArgumentException("Product not available or expired.");
        }
        CartItem previousProduct = null;
        for(CartItem item: items) {
        	if(item.getProduct().equals(product)) {
        		previousProduct = item;
        		if (!product.isAvailable(quantity + item.getQuantity())) {
                    throw new IllegalArgumentException("Product not available or expired.");
                }
        	}
        }
        if(previousProduct !=null) {
        	previousProduct.setQuantity(quantity + previousProduct.getQuantity());
        }
        else {
        items.add(new CartItem(product, quantity));
        }
    }


    public List<CartItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
}
