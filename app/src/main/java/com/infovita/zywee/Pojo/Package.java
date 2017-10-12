
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Package implements Serializable {

    @SerializedName("SubPackage")
    @Expose
    private SubPackage subPackage;
    @SerializedName("TreatmentPackage")
    @Expose
    private TreatmentPackage treatmentPackage;
    @SerializedName("entity")
    @Expose
    private List<Entity> entity = null;

    public SubPackage getSubPackage() {
        return subPackage;
    }

    public void setSubPackage(SubPackage subPackage) {
        this.subPackage = subPackage;
    }

    public TreatmentPackage getTreatmentPackage() {
        return treatmentPackage;
    }

    public void setTreatmentPackage(TreatmentPackage treatmentPackage) {
        this.treatmentPackage = treatmentPackage;
    }

    public List<Entity> getEntity() {
        return entity;
    }

    public void setEntity(List<Entity> entity) {
        this.entity = entity;
    }

}
