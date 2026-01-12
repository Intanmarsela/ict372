package com.example.ecommerceapp.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.adapters.OrderAdapter;
import com.example.ecommerceapp.data.AuthManager;
import com.example.ecommerceapp.data.OrderManager;
import com.example.ecommerceapp.models.CartItem;
import com.example.ecommerceapp.models.Order;
import com.example.ecommerceapp.models.User;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * PurchaseHistoryActivity - Order history screen
 * 
 * Features:
 * - RecyclerView showing orders from last 6 months only
 * - Filters orders by date using DateUtils
 * - Shows order ID, date, total, item count
 * - Only shows orders for current logged-in user
 * - Tapping order shows details in a dialog (no separate screen)
 */
public class PurchaseHistoryActivity extends AppCompatActivity {
    private RecyclerView rvOrders;
    private View tvEmpty;
    private OrderAdapter adapter;
    private OrderManager orderManager;
    private AuthManager authManager;
    private List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);

        orderManager = OrderManager.getInstance(this);
        authManager = AuthManager.getInstance(this);

        setupToolbar();
        initializeViews();
        loadOrders();
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

    @Override
    protected void onResume() {
        super.onResume();
        loadOrders();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        updateEmptyState();
    }

    private void initializeViews() {
        rvOrders = findViewById(R.id.rvOrders);
        tvEmpty = findViewById(R.id.tvEmpty);
    }

    /**
     * Load orders for current user from last 6 months
     */
    private void loadOrders() {
        User user = authManager.getCurrentUser();
        if (user != null) {
            // Get orders from last 6 months only
            orders = orderManager.getUserOrdersLast6Months(user.getEmail());
        } else {
            orders = new java.util.ArrayList<>();
        }
    }

    private void setupRecyclerView() {
        adapter = new OrderAdapter(orders, order -> {
            // Show order details in a dialog (no separate screen)
            showOrderDetailsDialog(order);
        });
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        rvOrders.setAdapter(adapter);
        updateEmptyState();
    }

    /**
     * Show order details in a dialog
     */
    private void showOrderDetailsDialog(Order order) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_order_detail, null);
        
        // Format date
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.US);
        String formattedDate = dateFormat.format(order.getTimestamp());
        
        // Set order details
        TextView tvOrderId = dialogView.findViewById(R.id.tvOrderId);
        TextView tvOrderDate = dialogView.findViewById(R.id.tvOrderDate);
        TextView tvOrderStatus = dialogView.findViewById(R.id.tvOrderStatus);
        TextView tvOrderTotal = dialogView.findViewById(R.id.tvOrderTotal);
        LinearLayout llOrderItems = dialogView.findViewById(R.id.llOrderItems);
        Button btnClose = dialogView.findViewById(R.id.btnClose);
        
        tvOrderId.setText(order.getFormattedOrderId());
        tvOrderDate.setText(formattedDate);
        tvOrderStatus.setText(order.getStatus());
        tvOrderTotal.setText(String.format(Locale.US, "$%.2f", order.getTotal()));
        
        // Add order items
        llOrderItems.removeAllViews();
        for (CartItem item : order.getItems()) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_order_detail_row, llOrderItems, false);
            
            TextView tvItemName = itemView.findViewById(R.id.tvItemName);
            TextView tvItemQuantity = itemView.findViewById(R.id.tvItemQuantity);
            TextView tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            android.widget.ImageView ivItemImage = itemView.findViewById(R.id.ivItemImage);
            
            tvItemName.setText(item.getProduct().getName());
            tvItemQuantity.setText("Qty: " + item.getQuantity());
            tvItemPrice.setText(String.format(Locale.US, "$%.2f", item.getTotalPrice()));
            
            Glide.with(this)
                    .load(item.getProduct().getImageUrl())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_gallery)
                    .into(ivItemImage);
            
            llOrderItems.addView(itemView);
        }
        
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        
        btnClose.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void updateEmptyState() {
        if (orders.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvOrders.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvOrders.setVisibility(View.VISIBLE);
        }
    }
}
