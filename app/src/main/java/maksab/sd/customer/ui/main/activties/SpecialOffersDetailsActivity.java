package maksab.sd.customer.ui.main.activties;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.orders.details.HomeSpecialOfferModel;
import maksab.sd.customer.storage.OrderSummaryDetails;
import maksab.sd.customer.storage.OrderSummaryInMemoryStorage;
import maksab.sd.customer.ui.media.viewer.MediaActivityOpener;
import maksab.sd.customer.util.general.ImageZoomingActivity;
import maksab.sd.customer.util.general.NumbersUtil;
import maksab.sd.customer.wizards.neworder.NewOrderWizardActivity;

public class SpecialOffersDetailsActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.offer_image)
    ImageView offer_image;
    @BindView(R.id.offer_title)
    TextView offer_title;
    @BindView(R.id.old_price)
    TextView old_price;
    @BindView(R.id.new_price)
    TextView new_price;
    @BindView(R.id.offer_description)
    TextView offer_description;
    @BindView(R.id.offer_end_on)
    TextView offer_end_on;

    private HomeSpecialOfferModel homeSpecialOfferModel;
    private String providerId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_offers_details);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews(){
        OrderSummaryInMemoryStorage.isOfferStorageActive = false;
        providerId = getIntent().getStringExtra("providerId");
        setupToolBar();
        homeSpecialOfferModel = getIntent().getParcelableExtra("model");
        Picasso.with(this).load(homeSpecialOfferModel.getOfferImage()).into(offer_image);
        offer_title.setText(homeSpecialOfferModel.getName());
        old_price.setText(NumbersUtil.formatAmount(homeSpecialOfferModel.getOriginalPrice()));
        old_price.setPaintFlags(old_price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        new_price.setText(NumbersUtil.formatAmount(homeSpecialOfferModel.getPrice()));
        offer_description.setText(homeSpecialOfferModel.getDescription());
        offer_end_on.setText(getString(R.string.end_on) + homeSpecialOfferModel.getToTimeString());

        offer_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaActivityOpener.openViewActivity(SpecialOffersDetailsActivity.this,
                        homeSpecialOfferModel.getOfferImage());
            }
        });
    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.offer_details);

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

    @OnClick(R.id.request_offer_btn)
    void onRequestOfferClicked(){
        askForQuantity();
    }

    private void openNewOrderWizard() {
        Intent intent = new Intent(this , NewOrderWizardActivity.class);
        intent.putExtra("isfrom_offer" , true);
        intent.putExtra("offer.id" , homeSpecialOfferModel.getId());
        intent.putExtra("speciality.id" , homeSpecialOfferModel.getSpecialty().getId());
        if(providerId !=null && !TextUtils.isEmpty(providerId)){
            intent.putExtra("provider_id",providerId);
        }
        startActivity(intent);
    }

    private void askForQuantity(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.are_you_sure_to_order_offer);
        builder.setPositiveButton(R.string.order_offer, (dialogInterface, i) -> {

            OrderSummaryInMemoryStorage.isOfferStorageActive = true;

            OrderSummaryInMemoryStorage.offer_quantity = 1;
            OrderSummaryInMemoryStorage.addSelectedService(new OrderSummaryDetails(homeSpecialOfferModel.getName() ,  1,
                    homeSpecialOfferModel.getPrice() , homeSpecialOfferModel.getId()));
            openNewOrderWizard();
        });
        builder.setNegativeButton(R.string.action_cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }
}