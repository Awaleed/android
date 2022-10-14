package maksab.sd.customer.ui.general.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.address.AddressModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.general.adapters.AddressesAdapter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressListActivity extends BaseActivity {
    @BindView(R.id.items_recyclerview)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.main_progress)
    ProgressBar progressBar;
    @BindView(R.id.no_data_layout)
    ViewGroup noDataLayout;
    @BindView(R.id.add_floating_button)
    FloatingActionButton add_floating_button;

    private boolean isOpenedForSelectAddress = false;
    private AddressesAdapter addressesAdapter;
    private List<AddressModel> customerAddressModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);
        ButterKnife.bind(this);
        isOpenedForSelectAddress = getIntent().getBooleanExtra("isForSelectAddress" , false);
        setupToolbar();
        setupRecyclerView();
        fetchAddresses();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAddresses();
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       if(isOpenedForSelectAddress){
           getSupportActionBar().setTitle(R.string.please_select_address);
       }else{
           getSupportActionBar().setTitle(R.string.saved_address);
       }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        customerAddressModels = new ArrayList<>();
        addressesAdapter = new AddressesAdapter(customerAddressModels , view -> {
            AddressModel customerAddressModel  = customerAddressModels.get(itemsRecyclerView.getChildAdapterPosition(view));
            if(isOpenedForSelectAddress){
                Intent intent = new Intent();
                intent.putExtra("selected.address.id", customerAddressModel.getId());
                intent.putExtra("selected.address.body" , customerAddressModel.getAddressType().getArabicName() + " - " +
                        customerAddressModel.getDistrict().getArabicName()
                + " - "+ customerAddressModel.getFloorType().getArabicName());

                intent.putExtra("selected.address.district", customerAddressModel.getDistrict().getArabicName());
                setResult(RESULT_OK , intent);
                finish();
            }
        } , position -> {
            new AlertDialog.Builder(this).setMessage(getString(R.string.are_you_sure_to_delete_address)).setCancelable(false).setPositiveButton(R.string.yes , (dialogInterface, i) -> {
                deleteAddress(customerAddressModels.get(position).getId());
                dialogInterface.dismiss();
            })
                    .setNegativeButton(R.string.no, (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    }).show();
        });
        itemsRecyclerView.setLayoutManager(layoutManager);
        itemsRecyclerView.setAdapter(addressesAdapter);
    }

    @OnClick(R.id.add_floating_button)
    void onNewAddressClicked(){
        Intent intent = new Intent(this , AddNewAddressActivity.class);
        startActivityForResult(intent,900);
    }

     private void fetchAddresses(){
        progressBar.setVisibility(View.VISIBLE);
         ILocalStorage localStorage = new SharedPreferencesStorage(this);
         String authToken = "bearer " + localStorage.getJwtToken().getStringToken();
         ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , authToken);
         customersService.getAddress().enqueue(new Callback<List<AddressModel>>() {
             @Override
             public void onResponse(Call<List<AddressModel>> call, Response<List<AddressModel>> response) {
                 if(response.isSuccessful()){
                     if(response.body().size() == 0){
                         swipeRefreshLayout.setVisibility(View.GONE);
                         noDataLayout.setVisibility(View.VISIBLE);
                     }
                     else{
                         noDataLayout.setVisibility(View.GONE);
                         swipeRefreshLayout.setVisibility(View.VISIBLE);
                         customerAddressModels.clear();
                         customerAddressModels.addAll(response.body());
                         addressesAdapter.notifyDataSetChanged();
                     }
                 }
                 swipeRefreshLayout.setRefreshing(false);
                 progressBar.setVisibility(View.GONE);
             }

             @Override
             public void onFailure(Call<List<AddressModel>> call, Throwable t) {
                 Toast.makeText(AddressListActivity.this , t.getMessage() , Toast.LENGTH_LONG).show();
                 progressBar.setVisibility(View.GONE);
                 swipeRefreshLayout.setRefreshing(false);
             }
         });

        addressesAdapter.notifyDataSetChanged();
     }

     private void deleteAddress(int addressId){
         ILocalStorage localStorage = new SharedPreferencesStorage(this);
         String authToken = "bearer " + localStorage.getJwtToken().getStringToken();
         ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , authToken);
         showWaitDialog();
         customersService.deleteAddress(addressId).enqueue(new Callback<ResponseBody>() {
             @Override
             public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                 dismissWaitDialog();
                 if(response.isSuccessful()){
                     fetchAddresses();
                 }
             }

             @Override
             public void onFailure(Call<ResponseBody> call, Throwable t) {
                 dismissWaitDialog();
             }
         });
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 900){
            fetchAddresses();
        }
    }
}