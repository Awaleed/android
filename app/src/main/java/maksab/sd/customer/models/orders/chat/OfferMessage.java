package maksab.sd.customer.models.orders.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferMessage{
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("quotationId")
    @Expose
    private Long quotationId;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("createdOnString")
    @Expose
    private String createdOnString;
    @SerializedName("isOffered")
    @Expose
    private Boolean isOffered;
    @SerializedName("isAccepted")
    @Expose
    private Boolean isAccepted;
    @SerializedName("offeredOn")
    @Expose
    private String offeredOn;
    @SerializedName("offerdOnString")
    @Expose
    private String offerdOnString;
    @SerializedName("orderTypeId")
    @Expose
    private int orderTypeId;
    @SerializedName("providerUserId")
    @Expose
    private String providerUserId;
    @SerializedName("providerId")
    @Expose
    private Long providerId;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("providerLatitude")
    @Expose
    private Double providerLatitude;
    @SerializedName("providerLongitude")
    @Expose
    private Double providerLongitude;
    @SerializedName("orderTypeName")
    @Expose
    private String orderTypeName;
    @SerializedName("providerName")
    @Expose
    private String providerName;
    @SerializedName("customerUserId")
    @Expose
    private String customerUserId;

    @SerializedName("providerImageUrl")
    @Expose
    private String providerImageUrl;

    @SerializedName("providerDistance")
    @Expose
    private Double providerDistance;

    @SerializedName("providerCompletedOrdersCount")
    @Expose
    private Integer ProviderCompletedOrdersCount;

    public OfferMessage(Long id, String createdOnString, String body, Double price, String orderTypeName, String providerName,
                        String providerImageUrl, Double providerDistance, Float providerRate) {
        this.id = id;
        this.createdOnString = createdOnString;
        this.body = body;
        this.price = price;
        this.orderTypeName = orderTypeName;
        this.providerName = providerName;
        this.providerImageUrl = providerImageUrl;
        this.providerDistance = providerDistance;
        ProviderRate = providerRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
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

    public Boolean getOffered() {
        return isOffered;
    }

    public void setOffered(Boolean offered) {
        isOffered = offered;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public String getOfferedOn() {
        return offeredOn;
    }

    public void setOfferedOn(String offeredOn) {
        this.offeredOn = offeredOn;
    }

    public String getOfferdOnString() {
        return offerdOnString;
    }

    public void setOfferdOnString(String offerdOnString) {
        this.offerdOnString = offerdOnString;
    }

    public int getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(int orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getProviderLatitude() {
        return providerLatitude;
    }

    public void setProviderLatitude(Double providerLatitude) {
        this.providerLatitude = providerLatitude;
    }

    public Double getProviderLongitude() {
        return providerLongitude;
    }

    public void setProviderLongitude(Double providerLongitude) {
        this.providerLongitude = providerLongitude;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getCustomerUserId() {
        return customerUserId;
    }

    public void setCustomerUserId(String customerUserId) {
        this.customerUserId = customerUserId;
    }

    public String getProviderImageUrl() {
        return providerImageUrl;
    }

    public void setProviderImageUrl(String providerImageUrl) {
        this.providerImageUrl = providerImageUrl;
    }

    public Double getProviderDistance() {
        return providerDistance;
    }

    public void setProviderDistance(Double providerDistance) {
        this.providerDistance = providerDistance;
    }

    public Integer getProviderCompletedOrdersCount() {
        return ProviderCompletedOrdersCount;
    }

    public void setProviderCompletedOrdersCount(Integer providerCompletedOrdersCount) {
        ProviderCompletedOrdersCount = providerCompletedOrdersCount;
    }

    public Float getProviderRate() {
        return ProviderRate;
    }

    public void setProviderRate(Float providerRate) {
        ProviderRate = providerRate;
    }

    @SerializedName("providerRate")
    @Expose
    private Float ProviderRate;
}