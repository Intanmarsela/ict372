package com.example.ecommerceapp.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.adapters.OrderDetailAdapter;
import com.example.ecommerceapp.data.OrderManager;
import com.example.ecommerceapp.models.Order;
import com.example.ecommerceapp.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * OrderDetailActivity - Order detail screen
 * 
 * Features:
 * - Shows order ID, date, status
 * - RecyclerView of purchased items
 * - Total amount at bottom
 */
public class OrderDetailActivity extends AppCompatActivity {
    private TextView tvOrderId;
    private TextView tvOrderDate;
    private TextView tvStatus;
    private TextView tvTotal;
    private RecyclerView rvOrderItems;
    private OrderDetailAdapter adapter;
    private OrderManager orderManager;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        orderManager = OrderManager.getInstance(this);

        // Get order ID from intent
        int orderId = getIntent().getIntExtra(Constants.EXTRA_ORDER_ID, -1);
        if (orderId == -1) {
            finish();
            return;
        }

        setupToolbar();
        initializeViews();
        loadOrder(orderId);
        setupRecyclerView();
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
        tvOrderId = findViewById(R.id.tvOrderId);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvStatus = findViewById(R.id.tvStatus);
        tvTotal = findViewById(R.id.tvTotal);
        rvOrderItems = findViewById(R.id.rvOrderItems);
    }

    private void loadOrder(int orderId) {
        order = orderManager.getOrderById(orderId);
        if (order == null) {
            finish();
            return;
        }

        // Display order info
        tvOrderId.setText("#" + order.getId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
        tvOrderDate.setText(dateFormat.format(order.getTimestamp()));
        tvStatus.setText(order.getStatus() != null ? order.getStatus() : "Delivered");
        tvTotal.setText(String.format("$%.2f", order.getTotal()));
    }

    private void setupRecyclerView() {
        adapter = new OrderDetailAdapter(order.getItems());
        rvOrderItems.setLayoutManager(new LinearLayoutManager(this));
        rvOrderItems.setAdapter(adapter);
    }
}
