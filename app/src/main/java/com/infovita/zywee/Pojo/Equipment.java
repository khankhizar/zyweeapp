
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Equipment implements Serializable {

    @SerializedName("Equipment")
    @Expose
    private Equipmented equipment;
    @SerializedName("EquipmentSpeciality")
    @Expose
    private EquipmentSpeciality equipmentSpeciality;
    @SerializedName("List")
    @Expose
    private java.util.List<List2> list = null;

    public Equipmented getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipmented equipment) {
        this.equipment = equipment;
    }

    public EquipmentSpeciality getEquipmentSpeciality() {
        return equipmentSpeciality;
    }

    public void setEquipmentSpeciality(EquipmentSpeciality equipmentSpeciality) {
        this.equipmentSpeciality = equipmentSpeciality;
    }

    public java.util.List<List2> getList() {
        return list;
    }

    public void setList(java.util.List<List2> list) {
        this.list = list;
    }

}
