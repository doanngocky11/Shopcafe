package com.example.shopcafe.model;

public class CartItem {
    private ProductModel product;

    private int numberInCart;
    private String size;


    public CartItem(ProductModel product, int numberInCart, String size) {
        this.product = product;
        this.numberInCart = numberInCart;
        this.size = size;
    }

    public ProductModel getProduct() { return product; }

    public int getNumberInCart() { return numberInCart; }

    public void setNumberInCart(int numberInCart) { this.numberInCart = numberInCart; }

    public String getSize() { return size; }


}