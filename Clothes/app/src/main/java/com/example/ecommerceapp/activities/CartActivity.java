package com.example.ecommerceapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.adapters.CartAdapter;
import com.example.ecommerceapp.data.CartManager;
import com.example.ecommerceapp.models.CartItem;

import java.util.List;

/**
 * CartActivity - Shopping cart screen
 * 
 * Features:
 * - RecyclerView showing cart items
 * - Quantity update buttons (+/-) for each item
 * - Remove item button
 * - Total price display
 * - Checkout button -> CheckoutActivity
 */
public class CartActivity extends AppCompatActivity {
    private RecyclerView rvCart;
    private TextView tvSubtotal;
    private TextView tvTotal;
    private Button btnCheckout;
    private Button btnContinueShopping;
    private View llEmptyState;
    private View cardTotal;
    private CartAdapter adapter;
    private CartManager cartManager;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartManager = CartManager.getInstance(this);

        setupToolbar();
        initializeViews();
        loadCart();
        setupRecyclerView();
        updateUI();
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

    @Override
    protected void onResume() {
        super.onResume();
        loadCart();
        if (adapter != null) {
            adapter.updateCartItems(cartItems);
        }
        updateUI();
    }

    private void initializeViews() {
        rvCart = findViewById(R.id.rvCart);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvTotal = findViewById(R.id.tvTotal);
        btnCheckout = findViewById(R.id.btnCheckout);
        btnContinueShopping = findViewById(R.id.btnContinueShopping);
        llEmptyState = findViewById(R.id.llEmptyState);
        cardTotal = findViewById(R.id.cardTotal);

        btnCheckout.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            // Navigate to CheckoutActivity
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        btnContinueShopping.setOnClickListener(v -> {
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }

    private void loadCart() {
        cartItems = cartManager.getCart();
    }

    private void setupRecyclerView() {
        adapter = new CartAdapter(cartItems, new CartAdapter.OnCartItemClickListener() {
            @Override
            public void onQuantityChanged(CartItem item, int newQuantity) {
                cartManager.updateQuantity(item.getProduct().getId(), newQuantity);
                loadCart();
                adapter.updateCartItems(cartItems);
                updateUI();
            }

            @Override
            public void onRemoveItem(CartItem item) {
                cartManager.removeFromCart(item.getProduct().getId());
                loadCart();
                adapter.updateCartItems(cartItems);
                updateUI();
            }
        });

        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.setAdapter(adapter);
    }

    private void updateUI() {
        if (cartItems.isEmpty()) {
            // Show empty state
            llEmptyState.setVisibility(View.VISIBLE);
            rvCart.setVisibility(View.GONE);
            cardTotal.setVisibility(View.GONE);
        } else {
            // Show cart items
            llEmptyState.setVisibility(View.GONE);
            rvCart.setVisibility(View.VISIBLE);
            cardTotal.setVisibility(View.VISIBLE);
            
            double subtotal = cartManager.getTotal();
            double shipping = 5; // Free shipping
            double total = subtotal + shipping;
            
            tvSubtotal.setText(String.format("$%.2f", subtotal));
            tvTotal.setText(String.format("$%.2f", total));
            btnCheckout.setEnabled(true);
        }
    }
}
