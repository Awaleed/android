package maksab.sd.customer.models.orders.details;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import maksab.sd.customer.models.speciality.SpecialityModel;

public class HomeSpecialOfferModel implements Parcelable {

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
    @SerializedName("offerImage")
    @Expose
    private String offerImage;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("originalPrice")
    @Expose
    private Double originalPrice;
    @SerializedName("addedOn")
    @Expose
    private String addedOn;
    @SerializedName("addedOnString")
    @Expose
    private String addedOnString;
    @SerializedName("fromTime")
    @Expose
    private String fromTime;
    @SerializedName("toTime")
    @Expose
    private String toTime;
    @SerializedName("viewCounts")
    @Expose
    private Integer viewCounts;
    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("fromTimeString")
    @Expose
    private String fromTimeString;

    @SerializedName("toTimeString")
    @Expose
    private String toTimeString;

    @SerializedName("userIntegerId")
    @Expose
    private Integer userIntegerId;
    @SerializedName("offerPriceDifferenceOn")
    @Expose
    private Integer offerPriceDifferenceOn;
    @SerializedName("offerBy")
    @Expose
    private Integer offerBy;
    @SerializedName("offerStatus")
    @Expose
    private Integer offerStatus;

    @SerializedName("specialtyViewModel")
    @Expose
    private SpecialityModel specialty;


    protected HomeSpecialOfferModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            specialtyId = null;
        } else {
            specialtyId = in.readInt();
        }
        name = in.readString();
        description = in.readString();
        offerImage = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        if (in.readByte() == 0) {
            originalPrice = null;
        } else {
            originalPrice = in.readDouble();
        }
        addedOn = in.readString();
        addedOnString = in.readString();
        fromTime = in.readString();
        toTime = in.readString();
        if (in.readByte() == 0) {
            viewCounts = null;
        } else {
            viewCounts = in.readInt();
        }
        userId = in.readString();
        fromTimeString = in.readString();
        toTimeString = in.readString();
        if (in.readByte() == 0) {
            userIntegerId = null;
        } else {
            userIntegerId = in.readInt();
        }
        if (in.readByte() == 0) {
            offerPriceDifferenceOn = null;
        } else {
            offerPriceDifferenceOn = in.readInt();
        }
        if (in.readByte() == 0) {
            offerBy = null;
        } else {
            offerBy = in.readInt();
        }
        if (in.readByte() == 0) {
            offerStatus = null;
        } else {
            offerStatus = in.readInt();
        }
        specialty = in.readParcelable(SpecialityModel.class.getClassLoader());
    }

    public static final Creator<HomeSpecialOfferModel> CREATOR = new Creator<HomeSpecialOfferModel>() {
        @Override
        public HomeSpecialOfferModel createFromParcel(Parcel in) {
            return new HomeSpecialOfferModel(in);
        }

        @Override
        public HomeSpecialOfferModel[] newArray(int size) {
            return new HomeSpecialOfferModel[size];
        }
    };

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

    public String getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(String offerImage) {
        this.offerImage = offerImage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getAddedOnString() {
        return addedOnString;
    }

    public void setAddedOnString(String addedOnString) {
        this.addedOnString = addedOnString;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public Integer getViewCounts() {
        return viewCounts;
    }

    public void setViewCounts(Integer viewCounts) {
        this.viewCounts = viewCounts;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserIntegerId() {
        return userIntegerId;
    }

    public void setUserIntegerId(Integer userIntegerId) {
        this.userIntegerId = userIntegerId;
    }

    public Integer getOfferPriceDifferenceOn() {
        return offerPriceDifferenceOn;
    }

    public void setOfferPriceDifferenceOn(Integer offerPriceDifferenceOn) {
        this.offerPriceDifferenceOn = offerPriceDifferenceOn;
    }

    public Integer getOfferBy() {
        return offerBy;
    }

    public void setOfferBy(Integer offerBy) {
        this.offerBy = offerBy;
    }

    public Integer getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(Integer offerStatus) {
        this.offerStatus = offerStatus;
    }

    public SpecialityModel getSpecialty() {
        return specialty;
    }

    public void setSpecialty(SpecialityModel specialty) {
        this.specialty = specialty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        if (specialtyId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(specialtyId);
        }
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(offerImage);
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(price);
        }
        if (originalPrice == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(originalPrice);
        }
        parcel.writeString(addedOn);
        parcel.writeString(addedOnString);
        parcel.writeString(fromTime);
        parcel.writeString(toTime);
        if (viewCounts == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(viewCounts);
        }
        parcel.writeString(userId);
        parcel.writeString(fromTimeString);
        parcel.writeString(toTimeString);
        if (userIntegerId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(userIntegerId);
        }
        if (offerPriceDifferenceOn == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(offerPriceDifferenceOn);
        }
        if (offerBy == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(offerBy);
        }
        if (offerStatus == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(offerStatus);
        }
        parcel.writeParcelable(specialty, i);
    }


    public String getFromTimeString() {
        return fromTimeString;
    }

    public void setFromTimeString(String fromTimeString) {
        this.fromTimeString = fromTimeString;
    }

    public String getToTimeString() {
        return toTimeString;
    }

    public void setToTimeString(String toTimeString) {
        this.toTimeString = toTimeString;
    }
}
