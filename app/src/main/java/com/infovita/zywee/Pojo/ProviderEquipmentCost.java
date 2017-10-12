
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProviderEquipmentCost implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("provider_equipment_cost_id")
    @Expose
    private String providerEquipmentCostId;
    @SerializedName("equipment_provider_id")
    @Expose
    private String equipmentProviderId;
    @SerializedName("equipment_speciality_id")
    @Expose
    private String equipmentSpecialityId;
    @SerializedName("equipment_id")
    @Expose
    private String equipmentId;
    @SerializedName("daily_cost")
    @Expose
    private String dailyCost;
    @SerializedName("weekly_cost")
    @Expose
    private String weeklyCost;
    @SerializedName("monthly_cost")
    @Expose
    private String monthlyCost;
    @SerializedName("equipment_booking_advance")
    @Expose
    private Object equipmentBookingAdvance;
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

    public String getProviderEquipmentCostId() {
        return providerEquipmentCostId;
    }

    public void setProviderEquipmentCostId(String providerEquipmentCostId) {
        this.providerEquipmentCostId = providerEquipmentCostId;
    }

    public String getEquipmentProviderId() {
        return equipmentProviderId;
    }

    public void setEquipmentProviderId(String equipmentProviderId) {
        this.equipmentProviderId = equipmentProviderId;
    }

    public String getEquipmentSpecialityId() {
        return equipmentSpecialityId;
    }

    public void setEquipmentSpecialityId(String equipmentSpecialityId) {
        this.equipmentSpecialityId = equipmentSpecialityId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getDailyCost() {
        return dailyCost;
    }

    public void setDailyCost(String dailyCost) {
        this.dailyCost = dailyCost;
    }

    public String getWeeklyCost() {
        return weeklyCost;
    }

    public void setWeeklyCost(String weeklyCost) {
        this.weeklyCost = weeklyCost;
    }

    public String getMonthlyCost() {
        return monthlyCost;
    }

    public void setMonthlyCost(String monthlyCost) {
        this.monthlyCost = monthlyCost;
    }

    public Object getEquipmentBookingAdvance() {
        return equipmentBookingAdvance;
    }

    public void setEquipmentBookingAdvance(Object equipmentBookingAdvance) {
        this.equipmentBookingAdvance = equipmentBookingAdvance;
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
