
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MasterLocality1 implements Serializable {

    @SerializedName("locality_name")
    @Expose
    private String localityName;

    public String getLocalityName() {
        return localityName;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

}
