package maksab.sd.customer.ui.entities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

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

public class CenterFragment extends Fragment {
    @BindView(R.id.center_name)
    TextInputEditText center_name;
    @BindView(R.id.center_information_textedit)
    TextInputEditText center_information_textedit;
    @BindView(R.id.center_location_description_textedit)
    TextInputEditText center_location_description_textedit;
    @BindView(R.id.daysSpinner)
    AutoCompleteTextView daysSpinner;
    @BindView(R.id.fromSpinner)
    AutoCompleteTextView fromSpinner;
    @BindView(R.id.toSpinner)
    AutoCompleteTextView toSpinner;
    @BindView(R.id.image_view)
    ImageView image_view;
    @BindView(R.id.image_layout)
    View image_layout;

    public static CenterFragment newInstance(String userId) {
        CenterFragment fragment = new CenterFragment();
        Bundle args = new Bundle();
        args.putString("Provider.UserID", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_center, container, false);
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

                            center_name.setText(profile.getCenterName());
                            center_information_textedit.setText(profile.getCenterDescription());
                            center_location_description_textedit.setText(profile.getCenterLocationDescription());

                            fromSpinner.setText(profile.getCenterOpenAt());
                            toSpinner.setText(profile.getCenterCloseAt());
                            daysSpinner.setText(Enums.getCenterWorkingDays(profile.getCenterOpenningDays()));

                            if (profile.getCenterLatitude() != 0 && profile.getCenterLongitude() != 0) {
                                showLocationOnMap(profile.getCenterLatitude() + "", profile.getCenterLongitude() + "");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProviderDetailsModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
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

