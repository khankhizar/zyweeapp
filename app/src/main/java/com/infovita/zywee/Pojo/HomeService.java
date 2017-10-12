
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HomeService implements Serializable {

    @SerializedName("HomeServiceCategory")
    @Expose
    private HomeServiceCategory homeServiceCategory;
    @SerializedName("List")
    @Expose
    private java.util.List<com.infovita.zywee.Pojo.List> list = null;

    public HomeServiceCategory getHomeServiceCategory() {
        return homeServiceCategory;
    }

    public void setHomeServiceCategory(HomeServiceCategory homeServiceCategory) {
        this.homeServiceCategory = homeServiceCategory;
    }

    public java.util.List<com.infovita.zywee.Pojo.List> getList() {
        return list;
    }

    public void setList(java.util.List<com.infovita.zywee.Pojo.List> list) {
        this.list = list;
    }

}
