
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubTest1 implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("test_category_id")
    @Expose
    private String testCategoryId;
    @SerializedName("sub_test_type")
    @Expose
    private String subTestType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestCategoryId() {
        return testCategoryId;
    }

    public void setTestCategoryId(String testCategoryId) {
        this.testCategoryId = testCategoryId;
    }

    public String getSubTestType() {
        return subTestType;
    }

    public void setSubTestType(String subTestType) {
        this.subTestType = subTestType;
    }

}
