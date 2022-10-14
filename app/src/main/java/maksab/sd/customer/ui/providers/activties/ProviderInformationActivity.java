package maksab.sd.customer.ui.providers.activties;

import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.adapters.BaseFragmentAdapter;
import maksab.sd.customer.models.providers.ProviderDetailsModel;
import maksab.sd.customer.ui.providers.fragments.BasicInfoFragment;
import maksab.sd.customer.ui.providers.fragments.CustomersReviewsFragment;
import maksab.sd.customer.ui.providers.fragments.GalleryItemsListFragment;

public class ProviderInformationActivity extends BaseActivity {

    @BindView(R.id.basic_info_chip)
    Chip basic_info_chip;

    @BindView(R.id.customers_rate_chip)
    Chip customers_rate_chip;

    @BindView(R.id.gallery_chip)
    Chip gallery_chip;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    private List<Fragment> fragments;
    private List<String> fragmentsTitles;
    private ProviderDetailsModel providerDetailsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_inormation);
        ButterKnife.bind(this);
        providerDetailsModel = getIntent().getParcelableExtra("provider.model");
        setupToolbar();
        setupFragments();
        setupViewPager();
        chipsLogic();
    }

    private void setupToolbar(){
        toolbar.setTitle(providerDetailsModel.getFullName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setupViewPager(){
        BaseFragmentAdapter baseFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragments , fragmentsTitles);
        view_pager.setAdapter(baseFragmentAdapter);
        view_pager.setOffscreenPageLimit(2);
    }

    private void setupFragments(){

        fragments = new ArrayList<>();
        fragmentsTitles = new ArrayList<>();
        fragments.add(BasicInfoFragment.newInstance(providerDetailsModel));
        fragments.add(CustomersReviewsFragment.newInstance(providerDetailsModel.getUserId()));
        fragments.add(GalleryItemsListFragment.newInstance(providerDetailsModel.getUserId()));

        fragmentsTitles.add(getString(R.string.basic_information));
        fragmentsTitles.add(getString(R.string.customer_reviews));
        fragmentsTitles.add(getString(R.string.work_gallery));
    }

    private void chipsLogic(){
        coloringChips(basic_info_chip, customers_rate_chip, gallery_chip , R.color.colorPrimary, R.color.white ,R.color.light_gray2,R.color.black,
                R.color.light_gray2,R.color.black);

        basic_info_chip.setOnClickListener(view -> {
            coloringChips(basic_info_chip, customers_rate_chip , gallery_chip , R.color.colorPrimary, R.color.white ,R.color.light_gray2,R.color.black,
                    R.color.light_gray2,R.color.black );
            view_pager.setCurrentItem(0);
        });

        customers_rate_chip.setOnClickListener(view -> {
            coloringChips(basic_info_chip, customers_rate_chip , gallery_chip , R.color.light_gray2, R.color.black ,R.color.colorAccent,R.color.white,
                    R.color.light_gray2, R.color.black);
            view_pager.setCurrentItem(1);

        });

        gallery_chip.setOnClickListener(view -> {
            coloringChips(basic_info_chip, customers_rate_chip , gallery_chip , R.color.light_gray2, R.color.black ,
                    R.color.light_gray2, R.color.black,R.color.yellow,R.color.white);
            view_pager.setCurrentItem(2);

        });
    }

    private void coloringChips(Chip basic_info_chip, Chip customers_rate_chip , Chip gallery_chip,int p1, int p2, int p3 , int p4,int p5 , int p6) {
        basic_info_chip.setChipBackgroundColor(AppCompatResources.getColorStateList(this, p1));
        basic_info_chip.setTextColor(AppCompatResources.getColorStateList(this, p2));


        customers_rate_chip.setChipBackgroundColor(AppCompatResources.getColorStateList(this, p3));
        customers_rate_chip.setTextColor(AppCompatResources.getColorStateList(this, p4));


        gallery_chip.setChipBackgroundColor(AppCompatResources.getColorStateList(this, p5));
        gallery_chip.setTextColor(AppCompatResources.getColorStateList(this, p6));
    }
}