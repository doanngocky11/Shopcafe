package com.example.shopcafe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopcafe.R;
import com.example.shopcafe.model.CategoryModel;
import java.util.List;

/**
 * Adapter cho RecyclerView hiển thị các Danh mục sản phẩm (Category) trong MainActivity.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<CategoryModel> categoryList;
    private int selectedPosition = 0; // Vị trí của item đang được chọn
    private final CategorySelectedListener listener; // Thêm Listener

    // Hàm tạo phải nhận thêm Listener
    public CategoryAdapter(List<CategoryModel> categoryList, CategorySelectedListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel category = categoryList.get(position);
        holder.title.setText(category.getTitle());

        // --- Xử lý trạng thái Highlight (Chọn) ---
        if (selectedPosition == position) {
            holder.title.setBackgroundResource(R.drawable.orange_bg);
        } else {
            holder.title.setBackgroundResource(R.drawable.edittext_bg);
        }

        holder.itemView.setOnClickListener(v -> {
            // Lấy vị trí an toàn hiện tại
            final int currentPosition = holder.getAdapterPosition();

            if (currentPosition == RecyclerView.NO_POSITION) return; // Kiểm tra an toàn

            if (currentPosition != selectedPosition) {
                // 1. Lưu lại vị trí cũ
                int oldSelected = selectedPosition;
                // 2. Cập nhật vị trí mới
                selectedPosition = currentPosition;

                // 3. Thông báo cập nhật cho item cũ và item mới để thay đổi màu nền
                notifyItemChanged(oldSelected);
                notifyItemChanged(selectedPosition);

                // 4. Gọi Listener để thông báo cho Activity lọc dữ liệu
                if (listener != null) {
                    CategoryModel selectedCategory = categoryList.get(currentPosition);
                    listener.onCategorySelected(selectedCategory.getId(), selectedCategory.getTitle());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleCat);
        }
    }


    public interface CategorySelectedListener {
        void onCategorySelected(int categoryId, String categoryTitle);
    }
}