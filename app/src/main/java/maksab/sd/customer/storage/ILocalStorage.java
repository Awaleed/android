package maksab.sd.customer.storage;

import maksab.sd.customer.models.profile.ProfileModel;
import maksab.sd.customer.models.profile.TokenResponse;

/**
 * Created by AdminUser on 11/13/2017.
 */

public interface ILocalStorage {
    void login();
    void logOut();

    TokenResponse getJwtToken();
    void setToken(TokenResponse token);

    boolean isLogedIn();

    void saveUserData(ProfileModel userProfileModel);
    ProfileModel getUserProfile();
    void setProfileComplete();
    boolean getIsProfileComplete();
    void setUserId(String userId);
    String getUserId();

    boolean checkCastNotification();
    void setCastNotification(boolean value);
    boolean checkCompetitionNotification();
    void setCompetitionNotification(boolean value);
    void savCastNotification(CastNotificatoion castNotificatoion);
    void savCompetitionNotification(CastNotificatoion castNotificatoion);
    CastNotificatoion getCastNotification();
    CastNotificatoion getCompetitionNotification();

    void saveReferralUserId(String userId);
    String getReferralUserId();

    String getDeepLink();
    void setDeepLink(String deepLink);

    void setGreetingMessage(String message);
    String getGreetingMessage();
}
