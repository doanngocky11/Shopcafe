package com.example.shopcafe.helper;

import com.example.shopcafe.R; // Cần import R để dùng ID hình ảnh
import com.example.shopcafe.model.CartItem;
import com.example.shopcafe.model.ProductModel; // Cần import ProductModel
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays; // Cần import Arrays để tạo dữ liệu giả
import java.lang.Math;

public class CartManager {

    private static CartManager instance;
    private ArrayList<CartItem> cartList = new ArrayList<>();

    // Khai báo Listener để CartActivity/Fragment có thể lắng nghe sự thay đổi
    private ChangeNumberItemsListener changeNumberItemsListener;

    private CartManager() {
        // Private constructor
    }

    /**
     * Trả về instance duy nhất của CartManager (Singleton Pattern)
     */
    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    // --- Quản lý Listener ---

    /**
     * Thiết lập Listener để thông báo cho Activity/Fragment khi dữ liệu giỏ hàng thay đổi.
     */
    public void setChangeNumberItemsListener(ChangeNumberItemsListener changeNumberItemsListener) {
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    // --- Các phương thức cơ bản và Tính toán ---

    public ArrayList<CartItem> getCartList() {
        return cartList;
    }

    public void addToCart(CartItem item) {
        // *Logic nâng cao: Nếu sản phẩm (và size) đã tồn tại, chỉ tăng số lượng
        boolean itemExists = false;
        for (CartItem existingItem : cartList) {
            if (existingItem.getProduct().getId() == item.getProduct().getId() &&
                    existingItem.getSize().equals(item.getSize())) {

                existingItem.setNumberInCart(existingItem.getNumberInCart() + item.getNumberInCart());
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            cartList.add(item);
        }

        // Sau khi thêm/cập nhật, thông báo cập nhật
        if (changeNumberItemsListener != null) {
            changeNumberItemsListener.onCartDataChanged();
        }
    }

    public double getSubTotal() {
        double total = 0;
        for (CartItem i : cartList) {
            total += i.getProduct().getPrice() * i.getNumberInCart();
        }
        return total;
    }

    public double getTax() {
        return Math.round(getSubTotal() * 0.02 * 100) / 100.0;
    }

    public double getDelivery() {
        return 15.0;
    }

    public double getTotal() {
        return Math.round((getSubTotal() + getTax() + getDelivery()) * 100) / 100.0;
    }

    // --- LOGIC QUẢN LÝ SỐ LƯỢNG (Dùng cho CartAdapter) ---

    public void plusNumberItem(List<CartItem> listItems, int position, Runnable callback) {
        listItems.get(position).setNumberInCart(listItems.get(position).getNumberInCart() + 1);

        callback.run();

        if (changeNumberItemsListener != null) {
            changeNumberItemsListener.onCartDataChanged();
        }
    }

    public void minusNumberItem(List<CartItem> listItems, int position, Runnable callback) {
        int currentNum = listItems.get(position).getNumberInCart();

        if (currentNum == 1) {
            // Xóa item khỏi danh sách nếu số lượng về 0
            listItems.remove(position);
        } else {
            listItems.get(position).setNumberInCart(currentNum - 1);
        }

        callback.run();

        if (changeNumberItemsListener != null) {
            changeNumberItemsListener.onCartDataChanged();
        }
    }

    public List<ProductModel> getPopularProducts() {

        return Arrays.asList(
                new ProductModel(1, "Cappuccino", 4.5, R.drawable.coffee_cup_1, 4.8, "Cà phê truyền thống Ý với bọt sữa dày và mịn.", Arrays.asList("S", "M", "L")),
                new ProductModel(2, "Espresso Double", 3.0, R.drawable.coffee_cup_1, 4.5, "Ly espresso mạnh gấp đôi, thích hợp cho buổi sáng.", Arrays.asList("Single", "Double")),
                new ProductModel(3, "Vietnamese Iced Coffee", 5.2, R.drawable.coffee_cup_1, 4.9, "Cà phê sữa đá Việt Nam truyền thống, ngọt và đậm đà.", Arrays.asList("L", "XL")),
                new ProductModel(4, "Vanilla Latte", 5.5, R.drawable.coffee_cup_1, 4.7, "Latte thơm béo vị vani, được yêu thích nhất.", Arrays.asList("M", "L"))
        );
    }
}