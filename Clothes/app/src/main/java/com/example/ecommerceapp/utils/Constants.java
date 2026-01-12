package com.example.ecommerceapp.utils;

/**
 * Constants class for Intent extras and SharedPreferences keys
 * Used throughout the app for navigation and data persistence
 */
public class Constants {
    // Intent Extra Keys
    public static final String EXTRA_PRODUCT_ID = "extra_product_id";
    public static final String EXTRA_ORDER_ID = "extra_order_id";

    // SharedPreferences Keys
    public static final String PREFS_NAME = "ecommerce_prefs";
    public static final String KEY_USERS = "users";
    public static final String KEY_CURRENT_USER = "current_user";
    public static final String KEY_CART = "cart";
    public static final String KEY_ORDERS = "orders";
    public static final String KEY_PRODUCTS = "products";
    public static final String KEY_LAST_ORDER_ID = "last_order_id";
}
