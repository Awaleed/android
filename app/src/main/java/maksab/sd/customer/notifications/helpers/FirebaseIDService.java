package maksab.sd.customer.notifications.helpers;

import com.google.firebase.messaging.FirebaseMessagingService;

import maksab.sd.customer.BuildConfig;
import maksab.sd.customer.network.appnetwork.CustomersService;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.util.general.FirebaseTokenModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dev2 on 12/24/2017.
 */

public class FirebaseIDService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseIDService";

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        if (!BuildConfig.DEBUG)
            sendRegistrationToServer(token);
    }

    private String tokenMaping(String token){
        return "bearer " + token;
    }

    private void sendRegistrationToServer(String token) {
        CustomersService customersService = new CustomersService();
        ILocalStorage localStorage = new SharedPreferencesStorage(this);
        if (localStorage.getJwtToken() != null){
            customersService.addToken(tokenMaping(localStorage.getJwtToken().getStringToken()), new FirebaseTokenModel(token), new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //do something here
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

}
