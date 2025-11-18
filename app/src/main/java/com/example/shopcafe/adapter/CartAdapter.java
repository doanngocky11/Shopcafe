package com.example.shopcafe.adapter;

import android.content.Context;
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

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    ArrayList<CartItem> list;
    ChangeNumberItemsListener listener;

    public CartAdapter(Context context, ArrayList<CartItem> list, ChangeNumberItemsListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = list.get(position);
        ProductModel p = item.getProduct();

        holder.titleTxt.setText(p.getTitle());
        holder.feeEachItem.setText(p.getPrice() + "đ");
        holder.numberItemTxt.setText("" + item.getQuantity());

        double total = p.getPrice() * item.getQuantity();
        holder.totalEachItem.setText(total + "đ");

        holder.cartPicture.setImageResource(p.getImage());

        holder.plusCartBtn.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyItemChanged(position);
            listener.onChanged();
        });

        holder.minusCartBtn.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                notifyItemChanged(position);
            } else {
                list.remove(position);
                notifyItemRemoved(position);
            }
            listener.onChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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

