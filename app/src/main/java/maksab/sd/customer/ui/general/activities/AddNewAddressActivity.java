package maksab.sd.customer.ui.general.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.address.AddressInput;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.lookups.activities.SelectLookupActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewAddressActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.address_type_Spinner)
    AutoCompleteTextView address_type_Spinner;
    @BindView(R.id.cityspinner)
    AutoCompleteTextView cityspinner;
    @BindView(R.id.districtspinner)
    AutoCompleteTextView districtspinner;
    @BindView(R.id.floor_number_spinner)
    AutoCompleteTextView floor_number_spinner;
    @BindView(R.id.address_edittext)
    TextInputEditText address_edittext;
    @BindView(R.id.image_view)
    ImageView image_view;

    private static final int CITY = 1;
    private static final int District = 2;
    private static final int FLOORNUMBER = 3;
    private static final int ADDRESSTYPE = 4;
    private static final String LOOKUPTYPEINTENT = "lookup_type";

    private int selectedCityId = 0;
    private int selectedAddressTypeId = 0;
    private int selectedDistrict = 0;
    private int selectedFloorNumber = 0;

    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_address);
        ButterKnife.bind(this);
        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.add_address);

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

    @OnClick(R.id.image_layout)
    void onOpenMapClick(){
        Intent intent = new Intent(this , ChooseLocationActivity.class);
        intent.putExtra("text_address" ,districtspinner.getText().toString());
        startActivityForResult(intent, 100);
    }

    @OnClick(R.id.address_type_Spinner)
    void onAddressTypeClicked(){
        Intent intent = new Intent(this , SelectLookupActivity.class);
        intent.putExtra(LOOKUPTYPEINTENT , ADDRESSTYPE);
        startActivityForResult(intent , ADDRESSTYPE);
    }

    @OnClick(R.id.cityspinner)
    void onCityNameClicked(){
        Intent intent = new Intent(this , SelectLookupActivity.class);
        intent.putExtra(LOOKUPTYPEINTENT , CITY);
        startActivityForResult(intent , CITY);
    }

    @OnClick(R.id.districtspinner)
    void onGadaClicked(){
        if(selectedCityId <=0){
            Toast.makeText(this , R.string.select_city_first, Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(this , SelectLookupActivity.class);
            intent.putExtra(LOOKUPTYPEINTENT , District);
            intent.putExtra("cityId" , selectedCityId);
            startActivityForResult(intent , District);
        }
    }

    @OnClick(R.id.floor_number_spinner)
    void onFloorNumberClicked(){
        Intent intent = new Intent(this , SelectLookupActivity.class);
        intent.putExtra(LOOKUPTYPEINTENT , FLOORNUMBER);
        startActivityForResult(intent , FLOORNUMBER);
    }

    @OnClick(R.id.save_button)
    void onSaveAddressType(){
        boolean emptyMapLocation = latitude == 0 || longitude == 0;
        if (emptyMapLocation) {
            Toast.makeText(this , R.string.select_your_location, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this , ChooseLocationActivity.class);
            intent.putExtra("text_address" ,districtspinner.getText().toString());
            startActivityForResult(intent, 100);
        }
        else {
            String locationDescription = address_edittext.getText().toString();
            boolean emptyValues = selectedAddressTypeId == 0 ||
                    selectedFloorNumber == 0 ||
                    selectedCityId == 0 ||
                    selectedDistrict == 0 ||
                    locationDescription.isEmpty();
            if (emptyValues) {
                Toast.makeText(AddNewAddressActivity.this,
                        R.string.enter_all_data_validation, Toast.LENGTH_LONG).show();
                return;
            }
            else {
                AddressInput addressInput = new AddressInput();
                addressInput.setAddressTypeId(selectedAddressTypeId);
                addressInput.setFloorTypeId(selectedFloorNumber);
                addressInput.setDistrictId(selectedDistrict);
                addressInput.setLatitude(latitude);
                addressInput.setLongitude(longitude);
                addressInput.setAddressDescription(locationDescription);
                saveAddress(addressInput);
            }
        }
    }

    private void saveAddress(AddressInput addressInput){
        ILocalStorage localStorage = new SharedPreferencesStorage(this);
        String authToken = "bearer " + localStorage.getJwtToken().getStringToken();
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , authToken);
        customersService.addNewAddress(addressInput).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    setResult(Activity.RESULT_OK);
                    finish();
                }else{
                    Toast.makeText(AddNewAddressActivity.this, response.message() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                setResult(Activity.RESULT_CANCELED);
                Toast.makeText(AddNewAddressActivity.this, t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == ADDRESSTYPE) {
                address_type_Spinner.setText(data.getStringExtra("name"));
                selectedAddressTypeId = data.getIntExtra(LOOKUPTYPEINTENT,0);
            }else if(requestCode == CITY){
                cityspinner.setText(data.getStringExtra("name"));
                selectedCityId = data.getIntExtra(LOOKUPTYPEINTENT,0);
            }else if(requestCode == District){
                districtspinner.setText(data.getStringExtra("name"));
                selectedDistrict = data.getIntExtra(LOOKUPTYPEINTENT,0);
            }else if(requestCode == FLOORNUMBER){
                floor_number_spinner.setText(data.getStringExtra("name"));
                selectedFloorNumber = data.getIntExtra(LOOKUPTYPEINTENT,0);
            }else if(requestCode == 100){
                image_view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                image_view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                image_view.requestLayout();
                latitude = data.getDoubleExtra("latitude",0);
                longitude = data.getDoubleExtra("longitude",0);
                String map_url = "https://maps.googleapis.com/maps/api/staticmap?markers=color:red|" + latitude+"," +longitude+"&size=400x400&zoom=15&key=AIzaSyCi-TxJLcmswJylE6ULJ3cIT1IHtWHwV-c&language=ar";
                Picasso.with(this).load(map_url).into(image_view);
            }
        }
    }
}