package maksab.sd.customer.viewmodels.providers;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.models.providers.ProviderDetailsModel;
import maksab.sd.customer.network.appnetwork.CustomersService;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dev on 11/28/2017.
 */

public class FavouriteProvidesViewModel extends AndroidViewModel {
    MutableLiveData<List<ProviderDetailsModel>> providersListModelLiveData = new MutableLiveData<>();

    CustomersService customersService = new CustomersService();
    private ILocalStorage localStorage ;
    private String TokenMaping(String token){
        return "bearer " + token;
    }

    public FavouriteProvidesViewModel(Application application) {
        super(application);
        localStorage = new SharedPreferencesStorage(application);
    }

    public void getFavouritesProvider(){
        customersService.getFavouritesProviders(TokenMaping(localStorage.getJwtToken().getStringToken()), new Callback<List<ProviderDetailsModel>>() {
            @Override
            public void onResponse(Call<List<ProviderDetailsModel>> call, Response<List<ProviderDetailsModel>> response) {
                if(response.isSuccessful() && response.body() != null){
                    providersListModelLiveData.setValue(response.body());

                }else {
                    providersListModelLiveData.setValue(null);
                    try {
                        Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProviderDetailsModel>> call, Throwable t) {
                providersListModelLiveData.setValue(null);
                t.printStackTrace();
                Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
            }
        });

    }

    public LiveData<List<ProviderDetailsModel>> getFavoiritesObservable(){
        return providersListModelLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        localStorage = null;
        customersService = null;
    }
}
