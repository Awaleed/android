package maksab.sd.customer.ui.entities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.providers.ProviderDetailsModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.util.constants.Enums;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopFragment extends Fragment {
    @BindView(R.id.shop_name)
    TextView shop_name;
    @BindView(R.id.shop_description)
    TextView shop_description;
    @BindView(R.id.shop_location_description)
    TextView shop_location_description;
    @BindView(R.id.delivery_price_near_edittext)
    TextView delivery_price_near_edittext;
    @BindView(R.id.delivery_price_far_edittext)
    TextView delivery_price_far_edittext;
    @BindView(R.id.deliverySpinner)
    TextView deliverySpinner;
    @BindView(R.id.delivery_price_layout)
    ViewGroup delivery_price_layout;
    @BindView(R.id.image_view)
    ImageView image_view;
    @BindView(R.id.image_layout)
    View image_layout;

    public static ShopFragment newInstance(String userId) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putString("Provider.UserID", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        ButterKnife.bind(this, view);
        fetchProfileData();
        return view;
    }

    private void fetchProfileData() {
        String providerUserId = getArguments().getString("Provider.UserID");

        SharedPreferencesStorage localStorage = new SharedPreferencesStorage(getActivity());
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        GetWayServiceGenerator.createService(ICustomersService.class, token)
                .getProviderProfile(providerUserId)
                .enqueue(new Callback<ProviderDetailsModel>() {
                    @Override
                    public void onResponse(Call<ProviderDetailsModel> call, Response<ProviderDetailsModel> response) {

                        if (response.isSuccessful()) {
                            ProviderDetailsModel profile = response.body();

                            deliverySpinner.setText(Enums.getHaveDeliveryString(profile.isShopHaveDelivery()));
                            shop_name.setText(profile.getShopName());
                            shop_description.setText(profile.getShopDescription());
                            shop_location_description.setText(profile.getShopLocationDescription());
                            delivery_price_near_edittext.setText(String.valueOf((int) profile.getDeliveryPriceNear()));
                            delivery_price_far_edittext.setText(String.valueOf((int) profile.getDeliveryPriceFar()));
                            handleDeliveryLayout(profile.isShopHaveDelivery());

                            if (profile.getShopLatitude() != 0 && profile.getShopLongitude() != 0) {
                                showLocationOnMap(profile.getShopLatitude() + "", profile.getShopLongitude() + "");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProviderDetailsModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void handleDeliveryLayout(boolean haveDelivery) {
        if (haveDelivery) {
            delivery_price_layout.setVisibility(View.VISIBLE);
        } else {
            delivery_price_layout.setVisibility(View.GONE);
        }
    }


    private void showLocationOnMap(String lat, String lon) {
        String link = String.format("https://maps.googleapis.com/maps/api/staticmap?markers=color:red|%s,%s&size=600x600&zoom=15&key=AIzaSyCi-TxJLcmswJylE6ULJ3cIT1IHtWHwV-c&language=ar",
                lat, lon);
        image_view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        Picasso.with(getActivity()).load(link)
                .placeholder(R.drawable.placeholder)
                .into(image_view);

        image_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" +
                                lat + "," + lon));
                startActivity(intent);
            }
        });
    }
}
