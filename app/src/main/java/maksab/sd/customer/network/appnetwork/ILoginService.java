package maksab.sd.customer.network.appnetwork;

import maksab.sd.customer.models.profile.ChangeMobileInputModel;
import maksab.sd.customer.models.profile.LoginModel;
import maksab.sd.customer.models.profile.MobileConfirmInputModel;
import maksab.sd.customer.models.profile.TokenModel;
import maksab.sd.customer.models.profile.TokenResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by AdminUser on 11/12/2017.
 */

public interface ILoginService {
    @POST("accounts")
    Call<String> Login(@Body LoginModel loginModel);

    @POST("auth")
    Call<TokenResponse> Token(@Body TokenModel tokenModel);

    @POST("accounts/update_mobile")
    Call<Boolean> UpdateMobile(@Body ChangeMobileInputModel model);

    @POST("accounts/confiramtion_mobile")
    Call<Boolean> MobileConfirmation(@Body MobileConfirmInputModel model);
}
