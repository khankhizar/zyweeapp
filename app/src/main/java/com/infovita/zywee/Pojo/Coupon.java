
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Coupon implements Serializable {

    @SerializedName("ZyweeCoupon")
    @Expose
    private ZyweeCoupon zyweeCoupon;

    public ZyweeCoupon getZyweeCoupon() {
        return zyweeCoupon;
    }

    public void setZyweeCoupon(ZyweeCoupon zyweeCoupon) {
        this.zyweeCoupon = zyweeCoupon;
    }

}
