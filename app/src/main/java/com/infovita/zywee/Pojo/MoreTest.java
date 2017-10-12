
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MoreTest implements Serializable {

    @SerializedName("MappingInstituteToDiagnosticsSpecialization")
    @Expose
    private MappingInstituteToDiagnosticsSpecialization mappingInstituteToDiagnosticsSpecialization;
    @SerializedName("MasterDiagnosticsSpecialization")
    @Expose
    private MasterDiagnosticsSpecialization masterDiagnosticsSpecialization;
    @SerializedName("TestInstituteSpecialization")
    @Expose
    private TestInstituteSpecialization testInstituteSpecialization;
    @SerializedName("EntityHealthInstitute")
    @Expose
    private MoreEntityHealthInstitute entityHealthInstitute;
    @SerializedName("discount_price")
    @Expose
    private Integer discountPrice;

    public MappingInstituteToDiagnosticsSpecialization getMappingInstituteToDiagnosticsSpecialization() {
        return mappingInstituteToDiagnosticsSpecialization;
    }

    public void setMappingInstituteToDiagnosticsSpecialization(MappingInstituteToDiagnosticsSpecialization mappingInstituteToDiagnosticsSpecialization) {
        this.mappingInstituteToDiagnosticsSpecialization = mappingInstituteToDiagnosticsSpecialization;
    }

    public MasterDiagnosticsSpecialization getMasterDiagnosticsSpecialization() {
        return masterDiagnosticsSpecialization;
    }

    public void setMasterDiagnosticsSpecialization(MasterDiagnosticsSpecialization masterDiagnosticsSpecialization) {
        this.masterDiagnosticsSpecialization = masterDiagnosticsSpecialization;
    }

    public TestInstituteSpecialization getTestInstituteSpecialization() {
        return testInstituteSpecialization;
    }

    public void setTestInstituteSpecialization(TestInstituteSpecialization testInstituteSpecialization) {
        this.testInstituteSpecialization = testInstituteSpecialization;
    }

    public MoreEntityHealthInstitute getEntityHealthInstitute() {
        return entityHealthInstitute;
    }

    public void setEntityHealthInstitute(MoreEntityHealthInstitute entityHealthInstitute) {
        this.entityHealthInstitute = entityHealthInstitute;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

}
