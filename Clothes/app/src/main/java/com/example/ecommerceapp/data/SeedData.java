package com.example.ecommerceapp.data;

import android.content.Context;

import com.example.ecommerceapp.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * SeedData class for initializing products
 * Creates exact 6 products as specified in requirements
 */
public class SeedData {
    /**
     * Initialize products if they don't exist
     * @param context Application context
     */
    public static void initializeProducts(Context context) {
        DataStore dataStore = DataStore.getInstance(context);
        List<Product> existingProducts = dataStore.loadProducts();
        
        // Only seed if products don't exist
        if (existingProducts == null || existingProducts.isEmpty()) {
            List<Product> products = createProductList();
            dataStore.saveProducts(products);
        }
    }

    /**
     * Create list of exact 6 products as specified
     */
    private static List<Product> createProductList() {
        List<Product> products = new ArrayList<>();

        products.add(new Product(1, "Premium Wireless Headphones", 129.99, 4.5, 124,
            "Premium wireless headphones with active noise cancellation and 30-hour battery life. Features crystal-clear audio quality and comfortable over-ear design perfect for long listening sessions.",
            "https://images.unsplash.com/photo-1484704849700-f032a568e944?auto=format&fit=crop&w=800&q=80"));
        
        products.add(new Product(2, "Smart Watch", 299.99, 4.8, 89,
            "Advanced smartwatch with health tracking, GPS, and smartphone notifications. Water-resistant design with a vibrant AMOLED display and comprehensive fitness monitoring capabilities.",
            "https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=800&q=60"));
        
        products.add(new Product(3, "Laptop Stand", 49.99, 4.3, 56,
            "Ergonomic aluminum laptop stand that elevates your screen to eye level. Adjustable height and angle settings help reduce neck strain and improve posture during long work sessions.",
            "https://images.unsplash.com/photo-1515879218367-8466d910aaa4?auto=format&fit=crop&w=800&q=60"));
        
        products.add(new Product(4, "Phone Case", 19.99, 4.6, 210,
            "Durable protective phone case with shock-absorbing technology. Features precise cutouts for all ports and buttons, maintaining full device functionality while providing maximum protection.",
            "https://images.unsplash.com/photo-1580910051074-7e6d56d3c5b2?auto=format&fit=crop&w=800&q=60"));
        
        products.add(new Product(5, "Bluetooth Speaker", 89.99, 4.7, 142,
            "Portable Bluetooth speaker with 360-degree sound and 12-hour battery life. Waterproof design makes it perfect for outdoor adventures, parties, and everyday use.",
            "https://images.unsplash.com/photo-1512446816042-444d641267d4?auto=format&fit=crop&w=800&q=60"));
        
        products.add(new Product(6, "USB-C Cable", 14.99, 4.4, 98,
            "High-speed USB-C charging cable with fast charging support. Durable braided design resists tangling and wear, ensuring reliable connections for all your devices.",
            "https://images.unsplash.com/photo-1583863788434-e58a36330f4f?auto=format&fit=crop&w=800&q=60"));

        return products;
    }
}
