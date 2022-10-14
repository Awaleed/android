package maksab.sd.customer.models.address;

import android.os.Parcel;
import android.os.Parcelable;

public class AddressType implements Parcelable {
    private int id;
    private String arabicName;
    private String englishName;
    private String imagePath;

    public int getId() {
        return id;
    }

    public String getArabicName() {
        return arabicName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.arabicName);
        dest.writeString(this.englishName);
        dest.writeString(this.imagePath);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.arabicName = source.readString();
        this.englishName = source.readString();
        this.imagePath = source.readString();
    }

    public AddressType() {
    }

    protected AddressType(Parcel in) {
        this.id = in.readInt();
        this.arabicName = in.readString();
        this.englishName = in.readString();
        this.imagePath = in.readString();
    }

    public static final Parcelable.Creator<AddressType> CREATOR = new Parcelable.Creator<AddressType>() {
        @Override
        public AddressType createFromParcel(Parcel source) {
            return new AddressType(source);
        }

        @Override
        public AddressType[] newArray(int size) {
            return new AddressType[size];
        }
    };
}
