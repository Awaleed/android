package maksab.sd.customer.models.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProviderRateModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customerId")
    @Expose
    private Integer customerId;
    @SerializedName("providerId")
    @Expose
    private Integer providerId;
    @SerializedName("customerUserId")
    @Expose
    private String customerUserId;
    @SerializedName("providerUserId")
    @Expose
    private String providerUserId;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("customerImage")
    @Expose
    private String customerImage;
    @SerializedName("specialityId")
    @Expose
    private Integer specialityId;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("rateComment")
    @Expose
    private String rateComment;
    @SerializedName("specialityName")
    @Expose
    private String specialityName;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("createdOnString")
    @Expose
    private String createdOnString;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getCustomerUserId() {
        return customerUserId;
    }

    public void setCustomerUserId(String customerUserId) {
        this.customerUserId = customerUserId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(String customerImage) {
        this.customerImage = customerImage;
    }

    public Integer getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Integer specialityId) {
        this.specialityId = specialityId;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getRateComment() {
        return rateComment;
    }

    public void setRateComment(String rateComment) {
        this.rateComment = rateComment;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
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
}
