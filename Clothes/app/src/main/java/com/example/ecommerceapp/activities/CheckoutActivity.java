package com.example.ecommerceapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.data.AuthManager;
import com.example.ecommerceapp.data.CartManager;
import com.example.ecommerceapp.data.OrderManager;
import com.example.ecommerceapp.models.CartItem;
import com.example.ecommerceapp.models.User;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

/**
 * CheckoutActivity - Checkout form screen
 * 
 * Features:
 * - Form fields: Full Name, Address, Phone, Email
 * - Validates input
 * - Creates order via OrderManager
 * - Clears cart after successful order
 * - Navigates to PurchaseHistoryActivity on success
 */
public class CheckoutActivity extends AppCompatActivity {
    private EditText etFullName;
    private EditText etAddress;
    private EditText etPhone;
    private EditText etEmail;
    private CheckBox cbPrivacyConsent;
    private TextView tvPrivacyPolicyLink;
    private Button btnPlaceOrder;
    
    private CartManager cartManager;
    private OrderManager orderManager;
    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        cartManager = CartManager.getInstance(this);
        orderManager = OrderManager.getInstance(this);
        authManager = AuthManager.getInstance(this);

        setupToolbar();
        initializeViews();
        populateUserInfo();
        setupPrivacyCheckbox();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> {
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }

    private void initializeViews() {
        TextInputLayout fullNameLayout = findViewById(R.id.layoutFullName);
        etFullName = fullNameLayout != null ? fullNameLayout.getEditText() : null;
        
        TextInputLayout addressLayout = findViewById(R.id.layoutAddress);
        etAddress = addressLayout != null ? addressLayout.getEditText() : null;
        
        TextInputLayout phoneLayout = findViewById(R.id.layoutPhone);
        etPhone = phoneLayout != null ? phoneLayout.getEditText() : null;
        
        TextInputLayout emailLayout = findViewById(R.id.layoutEmail);
        etEmail = emailLayout != null ? emailLayout.getEditText() : null;
        
        cbPrivacyConsent = findViewById(R.id.cbPrivacyConsent);
        tvPrivacyPolicyLink = findViewById(R.id.tvPrivacyPolicyLink);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        btnPlaceOrder.setOnClickListener(v -> handlePlaceOrder());
        
        tvPrivacyPolicyLink.setOnClickListener(v -> {
            // Open privacy policy (you can replace with actual URL)
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com/privacy"));
            startActivity(browserIntent);
        });
    }

    private void setupPrivacyCheckbox() {
        // Initially disable Place Order button
        btnPlaceOrder.setEnabled(false);
        btnPlaceOrder.setAlpha(0.5f);
        
        cbPrivacyConsent.setOnCheckedChangeListener((buttonView, isChecked) -> {
            btnPlaceOrder.setEnabled(isChecked);
            btnPlaceOrder.setAlpha(isChecked ? 1.0f : 0.5f);
        });
    }

    /**
     * Populate form with current user info if available
     */
    private void populateUserInfo() {
        User user = authManager.getCurrentUser();
        if (user != null && etFullName != null && etEmail != null) {
            etFullName.setText(user.getFullName());
            etEmail.setText(user.getEmail());
        }
    }

    /**
     * Handle place order button click
     * Validates form and creates order
     */
    private void handlePlaceOrder() {
        if (etFullName == null || etAddress == null || etPhone == null || etEmail == null) {
            Toast.makeText(this, "Form fields not initialized", Toast.LENGTH_SHORT).show();
            return;
        }
        
        String fullName = etFullName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        // Validation
        if (TextUtils.isEmpty(fullName)) {
            Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Please enter your address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check privacy consent
        if (!cbPrivacyConsent.isChecked()) {
            Toast.makeText(this, "Please agree to the Privacy Policy", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get cart items
        List<CartItem> cartItems = cartManager.getCart();
        if (cartItems.isEmpty()) {
            Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get current user email
        User user = authManager.getCurrentUser();
        String userEmail = user != null ? user.getEmail() : email;

        // Create order
        orderManager.createOrder(userEmail, cartItems, fullName, address, phone, email);

        // Clear cart
        cartManager.clearCart();

        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();

        // Navigate to PurchaseHistoryActivity
        Intent intent = new Intent(CheckoutActivity.this, PurchaseHistoryActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
