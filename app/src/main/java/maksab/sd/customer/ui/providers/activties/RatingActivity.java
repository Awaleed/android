package maksab.sd.customer.ui.providers.activties;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.main.RateOrder;
import maksab.sd.customer.models.providers.OrderModel;
import maksab.sd.customer.models.providers.SetFavoriteModel;
import maksab.sd.customer.ui.orders.fragments.RateAppNowDialog;
import maksab.sd.customer.util.general.ImageUtil;
import maksab.sd.customer.util.general.NumbersUtil;
import maksab.sd.customer.util.general.OrderUtils;
import maksab.sd.customer.viewmodels.main.RatingViewModel;
import maksab.sd.customer.viewmodels.providers.ProviderDetailsViewModel;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RatingActivity extends BaseActivity {
    @BindView(R.id.provider_profile_image)
    ShapeableImageView provider_profile_image;
    @BindView(R.id.provider_name)
    TextView provider_name;
    @BindView(R.id.total_rating_bar)
    MaterialRatingBar total_rating_bar;
    @BindView(R.id.service_view_text)
    TextView service_view_text;
    @BindView(R.id.order_date_textview)
    TextView order_date_textview;
    @BindView(R.id.total_rate)
    TextView total_rate;
    @BindView(R.id.rate_comment)
    EditText rate_comment;
    @BindView(R.id.price_text_view)
    TextView price_text_view;
    @BindView(R.id.complain_checkbox)
    CheckBox complain_checkbox;
    @BindView(R.id.rate_button)
    Button rate_button;

    @BindView(R.id.quality_icon_1)
    ImageView quality_icon_1;
    @BindView(R.id.quality_icon_2)
    ImageView quality_icon_2;
    @BindView(R.id.quality_icon_3)
    ImageView quality_icon_3;
    @BindView(R.id.quality_icon_4)
    ImageView quality_icon_4;
    @BindView(R.id.quality_icon_5)
    ImageView quality_icon_5;
    @BindView(R.id.provider_icon_1)
    ImageView provider_icon_1;
    @BindView(R.id.provider_icon_2)
    ImageView provider_icon_2;
    @BindView(R.id.provider_icon_3)
    ImageView provider_icon_3;
    @BindView(R.id.provider_icon_4)
    ImageView provider_icon_4;
    @BindView(R.id.provider_icon_5)
    ImageView provider_icon_5;

    @BindView(R.id.quality_text_1)
    TextView quality_text_1;
    @BindView(R.id.quality_text_2)
    TextView quality_text_2;
    @BindView(R.id.quality_text_3)
    TextView quality_text_3;
    @BindView(R.id.quality_text_4)
    TextView quality_text_4;
    @BindView(R.id.quality_text_5)
    TextView quality_text_5;
    @BindView(R.id.provider_text_1)
    TextView provider_text_1;
    @BindView(R.id.provider_text_2)
    TextView provider_text_2;
    @BindView(R.id.provider_text_3)
    TextView provider_text_3;
    @BindView(R.id.provider_text_4)
    TextView provider_text_4;
    @BindView(R.id.provider_text_5)
    TextView provider_text_5;

    @BindView(R.id.add_provider_to_favourite_checkbox)
    CheckBox add_provider_to_favourite_checkbox;

    private ImageView[] qualityImageViews;
    private TextView[] qualityTextViews;

    private ImageView[] providerImageViews;
    private TextView[] providerTextViews;

    private RatingViewModel ratingViewModel;
    private int qualityRating;
    private int providerRating;
    private enum RateType { Quality, Provider }

    private ProviderDetailsViewModel providerDetailsViewModel;
    OrderModel orderModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ButterKnife.bind(this);
        initViews();
        addRateLogic();
        providerDetailsViewModel = ViewModelProviders.of(this).get(ProviderDetailsViewModel.class);
    }

    private void addRateLogic() {
        qualityImageViews = new ImageView[]{quality_icon_5, quality_icon_4, quality_icon_3, quality_icon_2, quality_icon_1};
        qualityTextViews = new TextView[]{ quality_text_5, quality_text_4, quality_text_3, quality_text_2, quality_text_1};

        providerImageViews = new ImageView[]{provider_icon_5, provider_icon_4, provider_icon_3, provider_icon_2, provider_icon_1 };
        providerTextViews = new TextView[]{provider_text_5, provider_text_4, provider_text_3, provider_text_2, provider_text_1};

        handleButtons(qualityImageViews, qualityTextViews, -1, RateType.Quality);
        handleButtons(providerImageViews, providerTextViews, -1, RateType.Provider);

        setButtonListeners(qualityImageViews, qualityTextViews, RateType.Quality);
        setButtonListeners(providerImageViews, providerTextViews, RateType.Provider);
    }

    private void setButtonListeners(ImageView[] images, TextView[] texts, RateType type) {
        for (int i = 0; i < images.length; i++) {
            final int index = i;
            images[i].setOnClickListener(v -> handleButtons(images, texts, index, type));

            texts[i].setOnClickListener(v -> handleButtons(images, texts, index, type));
        }
    }

    private void handleButtons(ImageView[] images, TextView[] texts, int index, RateType type) {
        for (int i = 0; i < images.length; i++) {
            if (i != index) {
                ImageUtil.setLocked(images[i]);
                texts[i].setTextColor(getResources().getColor(R.color.light_gray));
            } else {
                ImageUtil.setUnlocked(images[i]);
                texts[i].setTextColor(getResources().getColor(R.color.black));
                if (type == RateType.Quality) {
                    qualityRating = i+1;
                }
                else {
                    providerRating = i+1;
                }
            }

        }

        if(index == 4){
            add_provider_to_favourite_checkbox.setChecked(true);
        }else{
            add_provider_to_favourite_checkbox.setChecked(false);
        }
    }

    private void initViews() {

        orderModel = getIntent().getParcelableExtra("Order");
        long orderid = orderModel.getId();
        ratingViewModel = ViewModelProviders.of(this).get(RatingViewModel.class);
        ratingViewModel.saveRatingObserver().observe(this, isdon -> {
            providerDetailsViewModel.setToFavorite(new SetFavoriteModel("" , orderModel.getProviderUserId() , orderModel.getProviderId(), true));
            dismissWaitDialog();
            if (isdon) {
                Toast.makeText(this, R.string.save_rating, Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("provider_rate" , providerRating);
                intent.putExtra("quality_rate" , qualityRating);
                setResult(RESULT_OK , intent);
                finish();
            }
        });



        provider_name.setText(orderModel.getProviderName());
        Picasso.with(this)
                .load(orderModel.getProviderImage())
                .placeholder(R.drawable.placeholder)
                .into(provider_profile_image);

        total_rating_bar.setNumStars((int)orderModel.getRate());
        total_rate.setText(String.valueOf((int)orderModel.getRate()));
        price_text_view.setText(NumbersUtil.formatAmount(OrderUtils.getOrderTotalPrice(orderModel)));
        service_view_text.setText(orderModel.getSpecialityName());
        order_date_textview.setText(orderModel.getOrderStatusUpdatedOnString());

        rate_button.setOnClickListener(view -> {
            if (qualityRating > 0 && providerRating > 0) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.rating_provider)
                        .setMessage(R.string.sure_rating_provider)
                        .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                            showWaitDialog();
                            RateOrder rateOrder = new RateOrder(orderid, qualityRating,
                                    rate_comment.getText().toString(), providerRating,
                                    complain_checkbox.isChecked());
                            ratingViewModel.ratingProvider(rateOrder);
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
            else {
                Toast.makeText(this, R.string.select_rate, Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.finishAffinity();
        } else {
            return;
        }
    }
}
