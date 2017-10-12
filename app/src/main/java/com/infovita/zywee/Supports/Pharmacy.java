package com.infovita.zywee.Supports;

import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by Khizarkhan on 03-02-2017.
 */
public class Pharmacy {
    private String id;
    private String ownerName;
    private String pharmacyName;
    private String phone;
    private String email;
    private String address;
    private String latitude;
    private String longitude;
    private String range;
    private String workingDays;
    private String openTime;
    private String closeTime;
    private String minimumOrder;
    private String created;
    private String modified;
    private String loginStatus;
    private String otp;
    private String distance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(String workingDays) {
        this.workingDays = workingDays;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getMinimumOrder() {
        return minimumOrder;
    }

    public void setMinimumOrder(String minimumOrder) {
        this.minimumOrder = minimumOrder;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
    @Override
    public String toString() {
        int pharmacyDistance = 0;
        try{
            double d = 0.0;
            Log.v("Pharmacy",distance+" :::");
            if(distance != null)
            {
                d =  (Double.parseDouble(distance.toString()));
            }
            DecimalFormat df = new DecimalFormat("#.##");
            double distance =	Double.valueOf(df.format(d));
            pharmacyDistance = (int) (distance*1000);
        }
        catch(Exception e){Log.e("Pharmacy","Error in getting distance of pharmacy: "+e.toString());
            e.printStackTrace();}
        return pharmacyName+" - "+ pharmacyDistance +"m";
    }
}
