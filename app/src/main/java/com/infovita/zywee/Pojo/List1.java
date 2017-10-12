
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class List1 implements Serializable {

    @SerializedName("EntityHealthInstitute")
    @Expose
    private EntityHealthInstitute1 entityHealthInstitute;
    @SerializedName("EntityInstituteAddress")
    @Expose
    private EntityInstituteAddress1 entityInstituteAddress;
    @SerializedName("MappingInstituteToDiagnosticsSpecialization")
    @Expose
    private MappingInstituteToDiagnosticsSpecialization1 mappingInstituteToDiagnosticsSpecialization;
    @SerializedName("MasterDiagnosticsSpecialization")
    @Expose
    private MasterDiagnosticsSpecialization1 masterDiagnosticsSpecialization;
    @SerializedName("MasterLocality")
    @Expose
    private MasterLocality1 masterLocality;
    @SerializedName("TestInstituteSpecialization")
    @Expose
    private TestInstituteSpecialization1 testInstituteSpecialization;

    public EntityHealthInstitute1 getEntityHealthInstitute() {
        return entityHealthInstitute;
    }

    public void setEntityHealthInstitute(EntityHealthInstitute1 entityHealthInstitute) {
        this.entityHealthInstitute = entityHealthInstitute;
    }

    public EntityInstituteAddress1 getEntityInstituteAddress() {
        return entityInstituteAddress;
    }

    public void setEntityInstituteAddress(EntityInstituteAddress1 entityInstituteAddress) {
        this.entityInstituteAddress = entityInstituteAddress;
    }

    public MappingInstituteToDiagnosticsSpecialization1 getMappingInstituteToDiagnosticsSpecialization() {
        return mappingInstituteToDiagnosticsSpecialization;
    }

    public void setMappingInstituteToDiagnosticsSpecialization(MappingInstituteToDiagnosticsSpecialization1 mappingInstituteToDiagnosticsSpecialization) {
        this.mappingInstituteToDiagnosticsSpecialization = mappingInstituteToDiagnosticsSpecialization;
    }

    public MasterDiagnosticsSpecialization1 getMasterDiagnosticsSpecialization() {
        return masterDiagnosticsSpecialization;
    }

    public void setMasterDiagnosticsSpecialization(MasterDiagnosticsSpecialization1 masterDiagnosticsSpecialization) {
        this.masterDiagnosticsSpecialization = masterDiagnosticsSpecialization;
    }

    public MasterLocality1 getMasterLocality() {
        return masterLocality;
    }

    public void setMasterLocality(MasterLocality1 masterLocality) {
        this.masterLocality = masterLocality;
    }

    public TestInstituteSpecialization1 getTestInstituteSpecialization() {
        return testInstituteSpecialization;
    }

    public void setTestInstituteSpecialization(TestInstituteSpecialization1 testInstituteSpecialization) {
        this.testInstituteSpecialization = testInstituteSpecialization;
    }

}
