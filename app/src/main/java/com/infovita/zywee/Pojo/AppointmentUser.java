
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentUser {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ea_appointment_id")
    @Expose
    private String eaAppointmentId;
    @SerializedName("appointment_title")
    @Expose
    private String appointmentTitle;
    @SerializedName("registration_id")
    @Expose
    private String registrationId;
    @SerializedName("executive_id")
    @Expose
    private String executiveId;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("zcash_used")
    @Expose
    private String zcashUsed;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;
    @SerializedName("dr_reference")
    @Expose
    private String drReference;
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

    public String getEaAppointmentId() {
        return eaAppointmentId;
    }

    public void setEaAppointmentId(String eaAppointmentId) {
        this.eaAppointmentId = eaAppointmentId;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getExecutiveId() {
        return executiveId;
    }

    public void setExecutiveId(String executiveId) {
        this.executiveId = executiveId;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getZcashUsed() {
        return zcashUsed;
    }

    public void setZcashUsed(String zcashUsed) {
        this.zcashUsed = zcashUsed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getDrReference() {
        return drReference;
    }

    public void setDrReference(String drReference) {
        this.drReference = drReference;
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
