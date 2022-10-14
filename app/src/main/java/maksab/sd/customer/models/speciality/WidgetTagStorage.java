package maksab.sd.customer.models.speciality;

public class WidgetTagStorage {
    private int questionId;
    private boolean isRequired;

    public WidgetTagStorage(int questionId, boolean isRequired) {
        this.questionId = questionId;
        this.isRequired = isRequired;
    }



    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }


}
