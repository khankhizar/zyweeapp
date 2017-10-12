
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EntityHealthInstitut {

    @SerializedName("health_institute_name")
    @Expose
    private String healthInstituteName;
    @SerializedName("payment_option")
    @Expose
    private String paymentOption;

    public String getHealthInstituteName() {
        return healthInstituteName;
    }

    public void setHealthInstituteName(String healthInstituteName) {
        this.healthInstituteName = healthInstituteName;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

}

