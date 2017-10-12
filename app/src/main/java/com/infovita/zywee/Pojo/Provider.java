
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Provider implements Serializable {

    @SerializedName("Provider")
    @Expose
    private Provider_ provider;
    @SerializedName("ProviderVehicle")
    @Expose
    private ProviderVehicle providerVehicle;
    @SerializedName("MasterLocality")
    @Expose
    private MasterLocality4 masterLocality;
    @SerializedName("ProviderDistance")
    @Expose
    private ProviderDistance providerDistance;

    public Provider_ getProvider() {
        return provider;
    }

    public void setProvider(Provider_ provider) {
        this.provider = provider;
    }

    public ProviderVehicle getProviderVehicle() {
        return providerVehicle;
    }

    public void setProviderVehicle(ProviderVehicle providerVehicle) {
        this.providerVehicle = providerVehicle;
    }

    public MasterLocality4 getMasterLocality() {
        return masterLocality;
    }

    public void setMasterLocality(MasterLocality4 masterLocality) {
        this.masterLocality = masterLocality;
    }

    public ProviderDistance getProviderDistance() {
        return providerDistance;
    }

    public void setProviderDistance(ProviderDistance providerDistance) {
        this.providerDistance = providerDistance;
    }

}
