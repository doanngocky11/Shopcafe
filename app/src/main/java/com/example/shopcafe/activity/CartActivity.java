package com.example.shopcafe.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopcafe.R;
import com.example.shopcafe.adapter.CartAdapter;
import com.example.shopcafe.helper.CartManager;
import com.example.shopcafe.helper.ChangeNumberItemsListener;

import java.util.Locale;


public class CartActivity extends AppCompatActivity implements ChangeNumberItemsListener {
    private CartManager cartManager;
    private CartAdapter adapter;
    private ImageView ivBack;
    private RecyclerView rvCartView;
    private AppCompatButton proceedCheckoutBtn;
    private AppCompatButton applyBtn;
    private TextView subTotalPriceTxt, deliveryPriceTxt, totalTaxPriceTxt, totalPriceTxt;
    private final double DELIVERY_FEE = 3.0;
    private final double TAX_PERCENT = 0.1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartManager = CartManager.getInstance();
        cartManager.setChangeNumberItemsListener(this);

        initView();
        setupCartRecyclerView();
        calculateCartTotals();
    }

    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        proceedCheckoutBtn = findViewById(R.id.proceedCheckoutBtn);
        rvCartView = findViewById(R.id.rvCartView);
        subTotalPriceTxt = findViewById(R.id.subTotalPriceTxt);
        deliveryPriceTxt = findViewById(R.id.deliveryPriceTxt);
        totalTaxPriceTxt = findViewById(R.id.totalTaxPriceTxt);
        totalPriceTxt = findViewById(R.id.totalPriceTxt);
        applyBtn = findViewById(R.id.applyBtn);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        proceedCheckoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartManager.getCartList().isEmpty()) {
                    Toast.makeText(CartActivity.this, "Giỏ hàng đang trống!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CartActivity.this, "Tiến hành Thanh toán với tổng: " + totalPriceTxt.getText().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        applyBtn.setOnClickListener(v -> {
            Toast.makeText(CartActivity.this, "Tính năng Coupon đang được phát triển!", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupCartRecyclerView() {
        rvCartView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(cartManager.getCartList(), this);
        rvCartView.setAdapter(adapter);
    }

    private void calculateCartTotals() {
        double subtotal = cartManager.getTotal();
        double tax = subtotal * TAX_PERCENT;
        double total = subtotal + DELIVERY_FEE + tax;

        subTotalPriceTxt.setText(String.format(Locale.US, "$%.2f", subtotal));
        deliveryPriceTxt.setText(String.format(Locale.US, "$%.2f", DELIVERY_FEE));
        totalTaxPriceTxt.setText(String.format(Locale.US, "$%.2f", tax));
        totalPriceTxt.setText(String.format(Locale.US, "$%.2f", total));
    }

    @Override
    public void onCartDataChanged() {
        adapter.notifyDataSetChanged();
        calculateCartTotals();
    }
}