package com.example.shopcafe.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopcafe.R;
import com.example.shopcafe.adapter.SizeAdapter;
import com.example.shopcafe.helper.CartManager;
import com.example.shopcafe.model.CartItem; // BẮT BUỘC: Import CartItem
import com.example.shopcafe.model.ProductModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Locale;
public class DetailedActivity extends BaseActivity implements SizeAdapter.OnSizeSelectedListener {
    private ProductModel product;
    private CartManager cartManager;
    private TextView titleTxt, priceTxt, descriptionTxt, numberItemTxt;
    private ImageView ivBack, ivFavourite;
    private TextView plusCart, minusCart;
    private RatingBar ratingBar;
    private AppCompatButton addToCartBtn;
    private ShapeableImageView productPic;
    private RecyclerView rvSizeList;
    private int numberOrder = 1;
    private SizeAdapter sizeAdapter;
    private String currentSelectedSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        cartManager = CartManager.getInstance();
        getBundle();
        initView();
        setVariable();
        setupSizeList();
    }
    private void initView() {
        titleTxt = findViewById(R.id.titleTxt);
        priceTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberItemTxt = findViewById(R.id.numberItemTxt);
        ivBack = findViewById(R.id.ivBack);
        ivFavourite = findViewById(R.id.ivFavourite);
        plusCart = findViewById(R.id.plusCart);
        minusCart = findViewById(R.id.minusCart);
        ratingBar = findViewById(R.id.ratingBar);
        addToCartBtn = findViewById(R.id.addToCart);
        productPic = findViewById(R.id.shapeableImageView);
        rvSizeList = findViewById(R.id.rvSizeList);
    }
    private void getBundle() {
        product = (ProductModel) getIntent().getSerializableExtra("object");
        if (product == null) {
            Toast.makeText(DetailedActivity.this, "Không tìm thấy thông tin sản phẩm.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    private void setVariable() {
        if (product == null) return;
        titleTxt.setText(product.getTitle());
        updatePrice();
        descriptionTxt.setText(product.getDescription());
        ratingBar.setRating((float) product.getStar());

        numberItemTxt.setText(String.valueOf(numberOrder));

        ivBack.setOnClickListener(v -> finish());

        ivFavourite.setOnClickListener(v -> {
            Toast.makeText(DetailedActivity.this, "Đã thêm/Xóa khỏi mục Yêu thích!", Toast.LENGTH_SHORT).show();
        });


        plusCart.setOnClickListener(v -> {
            numberOrder = numberOrder + 1;
            numberItemTxt.setText(String.valueOf(numberOrder));
            updatePrice();
        });


        minusCart.setOnClickListener(v -> {
            if (numberOrder > 1) {
                numberOrder = numberOrder - 1;
                numberItemTxt.setText(String.valueOf(numberOrder));
                updatePrice();
            }
        });


        addToCartBtn.setOnClickListener(v -> {
            if (currentSelectedSize == null || currentSelectedSize.isEmpty()) {
                Toast.makeText(DetailedActivity.this, "Vui lòng chọn kích cỡ!", Toast.LENGTH_SHORT).show();
                return;
            }


            CartItem newItem = new CartItem(product, numberOrder, currentSelectedSize);
            cartManager.addToCart(newItem);

            Toast.makeText(getApplicationContext(), "Đã thêm " + numberOrder + " " + product.getTitle() + " (" + currentSelectedSize + ") vào giỏ hàng.", Toast.LENGTH_LONG).show();
            finish();
        });


        if (product.getAvailableSizes() != null && !product.getAvailableSizes().isEmpty()) {
            currentSelectedSize = product.getAvailableSizes().get(0);
        }
    }

    private void setupSizeList() {

        if (product.getAvailableSizes() != null) {
            rvSizeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            sizeAdapter = new SizeAdapter(product.getAvailableSizes(), this);
            rvSizeList.setAdapter(sizeAdapter);
        }
    }

    private void updatePrice() {
        double totalItemPrice = product.getPrice() * numberOrder;
        priceTxt.setText(String.format(Locale.US, "$%.2f", totalItemPrice));
    }

    @Override
    public void onSizeSelected(String size) {
        currentSelectedSize = size;
    }
}