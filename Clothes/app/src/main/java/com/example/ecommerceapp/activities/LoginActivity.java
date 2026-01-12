package com.example.ecommerceapp.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.data.AuthManager;
import com.example.ecommerceapp.data.SeedData;

/**
 * LoginActivity - User login screen (LAUNCHER activity)
 * 
 * Flow:
 * - User enters email and password
 * - Validates and logs in via AuthManager
 * - On success: navigates to HomeActivity
 * - Has link to RegisterActivity for new users
 * - Initializes seed data on first launch
 */
public class  LoginActivity extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPassword;
    private CheckBox cbPrivacy;
    private Button btnLogin;
    private TextView tvRegisterLink;
    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authManager = AuthManager.getInstance(this);
        
        // Initialize seed data (products) on first launch
        SeedData.initializeProducts(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbPrivacy = findViewById(R.id.cbPrivacy);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegisterLink = findViewById(R.id.tvRegisterLink);
        
        // Initially disable login button
        btnLogin.setEnabled(false);
        btnLogin.setAlpha(0.5f);
        
        // Enable/disable login button based on privacy checkbox
        cbPrivacy.setOnCheckedChangeListener((buttonView, isChecked) -> {
            btnLogin.setEnabled(isChecked);
            btnLogin.setAlpha(isChecked ? 1.0f : 0.5f);
        });

        // Style the register link text
        String fullText = "Don't have an account? Register";
        SpannableString spannableString = new SpannableString(fullText);
        int startIndex = fullText.indexOf("Register");
        int endIndex = startIndex + "Register".length();
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.primary_purple)), 
            startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 
            startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRegisterLink.setText(spannableString);

        btnLogin.setOnClickListener(v -> handleLogin());

        tvRegisterLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }

    /**
     * Handle login button click
     * Validates input and logs in user
     */
    private void handleLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        // Validation
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Login user
        boolean success = authManager.login(email, password);
        if (success) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            // Navigate to HomeActivity
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }
}
