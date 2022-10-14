package maksab.sd.customer.ui.providers.activties;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.adapters.BaseFragmentAdapter;
import maksab.sd.customer.models.orders.details.OrderDetails;
import maksab.sd.customer.models.providers.ProviderDetailsModel;
import maksab.sd.customer.models.providers.ProviderSpecialtyModel;
import maksab.sd.customer.models.providers.SetFavoriteModel;
import maksab.sd.customer.storage.OrderInMemoryStorage;
import maksab.sd.customer.storage.OrderSummaryInMemoryStorage;
import maksab.sd.customer.ui.main.fragments.HomeOffersFragment;
import maksab.sd.customer.ui.media.viewer.MediaActivityOpener;
import maksab.sd.customer.ui.orders.fragments.ServicesListFragment;
import maksab.sd.customer.ui.providers.fragments.BasicInfoFragment;
import maksab.sd.customer.ui.providers.fragments.CustomersReviewsFragment;
import maksab.sd.customer.ui.providers.fragments.GalleryItemsListFragment;
import maksab.sd.customer.ui.providers.fragments.SpecialitiesFragment;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.ShareTextUtil;
import maksab.sd.customer.viewmodels.providers.ProviderDetailsViewModel;
import maksab.sd.customer.wizards.neworder.NewOrderWizardActivity;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class ProviderDetailsActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.profileName)
    TextView profileName;
    @BindView(R.id.profilerate)
    MaterialRatingBar profilerate;
    @BindView(R.id.provider_profile_image)
    ShapeableImageView profile_image;
    @BindView(R.id.favorite_btn)
    ShapeableImageView favoritebtn;
    @BindView(R.id.simpleViewPager)
    ViewPager viewPager;
    @BindView(R.id.simpleTabLayout)
    TabLayout tabLayout;
    TextView shopping_card;
    int selected_speciality;

    private ProviderDetailsModel model;
    private ProviderDetailsViewModel providerDetailsViewModel;
    private String providerId;
    private boolean isfavorite = false;
    private boolean isFromQuotation=false;
    private static final int WizardOrderRequest = 251;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_details);
        ButterKnife.bind(this);
        isFromQuotation = getIntent().getBooleanExtra("isFromQuotation" , false);
        setUpToolBar();
        initViews();
        OrderSummaryInMemoryStorage.clear();
        OrderInMemoryStorage.clear();
    }

    private void initViews() {
        providerDetailsViewModel = ViewModelProviders.of(this).get(ProviderDetailsViewModel.class);
        selected_speciality = getIntent().getIntExtra("select_speciality",0);
        getFavoriteObserver();
        providerDetailsObserver();
        setToFavoriteObserveViable();
        getProviderFromIntent();
    }

    private void setDataToScreen(ProviderDetailsModel fullProfile){
        profileName.setText(fullProfile.getFullName());
        profilerate.setRating(fullProfile.getRate());

        Picasso.with(this).load(fullProfile.getProfileImage())
                .placeholder(R.drawable.placeholder)
                .into(profile_image);

        profile_image.setOnClickListener(view -> {
            MediaActivityOpener.openViewActivity(this, fullProfile.getProfileImage());
        });

        providerId = model.getUserId();
        initTabLayout(model);
    }

    private void initTabLayout(ProviderDetailsModel model){

        // Dynamic Services Fragments
        List<ProviderSpecialtyModel> quotationSpecialties = new ArrayList<>();
        List<ProviderSpecialtyModel> specialtyModelList = model.getSpecialities();

       if(!isFromQuotation){
           if(selected_speciality > 0) showingSelectedSpecialtyAsFirstTab(specialtyModelList);
           for(ProviderSpecialtyModel item: specialtyModelList) {
               if (item.getSpecialtyExecuationModel() ==
                       Enums.SpecialtyExecuationModelEnum.MaksabServices.ordinal()) {
                   Fragment serviceFragment = ServicesListFragment.newInstance(item.getSpecialtyId(),
                           item.getSpecialtyType());
                   addFragment(serviceFragment, item.getSpecialityName());
               }
               else if (item.getSpecialtyExecuationModel() ==
                       Enums.SpecialtyExecuationModelEnum.ProviderServices.ordinal()) {
                   Fragment serviceFragment = ServicesListFragment.newInstance(item.getSpecialtyId(),
                           item.getSpecialtyType(), item.getUserId());
                   addFragment(serviceFragment, item.getSpecialityName());
               }
               else if (item.getSpecialtyExecuationModel() == Enums.SpecialtyExecuationModelEnum.Quotation.ordinal()) {
                   if(item.getSpecialtySelectionTypeId() == Enums.SpecialtyUISelectionEnum.QuotationWizard.ordinal() ||
                           item.getSpecialtySelectionTypeId() == Enums.SpecialtyUISelectionEnum.ProvidersList.ordinal()){
                       quotationSpecialties.add(item);
                   }else if(item.getSpecialtySelectionTypeId() == Enums.SpecialtyUISelectionEnum.MaksabServicesWizard.ordinal()){
                       Fragment serviceFragment = ServicesListFragment.newInstance(item.getSpecialtyId(),
                               item.getSpecialtyType());
                       addFragment(serviceFragment, item.getSpecialityName());
                   }

               }
           }

           // Speciality Quotation Fragment
           if (!quotationSpecialties.isEmpty()) {
               ProviderSpecialtyModel[] specialtyModels = new ProviderSpecialtyModel[quotationSpecialties.size()];
               quotationSpecialties.toArray(specialtyModels);
               addFragment(SpecialitiesFragment.newInstance(specialtyModels, providerId), getString(R.string.provider_services));
           }
       }

        // Other Fragments
        addFragment(GalleryItemsListFragment.newInstance(model.getUserId()), getString(R.string.provider_gallery));
        addFragment(HomeOffersFragment.newInstance(providerId), getString(R.string.provider_specail_offers));
        addFragment(CustomersReviewsFragment.newInstance(providerId), getString(R.string.customers_reviews));

        // Info
        addFragment(BasicInfoFragment.newInstance(model), getString(R.string.about_provider));

        arabicConfiguration();

        BaseFragmentAdapter baseFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager() , fragments , titles);
        viewPager.setAdapter(baseFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        viewPager.setOffscreenPageLimit(fragments.size() + 1);
        viewPager.setCurrentItem(fragments.size() - 1);
    }

    private void arabicConfiguration() {
        Configuration config = getResources().getConfiguration();
        boolean isArabic = config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
        if (isArabic) {
            Collections.reverse(fragments);
            Collections.reverse(titles);
        }

        if (isArabic) {
            viewPager.setCurrentItem(fragments.size() - 1);
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    private void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }

    public void setToFavoriteObserveViable(){
        providerDetailsViewModel.setFavoriteObservable().observe(this , isfavourite -> {

        });
    }

    public void getFavoriteObserver(){
        providerDetailsViewModel.getCheckFavoriteModelLiveData().observe(this , data -> {
            if (data !=null){
                changeFavoriteColor(data.getIsFavorite());
                //viewCount
            }
        });
    }

    @OnClick(R.id.favorite_btn)
    void onFavoriteClick(){
       if(isfavorite){
           providerDetailsViewModel.setToFavorite(new SetFavoriteModel("" , providerId , model.getId(), false));
           changeFavoriteColor(false);
           isfavorite = false;
           //model.setIsFavorite(false);
       }else {
           providerDetailsViewModel.setToFavorite(new SetFavoriteModel("" , providerId , model.getId(), true));
           changeFavoriteColor(true);
           isfavorite = true;
           //model.setIsFavorite(true);
       }
    }

    @OnClick(R.id.share_btn)
    void onshoreClicked(){
        String shareBody = getString(R.string.order_maksab_service_from_url) + "\n" + model.getCustomerProfileDeepLink();
        String captain = getString(R.string.share_provider_profile);
        new ShareTextUtil().shareText(this , captain , shareBody);
    }

    private void changeFavoriteColor(boolean favorite){
        if(favorite){
            favoritebtn.setImageDrawable(VectorDrawableCompat.create( getResources() , R.drawable.ic_greenfavorite , null));
        }else {
            favoritebtn.setImageDrawable( VectorDrawableCompat.create( getResources() , R.drawable.ic_heart, null));
        }
    }

    private void providerDetailsObserver(){
        providerDetailsViewModel.providerDetails().observe(this , providerDetailsModel -> {
            dismissWaitDialog();
            if (providerDetailsModel !=null){
                model = providerDetailsModel;
                providerDetailsViewModel.getFavorite(providerDetailsModel.getUserId());
                setDataToScreen(providerDetailsModel);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.info_menu, menu);

        MenuItem basket = menu.findItem(R.id.basket);
        if(isFromQuotation) basket.setVisible(false);
        View badgeLayout = basket.getActionView();
        shopping_card = (TextView) badgeLayout.findViewById(R.id.actionbar_notifcation_textview);
        setShoppingCartItemCount(0);

        badgeLayout.setOnClickListener(view -> {
            if(!OrderInMemoryStorage.getOrderItems().isEmpty()){
                OrderDetails orderDetails = OrderInMemoryStorage.getOrderItems().get(0);

                int specialityId = orderDetails.getSpecialityId();
                int serviceFor = orderDetails.getServiceFor();

                int orderType = serviceFor == Enums.ServiceForEnum.Maksab.ordinal() ?
                        Enums.OrderTypeEnum.MaksabPricedService.ordinal() :
                        Enums.OrderTypeEnum.ProviderPricedService.ordinal();

                if (OrderInMemoryStorage.getOrderItemsTotal() == 0)
                    orderType = Enums.OrderTypeEnum.Quotation.ordinal();

                Intent intent = new Intent(ProviderDetailsActivity.this, NewOrderWizardActivity.class);
                intent.putExtra("speciality.id" , specialityId);
                intent.putExtra("provider_id" , providerId);
                intent.putExtra("disableSelectServices" , true);
                intent.putExtra("opened_from_profile", true);
                intent.putExtra("order_type_id", orderType);
                startActivityForResult(intent , WizardOrderRequest);
            }
            else{
                Toast.makeText(ProviderDetailsActivity.this, R.string.select_item_first_error, Toast.LENGTH_SHORT).show();
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.basket){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateCart(){
        setShoppingCartItemCount(OrderInMemoryStorage.getOrderItems().size());
    }

    private void setShoppingCartItemCount(int count) {
        if (shopping_card != null) {
            if (count > 0) {
                shopping_card.setText(String.valueOf(count));
                shopping_card.setBackground(ContextCompat.getDrawable(ProviderDetailsActivity.this, R.drawable.circle_light));
            }
            else {
                shopping_card.setText("");
                shopping_card.setBackground(null);
            }
        }
    }

    private void getProviderFromIntent() {
        model = getIntent().getExtras().getParcelable("provider.model");
        if (model!=null){
            providerDetailsViewModel.getFavorite(model.getUserId());
            setDataToScreen(model);
        }
        else {
            showWaitDialog();
            providerDetailsViewModel.getProviderById(getIntent().getStringExtra("userId"));
        }
    }

    private void setUpToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.provider_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void showingSelectedSpecialtyAsFirstTab(List<ProviderSpecialtyModel> providerSpecialtyModels){
        int specialityCurrentIndex = 0;
        ProviderSpecialtyModel providerSpecialtyModel = null;
        for (int i = 0; i < providerSpecialtyModels.size(); i++) {
            if(providerSpecialtyModels.get(i).getSpecialtyId() == selected_speciality){
                specialityCurrentIndex = i;
                providerSpecialtyModel = providerSpecialtyModels.get(i);
            }
        }
        providerSpecialtyModels.remove(specialityCurrentIndex);
        providerSpecialtyModels.add(0,providerSpecialtyModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == WizardOrderRequest){
            updateCart();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OrderSummaryInMemoryStorage.clear();
        OrderInMemoryStorage.clear();
    }
}
