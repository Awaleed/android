package maksab.sd.customer.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeCategoryModel {

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

    @SerializedName("categoryStatus")
    @Expose
    private Integer categoryStatus;

    @SerializedName("isActive")
    @Expose
    private Boolean isActive;

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

    public Integer getCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(Integer categoryStatus) {
        this.categoryStatus = categoryStatus;
    }
}
