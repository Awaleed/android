package maksab.sd.customer.viewmodels.profile;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import maksab.sd.customer.R;
import maksab.sd.customer.models.profile.TokenModel;
import maksab.sd.customer.models.profile.TokenResponse;
import maksab.sd.customer.network.appnetwork.LoginService;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AdminUser on 11/12/2017.
 */

public class VerifyOtpViewModel extends AndroidViewModel {
    LoginService loginService = new LoginService();

    ILocalStorage localStorage ;

    MutableLiveData<TokenResponse> tokenResponse = new MutableLiveData<>();

    public VerifyOtpViewModel(Application application) {
        super(application);
        localStorage = new SharedPreferencesStorage(application);
    }

    public LiveData<TokenResponse> TokenObservable(){
        return tokenResponse;
    }

    public void RetrieveToken(TokenModel tokenModel){
        loginService.Token(tokenModel, new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                    if(response.isSuccessful()){
                        tokenResponse.setValue(response.body());
                    }else {
                        try {
                            Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        tokenResponse.setValue(null);
                    }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                        tokenResponse.setValue(null);
                Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        loginService = null;
        localStorage = null;
    }
}
