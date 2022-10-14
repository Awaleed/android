package maksab.sd.customer.wizards.neworder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.fragments.FragmentsContract;
import maksab.sd.customer.models.orders.details.OrderInputModel;
import maksab.sd.customer.models.providers.OrderModel;
import maksab.sd.customer.models.speciality.SpecialityModel;
import maksab.sd.customer.network.appnetwork.ICatalogService;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.OrderInMemoryStorage;
import maksab.sd.customer.storage.OrderSummaryInMemoryStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.orders.activities.OrderConfirmationActivity;
import maksab.sd.customer.ui.orders.fragments.ServicesListFragment;
import maksab.sd.customer.ui.providers.activties.ProviderDetailsActivity;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.StringUtils;
import maksab.sd.customer.wizards.neworder.adapters.OrderWizardPagerAdapter;
import maksab.sd.customer.wizards.neworder.fragments.OrderInputFragment;
import maksab.sd.customer.wizards.neworder.fragments.OrderSummaryFragment;
import maksab.sd.customer.wizards.neworder.fragments.SpecialityQuestionsFragment;
import maksab.sd.customer.wizards.neworder.fragments.TermsFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewOrderWizardActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.next_button)
    Button next_button;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private int index = 0;
    private FragmentsContract currentFragment;
    private List<FragmentsContract> fragmentsContractList = new ArrayList<>();

    private int specialityId =0;
    private String providerId;
    private boolean isFromOffer = false;
    private int offerId;
    private int transportation_price;
    private SpecialityModel specialityModel;
    private boolean disableSelectServices = false;
    private boolean isFromProfile = false;
    private int orderTypeId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_wizard);
        ButterKnife.bind(this);

        fillDataFromIntent();

        if(!isFromProfile){
            OrderInMemoryStorage.clear();
            OrderSummaryInMemoryStorage.clear();
        }

        getSpeciality();
    }

    private void fillDataFromIntent() {
        specialityId = getIntent().getIntExtra("speciality.id" , 0);
        providerId = getIntent().getStringExtra("provider_id");
        isFromOffer = getIntent().getBooleanExtra("isfrom_offer", false);
        transportation_price = getIntent().getIntExtra("speciality.transportation_price", 0);
        offerId = getIntent().getIntExtra("offer.id" ,0);
        disableSelectServices = getIntent().getBooleanExtra("disableSelectServices",false);
        isFromProfile = getIntent().getBooleanExtra("opened_from_profile" , false);
        orderTypeId = getIntent().getIntExtra("order_type_id" , 0);
    }

    private void setupToolbar(){
        toolbar.setTitle(R.string.welcome_to_maksab);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private FragmentsContract getFragmentByIndex(int index){
        return fragmentsContractList.get(index);
    }

    private void PrepareFragmentsBasedOnConfigurations(){

        fragmentsContractList.add(TermsFragment.newInstance(specialityModel.getHtmlTerms()));

        if(specialityModel.getHaveCustomerQuestionsForm()){
            fragmentsContractList.add(SpecialityQuestionsFragment.newInstance(specialityId));
        }

        if((specialityModel.getSpecialtySelectionTypeId() ==
                    Enums.SpecialtyUISelectionEnum.MaksabServicesWizard.ordinal()) &&
                !isFromOffer && !disableSelectServices){
            fragmentsContractList.add(ServicesListFragment.newInstance(specialityId,
                    specialityModel.getSpecialtyType()));
        }

        fragmentsContractList.add(OrderInputFragment.
                newInstance(providerId,
                specialityModel.getSpecialtySelectionTypeId(),specialityId,
                isFromOffer,offerId));

        fragmentsContractList.add(OrderSummaryFragment.newInstance(transportation_price));
    }

    private void initButtons(){
       next_button.setOnClickListener(view -> onNextClicked());
    }

    private void onNextClicked(){
        if(index == fragmentsContractList.size() - 1){
            if(currentFragment.isValidForm()){
                currentFragment.saveChange();
                sendOrder();
                return;
            }else{
                Toast.makeText(this,  getString(R.string.fill_data_before_next_step) , Toast.LENGTH_LONG).show();
            }
        }

        if(index < fragmentsContractList.size() -1){
            if(currentFragment.isValidForm()){
                currentFragment.saveChange();
                index ++;
                showStepFragment(index);
            }else{
                Toast.makeText(this,  getString(R.string.fill_data_before_next_step), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void onPrevClicked(){
        if(index > 0){
            index --;
            showStepFragment(index);
        }else{
            finish();
        }
    }

    private void showStepFragment(int index){
        FragmentsContract fragment = getFragmentByIndex(index);
        currentFragment = fragment;
        viewPager.setCurrentItem(index);
        toolbar.setTitle(fragment.getStepTitle(this));

        if (index == fragmentsContractList.size() - 1) {
            next_button.setText(getString(R.string.create_order));
            next_button.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
        }
        else {
            next_button.setText(getString(R.string.next));
            next_button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
    }

    private void sendOrder(){
        showWaitDialog();
        ILocalStorage localStorage = new SharedPreferencesStorage(this);
        OrderInputModel orderInputModel = OrderInMemoryStorage.getOrderInputs();
        orderInputModel.setOrderDetails(OrderInMemoryStorage.getOrderItems());
        if(isFromProfile){
            orderInputModel.setProviderUserId(providerId);
            orderInputModel.setOrderTypeId(orderTypeId);
        }

        if(isFromOffer){
            if(!StringUtils.isEmpty(providerId)){
                orderInputModel.setOrderTypeId(Enums.OrderTypeEnum.ProviderOffer.ordinal());
                orderInputModel.setProviderUserId(providerId);
            }
            else{
                orderInputModel.setOrderTypeId(Enums.OrderTypeEnum.MaksabOffer.ordinal());
            }
        }
        orderInputModel.setConsumeBalance(OrderInMemoryStorage.is_balance_used);
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , "bearer " + localStorage.getJwtToken().getStringToken());
        customersService.addNewOrder(orderInputModel)
                .enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                dismissWaitDialog();

                if(response.isSuccessful()){
                    OrderModel order = response.body();

                    OrderInMemoryStorage.setCreatedOrderId(order.getId());
                    OrderSummaryInMemoryStorage.clear();
                    OrderInMemoryStorage.clear();

                    if(isFromOffer)
                        OrderSummaryInMemoryStorage.isOfferStorageActive = false;

                    Intent intent = new Intent(NewOrderWizardActivity.this, OrderConfirmationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("orderTypeId" , order.getOrderTypeId());
                    startActivity(intent);
                    finish();
                }
                else{
                    try {
                        Toast.makeText(NewOrderWizardActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
               dismissWaitDialog();
            }
        });
    }

    private void getSpeciality(){
        showWaitDialog();
        ILocalStorage localStorage = new SharedPreferencesStorage(this);
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        ICatalogService catalogService = GetWayServiceGenerator.createService(ICatalogService.class , token);
        catalogService.getSpecialityById(specialityId)
                .enqueue(new Callback<SpecialityModel>() {
            @Override
            public void onResponse(Call<SpecialityModel> call, Response<SpecialityModel> response) {
                dismissWaitDialog();
                if(response.isSuccessful()){
                    specialityModel = response.body();
                    PrepareFragmentsBasedOnConfigurations();
                    setupToolbar();
                    initButtons();
                    viewPager.setAdapter(new OrderWizardPagerAdapter(getSupportFragmentManager(), fragmentsContractList));
                    viewPager.setOnTouchListener((v, event) -> true);
                    viewPager.setOffscreenPageLimit(fragmentsContractList.size() - 3);
                    OrderSummaryInMemoryStorage.addGuaranteeAmount(specialityModel.getGuaranteePrice());
                    showStepFragment(0);
                }
            }

            @Override
            public void onFailure(Call<SpecialityModel> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            showStepFragment(index);
        }
    }

    @Override
    public void onBackPressed() {
        onPrevClicked();
    }

}