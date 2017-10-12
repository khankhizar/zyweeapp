
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Test1 implements Serializable {

    @SerializedName("SubTest")
    @Expose
    private SubTest1 subTest;
    @SerializedName("list")
    @Expose
    private java.util.List<List1> list = null;

    public SubTest1 getSubTest() {
        return subTest;
    }

    public void setSubTest(SubTest1 subTest) {
        this.subTest = subTest;
    }

    public java.util.List<List1> getList() {
        return list;
    }

    public void setList() {
        this.list = list;
    }



}
