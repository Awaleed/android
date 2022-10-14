package maksab.sd.customer.ui.general.activities;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.chip.Chip;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.faq.FaqViewModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FaqDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.faq_type_text_view)
    TextView faq_type_text_view;
    @BindView(R.id.faq_view_count_text_view)
    TextView faq_view_count_text_view;
    @BindView(R.id.faq_title_text_view)
    TextView faq_title_text_view;
    @BindView(R.id.faq_body_text_view)
    TextView faq_body_text_view;
    @BindView(R.id.yes_chip)
    Chip yes_chip;
    @BindView(R.id.no_chip)
    Chip no_chip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_detail);
        ButterKnife.bind(this);

        int faqId = getIntent().getIntExtra("FaqId", 0);
        setupToolbar(faqId);
        getFaqById(faqId);

        yes_chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FaqDetailActivity.this,
                       getString(R.string.feedback_thanks),
                        Toast.LENGTH_LONG).show();

                likeFaq(faqId);
            }
        });

        no_chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FaqDetailActivity.this,
                        getString(R.string.feedback_thanks),
                        Toast.LENGTH_LONG).show();

                dislikeFaq(faqId);
            }
        });
    }

    private void likeFaq(int faqId) {
        ILocalStorage localStorage = new SharedPreferencesStorage(this);
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class, "Bearer " + localStorage.getJwtToken().getStringToken());
        customersService.likeFaq(faqId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });
    }

    private void dislikeFaq(int faqId) {
        ILocalStorage localStorage = new SharedPreferencesStorage(this);
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class, "Bearer " + localStorage.getJwtToken().getStringToken());
        customersService.dislikeFaq(faqId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });
    }

    private void getFaqById(int faqId) {
        ILocalStorage localStorage = new SharedPreferencesStorage(this);
        ICustomersService providerService = GetWayServiceGenerator.createService(ICustomersService.class, "Bearer " + localStorage.getJwtToken().getStringToken());
        providerService.getFaqById(faqId).enqueue(new Callback<FaqViewModel>() {
            @Override
            public void onResponse(Call<FaqViewModel> call, Response<FaqViewModel> response) {
                if (response.isSuccessful()) {
                    FaqViewModel item = response.body();
                    showDetails(item);
                }
            }

            @Override
            public void onFailure(Call<FaqViewModel> call, Throwable t) {
            }
        });
    }

    private void increaseViewCount(int faqId) {
        ILocalStorage localStorage = new SharedPreferencesStorage(this);
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class, "Bearer " + localStorage.getJwtToken().getStringToken());
        customersService.increaseViewCount(faqId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });
    }

    private void showDetails(FaqViewModel item) {
        faq_type_text_view.setText(item.getFaqType().getArabicName());
        faq_view_count_text_view.setText(item.getViewCounts() + "");
        faq_title_text_view.setText(item.getArabicTitle());

        if (item.getArabicHtmlBody() != null && !item.getArabicHtmlBody().isEmpty())
            faq_body_text_view.setText(Html.fromHtml(item.getArabicHtmlBody()));

        increaseViewCount(item.getId());

        getSupportActionBar().setTitle(item.getArabicTitle());
    }

    private void setupToolbar(int faqId) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.faq ) + " #" + faqId);

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
}
