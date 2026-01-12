package com.example.ecommerceapp.models;

/**
 * Product model representing an item in the store
 * Contains: id, name, price, description, image placeholder
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private double rating;
    private int reviews;
    private String description;
    private String imageUrl;

    public Product() {
    }

    public Product(int id, String name, double price, double rating, int reviews, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.reviews = reviews;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }
}
