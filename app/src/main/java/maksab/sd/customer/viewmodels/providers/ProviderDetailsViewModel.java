package maksab.sd.customer.viewmodels.providers;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import maksab.sd.customer.R;
import maksab.sd.customer.models.providers.CheckFavoriteModel;
import maksab.sd.customer.models.providers.ProviderDetailsModel;
import maksab.sd.customer.models.providers.SetFavoriteModel;
import maksab.sd.customer.network.appnetwork.CustomersService;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dev on 11/15/2017.
 */

public class ProviderDetailsViewModel extends AndroidViewModel {


    private MutableLiveData<Boolean> setToFavoriteStat = new MutableLiveData<>();
    private MutableLiveData<CheckFavoriteModel> checkFavoriteModelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ProviderDetailsModel> providerDetails = new MutableLiveData<>();

    private CustomersService customersService = new CustomersService();

    private ILocalStorage localStorage ;

    public ProviderDetailsViewModel(Application application) {
        super(application);
        localStorage = new SharedPreferencesStorage(application);
    }

    private String tokenMaping(String token){
        return "bearer " + token;
    }


    public void setToFavorite(SetFavoriteModel setFavoriteModel){
        customersService.setToFavorite(tokenMaping(localStorage.getJwtToken().getStringToken()), setFavoriteModel, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    setToFavoriteStat.setValue(true);
                }else {
                    setToFavoriteStat.setValue(false);
                    try {
                        Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                setToFavoriteStat.setValue(false);
                Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<Boolean> setFavoriteObservable(){
        return setToFavoriteStat;
    }

    public void getFavorite(String userid){
        CustomersService customersService = new CustomersService();
        customersService.getfavorite(tokenMaping(localStorage.getJwtToken().getStringToken()), userid, new Callback<CheckFavoriteModel>() {
            @Override
            public void onResponse(Call<CheckFavoriteModel> call, Response<CheckFavoriteModel> response) {
                if (response.isSuccessful()){
                    checkFavoriteModelMutableLiveData.setValue(response.body());
                }else {
                    try {
                        checkFavoriteModelMutableLiveData.setValue(null);
                        Toast.makeText(getApplication(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckFavoriteModel> call, Throwable t) {
                checkFavoriteModelMutableLiveData.setValue(null);
                Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.internetError), Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<CheckFavoriteModel> getCheckFavoriteModelLiveData(){
        return checkFavoriteModelMutableLiveData;
    }

    public void getProviderById(String userid){
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , tokenMaping(localStorage.getJwtToken().getStringToken()));
        customersService.getProviderProfile(userid).enqueue(new Callback<ProviderDetailsModel>() {
            @Override
            public void onResponse(Call<ProviderDetailsModel> call, Response<ProviderDetailsModel> response) {
                if (response.isSuccessful()){
                    providerDetails.setValue(response.body());

                }else {
                    providerDetails.setValue(null);
                    try {
                        Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProviderDetailsModel> call, Throwable t) {
                providerDetails.setValue(null);
                Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<ProviderDetailsModel> providerDetails(){
        return providerDetails;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        customersService = null;
    }
}
