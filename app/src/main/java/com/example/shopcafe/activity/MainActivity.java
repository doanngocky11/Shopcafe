package com.example.shopcafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopcafe.R;
import com.example.shopcafe.adapter.CategoryAdapter;
import com.example.shopcafe.adapter.OfferAdapter;
import com.example.shopcafe.adapter.PopularAdapter;
import com.example.shopcafe.helper.CartManager;
import com.example.shopcafe.helper.DataHelper;
import com.example.shopcafe.model.CategoryModel;
import com.example.shopcafe.model.OfferModel;
import com.example.shopcafe.model.ProductModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements CategoryAdapter.CategorySelectedListener {

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
            startActivity(new Intent(this, CartActivity.class));
        });

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileScreenActivity.class));
                return true;
            }
            if (id == R.id.card_credit) {
                startActivity(new Intent(this, CreditCardActivity.class));
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

    // ===================== CATEGORY =====================
    private void setupCategoryList() {
        progressBarCategory.setVisibility(View.GONE);

        // ✅ Dùng DataHelper
        List<CategoryModel> categoryList = DataHelper.getAllCategories();

        recyclerViewCategory.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        CategoryAdapter adapter = new CategoryAdapter(categoryList, this);
        recyclerViewCategory.setAdapter(adapter);
    }

    // ===================== POPULAR =====================
    private void setupPopularList() {
        progressBarPopular.setVisibility(View.GONE);

        // ✅ Dùng DataHelper
        List<ProductModel> popularList = DataHelper.getPopularProducts();

        recyclerViewPopular.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        PopularAdapter adapter = new PopularAdapter(popularList, this);
        recyclerViewPopular.setAdapter(adapter);
    }

    // ===================== OFFER =====================
    private void setupOfferList() {
        progressBarOffer.setVisibility(View.GONE);

        // ✅ Dùng DataHelper
        List<OfferModel> offerList = DataHelper.getActiveOffers();

        recyclerViewOffer.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        OfferAdapter adapter = new OfferAdapter(offerList);
        recyclerViewOffer.setAdapter(adapter);
    }

    // ===================== CATEGORY CLICK =====================
    @Override
    public void onCategorySelected(int categoryId, String categoryTitle) {

        // ✅ Lọc sản phẩm theo danh mục
        List<ProductModel> filteredProducts =
                DataHelper.getProductsByCategory(categoryId);

        PopularAdapter adapter =
                new PopularAdapter(filteredProducts, this);
        recyclerViewPopular.setAdapter(adapter);

        Toast.makeText(this,
                "Đã chọn: " + categoryTitle +
                        " (" + filteredProducts.size() + " sản phẩm)",
                Toast.LENGTH_SHORT).show();
    }
}
