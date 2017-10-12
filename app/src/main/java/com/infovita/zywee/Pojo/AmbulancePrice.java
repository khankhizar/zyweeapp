package com.infovita.zywee.Pojo;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AmbulancePrice {

    @SerializedName("accessory_id")
    @Expose
    private List<String> accessoryId = new ArrayList<String>();
    @SerializedName("accessory_type")
    @Expose
    private List<String> accessoryType = new ArrayList<String>();
    @SerializedName("amount")
    @Expose
    private List<String> amount = new ArrayList<String>();
    @SerializedName("cost")
    @Expose
    private List<String> cost = new ArrayList<String>();
    @SerializedName("distance_id")
    @Expose
    private List<String> distanceId = new ArrayList<String>();
    @SerializedName("distance_type")
    @Expose
    private List<String> distanceType = new ArrayList<String>();

    /**
     *
     * @return
     * The accessoryId
     */
    public List<String> getAccessoryId() {
        return accessoryId;
    }

    /**
     *
     * @param accessoryId
     * The accessory_id
     */
    public void setAccessoryId(List<String> accessoryId) {
        this.accessoryId = accessoryId;
    }

    /**
     *
     * @return
     * The accessoryType
     */
    public List<String> getAccessoryType() {
        return accessoryType;
    }

    /**
     *
     * @param accessoryType
     * The accessory_type
     */
    public void setAccessoryType(List<String> accessoryType) {
        this.accessoryType = accessoryType;
    }

    /**
     *
     * @return
     * The amount
     */
    public List<String> getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     * The amount
     */
    public void setAmount(List<String> amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     * The cost
     */
    public List<String> getCost() {
        return cost;
    }

    /**
     *
     * @param cost
     * The cost
     */
    public void setCost(List<String> cost) {
        this.cost = cost;
    }

    /**
     *
     * @return
     * The distanceId
     */
    public List<String> getDistanceId() {
        return distanceId;
    }

    /**
     *
     * @param distanceId
     * The distance_id
     */
    public void setDistanceId(List<String> distanceId) {
        this.distanceId = distanceId;
    }

    /**
     *
     * @return
     * The distanceType
     */
    public List<String> getDistanceType() {
        return distanceType;
    }

    /**
     *
     * @param distanceType
     * The distance_type
     */
    public void setDistanceType(List<String> distanceType) {
        this.distanceType = distanceType;
    }

}

