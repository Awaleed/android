package maksab.sd.customer.viewmodels.quotations;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.models.orders.details.CancelReasonModel;
import maksab.sd.customer.models.orders.details.OrderOffer;
import maksab.sd.customer.models.orders.details.QuotationViewModel;
import maksab.sd.customer.network.appnetwork.CustomersService;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dev on 3/15/2018.
 */

public class QuotationsViewModel extends AndroidViewModel {

    private MutableLiveData<List<QuotationViewModel>> quotations = new MutableLiveData<>();
    private MutableLiveData<List<OrderOffer>> quotationOffer = new MutableLiveData<>();
    private MutableLiveData<Boolean> isquotationCanceld = new MutableLiveData<>();
   private  MutableLiveData<QuotationViewModel> quotation = new MutableLiveData<>();

   private CustomersService customersService = new CustomersService();

    ILocalStorage localStorage;

    public QuotationsViewModel(Application application) {
        super(application);
        localStorage = new SharedPreferencesStorage(application);
    }

    private String tokenMaping(String token) {
        return "bearer " + token;
    }

    public void getQuotations(boolean newOnly){
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , tokenMaping(localStorage.getJwtToken().getStringToken()));
        customersService.getQuotations(newOnly).enqueue(new Callback<List<QuotationViewModel>>() {
            @Override
            public void onResponse(Call<List<QuotationViewModel>> call, Response<List<QuotationViewModel>> response) {
                if (response.isSuccessful()){
                    quotations.setValue(response.body());
                }else {
                    quotations.setValue(null);
                    try {
                        Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<QuotationViewModel>> call, Throwable t) {
                quotations.setValue(null);
                Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
            }
        });

    }

    public LiveData<List<QuotationViewModel>> getquotationsObserver(){
        return quotations;
    }

    public void getQuotationOffers(long quotatioid){
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , tokenMaping(localStorage.getJwtToken().getStringToken()));
        customersService.getOrderOffers(quotatioid, 1).enqueue(new Callback<List<OrderOffer>>() {
            @Override
            public void onResponse(Call<List<OrderOffer>> call, Response<List<OrderOffer>> response) {
                if (response.isSuccessful()){
                    quotationOffer.setValue(response.body());
                }else {
                    quotationOffer.setValue(null);
                    try {
                        Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OrderOffer>> call, Throwable t) {
                quotationOffer.setValue(null);
                Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<List<OrderOffer>> quotionoffersObservable(){
        return quotationOffer;
    }

    public void canelQuotation(long quotatonid , CancelReasonModel model){

        customersService.cancelQuotation(tokenMaping(localStorage.getJwtToken().getStringToken()) , quotatonid , model , new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    isquotationCanceld.setValue(true);
                }else {
                    isquotationCanceld.setValue(false);
                    try {
                        Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                isquotationCanceld.setValue(false);
                Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<Boolean> cancelQuotationObserver(){
        return isquotationCanceld;
    }

    public void getquoatation(long quotationId){
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , tokenMaping(localStorage.getJwtToken().getStringToken()));
        customersService.getquotation(quotationId).enqueue(new Callback<QuotationViewModel>() {
            @Override
            public void onResponse(Call<QuotationViewModel> call, Response<QuotationViewModel> response) {
                if (response.isSuccessful()){
                    quotation.setValue(response.body());
                }else {
                    quotation.setValue(null);
                    try {
                        Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<QuotationViewModel> call, Throwable t) {
                quotation.setValue(null);
                Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<QuotationViewModel> quotationObserver(){
        return quotation;
    }


}
