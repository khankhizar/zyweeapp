

package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MorePackage {

    @SerializedName("EntityTreatmentPackage")
    @Expose
    private EntityTreatmentPackage1 entityTreatmentPackage;
    @SerializedName("EntityHealthInstitut")
    @Expose
    private EntityHealthInstitut entityHealthInstitut;

    public EntityTreatmentPackage1 getEntityTreatmentPackage() {
        return entityTreatmentPackage;
    }

    public void setEntityTreatmentPackage(EntityTreatmentPackage1 entityTreatmentPackage) {
        this.entityTreatmentPackage = entityTreatmentPackage;
    }

    public EntityHealthInstitut getEntityHealthInstitut() {
        return entityHealthInstitut;
    }

    public void setEntityHealthInstitut(EntityHealthInstitut entityHealthInstitut) {
        this.entityHealthInstitut = entityHealthInstitut;
    }

}
