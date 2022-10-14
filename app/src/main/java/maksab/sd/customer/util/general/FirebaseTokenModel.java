package maksab.sd.customer.util.general;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dev2 on 1/21/2018.
 */

public class FirebaseTokenModel {

    @SerializedName("token")
    @Expose
    private String  token;

    public FirebaseTokenModel(String token){
        this.token = token;
    }
}
