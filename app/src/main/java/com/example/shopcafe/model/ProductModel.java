package com.example.shopcafe.model;

import java.io.Serializable;
import java.util.List; // BẮT BUỘC: Import List

public class ProductModel implements Serializable {

    // --- KHAI BÁO CÁC TRƯỜNG DỮ LIỆU ---
    private int id;
    private String title;
    private double price;
    private int image;
    private double star; // Rating
    private String description; // Mới
    private List<String> availableSizes; // Mới

    // --- HÀM TẠO ĐÃ SỬA LỖI (7 THAM SỐ) ---
    public ProductModel(int id, String title, double price, int image, double star, String description, List<String> availableSizes) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
        this.star = star;
        this.description = description;
        this.availableSizes = availableSizes;
    }


    public int getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public int getImage() { return image; }
    public double getStar() { return star; }
    public String getDescription() { return description; }
    public List<String> getAvailableSizes() { return availableSizes; }

}