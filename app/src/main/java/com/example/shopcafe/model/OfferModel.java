package com.example.shopcafe.model;

/**
 * Lớp mô hình cho một ưu đãi đặc biệt (Offer).
 */
public class OfferModel {
    private int id;
    private String title;
    private String extra;
    private String picUrl;

    public OfferModel(int id, String title, String extra, String picUrl) {
        this.id = id;
        this.title = title;
        this.extra = extra;
        this.picUrl = picUrl;
    }

    public String getTitle() { return title; }
    public String getExtra() { return extra; }
    public String getPicUrl() { return picUrl; }
}