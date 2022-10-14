package maksab.sd.customer.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AdminUser on 11/12/2017.
 */

public class LoginModel {

    public LoginModel(String mobile , String clinttype){

        this.phonenumber = mobile;
        this.clinttype = clinttype;
    }

    @SerializedName("mobile")
    @Expose
    private String phonenumber;
    @SerializedName("clientType")
    @Expose
    private String clinttype;



    public String getClinttype() {
        return clinttype;
    }

    public void setClinttype(String clinttype) {
        this.clinttype = clinttype;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
