
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class List implements Serializable {

    @SerializedName("ProviderHomeService")
    @Expose
    private ProviderHomeService providerHomeService;
    @SerializedName("ServiceProvider")
    @Expose
    private ServiceProvider serviceProvider;
    @SerializedName("MasterLocality")
    @Expose
    private MasterLocality2 masterLocality;
    @SerializedName("ProviderServiceCharge")
    @Expose
    private ProviderServiceCharge providerServiceCharge;

    public ProviderHomeService getProviderHomeService() {
        return providerHomeService;
    }

    public void setProviderHomeService(ProviderHomeService providerHomeService) {
        this.providerHomeService = providerHomeService;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public MasterLocality2 getMasterLocality() {
        return masterLocality;
    }

    public void setMasterLocality(MasterLocality2 masterLocality) {
        this.masterLocality = masterLocality;
    }

    public ProviderServiceCharge getProviderServiceCharge() {
        return providerServiceCharge;
    }

    public void setProviderServiceCharge(ProviderServiceCharge providerServiceCharge) {
        this.providerServiceCharge = providerServiceCharge;
    }

}
