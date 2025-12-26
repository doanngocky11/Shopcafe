package com.example.shopcafe.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shopcafe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileScreenActivity extends AppCompatActivity {

    private LinearLayout personalInfoRow;
    private LinearLayout cardsPaymentsRow;
    private LinearLayout transactionHistoryRow;
    private LinearLayout privacyDataRow;
    private LinearLayout accountIdRow;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);

        mAuth = FirebaseAuth.getInstance();

        initViews();
        setupListeners();
    }

    private void initViews() {
        personalInfoRow = findViewById(R.id.personal_info_row);
        cardsPaymentsRow = findViewById(R.id.cards_payments_row);
        transactionHistoryRow = findViewById(R.id.transaction_history_row);
        privacyDataRow = findViewById(R.id.privacy_data_row);
        accountIdRow = findViewById(R.id.account_id_row);
    }

    private void setupListeners() {
        // Click vào Personal Info -> Xem thông tin cá nhân
        personalInfoRow.setOnClickListener(v -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser != null) {
                String firebaseUid = currentUser.getUid();

                Intent intent = new Intent(ProfileScreenActivity.this, UserDetailsActivity.class);
                intent.putExtra("firebase_uid", firebaseUid); // ✨ Truyền Firebase UID
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
                // Hoặc chuyển về LoginActivity
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Các click events khác
        cardsPaymentsRow.setOnClickListener(v -> {
            Toast.makeText(this, "Cards & Payments", Toast.LENGTH_SHORT).show();
        });

        transactionHistoryRow.setOnClickListener(v -> {
            Toast.makeText(this, "Transaction History", Toast.LENGTH_SHORT).show();
        });

        privacyDataRow.setOnClickListener(v -> {
            Toast.makeText(this, "Privacy & Data", Toast.LENGTH_SHORT).show();
        });

        accountIdRow.setOnClickListener(v -> {
            Toast.makeText(this, "Account ID", Toast.LENGTH_SHORT).show();
        });
    }
}