
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubPackage implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("treatment_package_id")
    @Expose
    private String treatmentPackageId;
    @SerializedName("sub_package_type")
    @Expose
    private String subPackageType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("why_undergo_this_package")
    @Expose
    private String whyUndergoThisPackage;
    @SerializedName("what_preparation_required")
    @Expose
    private String whatPreparationRequired;
    @SerializedName("procedure_for_package")
    @Expose
    private String procedureForPackage;
    @SerializedName("meta_description")
    @Expose
    private String metaDescription;
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

    public String getTreatmentPackageId() {
        return treatmentPackageId;
    }

    public void setTreatmentPackageId(String treatmentPackageId) {
        this.treatmentPackageId = treatmentPackageId;
    }

    public String getSubPackageType() {
        return subPackageType;
    }

    public void setSubPackageType(String subPackageType) {
        this.subPackageType = subPackageType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWhyUndergoThisPackage() {
        return whyUndergoThisPackage;
    }

    public void setWhyUndergoThisPackage(String whyUndergoThisPackage) {
        this.whyUndergoThisPackage = whyUndergoThisPackage;
    }

    public String getWhatPreparationRequired() {
        return whatPreparationRequired;
    }

    public void setWhatPreparationRequired(String whatPreparationRequired) {
        this.whatPreparationRequired = whatPreparationRequired;
    }

    public String getProcedureForPackage() {
        return procedureForPackage;
    }

    public void setProcedureForPackage(String procedureForPackage) {
        this.procedureForPackage = procedureForPackage;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
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
