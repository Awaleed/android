package maksab.sd.customer.ui.general.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import org.json.JSONException;

import io.branch.referral.Branch;
import maksab.sd.customer.BuildConfig;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.main.VersionReslutModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.main.activties.MainActivity;
import maksab.sd.customer.util.general.ForceUpdateScreen;
import maksab.sd.customer.wizards.welcome.WizardActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private String providerProfileUserId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashScreenInit();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Branch init
        Branch.getInstance().initSession((referringParams, error) -> {
            if (error == null) {
                try {
                  if(referringParams.has("userid")) {
                      String userid =  referringParams.getString("userid");
                      providerProfileUserId = userid;
                  }

                    if(referringParams.has("customer_user_id")) {
                        String customer_userId =  referringParams.getString("customer_user_id");
                        addReffralLocalStorage(customer_userId);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.i("BRANCH SDK", error.getMessage());
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
    }

//    private void videoInit() {
//        videoView =  findViewById(R.id.player);
//
//        Uri uri = Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/" + R.raw.logo);
//        videoView.setup(uri, ContextCompat.getColor(this, R.color.colorPrimary));
//        videoView.setFrameVideoViewListener(new FrameVideoViewListener() {
//            @Override
//            public void mediaPlayerPrepared(final MediaPlayer mediaPlayer) {
//                mediaPlayer.setLooping(false);
//                mediaPlayer.start();
//                mediaPlayer.setOnCompletionListener(mp -> mediaPlayer.seekTo(mediaPlayer.getDuration()));
//            }
//
//            @Override
//            public void mediaPlayerPrepareFailed(MediaPlayer mediaPlayer, String s) {
//
//            }
//        });
//    }

    private void splashScreenInit(){
        new Handler().postDelayed(() -> {
            ILocalStorage localStorage = new SharedPreferencesStorage(this);
            if(localStorage.isLogedIn()){
                updateVersionCodeToServer();
            }else {
                openLoginActivity();
            }

        }, SPLASH_DISPLAY_LENGTH);

    }

    void openLoginActivity(){
        Intent mainIntent = new Intent(SplashActivity.this , WizardActivity.class);
        SplashActivity.this.startActivity(mainIntent);
        SplashActivity.this.finish();
    }

    void openMainActivity(){
        Intent mainIntent = new Intent(SplashActivity.this , MainActivity.class);
        mainIntent.putExtra("profile.id" , providerProfileUserId);
        SplashActivity.this.startActivity(mainIntent);
        SplashActivity.this.finish();

    }

       void addReffralLocalStorage(String userid){
         ILocalStorage localStorage = new SharedPreferencesStorage(getApplicationContext());
         localStorage.saveReferralUserId(userid);
       }

    private void updateVersionCodeToServer(){
        ILocalStorage localStorage = new SharedPreferencesStorage(getApplicationContext());
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class
                , "bearer " + localStorage.getJwtToken().getStringToken());

        customersService.updateVersionCode(BuildConfig.VERSION_CODE).enqueue(new Callback<VersionReslutModel>() {
            @Override
            public void onResponse(Call<VersionReslutModel> call, Response<VersionReslutModel> response) {
                if(response.isSuccessful() && response.body().getIsUpdateFound()){
                    forceUpdate(response.body().getIsForcedUpdate());
                }else{
                    openMainActivity();
                }
            }

            @Override
            public void onFailure(Call<VersionReslutModel> call, Throwable t) {

            }
        });
    }

    private void forceUpdate(boolean isForced) {
        Intent intent = new Intent(this, ForceUpdateScreen.class);
        intent.putExtra("isForced_to_update" , isForced);
        intent.putExtra("profile.id" , providerProfileUserId);
        startActivity(intent);
    }
}
