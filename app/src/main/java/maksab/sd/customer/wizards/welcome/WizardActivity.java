package maksab.sd.customer.wizards.welcome;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.main.SliderViewModel;
import maksab.sd.customer.network.appnetwork.ILookUpsService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.ui.profile.activities.LoginActivity;
import maksab.sd.customer.util.constants.UserTypeEnum;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WizardActivity extends BaseActivity {
    @BindView(R.id.wizard_view_pager)
    ViewPager wizard_view_pager;
    @BindView(R.id.start_button)
    Button start_button;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;

    private WizardAdapter sliderAdapter;
    private int currentPageIndex;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);
        ButterKnife.bind(this);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void init() {
        ILookUpsService lookUpsService = GetWayServiceGenerator.createService(ILookUpsService.class);
        pageIndicatorView.setVisibility(View.INVISIBLE);

        lookUpsService.getWizardSliders(UserTypeEnum.Customer.ordinal()).enqueue(new Callback<List<SliderViewModel>>() {
            @Override
            public void onResponse(Call<List<SliderViewModel>> call, Response<List<SliderViewModel>> response) {
                if (response.isSuccessful()) {
                    final List<WizardFragment> fragments = new ArrayList<>();
                    for (SliderViewModel sliderViewModel : response.body()) {
                        fragments.add(WizardFragment.newInstance(
                                new WizardModel(sliderViewModel.getImagePath(),
                                        sliderViewModel.getArabicName(),
                                        sliderViewModel.getArabicDescription())));
                    }

                    showWizardItems(fragments);

                }
            }

            @Override
            public void onFailure(Call<List<SliderViewModel>> call, Throwable t) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showWizardItems(List<WizardFragment> fragments) {
        pageIndicatorView.setVisibility(View.VISIBLE);

        Configuration config = getResources().getConfiguration();
        final boolean isArabic = config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;

        if (isArabic) {
            Collections.reverse(fragments);
        }

        currentPageIndex = 0;
        if (isArabic) {
            currentPageIndex = fragments.size() - 1;
        }

        sliderAdapter = new WizardAdapter(getSupportFragmentManager(), this, fragments);
        wizard_view_pager.setAdapter(sliderAdapter);
        wizard_view_pager.setCurrentItem(currentPageIndex);

        start_button.setOnClickListener(view -> {
            boolean isReachedEnd = true;
//                    isArabic ?
//                            currentPageIndex == 0 :
//                            currentPageIndex == fragments.size() - 1;

            if (isReachedEnd) {
                Intent intent = new Intent(WizardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                int nexItem = isArabic ? currentPageIndex - 1 : currentPageIndex + 1;
                start_button.setText(R.string.next);
                wizard_view_pager.setCurrentItem(nexItem);
            }
        });

        wizard_view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/*empty*/}

            @Override
            public void onPageSelected(int position) {
                currentPageIndex = position;
                pageIndicatorView.setSelection(position);

                boolean isReachedEnd =
                        isArabic ?
                                currentPageIndex == 0 :
                                currentPageIndex == fragments.size() - 1;

                if (isReachedEnd) {
                    start_button.setText(R.string.start_now);
                } else {
                    start_button.setText(R.string.next);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {/*empty*/}
        });
    }

    class WizardAdapter extends FragmentPagerAdapter {
        private List<WizardFragment> fragments;
        private Context context;

        public WizardAdapter(FragmentManager fm, Context context,
                             List<WizardFragment> fragments) {
            super(fm);
            this.context = context;
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
