package maksab.sd.customer.models.address;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class District implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cityId")
    @Expose
    private Integer cityId;
    @SerializedName("arabicName")
    @Expose
    private String arabicName;
    @SerializedName("englishName")
    @Expose
    private String englishName;
    @SerializedName("cityArabicName")
    @Expose
    private String cityArabicName;
    @SerializedName("cityEnglishName")
    @Expose
    private String cityEnglishName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getCityArabicName() {
        return cityArabicName;
    }

    public void setCityArabicName(String cityArabicName) {
        this.cityArabicName = cityArabicName;
    }

    public String getCityEnglishName() {
        return cityEnglishName;
    }

    public void setCityEnglishName(String cityEnglishName) {
        this.cityEnglishName = cityEnglishName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.cityId);
        dest.writeString(this.arabicName);
        dest.writeString(this.englishName);
        dest.writeString(this.cityArabicName);
        dest.writeString(this.cityEnglishName);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Integer) source.readValue(Integer.class.getClassLoader());
        this.cityId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.arabicName = source.readString();
        this.englishName = source.readString();
        this.cityArabicName = source.readString();
        this.cityEnglishName = source.readString();
    }

    public District() {
    }

    protected District(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.cityId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.arabicName = in.readString();
        this.englishName = in.readString();
        this.cityArabicName = in.readString();
        this.cityEnglishName = in.readString();
    }

    public static final Parcelable.Creator<District> CREATOR = new Parcelable.Creator<District>() {
        @Override
        public District createFromParcel(Parcel source) {
            return new District(source);
        }

        @Override
        public District[] newArray(int size) {
            return new District[size];
        }
    };
}
