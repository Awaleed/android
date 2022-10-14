package maksab.sd.customer.wizards.neworder.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.constant.ImageProvider;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.fragments.FragmentsContract;
import maksab.sd.customer.basecode.fragments.MessageDialog;
import maksab.sd.customer.models.orders.details.OrderDetails;
import maksab.sd.customer.models.orders.details.OrderImageViewModel;
import maksab.sd.customer.storage.OrderInMemoryStorage;
import maksab.sd.customer.storage.OrderSummaryDetails;
import maksab.sd.customer.storage.OrderSummaryInMemoryStorage;
import maksab.sd.customer.ui.general.activities.AddressListActivity;
import maksab.sd.customer.ui.lookups.activities.SelectCouponActivity;
import maksab.sd.customer.ui.lookups.activities.SelectDateTimeActivity;
import maksab.sd.customer.ui.orders.adapters.OrderImagesAdapter;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.AddressInMemoryStorage;
import maksab.sd.customer.util.general.NumbersUtil;
import maksab.sd.customer.util.general.StringUtils;
import maksab.sd.customer.viewmodels.quotations.AddQuotationsViewModel;

public class OrderInputFragment extends Fragment implements FragmentsContract {
    @BindView(R.id.quotation_description)
    TextInputEditText quotation_decription;
    @BindView(R.id.order_location_input)
    AutoCompleteTextView order_location_input;
    @BindView(R.id.coupon)
    AutoCompleteTextView coupon;
    @BindView(R.id.coupon_textview)
    TextView coupon_textview;
    @BindView(R.id.order_time_input)
    AutoCompleteTextView order_time_input;

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private OrderImagesAdapter orderImagesAdapter;
    private AddQuotationsViewModel quotationsViewModel;
    private List<OrderImageViewModel> orderImages = new ArrayList<>();

    private int specialtyExecuationModel;
    private String providerId;
    private int specialityId;
    private boolean isOffer = false;
    private int offerId;
    private static final int SELECT_TIME_RESULT = 100;
    private static final int SELECT_COUPON = 80;
    private static final int SELECT_ADDRESS = 910;
    private static final int SELECT_IMAGES = 12000;
    private ProgressDialog progressDialog;

    public static OrderInputFragment newInstance(String providerId, int specialtyExecuationModel, int specialityId, boolean isOffer, int offerId) {
        OrderInputFragment fragment = new OrderInputFragment();
        Bundle args = new Bundle();
        args.putString("providerId", providerId);
        args.putInt("specialtyExecuationModel", specialtyExecuationModel);
        args.putInt("specialityId", specialityId);
        args.putBoolean("isOffer", isOffer);
        args.putInt("offer.id", offerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_input, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        ButterKnife.bind(this, view);
        providerId = getArguments().getString("providerId");
        specialtyExecuationModel = getArguments().getInt("specialtyExecuationModel", 0);
        specialityId = getArguments().getInt("specialityId", 0);
        isOffer = getArguments().getBoolean("isOffer");
        offerId = getArguments().getInt("offer.id");
        quotationsViewModel = ViewModelProviders.of(this).get(AddQuotationsViewModel.class);
        addAddressData();
        initProgressbarDialog();
        imageObserver();
        setupRecyclerview(view);
    }

    void setupRecyclerview(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(getContext(), 3);
        orderImagesAdapter = new OrderImagesAdapter(orderImages , view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.want_to_delete_image);
            builder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                orderImages.remove(recyclerView.getChildAdapterPosition(view1));
                orderImagesAdapter.notifyDataSetChanged();
            });
            builder.setNegativeButton(R.string.action_cancel, (dialog, which) -> dialog.cancel());

            builder.show();
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(orderImagesAdapter);
    }

    @OnClick(R.id.images_picker)
    void selectImages() {
        ImagePicker.with(this).crop().compress(150).provider(ImageProvider.BOTH).start(SELECT_IMAGES);
    }

    @OnClick(R.id.order_time_input)
    void onSelectTimeClicked() {
        Intent intent = new Intent(getActivity(), SelectDateTimeActivity.class);
        intent.putExtra("speciality.id" , specialityId);
        startActivityForResult(intent, SELECT_TIME_RESULT);
    }

    @OnClick(R.id.coupon)
    void onSelectCoupon() {
       if(!isOffer){
           Intent intent = new Intent(getActivity(), SelectCouponActivity.class);
           intent.putExtra("speciality.id", specialityId);
           startActivityForResult(intent, SELECT_COUPON);
       }else{
           MessageDialog.showMessageDialog((BaseActivity) getActivity() , getString(R.string.maksab_app), getString(R.string.you_cannot_use_copoun_with_offers));
       }
    }

    @OnClick(R.id.order_location_input)
    void onSelectAddress(){
        Intent intent = new Intent(getActivity() , AddressListActivity.class);
        intent.putExtra("isForSelectAddress" , true);
        startActivityForResult(intent , SELECT_ADDRESS);
    }

    private boolean isInputsValidated() {
        if (TextUtils.isEmpty(quotation_decription.getText())) {
            Toast.makeText(getContext(), getString(R.string.fill_description_first_error), Toast.LENGTH_LONG).show();
            return false;
        }

        if(AddressInMemoryStorage.id == 0 || TextUtils.isEmpty(AddressInMemoryStorage.district)){
            Toast.makeText(getContext(), getString(R.string.select_address_first), Toast.LENGTH_LONG).show();
            return false;
        }
        String time = OrderInMemoryStorage.getOrderInputs().getSelectedTime();
        if(time == null || TextUtils.isEmpty(time)){
            Toast.makeText(getContext(), getString(R.string.fill_time_error), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    void initProgressbarDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setMessage(getString(R.string.uploading_images));
    }

    private void imageObserver() {
        quotationsViewModel.imageObserver().observe(getViewLifecycleOwner(), path -> {
            progressDialog.incrementProgressBy(100);
            progressDialog.dismiss();
            orderImages.add(new OrderImageViewModel(path));
            orderImagesAdapter.notifyDataSetChanged();
        });
    }

    private int getOrderTypeId() {
        boolean havePrices = checkIfServicesHavePrice();

        if (isOffer && havePrices) {
            if (StringUtils.isEmpty(providerId))
                return Enums.OrderTypeEnum.MaksabOffer.ordinal();
            else
                return Enums.OrderTypeEnum.ProviderOffer.ordinal();
        }

        if (specialtyExecuationModel == Enums.SpecialtyExecuationModelEnum.Quotation.ordinal() || !havePrices) {
            return Enums.OrderTypeEnum.Quotation.ordinal();
        }
        else if (specialtyExecuationModel == Enums.SpecialtyExecuationModelEnum.MaksabServices.ordinal() && havePrices) {
            return Enums.OrderTypeEnum.MaksabPricedService.ordinal();
        }
        else if (specialtyExecuationModel == Enums.SpecialtyExecuationModelEnum.ProviderServices.ordinal() && havePrices) {
            return Enums.OrderTypeEnum.ProviderPricedService.ordinal();
        }

        return Enums.OrderTypeEnum.Quotation.ordinal();
    }

    private boolean checkIfServicesHavePrice() {
        List<OrderSummaryDetails> ordersOrderDetails = OrderSummaryInMemoryStorage.getSelectedServices();
        if(ordersOrderDetails.isEmpty())
            return false;

        for (int i = 0; i < ordersOrderDetails.size(); i++) {
            if(ordersOrderDetails.get(i).getPrice() >0)
                return true;
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_TIME_RESULT && resultCode == RESULT_OK) {
            String selectedTime = data.getStringExtra("selected_time");
            String selectedDate = data.getStringExtra("selected_date");
            boolean acceptFlexibleTime = data.getBooleanExtra("accept_flexible_times", true);
            OrderInMemoryStorage.setSelectedTime(selectedTime, selectedDate, acceptFlexibleTime);

            String result = selectedDate;
            order_time_input.setText(R.string.time_selected);
        }

        if (requestCode == SELECT_COUPON && resultCode == RESULT_OK) {
            String couponCode = data.getStringExtra("coupon");
            double amount = data.getDoubleExtra("coupon_amount", 0);
            coupon.setText(couponCode);
            OrderSummaryInMemoryStorage.addCouponData(amount , couponCode);
            String msg = getString(R.string.coupon_discount) + " : " + NumbersUtil.formatAmount(amount);
            coupon_textview.setVisibility(View.VISIBLE);
            coupon_textview.setText(msg);
            OrderInMemoryStorage.setCoupon(data.getStringExtra("coupon"));
        }

        if (requestCode == SELECT_ADDRESS && resultCode == RESULT_OK) {
            AddressInMemoryStorage.id = data.getIntExtra("selected.address.id" , 0);
            AddressInMemoryStorage.district = data.getStringExtra("selected.address.district");
            AddressInMemoryStorage.body = data.getStringExtra("selected.address.body");
            OrderInMemoryStorage.setAddressId(AddressInMemoryStorage.id);
            order_location_input.setText(AddressInMemoryStorage.district);
        }

        if(requestCode == SELECT_IMAGES && resultCode == RESULT_OK){
            progressDialog.show();
            File image = new File(data.getData().getPath());
            quotationsViewModel.uploadImage(image);
        }
    }

    private void addAddressData() {
        if(AddressInMemoryStorage.id > 0){
            order_location_input.setText(AddressInMemoryStorage.district);
            order_location_input.setEnabled(false);
            order_location_input.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.gray));
        }
        OrderSummaryInMemoryStorage.addAddress(AddressInMemoryStorage.body);
        OrderInMemoryStorage.setAddressId(AddressInMemoryStorage.id);
    }

    @Override
    public boolean isValidForm() {
        return isInputsValidated();
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public String getStepTitle(Context context) {
        return getString(R.string.complete_order_inputs);
    }

    @Override
    public void saveChange() {
        if (isOffer) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setItemId(offerId);
            orderDetails.setQuantity(OrderSummaryInMemoryStorage.offer_quantity);
            OrderInMemoryStorage.addOrderItem(orderDetails);
        }
        OrderInMemoryStorage.addBasicInputs(specialityId, getOrderTypeId(), providerId, quotation_decription.getText().toString(), getOrderImages());
    }

    private List<String> getOrderImages(){
        List<String> images = new ArrayList<>();
        for (OrderImageViewModel orderImages :orderImages) {
            images.add(orderImages.getImagePath());
        }
        return images;
    }
}