package com.example.shopcafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.shopcafe.R;
import com.example.shopcafe.database.DatabaseHelper;
import com.example.shopcafe.model.UserModel;

public class UserDetailsActivity extends AppCompatActivity {

    private static final int REQUEST_ADD_EDIT_USER = 1;

    private ImageView imgAvatar;
    private TextView tvUserName, tvUsername, tvAge, tvGender;
    private TextView tvEmail, tvPhone, tvAddress;
    private TextView tvBankName, tvBankAccountNumber, tvBankAccountName;
    private Button btnEditProfile;

    private UserModel currentUser;
    private DatabaseHelper dbHelper;
    private String firebaseUid; // ✨ Dùng Firebase UID thay vì userId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        dbHelper = new DatabaseHelper(this);

        // ✨ Lấy Firebase UID từ Intent
        firebaseUid = getIntent().getStringExtra("firebase_uid");

        initViews();
        loadUserData();
        setupListeners();
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("Thông tin cá nhân");
            }
        }

        imgAvatar = findViewById(R.id.imgAvatar);
        tvUserName = findViewById(R.id.tvUserName);
        tvUsername = findViewById(R.id.tvUsername);

        tvAge = findViewById(R.id.tvAge);
        tvGender = findViewById(R.id.tvGender);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);

        tvBankName = findViewById(R.id.tvBankName);
        tvBankAccountNumber = findViewById(R.id.tvBankAccountNumber);
        tvBankAccountName = findViewById(R.id.tvBankAccountName);

        btnEditProfile = findViewById(R.id.btnEditProfile);
    }

    private void loadUserData() {
        if (firebaseUid == null || firebaseUid.isEmpty()) {
            Toast.makeText(this, "Lỗi: Không tìm thấy thông tin xác thực",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // ✨ Lấy dữ liệu từ SQLite bằng Firebase UID
        currentUser = dbHelper.getUserByFirebaseUid(firebaseUid);

        if (currentUser != null && !currentUser.getName().isEmpty()) {
            // Đã có thông tin đầy đủ
            displayUserData(currentUser);
            btnEditProfile.setText("Chỉnh sửa thông tin");
        } else {
            // Chưa có thông tin hoặc thông tin trống
            showEmptyState();
            btnEditProfile.setText("Thêm thông tin");
        }
    }

    private void displayUserData(UserModel user) {
        tvUserName.setText(user.getName());
        tvUsername.setText("@" + (user.getUsername() != null && !user.getUsername().isEmpty()
                ? user.getUsername() : "username"));

        tvAge.setText(String.valueOf(user.getAge()));
        tvGender.setText(user.getGender());
        tvEmail.setText(user.getEmail());
        tvPhone.setText(user.getPhoneNumber());
        tvAddress.setText(user.getAddress() != null && !user.getAddress().isEmpty()
                ? user.getAddress() : "Chưa cập nhật");

        tvBankName.setText(user.getBankName() != null && !user.getBankName().isEmpty()
                ? user.getBankName() : "Chưa liên kết");
        tvBankAccountNumber.setText(user.getBankAccountNumber() != null &&
                !user.getBankAccountNumber().isEmpty()
                ? user.getBankAccountNumber() : "---");
        tvBankAccountName.setText(user.getBankAccountName() != null &&
                !user.getBankAccountName().isEmpty()
                ? user.getBankAccountName() : "---");
    }

    private void showEmptyState() {
        tvUserName.setText("Chưa có thông tin");
        tvUsername.setText("@username");
        tvAge.setText("--");
        tvGender.setText("--");
        tvEmail.setText("Chưa cập nhật");
        tvPhone.setText("Chưa cập nhật");
        tvAddress.setText("Chưa cập nhật");
        tvBankName.setText("Chưa liên kết");
        tvBankAccountNumber.setText("---");
        tvBankAccountName.setText("---");
    }

    private void setupListeners() {
        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditUserActivity.class);

            // ✨ Truyền Firebase UID
            intent.putExtra("firebase_uid", firebaseUid);

            if (currentUser != null) {
                // Chế độ chỉnh sửa
                intent.putExtra("user", currentUser);
            }

            startActivityForResult(intent, REQUEST_ADD_EDIT_USER);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_EDIT_USER && resultCode == RESULT_OK) {
            loadUserData();
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}