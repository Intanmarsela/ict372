package com.example.ecommerceapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.Order;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * OrderAdapter for displaying orders in RecyclerView
 * Used in PurchaseHistoryActivity to show order history
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orders;
    private SimpleDateFormat dateFormat;
    private OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

    public OrderAdapter(List<Order> orders, OnOrderClickListener listener) {
        this.orders = orders;
        this.listener = listener;
        this.dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_row, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders != null ? orders.size() : 0;
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderId;
        private TextView tvOrderDate;
        private TextView tvOrderTotal;
        private TextView tvStatus;

        OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
            tvStatus = itemView.findViewById(R.id.tvStatus);

            // Click listener for the entire card
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onOrderClick(orders.get(position));
                }
            });

            // Click listener specifically for the order number (makes it more intuitive)
            tvOrderId.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onOrderClick(orders.get(position));
                }
            });
        }

        void bind(Order order) {
            tvOrderId.setText(order.getFormattedOrderId());
            tvOrderDate.setText(dateFormat.format(order.getTimestamp()));
            tvOrderTotal.setText(String.format("$%.2f", order.getTotal()));
            
            String status = order.getStatus() != null ? order.getStatus() : "Delivered";
            tvStatus.setText(status);
            
            // Set status badge colors based on status
            if ("Processing".equalsIgnoreCase(status)) {
                tvStatus.setBackgroundResource(R.drawable.bg_status_processing);
                tvStatus.setTextColor(itemView.getContext().getResources().getColor(R.color.status_processing_text, null));
            } else if ("Shipped".equalsIgnoreCase(status)) {
                tvStatus.setBackgroundResource(R.drawable.bg_status_shipped);
                tvStatus.setTextColor(itemView.getContext().getResources().getColor(R.color.status_shipped_text, null));
            } else { // Delivered
                tvStatus.setBackgroundResource(R.drawable.bg_status_delivered);
                tvStatus.setTextColor(itemView.getContext().getResources().getColor(R.color.success_text, null));
            }
        }
    }
}
