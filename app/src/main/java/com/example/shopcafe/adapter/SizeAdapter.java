package com.example.shopcafe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView; // Cần thiết nếu bạn thêm TextView
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopcafe.R;
import java.util.List;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.ViewHolder> {

    private List<String> sizeList;
    private int selectedPosition = 0;
    private OnSizeSelectedListener listener;

    public interface OnSizeSelectedListener {
        void onSizeSelected(String size);
    }

    public SizeAdapter(List<String> sizeList, OnSizeSelectedListener listener) {
        this.sizeList = sizeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_size, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // --- Xử lý trạng thái được chọn ---
        if (selectedPosition == position) {
            holder.sizeLayout.setBackgroundResource(R.drawable.size_bg_selected);
        } else {
            holder.sizeLayout.setBackgroundResource(R.drawable.size_bg);
        }




        holder.itemView.setOnClickListener(v -> {

            final int currentPosition = holder.getAdapterPosition();

            if (currentPosition == RecyclerView.NO_POSITION || currentPosition == selectedPosition) {
                return;
            }


            int oldSelected = selectedPosition;

            selectedPosition = currentPosition;


            notifyItemChanged(oldSelected);
            notifyItemChanged(selectedPosition);


            if (listener != null) {
                listener.onSizeSelected(sizeList.get(currentPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return sizeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout sizeLayout;
        ImageView coffeeIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sizeLayout = itemView.findViewById(R.id.sizeLayout);
            coffeeIcon = itemView.findViewById(R.id.coffee);
        }
    }
}