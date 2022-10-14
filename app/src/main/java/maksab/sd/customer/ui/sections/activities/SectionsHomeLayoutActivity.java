package maksab.sd.customer.ui.sections.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.fragments.MessageDialog;
import maksab.sd.customer.models.categories.CardBasedCategoriesModel;
import maksab.sd.customer.models.categories.CategoryDetailsModel;
import maksab.sd.customer.models.main.SliderViewModel;
import maksab.sd.customer.models.orders.details.HomeSpecialOfferModel;
import maksab.sd.customer.models.speciality.SpecialityModel;
import maksab.sd.customer.network.appnetwork.ICatalogService;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.main.activties.SpecialOffersDetailsActivity;
import maksab.sd.customer.ui.main.fragments.HomeOffersFragment;
import maksab.sd.customer.ui.orders.activities.ServicesActivity;
import maksab.sd.customer.ui.providers.activties.ProviderDetailsActivity;
import maksab.sd.customer.ui.providers.activties.ProviderListActivity;
import maksab.sd.customer.ui.sections.fragments.CardBasedSectionsFragment;
import maksab.sd.customer.ui.sections.fragments.GridBasedSectionsFragments;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.AddressInMemoryStorage;
import maksab.sd.customer.wizards.neworder.NewOrderWizardActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SectionsHomeLayoutActivity extends BaseActivity {
    @BindView(R.id.container)
    ViewGroup container;
    @BindView(R.id.main_progress)
    ProgressBar main_progress;
    @BindView(R.id.offers_container)
    FrameLayout offers_containers;
    @BindView(R.id.offer_layout)
    ViewGroup offer_layout;
    @BindView(R.id.slider)
    SliderLayout sliderLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private boolean isRefreshed = false;
    private  int categoryId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_home_layout);
        ButterKnife.bind(this);

        String categoryName = getIntent().getStringExtra("category.name");
        setupToolBar(categoryName);

        onRefresh();
        getItems();
    }

    private int createFrameLayout() {
        int id = ViewCompat.generateViewId();
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(id);
        ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        frameLayout.setLayoutParams(layoutParams);
        container.addView(frameLayout);
        return id;
    }

    private void getItems() {
        container.setVisibility(View.GONE);
        main_progress.setVisibility(View.VISIBLE);

        ILocalStorage localStorage = new SharedPreferencesStorage(this);
        String authToken = "bearer " + localStorage.getJwtToken().getStringToken();
        ICatalogService customersService = GetWayServiceGenerator.createService(ICatalogService.class, authToken);

         categoryId = getIntent().getIntExtra("category.id", 0);
        int addressId = AddressInMemoryStorage.id;

        customersService.getCategoryDetails(categoryId, addressId)
            .enqueue(new Callback<CategoryDetailsModel>() {
            @Override
            public void onResponse(Call<CategoryDetailsModel> call, Response<CategoryDetailsModel> response) {
                if (response.isSuccessful()) {
                    CategoryDetailsModel categoryDetailsModels = response.body();

                    for (int i = 0; i < categoryDetailsModels.getSubCategoryViewModels().size(); i++) {
                        CategoryDetailsModel.SubCategoryViewModel subCategoryViewModel = categoryDetailsModels.getSubCategoryViewModels().get(i);
                        ArrayList<CardBasedCategoriesModel> homeSpecialities = new ArrayList<>();

                        for (int j = 0; j < subCategoryViewModel.getSpecialtyViewModels().size(); j++) {
                            CategoryDetailsModel.SpecialtyViewModel specialtyViewModel = subCategoryViewModel.getSpecialtyViewModels().get(j);
                            homeSpecialities.add(new CardBasedCategoriesModel(specialtyViewModel.getId(), specialtyViewModel.getArabicName(), specialtyViewModel.getImagePath(),
                                    specialtyViewModel.getHtmlTerms(), specialtyViewModel.getHaveCustomQuestionsForm(), specialtyViewModel.getSpecialtySelectionTypeId(),
                                    specialtyViewModel.isCoverage() , specialtyViewModel.getTransportationPrice() , specialtyViewModel.getSpecialtyType()));
                        }
                        createLayoutBasedOnConfigurations(homeSpecialities, 1, subCategoryViewModel.getArabicName());
                    }

                    showOffers();
                    addSliders(categoryDetailsModels.getSliderViewModels());
                }

                container.setVisibility(View.VISIBLE);
                main_progress.setVisibility(View.GONE);
                offer_layout.setVisibility(View.VISIBLE);

                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<CategoryDetailsModel> call, Throwable t) {
                Toast.makeText(SectionsHomeLayoutActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                container.setVisibility(View.VISIBLE);
                main_progress.setVisibility(View.GONE);
            }
        });
    }

    //offers
    private void showOffers() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.isDestroyed())
            return;

        offer_layout.setVisibility(View.VISIBLE);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(offers_containers.getId(), HomeOffersFragment.newInstance(categoryId));
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    private void createLayoutBasedOnConfigurations(ArrayList<CardBasedCategoriesModel> categoriesModels, int type, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.isDestroyed())
            return;

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (type == 1) {
            transaction.replace(createFrameLayout(), CardBasedSectionsFragment.newInstance(categoriesModels, title));
        } else {
            transaction.replace(createFrameLayout(), GridBasedSectionsFragments.newInstance(1, 1));
        }
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    private void addSliders(List<SliderViewModel> sliderViewModels) {
        if (sliderViewModels.isEmpty()) {
            sliderLayout.setVisibility(View.GONE);
        }
        else {
            sliderLayout.setVisibility(View.VISIBLE);
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);

            for (int i = 0; i < sliderViewModels.size(); i++) {
                SliderViewModel sliderViewModel = sliderViewModels.get(i);
                DefaultSliderView defaultSliderView = new DefaultSliderView(this);
                defaultSliderView.image(sliderViewModel.getImagePath());
                defaultSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        int index = sliderLayout.getCurrentPosition();
                        if (index < sliderViewModels.size()) {
                            SliderViewModel item = sliderViewModels.get(index);
                            if (item != null) {
                                if (item.getSliderTypeEnum() == Enums.SliderTypeEnum.ImageBanner.ordinal()) {
                                    MessageDialog.showMessageDialog(SectionsHomeLayoutActivity.this,
                                            item.getArabicName(), item.getArabicDescription());
                                }
                                else if (item.getSliderTypeEnum() == Enums.SliderTypeEnum.ProviderBanner.ordinal()) {
                                    Intent intent = new Intent(SectionsHomeLayoutActivity.this, ProviderDetailsActivity.class);
                                    intent.putExtra("userId", item.getItemId());
                                    startActivity(intent);
                                }
                                else if (item.getSliderTypeEnum() == Enums.SliderTypeEnum.SpecialtyBanner.ordinal()) {
                                    openSpecialtyById(Integer.parseInt(item.getItemId()));
                                }
                                else if (item.getSliderTypeEnum() == Enums.SliderTypeEnum.OfferBanner.ordinal()) {
                                    openOfferDetailById(Integer.parseInt(item.getItemId()));
                                }
                            }
                        }
                    }
                });

                sliderLayout.addSlider(defaultSliderView);
            }
        }
    }

    private void openSpecialtyById(int specialtyId) {
        ILocalStorage localStorage = new SharedPreferencesStorage(this);
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        ICatalogService catalogService = GetWayServiceGenerator.createService(ICatalogService.class , token);
        catalogService.getSpecialityById(specialtyId)
                .enqueue(new Callback<SpecialityModel>() {
                    @Override
                    public void onResponse(Call<SpecialityModel> call, Response<SpecialityModel> response) {
                        dismissWaitDialog();
                        if(response.isSuccessful()){
                            SpecialityModel specialityModel = response.body();

                            if (specialityModel.getSpecialtySelectionTypeId() == Enums.SpecialtyUISelectionEnum.ProvidersList.ordinal()){
                                Intent intent = new Intent(SectionsHomeLayoutActivity.this , ProviderListActivity.class);
                                intent.putExtra("selected.speciality", specialityModel.getId());
                                intent.putExtra("selected.speciality.title", specialityModel.getArabicName());
                                startActivity(intent);
                            }
                            else if(specialityModel.getSpecialtySelectionTypeId() == Enums.SpecialtyUISelectionEnum.ProviderServicesList.ordinal()){
                                Intent intent = new Intent(SectionsHomeLayoutActivity.this , ServicesActivity.class);
                                intent.putExtra("speciality.id", specialityModel.getId());
                                startActivity(intent);
                            }
                            else if(specialityModel.getSpecialtySelectionTypeId() == Enums.SpecialtyUISelectionEnum.QuotationWizard.ordinal() ||
                                    specialityModel.getSpecialtySelectionTypeId() == Enums.SpecialtyUISelectionEnum.MaksabServicesWizard.ordinal()){
                                Intent intent = new Intent(SectionsHomeLayoutActivity.this , NewOrderWizardActivity.class);
                                intent.putExtra("speciality.id" , specialityModel.getId());
                                intent.putExtra("speciality.transportation_price" , specialityModel.getTransportationPrice());
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SpecialityModel> call, Throwable t) {

                    }
                });
    }

    private void openOfferDetailById(int offerId) {
        SharedPreferencesStorage localStorage = new SharedPreferencesStorage(this);
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class ,
                "bearer " + localStorage.getJwtToken().getStringToken());
        customersService.getOfferById(offerId)
                .enqueue(new Callback<HomeSpecialOfferModel>() {
            @Override
            public void onResponse(Call<HomeSpecialOfferModel> call, Response<HomeSpecialOfferModel> response) {
                if (response.isSuccessful()){
                    HomeSpecialOfferModel offer = response.body();
                    Intent intent = new Intent(SectionsHomeLayoutActivity.this, SpecialOffersDetailsActivity.class);
                    intent.putExtra("model", offer);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<HomeSpecialOfferModel> call, Throwable t) {
                Toast.makeText(getApplication() , getApplication().getResources().getText(R.string.internetError) , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupToolBar(String categoryName) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(categoryName);

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

    private void onRefresh() {
        swipeRefresh.setOnRefreshListener(() -> {
            offer_layout.setVisibility(View.GONE);
            sliderLayout.setVisibility(View.GONE);
            isRefreshed = true;
            container.removeAllViews();
            getItems();
        });
    }
}