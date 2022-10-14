package maksab.sd.customer.models.categories;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class CardBasedCategoriesModel implements Parcelable {
    private int id;
    private String label;
    private String imageUrl;
    private String htmlTerms;
    private boolean haveQuestionForm;
    private int specialtySelectionTypeId;
    private int transportationPrice;
    private boolean isCoverage;
    private int specialtyType;

    public CardBasedCategoriesModel(int id, String label, String imageUrl, String htmlTerms , boolean haveQuestionForm ,
                                    int specialtySelectionTypeId,boolean isCoverage , int transportationPrice , int specialtyType) {
        this.id = id;
        this.label = label;
        this.imageUrl = imageUrl;
        this.htmlTerms = htmlTerms;
        this.haveQuestionForm = haveQuestionForm;
        this.specialtySelectionTypeId = specialtySelectionTypeId;
        this.isCoverage = isCoverage;
        this.transportationPrice = transportationPrice;
        this.specialtyType = specialtyType;
    }

    protected CardBasedCategoriesModel(Parcel in) {
        id = in.readInt();
        label = in.readString();
        imageUrl = in.readString();
        htmlTerms = in.readString();
        haveQuestionForm = in.readByte() != 0;
        specialtySelectionTypeId = in.readInt();
        isCoverage = in.readByte() != 0;
        transportationPrice = in.readInt();
        specialtyType = in.readInt();
    }

    public static final Creator<CardBasedCategoriesModel> CREATOR = new Creator<CardBasedCategoriesModel>() {
        @Override
        public CardBasedCategoriesModel createFromParcel(Parcel in) {
            return new CardBasedCategoriesModel(in);
        }

        @Override
        public CardBasedCategoriesModel[] newArray(int size) {
            return new CardBasedCategoriesModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHtmlTerms() {
        return htmlTerms;
    }

    public void setHtmlTerms(String htmlTerms) {
        this.htmlTerms = htmlTerms;
    }

    public boolean isHaveQuestionForm() {
        return haveQuestionForm;
    }

    public void setHaveQuestionForm(boolean haveQuestionForm) {
        this.haveQuestionForm = haveQuestionForm;
    }

    public int getSpecialtySelectionTypeId() {
        return specialtySelectionTypeId;
    }

    public void setSpecialtySelectionTypeId(int specialtySelectionTypeId) {
        this.specialtySelectionTypeId = specialtySelectionTypeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(label);
        parcel.writeString(imageUrl);
        parcel.writeString(htmlTerms);
        parcel.writeByte((byte) (haveQuestionForm ? 1 : 0));
        parcel.writeInt(specialtySelectionTypeId);
        parcel.writeByte((byte) (isCoverage ? 1 : 0));
        parcel.writeInt(transportationPrice);
        parcel.writeInt(specialtyType);
    }

    public boolean isCoverage() {
        return isCoverage;
    }

    public void setCoverage(boolean coverage) {
        isCoverage = coverage;
    }

    public int getTransportationPrice() {
        return transportationPrice;
    }

    public void setTransportationPrice(int transportationPrice) {
        this.transportationPrice = transportationPrice;
    }

    public int getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(int specialtyType) {
        this.specialtyType = specialtyType;
    }
}
