package maksab.sd.customer.viewmodels.orders;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.models.providers.OrderModel;
import maksab.sd.customer.network.appnetwork.CustomersService;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dev on 11/19/2017.
 */

public class OrderViewModel extends AndroidViewModel {

    ILocalStorage localStorage ;
    CustomersService customersService = new CustomersService();

    MutableLiveData<List<OrderModel>> ordermodelLiveData = new MutableLiveData<>();

    public OrderViewModel(Application application) {
        super(application);
        localStorage = new SharedPreferencesStorage(getApplication());
    }

    private String tokenMaping(String token){
        return "bearer " + token;
    }

    public void getOrders(int pagenum , int orderstatusid) {
        customersService.getOrders(pagenum, orderstatusid , tokenMaping(localStorage.getJwtToken().getStringToken()), new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                if(response.isSuccessful()){
                    ordermodelLiveData.setValue(response.body());
                }else {
                    ordermodelLiveData.setValue(null);
                    try {
                        Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {
                ordermodelLiveData.setValue(null);
                t.printStackTrace();
                Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();

            }
        });

    }

    public LiveData<List<OrderModel>> ordersObseravble(){
        return ordermodelLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        customersService = null;
        localStorage = null;
    }
}
