package com.example.shopcafe.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopcafe.R;
import com.example.shopcafe.activity.DetailedActivity;
import com.example.shopcafe.model.ProductModel;
import com.google.android.material.imageview.ShapeableImageView; // Thêm import này
import java.util.List;
import java.util.Locale;

/**
 * Adapter cho RecyclerView hiển thị các Sản phẩm Phổ biến (Popular) trong MainActivity.
 * Xử lý click để chuyển đến DetailedActivity.
 */
public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private final List<ProductModel> productList;
    private final Context context; // Đã thêm final và nhận qua constructor

    // Sửa hàm tạo để nhận Context
    public PopularAdapter(List<ProductModel> productList, Context context) {
        this.productList = productList;
        this.context = context; // Gán Context ở đây
    }

    // Nếu bạn muốn giữ hàm tạo cũ, bạn có thể làm như sau:
    /*
    public PopularAdapter(List<ProductModel> productList) {
        this.productList = productList;
        this.context = null; // Cần phải gán context trong onCreateViewHolder
    }
    */

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Nếu không truyền Context qua constructor, gán Context ở đây:
        // context = parent.getContext();

        // Sử dụng viewholder_popular.xml
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Sử dụng final để đảm bảo position không bị thay đổi trong lambda
        final int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition == RecyclerView.NO_POSITION) return;

        ProductModel product = productList.get(adapterPosition);

        // 1. Tiêu đề
        holder.title.setText(product.getTitle());

        // 2. Giá tiền
        holder.price.setText(String.format(Locale.US, "$%.2f", product.getPrice()));

        // 3. Rating
        holder.ratingBar.setRating((float) product.getStar());

        // TODO: Thiết lập hình ảnh cho sản phẩm
        // Giả sử ProductModel có getImageUrl() và bạn dùng thư viện tải ảnh (Glide/Picasso)
        /*
        Glide.with(context)
             .load(product.getImageUrl()) // Giả định method getImageUrl()
             .into(holder.pic);
        */

        // --- Xử lý click để chuyển sang màn hình chi tiết ---
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailedActivity.class);
            // Truyền đối tượng ProductModel sang DetailedActivity
            intent.putExtra("object", product); // ProductModel PHẢI implement Serializable hoặc Parcelable
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, price;
        RatingBar ratingBar;

        ShapeableImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Khởi tạo các ID từ viewholder_popular.xml
            title = itemView.findViewById(R.id.titleTxt);
            price = itemView.findViewById(R.id.priceTxt);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            // Cần kiểm tra xem ID này có trỏ đến ShapeableImageView hay không
            pic = itemView.findViewById(R.id.shapeableImageView);
        }
    }
}