package maksab.sd.customer.models.providers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dev on 2/21/2018.
 */

public class GalleryModel implements Parcelable {
    public int id;
    public String providerUserId;
    public int providerId;
    public int specialtyId;
    public String name;
    public String description;
    public int viewCount;
    public String imagePath;
    public String imageThumbPath;
    public String createdOn;
    public String createdOnString;
    public String specialtyName;
    public boolean isActive;
    public String providerName;
    public String providerLastTimeUseTheApp;
    public String providerImage;
    public double providerRate;

    public int getId() {
        return id;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public int getProviderId() {
        return providerId;
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getViewCount() {
        return viewCount;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getImageThumbPath() {
        return imageThumbPath;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getCreatedOnString() {
        return createdOnString;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getProviderLastTimeUseTheApp() {
        return providerLastTimeUseTheApp;
    }

    public String getProviderImage() {
        return providerImage;
    }

    public double getProviderRate() {
        return providerRate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.providerUserId);
        dest.writeInt(this.providerId);
        dest.writeInt(this.specialtyId);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeInt(this.viewCount);
        dest.writeString(this.imagePath);
        dest.writeString(this.imageThumbPath);
        dest.writeString(this.createdOn);
        dest.writeString(this.createdOnString);
        dest.writeString(this.specialtyName);
        dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
        dest.writeString(this.providerName);
        dest.writeString(this.providerLastTimeUseTheApp);
        dest.writeString(this.providerImage);
        dest.writeDouble(this.providerRate);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.providerUserId = source.readString();
        this.providerId = source.readInt();
        this.specialtyId = source.readInt();
        this.name = source.readString();
        this.description = source.readString();
        this.viewCount = source.readInt();
        this.imagePath = source.readString();
        this.imageThumbPath = source.readString();
        this.createdOn = source.readString();
        this.createdOnString = source.readString();
        this.specialtyName = source.readString();
        this.isActive = source.readByte() != 0;
        this.providerName = source.readString();
        this.providerLastTimeUseTheApp = source.readString();
        this.providerImage = source.readString();
        this.providerRate = source.readDouble();
    }

    public GalleryModel() {
    }

    protected GalleryModel(Parcel in) {
        this.id = in.readInt();
        this.providerUserId = in.readString();
        this.providerId = in.readInt();
        this.specialtyId = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.viewCount = in.readInt();
        this.imagePath = in.readString();
        this.imageThumbPath = in.readString();
        this.createdOn = in.readString();
        this.createdOnString = in.readString();
        this.specialtyName = in.readString();
        this.isActive = in.readByte() != 0;
        this.providerName = in.readString();
        this.providerLastTimeUseTheApp = in.readString();
        this.providerImage = in.readString();
        this.providerRate = in.readDouble();
    }

    public static final Parcelable.Creator<GalleryModel> CREATOR = new Parcelable.Creator<GalleryModel>() {
        @Override
        public GalleryModel createFromParcel(Parcel source) {
            return new GalleryModel(source);
        }

        @Override
        public GalleryModel[] newArray(int size) {
            return new GalleryModel[size];
        }
    };
}
