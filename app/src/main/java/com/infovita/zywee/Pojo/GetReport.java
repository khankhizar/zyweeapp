
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetReport {

    @SerializedName("UserReport")
    @Expose
    private UserReport userReport;

    /**
     * 
     * @return
     *     The userReport
     */
    public UserReport getUserReport() {
        return userReport;
    }

    /**
     * 
     * @param userReport
     *     The UserReport
     */
    public void setUserReport(UserReport userReport) {
        this.userReport = userReport;
    }

}
