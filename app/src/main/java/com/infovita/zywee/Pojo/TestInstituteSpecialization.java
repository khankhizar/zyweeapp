
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestInstituteSpecialization implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("test_category_id")
    @Expose
    private String testCategoryId;
    @SerializedName("sub_test_id")
    @Expose
    private String subTestId;
    @SerializedName("mapping_institute_diagnostic_id")
    @Expose
    private String mappingInstituteDiagnosticId;
    @SerializedName("diagnostics_specialization_id")
    @Expose
    private String diagnosticsSpecializationId;
    @SerializedName("test_type_discount")
    @Expose
    private String testTypeDiscount;
    @SerializedName("test_popularity")
    @Expose
    private String testPopularity;
    @SerializedName("home_page_listing")
    @Expose
    private String homePageListing;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("created")
    @Expose
    private String created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestCategoryId() {
        return testCategoryId;
    }

    public void setTestCategoryId(String testCategoryId) {
        this.testCategoryId = testCategoryId;
    }

    public String getSubTestId() {
        return subTestId;
    }

    public void setSubTestId(String subTestId) {
        this.subTestId = subTestId;
    }

    public String getMappingInstituteDiagnosticId() {
        return mappingInstituteDiagnosticId;
    }

    public void setMappingInstituteDiagnosticId(String mappingInstituteDiagnosticId) {
        this.mappingInstituteDiagnosticId = mappingInstituteDiagnosticId;
    }

    public String getDiagnosticsSpecializationId() {
        return diagnosticsSpecializationId;
    }

    public void setDiagnosticsSpecializationId(String diagnosticsSpecializationId) {
        this.diagnosticsSpecializationId = diagnosticsSpecializationId;
    }

    public String getTestTypeDiscount() {
        return testTypeDiscount;
    }

    public void setTestTypeDiscount(String testTypeDiscount) {
        this.testTypeDiscount = testTypeDiscount;
    }

    public String getTestPopularity() {
        return testPopularity;
    }

    public void setTestPopularity(String testPopularity) {
        this.testPopularity = testPopularity;
    }

    public String getHomePageListing() {
        return homePageListing;
    }

    public void setHomePageListing(String homePageListing) {
        this.homePageListing = homePageListing;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

}
