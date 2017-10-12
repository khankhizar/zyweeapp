
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("BucketImage")
    @Expose
    private BucketImage bucketImage;

    public BucketImage getBucketImage() {
        return bucketImage;
    }

    public void setBucketImage(BucketImage bucketImage) {
        this.bucketImage = bucketImage;
    }

}
