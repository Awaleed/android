package maksab.sd.customer.ui.orders.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import maksab.sd.customer.R;
import maksab.sd.customer.models.address.District;
import maksab.sd.customer.models.address.FloorType;
import maksab.sd.customer.models.address.OrderAddressViewModel;
import maksab.sd.customer.models.orders.details.OrderDetailViewModel;
import maksab.sd.customer.models.orders.details.OrderImageViewModel;
import maksab.sd.customer.models.providers.OrderModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.media.viewer.MediaActivityOpener;
import maksab.sd.customer.ui.orders.adapters.OrderImagesAdapter;
import maksab.sd.customer.ui.providers.activties.ProviderDetailsActivity;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.BuildDynamicUIQuestionsHelper;
import maksab.sd.customer.util.general.DateUtil;
import maksab.sd.customer.util.general.NumbersUtil;
import maksab.sd.customer.util.general.OrderUtils;
import maksab.sd.customer.util.general.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderInformationFragment extends Fragment {
    @BindView(R.id.specialty_type_text_view)
    TextView specialty_type_text_view;
    @BindView(R.id.order_type_text_view)
    TextView order_type_text_view;
    @BindView(R.id.price_text_view)
    TextView price_text_view;
    @BindView(R.id.order_status_textview)
    TextView order_status_textview;
    @BindView(R.id.order_body_text_view)
    TextView order_body_text_view;
    @BindView(R.id.order_creation_text_view)
    TextView order_creation_text_view;
    @BindView(R.id.order_desire_time_text_view)
    TextView order_desire_time_text_view;
    @BindView(R.id.order_desire_text_view)
    TextView order_desire_text_view;
    @BindView(R.id.order_flex_time_text_view)
    TextView order_flex_time_text_view;
    @BindView(R.id.order_location_text_view)
    TextView order_location_text_view;
    @BindView(R.id.order_address_text_view)
    TextView order_address_text_view;
    @BindView(R.id.order_location_map)
    ImageView order_location_map;
    @BindView(R.id.provider_view)
    View provider_view;
    @BindView(R.id.provider_image)
    ImageView provider_image;
    @BindView(R.id.provider_name_text_view)
    TextView provider_name_text_view;
    @BindView(R.id.provider_mobile_text_view)
    TextView provider_mobile_text_view;
    @BindView(R.id.items_recyclerview)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.no_data_text_view)
    TextView no_data_text_view;
    @BindView(R.id.items_layout)
    View items_layout;
    @BindView(R.id.answers_layout)
    View answers_layout;
    @BindView(R.id.dynamic_view)
    LinearLayout dynamic_view;
    @BindView(R.id.items_view)
    LinearLayout items_view;
    @BindView(R.id.scheduling_view)
    View scheduling_view;
    @BindView(R.id.order_last_update_date)
    TextView order_updated_on;
    @BindView(R.id.order_inner_updated_on_textview)
    TextView order_inner_updated_on_textview;
    @BindView(R.id.execution_type_text_view)
    TextView execution_type_text_view;
    @BindView(R.id.order_scheduled_time_textview)
    TextView order_scheduled_time_textview;
    @BindView(R.id.order_inner_status_textview)
    TextView order_inner_status_textview;
    @BindView(R.id.items_empty_textview)
    TextView items_empty_textview;
    @BindView(R.id.additional_empty_textview)
    TextView additional_empty_textview;

    @BindView(R.id.cancel_view)
    View cancel_view;
    @BindView(R.id.cancel_by_text_view)
    TextView cancel_by_text_view;
    @BindView(R.id.cancel_reason_text_view)
    TextView cancel_reason_text_view;
    @BindView(R.id.close_reason_body)
    TextView close_reason_body;

    private ILocalStorage localStorage;
    private OrderImagesAdapter itemsAdapter;

    public static OrderInformationFragment newInstance(int orderId) {
        OrderInformationFragment fragment = new OrderInformationFragment();
        Bundle arg = new Bundle();
        arg.putInt("OrderId", orderId);
        fragment.setArguments(arg);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        ButterKnife.bind(this, view);

        localStorage = new SharedPreferencesStorage(getActivity());
        int orderId = getArguments().getInt("OrderId");
        getOrderById(orderId);

        return view;
    }

    public void getOrderById(long id) {
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        GetWayServiceGenerator.createService(ICustomersService.class, token)
                .getorderbyId(id)
                .enqueue(new Callback<OrderModel>() {
                    @Override
                    public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                        if (response.isSuccessful()) {
                            if(!OrderInformationFragment.this.isDetached() && !OrderInformationFragment.this.isRemoving() && OrderInformationFragment.this.getActivity() !=null){
                                showOrderDetail(response.body());
                            }

                        } else {
                            try {
                                Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderModel> call, Throwable t) {
                        Toast.makeText(getActivity(), getActivity().getResources().getText(R.string.internetError), Toast.LENGTH_LONG).show();
                        t.printStackTrace();
                    }
                });
    }

    private String getOrderSpecialtyWithExecutionType(OrderModel order) {
        String result = "";
        String execuationType = Enums.getExecuationtypeString(order.getExecutionTypeEnum());

        if (order.getOrderTypeId() == Enums.OrderTypeEnum.Quotation.ordinal()) {
            if (order.getOrderStatusId() == Enums.OrderStatusEnum.PENDING.ordinal() ||
                    order.getOrderStatusId() == Enums.OrderStatusEnum.CANCELED.ordinal() ||
                    order.getOrderStatusId() == Enums.OrderStatusEnum.WaitingProviders.ordinal()) {
                result = order.getSpecialityName();
            }
            else {
                result = execuationType + " - " + order.getSpecialityName();
            }
        }
        else {
            result = execuationType + " - " + order.getSpecialityName();
        }
        return result;
    }

    private void showOrderDetail(OrderModel order) {
        if (order.getOrderStatusId() == Enums.OrderStatusEnum.WaitingProviders.ordinal() ||
            order.getOrderStatusId() == Enums.OrderStatusEnum.PENDING.ordinal()) {
            scheduling_view.setVisibility(View.GONE);
        }
        else {
            scheduling_view.setVisibility(View.VISIBLE);
            execution_type_text_view.setText(Enums.getExecuationtypeString(order.getExecutionTypeEnum()));
            order_updated_on.setText(order.getOrderStatusUpdatedOnString());
            order_inner_updated_on_textview.setText(order.getInnerOrderStatusUpdatedOnString());
            order_inner_status_textview.setText(order.getInnerOrderStatusString());

            if (!StringUtils.isEmpty(order.getAgreedTime())) {
                DateUtil parser = DateUtil.newInstance();
                parser.parse(order.getAgreedTimeOn());
                order_scheduled_time_textview.setText(parser.getDateTimeString());
            }
            else {
                order_scheduled_time_textview.setText(R.string.no);
            }
        }

        specialty_type_text_view.setText(order.getSpecialityName());
        order_type_text_view.setText(order.getOrderTypeArabic());
        order_status_textview.setText(order.getOrderStatusArabic());

        price_text_view.setText(OrderUtils.getOrderTotalPriceAsString(getActivity(), order));

        if (!TextUtils.isEmpty(order.getBody())) {
            order_body_text_view.setText(order.getBody());
        } else {
            order_body_text_view.setText(R.string.noDescription);
        }

        order_creation_text_view.setText(order.getCreatedOnString());

        if (!StringUtils.isEmpty(order.getDesireOn())) {
            DateUtil dateUtil = DateUtil.newInstance();
            dateUtil.parse(order.getDesireOn());
            order_desire_text_view.setText(dateUtil.getDateString());
        }
        else {
            order_desire_text_view.setText(R.string.nearest_available_time);
        }

        if (!StringUtils.isEmpty(order.getSelectedTime())) {
            order_desire_time_text_view.setText(order.getSelectedTime());
        }
        else {
            order_desire_time_text_view.setText(R.string.nearest_available_time);
        }

        String status = order.isAcceptFlexibleTime() ?
               getActivity().getString(R.string.yes) : getActivity().getString(R.string.no);
        order_flex_time_text_view.setText(status);

        OrderAddressViewModel address = order.getOrderAddressViewModel();
        order_address_text_view.setText(buildAddressString(address));
        order_location_text_view.setText(address.getAddressDescription());
        Picasso.with(getActivity())
                .load(address.getLocationStaticMapImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(order_location_map);

        boolean isPendingOrderWithoutProvider = order.getOrderStatusId() == Enums.OrderStatusEnum.WaitingProviders.ordinal() ||
                order.getOrderStatusId() == Enums.OrderStatusEnum.PENDING.ordinal();

        boolean isCanceledWithoutProvider =
                order.getOrderStatusId() == Enums.OrderStatusEnum.CANCELED.ordinal() &&
                        StringUtils.isEmpty(order.getProviderUserId());

        if (isPendingOrderWithoutProvider || isCanceledWithoutProvider) {
            provider_name_text_view.setText(R.string.not_specified);
            provider_mobile_text_view.setText(R.string.provider_mobile_number_will_show_after_accepting);
            provider_mobile_text_view.setLinksClickable(false);
            provider_mobile_text_view.setCompoundDrawables(null, null, null, null);
        }
        else {
            provider_name_text_view.setText(order.getProviderName());
            provider_mobile_text_view.setText(order.getProviderMobile());
            provider_mobile_text_view.setLinksClickable(true);

            Picasso.with(getActivity())
                    .load(order.getProviderImage())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(provider_image);

            provider_name_text_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ProviderDetailsActivity.class);
                    intent.putExtra("userId", order.getProviderUserId());
                    startActivity(intent);
                }
            });

            provider_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ProviderDetailsActivity.class);
                    intent.putExtra("userId", order.getProviderUserId());
                    startActivity(intent);
                }
            });
        }

        addOrderImages(order.getOrderImages());

        addOrderItems(order.getOrderItems());

        BuildDynamicUIQuestionsHelper buildDynamicUI =
                new BuildDynamicUIQuestionsHelper(getActivity(), additional_empty_textview, dynamic_view);
        buildDynamicUI.showAnswers(order.getOrderAnswers());

        // Cancelling
        if (order.getOrderStatusId() == Enums.OrderStatusEnum.CANCELED.ordinal()) {
            cancel_view.setVisibility(View.VISIBLE);

            String byUser = "";
            if (order.getCustomerUserId().equalsIgnoreCase(order.getCancelByUserId()))
                byUser = "العميل";
            else if (!StringUtils.isEmpty(order.getProviderUserId()) &&
                    order.getProviderUserId().equalsIgnoreCase(order.getCancelByUserId()))
                byUser = "مقدم الخدمة";
            else
                byUser = "الدعم الفني";

            cancel_reason_text_view.setText(order.getCancelReason());
            close_reason_body.setText(order.getClosedBody());
            cancel_by_text_view.setText(byUser);
        } else {
            cancel_view.setVisibility(View.GONE);
        }

        order_location_map.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr=" + address.getLatitude() + "," + address.getLongitude()));
            startActivity(intent);
        });
    }


    private String buildAddressString(OrderAddressViewModel address) {
        String result = "";
        try {
            if (address == null)
                return result;

            District district = address.getDistrict();
            if (district != null) {
                result += address.getDistrict().getCityArabicName() + " - ";
                result += address.getDistrict().getArabicName();
            }

            FloorType floor = address.getFloorType();
            if (floor != null) {
                result += " - " +
                       getActivity().getString(R.string.floor_number)  + " :"  + address.getFloorType().getArabicName();
            }
        }
        catch(Exception e) {

        }
        return result;
    }

    private void addOrderImages(List<OrderImageViewModel> orderImages) {
        itemsAdapter = new OrderImagesAdapter(orderImages, view -> {
            OrderImageViewModel image = orderImages.get(itemsRecyclerView.getChildAdapterPosition(view));
            MediaActivityOpener.openViewActivity(getActivity(), image.getImagePath());
        });

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        itemsRecyclerView.setLayoutManager(mLayoutManager);
        itemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        itemsRecyclerView.setAdapter(itemsAdapter);

        if (orderImages != null && orderImages.size() == 0) {
            no_data_text_view.setVisibility(View.VISIBLE);
        } else {
            no_data_text_view.setVisibility(View.GONE);
        }
    }

    private void addOrderItems(List<OrderDetailViewModel> items) {
        if (items == null || items.size() == 0) {
            items_view.setVisibility(View.GONE);
            items_empty_textview.setVisibility(View.VISIBLE);
        }
        else {
            items_view.setVisibility(View.VISIBLE);
            items_empty_textview.setVisibility(View.GONE);

            for (OrderDetailViewModel item : items) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.item_order_detail, null);

                TextView item_title = (TextView) view.findViewById(R.id.item_title);
                TextView item_quantity = (TextView) view.findViewById(R.id.item_quantity);
                TextView item_price = (TextView) view.findViewById(R.id.item_price);

                item_title.setText(item.getItemName());
                item_quantity.setText(getActivity().getString(R.string.qountity) + item.getQuantity());
                item_price.setText(NumbersUtil.formatAmount(item.getPrice()));

                items_view.addView(view);
            }
        }
    }
}