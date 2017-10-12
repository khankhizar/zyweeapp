
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MasterDiagnosticsSpecialization1 implements Serializable {

    @SerializedName("specialization_id")
    @Expose
    private String specializationId;
    @SerializedName("diagnostics_id")
    @Expose
    private String diagnosticsId;
    @SerializedName("specialization_name")
    @Expose
    private String specializationName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("test_type")
    @Expose
    private String testType;
    @SerializedName("specialization_discount")
    @Expose
    private String specializationDiscount;
    @SerializedName("flag")
    @Expose
    private String flag;

    public String getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(String specializationId) {
        this.specializationId = specializationId;
    }

    public String getDiagnosticsId() {
        return diagnosticsId;
    }

    public void setDiagnosticsId(String diagnosticsId) {
        this.diagnosticsId = diagnosticsId;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getSpecializationDiscount() {
        return specializationDiscount;
    }

    public void setSpecializationDiscount(String specializationDiscount) {
        this.specializationDiscount = specializationDiscount;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
