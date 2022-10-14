package maksab.sd.customer.basecode.fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.events.BottomSheetEvents;
import maksab.sd.customer.models.address.AddressModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.general.activities.AddNewAddressActivity;
import maksab.sd.customer.ui.general.adapters.AddressesAdapter;
import maksab.sd.customer.util.general.AddressInMemoryStorage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectAddressSheet extends BottomSheetDialogFragment {

    RecyclerView itemsRecyclerView;
    ProgressBar progressBar;
    ViewGroup noDataLayout;

    private BottomSheetEvents bottomSheetEvents;

    private boolean isOpenedForSelectAddress = true;
    private AddressesAdapter addressesAdapter;
    private List<AddressModel> customerAddressModels;
    private static final int SELECT_ADDRESS_RESULT = 135;

   public static SelectAddressSheet newInstance(BottomSheetEvents bottomSheetEvents){
        SelectAddressSheet selectAddressSheet = new SelectAddressSheet();
        selectAddressSheet.setOnCloseEvent(bottomSheetEvents);
        return selectAddressSheet;
    }

    @NonNull
    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return  dialog;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = View.inflate(getContext(), R.layout.select_address_sheet,  null);
        initView(view);
        dialog.setContentView(view);
    }

    private void initView(View view){
        itemsRecyclerView = view.findViewById(R.id.items_recyclerview);
        progressBar = view.findViewById(R.id.main_progress);
        noDataLayout = view.findViewById(R.id.no_data_layout);
       Button add_new_address = view.findViewById(R.id.add_new_address);
       add_new_address.setOnClickListener(view1 -> {
           Intent intent = new Intent(getContext() , AddNewAddressActivity.class);
           startActivityForResult(intent,SELECT_ADDRESS_RESULT);
       });
        setupRecyclerView();
        fetchAddresses();
    }

    void setOnCloseEvent(BottomSheetEvents bottomSheetEvents){
        this.bottomSheetEvents = bottomSheetEvents;
    }

    private void setupRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        customerAddressModels = new ArrayList<>();
        addressesAdapter = new AddressesAdapter(customerAddressModels , view -> {
            AddressModel customerAddressModel  = customerAddressModels.get(itemsRecyclerView.getChildAdapterPosition(view));
            if(isOpenedForSelectAddress){
                AddressInMemoryStorage.body =
                        customerAddressModel.getAddressType().getArabicName() + " - " +
                        customerAddressModel.getDistrict().getArabicName()
                        + " - "+ customerAddressModel.getFloorType().getArabicName();
                AddressInMemoryStorage.district = customerAddressModel.getDistrict().getArabicName();
                AddressInMemoryStorage.id =  customerAddressModel.getId();
                this.dismiss();
            }
        } , position -> {
            new AlertDialog.Builder(getContext()).setMessage(getString(R.string.remove_address_confirmation)).setCancelable(false).setPositiveButton(R.string.yes , (dialogInterface, i) -> {
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

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if(this.bottomSheetEvents!=null){
            this.bottomSheetEvents.onAddService();
        }

    }

    private void fetchAddresses(){
        progressBar.setVisibility(View.VISIBLE);
        ILocalStorage localStorage = new SharedPreferencesStorage(getContext());
        String authToken = "bearer " + localStorage.getJwtToken().getStringToken();
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , authToken);

        customersService.getAddress().enqueue(new Callback<List<AddressModel>>() {
            @Override
            public void onResponse(Call<List<AddressModel>> call, Response<List<AddressModel>> response) {
                if(response.isSuccessful()){
                    if(response.body().size() == 0){
                        noDataLayout.setVisibility(View.VISIBLE);
                    }
                    else{
                        noDataLayout.setVisibility(View.GONE);
                        customerAddressModels.clear();
                        customerAddressModels.addAll(response.body());
                        addressesAdapter.notifyDataSetChanged();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<AddressModel>> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        addressesAdapter.notifyDataSetChanged();
    }

    private void deleteAddress(int addressId){
        ILocalStorage localStorage = new SharedPreferencesStorage(getContext());
        String authToken = "bearer " + localStorage.getJwtToken().getStringToken();
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , authToken);

        customersService.deleteAddress(addressId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    fetchAddresses();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_ADDRESS_RESULT && resultCode == RESULT_OK){
            fetchAddresses();
        }
    }
}
