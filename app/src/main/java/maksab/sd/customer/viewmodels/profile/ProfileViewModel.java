package maksab.sd.customer.viewmodels.profile;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import maksab.sd.customer.R;
import maksab.sd.customer.models.profile.ProfileModel;
import maksab.sd.customer.models.profile.UpdateProfileModel;
import maksab.sd.customer.network.appnetwork.CustomersService;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dev on 11/15/2017.
 */

public class ProfileViewModel extends AndroidViewModel {


    private MutableLiveData<Boolean> updateprofilelivedata = new MutableLiveData<>();

    private MutableLiveData<ProfileModel> profileData = new MutableLiveData<>();

    private CustomersService customersService = new CustomersService();


    private ILocalStorage localStorage ;

    public ProfileViewModel(Application application) {
        super(application);
        localStorage = new SharedPreferencesStorage(application);
    }

    private String tokenMaping(String token){
        return "bearer " + token;
    }

    //Update anynoymouse
    public void updateProfile(UpdateProfileModel updateProfile){
        if(localStorage == null) localStorage = new SharedPreferencesStorage(getApplication());

        customersService.updateProfile(updateProfile, tokenMaping(localStorage.getJwtToken().getStringToken()), new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if(response.isSuccessful()&& response.body()!=null){
                    updateprofilelivedata.setValue(true);
                    if(localStorage != null){
                        localStorage.saveUserData(response.body());
                        localStorage.setProfileComplete();
                    }


                }else {
                    updateprofilelivedata.setValue(false);
                    try {
                        Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
             updateprofilelivedata.setValue(false);

                t.printStackTrace();
                Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<Boolean> UpdateProfileObservable(){
        return updateprofilelivedata;
    }

    public void fetchProfileData(){
        if(localStorage == null) localStorage = new SharedPreferencesStorage(getApplication());
            customersService.getProviderProfile(tokenMaping(localStorage.getJwtToken().getStringToken()), new Callback<ProfileModel>() {
                @Override
                public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                    if(response.isSuccessful()&& response.body()!=null){
                        profileData.setValue(response.body());
                    }else {
                        try {
                            Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        profileData.setValue(null);
                    }
                }

                @Override
                public void onFailure(Call<ProfileModel> call, Throwable t) {

                    t.printStackTrace();
                    Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
                   profileData.setValue(null);

                }
            });

    }

    public LiveData<ProfileModel> profileDataObservable() {
        return profileData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        customersService = null;
        localStorage = null;
    }
}
