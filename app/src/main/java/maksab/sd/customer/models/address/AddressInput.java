package maksab.sd.customer.models.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressInput {
    @SerializedName("addressTypeId")
    @Expose
    private Integer addressTypeId;
    @SerializedName("floorTypeId")
    @Expose
    private Integer floorTypeId;
    @SerializedName("districtId")
    @Expose
    private Integer districtId;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("addressDescription")
    @Expose
    private String addressDescription;

    public Integer getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(Integer addressTypeEnum) {
        this.addressTypeId = addressTypeEnum;
    }

    public Integer getFloorTypeId() {
        return floorTypeId;
    }

    public void setFloorTypeId(Integer floorEnum) {
        this.floorTypeId = floorEnum;
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

    public String getAddressDescription() {
        return addressDescription;
    }

    public void setAddressDescription(String addressDescription) {
        this.addressDescription = addressDescription;
    }
}
