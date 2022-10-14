package maksab.sd.customer.viewmodels.quotations;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.io.IOException;

import maksab.sd.customer.R;
import maksab.sd.customer.models.orders.details.OrderInputModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.appnetwork.IUploadInterface;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.OrderStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.util.general.FileModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dev on 3/14/2018.
 */

public class AddQuotationsViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isQuotationsSaved = new MutableLiveData<>();

    private MutableLiveData<String> imagepath = new MutableLiveData<>();

    private ILocalStorage localStorage;

    private String tokenMaping(String token){
        return "bearer " + token;
    }

    public AddQuotationsViewModel(Application application) {
        super(application);
        localStorage = new SharedPreferencesStorage(application);
    }

    public void addQuotation(OrderInputModel orderInputModel){
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , tokenMaping(localStorage.getJwtToken().getStringToken()));
        customersService.addQuotations(orderInputModel).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                OrderStorage storage = new OrderStorage(getApplication());

                if (response.isSuccessful()){
                    storage.clearSavedData();
                    isQuotationsSaved.setValue(true);
                }else {
                    isQuotationsSaved.setValue(false);
                    try {
                        Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                isQuotationsSaved.setValue(false);
                Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addDirctQuotation(OrderInputModel orderInputModel){
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , tokenMaping(localStorage.getJwtToken().getStringToken()));

        customersService.addDirctQuotations(orderInputModel).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    isQuotationsSaved.setValue(true);
                }else {
                    isQuotationsSaved.setValue(false);
                    try {
                        Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                isQuotationsSaved.setValue(false);
                Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<Boolean> addQuotationObserver(){
        return isQuotationsSaved;
    }

    public void uploadImage(File file){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*" ) , file);
        MultipartBody.Part partfile = MultipartBody.Part.createFormData("file" , file.getName() , requestBody);
        IUploadInterface uploadInterface = GetWayServiceGenerator.createService(IUploadInterface.class , tokenMaping(localStorage.getJwtToken().getStringToken()));
        uploadInterface.uploadFile(partfile).enqueue(new Callback<FileModel>() {
            @Override
            public void onResponse(Call<FileModel> call, Response<FileModel> response) {
                if (response.isSuccessful()){
                    imagepath.setValue(response.body().getFilePath());
                }else {
                    imagepath.setValue(null);
                    try {
                        Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<FileModel> call, Throwable t) {
                imagepath.setValue(null);
                Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<String> imageObserver(){
        return imagepath;
    }

}
