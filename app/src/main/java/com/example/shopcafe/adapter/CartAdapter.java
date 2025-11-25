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

/**
 * Adapter cho RecyclerView hiển thị các mặt hàng trong Giỏ hàng.
 */
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Sử dụng final int adapterPosition để đảm bảo sử dụng đúng vị trí khi click
        final int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition == RecyclerView.NO_POSITION) return; // Bảo vệ khỏi lỗi

        CartItem item = cartItems.get(adapterPosition);
        ProductModel p = item.getProduct();

        // 1. Hiển thị Tên sản phẩm
        holder.titleTxt.setText(p.getTitle());

        // 2. Giá mỗi item (feeEachItem)
        holder.feeEachItem.setText(String.format(Locale.US, "$%.2f", p.getPrice()));

        // 3. Tổng giá item này (totalEachItem)
        double total = p.getPrice() * item.getNumberInCart();
        holder.totalEachItem.setText(String.format(Locale.US, "$%.2f", total));

        // 4. Số lượng
        holder.numberItemTxt.setText(String.valueOf(item.getNumberInCart()));

        // TODO: Load ảnh sản phẩm (Giữ nguyên phần này)

        // --- Xử lý sự kiện click ---

        // Nút Tăng số lượng
        holder.plusCartBtn.setOnClickListener(v -> {
            cartManager.plusNumberItem(cartItems, adapterPosition, () -> {
                // Callback sau khi dữ liệu giỏ hàng thay đổi
                notifyItemChanged(adapterPosition); // Cập nhật hàng hiện tại
                changeNumberItemsListener.onCartDataChanged(); // Cập nhật tổng tiền ở Activity/Fragment
            });
        });

        // Nút Giảm số lượng
        holder.minusCartBtn.setOnClickListener(v -> {
            cartManager.minusNumberItem(cartItems, adapterPosition, () -> {
                // Callback sau khi dữ liệu giỏ hàng thay đổi
                // Kiểm tra nếu item bị xóa (số lượng về 0), ta cần dùng notifyDataSetChanged hoặc notifyItemRemoved
                if (cartItems.isEmpty() || cartItems.size() <= adapterPosition) {
                    notifyDataSetChanged(); // Cập nhật lại toàn bộ list nếu có xóa
                } else {
                    notifyItemChanged(adapterPosition); // Cập nhật hàng hiện tại
                }
                changeNumberItemsListener.onCartDataChanged(); // Cập nhật tổng tiền ở Activity/Fragment
            });
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // ViewHolder class (giữ nguyên)
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