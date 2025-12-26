package com.example.shopcafe.model;

public class CouponModel {
    private String code;              // Mã giảm giá (VD: "WELCOME10")
    private String description;       // Mô tả
    private double discountPercent;   // Phần trăm giảm (VD: 10 = 10%)
    private double discountAmount;    // Số tiền giảm cố định (VD: 5.0 = $5)
    private double minOrderAmount;    // Đơn hàng tối thiểu
    private String type;              // "PERCENT" hoặc "FIXED"

    // Constructor cho mã giảm theo phần trăm
    public CouponModel(String code, String description, double discountPercent, double minOrderAmount) {
        this.code = code;
        this.description = description;
        this.discountPercent = discountPercent;
        this.minOrderAmount = minOrderAmount;
        this.type = "PERCENT";
        this.discountAmount = 0;
    }

    // Constructor cho mã giảm số tiền cố định
    public CouponModel(String code, String description, double discountAmount, double minOrderAmount, boolean isFixed) {
        this.code = code;
        this.description = description;
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
        this.type = "FIXED";
        this.discountPercent = 0;
    }

    public String getCode() { return code; }
    public String getDescription() { return description; }
    public double getDiscountPercent() { return discountPercent; }
    public double getDiscountAmount() { return discountAmount; }
    public double getMinOrderAmount() { return minOrderAmount; }
    public String getType() { return type; }
}
