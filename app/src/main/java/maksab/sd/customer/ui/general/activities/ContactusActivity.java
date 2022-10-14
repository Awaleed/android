package maksab.sd.customer.ui.general.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.faq.AboutUsModel;
import maksab.sd.customer.network.appnetwork.ILookUpsService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.ui.tickets.activities.AddTicketWizardActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactusActivity extends BaseActivity {

    @BindView(R.id.facebook_imageview)
    ImageView facebookImageView;
    @BindView(R.id.twitter_imageview)
    ImageView twitterImageView;
    @BindView(R.id.whatsapp_imageview)
    ImageView whatsapp_imageview;
    @BindView(R.id.youtube_imageview)
    ImageView youtube_imageview;
    @BindView(R.id.instagram_imageview)
    ImageView instagram_imageview;
    @BindView(R.id.call_button)
    Button call_button;
    @BindView(R.id.open_ticket_button)
    Button open_ticket_button;
    @BindView(R.id.image_view)
    ImageView map_view;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_progress)
    ProgressBar main_progress;

    @BindView(R.id.whole_layout)
    ViewGroup whole_layout;

    @BindView(R.id.office_description)
    TextView office_description;

    String phoneNumber = "6755";

    AboutUsModel aboutUsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        ButterKnife.bind(this);
        setupToolbar();
        call_button.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:"+phoneNumber));
            startActivity(callIntent);
        });
        open_ticket_button.setOnClickListener(view -> {
            Intent intent = new Intent(ContactusActivity.this, AddTicketWizardActivity.class);
            startActivity(intent);
        });
        getInfo();
    }

    private void initMap() {
        String link = aboutUsModel.getOfficeLocationMapImage();
        office_description.setText(aboutUsModel.getOfficeLocationDescription());
        map_view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

        Picasso.with(this).load(link).placeholder(R.drawable.logo_gray).into(map_view);
    }

    private void initSocialButtons() {
        phoneNumber = aboutUsModel.getPhoneNumber();
        call_button.setText(phoneNumber);
        facebookImageView.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(aboutUsModel.getFacebookLink()))));

        twitterImageView.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(aboutUsModel.getTwitterLink()))));

        instagram_imageview.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(aboutUsModel.getInstegramLink()))));

        youtube_imageview.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(aboutUsModel.getYoutubeLink()))));

        whatsapp_imageview.setOnClickListener(v -> {
            String url = "https://api.whatsapp.com/send?phone=" + "+249113555535";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.contact_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void getInfo(){
        main_progress.setVisibility(View.VISIBLE);
        whole_layout.setVisibility(View.GONE);

        ILookUpsService lookUpsService = GetWayServiceGenerator.createService(ILookUpsService.class);
        lookUpsService.getDynamicConfig().enqueue(new Callback<AboutUsModel>() {
            @Override
            public void onResponse(Call<AboutUsModel> call, Response<AboutUsModel> response) {
                main_progress.setVisibility(View.GONE);

                if(response.isSuccessful()){
                    aboutUsModel = response.body();
                    initMap();
                    initSocialButtons();
                    whole_layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AboutUsModel> call, Throwable t) {

            }
        });
    }
}