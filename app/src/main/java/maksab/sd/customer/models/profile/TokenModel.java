package maksab.sd.customer.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AdminUser on 11/12/2017.
 */

public class TokenModel  {

    public TokenModel(String moblie , String otp , String clientType){
        this.moblie = moblie;
        this.otp = otp;
        this.clientType = clientType;
    }

    @SerializedName("mobile")
    @Expose
    private String moblie;
    @SerializedName("oTP")
    @Expose
    private String otp;

    @SerializedName("clientType")
    @Expose
    private String clientType;

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public String getOpt() {
        return otp;
    }

    public void setOpt(String opt) {
        this.otp = opt;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
}
