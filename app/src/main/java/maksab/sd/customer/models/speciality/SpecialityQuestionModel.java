package maksab.sd.customer.models.speciality;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecialityQuestionModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("specialtyId")
    @Expose
    private Integer specialtyId;
    @SerializedName("specialtyQuestionControlType")
    @Expose
    private Integer specialtyQuestionControlType;
    @SerializedName("isRequired")
    @Expose
    private Boolean isRequired;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("createdOnString")
    @Expose
    private String createdOnString;
    @SerializedName("arabicQuestion")
    @Expose
    private String arabicQuestion;
    @SerializedName("englishQuestion")
    @Expose
    private String englishQuestion;
    @SerializedName("arabicAnswer")
    @Expose
    private String arabicAnswer;
    @SerializedName("englishAnswer")
    @Expose
    private String englishAnswer;
    @SerializedName("answerSeparator")
    @Expose
    private String answerSeparator;

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

    public Integer getSpecialtyQuestionControlType() {
        return specialtyQuestionControlType;
    }

    public void setSpecialtyQuestionControlType(Integer specialtyQuestionControlType) {
        this.specialtyQuestionControlType = specialtyQuestionControlType;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedOnString() {
        return createdOnString;
    }

    public void setCreatedOnString(String createdOnString) {
        this.createdOnString = createdOnString;
    }

    public String getArabicQuestion() {
        return arabicQuestion;
    }

    public void setArabicQuestion(String arabicQuestion) {
        this.arabicQuestion = arabicQuestion;
    }

    public String getEnglishQuestion() {
        return englishQuestion;
    }

    public void setEnglishQuestion(String englishQuestion) {
        this.englishQuestion = englishQuestion;
    }

    public String getArabicAnswer() {
        return arabicAnswer;
    }

    public void setArabicAnswer(String arabicAnswer) {
        this.arabicAnswer = arabicAnswer;
    }

    public String getEnglishAnswer() {
        return englishAnswer;
    }

    public void setEnglishAnswer(String englishAnswer) {
        this.englishAnswer = englishAnswer;
    }

    public String getAnswerSeparator() {
        return answerSeparator;
    }

    public void setAnswerSeparator(String answerSeparator) {
        this.answerSeparator = answerSeparator;
    }

}
