package maksab.sd.customer.ui.main.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.BuildConfig;
import maksab.sd.customer.R;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.general.activities.AddressListActivity;
import maksab.sd.customer.ui.general.activities.ContactusActivity;
import maksab.sd.customer.ui.general.activities.CreditsPointsActivity;
import maksab.sd.customer.ui.general.activities.FaqsListActivity;
import maksab.sd.customer.ui.main.activties.BalanceActivity;
import maksab.sd.customer.ui.profile.activities.LoginActivity;
import maksab.sd.customer.ui.profile.activities.ProfileActivity;
import maksab.sd.customer.ui.providers.activties.FavouritesProvidersActivity;

public class SettingsFragment extends Fragment {


    @BindView(R.id.profile_layout)
    View profile_layout;
    @BindView(R.id.address)
    View address;
    @BindView(R.id.balance)
    View balance;
    @BindView(R.id.how_it_works)
    View how_it_works;
    @BindView(R.id.contact_us_layout)
    View contact_us_layout;
    @BindView(R.id.credits)
    View credits;
    @BindView(R.id.rate_layout)
    View rate_layout;
    @BindView(R.id.terms_layout)
    View terms_layout;

    @BindView(R.id.favorite_providers)
    View favorite_providers;

    @BindView(R.id.version_code)
    TextView version_code;
    @BindView(R.id.singout)
    Button singout;

    private ReviewManager reviewManager;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        reviewManager = ReviewManagerFactory.create(getActivity());

        singout.setOnClickListener(view18 -> {
            SharedPreferencesStorage localStorage = new SharedPreferencesStorage(getActivity());
            localStorage.logOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
           getActivity().startActivity(intent);
            getActivity().finish();
        });

        profile_layout.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        });

        address.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), AddressListActivity.class);
            startActivity(intent);
        });

        balance.setOnClickListener(view13 -> {
            Intent intent = new Intent(getActivity(), BalanceActivity.class);
            startActivity(intent);
        });

        contact_us_layout.setOnClickListener(view14 -> {
            Intent intent = new Intent(getActivity(), ContactusActivity.class);
            startActivity(intent);
        });

        how_it_works.setOnClickListener(view15 -> {
            Intent intent = new Intent(getActivity(), FaqsListActivity.class);
            startActivity(intent);
        });

        credits.setOnClickListener(view16 -> {
            Intent intent = new Intent(getActivity(), CreditsPointsActivity.class);
            startActivity(intent);
        });

        rate_layout.setOnClickListener(view17 -> {
            //showRateApp();
            showRatePage();
        });

        favorite_providers.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity() , FavouritesProvidersActivity.class);
            startActivity(intent);
        });


        version_code.setText(String.format("%s", BuildConfig.VERSION_CODE));
    }

    public void showRateApp() {
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();

                Task<Void> flow = reviewManager.launchReviewFlow(getActivity(), reviewInfo);
                flow.addOnCompleteListener(task1 -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                });
            } else {
                showRatePage();
            }
        });
    }

    private void showRatePage() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(
                "https://play.google.com/store/apps/details?id=sd.maksab.provider&hl=ar"));
        intent.setPackage("com.android.vending");
        startActivity(intent);
    }
}