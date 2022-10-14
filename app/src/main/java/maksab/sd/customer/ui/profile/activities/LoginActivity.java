package maksab.sd.customer.ui.profile.activities;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.profile.LoginModel;
import maksab.sd.customer.util.constants.ClintTypeConstaent;
import maksab.sd.customer.util.general.NumberValidator;
import maksab.sd.customer.viewmodels.profile.LoginViewModel;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.phonenumber)
    EditText phoneNumber;

    private LoginViewModel loginViewModel;
    private String phonenumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ButterKnife.bind(this);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        getLoginDataObservable();
        String moblienumber = getIntent().getStringExtra("mobile");
        if (moblienumber != null) {
            phoneNumber.setText(moblienumber);
        }
    }

    @OnClick(R.id.signIn)
    void signInClick() {
        phonenumber = phoneNumber.getText().toString();
        if (!TextUtils.isEmpty(phonenumber) && NumberValidator.IsVaildNumber(phonenumber)) {
/*
            if(ContextCompat.checkSelfPermission(this, "android.permission.READ_SMS") != PackageManager.PERMISSION_GRANTED) {
                readSmsPerm();
            }else {
                *//*showWaitDialog();
                loginViewModel.login(new LoginModel(phonenumber, ClintTypeConstaent.CUSTOMER));*//*
            }*/

            showWaitDialog();
            loginViewModel.login(new LoginModel(phonenumber, ClintTypeConstaent.CUSTOMER));

        } else {
            Toast.makeText(this, R.string.pleaseEnterNumber, Toast.LENGTH_LONG).show();
        }
    }

    private void getLoginDataObservable() {
        loginViewModel.loginObservable().observe(this, s -> {
            if (s != null) {
                openOtpScreen(phonenumber);
            } else {
                dismissWaitDialog();
            }
        });
    }

    private void openOtpScreen(String phoneNumber) {
        dismissWaitDialog();
        Intent intent = new Intent(this, VerifyOtpActivity.class);
        intent.putExtra("mobile", phoneNumber);
        startActivity(intent);
        finish();

    }

    private void readSmsPerm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.give_per);
        builder.setMessage(R.string.smsPerm)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    ActivityCompat.requestPermissions(LoginActivity.this, new String[]{"android.permission.READ_SMS"}, 976);
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        showWaitDialog();
        loginViewModel.login(new LoginModel(phonenumber, ClintTypeConstaent.CUSTOMER));

    }
}
