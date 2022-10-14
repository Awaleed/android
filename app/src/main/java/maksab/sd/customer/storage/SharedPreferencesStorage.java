package maksab.sd.customer.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import maksab.sd.customer.models.profile.ProfileModel;
import maksab.sd.customer.models.profile.TokenResponse;

public class SharedPreferencesStorage implements ILocalStorage {
    private final Context _context;
    private final SharedPreferences _SharedPreferences;
    private final SharedPreferences.Editor _editor;
    private final String fileName = "datastore.xml";
    private final String accessTokenfield = "TokenData";
    private final String logingStatefield = "LogState";
    private final String userDatafield = "UserData";
    private final String profileCompleteField = "profileComplete";
    private final String useridfield = "userid";
    private final String castnotifiactiondata = "castnotifydata";
    private final String competitionNotifiactiondata = "compNotifydata";
    private final String competitionNotifiaction = "compNotify";
    private final String castnotifiaction = "castnotify";

    public SharedPreferencesStorage(Context context) {
        _context = context;
        _SharedPreferences = _context.getSharedPreferences(fileName , Context.MODE_PRIVATE);
        _editor = _SharedPreferences.edit();
    }

    @Override
    public void login() {
        _editor.putBoolean(logingStatefield , true);
        _editor.commit();
    }

    @Override
    public void logOut() {
        _editor.putBoolean(logingStatefield , false);
        _editor.clear();
        _editor.commit();

    }

    @Override
    public TokenResponse getJwtToken() {
        Gson gson = new Gson();
        return gson.fromJson(_SharedPreferences.getString(accessTokenfield , "") ,TokenResponse.class);
    }

    @Override
    public void setToken(TokenResponse token) {
        Gson gson = new Gson();
         _editor.putString( accessTokenfield , gson.toJson(token));
         _editor.commit();
    }

    @Override
    public boolean isLogedIn() {
        return _SharedPreferences.getBoolean(logingStatefield , false);
    }

    @Override
    public void saveUserData(ProfileModel fullProfileModel) {
        Gson gson = new Gson();
        _editor.putString(userDatafield , gson.toJson(fullProfileModel));
        _editor.commit();
    }

    @Override
    public ProfileModel getUserProfile() {
        Gson gson = new Gson();
        String data = _SharedPreferences.getString(userDatafield , "");
        if(TextUtils.isEmpty(data)){
            return null;
        }
        return gson.fromJson(data, ProfileModel.class) ;
    }

    @Override
    public void setProfileComplete() {
        _editor.putBoolean(profileCompleteField , true);
        _editor.commit();
    }

    @Override
    public boolean getIsProfileComplete() {
        return _SharedPreferences.getBoolean(profileCompleteField , false);
    }

    @Override
    public void setUserId(String userId) {
     _editor.putString(useridfield , userId);
     _editor.commit();
    }

    @Override
    public String getUserId() {
        return _SharedPreferences.getString(useridfield , "");
    }

    @Override
    public boolean checkCastNotification() {
        return _SharedPreferences.getBoolean(castnotifiaction , false);
    }

    @Override
    public void setCastNotification(boolean value) {
        _editor.putBoolean(castnotifiaction , value);
        _editor.commit();
    }

    @Override
    public boolean checkCompetitionNotification() {
        return _SharedPreferences.getBoolean(competitionNotifiaction , false);
    }

    @Override
    public void setCompetitionNotification(boolean value) {
        _editor.putBoolean(competitionNotifiaction , value);
        _editor.commit();
    }

    @Override
    public void savCastNotification(CastNotificatoion castNotificatoion) {
        Gson gson = new Gson();
        _editor.putString(castnotifiactiondata , gson.toJson(castNotificatoion));
        _editor.commit();
    }

    @Override
    public void savCompetitionNotification(CastNotificatoion castNotificatoion) {
        Gson gson = new Gson();
        _editor.putString(competitionNotifiactiondata , gson.toJson(castNotificatoion));
        _editor.commit();
    }

    @Override
    public CastNotificatoion getCastNotification() {
        Gson gson = new Gson();
        return gson.fromJson(_SharedPreferences.getString(castnotifiactiondata , null) , CastNotificatoion.class);
    }

    @Override
    public CastNotificatoion getCompetitionNotification() {
        Gson gson = new Gson();
        return gson.fromJson(_SharedPreferences.getString(competitionNotifiactiondata , null) , CastNotificatoion.class);
    }

    @Override
    public void saveReferralUserId(String userId) {
        _editor.putString("referral" , userId);
        _editor.commit();
    }

    @Override
    public String getReferralUserId() {
        return _SharedPreferences.getString("referral" , null);
    }

    @Override
    public String getDeepLink() {
        return _SharedPreferences.getString("deepLink" , null);
    }

    @Override
    public void setDeepLink(String deepLink) {
        _editor.putString("deepLink" , deepLink);
        _editor.commit();
    }

    @Override
    public void setGreetingMessage(String message) {
        _editor.putString("greeting_message" , message);
        _editor.commit();
    }

    @Override
    public String getGreetingMessage() {
        return _SharedPreferences.getString("greeting_message" , null);
    }
}
