package com.example.shopcafe.helper;

import com.example.shopcafe.model.CartItem;
import java.util.ArrayList;
import java.util.List; // ✅ Thêm import này
import java.lang.Math;

public class CartManager {

    private static CartManager instance;
    private ArrayList<CartItem> cartList = new ArrayList<>();
    private ChangeNumberItemsListener changeNumberItemsListener;

    private CartManager() {}

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void setChangeNumberItemsListener(ChangeNumberItemsListener changeNumberItemsListener) {
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    public ArrayList<CartItem> getCartList() {
        return cartList;
    }

    public void addToCart(CartItem item) {
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

    // ✅ ĐỔI ArrayList THÀNH List
    public void plusNumberItem(List<CartItem> listItems, int position, Runnable callback) {
        listItems.get(position).setNumberInCart(listItems.get(position).getNumberInCart() + 1);
        callback.run();
        if (changeNumberItemsListener != null) {
            changeNumberItemsListener.onCartDataChanged();
        }
    }

    // ✅ ĐỔI ArrayList THÀNH List
    public void minusNumberItem(List<CartItem> listItems, int position, Runnable callback) {
        int currentNum = listItems.get(position).getNumberInCart();
        if (currentNum == 1) {
            listItems.remove(position);
        } else {
            listItems.get(position).setNumberInCart(currentNum - 1);
        }
        callback.run();
        if (changeNumberItemsListener != null) {
            changeNumberItemsListener.onCartDataChanged();
        }
    }
}