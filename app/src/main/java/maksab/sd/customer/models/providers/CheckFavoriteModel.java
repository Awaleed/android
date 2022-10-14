package maksab.sd.customer.models.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AdminUser on 02/17/2018.
 */

public class CheckFavoriteModel {

    @SerializedName("isFavorite")
    @Expose
    private Boolean isFavorite;
    @SerializedName("viewCount")
    @Expose
    private Integer viewCount;

    /**
     * No args constructor for use in serialization
     *
     */
    public CheckFavoriteModel() {
    }

    /**
     *
     * @param isFavorite
     * @param viewCount
     */
    public CheckFavoriteModel(Boolean isFavorite, Integer viewCount) {
        super();
        this.isFavorite = isFavorite;
        this.viewCount = viewCount;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
    
}
