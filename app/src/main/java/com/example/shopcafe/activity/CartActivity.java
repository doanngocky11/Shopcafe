package com.example.shopcafe.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.example.shopcafe.helper.CouponManager;

import java.util.Locale;

public class CartActivity extends AppCompatActivity
        implements ChangeNumberItemsListener {

    private CartManager cartManager;
    private CartAdapter adapter;

    private ImageView ivBack;
    private RecyclerView rvCartView;
    private AppCompatButton proceedCheckoutBtn, applyBtn;
    private EditText couponEditText;

    private TextView subTotalPriceTxt, deliveryPriceTxt,
            totalTaxPriceTxt, totalPriceTxt,
            discountPriceTxt;

    private final double DELIVERY_FEE = 3.0;
    private final double TAX_PERCENT = 0.1;

    private double currentDiscount = 0;
    private CouponManager.CouponResult appliedCoupon = null;

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
        discountPriceTxt = findViewById(R.id.discountPriceTxt);

        applyBtn = findViewById(R.id.applyBtn);
        couponEditText = findViewById(R.id.couponEditText);

        ivBack.setOnClickListener(v -> finish());

        proceedCheckoutBtn.setOnClickListener(v -> {
            if (cartManager.getCartList().isEmpty()) {
                Toast.makeText(this, "Giỏ hàng đang trống!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                        "Thanh toán: " + totalPriceTxt.getText().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });

        // ✅ ÁP DỤNG COUPON
        applyBtn.setOnClickListener(v -> applyCoupon());
    }

    private void applyCoupon() {
        String code = couponEditText.getText().toString().trim();

        if (code.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã giảm giá", Toast.LENGTH_SHORT).show();
            return;
        }

        CouponManager.CouponResult result =
                CouponManager.applyCoupon(code, cartManager.getSubTotal());

        if (result.isValid) {
            appliedCoupon = result;
            currentDiscount = result.discount;
            couponEditText.setText("");
            calculateCartTotals();

            Toast.makeText(this,
                    result.message + " - Giảm $" +
                            String.format(Locale.US, "%.2f", currentDiscount),
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show();
        }
    }

    private void setupCartRecyclerView() {
        rvCartView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(cartManager.getCartList(), this);
        rvCartView.setAdapter(adapter);
    }

    private void calculateCartTotals() {
        double subtotal = cartManager.getSubTotal();
        double afterDiscount = Math.max(subtotal - currentDiscount, 0);
        double tax = afterDiscount * TAX_PERCENT;
        double total = afterDiscount + DELIVERY_FEE + tax;

        subTotalPriceTxt.setText(String.format(Locale.US, "$%.2f", subtotal));
        deliveryPriceTxt.setText(String.format(Locale.US, "$%.2f", DELIVERY_FEE));
        totalTaxPriceTxt.setText(String.format(Locale.US, "$%.2f", tax));
        totalPriceTxt.setText(String.format(Locale.US, "$%.2f", total));

        if (currentDiscount > 0) {
            discountPriceTxt.setText(String.format(Locale.US, "-$%.2f", currentDiscount));
            discountPriceTxt.setVisibility(View.VISIBLE);
        } else {
            discountPriceTxt.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCartDataChanged() {
        adapter.notifyDataSetChanged();

        if (appliedCoupon != null) {
            CouponManager.CouponResult recheck =
                    CouponManager.applyCoupon(
                            appliedCoupon.coupon.getCode(),
                            cartManager.getSubTotal()
                    );

            if (recheck.isValid) {
                currentDiscount = recheck.discount;
            } else {
                currentDiscount = 0;
                appliedCoupon = null;
                Toast.makeText(this,
                        "Mã giảm giá không còn hợp lệ",
                        Toast.LENGTH_SHORT).show();
            }
        }

        calculateCartTotals();
    }
}
