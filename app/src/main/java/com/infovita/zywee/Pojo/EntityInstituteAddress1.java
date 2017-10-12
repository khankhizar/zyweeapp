
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EntityInstituteAddress1 implements Serializable {

    @SerializedName("institute_address_line1")
    @Expose
    private String instituteAddressLine1;
    @SerializedName("institute_address_line2")
    @Expose
    private String instituteAddressLine2;
    @SerializedName("institute_address_pincode")
    @Expose
    private String instituteAddressPincode;

    public String getInstituteAddressLine1() {
        return instituteAddressLine1;
    }

    public void setInstituteAddressLine1(String instituteAddressLine1) {
        this.instituteAddressLine1 = instituteAddressLine1;
    }

    public String getInstituteAddressLine2() {
        return instituteAddressLine2;
    }

    public void setInstituteAddressLine2(String instituteAddressLine2) {
        this.instituteAddressLine2 = instituteAddressLine2;
    }

    public String getInstituteAddressPincode() {
        return instituteAddressPincode;
    }

    public void setInstituteAddressPincode(String instituteAddressPincode) {
        this.instituteAddressPincode = instituteAddressPincode;
    }

}
