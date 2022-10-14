package maksab.sd.customer.models.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AdminUser on 02/07/2018.
 */

public class CouponModel {

    @SerializedName("discountAmount")
    @Expose
    private Double discountAmount;
    @SerializedName("discountPercentage")
    @Expose
    private Integer discountPercentage;
    @SerializedName("couponStatusId")
    @Expose
    private Integer couponStatusId;
    @SerializedName("couponStatusName")
    @Expose
    private String couponStatusName;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;

    /**
     * No args constructor for use in serialization
     *
     */
    public CouponModel() {
    }

    /**
     *
     * @param isActive
     * @param couponStatusId
     * @param discountPercentage
     * @param discountAmount
     * @param couponStatusName
     */
    public CouponModel(Double discountAmount, Integer discountPercentage, Integer couponStatusId, String couponStatusName, Boolean isActive) {
        super();
        this.discountAmount = discountAmount;
        this.discountPercentage = discountPercentage;
        this.couponStatusId = couponStatusId;
        this.couponStatusName = couponStatusName;
        this.isActive = isActive;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Integer getCouponStatusId() {
        return couponStatusId;
    }

    public void setCouponStatusId(Integer couponStatusId) {
        this.couponStatusId = couponStatusId;
    }

    public String getCouponStatusName() {
        return couponStatusName;
    }

    public void setCouponStatusName(String couponStatusName) {
        this.couponStatusName = couponStatusName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
