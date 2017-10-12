
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Appointment implements Serializable {

    @SerializedName("appointment_id")
    @Expose
    private String appointmentId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("end_datetime")
    @Expose
    private String endDatetime;
    @SerializedName("total_cost")
    @Expose
    private String totalCost;
    @SerializedName("collection_cost")
    @Expose
    private String collectionCost;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;
    @SerializedName("provider_name")
    @Expose
    private String providerName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("appointment_title")
    @Expose
    private String appointmentTitle;
    @SerializedName("charge_type")
    @Expose
    private Object chargeType;
    @SerializedName("speciality_name")
    @Expose
    private Object specialityName;

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getCollectionCost() {
        return collectionCost;
    }

    public void setCollectionCost(String collectionCost) {
        this.collectionCost = collectionCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    public Object getChargeType() {
        return chargeType;
    }

    public void setChargeType(Object chargeType) {
        this.chargeType = chargeType;
    }

    public Object getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(Object specialityName) {
        this.specialityName = specialityName;
    }

}