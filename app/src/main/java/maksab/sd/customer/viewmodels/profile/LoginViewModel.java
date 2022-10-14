package maksab.sd.customer.viewmodels.profile;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import maksab.sd.customer.R;
import maksab.sd.customer.models.profile.LoginModel;
import maksab.sd.customer.network.appnetwork.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AdminUser on 10/24/2017.
 */

public class LoginViewModel extends AndroidViewModel {

  private LoginService loginService = new LoginService();

 private MutableLiveData<String> logindata = new MutableLiveData<>();

    public LoginViewModel(Application application) {
        super(application);
    }

    public void login(LoginModel loginModel){

       loginService.Login(loginModel, new Callback<String>() {
          @Override
          public void onResponse(Call<String> call, Response<String> response) {
               if(response.isSuccessful() && response.body()!=null){
                   logindata.setValue(response.body());
               }else {
                   try {
                       Toast.makeText(getApplication() ,response.errorBody().string() , Toast.LENGTH_LONG).show();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
          }

          @Override
          public void onFailure(Call<String> call , Throwable t) {
              logindata.setValue(null);
              Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();

          }
      });
  }

  public LiveData<String> loginObservable(){
     return logindata;
  }

    @Override
    protected void onCleared() {
        super.onCleared();
       loginService = null;

    }
}
