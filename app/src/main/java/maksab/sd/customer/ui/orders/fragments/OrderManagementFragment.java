package maksab.sd.customer.ui.orders.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.orders.details.OrderStatusUpdateModel;
import maksab.sd.customer.models.providers.OrderModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.tickets.activities.AddTicketWizardActivity;
import maksab.sd.customer.util.constants.Enums;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderManagementFragment extends Fragment {
    @BindView(R.id.have_problem_button)
    Button have_problem_button;
    @BindView(R.id.end_order_button)
    Button end_order_button;
    @BindView(R.id.change_provider_button)
    Button change_provider_button;
    @BindView(R.id.change_provider_view)
    View change_provider_view;
    @BindView(R.id.cancel_order_view)
    View cancel_order_view;

    ProgressDialog progressDialog;

    public static OrderManagementFragment newInstance(long orderId) {
        OrderManagementFragment fragment = new OrderManagementFragment();
        Bundle args = new Bundle();
        args.putLong("order.id" , orderId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_order_managment, container, false);
        ButterKnife.bind(this , view);
        long orderId = getArguments().getLong("order.id");
        getOrderById(orderId);
        progressDialog = new ProgressDialog(getContext());
        return view;
    }

    private void getOrderById(long orderId){
        SharedPreferencesStorage localStorage = new SharedPreferencesStorage(getActivity());
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        GetWayServiceGenerator.createService(ICustomersService.class, token)
            .getorderbyId(orderId)
            .enqueue(new Callback<OrderModel>() {
                @Override
                public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                    if (response.isSuccessful()) {
                        showOrderDetail(response.body());
                    }
                }

                @Override
                public void onFailure(Call<OrderModel> call, Throwable t) {
                    Toast.makeText(getActivity(), getActivity().getResources().getText(R.string.internetError), Toast.LENGTH_LONG).show();
                    t.printStackTrace();
                }
            });
    }

    private void showOrderDetail(OrderModel order) {
        if (order.getOrderStatusId() == Enums.OrderStatusEnum.FINISHED.ordinal() ||
            order.getOrderStatusId() == Enums.OrderStatusEnum.CANCELED.ordinal()) {
            cancel_order_view.setVisibility(View.GONE);
            change_provider_view.setVisibility(View.GONE);
        }
        else {
            cancel_order_view.setVisibility(View.VISIBLE);

            boolean isQuotationBased = order.getOrderTypeId() == Enums.OrderTypeEnum.Quotation.ordinal() ||
                    order.getOrderTypeId() == Enums.OrderTypeEnum.MaksabPricedService.ordinal() ||
                    order.getOrderTypeId() == Enums.OrderTypeEnum.MaksabOffer.ordinal();

            boolean isOpening = order.getOrderStatusId() == Enums.OrderStatusEnum.Opening.ordinal();

            if (isQuotationBased && isOpening) {
                change_provider_view.setVisibility(View.VISIBLE);
            }
            else {
                change_provider_view.setVisibility(View.GONE);
            }
        }

        end_order_button.setOnClickListener(view1 -> {
            showCancelReasonDialog(order.getId());
        });

        have_problem_button.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), AddTicketWizardActivity.class);
            startActivity(intent);
        });

        change_provider_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeProviderDialog(order.getId());
            }
        });
    }

    public void updateOrderStatus(long orderId, OrderStatusUpdateModel model) {
        progressDialog.show(getContext() , "" , getString(R.string.loading_please_wait),true);
        ILocalStorage localStorage = new SharedPreferencesStorage(getContext());
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , "bearer " + localStorage.getJwtToken().getStringToken());
        customersService.changeOrderStatus(orderId, model)
                .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void showCancelReasonDialog(long orderId) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_cancel, null);
        dialogBuilder.setView(dialogView);

        final EditText body = dialogView.findViewById(R.id.body);
        final RadioButton try_app = dialogView.findViewById(R.id.try_app);
        final RadioButton no_response = dialogView.findViewById(R.id.no_response);
        final RadioButton another_reson = dialogView.findViewById(R.id.another_reson);
        final RadioButton change_my_mind = dialogView.findViewById(R.id.change_my_mind);
        final RadioButton found_provider = dialogView.findViewById(R.id.found_provider);
        another_reson.setChecked(true);

        dialogBuilder.setTitle(R.string.cancel_order);
        dialogBuilder.setMessage(R.string.cancel_order_reson);
        dialogBuilder.setPositiveButton( R.string.cancel_order, (dialog, whichButton) -> {
        });
        dialogBuilder.setNegativeButton(R.string.backward, (dialog, whichButton) -> {
        });

        AlertDialog b = dialogBuilder.create();
        b.show();

        b.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(v -> {
            b.dismiss();
        });

        b.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String cancel_reason = "";
            String body_message = body.getText().toString();

            if (another_reson.isChecked()){
                if (!TextUtils.isEmpty(body_message)){
                    cancel_reason = body.getText().toString();
                } else {
                    Toast.makeText(getContext() , getString(R.string.fill_cancel_reson_error), Toast.LENGTH_LONG).show();
                    return;
                }
            }

            if (try_app.isChecked()){
                cancel_reason = getString(R.string.try_app);
            }

            if (no_response.isChecked()) {
                cancel_reason = getString(R.string.no_response_to_order);
            }

            if(change_my_mind.isChecked()){
                cancel_reason = getString(R.string.change_my_mind);
            }

            if(found_provider.isChecked()){
                cancel_reason = getString(R.string.found_provider_outside);
            }

            OrderStatusUpdateModel model = OrderStatusUpdateModel.cancelOrder(
                    Enums.OrderUpdateTypeEnum.UpdateOrderStatus.ordinal(),
                    Enums.OrderStatusEnum.CANCELED.ordinal(), cancel_reason, body_message);
            updateOrderStatus(orderId, model);
            b.dismiss();
        });
    }


    public void showChangeProviderDialog(long orderId) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_change_provider, null);
        dialogBuilder.setView(dialogView);

        final EditText body = dialogView.findViewById(R.id.body);
        final RadioButton provider_not_answer = dialogView.findViewById(R.id.provider_not_answer);
        final RadioButton provider_high_priced = dialogView.findViewById(R.id.provider_high_priced);
        final RadioButton change_my_mind = dialogView.findViewById(R.id.change_my_mind);
        final RadioButton dont_like_gallery = dialogView.findViewById(R.id.dont_like_gallery);
        final RadioButton another_reson = dialogView.findViewById(R.id.another_reson);
        another_reson.setChecked(true);

        dialogBuilder.setTitle(R.string.change_provider);
        dialogBuilder.setMessage(R.string.why_need_change_provider);
        dialogBuilder.setPositiveButton( R.string.change_provider, (dialog, whichButton) -> {
        });
        dialogBuilder.setNegativeButton(R.string.backward, (dialog, whichButton) -> {
        });

        AlertDialog b = dialogBuilder.create();
        b.show();

        b.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(v -> {
            b.dismiss();
        });

        b.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String cancel_reason = "";
            String body_message = body.getText().toString();

            if (another_reson.isChecked()){
                if (!TextUtils.isEmpty(body_message)){
                    cancel_reason = body.getText().toString();
                } else {
                    Toast.makeText(getContext() , getString(R.string.fill_cancel_reson_error), Toast.LENGTH_LONG).show();
                    return;
                }
            }

            if (provider_not_answer.isChecked()){
                cancel_reason = getString(R.string.provider_not_answered);
            }

            if (provider_high_priced.isChecked()) {
                cancel_reason = getString(R.string.provider_high_priced);
            }

            if(change_my_mind.isChecked()){
                cancel_reason = getString(R.string.change_my_mind);
            }

            if(dont_like_gallery.isChecked()){
                cancel_reason = getString(R.string.dont_like_gallery);
            }

            OrderStatusUpdateModel model = OrderStatusUpdateModel.changeOrderStatus(
                    Enums.OrderUpdateTypeEnum.UpdateOrderStatus.ordinal(),
                    Enums.OrderStatusEnum.WaitingProviders.ordinal());
            updateOrderStatus(orderId, model);

            b.dismiss();
        });
    }
}