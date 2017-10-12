
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EntityTreatmentPackage implements Serializable {

    @SerializedName("treatment_package_id")
    @Expose
    private String treatmentPackageId;
    @SerializedName("treatment_package_name")
    @Expose
    private String treatmentPackageName;
    @SerializedName("treatment_package_desc")
    @Expose
    private String treatmentPackageDesc;
    @SerializedName("treatment_package_rate")
    @Expose
    private String treatmentPackageRate;
    @SerializedName("package_discount")
    @Expose
    private String packageDiscount;
    @SerializedName("health_institute_id")
    @Expose
    private String healthInstituteId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("working_plan")
    @Expose
    private String workingPlan;
    @SerializedName("notifications")
    @Expose
    private String notifications;
    @SerializedName("time_slot")
    @Expose
    private String timeSlot;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("sub_type")
    @Expose
    private String subType;

    public String getTreatmentPackageId() {
        return treatmentPackageId;
    }

    public void setTreatmentPackageId(String treatmentPackageId) {
        this.treatmentPackageId = treatmentPackageId;
    }

    public String getTreatmentPackageName() {
        return treatmentPackageName;
    }

    public void setTreatmentPackageName(String treatmentPackageName) {
        this.treatmentPackageName = treatmentPackageName;
    }

    public String getTreatmentPackageDesc() {
        return treatmentPackageDesc;
    }

    public void setTreatmentPackageDesc(String treatmentPackageDesc) {
        this.treatmentPackageDesc = treatmentPackageDesc;
    }

    public String getTreatmentPackageRate() {
        return treatmentPackageRate;
    }

    public void setTreatmentPackageRate(String treatmentPackageRate) {
        this.treatmentPackageRate = treatmentPackageRate;
    }

    public String getPackageDiscount() {
        return packageDiscount;
    }

    public void setPackageDiscount(String packageDiscount) {
        this.packageDiscount = packageDiscount;
    }

    public String getHealthInstituteId() {
        return healthInstituteId;
    }

    public void setHealthInstituteId(String healthInstituteId) {
        this.healthInstituteId = healthInstituteId;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkingPlan() {
        return workingPlan;
    }

    public void setWorkingPlan(String workingPlan) {
        this.workingPlan = workingPlan;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

}
