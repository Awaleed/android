package maksab.sd.customer.models.orders.details;

import android.os.Parcel;
import android.os.Parcelable;

public class SpecialtyQuestionAnswerViewModel implements Parcelable {
    private int id;
    private String arabicAnswer;
    private String englishAnswer;
    private String createdOn;
    private String createdOnString;
    private int specialtyQuestionId;
    private int itemId;
    private SpecialtyQuestionViewModel specialtyQuestion;

    public int getId() {
        return id;
    }

    public String getArabicAnswer() {
        return arabicAnswer;
    }

    public String getEnglishAnswer() {
        return englishAnswer;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getCreatedOnString() {
        return createdOnString;
    }

    public int getSpecialtyQuestionId() {
        return specialtyQuestionId;
    }

    public int getItemId() {
        return itemId;
    }

    public SpecialtyQuestionViewModel getSpecialtyQuestion() {
        return specialtyQuestion;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.arabicAnswer);
        dest.writeString(this.englishAnswer);
        dest.writeString(this.createdOn);
        dest.writeString(this.createdOnString);
        dest.writeInt(this.specialtyQuestionId);
        dest.writeInt(this.itemId);
        dest.writeParcelable(this.specialtyQuestion, flags);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.arabicAnswer = source.readString();
        this.englishAnswer = source.readString();
        this.createdOn = source.readString();
        this.createdOnString = source.readString();
        this.specialtyQuestionId = source.readInt();
        this.itemId = source.readInt();
        this.specialtyQuestion = source.readParcelable(SpecialtyQuestionViewModel.class.getClassLoader());
    }

    public SpecialtyQuestionAnswerViewModel() {
    }

    protected SpecialtyQuestionAnswerViewModel(Parcel in) {
        this.id = in.readInt();
        this.arabicAnswer = in.readString();
        this.englishAnswer = in.readString();
        this.createdOn = in.readString();
        this.createdOnString = in.readString();
        this.specialtyQuestionId = in.readInt();
        this.itemId = in.readInt();
        this.specialtyQuestion = in.readParcelable(SpecialtyQuestionViewModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<SpecialtyQuestionAnswerViewModel> CREATOR = new Parcelable.Creator<SpecialtyQuestionAnswerViewModel>() {
        @Override
        public SpecialtyQuestionAnswerViewModel createFromParcel(Parcel source) {
            return new SpecialtyQuestionAnswerViewModel(source);
        }

        @Override
        public SpecialtyQuestionAnswerViewModel[] newArray(int size) {
            return new SpecialtyQuestionAnswerViewModel[size];
        }
    };
}
