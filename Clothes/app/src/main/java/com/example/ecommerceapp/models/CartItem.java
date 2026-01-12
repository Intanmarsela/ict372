package com.example.ecommerceapp.models;

/**
 * CartItem model representing a product in the shopping cart
 * Contains: product reference and quantity
 */
public class CartItem {
    private Product product;
    private int quantity;

    public CartItem() {
    }

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Calculate total price for this cart item
     */
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}
