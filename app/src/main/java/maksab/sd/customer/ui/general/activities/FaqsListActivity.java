package maksab.sd.customer.ui.general.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.faq.FaqTypeViewModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.general.fragments.FaqListFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqsListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private static final int CustomerUserType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs_list);
        ButterKnife.bind(this);
        setupToolbar();

        getFaqsTypes(CustomerUserType);
    }

    private void getFaqsTypes(int userType) {
        ILocalStorage localStorage = new SharedPreferencesStorage(this);
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class, "Bearer " + localStorage.getJwtToken().getStringToken());
        customersService.getFaqTypes(userType).enqueue(new Callback<List<FaqTypeViewModel>>() {
            @Override
            public void onResponse(Call<List<FaqTypeViewModel>> call, Response<List<FaqTypeViewModel>> response) {
                if (response.isSuccessful()) {
                    List<FaqTypeViewModel> items = response.body();
                    setupViewPager(items);
                }
            }

            @Override
            public void onFailure(Call<List<FaqTypeViewModel>> call, Throwable t) {
            }
        });
    }

    private void setupViewPager(List<FaqTypeViewModel> items) {
        ViewPager viewPager = findViewById(R.id.profile_view_pager);
        ChipGroup chipGroup = findViewById(R.id.chipGroup);

        int i = 0;
        for (FaqTypeViewModel item : items) {
            addFragment(FaqListFragment.newInstance(item.getId()), item.getArabicName());

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.item_chip_choice, null);

            Chip chip = view.findViewById(R.id.chip_item);
            chip.setText(item.getArabicName());
            chip.setCheckable(true);
            chip.setId(item.getId());
            chip.setTag(i++);
            chip.setChipBackgroundColorResource(R.color.chip_color);
            chip.setTextColor(getResources().getColorStateList(R.color.tag_text_colors));
            chipGroup.addView(chip);
        }

        Configuration config = getResources().getConfiguration();

        FaqFragmentAdapter adapter = new FaqFragmentAdapter(getSupportFragmentManager(),
                fragments, titles);
        viewPager.setAdapter(adapter);
        if (items.size() > 0) {
            viewPager.setCurrentItem(0);
            ((Chip)chipGroup.getChildAt(0)).setChecked(true);
        }

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                Chip chip = chipGroup.findViewById(i);
                if (chip != null) {
                    viewPager.setCurrentItem((Integer) chip.getTag(), true);
                }
            }
        });

        viewPager.setOffscreenPageLimit(items.size());
    }

    private void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.most_frequnt_qouation);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    class FaqFragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        private List<String> titles;

        public FaqFragmentAdapter(FragmentManager fm,
                                  List<Fragment> fragments, List<String> titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}