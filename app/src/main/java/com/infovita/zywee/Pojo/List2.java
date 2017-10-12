
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class List2 implements Serializable {

    @SerializedName("ProviderEquipmentCost")
    @Expose
    private ProviderEquipmentCost providerEquipmentCost;
    @SerializedName("EquipmentProvider")
    @Expose
    private EquipmentProvider equipmentProvider;
    @SerializedName("MasterLocality")
    @Expose
    private MasterLocality3 masterLocality;

    public ProviderEquipmentCost getProviderEquipmentCost() {
        return providerEquipmentCost;
    }

    public void setProviderEquipmentCost(ProviderEquipmentCost providerEquipmentCost) {
        this.providerEquipmentCost = providerEquipmentCost;
    }

    public EquipmentProvider getEquipmentProvider() {
        return equipmentProvider;
    }

    public void setEquipmentProvider(EquipmentProvider equipmentProvider) {
        this.equipmentProvider = equipmentProvider;
    }

    public MasterLocality3 getMasterLocality() {
        return masterLocality;
    }

    public void setMasterLocality(MasterLocality3 masterLocality) {
        this.masterLocality = masterLocality;
    }

}
