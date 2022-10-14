package maksab.sd.customer.viewmodels.profile;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.File;

import maksab.sd.customer.R;
import maksab.sd.customer.network.appnetwork.CustomersService;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.util.general.FileModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dev on 11/18/2017.
 */

public class UploadFileViewModel extends AndroidViewModel {
    private CustomersService customersService = new CustomersService();
    private MutableLiveData<String> filePath = new MutableLiveData<>();
    private ILocalStorage localStorage;

    public UploadFileViewModel(Application application) {
        super(application);
        localStorage = new SharedPreferencesStorage(getApplication());
    }

    public void upload(File file) {
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        customersService.uploadFile(file, token, new Callback<FileModel>() {
            @Override
            public void onResponse(Call<FileModel> call, Response<FileModel> response) {
                try {
                    if (response.isSuccessful()) {
                        FileModel fileModel = response.body();
                        filePath.setValue(fileModel.getFilePath());
                    }
                    else {
                        Toast.makeText(getApplication(), R.string.internetError, Toast.LENGTH_LONG).show();
                        filePath.setValue(null);
                    }
                }
                catch(Exception e) {
                    filePath.setValue(null);
                    Toast.makeText(getApplication(), R.string.internetError, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<FileModel> call, Throwable t) {
                filePath.setValue(null);
                Toast.makeText(getApplication(), R.string.internetError, Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<String> getFilePathObserver() {
        return filePath;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        customersService = null;
        localStorage = null;
    }
}
