package maksab.sd.customer.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dev on 11/27/2017.
 */

public class UpdateProfileModel {


    @SerializedName("genderId")
    @Expose
    private Integer genderId;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("identityNo")
    @Expose
    private String identityNo;
    @SerializedName("mobile")
    @Expose
    private String mobileNo;

    @SerializedName("profileImage")
    @Expose
    private String profileImage;

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("accountType")
    @Expose
    private Integer accountType;

    @SerializedName("birthDate")
    @Expose
    private String birthDate;

    /**
     * No args constructor for use in serialization
     *
     */
    public UpdateProfileModel() {
    }

    /**
     *  @param genderId
     * @param fullName
     * @param identityNo
     * @param mobileNo
     * @param accountType
     */
    public UpdateProfileModel(Integer genderId, String fullName, String identityNo, String mobileNo, String email, Integer accountType) {
        super();
        this.genderId = genderId;
        this.fullName = fullName;
        this.identityNo = identityNo;
        this.mobileNo = mobileNo;
        this.email = email;
        this.accountType = accountType;
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


    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
