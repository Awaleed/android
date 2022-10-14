package maksab.sd.customer.models.address;

import android.os.Parcel;
import android.os.Parcelable;

public class FloorType implements Parcelable {
    private int id;
    private String arabicName;
    private String englishName;

    public int getId() {
        return id;
    }

    public String getArabicName() {
        return arabicName;
    }

    public String getEnglishName() {
        return englishName;
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
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.arabicName = source.readString();
        this.englishName = source.readString();
    }

    public FloorType() {
    }

    protected FloorType(Parcel in) {
        this.id = in.readInt();
        this.arabicName = in.readString();
        this.englishName = in.readString();
    }

    public static final Parcelable.Creator<FloorType> CREATOR = new Parcelable.Creator<FloorType>() {
        @Override
        public FloorType createFromParcel(Parcel source) {
            return new FloorType(source);
        }

        @Override
        public FloorType[] newArray(int size) {
            return new FloorType[size];
        }
    };
}