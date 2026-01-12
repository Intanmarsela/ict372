package com.example.ecommerceapp.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ecommerceapp.models.CartItem;
import com.example.ecommerceapp.models.Order;
import com.example.ecommerceapp.models.Product;
import com.example.ecommerceapp.models.User;
import com.example.ecommerceapp.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DataStore class for managing SharedPreferences with JSON serialization
 * Handles saving and loading: users, current user, cart, orders, products
 * Uses Gson for JSON conversion
 */
public class DataStore {
    private static DataStore instance;
    private SharedPreferences prefs;
    private Gson gson;

    private DataStore(Context context) {
        prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public static synchronized DataStore getInstance(Context context) {
        if (instance == null) {
            instance = new DataStore(context.getApplicationContext());
        }
        return instance;
    }

    // ========== Users Management ==========
    /**
     * Save users map (email -> User)
     */
    public void saveUsers(Map<String, User> users) {
        String json = gson.toJson(users);
        prefs.edit().putString(Constants.KEY_USERS, json).apply();
    }

    /**
     * Load users map
     */
    public Map<String, User> loadUsers() {
        String json = prefs.getString(Constants.KEY_USERS, null);
        if (json == null) {
            return new HashMap<>();
        }
        Type type = new TypeToken<Map<String, User>>(){}.getType();
        return gson.fromJson(json, type);
    }

    // ========== Current User Management ==========
    /**
     * Save currently logged-in user email
     */
    public void saveCurrentUser(String email) {
        prefs.edit().putString(Constants.KEY_CURRENT_USER, email).apply();
    }

    /**
     * Get currently logged-in user email
     */
    public String getCurrentUser() {
        return prefs.getString(Constants.KEY_CURRENT_USER, null);
    }

    /**
     * Clear current user (logout)
     */
    public void clearCurrentUser() {
        prefs.edit().remove(Constants.KEY_CURRENT_USER).apply();
    }

    // ========== Cart Management ==========
    /**
     * Save cart items list
     */
    public void saveCart(List<CartItem> cart) {
        String json = gson.toJson(cart);
        prefs.edit().putString(Constants.KEY_CART, json).apply();
    }

    /**
     * Load cart items list
     */
    public List<CartItem> loadCart() {
        String json = prefs.getString(Constants.KEY_CART, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<CartItem>>(){}.getType();
        List<CartItem> cart = gson.fromJson(json, type);
        // Restore product references in cart items
        List<Product> products = loadProducts();
        Map<Integer, Product> productMap = new HashMap<>();
        for (Product p : products) {
            productMap.put(p.getId(), p);
        }
        for (CartItem item : cart) {
            Product p = productMap.get(item.getProduct().getId());
            if (p != null) {
                item.setProduct(p);
            }
        }
        return cart;
    }

    /**
     * Clear cart
     */
    public void clearCart() {
        prefs.edit().remove(Constants.KEY_CART).apply();
    }

    // ========== Orders Management ==========
    /**
     * Save orders list
     */
    public void saveOrders(List<Order> orders) {
        String json = gson.toJson(orders);
        prefs.edit().putString(Constants.KEY_ORDERS, json).apply();
    }

    /**
     * Load orders list
     */
    public List<Order> loadOrders() {
        String json = prefs.getString(Constants.KEY_ORDERS, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<Order>>(){}.getType();
        List<Order> orders = gson.fromJson(json, type);
        // Restore product references in order items
        List<Product> products = loadProducts();
        Map<Integer, Product> productMap = new HashMap<>();
        for (Product p : products) {
            productMap.put(p.getId(), p);
        }
        for (Order order : orders) {
            for (CartItem item : order.getItems()) {
                Product p = productMap.get(item.getProduct().getId());
                if (p != null) {
                    item.setProduct(p);
                }
            }
        }
        return orders;
    }

    // ========== Products Management ==========
    /**
     * Save products list
     */
    public void saveProducts(List<Product> products) {
        String json = gson.toJson(products);
        prefs.edit().putString(Constants.KEY_PRODUCTS, json).apply();
    }

    /**
     * Load products list
     */
    public List<Product> loadProducts() {
        String json = prefs.getString(Constants.KEY_PRODUCTS, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<Product>>(){}.getType();
        return gson.fromJson(json, type);
    }

    // ========== Order ID Management ==========
    /**
     * Get next order ID and increment
     */
    public int getNextOrderId() {
        int lastId = prefs.getInt(Constants.KEY_LAST_ORDER_ID, 0);
        int nextId = lastId + 1;
        prefs.edit().putInt(Constants.KEY_LAST_ORDER_ID, nextId).apply();
        return nextId;
    }
}
