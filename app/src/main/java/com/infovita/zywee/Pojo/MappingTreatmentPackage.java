
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MappingTreatmentPackage implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("treatment_package_id")
    @Expose
    private String treatmentPackageId;
    @SerializedName("sub_package_id")
    @Expose
    private String subPackageId;
    @SerializedName("entity_treatment_package_id")
    @Expose
    private String entityTreatmentPackageId;
    @SerializedName("tagging")
    @Expose
    private String tagging;
    @SerializedName("package_popularity")
    @Expose
    private String packagePopularity;
    @SerializedName("home_page_listing")
    @Expose
    private String homePageListing;
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

    public String getSubPackageId() {
        return subPackageId;
    }

    public void setSubPackageId(String subPackageId) {
        this.subPackageId = subPackageId;
    }

    public String getEntityTreatmentPackageId() {
        return entityTreatmentPackageId;
    }

    public void setEntityTreatmentPackageId(String entityTreatmentPackageId) {
        this.entityTreatmentPackageId = entityTreatmentPackageId;
    }

    public String getTagging() {
        return tagging;
    }

    public void setTagging(String tagging) {
        this.tagging = tagging;
    }

    public String getPackagePopularity() {
        return packagePopularity;
    }

    public void setPackagePopularity(String packagePopularity) {
        this.packagePopularity = packagePopularity;
    }

    public String getHomePageListing() {
        return homePageListing;
    }

    public void setHomePageListing(String homePageListing) {
        this.homePageListing = homePageListing;
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
