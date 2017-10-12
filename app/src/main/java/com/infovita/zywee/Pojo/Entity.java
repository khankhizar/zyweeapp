
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Entity implements Serializable {

    @SerializedName("EntityHealthInstitute")
    @Expose
    private EntityHealthInstitute2 entityHealthInstitute;
    @SerializedName("EntityInstituteAddress")
    @Expose
    private EntityInstituteAddress2 entityInstituteAddress;
    @SerializedName("EntityTreatmentPackage")
    @Expose
    private EntityTreatmentPackage entityTreatmentPackage;
    @SerializedName("MasterLocality")
    @Expose
    private MasterLocality masterLocality;
    @SerializedName("MappingTreatmentPackage")
    @Expose
    private MappingTreatmentPackage mappingTreatmentPackage;

    public EntityHealthInstitute2 getEntityHealthInstitute() {
        return entityHealthInstitute;
    }

    public void setEntityHealthInstitute(EntityHealthInstitute2 entityHealthInstitute) {
        this.entityHealthInstitute = entityHealthInstitute;
    }

    public EntityInstituteAddress2 getEntityInstituteAddress() {
        return entityInstituteAddress;
    }

    public void setEntityInstituteAddress(EntityInstituteAddress2 entityInstituteAddress) {
        this.entityInstituteAddress = entityInstituteAddress;
    }

    public EntityTreatmentPackage getEntityTreatmentPackage() {
        return entityTreatmentPackage;
    }

    public void setEntityTreatmentPackage(EntityTreatmentPackage entityTreatmentPackage) {
        this.entityTreatmentPackage = entityTreatmentPackage;
    }

    public MasterLocality getMasterLocality() {
        return masterLocality;
    }

    public void setMasterLocality(MasterLocality masterLocality) {
        this.masterLocality = masterLocality;
    }

    public MappingTreatmentPackage getMappingTreatmentPackage() {
        return mappingTreatmentPackage;
    }

    public void setMappingTreatmentPackage(MappingTreatmentPackage mappingTreatmentPackage) {
        this.mappingTreatmentPackage = mappingTreatmentPackage;
    }

}
