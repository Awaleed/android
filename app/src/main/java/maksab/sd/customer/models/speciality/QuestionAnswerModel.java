package maksab.sd.customer.models.speciality;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionAnswerModel {


    @SerializedName("ArabicAnswer")
    @Expose
    private String arabicAnswer;
    @SerializedName("SpecialtyQuestionId")
    @Expose
    private Integer specialtyQuestionId;

    public String getArabicAnswer() {
        return arabicAnswer;
    }

    public void setArabicAnswer(String arabicAnswer) {
        this.arabicAnswer = arabicAnswer;
    }

    public Integer getSpecialtyQuestionId() {
        return specialtyQuestionId;
    }

    public void setSpecialtyQuestionId(Integer specialtyQuestionId) {
        this.specialtyQuestionId = specialtyQuestionId;
    }

}
