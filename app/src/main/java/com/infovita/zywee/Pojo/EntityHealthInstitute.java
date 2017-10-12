
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EntityHealthInstitute {

    @SerializedName("health_institute_name")
    @Expose
    private String healthInstituteName;
    @SerializedName("health_institute_id")
    @Expose
    private String healthInstituteId;

    /**
     * 
     * @return
     *     The healthInstituteName
     */
    public String getHealthInstituteName() {
        return healthInstituteName;
    }

    /**
     * 
     * @param healthInstituteName
     *     The health_institute_name
     */
    public void setHealthInstituteName(String healthInstituteName) {
        this.healthInstituteName = healthInstituteName;
    }

    /**
     * 
     * @return
     *     The healthInstituteId
     */
    public String getHealthInstituteId() {
        return healthInstituteId;
    }

    /**
     * 
     * @param healthInstituteId
     *     The health_institute_id
     */
    public void setHealthInstituteId(String healthInstituteId) {
        this.healthInstituteId = healthInstituteId;
    }

}
