package maksab.sd.customer.models.main;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import maksab.sd.customer.models.home.HomeCategoryModel;


public class HomeModel{

    @SerializedName("greeting")
    @Expose
    private String greeting;
    @SerializedName("categories")
    @Expose
    private List<HomeCategoryModel> categories = null;
    @SerializedName("offers")
    @Expose
    private Object offers;
    @SerializedName("commonsSpecialties")
    @Expose
    private List<HomeSpecialtyModel> commonsSpecialties;
    @SerializedName("bookingSpecialties")
    @Expose
    private List<HomeSpecialtyModel> bookingSpecialties;
    @SerializedName("shopsSpecialties")
    @Expose
    private List<HomeSpecialtyModel> shopsSpecialties;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public List<HomeCategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<HomeCategoryModel> categories) {
        this.categories = categories;
    }

    public Object getOffers() {
        return offers;
    }

    public void setOffers(Object offers) {
        this.offers = offers;
    }

    public List<HomeSpecialtyModel> getCommonsSpecialties() {
        return commonsSpecialties;
    }

    public void setCommonsSpecialties(List<HomeSpecialtyModel> commonsSpecialties) {
        this.commonsSpecialties = commonsSpecialties;
    }

    public List<HomeSpecialtyModel> getBookingSpecialties() {
        return bookingSpecialties;
    }

    public void setBookingSpecialties(List<HomeSpecialtyModel> bookingSpecialties) {
        this.bookingSpecialties = bookingSpecialties;
    }

    public List<HomeSpecialtyModel> getShopsSpecialties() {
        return shopsSpecialties;
    }

    public void setShopsSpecialties(List<HomeSpecialtyModel> shopsSpecialties) {
        this.shopsSpecialties = shopsSpecialties;
    }



    public class HomeSpecialtyModel {
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
        @SerializedName("haveCustomQuestionsForm")
        @Expose
        private Boolean haveCustomQuestionsForm;
        @SerializedName("htmlTerms")
        @Expose
        private String htmlTerms;

        @SerializedName("isCoverage")
        @Expose
        private boolean isCoverage;

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


}
