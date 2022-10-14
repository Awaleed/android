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
import maksab.sd.customer.models.speciality.SpecialityModel;
import maksab.sd.customer.network.appnetwork.CustomersService;
import maksab.sd.customer.network.appnetwork.LookUpsService;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dev on 11/28/2017.
 */

public class ProvidersListViewModel extends AndroidViewModel {

    MutableLiveData<List<ProviderDetailsModel>> providersListModelLiveData = new MutableLiveData<>();
    MutableLiveData<List<SpecialityModel>> catspecialtyLiveData = new MutableLiveData<>();

  CustomersService customersService = new CustomersService();
  LookUpsService lookUpsService = new LookUpsService();
    private ILocalStorage localStorage ;

    private String tokenMaping(String token){
        return "bearer " + token;
    }

    public ProvidersListViewModel(Application application) {
        super(application);
        localStorage = new SharedPreferencesStorage(application);
    }

    public void getProviderNearBy(int speciltyId , int addressId , int pagenumb) {
        customersService.getProvidersNearBy(tokenMaping(localStorage.getJwtToken().getStringToken()), speciltyId, addressId , pagenumb, new Callback<List<ProviderDetailsModel>>() {
            @Override
            public void onResponse(Call<List<ProviderDetailsModel>> call, Response<List<ProviderDetailsModel>> response) {
                if(response.isSuccessful() && response.body() !=null){
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

    public void getCatSpecialty(int catid){
      lookUpsService.GetCatSpecialty(catid, new Callback<List<SpecialityModel>>() {
          @Override
          public void onResponse(Call<List<SpecialityModel>> call, Response<List<SpecialityModel>> response) {
              if(response.isSuccessful() && response.body() !=null){
                  catspecialtyLiveData.setValue(response.body());
              }else {
                  catspecialtyLiveData.setValue(null);
                  try {
                      Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
          }

          @Override
          public void onFailure(Call<List<SpecialityModel>> call, Throwable t) {
              catspecialtyLiveData.setValue(null);
              Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
          }
      });
    }

    public LiveData<List<ProviderDetailsModel>> getProvidersObservable() {
        return providersListModelLiveData;
    }

    public LiveData<List<SpecialityModel>> getCatSpecialtyObservable() {
        return catspecialtyLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        localStorage = null;
        customersService = null;
        lookUpsService = null;
    }
}
