package maksab.sd.customer.models.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("userTypeEnum")
    @Expose
    private Integer userTypeEnum;
    @SerializedName("addressDescription")
    @Expose
    private String addressDescription;
    @SerializedName("districtId")
    @Expose
    private Integer districtId;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("customerId")
    @Expose
    private Integer customerId;
    @SerializedName("customerUserId")
    @Expose
    private String customerUserId;
    @SerializedName("providerId")
    @Expose
    private Object providerId;
    @SerializedName("providerUserId")
    @Expose
    private Object providerUserId;
    @SerializedName("district")
    @Expose
    private District district;
    @SerializedName("addressTypeId")
    @Expose
    private Integer addressTypeId;
    @SerializedName("addressType")
    @Expose
    private AddressType addressType;
    @SerializedName("floorTypeId")
    @Expose
    private Integer floorTypeId;
    @SerializedName("floorType")
    @Expose
    private FloorType floorType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getUserTypeEnum() {
        return userTypeEnum;
    }

    public void setUserTypeEnum(Integer userTypeEnum) {
        this.userTypeEnum = userTypeEnum;
    }

    public String getAddressDescription() {
        return addressDescription;
    }

    public void setAddressDescription(String addressDescription) {
        this.addressDescription = addressDescription;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerUserId() {
        return customerUserId;
    }

    public void setCustomerUserId(String customerUserId) {
        this.customerUserId = customerUserId;
    }

    public Object getProviderId() {
        return providerId;
    }

    public void setProviderId(Object providerId) {
        this.providerId = providerId;
    }

    public Object getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(Object providerUserId) {
        this.providerUserId = providerUserId;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Integer getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(Integer addressTypeId) {
        this.addressTypeId = addressTypeId;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public Integer getFloorTypeId() {
        return floorTypeId;
    }

    public void setFloorTypeId(Integer floorTypeId) {
        this.floorTypeId = floorTypeId;
    }

    public FloorType getFloorType() {
        return floorType;
    }

    public void setFloorType(FloorType floorType) {
        this.floorType = floorType;
    }
}
