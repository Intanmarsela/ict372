package com.example.ecommerceapp.data;

import android.content.Context;

import com.example.ecommerceapp.models.User;

import java.util.Map;

/**
 * AuthManager class for managing user authentication
 * Handles: register, login, logout, get current user
 * Uses DataStore for persistence
 */
public class AuthManager {
    private static AuthManager instance;
    private DataStore dataStore;

    private AuthManager(Context context) {
        dataStore = DataStore.getInstance(context);
    }

    public static synchronized AuthManager getInstance(Context context) {
        if (instance == null) {
            instance = new AuthManager(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * Register a new user
     * @param email User email (must be unique)
     * @param fullName User full name
     * @param password User password
     * @return true if registration successful, false if email already exists
     */
    public boolean register(String email, String fullName, String password) {
        Map<String, User> users = dataStore.loadUsers();
        
        // Check if email already exists
        if (users.containsKey(email)) {
            return false;
        }
        
        // Create and save new user
        User user = new User(email, fullName, password);
        users.put(email, user);
        dataStore.saveUsers(users);
        
        return true;
    }

    /**
     * Login with email and password
     * @param email User email
     * @param password User password
     * @return true if login successful, false otherwise
     */
    public boolean login(String email, String password) {
        Map<String, User> users = dataStore.loadUsers();
        User user = users.get(email);
        
        if (user != null && user.getPassword().equals(password)) {
            dataStore.saveCurrentUser(email);
            return true;
        }
        
        return false;
    }

    /**
     * Logout current user
     */
    public void logout() {
        dataStore.clearCurrentUser();
    }

    /**
     * Get currently logged-in user
     * @return User object or null if not logged in
     */
    public User getCurrentUser() {
        String email = dataStore.getCurrentUser();
        if (email == null) {
            return null;
        }
        
        Map<String, User> users = dataStore.loadUsers();
        return users.get(email);
    }

    /**
     * Check if user is logged in
     * @return true if user is logged in
     */
    public boolean isLoggedIn() {
        return dataStore.getCurrentUser() != null;
    }
}
