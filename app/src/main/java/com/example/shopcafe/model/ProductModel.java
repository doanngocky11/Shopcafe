package com.example.shopcafe.model;


public class ProductModel {
    private String title;
    private double price;
    private int image;

    public ProductModel(String title, double price, int image) {
        this.title = title;
        this.price = price;
        this.image = image;
    }

    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public int getImage() { return image; }
}
