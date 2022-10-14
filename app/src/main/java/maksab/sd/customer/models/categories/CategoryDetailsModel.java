package maksab.sd.customer.models.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import maksab.sd.customer.models.main.SliderViewModel;


public class CategoryDetailsModel {

    @SerializedName("sliderViewModels")
    @Expose
    private List<SliderViewModel> sliderViewModels = null;
    @SerializedName("subCategoryViewModels")
    @Expose
    private List<SubCategoryViewModel> subCategoryViewModels = null;
    @SerializedName("offers")
    @Expose
    private List<Offer> offers = null;

    public List<SliderViewModel> getSliderViewModels() {
        return sliderViewModels;
    }

    public void setSliderViewModels(List<SliderViewModel> sliderViewModels) {
        this.sliderViewModels = sliderViewModels;
    }

    public List<SubCategoryViewModel> getSubCategoryViewModels() {
        return subCategoryViewModels;
    }

    public void setSubCategoryViewModels(List<SubCategoryViewModel> subCategoryViewModels) {
        this.subCategoryViewModels = subCategoryViewModels;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }



    public class Offer {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("specialtyId")
        @Expose
        private Integer specialtyId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("offerImage")
        @Expose
        private String offerImage;
        @SerializedName("price")
        @Expose
        private Double price;
        @SerializedName("originalPrice")
        @Expose
        private Double originalPrice;
        @SerializedName("addedOn")
        @Expose
        private String addedOn;
        @SerializedName("addedOnString")
        @Expose
        private String addedOnString;
        @SerializedName("fromTime")
        @Expose
        private String fromTime;
        @SerializedName("toTime")
        @Expose
        private String toTime;
        @SerializedName("viewCounts")
        @Expose
        private Integer viewCounts;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("userIntegerId")
        @Expose
        private Integer userIntegerId;
        @SerializedName("offerPriceDifferenceOn")
        @Expose
        private Integer offerPriceDifferenceOn;
        @SerializedName("offerBy")
        @Expose
        private Integer offerBy;
        @SerializedName("offerStatus")
        @Expose
        private Integer offerStatus;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getOfferImage() {
            return offerImage;
        }

        public void setOfferImage(String offerImage) {
            this.offerImage = offerImage;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Double getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(Double originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getAddedOn() {
            return addedOn;
        }

        public void setAddedOn(String addedOn) {
            this.addedOn = addedOn;
        }

        public String getAddedOnString() {
            return addedOnString;
        }

        public void setAddedOnString(String addedOnString) {
            this.addedOnString = addedOnString;
        }

        public String getFromTime() {
            return fromTime;
        }

        public void setFromTime(String fromTime) {
            this.fromTime = fromTime;
        }

        public String getToTime() {
            return toTime;
        }

        public void setToTime(String toTime) {
            this.toTime = toTime;
        }

        public Integer getViewCounts() {
            return viewCounts;
        }

        public void setViewCounts(Integer viewCounts) {
            this.viewCounts = viewCounts;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Integer getUserIntegerId() {
            return userIntegerId;
        }

        public void setUserIntegerId(Integer userIntegerId) {
            this.userIntegerId = userIntegerId;
        }

        public Integer getOfferPriceDifferenceOn() {
            return offerPriceDifferenceOn;
        }

        public void setOfferPriceDifferenceOn(Integer offerPriceDifferenceOn) {
            this.offerPriceDifferenceOn = offerPriceDifferenceOn;
        }

        public Integer getOfferBy() {
            return offerBy;
        }

        public void setOfferBy(Integer offerBy) {
            this.offerBy = offerBy;
        }

        public Integer getOfferStatus() {
            return offerStatus;
        }

        public void setOfferStatus(Integer offerStatus) {
            this.offerStatus = offerStatus;
        }

    }

    public class SpecialtyViewModel {

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
        @SerializedName("searchDistance")
        @Expose
        private Integer searchDistance;
        @SerializedName("createdOn")
        @Expose
        private String createdOn;
        @SerializedName("specialtySelectionTypeId")
        @Expose
        private Integer specialtySelectionTypeId;
        @SerializedName("specialtyType")
        @Expose
        private Integer specialtyType;
        @SerializedName("haveCustomerQuestionsForm")
        @Expose
        private Boolean haveCustomQuestionsForm;
        @SerializedName("htmlTerms")
        @Expose
        private String htmlTerms;

        @SerializedName("isCoverage")
        @Expose
        private boolean isCoverage;

        @SerializedName("transportationPrice")
        @Expose
        private int transportationPrice;

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

        public Integer getSearchDistance() {
            return searchDistance;
        }

        public void setSearchDistance(Integer searchDistance) {
            this.searchDistance = searchDistance;
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

        public Boolean getHaveCustomQuestionsForm() {
            return haveCustomQuestionsForm;
        }

        public void setHaveCustomQuestionsForm(Boolean haveCustomQuestionsForm) {
            this.haveCustomQuestionsForm = haveCustomQuestionsForm;
        }

        public String getHtmlTerms() {
            return htmlTerms;
        }

        public void setHtmlTerms(String htmlTerms) {
            this.htmlTerms = htmlTerms;
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
    }

    public class SubCategoryViewModel {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("arabicName")
        @Expose
        private String arabicName;
        @SerializedName("englishName")
        @Expose
        private String englishName;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("imagePath")
        @Expose
        private String imagePath;
        @SerializedName("orderPriority")
        @Expose
        private Integer orderPriority;
        @SerializedName("isActive")
        @Expose
        private Boolean isActive;
        @SerializedName("categoryId")
        @Expose
        private Integer categoryId;
        @SerializedName("specialtyViewModels")
        @Expose
        private List<SpecialtyViewModel> specialtyViewModels = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public Integer getOrderPriority() {
            return orderPriority;
        }

        public void setOrderPriority(Integer orderPriority) {
            this.orderPriority = orderPriority;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public List<SpecialtyViewModel> getSpecialtyViewModels() {
            return specialtyViewModels;
        }

        public void setSpecialtyViewModels(List<SpecialtyViewModel> specialtyViewModels) {
            this.specialtyViewModels = specialtyViewModels;
        }
    }

}

