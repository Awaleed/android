package maksab.sd.customer.models.address;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderAddressViewModel implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("addressDescription")
    @Expose
    private String addressDescription;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("locationStaticMapImage")
    @Expose
    private String locationStaticMapImage;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("addressTypeId")
    @Expose
    private Integer addressTypeId;
    @SerializedName("floorTypeId")
    @Expose
    private Integer floorTypeId;
    @SerializedName("districtId")
    @Expose
    private Integer districtId;
    @SerializedName("district")
    @Expose
    private District district;
    @SerializedName("addressType")
    @Expose
    private AddressType addressType;
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

    public String getAddressDescription() {
        return addressDescription;
    }

    public void setAddressDescription(String addressDescription) {
        this.addressDescription = addressDescription;
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

    public String getLocationStaticMapImage() {
        return locationStaticMapImage;
    }

    public void setLocationStaticMapImage(String locationStaticMapImage) {
        this.locationStaticMapImage = locationStaticMapImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(Integer addressTypeId) {
        this.addressTypeId = addressTypeId;
    }

    public Integer getFloorTypeId() {
        return floorTypeId;
    }

    public void setFloorTypeId(Integer floorTypeId) {
        this.floorTypeId = floorTypeId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(String District) {
        this.district = district;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public FloorType getFloorType() {
        return floorType;
    }

    public void setFloorType(FloorType floorType) {
        this.floorType = floorType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.createdOn);
        dest.writeString(this.addressDescription);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeString(this.locationStaticMapImage);
        dest.writeString(this.userId);
        dest.writeValue(this.addressTypeId);
        dest.writeValue(this.floorTypeId);
        dest.writeValue(this.districtId);
        dest.writeParcelable(this.district, flags);
        dest.writeParcelable(this.addressType, flags);
        dest.writeParcelable(this.floorType, flags);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Integer) source.readValue(Integer.class.getClassLoader());
        this.createdOn = source.readString();
        this.addressDescription = source.readString();
        this.latitude = (Double) source.readValue(Double.class.getClassLoader());
        this.longitude = (Double) source.readValue(Double.class.getClassLoader());
        this.locationStaticMapImage = source.readString();
        this.userId = source.readString();
        this.addressTypeId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.floorTypeId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.districtId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.district = source.readParcelable(District.class.getClassLoader());
        this.addressType = source.readParcelable(AddressType.class.getClassLoader());
        this.floorType = source.readParcelable(FloorType.class.getClassLoader());
    }

    public OrderAddressViewModel() {
    }

    protected OrderAddressViewModel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.createdOn = in.readString();
        this.addressDescription = in.readString();
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.locationStaticMapImage = in.readString();
        this.userId = in.readString();
        this.addressTypeId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.floorTypeId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.districtId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.district = in.readParcelable(District.class.getClassLoader());
        this.addressType = in.readParcelable(AddressType.class.getClassLoader());
        this.floorType = in.readParcelable(FloorType.class.getClassLoader());
    }

    public static final Parcelable.Creator<OrderAddressViewModel> CREATOR = new Parcelable.Creator<OrderAddressViewModel>() {
        @Override
        public OrderAddressViewModel createFromParcel(Parcel source) {
            return new OrderAddressViewModel(source);
        }

        @Override
        public OrderAddressViewModel[] newArray(int size) {
            return new OrderAddressViewModel[size];
        }
    };
}
