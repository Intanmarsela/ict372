package com.example.ecommerceapp.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.adapters.SuggestionAdapter;
import com.example.ecommerceapp.data.DataStore;
import com.example.ecommerceapp.models.Product;
import com.example.ecommerceapp.utils.Constants;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchActivity - Search screen with type-ahead suggestions
 * 
 * Features:
 * - EditText with TextWatcher for real-time search
 * - RecyclerView showing filtered suggestions as user types
 * - Tap suggestion -> ProductDetailActivity (with EXTRA_PRODUCT_ID)
 * - Filters products by name (case-insensitive)
 */
public class SearchActivity extends AppCompatActivity {
    private TextInputLayout searchLayout;
    private RecyclerView rvSuggestions;
    private SuggestionAdapter adapter;
    private List<Product> allProducts;
    private List<Product> filteredProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupToolbar();
        initializeViews();
        loadProducts();
        setupRecyclerView();
        setupSearchListener();
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
        searchLayout = findViewById(R.id.searchLayout);
        rvSuggestions = findViewById(R.id.rvSuggestions);
    }

    private void loadProducts() {
        DataStore dataStore = DataStore.getInstance(this);
        allProducts = dataStore.loadProducts();
        filteredProducts = new ArrayList<>();
    }

    private void setupRecyclerView() {
        adapter = new SuggestionAdapter(filteredProducts, product -> {
            // Navigate to ProductDetailActivity with product ID
            android.content.Intent intent = new android.content.Intent(SearchActivity.this, ProductDetailActivity.class);
            intent.putExtra(Constants.EXTRA_PRODUCT_ID, product.getId());
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        rvSuggestions.setLayoutManager(new LinearLayoutManager(this));
        rvSuggestions.setAdapter(adapter);
    }

    private void setupSearchListener() {
        if (searchLayout != null && searchLayout.getEditText() != null) {
            searchLayout.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filterProducts(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    /**
     * Filter products by search query (type-ahead)
     * Updates RecyclerView with filtered suggestions
     */
    private void filterProducts(String query) {
        filteredProducts.clear();
        
        if (query.isEmpty()) {
            adapter.updateSuggestions(filteredProducts);
            return;
        }

        String lowerQuery = query.toLowerCase();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(lowerQuery)) {
                filteredProducts.add(product);
            }
        }

        adapter.updateSuggestions(filteredProducts);
    }
}
