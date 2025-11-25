package com.example.shopcafe.model;

/**
 * Lớp mô hình cho một danh mục sản phẩm (ví dụ: Latte, Cappuccino).
 */
public class CategoryModel {
    private int id;
    private String title;

    public CategoryModel(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
}