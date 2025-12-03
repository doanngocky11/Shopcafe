package com.example.shopcafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopcafe.R;

public class CreditCardActivity extends AppCompatActivity {

    private EditText etCardNumber, etCardholderName, etExpiryDate, etCvv;
    private TextView tvCardNumberPreview, tvCardHolderPreview, tvExpiryPreview, tvCvvPreview;
    private Button btnProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        initViews();
        setupTextWatchers();
        setupButton();
    }

    private void initViews() {
        // Input fields
        etCardNumber = findViewById(R.id.etCardNumber);
        etCardholderName = findViewById(R.id.etCardholderName);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        etCvv = findViewById(R.id.etCvv);

        // Preview fields
        tvCardNumberPreview = findViewById(R.id.tvCardNumberPreview);
        tvCardHolderPreview = findViewById(R.id.tvCardHolderPreview);
        tvExpiryPreview = findViewById(R.id.tvExpiryPreview);
        tvCvvPreview = findViewById(R.id.tvCvvPreview);

        // Button
        btnProceed = findViewById(R.id.btnProceed);
    }

    private void setupTextWatchers() {
        // Card Number TextWatcher
        etCardNumber.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting;
            private int previousLength;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                previousLength = s.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isFormatting) return;
                isFormatting = true;

                String input = s.toString().replaceAll("\\s", "");
                StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < input.length(); i++) {
                    if (i > 0 && i % 4 == 0) {
                        formatted.append(" ");
                    }
                    formatted.append(input.charAt(i));
                }

                s.replace(0, s.length(), formatted.toString());

                // Update preview
                if (input.length() > 0) {
                    tvCardNumberPreview.setText(formatted.toString());
                } else {
                    tvCardNumberPreview.setText("•••• •••• •••• ••••");
                }

                isFormatting = false;
            }
        });

        // Cardholder Name TextWatcher
        etCardholderName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String name = s.toString().toUpperCase();
                if (name.length() > 0) {
                    tvCardHolderPreview.setText(name);
                } else {
                    tvCardHolderPreview.setText("CARDHOLDER NAME");
                }
            }
        });

        // Expiry Date TextWatcher
        etExpiryDate.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isFormatting) return;
                isFormatting = true;

                String input = s.toString().replaceAll("[^0-9]", "");
                StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < input.length(); i++) {
                    if (i == 2) {
                        formatted.append(" / ");
                    }
                    formatted.append(input.charAt(i));
                }

                s.replace(0, s.length(), formatted.toString());

                // Update preview
                if (input.length() > 0) {
                    tvExpiryPreview.setText(formatted.toString());
                } else {
                    tvExpiryPreview.setText("MM / YY");
                }

                isFormatting = false;
            }
        });

        // CVV TextWatcher
        etCvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    tvCvvPreview.setText("•••");
                } else {
                    tvCvvPreview.setText("•••");
                }
            }
        });
    }

    private void setupButton() {
        btnProceed.setOnClickListener(v -> {
            if (validateInputs()) {
                // Chuyển sang màn hình hiển thị thông tin thẻ
                Intent intent = new Intent(CreditCardActivity.this, CardDisplayActivity.class);
                intent.putExtra("CARD_NUMBER", etCardNumber.getText().toString());
                intent.putExtra("CARDHOLDER_NAME", etCardholderName.getText().toString().toUpperCase());
                intent.putExtra("EXPIRY_DATE", etExpiryDate.getText().toString());
                intent.putExtra("CVV", etCvv.getText().toString());
                startActivity(intent);
            }
        });
    }

    private boolean validateInputs() {
        String cardNumber = etCardNumber.getText().toString().replaceAll("\\s", "");
        String cardholderName = etCardholderName.getText().toString().trim();
        String expiryDate = etExpiryDate.getText().toString().replaceAll("[^0-9]", "");
        String cvv = etCvv.getText().toString();

        if (cardNumber.length() != 16) {
            Toast.makeText(this, "Please enter valid card number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (cardholderName.isEmpty()) {
            Toast.makeText(this, "Please enter cardholder name", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (expiryDate.length() != 4) {
            Toast.makeText(this, "Please enter valid expiry date", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (cvv.length() != 3) {
            Toast.makeText(this, "Please enter valid CVV", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
