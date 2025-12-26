package com.example.shopcafe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopcafe.R;
import com.example.shopcafe.helper.CartManager;
import com.example.shopcafe.helper.ChangeNumberItemsListener;
import com.example.shopcafe.model.CartItem;
import com.example.shopcafe.model.ProductModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final List<CartItem> cartItems;
    private final ChangeNumberItemsListener changeNumberItemsListener;
    private final CartManager cartManager;

    public CartAdapter(List<CartItem> cartItems, ChangeNumberItemsListener changeNumberItemsListener) {
        this.cartItems = cartItems;
        this.changeNumberItemsListener = changeNumberItemsListener;
        this.cartManager = CartManager.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition == RecyclerView.NO_POSITION) return;

        CartItem item = cartItems.get(adapterPosition);
        ProductModel p = item.getProduct();

        holder.titleTxt.setText(p.getTitle());
        holder.feeEachItem.setText(String.format(Locale.US, "$%.2f", p.getPrice()));

        double total = p.getPrice() * item.getNumberInCart();
        holder.totalEachItem.setText(String.format(Locale.US, "$%.2f", total));

        holder.numberItemTxt.setText(String.valueOf(item.getNumberInCart()));

        // ===== HIỂN THỊ HÌNH ẢNH TRONG GIỎ HÀNG =====
        holder.cartPicture.setImageResource(p.getImage());
        // ============================================

        holder.plusCartBtn.setOnClickListener(v -> {
            cartManager.plusNumberItem(cartItems, adapterPosition, () -> {
                notifyItemChanged(adapterPosition);
                changeNumberItemsListener.onCartDataChanged();
            });
        });

        holder.minusCartBtn.setOnClickListener(v -> {
            cartManager.minusNumberItem(cartItems, adapterPosition, () -> {
                if (cartItems.isEmpty() || cartItems.size() <= adapterPosition) {
                    notifyDataSetChanged();
                } else {
                    notifyItemChanged(adapterPosition);
                }
                changeNumberItemsListener.onCartDataChanged();
            });
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, feeEachItem, totalEachItem, minusCartBtn, numberItemTxt, plusCartBtn;
        ShapeableImageView cartPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            minusCartBtn = itemView.findViewById(R.id.minusCartBtn);
            numberItemTxt = itemView.findViewById(R.id.numberItemTxt);
            plusCartBtn = itemView.findViewById(R.id.plusCartBtn);
            cartPicture = itemView.findViewById(R.id.cartPicture);
        }
    }
}
