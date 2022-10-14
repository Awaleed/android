package maksab.sd.customer.models.providers;

import android.os.Parcel;
import android.os.Parcelable;

/**
* Created by dev2 on 12/12/2017.
*/
public class ProviderSpecialtyModel implements Parcelable {
    private int id;
    private String userId;
    private int providerId;
    private int specialtyId;
    private int categoryId;
    private String specialityName;
    private String specialtyArabic;
    private String imagePath;
    private int specialtyType;
    private int specialtySelectionTypeId;
    private int specialtyExecuationModel;

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public int getProviderId() {
        return providerId;
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public String getSpecialtyArabic() {
        return specialtyArabic;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getSpecialtyType() {
        return specialtyType;
    }

    public int getSpecialtySelectionTypeId() {
        return specialtySelectionTypeId;
    }

    public int getSpecialtyExecuationModel() {
        return specialtyExecuationModel;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.userId);
        dest.writeInt(this.providerId);
        dest.writeInt(this.specialtyId);
        dest.writeInt(this.categoryId);
        dest.writeString(this.specialityName);
        dest.writeString(this.specialtyArabic);
        dest.writeString(this.imagePath);
        dest.writeInt(this.specialtyType);
        dest.writeInt(this.specialtySelectionTypeId);
        dest.writeInt(this.specialtyExecuationModel);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.userId = source.readString();
        this.providerId = source.readInt();
        this.specialtyId = source.readInt();
        this.categoryId = source.readInt();
        this.specialityName = source.readString();
        this.specialtyArabic = source.readString();
        this.imagePath = source.readString();
        this.specialtyType = source.readInt();
        this.specialtySelectionTypeId = source.readInt();
        this.specialtyExecuationModel = source.readInt();
    }

    public ProviderSpecialtyModel() {
    }

    protected ProviderSpecialtyModel(Parcel in) {
        this.id = in.readInt();
        this.userId = in.readString();
        this.providerId = in.readInt();
        this.specialtyId = in.readInt();
        this.categoryId = in.readInt();
        this.specialityName = in.readString();
        this.specialtyArabic = in.readString();
        this.imagePath = in.readString();
        this.specialtyType = in.readInt();
        this.specialtySelectionTypeId = in.readInt();
        this.specialtyExecuationModel = in.readInt();
    }

    public static final Parcelable.Creator<ProviderSpecialtyModel> CREATOR = new Parcelable.Creator<ProviderSpecialtyModel>() {
        @Override
        public ProviderSpecialtyModel createFromParcel(Parcel source) {
            return new ProviderSpecialtyModel(source);
        }

        @Override
        public ProviderSpecialtyModel[] newArray(int size) {
            return new ProviderSpecialtyModel[size];
        }
    };
}
