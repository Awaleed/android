package maksab.sd.customer.models.faq;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutUsModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("officeTiming")
    @Expose
    private String officeTiming;
    @SerializedName("officeLocationDescription")
    @Expose
    private String officeLocationDescription;
    @SerializedName("officeLocationMapImage")
    @Expose
    private String officeLocationMapImage;
    @SerializedName("officeLocationLatitude")
    @Expose
    private Double officeLocationLatitude;
    @SerializedName("officeLocationLongitude")
    @Expose
    private Double officeLocationLongitude;
    @SerializedName("facebookLink")
    @Expose
    private String facebookLink;
    @SerializedName("twitterLink")
    @Expose
    private String twitterLink;
    @SerializedName("whatsupLink")
    @Expose
    private String whatsupLink;
    @SerializedName("instegramLink")
    @Expose
    private String instegramLink;
    @SerializedName("youtubeLink")
    @Expose
    private String youtubeLink;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOfficeTiming() {
        return officeTiming;
    }

    public void setOfficeTiming(String officeTiming) {
        this.officeTiming = officeTiming;
    }

    public String getOfficeLocationDescription() {
        return officeLocationDescription;
    }

    public void setOfficeLocationDescription(String officeLocationDescription) {
        this.officeLocationDescription = officeLocationDescription;
    }

    public String getOfficeLocationMapImage() {
        return officeLocationMapImage;
    }

    public void setOfficeLocationMapImage(String officeLocationMapImage) {
        this.officeLocationMapImage = officeLocationMapImage;
    }

    public Double getOfficeLocationLatitude() {
        return officeLocationLatitude;
    }

    public void setOfficeLocationLatitude(Double officeLocationLatitude) {
        this.officeLocationLatitude = officeLocationLatitude;
    }

    public Double getOfficeLocationLongitude() {
        return officeLocationLongitude;
    }

    public void setOfficeLocationLongitude(Double officeLocationLongitude) {
        this.officeLocationLongitude = officeLocationLongitude;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getWhatsupLink() {
        return whatsupLink;
    }

    public void setWhatsupLink(String whatsupLink) {
        this.whatsupLink = whatsupLink;
    }

    public String getInstegramLink() {
        return instegramLink;
    }

    public void setInstegramLink(String instegramLink) {
        this.instegramLink = instegramLink;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

}
