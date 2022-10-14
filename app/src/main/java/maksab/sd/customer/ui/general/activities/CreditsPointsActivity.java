package maksab.sd.customer.ui.general.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.profile.ProfileModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.util.general.SpanningUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreditsPointsActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.share_button)
    Button share_button;
    @BindView(R.id.credits_text_view)
    TextView credits_text_view;
    @BindView(R.id.deep_link_text_view)
    TextView deep_link_text_view;
    @BindView(R.id.share_details_text_view)
    TextView share_details_text_view;
    @BindView(R.id.copy_image_view)
    ImageView copy_image_view;
    private ILocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits_points);
        ButterKnife.bind(this);
        localStorage = new SharedPreferencesStorage(this);

        setupToolbar();
        getFullProfile();

        share_button.setOnClickListener(view -> {
            String body = getShareBodyMessage(localStorage.getUserProfile().getDeepLink());
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_maksab));
            sharingIntent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_with)));
        });

        copy_image_view.setOnClickListener(view -> {
            String body = getShareBodyMessage(localStorage.getUserProfile().getDeepLink());
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied Text", body);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(CreditsPointsActivity.this, getString(R.string.copied_to_cliport), Toast.LENGTH_SHORT).show();
        });

        ClickableSpan termsSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent mIntent = new Intent(CreditsPointsActivity.this, FaqsListActivity.class);
                startActivity(mIntent);
            }
        };

        SpanningUtil.setClickableString(getString(R.string.credit_description),
                share_details_text_view,
                new String[]{getString(R.string.click_for_more_details)},
                new ClickableSpan[]{termsSpan});
    }


    private String getShareBodyMessage(String deepLink) {
        String body =
                getString(R.string.need_service_with_good_price) +
                "\n" + getString(R.string.in_housing_services) +
                "\n" + getString(R.string.download_maksab) +
                "\n" + deepLink;

        return body;
    }

    private void getFullProfile() {
        showWaitDialog();

        ICustomersService providerService = GetWayServiceGenerator.createService(ICustomersService.class, "Bearer " + localStorage.getJwtToken().getStringToken());
        providerService.getProfile().enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if (response.isSuccessful()) {
                    ProfileModel profile = response.body();
                    String codeOnly = profile.getDeepLink().replace("https://maksabcustomer.app.link/", "");
                    credits_text_view.setText(profile.getDeepLinkCredits() + "");
                    deep_link_text_view.setText(codeOnly);
                }

                dismissWaitDialog();
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                dismissWaitDialog();
            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.credits);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}