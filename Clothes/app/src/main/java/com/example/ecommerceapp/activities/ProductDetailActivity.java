package com.example.ecommerceapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.data.CartManager;
import com.example.ecommerceapp.data.DataStore;
import com.example.ecommerceapp.models.Product;
import com.example.ecommerceapp.utils.Constants;

import java.util.List;

/**
 * ProductDetailActivity - Product detail screen
 * 
 * Features:
 * - Shows product image, name, price, description
 * - Quantity selector with +/- buttons
 * - Add to Cart button
 * - Receives EXTRA_PRODUCT_ID from intent
 */
public class ProductDetailActivity extends AppCompatActivity {
    private ImageView ivProductImage;
    private TextView tvProductName;
    private TextView tvProductPrice;
    private TextView tvProductDescription;
    private TextView tvQuantity;
    private Button btnDecrease;
    private Button btnIncrease;
    private Button btnAddToCart;
    
    private Product product;
    private int quantity = 1;
    private CartManager cartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        cartManager = CartManager.getInstance(this);

        // Get product ID from intent
        int productId = getIntent().getIntExtra(Constants.EXTRA_PRODUCT_ID, -1);
        if (productId == -1) {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupToolbar();
        initializeViews();
        loadProduct(productId);
        setupQuantityControls();
        updateAddToCartButton();
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
        ivProductImage = findViewById(R.id.ivProductImage);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvProductDescription = findViewById(R.id.tvProductDescription);
        tvQuantity = findViewById(R.id.tvQuantity);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnIncrease = findViewById(R.id.btnIncrease);
        btnAddToCart = findViewById(R.id.btnAddToCart);
    }

    private void loadProduct(int productId) {
        DataStore dataStore = DataStore.getInstance(this);
        List<Product> products = dataStore.loadProducts();
        
        for (Product p : products) {
            if (p.getId() == productId) {
                product = p;
                break;
            }
        }

        if (product == null) {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Display product info
        tvProductName.setText(product.getName());
        tvProductPrice.setText(String.format("$%.2f", product.getPrice()));
        tvProductDescription.setText(product.getDescription());
        
        // Load image using Glide
        Glide.with(this)
                .load(product.getImageUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .into(ivProductImage);
    }

    private void setupQuantityControls() {
        tvQuantity.setText(String.valueOf(quantity));

        btnDecrease.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
                updateAddToCartButton();
            }
        });

        btnIncrease.setOnClickListener(v -> {
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
            updateAddToCartButton();
        });

        btnAddToCart.setOnClickListener(v -> {
            cartManager.addToCart(product, quantity);
            Toast.makeText(this, "Added to cart!", Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }

    private void updateAddToCartButton() {
        double totalPrice = product.getPrice() * quantity;
        btnAddToCart.setText(String.format("Add to Cart - $%.2f", totalPrice));
    }
}
