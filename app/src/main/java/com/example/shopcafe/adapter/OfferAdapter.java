package com.example.shopcafe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopcafe.R;
import com.example.shopcafe.model.OfferModel;
import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {

    private List<OfferModel> offerList;

    public OfferAdapter(List<OfferModel> offerList) {
        this.offerList = offerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_offer, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OfferModel offer = offerList.get(position);

        holder.title.setText(offer.getTitle());
        holder.price.setText(offer.getExtra());

        // ===== HIỂN THỊ HÌNH ẢNH BANNER =====
        // Chuyển tên hình từ String sang resource ID
        String picName = offer.getPicUrl(); // Ví dụ: "pic_offer1"
        int imageResId = holder.itemView.getContext().getResources()
                .getIdentifier(picName, "drawable", holder.itemView.getContext().getPackageName());

        if (imageResId != 0) {
            holder.pic.setImageResource(imageResId);
        } else {
            // Hiển thị hình placeholder nếu không tìm thấy
            holder.pic.setImageResource(R.drawable.coffee_cup_1);
        }
        // ====================================

        holder.itemView.setOnClickListener(v -> {
            // Xử lý click nếu cần
        });
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, price;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            price = itemView.findViewById(R.id.priceTxt);
            pic = itemView.findViewById(R.id.shapeableImageView);
        }
    }
}
