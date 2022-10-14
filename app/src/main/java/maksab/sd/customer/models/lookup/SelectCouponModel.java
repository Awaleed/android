package maksab.sd.customer.models.lookup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectCouponModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("couponTypeId")
    @Expose
    private Integer couponTypeId;
    @SerializedName("couponTypeName")
    @Expose
    private String couponTypeName;
    @SerializedName("firstUsingCount")
    @Expose
    private Integer firstUsingCount;
    @SerializedName("reusingCount")
    @Expose
    private Integer reusingCount;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("discountAmount")
    @Expose
    private Double discountAmount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("addedByUserId")
    @Expose
    private String addedByUserId;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("createdOnString")
    @Expose
    private String createdOnString;
    @SerializedName("activatedOn")
    @Expose
    private String activatedOn;
    @SerializedName("activatedOnString")
    @Expose
    private String activatedOnString;
    @SerializedName("expriedOn")
    @Expose
    private String expriedOn;
    @SerializedName("expiredOnString")
    @Expose
    private String expiredOnString;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("couponUsageCount")
    @Expose
    private Integer couponUsageCount;
    @SerializedName("couponScopeTargetId")
    @Expose
    private Integer couponScopeTargetId;
    @SerializedName("couponScopeTargetName")
    @Expose
    private Object couponScopeTargetName;
    @SerializedName("couponScope")
    @Expose
    private Integer couponScope;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCouponTypeId() {
        return couponTypeId;
    }

    public void setCouponTypeId(Integer couponTypeId) {
        this.couponTypeId = couponTypeId;
    }

    public String getCouponTypeName() {
        return couponTypeName;
    }

    public void setCouponTypeName(String couponTypeName) {
        this.couponTypeName = couponTypeName;
    }

    public Integer getFirstUsingCount() {
        return firstUsingCount;
    }

    public void setFirstUsingCount(Integer firstUsingCount) {
        this.firstUsingCount = firstUsingCount;
    }

    public Integer getReusingCount() {
        return reusingCount;
    }

    public void setReusingCount(Integer reusingCount) {
        this.reusingCount = reusingCount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddedByUserId() {
        return addedByUserId;
    }

    public void setAddedByUserId(String addedByUserId) {
        this.addedByUserId = addedByUserId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedOnString() {
        return createdOnString;
    }

    public void setCreatedOnString(String createdOnString) {
        this.createdOnString = createdOnString;
    }

    public String getActivatedOn() {
        return activatedOn;
    }

    public void setActivatedOn(String activatedOn) {
        this.activatedOn = activatedOn;
    }

    public String getActivatedOnString() {
        return activatedOnString;
    }

    public void setActivatedOnString(String activatedOnString) {
        this.activatedOnString = activatedOnString;
    }

    public String getExpriedOn() {
        return expriedOn;
    }

    public void setExpriedOn(String expriedOn) {
        this.expriedOn = expriedOn;
    }

    public String getExpiredOnString() {
        return expiredOnString;
    }

    public void setExpiredOnString(String expiredOnString) {
        this.expiredOnString = expiredOnString;
    }

    public Integer getCouponUsageCount() {
        return couponUsageCount;
    }

    public void setCouponUsageCount(Integer couponUsageCount) {
        this.couponUsageCount = couponUsageCount;
    }

    public Integer getCouponScopeTargetId() {
        return couponScopeTargetId;
    }

    public void setCouponScopeTargetId(Integer couponScopeTargetId) {
        this.couponScopeTargetId = couponScopeTargetId;
    }

    public Object getCouponScopeTargetName() {
        return couponScopeTargetName;
    }

    public void setCouponScopeTargetName(Object couponScopeTargetName) {
        this.couponScopeTargetName = couponScopeTargetName;
    }

    public Integer getCouponScope() {
        return couponScope;
    }

    public void setCouponScope(Integer couponScope) {
        this.couponScope = couponScope;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
