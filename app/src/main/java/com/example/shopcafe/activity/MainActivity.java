package com.example.shopcafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.shopcafe.R;
import com.example.shopcafe.adapter.CategoryAdapter;
import com.example.shopcafe.adapter.CategoryAdapter.CategorySelectedListener;
import com.example.shopcafe.adapter.OfferAdapter;
import com.example.shopcafe.adapter.PopularAdapter;
import com.example.shopcafe.helper.CartManager;
import com.example.shopcafe.model.CategoryModel;
import com.example.shopcafe.model.OfferModel;
import com.example.shopcafe.model.ProductModel;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CategorySelectedListener {

    private RecyclerView recyclerViewCategory, recyclerViewPopular, recyclerViewOffer;
    private ProgressBar progressBarCategory, progressBarPopular, progressBarOffer;
    private FloatingActionButton cartBtn;
    private CartManager cartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cartManager = CartManager.getInstance();

        initView();
        setupCategoryList();
        setupPopularList();
        setupOfferList();

        cartBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });

        
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnItemSelectedListener(item -> {

            int id = item.getItemId();



            if (id == R.id.nav_profile) {
                Intent intent = new Intent(MainActivity.this, ProfileScreenActivity.class);
                startActivity(intent);
                return true;
            }
            if (id == R.id.card_credit) {
                Intent intent = new Intent(MainActivity.this, CreditCardActivity.class);
                startActivity(intent);
                return true;
            }



            return false;
        });
    }

    private void initView() {
        recyclerViewCategory = findViewById(R.id.recyclerViewCategory);
        recyclerViewPopular = findViewById(R.id.recyclerViewPopular);
        recyclerViewOffer = findViewById(R.id.recyclerViewOffer);

        progressBarCategory = findViewById(R.id.progressBarCategory);
        progressBarPopular = findViewById(R.id.progressBarPopular);
        progressBarOffer = findViewById(R.id.progressBarOffer);

        cartBtn = findViewById(R.id.cartBtn);
    }

    private void setupCategoryList() {
        progressBarCategory.setVisibility(View.GONE);

        List<CategoryModel> categoryList = Arrays.asList(
                new CategoryModel(1, "Espresso"),
                new CategoryModel(2, "Latte"),
                new CategoryModel(3, "Cappuccino"),
                new CategoryModel(4, "Filter Coffee"),
                new CategoryModel(5, "Iced Drinks")
        );

        recyclerViewCategory.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        CategoryAdapter adapter = new CategoryAdapter(categoryList, this);
        recyclerViewCategory.setAdapter(adapter);
    }

    private void setupPopularList() {
        progressBarPopular.setVisibility(View.GONE);

        List<ProductModel> popularList = cartManager.getPopularProducts();
        recyclerViewPopular.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        PopularAdapter adapter = new PopularAdapter(popularList, this);
        recyclerViewPopular.setAdapter(adapter);
    }

    private void setupOfferList() {
        progressBarOffer.setVisibility(View.GONE);

        List<OfferModel> offerList = Arrays.asList(
                new OfferModel(1, "Free Medium Latte", "Khi mua 2 món", "pic_offer1"),
                new OfferModel(2, "Giảm 15%", "Cho đơn hàng trên $15", "pic_offer2"),
                new OfferModel(3, "Món mới", "Dâu tây kem tươi", "pic_offer3")
        );

        recyclerViewOffer.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        OfferAdapter adapter = new OfferAdapter(offerList);
        recyclerViewOffer.setAdapter(adapter);
    }

    @Override
    public void onCategorySelected(int categoryId, String categoryTitle) {
        Toast.makeText(this,
                "Đã chọn danh mục: " + categoryTitle + " (ID: " + categoryId + ")",
                Toast.LENGTH_SHORT).show();
    }
}
