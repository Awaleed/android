package maksab.sd.customer.models.speciality;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dev2 on 12/11/2017.
 */

public class SpecialityModel implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("subCategoryId")
    @Expose
    private Integer subCategoryId;
    @SerializedName("arabicName")
    @Expose
    private String arabicName;
    @SerializedName("englishName")
    @Expose
    private String englishName;
    @SerializedName("imagePath")
    @Expose
    private String imagePath;
    @SerializedName("serviceQuestionDescription")
    @Expose
    private String serviceQuestionDescription;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("orderPriority")
    @Expose
    private Integer orderPriority;
    @SerializedName("requiredOfficeChecking")
    @Expose
    private Boolean requiredOfficeChecking;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("specialtySelectionTypeId")
    @Expose
    private Integer specialtySelectionTypeId;
    @SerializedName("specialtyType")
    @Expose
    private Integer specialtyType;
    @SerializedName("specialtyExecuationModel")
    @Expose
    private Integer specialtyExecuationModel;
    @SerializedName("haveCustomerQuestionsForm")
    @Expose
    private Boolean haveCustomerQuestionsForm;
    @SerializedName("haveProviderQuestionsForm")
    @Expose
    private Boolean haveProviderQuestionsForm;
    @SerializedName("haveProviderCompletionReportForm")
    @Expose
    private Boolean haveProviderCompletionReportForm;
    @SerializedName("transportationPriceType")
    @Expose
    private Integer transportationPriceType;
    @SerializedName("fixedTransportationPrice")
    @Expose
    private Double fixedTransportationPrice;
    @SerializedName("coverageType")
    @Expose
    private Integer coverageType;
    @SerializedName("checkingPrice")
    @Expose
    private Double checkingPrice;
    @SerializedName("visitPrice")
    @Expose
    private Double visitPrice;
    @SerializedName("guaranteePeriodInDays")
    @Expose
    private Integer guaranteePeriodInDays;
    @SerializedName("guaranteePrice")
    @Expose
    private Double guaranteePrice;
    @SerializedName("htmlTerms")
    @Expose
    private String htmlTerms;
    @SerializedName("specialtyDispatcherId")
    @Expose
    private Integer specialtyDispatcherId;
    @SerializedName("transportationPrice")
    @Expose
    private Double transportationPrice;
    @SerializedName("isCoverage")
    @Expose
    private Boolean isCoverage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getServiceQuestionDescription() {
        return serviceQuestionDescription;
    }

    public void setServiceQuestionDescription(String serviceQuestionDescription) {
        this.serviceQuestionDescription = serviceQuestionDescription;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getOrderPriority() {
        return orderPriority;
    }

    public void setOrderPriority(Integer orderPriority) {
        this.orderPriority = orderPriority;
    }

    public Boolean getRequiredOfficeChecking() {
        return requiredOfficeChecking;
    }

    public void setRequiredOfficeChecking(Boolean requiredOfficeChecking) {
        this.requiredOfficeChecking = requiredOfficeChecking;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getSpecialtySelectionTypeId() {
        return specialtySelectionTypeId;
    }

    public void setSpecialtySelectionTypeId(Integer specialtySelectionTypeId) {
        this.specialtySelectionTypeId = specialtySelectionTypeId;
    }

    public Integer getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(Integer specialtyType) {
        this.specialtyType = specialtyType;
    }

    public Boolean getHaveCustomerQuestionsForm() {
        return haveCustomerQuestionsForm;
    }

    public void setHaveCustomerQuestionsForm(Boolean haveCustomerQuestionsForm) {
        this.haveCustomerQuestionsForm = haveCustomerQuestionsForm;
    }

    public Boolean getHaveProviderQuestionsForm() {
        return haveProviderQuestionsForm;
    }

    public void setHaveProviderQuestionsForm(Boolean haveProviderQuestionsForm) {
        this.haveProviderQuestionsForm = haveProviderQuestionsForm;
    }

    public Boolean getHaveProviderCompletionReportForm() {
        return haveProviderCompletionReportForm;
    }

    public void setHaveProviderCompletionReportForm(Boolean haveProviderCompletionReportForm) {
        this.haveProviderCompletionReportForm = haveProviderCompletionReportForm;
    }

    public Integer getTransportationPriceType() {
        return transportationPriceType;
    }

    public void setTransportationPriceType(Integer transportationPriceType) {
        this.transportationPriceType = transportationPriceType;
    }

    public Double getFixedTransportationPrice() {
        return fixedTransportationPrice;
    }

    public void setFixedTransportationPrice(Double fixedTransportationPrice) {
        this.fixedTransportationPrice = fixedTransportationPrice;
    }

    public Integer getCoverageType() {
        return coverageType;
    }

    public void setCoverageType(Integer coverageType) {
        this.coverageType = coverageType;
    }

    public Double getCheckingPrice() {
        return checkingPrice;
    }

    public void setCheckingPrice(Double checkingPrice) {
        this.checkingPrice = checkingPrice;
    }

    public Double getVisitPrice() {
        return visitPrice;
    }

    public void setVisitPrice(Double visitPrice) {
        this.visitPrice = visitPrice;
    }

    public Integer getGuaranteePeriodInDays() {
        return guaranteePeriodInDays;
    }

    public void setGuaranteePeriodInDays(Integer guaranteePeriodInDays) {
        this.guaranteePeriodInDays = guaranteePeriodInDays;
    }

    public Double getGuaranteePrice() {
        return guaranteePrice;
    }

    public void setGuaranteePrice(Double guaranteePrice) {
        this.guaranteePrice = guaranteePrice;
    }

    public String getHtmlTerms() {
        return htmlTerms;
    }

    public void setHtmlTerms(String htmlTerms) {
        this.htmlTerms = htmlTerms;
    }

    public Integer getSpecialtyDispatcherId() {
        return specialtyDispatcherId;
    }

    public void setSpecialtyDispatcherId(Integer specialtyDispatcherId) {
        this.specialtyDispatcherId = specialtyDispatcherId;
    }

    public Double getTransportationPrice() {
        return transportationPrice;
    }

    public void setTransportationPrice(Double transportationPrice) {
        this.transportationPrice = transportationPrice;
    }

    public Boolean getIsCoverage() {
        return isCoverage;
    }

    public void setIsCoverage(Boolean isCoverage) {
        this.isCoverage = isCoverage;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Integer getSpecialtyExecuationModel() {
        return specialtyExecuationModel;
    }

    public Boolean getCoverage() {
        return isCoverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.categoryId);
        dest.writeValue(this.subCategoryId);
        dest.writeString(this.arabicName);
        dest.writeString(this.englishName);
        dest.writeString(this.imagePath);
        dest.writeString(this.serviceQuestionDescription);
        dest.writeValue(this.isActive);
        dest.writeValue(this.orderPriority);
        dest.writeValue(this.requiredOfficeChecking);
        dest.writeString(this.createdOn);
        dest.writeValue(this.specialtySelectionTypeId);
        dest.writeValue(this.specialtyType);
        dest.writeValue(this.specialtyExecuationModel);
        dest.writeValue(this.haveCustomerQuestionsForm);
        dest.writeValue(this.haveProviderQuestionsForm);
        dest.writeValue(this.haveProviderCompletionReportForm);
        dest.writeValue(this.transportationPriceType);
        dest.writeValue(this.fixedTransportationPrice);
        dest.writeValue(this.coverageType);
        dest.writeValue(this.checkingPrice);
        dest.writeValue(this.visitPrice);
        dest.writeValue(this.guaranteePeriodInDays);
        dest.writeValue(this.guaranteePrice);
        dest.writeString(this.htmlTerms);
        dest.writeValue(this.specialtyDispatcherId);
        dest.writeValue(this.transportationPrice);
        dest.writeValue(this.isCoverage);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Integer) source.readValue(Integer.class.getClassLoader());
        this.categoryId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.subCategoryId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.arabicName = source.readString();
        this.englishName = source.readString();
        this.imagePath = source.readString();
        this.serviceQuestionDescription = source.readString();
        this.isActive = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.orderPriority = (Integer) source.readValue(Integer.class.getClassLoader());
        this.requiredOfficeChecking = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.createdOn = source.readString();
        this.specialtySelectionTypeId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.specialtyType = (Integer) source.readValue(Integer.class.getClassLoader());
        this.specialtyExecuationModel = (Integer) source.readValue(Integer.class.getClassLoader());
        this.haveCustomerQuestionsForm = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.haveProviderQuestionsForm = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.haveProviderCompletionReportForm = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.transportationPriceType = (Integer) source.readValue(Integer.class.getClassLoader());
        this.fixedTransportationPrice = (Double) source.readValue(Double.class.getClassLoader());
        this.coverageType = (Integer) source.readValue(Integer.class.getClassLoader());
        this.checkingPrice = (Double) source.readValue(Double.class.getClassLoader());
        this.visitPrice = (Double) source.readValue(Double.class.getClassLoader());
        this.guaranteePeriodInDays = (Integer) source.readValue(Integer.class.getClassLoader());
        this.guaranteePrice = (Double) source.readValue(Double.class.getClassLoader());
        this.htmlTerms = source.readString();
        this.specialtyDispatcherId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.transportationPrice = (Double) source.readValue(Double.class.getClassLoader());
        this.isCoverage = (Boolean) source.readValue(Boolean.class.getClassLoader());
    }

    public SpecialityModel() {
    }

    protected SpecialityModel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.categoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.subCategoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.arabicName = in.readString();
        this.englishName = in.readString();
        this.imagePath = in.readString();
        this.serviceQuestionDescription = in.readString();
        this.isActive = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.orderPriority = (Integer) in.readValue(Integer.class.getClassLoader());
        this.requiredOfficeChecking = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.createdOn = in.readString();
        this.specialtySelectionTypeId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.specialtyType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.specialtyExecuationModel = (Integer) in.readValue(Integer.class.getClassLoader());
        this.haveCustomerQuestionsForm = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.haveProviderQuestionsForm = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.haveProviderCompletionReportForm = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.transportationPriceType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.fixedTransportationPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.coverageType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.checkingPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.visitPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.guaranteePeriodInDays = (Integer) in.readValue(Integer.class.getClassLoader());
        this.guaranteePrice = (Double) in.readValue(Double.class.getClassLoader());
        this.htmlTerms = in.readString();
        this.specialtyDispatcherId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.transportationPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.isCoverage = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<SpecialityModel> CREATOR = new Parcelable.Creator<SpecialityModel>() {
        @Override
        public SpecialityModel createFromParcel(Parcel source) {
            return new SpecialityModel(source);
        }

        @Override
        public SpecialityModel[] newArray(int size) {
            return new SpecialityModel[size];
        }
    };
}
