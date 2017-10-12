
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBookingDetail {

    @SerializedName("EntityHealthInstitute")
    @Expose
    private EntityHealthInstitute entityHealthInstitute;
    @SerializedName("EntityInstituteAddress")
    @Expose
    private EntityInstituteAddress entityInstituteAddress;

    /**
     * 
     * @return
     *     The entityHealthInstitute
     */
    public EntityHealthInstitute getEntityHealthInstitute() {
        return entityHealthInstitute;
    }

    /**
     *
     * @param entityHealthInstitute
     *     The EntityHealthInstitute
     */
    public void setEntityHealthInstitute(EntityHealthInstitute entityHealthInstitute) {
        this.entityHealthInstitute = entityHealthInstitute;
    }

    /**
     * 
     * @return
     *     The entityInstituteAddress
     */
    public EntityInstituteAddress getEntityInstituteAddress() {
        return entityInstituteAddress;
    }

    /**
     * 
     * @param entityInstituteAddress
     *     The EntityInstituteAddress
     */
    public void setEntityInstituteAddress(EntityInstituteAddress entityInstituteAddress) {
        this.entityInstituteAddress = entityInstituteAddress;
    }

}
