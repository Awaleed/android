package maksab.sd.customer.network.appnetwork;

import maksab.sd.customer.models.profile.ChangeMobileInputModel;
import maksab.sd.customer.models.profile.LoginModel;
import maksab.sd.customer.models.profile.MobileConfirmInputModel;
import maksab.sd.customer.models.profile.TokenModel;
import maksab.sd.customer.models.profile.TokenResponse;
import maksab.sd.customer.network.servicegenratores.IdentityServiceGenerator;
import retrofit2.Callback;

/**
 * Created by AdminUser on 11/12/2017.
 */

public class LoginService {

    public void Login(LoginModel loginModel, Callback<String> callback) {
        ILoginService loginService = IdentityServiceGenerator.createService(ILoginService.class);
        loginService.Login(loginModel).enqueue(callback);
    }

    public void Token(TokenModel tokenModel, Callback<TokenResponse> tokenResponseCallback) {
        ILoginService loginService = IdentityServiceGenerator.createService(ILoginService.class);
        loginService.Token(tokenModel).enqueue(tokenResponseCallback);
    }

    public void UpdateMobile(ChangeMobileInputModel model, Callback<Boolean> tokenResponseCallback) {
        ILoginService loginService = IdentityServiceGenerator.createService(ILoginService.class);
        loginService.UpdateMobile(model).enqueue(tokenResponseCallback);
    }

    public void MobileConfirmation(MobileConfirmInputModel model, Callback<Boolean> tokenResponseCallback) {
        ILoginService loginService = IdentityServiceGenerator.createService(ILoginService.class);
        loginService.MobileConfirmation(model).enqueue(tokenResponseCallback);
    }
}



