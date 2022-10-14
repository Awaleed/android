package maksab.sd.customer.models.services;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceModel implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("specialtyId")
    @Expose
    private Integer specialtyId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private Double price;

    private int orderPriority;
    private int subSpecialtyId;
    private String dubSpecialtyName;
    private int minimumQuantity;
    private int maximumQuantity;
    private int serviceType;
    private int serviceFor;
    private String imagePath;
    private boolean ssActive;
    private String addedOn;
    private String providerUserId;
    private int providerId;

    // Selection
    private Integer quantity;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Integer specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public int getOrderPriority() {
        return orderPriority;
    }

    public int getSubSpecialtyId() {
        return subSpecialtyId;
    }

    public String getDubSpecialtyName() {
        return dubSpecialtyName;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public int getMaximumQuantity() {
        return maximumQuantity;
    }

    public int getServiceType() {
        return serviceType;
    }

    public int getServiceFor() {
        return serviceFor;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isSsActive() {
        return ssActive;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public int getProviderId() {
        return providerId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.specialtyId);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeValue(this.price);
        dest.writeInt(this.orderPriority);
        dest.writeInt(this.subSpecialtyId);
        dest.writeString(this.dubSpecialtyName);
        dest.writeInt(this.minimumQuantity);
        dest.writeInt(this.maximumQuantity);
        dest.writeInt(this.serviceType);
        dest.writeInt(this.serviceFor);
        dest.writeString(this.imagePath);
        dest.writeByte(this.ssActive ? (byte) 1 : (byte) 0);
        dest.writeString(this.addedOn);
        dest.writeString(this.providerUserId);
        dest.writeInt(this.providerId);
        dest.writeValue(this.quantity);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Integer) source.readValue(Integer.class.getClassLoader());
        this.specialtyId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.name = source.readString();
        this.description = source.readString();
        this.price = (Double) source.readValue(Double.class.getClassLoader());
        this.orderPriority = source.readInt();
        this.subSpecialtyId = source.readInt();
        this.dubSpecialtyName = source.readString();
        this.minimumQuantity = source.readInt();
        this.maximumQuantity = source.readInt();
        this.serviceType = source.readInt();
        this.serviceFor = source.readInt();
        this.imagePath = source.readString();
        this.ssActive = source.readByte() != 0;
        this.addedOn = source.readString();
        this.providerUserId = source.readString();
        this.providerId = source.readInt();
        this.quantity = (Integer) source.readValue(Integer.class.getClassLoader());
        this.isSelected = source.readByte() != 0;
    }

    public ServiceModel() {
    }

    protected ServiceModel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.specialtyId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.price = (Double) in.readValue(Double.class.getClassLoader());
        this.orderPriority = in.readInt();
        this.subSpecialtyId = in.readInt();
        this.dubSpecialtyName = in.readString();
        this.minimumQuantity = in.readInt();
        this.maximumQuantity = in.readInt();
        this.serviceType = in.readInt();
        this.serviceFor = in.readInt();
        this.imagePath = in.readString();
        this.ssActive = in.readByte() != 0;
        this.addedOn = in.readString();
        this.providerUserId = in.readString();
        this.providerId = in.readInt();
        this.quantity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isSelected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ServiceModel> CREATOR = new Parcelable.Creator<ServiceModel>() {
        @Override
        public ServiceModel createFromParcel(Parcel source) {
            return new ServiceModel(source);
        }

        @Override
        public ServiceModel[] newArray(int size) {
            return new ServiceModel[size];
        }
    };
}


