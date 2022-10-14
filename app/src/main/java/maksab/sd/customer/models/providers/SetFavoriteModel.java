package maksab.sd.customer.models.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AdminUser on 01/05/2018.
 */

public class SetFavoriteModel {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("providerUserId")
    @Expose
    private String providerUserId;
    @SerializedName("providerId")
    @Expose
    private Integer providerId;
    @SerializedName("toAdd")
    @Expose
    private Boolean toAdd;

    /**
     * No args constructor for use in serialization
     *
     */
    public SetFavoriteModel() {
    }

    /**
     *
     * @param toAdd
     * @param userId
     * @param providerId
     * @param providerUserId
     */
    public SetFavoriteModel(String userId, String providerUserId, Integer providerId, Boolean toAdd) {
        super();
        this.userId = userId;
        this.providerUserId = providerUserId;
        this.providerId = providerId;
        this.toAdd = toAdd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Boolean getToAdd() {
        return toAdd;
    }

    public void setToAdd(Boolean toAdd) {
        this.toAdd = toAdd;
    }

}
