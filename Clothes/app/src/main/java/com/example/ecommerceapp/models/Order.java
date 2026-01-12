package com.example.ecommerceapp.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Order model representing a completed purchase
 * Contains: id, user email, items, total, timestamp, shipping info
 */
public class Order {
    private int id;
    private String userEmail;
    private List<CartItem> items;
    private double total;
    private Date timestamp;
    private String fullName;
    private String address;
    private String phone;
    private String email;
    private String status; // e.g., "Delivered", "Pending", "Shipped"

    public Order() {
        this.items = new ArrayList<>();
        this.timestamp = new Date();
    }

    public Order(int id, String userEmail, List<CartItem> items, double total,
                 String fullName, String address, String phone, String email) {
        this.id = id;
        this.userEmail = userEmail;
        this.items = new ArrayList<>(items);
        this.total = total;
        this.timestamp = new Date();
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.status = "Delivered"; // Default status
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Get formatted order ID string (ORD-2025-XXXX)
     */
    public String getFormattedOrderId() {
        return String.format("ORD-2025-%04d", id);
    }
}
