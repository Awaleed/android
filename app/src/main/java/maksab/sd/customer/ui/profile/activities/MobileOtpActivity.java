package maksab.sd.customer.ui.profile.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.goodiebag.pinview.Pinview;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.profile.ChangeMobileInputModel;
import maksab.sd.customer.models.profile.MobileConfirmInputModel;
import maksab.sd.customer.util.constants.ClintTypeConstaent;
import maksab.sd.customer.viewmodels.profile.ConfirmMobileViewModel;
import maksab.sd.customer.viewmodels.profile.UpdateMobileViewModel;

public class MobileOtpActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    UpdateMobileViewModel updateMobileViewModel;
    ConfirmMobileViewModel confirmMobileViewModel;

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
    private String originalMobile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_otp);
        ButterKnife.bind(this);
        setupToolbar();
        initViews();
    }

    private void initViews() {
        resent_layout.setVisibility(View.INVISIBLE);
        phoneNumber = getIntent().getStringExtra("mobile");
        originalMobile = getIntent().getStringExtra("originalMobile");
        phoneNumberlable.setText(phoneNumber);

        updateMobileViewModel = ViewModelProviders.of(this).get(UpdateMobileViewModel.class);
        confirmMobileViewModel = ViewModelProviders.of(this).get(ConfirmMobileViewModel.class);

        onConfirmationDone();
        countDownTimer();
        sendMobileUpdateRequest();
        onUpdateRequestDone();

        optcode.setPinViewEventListener((pinview, b) -> {
            if (optcode.getValue().length() == 6) {
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
            sendMobileUpdateRequest();
        });
    }

    private void onUpdateRequestDone() {
        updateMobileViewModel.TokenObservable().observe(this, response -> {
            if (response != null) {
                dismissWaitDialog();
            }
            else {
                dismissWaitDialog();
                finish();
            }
        });
    }

    private void sendMobileUpdateRequest() {
        updateMobileViewModel.UpdateMobile(new ChangeMobileInputModel(phoneNumber, originalMobile, ClintTypeConstaent.CUSTOMER));
    }

    private void onConfirmationDone() {
        confirmMobileViewModel.TokenObservable().observe(this, tokenResponse -> {
            if (tokenResponse != null) {
                dismissWaitDialog();
                Intent intent = new Intent();
                intent.putExtra("mobile", phoneNumber);
                intent.putExtra("originalMobile", originalMobile);
                setResult(RESULT_OK, intent);
                finish();
            }
            else {
                dismissWaitDialog();
            }
        });
    }

    void onVerifyClick() {
        if (!TextUtils.isEmpty(optcode.getValue())) {
            showWaitDialog();
            confirmMobileViewModel.MobileConfirmation(new MobileConfirmInputModel(phoneNumber, originalMobile, ClintTypeConstaent.CUSTOMER, optcode.getValue()));
        } else {
            Toast.makeText(this, R.string.please_enter_otp, Toast.LENGTH_LONG).show();
        }
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

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.update_your_mobile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(5f);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
