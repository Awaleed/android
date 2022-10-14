package maksab.sd.customer.viewmodels.orders;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import maksab.sd.customer.R;
import maksab.sd.customer.models.providers.OrderModel;
import maksab.sd.customer.network.appnetwork.CustomersService;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by dev2 on 1/6/2018.
 */

public class OrderDeatilsViewModel extends AndroidViewModel {

    ILocalStorage localStorage;

    CustomersService customerService = new CustomersService();

    MutableLiveData<Boolean> isDone = new MutableLiveData<>();

    MutableLiveData<OrderModel> ordermodel = new MutableLiveData<>();

    MutableLiveData<Boolean> isreasonSend = new MutableLiveData<>();

    public OrderDeatilsViewModel(Application application) {
        super(application);
        localStorage = new SharedPreferencesStorage(getApplication());
    }

    private String tokenMaping(String token) {
        return "bearer " + token;
    }

    public void cancelOrder(long orderId) {
        customerService.cancelOrder(tokenMaping(localStorage.getJwtToken().getStringToken()), orderId , new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        isDone.setValue(true);
                    } else {
                        isDone.setValue(false);
                        Toast.makeText(getApplication(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                isDone.setValue(false);
                Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.internetError), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    public LiveData<Boolean> getOrderStateObservable() {
        return isDone;
    }

    public void getOrderById(long id) {
        customerService.getorderById(id, tokenMaping(localStorage.getJwtToken().getStringToken()), new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                if (response.isSuccessful()) {
                    ordermodel.setValue(response.body());
                } else {
                    ordermodel.setValue(null);
                    try {
                        Toast.makeText(getApplication(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                ordermodel.setValue(null);
                Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.internetError), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    public LiveData<OrderModel> getorderByidObserver() {
        return ordermodel;
    }


    public LiveData<Boolean> cancellationReasonObserver(){
        return isreasonSend;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        customerService = null;
        localStorage = null;
    }
}
