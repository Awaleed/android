package maksab.sd.customer.ui.orders.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.rygelouv.audiosensei.player.AudioSenseiListObserver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.adapters.BaseFragmentAdapter;
import maksab.sd.customer.basecode.utility.OrderStatusEnum;
import maksab.sd.customer.ui.chat.chats.ChatListFragment;
import maksab.sd.customer.ui.orders.fragments.OffersFragment;
import maksab.sd.customer.ui.orders.fragments.OrderInformationFragment;
import maksab.sd.customer.ui.orders.fragments.OrderInvoiceFragment;
import maksab.sd.customer.ui.orders.fragments.OrderManagementFragment;
import maksab.sd.customer.util.constants.Enums;

public class OrderDetailsActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.offers_chip)
    Chip offers_chip;
    @BindView(R.id.chat_chip)
    Chip chat_chip;
    @BindView(R.id.invoice_chip)
    Chip invoice_chip;
    @BindView(R.id.order_management)
    Chip order_management;
    @BindView(R.id.order_info_chip)
    Chip order_info_chip;
    @BindView(R.id.chipGroup)
    ChipGroup chipGroup;

    private int orderStatus;
    private List<Fragment> fragments;
    private List<String> fragmentsTitles;
    private long orderId;
    private boolean isQuotation = false;
    private int orderType =0;
    private String providerId = "";
    private boolean showingOffers = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_details);
        ButterKnife.bind(this);

        orderId = getIntent().getIntExtra("id", 0);
        orderStatus = getIntent().getIntExtra("order.status", 0);
        orderType = getIntent().getIntExtra("order.type", 0);
        providerId = getIntent().getStringExtra("order.provider_id");
        isQuotation = getIntent().getBooleanExtra("order.isQuotation" , false);

        showingOffers = (isQuotation || ( (orderType == Enums.OrderTypeEnum.MaksabPricedService.ordinal() ||
                orderType == Enums.OrderTypeEnum.MaksabOffer.ordinal()) && (providerId == null || TextUtils.isEmpty(providerId))))
                && orderStatus == OrderStatusEnum.WaitingProviders.ordinal();

        AudioSenseiListObserver.getInstance().registerLifecycle(getLifecycle());

        setupToolbar();
        setupFragments();
        setupViewPager();
        hideShowChipsBasedOnOrderType();
    }

    private void setupToolbar() {
        toolbar.setTitle(getString(R.string.order_details) + orderId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupViewPager() {
        BaseFragmentAdapter baseFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragments, fragmentsTitles);
        view_pager.setAdapter(baseFragmentAdapter);
        view_pager.setOffscreenPageLimit(fragments.size());
    }

    private void hideShowChipsBasedOnOrderType() {
        chipGroup.setVisibility(View.VISIBLE);
        if(showingOffers){
            offers_chip.setVisibility(View.VISIBLE);
        }else{
            offers_chip.setVisibility(View.GONE);
        }

        if (isQuotation) {
            invoice_chip.setVisibility(View.GONE);
        } else {

            invoice_chip.setVisibility(View.VISIBLE);
        }
    }

    private void setupFragments() {
        boolean isOrderFinished = false;

        if (!isQuotation) {
            order_info_chip.setTag(0);
            chat_chip.setTag(1);
            invoice_chip.setTag(2);
            order_management.setTag(3);
        }

        if(showingOffers){
            offers_chip.setTag(0);
            order_info_chip.setTag(1);
            chat_chip.setTag(2);
            invoice_chip.setTag(3);
            order_management.setTag(4);
        }

        order_info_chip.setChecked(true);

        if (orderStatus == OrderStatusEnum.FINISHED.ordinal() || orderStatus == OrderStatusEnum.CANCELED.ordinal()) {
            isOrderFinished = true;
        }

        fragments = new ArrayList<>();
        fragmentsTitles = new ArrayList<>();
        if (showingOffers) {
            fragments.add(OffersFragment.newInstance(orderId, false));
            offers_chip.setChecked(true);
        }

        fragments.add(OrderInformationFragment.newInstance(Integer.parseInt(orderId + "")));
        fragments.add(ChatListFragment.newInstance((int) orderId, ChatListFragment.ChatForType.Order,
                isOrderFinished));

        if (!isQuotation) {
            fragments.add(OrderInvoiceFragment.newInstance(Integer.parseInt(orderId + "")));
        }

        fragments.add(OrderManagementFragment.newInstance(orderId));

        chipGroup.setOnCheckedChangeListener((chipGroup, i) -> {
            Chip chip = chipGroup.findViewById(i);
            if (chip != null) {
                view_pager.setCurrentItem(Integer.parseInt(chip.getTag() + ""), true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
