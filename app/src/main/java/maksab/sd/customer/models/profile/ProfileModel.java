package maksab.sd.customer.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dev on 11/27/2017.
 */

public class ProfileModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("genderId")
    @Expose
    private Integer genderId;
    @SerializedName("accountType")
    @Expose
    private Integer accountType;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("identityNo")
    @Expose
    private String identityNo;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("genderArabic")
    @Expose
    private String genderArabic;
    @SerializedName("profileImage")
    @Expose
    private String profileImage;

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("deepLink")
    @Expose
    private String deepLink;

    @SerializedName("balance")
    @Expose
    private double balance;

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @SerializedName("birthDate")
    @Expose
    private String birthDate;


    @SerializedName("onBoardingStatus")
    @Expose
    private Integer onBoardingStatus;

    private String deepLinkCredits;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProfileModel() {
    }

    /**
     *
     * @param identityNo
     * @param genderId
     * @param fullName
     * @param mobileNo
     */
    public ProfileModel(Integer genderId, String fullName, String identityNo, String mobileNo, String email) {
        super();
        this.genderId = genderId;
        this.fullName = fullName;
        this.identityNo = identityNo;
        this.mobileNo = mobileNo;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getGenderArabic() {
        return genderArabic;
    }

    public void setGenderArabic(String genderArabic) {
        this.genderArabic = genderArabic;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getDeepLink() {
        return deepLink;
    }

    public String getDeepLinkCredits() {
        return deepLinkCredits;
    }

    public void setDeepLinkCredits(String deepLinkCredits) {
        this.deepLinkCredits = deepLinkCredits;
    }

    public Integer getOnBoardingStatus() {
        return onBoardingStatus;
    }

    public void setOnBoardingStatus(Integer onBoardingStatus) {
        this.onBoardingStatus = onBoardingStatus;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
