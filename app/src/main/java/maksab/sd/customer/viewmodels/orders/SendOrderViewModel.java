package maksab.sd.customer.viewmodels.orders;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import maksab.sd.customer.R;
import maksab.sd.customer.models.orders.details.OrderInputModel;
import maksab.sd.customer.models.providers.CheckCuopon;
import maksab.sd.customer.models.providers.CouponModel;
import maksab.sd.customer.network.appnetwork.CustomersService;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dev2 on 11/29/2017.
 */

public class SendOrderViewModel extends AndroidViewModel {

   private MutableLiveData<Boolean> orderSendState = new MutableLiveData<>();

   private MutableLiveData<CouponModel> coupon = new MutableLiveData<>();

    private CustomersService customersService = new CustomersService();
    private ILocalStorage localStorage ;
    private String tokenMaping(String token){
        return "bearer " + token;
    }

    public SendOrderViewModel(Application application) {
        super(application);
        localStorage = new SharedPreferencesStorage(getApplication());
    }

    public void sendOrder(OrderInputModel orderModel) {
        customersService.sendOrder(tokenMaping(localStorage.getJwtToken().getStringToken()), orderModel, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    orderSendState.setValue(true);
                }else {
                    orderSendState.setValue(false);
                    try {
                      Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                orderSendState.setValue(false);
                t.printStackTrace();
                Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<Boolean> sendOrderObservable() {
        return orderSendState;
    }

    public void checkCoupon(CheckCuopon checkCuopon){
        customersService.checkCupon(tokenMaping(localStorage.getJwtToken().getStringToken()), checkCuopon, new Callback<CouponModel>() {
            @Override
            public void onResponse(Call<CouponModel> call, Response<CouponModel> response) {
                    if (response.isSuccessful()){
                        coupon.setValue(response.body());
                    }else {
                        coupon.setValue(null);
                        try {
                            Toast.makeText(getApplication(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }

            @Override
            public void onFailure(Call<CouponModel> call, Throwable t) {
                coupon.setValue(null);
                Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.internetError), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    public LiveData<CouponModel> getcouponObserver(){
        return coupon;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        localStorage = null;
        customersService = null;
    }
}
