package maksab.sd.customer.viewmodels.providers;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.models.providers.GalleryModel;
import maksab.sd.customer.network.appnetwork.CustomersService;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Dev on 2/22/2018.
 */

public class GalleriesViewModel extends AndroidViewModel {

    ILocalStorage localStorage;
    CustomersService customersService = new CustomersService();

    MutableLiveData<List<GalleryModel>> galleries = new MutableLiveData<>();

    public GalleriesViewModel(Application application) {
        super(application);
        localStorage = new SharedPreferencesStorage(application);
    }

    private String tokenMaping(String token) {
        return "bearer " + token;
    }

    public void getGalleries(int pagenum , String providerid){
        customersService.getGalleries(tokenMaping(localStorage.getJwtToken().getStringToken()), pagenum , providerid, new Callback<List<GalleryModel>>() {
            @Override
            public void onResponse(Call<List<GalleryModel>> call, Response<List<GalleryModel>> response) {
                if (response.isSuccessful()){
                    galleries.setValue(response.body());
                }else {
                    galleries.setValue(null);
                    try {
                        Toast.makeText(getApplication(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GalleryModel>> call, Throwable t) {
                galleries.setValue(null);
                Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.internetError), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    public LiveData<List<GalleryModel>> galleriesObservable(){
        return galleries;
    }

    public void viewCount(long itemId){
        customersService.viewCount(tokenMaping(localStorage.getJwtToken().getStringToken()), itemId, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        customersService = null;
        localStorage = null;
    }

}
