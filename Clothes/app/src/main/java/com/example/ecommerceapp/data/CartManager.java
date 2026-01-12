package com.example.ecommerceapp.data;

import android.content.Context;

import com.example.ecommerceapp.models.CartItem;
import com.example.ecommerceapp.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * CartManager class for managing shopping cart operations
 * Handles: add to cart, update quantity, remove item, calculate total
 * Persists cart to DataStore
 */
public class CartManager {
    private static CartManager instance;
    private DataStore dataStore;
    private List<CartItem> cart;

    private CartManager(Context context) {
        dataStore = DataStore.getInstance(context);
        cart = dataStore.loadCart();
        if (cart == null) {
            cart = new ArrayList<>();
        }
    }

    public static synchronized CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * Add product to cart or increase quantity if already exists
     * @param product Product to add
     * @param quantity Quantity to add
     */
    public void addToCart(Product product, int quantity) {
        CartItem existingItem = findCartItem(product.getId());
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            cart.add(new CartItem(product, quantity));
        }
        saveCart();
    }

    /**
     * Update quantity of a cart item
     * @param productId Product ID
     * @param quantity New quantity (if 0 or less, item is removed)
     */
    public void updateQuantity(int productId, int quantity) {
        CartItem item = findCartItem(productId);
        if (item != null) {
            if (quantity <= 0) {
                cart.remove(item);
            } else {
                item.setQuantity(quantity);
            }
            saveCart();
        }
    }

    /**
     * Remove item from cart
     * @param productId Product ID to remove
     */
    public void removeFromCart(int productId) {
        CartItem item = findCartItem(productId);
        if (item != null) {
            cart.remove(item);
            saveCart();
        }
    }

    /**
     * Get all cart items
     */
    public List<CartItem> getCart() {
        return new ArrayList<>(cart);
    }

    /**
     * Calculate total price of all items in cart
     */
    public double getTotal() {
        double total = 0.0;
        for (CartItem item : cart) {
            total += item.getTotalPrice();
        }
        return total;
    }

    /**
     * Get cart item count (total quantity of all items)
     */
    public int getItemCount() {
        int count = 0;
        for (CartItem item : cart) {
            count += item.getQuantity();
        }
        return count;
    }

    /**
     * Get total quantity (alias for getItemCount for compatibility)
     */
    public int getTotalQuantity() {
        return getItemCount();
    }

    /**
     * Clear entire cart
     */
    public void clearCart() {
        cart.clear();
        dataStore.clearCart();
    }

    /**
     * Find cart item by product ID
     */
    private CartItem findCartItem(int productId) {
        for (CartItem item : cart) {
            if (item.getProduct().getId() == productId) {
                return item;
            }
        }
        return null;
    }

    /**
     * Save cart to DataStore
     */
    private void saveCart() {
        dataStore.saveCart(cart);
    }
}
