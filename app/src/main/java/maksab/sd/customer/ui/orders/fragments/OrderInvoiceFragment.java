package maksab.sd.customer.ui.orders.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.providers.OrderModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.NumbersUtil;
import maksab.sd.customer.util.general.OrderUtils;
import maksab.sd.customer.util.general.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderInvoiceFragment extends Fragment {
    @BindView(R.id.execuation_price_textview)
    TextView execuation_price_textview;
    @BindView(R.id.spears_total_price_textview)
    TextView spears_total_price_textview;
    @BindView(R.id.balance_discount_textview)
    TextView balance_discount_textview;
    @BindView(R.id.maksab_insurance_amount_textview)
    TextView maksab_insurance_amount_textview;
    @BindView(R.id.transportation_amount_textview)
    TextView transportation_amount_textview;
    @BindView(R.id.coupon_discount_textview)
    TextView coupon_discount_textview;
    @BindView(R.id.coupon_name_textview)
    TextView coupon_name_textview;
    @BindView(R.id.total_price_textview)
    TextView total_price_textview;

    @BindView(R.id.guarantee_description_textview)
    TextView guarantee_description_textview;
    @BindView(R.id.guarantee_status_textview)
    TextView guarantee_status_textview;
    @BindView(R.id.guarantee_days_textview)
    TextView guarantee_days_textview;
    @BindView(R.id.guarantee_end_date_textview)
    TextView guarantee_end_date_textview;

    @BindView(R.id.no_invoice_layout)
    View no_invoice_layout;
    @BindView(R.id.invoice_layout)
    View invoice_layout;
    @BindView(R.id.gurantee_layout)
    View gurantee_layout;

    private ILocalStorage localStorage;

    public static OrderInvoiceFragment newInstance(int orderId) {
        OrderInvoiceFragment fragment = new OrderInvoiceFragment();
        Bundle arg = new Bundle();
        arg.putInt("OrderId", orderId);
        fragment.setArguments(arg);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_invoice, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        int orderId = getArguments().getInt("OrderId");
        getOrderById(orderId);
    }

    public void getOrderById(long id) {
        localStorage = new SharedPreferencesStorage(getContext());
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        GetWayServiceGenerator.createService(ICustomersService.class, token)
                .getorderbyId(id)
                .enqueue(new Callback<OrderModel>() {
                    @Override
                    public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                        if (response.isSuccessful()) {
                            initViews(response.body());
                        }
                        else {
                            try {
                                Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderModel> call, Throwable t) {
                        Toast.makeText(getActivity(), getString(R.string.internetError), Toast.LENGTH_LONG).show();
                        t.printStackTrace();
                    }
                });
    }

    private void initViews(OrderModel order) {
        no_invoice_layout.setVisibility(View.GONE);
        invoice_layout.setVisibility(View.VISIBLE);

        execuation_price_textview.setText(NumbersUtil.formatAmount(order.getInitialPrice()));
        balance_discount_textview.setText(NumbersUtil.formatAmount(order.getCustomerBalanceDiscount()));
        coupon_discount_textview.setText(NumbersUtil.formatAmount(order.getCouponDiscoun()));

        if (StringUtils.isEmpty(order.getCoupon())) {
            coupon_name_textview.setVisibility(View.GONE);
        }
        else {
            coupon_name_textview.setVisibility(View.VISIBLE);
            coupon_name_textview.setText(order.getCoupon());
        }

        transportation_amount_textview.setText(NumbersUtil.formatAmount(order.getTransportationPrice()));
        maksab_insurance_amount_textview.setText(NumbersUtil.formatAmount(order.getGuaranteePrice()));
        spears_total_price_textview.setText(NumbersUtil.formatAmount(order.getSpearItemsTotal()));

        double total = OrderUtils.getOrderTotalPrice(order);
        total_price_textview.setText(NumbersUtil.formatAmount(total));

        // Guarantee
        if (order.getOrderStatusId() == Enums.OrderStatusEnum.CANCELED.ordinal()) {
            gurantee_layout.setVisibility(View.GONE);
        }
        else {
            gurantee_layout.setVisibility(View.VISIBLE);
            guarantee_status_textview.setText(Enums.getGuaranteeStatusText(order.getGuaranteeStatusEnum()));
            guarantee_days_textview.setText(String.valueOf(order.getGuaranteePeriodInDays()));
            guarantee_end_date_textview.setText(order.getGuaranteeEndOnString());
        }
    }
}