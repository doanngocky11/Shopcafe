package com.example.shopcafe.helper;

import com.example.shopcafe.R;
import com.example.shopcafe.model.CategoryModel;
import com.example.shopcafe.model.OfferModel;
import com.example.shopcafe.model.ProductModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DataHelper - Quản lý tất cả dữ liệu giả (Mock Data) cho ứng dụng
 * Bao gồm: Products, Categories, Offers
 *
 * Lưu ý: File này khác với DatabaseHelper
 * - DatabaseHelper: Quản lý dữ liệu thật từ SQLite (User data)
 * - DataHelper: Quản lý dữ liệu giả cho Products, Categories, Offers
 */
public class DataHelper {

    // ==================== PRODUCTS DATA ====================

    /**
     * Lấy danh sách tất cả sản phẩm
     */
    public static List<ProductModel> getAllProducts() {
        return Arrays.asList(
                new ProductModel(1, "Cappuccino", 4.5, R.drawable.cappuccino, 4.8,
                        "Cà phê truyền thống Ý với bọt sữa dày và mịn.",
                        Arrays.asList("S", "M", "L")),

                new ProductModel(2, "Espresso Double", 3.0, R.drawable.espresso, 4.5,
                        "Ly espresso mạnh gấp đôi, thích hợp cho buổi sáng.",
                        Arrays.asList("Single", "Double")),

                new ProductModel(3, "Vietnamese Iced Coffee", 5.2, R.drawable.vietnamese_coffee, 4.9,
                        "Cà phê sữa đá Việt Nam truyền thống, ngọt và đậm đà.",
                        Arrays.asList("L", "XL")),

                new ProductModel(4, "Latte", 5.5, R.drawable.latte, 4.7,
                        "Latte thơm béo, được yêu thích nhất.",
                        Arrays.asList("M", "L")),

                new ProductModel(5, "Americano", 3.5, R.drawable.americano, 4.6,
                        "Cà phê pha loãng kiểu Mỹ, thanh nhẹ.",
                        Arrays.asList("M", "L")),

                new ProductModel(6, "Cold Brew", 4.8, R.drawable.cold_brew, 4.9,
                        "Cà phê ủ lạnh 12 tiếng, mượt mà.",
                        Arrays.asList("M", "L")),

                new ProductModel(7, "Flat White", 5.0, R.drawable.flat_white, 4.7,
                        "Espresso pha với sữa mịn kiểu Úc.",
                        Arrays.asList("S", "M")),

                new ProductModel(8, "Macchiato", 4.2, R.drawable.macchiato, 4.6,
                        "Espresso với một chút bọt sữa.",
                        Arrays.asList("Single", "Double")),

                new ProductModel(9, "Mocha", 5.8, R.drawable.mocha, 4.8,
                        "Cà phê socola kem tươi.",
                        Arrays.asList("M", "L")),

                new ProductModel(10, "Iced Coffee", 4.0, R.drawable.iced_coffee, 4.5,
                        "Cà phê đá truyền thống.",
                        Arrays.asList("M", "L"))
        );
    }

    /**
     * Lấy danh sách sản phẩm phổ biến (Popular)
     * Hiển thị trong MainActivity
     */
    public static List<ProductModel> getPopularProducts() {
        // Trả về top 4 sản phẩm có rating cao nhất
        return Arrays.asList(
                new ProductModel(3, "Vietnamese Iced Coffee", 5.2, R.drawable.vietnamese_coffee, 4.9,
                        "Cà phê sữa đá Việt Nam truyền thống, ngọt và đậm đà.",
                        Arrays.asList("L", "XL")),

                new ProductModel(6, "Cold Brew", 4.8, R.drawable.cold_brew, 4.9,
                        "Cà phê ủ lạnh 12 tiếng, mượt mà.",
                        Arrays.asList("M", "L")),

                new ProductModel(1, "Cappuccino", 4.5, R.drawable.cappuccino, 4.8,
                        "Cà phê truyền thống Ý với bọt sữa dày và mịn.",
                        Arrays.asList("S", "M", "L")),

                new ProductModel(9, "Mocha", 5.8, R.drawable.mocha, 4.8,
                        "Cà phê socola kem tươi.",
                        Arrays.asList("M", "L"))
        );
    }

    /**
     * Lọc sản phẩm theo danh mục
     * @param categoryId ID của danh mục (1=Espresso, 2=Latte, 3=Cappuccino, ...)
     * @return Danh sách sản phẩm thuộc danh mục đó
     */
    public static List<ProductModel> getProductsByCategory(int categoryId) {
        List<ProductModel> filteredList = new ArrayList<>();
        List<ProductModel> allProducts = getAllProducts();

        switch (categoryId) {
            case 1: // Espresso
                for (ProductModel p : allProducts) {
                    if (p.getTitle().toLowerCase().contains("espresso")) {
                        filteredList.add(p);
                    }
                }
                break;

            case 2: // Latte
                for (ProductModel p : allProducts) {
                    if (p.getTitle().toLowerCase().contains("latte")) {
                        filteredList.add(p);
                    }
                }
                break;

            case 3: // Cappuccino
                for (ProductModel p : allProducts) {
                    if (p.getTitle().toLowerCase().contains("cappuccino")) {
                        filteredList.add(p);
                    }
                }
                break;

            case 4: // Filter Coffee
                for (ProductModel p : allProducts) {
                    String title = p.getTitle().toLowerCase();
                    if (title.contains("americano") || title.contains("cold brew")) {
                        filteredList.add(p);
                    }
                }
                break;

            case 5: // Iced Drinks
                for (ProductModel p : allProducts) {
                    String title = p.getTitle().toLowerCase();
                    if (title.contains("iced") || title.contains("cold")) {
                        filteredList.add(p);
                    }
                }
                break;

            default:
                filteredList = allProducts;
        }

        return filteredList;
    }

    /**
     * Tìm kiếm sản phẩm theo tên
     */
    public static List<ProductModel> searchProducts(String keyword) {
        List<ProductModel> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase().trim();

        for (ProductModel p : getAllProducts()) {
            if (p.getTitle().toLowerCase().contains(lowerKeyword) ||
                    p.getDescription().toLowerCase().contains(lowerKeyword)) {
                results.add(p);
            }
        }

        return results;
    }

    /**
     * Lấy sản phẩm theo ID
     */
    public static ProductModel getProductById(int productId) {
        for (ProductModel p : getAllProducts()) {
            if (p.getId() == productId) {
                return p;
            }
        }
        return null;
    }

    // ==================== CATEGORIES DATA ====================

    /**
     * Lấy danh sách tất cả danh mục
     */
    public static List<CategoryModel> getAllCategories() {
        return Arrays.asList(
                new CategoryModel(1, "Espresso"),
                new CategoryModel(2, "Latte"),
                new CategoryModel(3, "Cappuccino"),
                new CategoryModel(4, "Filter Coffee"),
                new CategoryModel(5, "Iced Drinks")
        );
    }

    /**
     * Lấy tên danh mục theo ID
     */
    public static String getCategoryName(int categoryId) {
        for (CategoryModel c : getAllCategories()) {
            if (c.getId() == categoryId) {
                return c.getTitle();
            }
        }
        return "All";
    }

    // ==================== OFFERS DATA ====================

    /**
     * Lấy danh sách tất cả ưu đãi/khuyến mãi
     */
    public static List<OfferModel> getAllOffers() {
        return Arrays.asList(
                new OfferModel(1, "Free Medium Latte", "Khi mua 2 món", "pic_offer1"),
                new OfferModel(2, "Giảm 15%", "Cho đơn hàng trên $15", "pic_offer3"),
                new OfferModel(3, "Món mới", "Dâu tây kem tươi", "pic_offer2")
        );
    }

    /**
     * Lấy ưu đãi đang hoạt động (active offers)
     * Có thể mở rộng thêm logic kiểm tra thời gian
     */
    public static List<OfferModel> getActiveOffers() {
        // Hiện tại trả về top 3 offers
        List<OfferModel> allOffers = getAllOffers();
        return allOffers.subList(0, Math.min(3, allOffers.size()));
    }

    // ==================== THỐNG KÊ ====================

    /**
     * Đếm tổng số sản phẩm
     */
    public static int getTotalProductCount() {
        return getAllProducts().size();
    }

    /**
     * Đếm tổng số danh mục
     */
    public static int getTotalCategoryCount() {
        return getAllCategories().size();
    }

    /**
     * Lấy sản phẩm có rating cao nhất
     */
    public static ProductModel getTopRatedProduct() {
        List<ProductModel> products = getAllProducts();
        ProductModel topProduct = products.get(0);

        for (ProductModel p : products) {
            if (p.getStar() > topProduct.getStar()) {
                topProduct = p;
            }
        }

        return topProduct;
    }

    /**
     * Lấy sản phẩm rẻ nhất
     */
    public static ProductModel getCheapestProduct() {
        List<ProductModel> products = getAllProducts();
        ProductModel cheapest = products.get(0);

        for (ProductModel p : products) {
            if (p.getPrice() < cheapest.getPrice()) {
                cheapest = p;
            }
        }

        return cheapest;
    }

    /**
     * Lấy sản phẩm đắt nhất
     */
    public static ProductModel getMostExpensiveProduct() {
        List<ProductModel> products = getAllProducts();
        ProductModel expensive = products.get(0);

        for (ProductModel p : products) {
            if (p.getPrice() > expensive.getPrice()) {
                expensive = p;
            }
        }

        return expensive;
    }
}