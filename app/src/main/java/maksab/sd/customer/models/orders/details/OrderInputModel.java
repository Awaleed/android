package maksab.sd.customer.models.orders.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dev on 3/14/2018.
 */

public class OrderInputModel {

    public OrderInputModel(){

    }
    @SerializedName("specialityId")
    @Expose
    private Integer specialityId;
    @SerializedName("orderTypeId")
    @Expose
    private Integer orderTypeId;
    @SerializedName("providerId")
    @Expose
    private Integer providerId;

    @SerializedName("addressId")
    @Expose
    private Integer addressId;

    @SerializedName("providerUserId")
    @Expose
    private String providerUserId;
    @SerializedName("desireOn")
    @Expose
    private String desireOn;
    @SerializedName("acceptFlexibleTime")
    @Expose
    private boolean acceptFlexibleTime;
    @SerializedName("selectedTime")
    @Expose
    private String selectedTime;
    @SerializedName("body")
    @Expose
    private String body;

    @SerializedName("couponCode")
    @Expose
    private String couponCode;

    @SerializedName("customerLocationDescription")
    @Expose
    private String customerLocationDescription;
    @SerializedName("customerLatitude")
    @Expose
    private Double customerLatitude;
    @SerializedName("customerLongitude")
    @Expose
    private Double customerLongitude;

    @SerializedName("consumeBalance")
    @Expose
    private boolean consumeBalance;


    @SerializedName("imagePaths")
    @Expose
    private List<String> imagePaths = null;
    @SerializedName("orderDetails")
    @Expose
    private List<OrderDetails> orderDetails = new ArrayList<>();
    @SerializedName("specialtyQuestionAnswers")
    @Expose
    private List<SpecialtyQuestionAnswers> specialtyQuestionAnswers = new ArrayList<>();

    public Integer getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Integer specialityId) {
        this.specialityId = specialityId;
    }

    public Integer getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Integer orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getDesireOn() {
        return desireOn;
    }

    public void setDesireOn(String desireOn) {
        this.desireOn = desireOn;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCustomerLocationDescription() {
        return customerLocationDescription;
    }

    public void setCustomerLocationDescription(String customerLocationDescription) {
        this.customerLocationDescription = customerLocationDescription;
    }

    public Double getCustomerLatitude() {
        return customerLatitude;
    }

    public void setCustomerLatitude(Double customerLatitude) {
        this.customerLatitude = customerLatitude;
    }

    public Double getCustomerLongitude() {
        return customerLongitude;
    }

    public void setCustomerLongitude(Double customerLongitude) {
        this.customerLongitude = customerLongitude;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<SpecialtyQuestionAnswers> getSpecialtyQuestionAnswers() {
        return specialtyQuestionAnswers;
    }

    public void setSpecialtyQuestionAnswers(List<SpecialtyQuestionAnswers> specialtyQuestionAnswers) {
        this.specialtyQuestionAnswers = specialtyQuestionAnswers;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public boolean isAcceptFlexibleTime() {
        return acceptFlexibleTime;
    }

    public void setAcceptFlexibleTime(boolean acceptFlexibleTime) {
        this.acceptFlexibleTime = acceptFlexibleTime;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public boolean isConsumeBalance() {
        return consumeBalance;
    }

    public void setConsumeBalance(boolean consumeBalance) {
        this.consumeBalance = consumeBalance;
    }
}


