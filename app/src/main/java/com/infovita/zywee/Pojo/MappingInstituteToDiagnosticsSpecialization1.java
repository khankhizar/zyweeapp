
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MappingInstituteToDiagnosticsSpecialization1 implements Serializable {

    @SerializedName("specialization_id")
    @Expose
    private String specializationId;
    @SerializedName("discount_price")
    @Expose
    private String discountPrice;
    @SerializedName("charge")
    @Expose
    private String charge;

    public String getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(String specializationId) {
        this.specializationId = specializationId;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

}
