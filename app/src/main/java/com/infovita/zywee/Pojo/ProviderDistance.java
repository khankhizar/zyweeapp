
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProviderDistance implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("provider_id")
    @Expose
    private String providerId;
    @SerializedName("distance_id")
    @Expose
    private String distanceId;
    @SerializedName("provider_vehicle_id")
    @Expose
    private String providerVehicleId;
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

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getDistanceId() {
        return distanceId;
    }

    public void setDistanceId(String distanceId) {
        this.distanceId = distanceId;
    }

    public String getProviderVehicleId() {
        return providerVehicleId;
    }

    public void setProviderVehicleId(String providerVehicleId) {
        this.providerVehicleId = providerVehicleId;
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
