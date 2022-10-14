package maksab.sd.customer.wizards.registeration;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.fragments.FragmentsContract;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.main.activties.MainActivity;
import maksab.sd.customer.ui.profile.fragments.ProfileFragment;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.wizards.registeration.fragments.FinalGreetingFragment;
import maksab.sd.customer.wizards.registeration.fragments.SelectSourceFragment;
import maksab.sd.customer.wizards.registeration.fragments.WelcomeFragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends BaseActivity {
   @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.next_button)
    Button next_button;

    private int index = 0;
    private FragmentsContract currentFragment;
    private List<FragmentsContract> mFragmentsContracts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        setupToolbar();
        initViews();
    }

    private void initViews(){
        int onBoardingStatusId = getIntent().getIntExtra("OnBoardingStatusId",
                Enums.OnBoardingStatusEnum.NewAccount.ordinal());

        index = 0;
        mFragmentsContracts.add(WelcomeFragment.newInstance(onBoardingStatusId));
        mFragmentsContracts.add(ProfileFragment.newInstance(false));
        mFragmentsContracts.add(SelectSourceFragment.newInstance());
        mFragmentsContracts.add(FinalGreetingFragment.newInstance(onBoardingStatusId));

        showFragment(index, true);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNextClick();
            }
        });
    }

    private void setOnBoardingDone() {
        ILocalStorage localStorage = new SharedPreferencesStorage(this);
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , tokenMapping(localStorage.getJwtToken().getStringToken()));
        customersService.updateOnboardingStatus(Enums.OnBoardingStatusEnum.Complete.ordinal())
                .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    RegistrationActivity.this.finish();
                    Intent intent = new Intent(RegistrationActivity.this , MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegistrationActivity.this , getString(R.string.retry_again), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private String tokenMapping(String token) {
        return "bearer " + token;
    }

    private void showFragment(int index, boolean forward) {
        FragmentsContract fragment = mFragmentsContracts.get(index);
        currentFragment = fragment;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Configuration config = getResources().getConfiguration();

        boolean isArabic = true;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            isArabic = config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
        }

        if (isArabic) {
            if (forward)
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
            else
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        }
        else {
            if (forward)
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            else
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        }

        transaction.replace(R.id.fragment_container, (Fragment) fragment);
        transaction.commit();

        getSupportActionBar().setTitle(fragment.getStepTitle(this));
        updateButtonsLables(index);
    }

    private void updateButtonsLables(int index) {
        if (index == mFragmentsContracts.size() - 1) {
            next_button.setText(getString(R.string.start_now));
            next_button.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
        }
        else {
            next_button.setText(getString(R.string.next));
            next_button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
    }

    public void onNextClick() {
        if (index < mFragmentsContracts.size() - 1) {
            if (isValidStep()) {
                currentFragment.saveChange();
                index++;
                showFragment(index, true);
            }
            else {
                Toast.makeText(this, R.string.enter_all_data_validation, Toast.LENGTH_LONG).show();
            }
        }
        else if (index == mFragmentsContracts.size() - 1) {
            if (isValidStep()) {
                currentFragment.saveChange();
                setOnBoardingDone();
            }
            else {
                Toast.makeText(this, R.string.enter_all_data_validation, Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isValidStep() {
        if (currentFragment != null && !currentFragment.isValidForm()) {
            return false;
        }

        return true;
    }

    private void onPrevClick() {
        if (index >= 1) {
            index--;
            showFragment(index, false);
        }
        else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (index >= 1) {
            onPrevClick();
        }
        else {
            finish();
        }
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.welcome_to_app);

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
