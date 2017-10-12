
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProviderHomeService implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("service_provider_id")
    @Expose
    private String serviceProviderId;
    @SerializedName("home_service_category_id")
    @Expose
    private String homeServiceCategoryId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getHomeServiceCategoryId() {
        return homeServiceCategoryId;
    }

    public void setHomeServiceCategoryId(String homeServiceCategoryId) {
        this.homeServiceCategoryId = homeServiceCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

}
