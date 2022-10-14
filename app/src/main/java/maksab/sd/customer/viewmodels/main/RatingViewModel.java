package maksab.sd.customer.viewmodels.main;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import maksab.sd.customer.R;
import maksab.sd.customer.models.main.RateOrder;
import maksab.sd.customer.network.appnetwork.CustomersService;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AdminUser on 02/06/2018.
 */

public class RatingViewModel extends AndroidViewModel {

    private CustomersService customersService = new CustomersService();

    private ILocalStorage localStorage ;
    MutableLiveData<Boolean> isdone = new MutableLiveData<>();


    public RatingViewModel(Application application) {
        super(application);
        localStorage = new SharedPreferencesStorage(application);
    }

    private String tokenMaping(String token){
        return "bearer " + token;
    }


    public void ratingProvider(RateOrder rateOrder){
        customersService.ratingProvider(tokenMaping(localStorage.getJwtToken().getStringToken()), rateOrder, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    isdone.setValue(true);
                }else {
                    isdone.setValue(false);
                    try {
                        Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                isdone.setValue(false);
                t.printStackTrace();
                Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<Boolean> saveRatingObserver(){
        return isdone;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        localStorage = null;
        customersService = null;

    }
}
