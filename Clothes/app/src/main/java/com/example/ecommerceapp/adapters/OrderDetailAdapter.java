package com.example.ecommerceapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.CartItem;

import java.util.List;

/**
 * OrderDetailAdapter for displaying order items in RecyclerView
 * Used in OrderDetailActivity to show purchased items
 */
public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderItemViewHolder> {
    private List<CartItem> items;

    public OrderDetailAdapter(List<CartItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_detail_row, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        CartItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivItemImage;
        private TextView tvItemName;
        private TextView tvItemQuantity;
        private TextView tvItemPrice;

        OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemImage = itemView.findViewById(R.id.ivItemImage);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemQuantity = itemView.findViewById(R.id.tvItemQuantity);
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
        }

        void bind(CartItem item) {
            tvItemName.setText(item.getProduct().getName());
            tvItemQuantity.setText("Qty: " + item.getQuantity());
            double lineTotal = item.getTotalPrice();
            tvItemPrice.setText(String.format("$%.2f", lineTotal));
            
            // Load image using Glide
            Glide.with(itemView.getContext())
                    .load(item.getProduct().getImageUrl())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_gallery)
                    .into(ivItemImage);
        }
    }
}
