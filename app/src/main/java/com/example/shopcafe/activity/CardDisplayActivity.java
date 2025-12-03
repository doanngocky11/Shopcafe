package com.example.shopcafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopcafe.R;

public class CardDisplayActivity extends AppCompatActivity {

    private TextView tvCardNumberDisplay, tvCardHolderDisplay, tvExpiryDisplay;
    private TextView tvCardNumberDetail, tvCardHolderDetail, tvExpiryDetail, tvCvvDetail;
    private Button btnEdit, btnConfirm;
    private ImageView btnBack;

    private String cardNumber, cardholderName, expiryDate, cvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_display);

        initViews();
        getDataFromIntent();
        displayCardInfo();
        setupButtons();
    }

    private void initViews() {
        // Card display views
        tvCardNumberDisplay = findViewById(R.id.tvCardNumberDisplay);
        tvCardHolderDisplay = findViewById(R.id.tvCardHolderDisplay);
        tvExpiryDisplay = findViewById(R.id.tvExpiryDisplay);

        // Detail views
        tvCardNumberDetail = findViewById(R.id.tvCardNumberDetail);
        tvCardHolderDetail = findViewById(R.id.tvCardHolderDetail);
        tvExpiryDetail = findViewById(R.id.tvExpiryDetail);
        tvCvvDetail = findViewById(R.id.tvCvvDetail);

        // Buttons
        btnEdit = findViewById(R.id.btnEdit);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnBack = findViewById(R.id.btnBack);
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        cardNumber = intent.getStringExtra("CARD_NUMBER");
        cardholderName = intent.getStringExtra("CARDHOLDER_NAME");
        expiryDate = intent.getStringExtra("EXPIRY_DATE");
        cvv = intent.getStringExtra("CVV");
    }

    private void displayCardInfo() {
        // Display on card
        tvCardNumberDisplay.setText(cardNumber);
        tvCardHolderDisplay.setText(cardholderName);
        tvExpiryDisplay.setText(expiryDate);

        // Display in details section
        tvCardNumberDetail.setText(cardNumber);
        tvCardHolderDetail.setText(cardholderName);
        tvExpiryDetail.setText(expiryDate);
        tvCvvDetail.setText(cvv);
    }

    private void setupButtons() {
        // Back button
        btnBack.setOnClickListener(v -> {
            finish();
        });

        // Edit button - quay lại màn hình nhập liệu
        btnEdit.setOnClickListener(v -> {
            finish();
        });

        // Confirm button - xác nhận và xử lý thanh toán
        btnConfirm.setOnClickListener(v -> {
            Toast.makeText(this, "Payment Confirmed!", Toast.LENGTH_LONG).show();

            // TODO: Thêm logic xử lý thanh toán thực tế ở đây
            // Ví dụ: Gọi API thanh toán, lưu thông tin, etc.

            // Sau khi xác nhận, có thể chuyển sang màn hình khác hoặc đóng activity
            finish();
        });
    }
}