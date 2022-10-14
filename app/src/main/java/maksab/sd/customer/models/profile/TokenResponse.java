package maksab.sd.customer.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AdminUser on 11/12/2017.
 */

public class TokenResponse {

    public TokenResponse(String token , String expiration){
        this.token = token;
        this.expiration = expiration;

    }

    @SerializedName("tokenString")
    @Expose
    private String token;

    @SerializedName("expiration")
    @Expose
    private String expiration;


    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getStringToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}