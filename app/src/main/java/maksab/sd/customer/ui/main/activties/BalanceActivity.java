package maksab.sd.customer.ui.main.activties;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.adapters.BaseFragmentAdapter;
import maksab.sd.customer.models.profile.BalanceViewModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.balance.BalancePaymentFragment;
import maksab.sd.customer.ui.balance.BalanceTransactionListFragment;
import maksab.sd.customer.ui.tickets.activities.AddTicketWizardActivity;
import maksab.sd.customer.util.general.NumbersUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalanceActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.balance_text_view)
    TextView balance_text_view;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        ButterKnife.bind(this);

        setupToolbar();
        initBalanceViews();
        setupTabs();
    }

    private void setupTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.profile_view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.profile_tabs);

        addFragment(BalancePaymentFragment.newInstance(),
                getString(R.string.balance));

        addFragment(BalanceTransactionListFragment.newInstance(),
                getString(R.string.account_transactions));

        Configuration config = getResources().getConfiguration();
        boolean isArabic = config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;

        if (isArabic) {
            Collections.reverse(fragments);
            Collections.reverse(titles);
        }

        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);

        if (isArabic) {
            viewPager.setCurrentItem(fragments.size() - 1);
        }
        else {
            viewPager.setCurrentItem(0);
        }

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
    }

    private void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.your_balance);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initBalanceViews() {
        showWaitDialog();
        SharedPreferencesStorage localStorage = new SharedPreferencesStorage(this);
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        GetWayServiceGenerator.createService(ICustomersService.class, token)
                .getBalance()
                .enqueue(new Callback<BalanceViewModel>() {
                    @Override
                    public void onResponse(Call<BalanceViewModel> call, Response<BalanceViewModel> response) {
                        dismissWaitDialog();
                        if (response.isSuccessful()) {
                            BalanceViewModel balance = response.body();
                            balance_text_view.setText(NumbersUtil.formatAmount(balance.getBalance()));
                        }
                    }

                    @Override
                    public void onFailure(Call<BalanceViewModel> call, Throwable t) {
                        dismissWaitDialog();
                    }
                });
    }
}