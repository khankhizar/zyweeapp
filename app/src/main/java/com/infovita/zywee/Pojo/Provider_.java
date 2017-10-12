
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Provider_ implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("provider_name")
    @Expose
    private String providerName;
    @SerializedName("provider_address")
    @Expose
    private String providerAddress;
    @SerializedName("master_state_id")
    @Expose
    private String masterStateId;
    @SerializedName("master_city_id")
    @Expose
    private String masterCityId;
    @SerializedName("master_locality_id")
    @Expose
    private String masterLocalityId;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("avg_rating")
    @Expose
    private String avgRating;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("manager_email")
    @Expose
    private String managerEmail;
    @SerializedName("manager_phone")
    @Expose
    private String managerPhone;
    @SerializedName("payment_option")
    @Expose
    private String paymentOption;
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

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderAddress() {
        return providerAddress;
    }

    public void setProviderAddress(String providerAddress) {
        this.providerAddress = providerAddress;
    }

    public String getMasterStateId() {
        return masterStateId;
    }

    public void setMasterStateId(String masterStateId) {
        this.masterStateId = masterStateId;
    }

    public String getMasterCityId() {
        return masterCityId;
    }

    public void setMasterCityId(String masterCityId) {
        this.masterCityId = masterCityId;
    }

    public String getMasterLocalityId() {
        return masterLocalityId;
    }

    public void setMasterLocalityId(String masterLocalityId) {
        this.masterLocalityId = masterLocalityId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(String avgRating) {
        this.avgRating = avgRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
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
