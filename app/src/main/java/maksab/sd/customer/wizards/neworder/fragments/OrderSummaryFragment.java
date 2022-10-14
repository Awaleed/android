package maksab.sd.customer.wizards.neworder.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.fragments.FragmentsContract;
import maksab.sd.customer.models.orders.details.OrderInputModel;
import maksab.sd.customer.models.profile.BalanceViewModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.OrderInMemoryStorage;
import maksab.sd.customer.storage.OrderSummaryDetails;
import maksab.sd.customer.storage.OrderSummaryInMemoryStorage;
import maksab.sd.customer.storage.OrderSummaryModel;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.general.activities.TransportationPricesActivity;
import maksab.sd.customer.util.general.AddressInMemoryStorage;
import maksab.sd.customer.util.general.DateUtil;
import maksab.sd.customer.util.general.NumbersUtil;
import maksab.sd.customer.util.general.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSummaryFragment extends Fragment implements FragmentsContract {
    @BindView(R.id.order_body_text_view)
    TextView order_body_text_view;
    @BindView(R.id.order_desire_time_text_view)
    TextView order_desire_time_text_view;
    @BindView(R.id.order_desire_text_view)
    TextView order_desire_text_view;
    @BindView(R.id.order_flex_time_text_view)
    TextView order_flex_time_text_view;
    @BindView(R.id.order_address_text_view)
    TextView order_address_text_view;

    @BindView(R.id.items_layout)
    View items_layout;
    @BindView(R.id.items_view)
    LinearLayout items_view;
    @BindView(R.id.items_empty_textview)
    TextView items_empty_textview;

    @BindView(R.id.execuation_price_textview)
    TextView execuation_price_textview;
    @BindView(R.id.balance_discount_textview)
    TextView balance_discount_textview;
    @BindView(R.id.maksab_insurance_amount_textview)
    TextView maksab_insurance_amount_textview;
    @BindView(R.id.transportation_amount_textview)
    TextView transportation_amount_textview;
    @BindView(R.id.coupon_discount_textview)
    TextView coupon_discount_textview;
    @BindView(R.id.total_price_textview)
    TextView total_price_textview;
    @BindView(R.id.show_districts_transportation_price)
    TextView show_districts_transportation_price;

    @BindView(R.id.show_coupon_code)
    TextView show_coupon_code;

    @BindView(R.id.not_using_balance)
    CheckBox not_using_balance_checkbox;

    private double balanceAmount = 0;
    private ILocalStorage localStorage;

    private ProgressDialog dialog;

    public static OrderSummaryFragment newInstance(int transportationPrice) {
        OrderSummaryFragment fragment = new OrderSummaryFragment();
        Bundle args = new Bundle();
        args.putInt("transportationPrice", transportationPrice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_summary, container, false);
        ButterKnife.bind(this, view);
        localStorage = new SharedPreferencesStorage(getContext());
        not_using_balance_checkbox.setOnCheckedChangeListener((compoundButton, b) -> {
            OrderInMemoryStorage.is_balance_used = b;
            if(!b){
                finalPrice = finalPrice + balanceAmount;
            }else{
                finalPrice = finalPrice - balanceAmount;
            }
            setFinalPrice();
        });
        return view;
    }

    private void setFinalPrice() {
        if (finalPrice > 0) {
            total_price_textview.setText(NumbersUtil.formatAmount(finalPrice));
        } else if (finalPrice == 0 || finalPrice < 0) {
            total_price_textview.setText("0");
        } else {
            total_price_textview.setText(R.string.not_specified);
        }
    }

    double finalPrice = 0;
    private void showOrderDetail() {
        int transportationPrice = getArguments().getInt("transportationPrice");

        OrderInputModel order = OrderInMemoryStorage.getOrderInputs();
        OrderSummaryModel orderSummaryModel = OrderSummaryInMemoryStorage.getOrderSummary();

        if (!TextUtils.isEmpty(order.getBody())) {
            order_body_text_view.setText(order.getBody());
        } else {
            order_body_text_view.setText(R.string.noDescription);
        }

        if (!StringUtils.isEmpty(order.getDesireOn())) {
            DateUtil dateUtil = DateUtil.newInstance();
            dateUtil.parse(order.getDesireOn());
            order_desire_text_view.setText(dateUtil.getDateString());
        } else {
            order_desire_text_view.setText(R.string.nearest_available_time);
        }

        if (!StringUtils.isEmpty(order.getSelectedTime())) {
            order_desire_time_text_view.setText(order.getSelectedTime());
        } else {
            order_desire_time_text_view.setText(R.string.nearest_available_time);
        }

        String status = order.isAcceptFlexibleTime() ?
                getString(R.string.yes) : getString(R.string.no);
        order_flex_time_text_view.setText(status);

        show_districts_transportation_price.setPaintFlags(show_districts_transportation_price.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        show_districts_transportation_price.setOnClickListener(view -> {
            Intent intent = new Intent(getContext() , TransportationPricesActivity.class);
            intent.putExtra("speciality_id" , order.getSpecialityId());
            intent.putExtra("speciality_name" , getString(R.string.prices_details));
            startActivity(intent);
        });

        // Invoice
        double total = getTotal();

        transportation_amount_textview.setText(NumbersUtil.formatAmount(transportationPrice));

        //show_districts_transportation_price.setPaintFlags(show_districts_transportation_price.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        if (total == 0)
            execuation_price_textview.setText(R.string.not_specified);
        else
            execuation_price_textview.setText(NumbersUtil.formatAmount(total));

        balance_discount_textview.setText(NumbersUtil.formatAmount(balanceAmount));
        coupon_discount_textview.setText(NumbersUtil.formatAmount(orderSummaryModel.getCouponAmount()));
        show_coupon_code.setText(orderSummaryModel.getCouponCode());
        maksab_insurance_amount_textview.setText(NumbersUtil.formatAmount(orderSummaryModel.getGuranteePrice()));

         finalPrice = total -
                balanceAmount - orderSummaryModel.getCouponAmount() +
                transportationPrice + orderSummaryModel.getGuranteePrice();
        setFinalPrice();

        order_address_text_view.setText(AddressInMemoryStorage.body);
    }

    private double getTotal() {
        double total = 0;
        List<OrderSummaryDetails> items = OrderSummaryInMemoryStorage.getSelectedServices();

        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).getPrice() * items.get(i).getQuantity();
        }

        return total;
    }

    private void addOrderItems(List<OrderSummaryDetails> items) {
        if(items_view !=null){
            items_view.removeAllViews();
        }else return;

        if (items == null || items.size() == 0) {
            items_view.setVisibility(View.GONE);
            items_empty_textview.setVisibility(View.VISIBLE);
        } else {
            items_view.setVisibility(View.VISIBLE);
            items_empty_textview.setVisibility(View.GONE);

            for (OrderSummaryDetails item : items) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.item_order_detail, null);

                TextView item_title = view.findViewById(R.id.item_title);
                TextView item_quantity = view.findViewById(R.id.item_quantity);
                TextView item_price = view.findViewById(R.id.item_price);

                item_title.setText(item.getName());
                item_quantity.setText(getString(R.string.qountity) + item.getQuantity());
                item_price.setText(NumbersUtil.formatAmount(item.getPrice()));

                items_view.addView(view);
            }
        }
    }

    @Override
    public boolean isValidForm() {
        return true;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public String getStepTitle(Context context) {
        return getString(R.string.order_wizard_final_step);
    }

    private void getCustomerBalance(){
        showWaitDialog();
        SharedPreferencesStorage localStorage = new SharedPreferencesStorage(getContext());
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        GetWayServiceGenerator.createService(ICustomersService.class, token)
                .getBalance()
                .enqueue(new Callback<BalanceViewModel>() {
                    @Override
                    public void onResponse(Call<BalanceViewModel> call, Response<BalanceViewModel> response) {
                        if (response.isSuccessful()) {
                            BalanceViewModel balance = response.body();
                            balanceAmount = balance.getBalance();
                            List<OrderSummaryDetails> orderSummaryDetails = OrderSummaryInMemoryStorage.getSelectedServices();
                            addOrderItems(orderSummaryDetails);
                            showOrderDetail();
                            dismissWaitDialog();
                            if(balanceAmount ==0){
                                not_using_balance_checkbox.setVisibility(View.GONE);
                            } else{
                                OrderInMemoryStorage.is_balance_used = true;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BalanceViewModel> call, Throwable t) {
                        dismissWaitDialog();
                    }
                });
    }


    public void showWaitDialog() {
        dialog = ProgressDialog.show(getContext(), "",
                getString(R.string.loading_please_wait), true);
    }

    public void dismissWaitDialog(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    @Override
    public void saveChange() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //call it once on create fragment
        if(isVisibleToUser){
           getCustomerBalance();
        }
    }
}