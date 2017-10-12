
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProviderServiceCharge implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("provider_home_service_id")
    @Expose
    private String providerHomeServiceId;
    @SerializedName("home_service_charge_id")
    @Expose
    private String homeServiceChargeId;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("discounted_prize")
    @Expose
    private String discountedPrize;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProviderHomeServiceId() {
        return providerHomeServiceId;
    }

    public void setProviderHomeServiceId(String providerHomeServiceId) {
        this.providerHomeServiceId = providerHomeServiceId;
    }

    public String getHomeServiceChargeId() {
        return homeServiceChargeId;
    }

    public void setHomeServiceChargeId(String homeServiceChargeId) {
        this.homeServiceChargeId = homeServiceChargeId;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountedPrize() {
        return discountedPrize;
    }

    public void setDiscountedPrize(String discountedPrize) {
        this.discountedPrize = discountedPrize;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

}
