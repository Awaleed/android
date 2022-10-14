package maksab.sd.customer.viewmodels.profile;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import maksab.sd.customer.R;
import maksab.sd.customer.models.profile.MobileConfirmInputModel;
import maksab.sd.customer.network.appnetwork.LoginService;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmMobileViewModel extends AndroidViewModel {
    LoginService loginService = new LoginService();

    ILocalStorage localStorage ;

    MutableLiveData<Boolean> tokenResponse = new MutableLiveData<>();

    public ConfirmMobileViewModel(Application application) {
        super(application);
        localStorage = new SharedPreferencesStorage(application);
    }

    public LiveData<Boolean> TokenObservable(){
        return tokenResponse;
    }

    public void MobileConfirmation(MobileConfirmInputModel model){
        loginService.MobileConfirmation(model, new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    tokenResponse.setValue(response.body());
                }
                else {
                    try {
                        Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    tokenResponse.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
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
