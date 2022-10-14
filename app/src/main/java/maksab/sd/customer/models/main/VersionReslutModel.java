package maksab.sd.customer.models.main;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionReslutModel {

    @SerializedName("isUpdateFound")
    @Expose
    private Boolean isUpdateFound;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("versionCode")
    @Expose
    private Integer versionCode;
    @SerializedName("isForcedUpdate")
    @Expose
    private Boolean isForcedUpdate;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;

    public Boolean getIsUpdateFound() {
        return isUpdateFound;
    }

    public void setIsUpdateFound(Boolean isUpdateFound) {
        this.isUpdateFound = isUpdateFound;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public Boolean getIsForcedUpdate() {
        return isForcedUpdate;
    }

    public void setIsForcedUpdate(Boolean isForcedUpdate) {
        this.isForcedUpdate = isForcedUpdate;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}