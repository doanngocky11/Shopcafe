package com.example.shopcafe.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopcafe.R;
import com.example.shopcafe.activity.UserDetailsActivity;
import com.example.shopcafe.model.UserModel;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private List<UserModel> userList;
    private OnUserClickListener listener;

    // Interface để xử lý click
    public interface OnUserClickListener {
        void onUserClick(UserModel user);
    }

    public UserAdapter(Context context, List<UserModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    public UserAdapter(Context context, List<UserModel> userList, OnUserClickListener listener) {
        this.context = context;
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    public void updateList(List<UserModel> newList) {
        this.userList = newList;
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgAvatar;
        private TextView tvName, tvEmail, tvPhone;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgUserAvatar);
            tvName = itemView.findViewById(R.id.tvUserName);
            tvEmail = itemView.findViewById(R.id.tvUserEmail);
            tvPhone = itemView.findViewById(R.id.tvUserPhone);
        }

        public void bind(UserModel user) {
            tvName.setText(user.getName());
            tvEmail.setText(user.getEmail());
            tvPhone.setText(user.getPhoneNumber());

            // Load avatar (có thể dùng Glide)
            // Glide.with(context).load(user.getAvatarUrl()).into(imgAvatar);

            // Xử lý click vào item
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onUserClick(user);
                } else {
                    // Mở màn hình chi tiết user
                    Intent intent = new Intent(context, UserDetailsActivity.class);
                    intent.putExtra("user", user);
                    context.startActivity(intent);
                }
            });
        }
    }
}