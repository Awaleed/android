package maksab.sd.customer.ui.profile.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.branch.referral.Branch;
import maksab.sd.customer.BuildConfig;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.profile.TokenModel;
import maksab.sd.customer.models.profile.TokenResponse;
import maksab.sd.customer.network.appnetwork.CustomersService;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.main.activties.MainActivity;
import maksab.sd.customer.util.constants.ClintTypeConstaent;
import maksab.sd.customer.util.general.FirebaseTokenModel;
import maksab.sd.customer.viewmodels.main.SupportViewModel;
import maksab.sd.customer.viewmodels.profile.VerifyOtpViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpActivity extends BaseActivity {

    VerifyOtpViewModel verifyOtpViewModel;
    SupportViewModel supportViewModel;

    @BindView(R.id.phonenumber)
    TextView phoneNumberlable;
    @BindView(R.id.counter)
    TextView counter;
    @BindView(R.id.call_us)
    TextView call_us;
    @BindView(R.id.optcode)
    Pinview optcode;
    @BindView(R.id.resent_layout)
    View resent_layout;
    @BindView(R.id.timer_layout)
    View timer_layout;
    CountDownTimer timer;
    private String phoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this);

        resent_layout.setVisibility(View.INVISIBLE);
        phoneNumber = getIntent().getStringExtra("mobile");
        phoneNumberlable.setText(phoneNumber);

        verifyOtpViewModel = ViewModelProviders.of(this).get(VerifyOtpViewModel.class);
        supportViewModel = ViewModelProviders.of(this).get(SupportViewModel.class);

        retrieveTokenObservable();
        onResetDone();
        countDownTimer();

        optcode.setPinViewEventListener((pinview, b) -> {
            if (optcode.getValue().length() == 4) {
                onVerifyClick();
            }
        });

        call_us.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+249113555535"));
            startActivity(intent);
        });

        resent_layout.setOnClickListener(v -> {
            timer_layout.setVisibility(View.VISIBLE);
            resent_layout.setVisibility(View.INVISIBLE);
            countDownTimer();

            showWaitDialog();
            supportViewModel.resendOTP(phoneNumber, ClintTypeConstaent.CUSTOMER);
        });
    }

    void onVerifyClick() {
        if (!TextUtils.isEmpty(optcode.getValue())) {
            showWaitDialog();
            verifyOtpViewModel.RetrieveToken(new TokenModel(phoneNumber, optcode.getValue(), ClintTypeConstaent.CUSTOMER));
        } else {
            Toast.makeText(this, R.string.please_enter_otp, Toast.LENGTH_LONG).show();
        }
    }

    void retrieveTokenObservable() {
        verifyOtpViewModel.TokenObservable().observe(this, tokenResponse -> {
            if (tokenResponse != null) {
                dismissWaitDialog();
                Branch.getInstance().setIdentity(phoneNumber);
                login(tokenResponse);

            } else {
                dismissWaitDialog();
            }
        });
    }

    private void onResetDone() {
        supportViewModel.PostFormObserver().observe(this, isdone -> {
            dismissWaitDialog();
            if (isdone) {

            }
        });
    }

    void countDownTimer() {
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                counter.setText(l / 1000 + " " + getString(R.string.seconds));
            }

            @Override
            public void onFinish() {
                resent_layout.setVisibility(View.VISIBLE);
                timer_layout.setVisibility(View.INVISIBLE);
            }
        }.start();
    }

    //save token in localstorage , set login state to true , then open main activity and close this activity
    void login(TokenResponse tokenResponse) {
        ILocalStorage localStorage = new SharedPreferencesStorage(this);
        localStorage.setToken(tokenResponse);
        localStorage.login();
        FirebaseApp.initializeApp(this);

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                String token = task.getResult();
                sendRegistrationToServer(token);
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic(BuildConfig.TopicName);
    }

    private String tokenMaping(String token) {
        return "bearer " + token;
    }

    private void sendRegistrationToServer(String token) {
        CustomersService customersService = new CustomersService();
        ILocalStorage localStorage = new SharedPreferencesStorage(this);
        if (localStorage.getJwtToken() != null) {
            customersService.addToken(tokenMaping(localStorage.getJwtToken().getStringToken()), new FirebaseTokenModel(token), new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                     //   timer.cancel();
                        View view = VerifyOtpActivity.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }

                    }
                    Intent intent = new Intent(VerifyOtpActivity.this, MainActivity.class);
                    startActivity(intent);
                    VerifyOtpActivity.this.finish();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    Intent intent = new Intent(VerifyOtpActivity.this, MainActivity.class);
                    startActivity(intent);
                    VerifyOtpActivity.this.finish();
                }
            });
        }
    }
}