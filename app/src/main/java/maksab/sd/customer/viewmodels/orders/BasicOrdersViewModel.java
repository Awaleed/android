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

public class BasicOrdersViewModel extends AndroidViewModel {

    MutableLiveData<List<OrderModel>> ordersandqouations = new MutableLiveData<>();
    private CustomersService customersService = new CustomersService();
    ILocalStorage localStorage ;

    public BasicOrdersViewModel(Application application) {
        super(application);
        localStorage = new SharedPreferencesStorage(application);
    }

    private String tokenMaping(String token) {
        return "bearer " + token;
    }


    public void getBasicOrders(int pagenum,int combinedId){

        customersService.getBasicOrders(tokenMaping(localStorage.getJwtToken().getStringToken()),pagenum , combinedId , new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                if (response.isSuccessful()){
                    ordersandqouations.setValue(response.body());
                }else {
                    ordersandqouations.setValue(null);
                    try {
                        Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {
                t.printStackTrace();
                ordersandqouations.setValue(null);
                Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
            }
        });

    }

    public LiveData<List<OrderModel>> getBasicOrdersObservable(){
        return ordersandqouations;
    }

}
