package com.example.ecommerceapp.data;

import android.content.Context;

import com.example.ecommerceapp.models.CartItem;
import com.example.ecommerceapp.models.Order;
import com.example.ecommerceapp.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderManager class for managing orders
 * Handles: create order, store order, filter orders by date (last 6 months)
 */
public class OrderManager {
    private static OrderManager instance;
    private DataStore dataStore;

    private OrderManager(Context context) {
        dataStore = DataStore.getInstance(context);
    }

    public static synchronized OrderManager getInstance(Context context) {
        if (instance == null) {
            instance = new OrderManager(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * Create a new order from cart items and shipping info
     * @param userEmail User email
     * @param items Cart items
     * @param fullName Shipping full name
     * @param address Shipping address
     * @param phone Shipping phone
     * @param email Shipping email
     * @return Created order
     */
    public Order createOrder(String userEmail, List<CartItem> items, 
                             String fullName, String address, String phone, String email) {
        int orderId = dataStore.getNextOrderId();
        double total = calculateTotal(items);
        
        Order order = new Order(orderId, userEmail, items, total, 
                               fullName, address, phone, email);
        
        // Save order
        List<Order> orders = dataStore.loadOrders();
        orders.add(order);
        dataStore.saveOrders(orders);
        
        return order;
    }

    /**
     * Get all orders for a user
     * @param userEmail User email
     * @return List of orders
     */
    public List<Order> getUserOrders(String userEmail) {
        List<Order> allOrders = dataStore.loadOrders();
        List<Order> userOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if (order.getUserEmail().equals(userEmail)) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    /**
     * Get orders for a user within last 6 months
     * @param userEmail User email
     * @return List of orders from last 6 months
     */
    public List<Order> getUserOrdersLast6Months(String userEmail) {
        List<Order> userOrders = getUserOrders(userEmail);
        List<Order> filteredOrders = new ArrayList<>();
        
        for (Order order : userOrders) {
            if (DateUtils.isWithinLast6Months(order.getTimestamp())) {
                filteredOrders.add(order);
            }
        }
        
        return filteredOrders;
    }

    /**
     * Get order by ID
     * @param orderId Order ID
     * @return Order or null if not found
     */
    public Order getOrderById(int orderId) {
        List<Order> orders = dataStore.loadOrders();
        for (Order order : orders) {
            if (order.getId() == orderId) {
                return order;
            }
        }
        return null;
    }

    /**
     * Calculate total price from cart items
     */
    private double calculateTotal(List<CartItem> items) {
        double total = 0.0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }
}
