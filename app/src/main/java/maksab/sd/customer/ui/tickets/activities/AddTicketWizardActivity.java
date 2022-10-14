package maksab.sd.customer.ui.tickets.activities;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.fragments.FragmentsContract;
import maksab.sd.customer.models.tickets.TicketInputModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.tickets.fragments.AddTicketCategoryFragment;
import maksab.sd.customer.ui.tickets.fragments.AddTicketFinalFragment;
import maksab.sd.customer.ui.tickets.fragments.AddTicketSubCategoryFragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTicketWizardActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private int index = 0;
    private ICustomersService providerService;
    private ILocalStorage localStorage;
    private FragmentsContract currentFragment;
    private List<FragmentsContract> mFragmentsContracts = new ArrayList<>();
    private TicketInputModel mTicketInputModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket_wizard);
        ButterKnife.bind(this);
        setupToolbar();

        localStorage = new SharedPreferencesStorage(this);
        String authToken = "bearer " + localStorage.getJwtToken().getStringToken();
        providerService = GetWayServiceGenerator.createService(ICustomersService.class, authToken);

        mTicketInputModel = new TicketInputModel();

        index = 0;
        mFragmentsContracts.add(AddTicketCategoryFragment.newInstance(mTicketInputModel));
        mFragmentsContracts.add(AddTicketSubCategoryFragment.newInstance(mTicketInputModel));
        mFragmentsContracts.add(AddTicketFinalFragment.newInstance(mTicketInputModel));

        showFragment(index, true);
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
                showConfirmationDialog();
            }
            else {
                Toast.makeText(this, R.string.fill_all_data_error, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.are_you_sure))
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addTicket(mTicketInputModel);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void addTicket(TicketInputModel ticketInputModel) {
        showWaitDialog();
        providerService.addTicket(ticketInputModel).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddTicketWizardActivity.this, getString(R.string.ticket_added), Toast.LENGTH_LONG).show();
                    dismissWaitDialog();
                    finish();
                }
                else {
                    dismissWaitDialog();
                    try {
                        Toast.makeText(getApplication(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dismissWaitDialog();
                Toast.makeText(AddTicketWizardActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.add_new_ticket);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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