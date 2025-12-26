package com.example.shopcafe.helper;

import com.example.shopcafe.model.CouponModel;
import java.util.Arrays;
import java.util.List;

public class CouponManager {

    public static List<CouponModel> getAllCoupons() {
        return Arrays.asList(
                new CouponModel("WELCOME10", "Giảm 10% cho đơn đầu tiên", 10, 0),
                new CouponModel("SAVE15", "Giảm 15% cho đơn từ $20", 15, 20),
                new CouponModel("VIP20", "Giảm 20% cho đơn từ $30", 20, 30),
                new CouponModel("FREESHIP", "Giảm $3 phí ship", 3.0, 10, true),
                new CouponModel("SAVE5", "Giảm $5 cho đơn từ $25", 5.0, 25, true)
        );
    }

    public static CouponResult applyCoupon(String code, double subtotal) {
        if (code == null || code.trim().isEmpty()) {
            return new CouponResult(false, "Vui lòng nhập mã", 0, null);
        }

        CouponModel found = null;
        for (CouponModel c : getAllCoupons()) {
            if (c.getCode().equalsIgnoreCase(code.trim())) {
                found = c;
                break;
            }
        }

        if (found == null) {
            return new CouponResult(false, "Mã giảm giá không hợp lệ", 0, null);
        }

        if (subtotal < found.getMinOrderAmount()) {
            return new CouponResult(
                    false,
                    "Đơn tối thiểu $" + found.getMinOrderAmount(),
                    0,
                    null
            );
        }

        double discount = found.getType().equals("PERCENT")
                ? subtotal * found.getDiscountPercent() / 100
                : found.getDiscountAmount();

        discount = Math.min(discount, subtotal);

        return new CouponResult(true, "Áp dụng thành công", discount, found);
    }

    public static class CouponResult {
        public boolean isValid;
        public String message;
        public double discount;
        public CouponModel coupon;

        public CouponResult(boolean isValid, String message, double discount, CouponModel coupon) {
            this.isValid = isValid;
            this.message = message;
            this.discount = discount;
            this.coupon = coupon;
        }
    }
}
