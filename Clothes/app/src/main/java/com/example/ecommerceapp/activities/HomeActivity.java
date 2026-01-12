package com.example.ecommerceapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.adapters.ProductAdapter;
import com.example.ecommerceapp.data.AuthManager;
import com.example.ecommerceapp.data.CartManager;
import com.example.ecommerceapp.data.DataStore;
import com.example.ecommerceapp.models.Product;
import com.example.ecommerceapp.utils.Constants;

import java.util.List;

/**
 * HomeActivity - Main screen showing products in 2-column grid
 * 
 * Features:
 * - Product cards in RecyclerView with GridLayoutManager (2 columns)
 * - Search bar at top (navigates to SearchActivity)
 * - Toolbar with icons: History, Cart (with badge), Profile
 * - Tap product card -> ProductDetailActivity (with EXTRA_PRODUCT_ID)
 */
public class HomeActivity extends AppCompatActivity {
    private RecyclerView rvProducts;
    private EditText etSearch;
    private ImageButton btnHistory;
    private ImageButton btnCart;
    private ImageButton btnProfile;
    private TextView tvCartBadge;
    private ProductAdapter adapter;
    private List<Product> products;
    private CartManager cartManager;
    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cartManager = CartManager.getInstance(this);
        authManager = AuthManager.getInstance(this);

        // Check if user is logged in
        if (!authManager.isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setupToolbar();
        initializeViews();
        loadProducts();
        setupRecyclerView();
        setupClickListeners();
        updateCartBadge();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
        // Refresh products if needed
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void initializeViews() {
        rvProducts = findViewById(R.id.rvProducts);
        etSearch = findViewById(R.id.etSearch);
        btnHistory = findViewById(R.id.btnHistory);
        btnCart = findViewById(R.id.btnCart);
        btnProfile = findViewById(R.id.btnProfile);
        tvCartBadge = findViewById(R.id.tvCartBadge);
    }

    private void setupClickListeners() {
        // Search bar click -> SearchActivity
        etSearch.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        // History button
        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PurchaseHistoryActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        // Cart button
        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        // Profile button (for now, just show a toast - can add ProfileActivity later if needed)
        btnProfile.setOnClickListener(v -> {
            // Could navigate to a profile/settings screen
            // For now, just logout option
        });
    }

    private void updateCartBadge() {
        int cartCount = cartManager.getTotalQuantity();
        if (cartCount > 0) {
            tvCartBadge.setText(String.valueOf(cartCount));
            tvCartBadge.setVisibility(View.VISIBLE);
        } else {
            tvCartBadge.setVisibility(View.GONE);
        }
    }

    private void loadProducts() {
        DataStore dataStore = DataStore.getInstance(this);
        products = dataStore.loadProducts();
    }

    private void setupRecyclerView() {
        adapter = new ProductAdapter(products, product -> {
            // Navigate to ProductDetailActivity with product ID
            Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
            intent.putExtra(Constants.EXTRA_PRODUCT_ID, product.getId());
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        // Use GridLayoutManager with 2 columns
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvProducts.setLayoutManager(layoutManager);
        rvProducts.setAdapter(adapter);
    }
}
