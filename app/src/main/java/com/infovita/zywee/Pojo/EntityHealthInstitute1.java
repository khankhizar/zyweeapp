
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EntityHealthInstitute1 implements Serializable {

    @SerializedName("health_institute_id")
    @Expose
    private String healthInstituteId;
    @SerializedName("health_institute_name")
    @Expose
    private String healthInstituteName;
    @SerializedName("has_appointment_booking")
    @Expose
    private String hasAppointmentBooking;
    @SerializedName("health_institute_avg_rating")
    @Expose
    private String healthInstituteAvgRating;
    @SerializedName("payment_option")
    @Expose
    private String paymentOption;

    public String getHealthInstituteId() {
        return healthInstituteId;
    }

    public void setHealthInstituteId(String healthInstituteId) {
        this.healthInstituteId = healthInstituteId;
    }

    public String getHealthInstituteName() {
        return healthInstituteName;
    }

    public void setHealthInstituteName(String healthInstituteName) {
        this.healthInstituteName = healthInstituteName;
    }

    public String getHasAppointmentBooking() {
        return hasAppointmentBooking;
    }

    public void setHasAppointmentBooking(String hasAppointmentBooking) {
        this.hasAppointmentBooking = hasAppointmentBooking;
    }

    public String getHealthInstituteAvgRating() {
        return healthInstituteAvgRating;
    }

    public void setHealthInstituteAvgRating(String healthInstituteAvgRating) {
        this.healthInstituteAvgRating = healthInstituteAvgRating;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

}
