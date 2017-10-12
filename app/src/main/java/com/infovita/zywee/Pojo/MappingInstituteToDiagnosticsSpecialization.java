
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MappingInstituteToDiagnosticsSpecialization implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("specialization_id")
    @Expose
    private String specializationId;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("first_name")
    @Expose
    private Object firstName;
    @SerializedName("last_name")
    @Expose
    private Object lastName;
    @SerializedName("mobile_number")
    @Expose
    private Object mobileNumber;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("test_method")
    @Expose
    private Object testMethod;
    @SerializedName("sample_type")
    @Expose
    private Object sampleType;
    @SerializedName("analysis_time")
    @Expose
    private Object analysisTime;
    @SerializedName("charge")
    @Expose
    private String charge;
    @SerializedName("discount_price")
    @Expose
    private String discountPrice;
    @SerializedName("notes")
    @Expose
    private Object notes;
    @SerializedName("working_plan")
    @Expose
    private String workingPlan;
    @SerializedName("notifications")
    @Expose
    private Object notifications;
    @SerializedName("time_slot")
    @Expose
    private String timeSlot;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(String specializationId) {
        this.specializationId = specializationId;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public Object getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Object mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getTestMethod() {
        return testMethod;
    }

    public void setTestMethod(Object testMethod) {
        this.testMethod = testMethod;
    }

    public Object getSampleType() {
        return sampleType;
    }

    public void setSampleType(Object sampleType) {
        this.sampleType = sampleType;
    }

    public Object getAnalysisTime() {
        return analysisTime;
    }

    public void setAnalysisTime(Object analysisTime) {
        this.analysisTime = analysisTime;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
        this.notes = notes;
    }

    public String getWorkingPlan() {
        return workingPlan;
    }

    public void setWorkingPlan(String workingPlan) {
        this.workingPlan = workingPlan;
    }

    public Object getNotifications() {
        return notifications;
    }

    public void setNotifications(Object notifications) {
        this.notifications = notifications;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

}
