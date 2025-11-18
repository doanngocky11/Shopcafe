package com.example.shopcafe.helper;
import com.example.shopcafe.model.CartItem;
import java.util.ArrayList;

public class CartManager {

    private static CartManager instance;
    private ArrayList<CartItem> cartList = new ArrayList<>();

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public ArrayList<CartItem> getCartList() {
        return cartList;
    }

    public void addToCart(CartItem item) {
        cartList.add(item);
    }

    public double getSubTotal() {
        double total = 0;
        for (CartItem i : cartList) {
            total += i.getProduct().getPrice() * i.getQuantity();
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
}
