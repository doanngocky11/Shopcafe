package com.example.shopcafe.model;

public class CartItem {
    private ProductModel product;
    private int quantity;
    private String size;

    public CartItem(ProductModel product, int quantity, String size) {
        this.product = product;
        this.quantity = quantity;
        this.size = size;
    }

    public ProductModel getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getSize() { return size; }
}
