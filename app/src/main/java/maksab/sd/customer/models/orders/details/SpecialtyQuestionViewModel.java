package maksab.sd.customer.models.orders.details;

import android.os.Parcel;
import android.os.Parcelable;

public class SpecialtyQuestionViewModel implements Parcelable {
    private int id;
    private String createdOn;
    private int specialtyId;
    private int specialtyQuestionForType;
    private int specialtyQuestionControlType;
    private boolean isRequired;
    private boolean isActive;
    private String arabicQuestion;
    private String englishQuestion;
    private String arabicAnswer;
    private String englishAnswer;
    private String answerSeparator;

    public int getId() {
        return id;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public int getSpecialtyQuestionForType() {
        return specialtyQuestionForType;
    }

    public int getSpecialtyQuestionControlType() {
        return specialtyQuestionControlType;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getArabicQuestion() {
        return arabicQuestion;
    }

    public String getEnglishQuestion() {
        return englishQuestion;
    }

    public String getArabicAnswer() {
        return arabicAnswer;
    }

    public String getEnglishAnswer() {
        return englishAnswer;
    }

    public String getAnswerSeparator() {
        return answerSeparator;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.createdOn);
        dest.writeInt(this.specialtyId);
        dest.writeInt(this.specialtyQuestionForType);
        dest.writeInt(this.specialtyQuestionControlType);
        dest.writeByte(this.isRequired ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
        dest.writeString(this.arabicQuestion);
        dest.writeString(this.englishQuestion);
        dest.writeString(this.arabicAnswer);
        dest.writeString(this.englishAnswer);
        dest.writeString(this.answerSeparator);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.createdOn = source.readString();
        this.specialtyId = source.readInt();
        this.specialtyQuestionForType = source.readInt();
        this.specialtyQuestionControlType = source.readInt();
        this.isRequired = source.readByte() != 0;
        this.isActive = source.readByte() != 0;
        this.arabicQuestion = source.readString();
        this.englishQuestion = source.readString();
        this.arabicAnswer = source.readString();
        this.englishAnswer = source.readString();
        this.answerSeparator = source.readString();
    }

    public SpecialtyQuestionViewModel() {
    }

    protected SpecialtyQuestionViewModel(Parcel in) {
        this.id = in.readInt();
        this.createdOn = in.readString();
        this.specialtyId = in.readInt();
        this.specialtyQuestionForType = in.readInt();
        this.specialtyQuestionControlType = in.readInt();
        this.isRequired = in.readByte() != 0;
        this.isActive = in.readByte() != 0;
        this.arabicQuestion = in.readString();
        this.englishQuestion = in.readString();
        this.arabicAnswer = in.readString();
        this.englishAnswer = in.readString();
        this.answerSeparator = in.readString();
    }

    public static final Parcelable.Creator<SpecialtyQuestionViewModel> CREATOR = new Parcelable.Creator<SpecialtyQuestionViewModel>() {
        @Override
        public SpecialtyQuestionViewModel createFromParcel(Parcel source) {
            return new SpecialtyQuestionViewModel(source);
        }

        @Override
        public SpecialtyQuestionViewModel[] newArray(int size) {
            return new SpecialtyQuestionViewModel[size];
        }
    };
}
