package maksab.sd.customer.models.lookup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LookupModel {

    public LookupModel(Integer id, String arabicName) {
        this.id = id;
        this.arabicName = arabicName;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("countryId")
    @Expose
    private Integer countryId;
    @SerializedName("arabicName")
    @Expose
    private String arabicName;
    @SerializedName("englishName")
    @Expose
    private String englishName;
    @SerializedName("countryArabicName")
    @Expose
    private Object countryArabicName;
    @SerializedName("countryEnglishName")
    @Expose
    private Object countryEnglishName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
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

    public Object getCountryArabicName() {
        return countryArabicName;
    }

    public void setCountryArabicName(Object countryArabicName) {
        this.countryArabicName = countryArabicName;
    }

    public Object getCountryEnglishName() {
        return countryEnglishName;
    }

    public void setCountryEnglishName(Object countryEnglishName) {
        this.countryEnglishName = countryEnglishName;
    }

}
