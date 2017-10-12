
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EntityInstituteAddress {

    @SerializedName("institute_address_line1")
    @Expose
    private String instituteAddressLine1;
    @SerializedName("institute_address_line2")
    @Expose
    private String instituteAddressLine2;

    /**
     *
     * @return
     *     The instituteAddressLine1
     */
    public String getInstituteAddressLine1() {
        return instituteAddressLine1;
    }

    /**
     *
     * @param instituteAddressLine1
     *     The institute_address_line1
     */
    public void setInstituteAddressLine1(String instituteAddressLine1) {
        this.instituteAddressLine1 = instituteAddressLine1;
    }

    /**
     *
     * @return
     *     The instituteAddressLine2
     */
    public String getInstituteAddressLine2() {
        return instituteAddressLine2;
    }

    /**
     *
     * @param instituteAddressLine2
     *     The institute_address_line2
     */
    public void setInstituteAddressLine2(String instituteAddressLine2) {
        this.instituteAddressLine2 = instituteAddressLine2;
    }

}
