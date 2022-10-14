package maksab.sd.customer.ui.orders.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.events.BottomSheetEvents;
import maksab.sd.customer.models.orders.details.OrderDetails;
import maksab.sd.customer.models.services.ServiceModel;
import maksab.sd.customer.storage.OrderInMemoryStorage;
import maksab.sd.customer.storage.OrderSummaryDetails;
import maksab.sd.customer.storage.OrderSummaryInMemoryStorage;
import maksab.sd.customer.util.general.NumbersUtil;
import me.himanshusoni.quantityview.QuantityView;

public class ProductDetailsBottomSheetDialog extends BottomSheetDialogFragment {

   private BottomSheetEvents bottomSheetEvents;
   private int quantity;

    static ProductDetailsBottomSheetDialog newInstance(ServiceModel serviceModel, boolean showingImage , int position , BottomSheetEvents bottomSheetEvents) {
        ProductDetailsBottomSheetDialog frg = new ProductDetailsBottomSheetDialog();
        Bundle args = new Bundle();
        args.putParcelable("service.model", serviceModel);
        args.putBoolean("showing_image", showingImage);
        args.putInt("position", position);
        frg.setArguments(args);
        frg.setOnCloseEvent(bottomSheetEvents);
        return frg;
    }

    @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return  dialog;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = View.inflate(getContext(), R.layout.product_details_bottom_sheet,  null);
        ServiceModel serviceModel = getArguments().getParcelable("service.model");
        boolean showingImage = getArguments().getBoolean("showing_image");

        initView(view , serviceModel,showingImage);
        dialog.setContentView(view);
    }

    void setOnCloseEvent(BottomSheetEvents bottomSheetEvents){
        this.bottomSheetEvents = bottomSheetEvents;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    private void initView(View view , ServiceModel serviceModel , boolean showImage){
        ImageView serviceImage = view.findViewById(R.id.service_image);
        TextView serviceTitle = view.findViewById(R.id.service_title);
        TextView servicePrice = view.findViewById(R.id.service_price);
        TextView serviceDescription = view.findViewById(R.id.service_description);
        QuantityView quantityView = view.findViewById(R.id.quantityView_default);
        Button addBtn = view.findViewById(R.id.add_service_btn);
        quantityView.setQuantity(serviceModel.getMinimumQuantity());
        quantity = serviceModel.getMinimumQuantity();
        quantityView.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {
                quantity = newQuantity;

                if(newQuantity > serviceModel.getMaximumQuantity()){
                    Toast.makeText(getContext(), "لا يمكنك الطلب اكثر من " + serviceModel.getMaximumQuantity() + "مرة في هذه الخدمة", Toast.LENGTH_SHORT).show();
                    quantityView.setQuantity(oldQuantity);
                    quantity = oldQuantity;
                    return;
                }

                if(newQuantity < serviceModel.getMinimumQuantity() && newQuantity !=0){
                    quantityView.setQuantity(0);
                    quantity = 0;
                    addBtn.setBackgroundColor(getResources().getColor(R.color.red));
                    addBtn.setText(R.string.remove_item);
                }

                if(newQuantity < serviceModel.getMinimumQuantity() && oldQuantity == 0){
                    quantityView.setQuantity(serviceModel.getMinimumQuantity());
                    quantity = serviceModel.getMinimumQuantity();
                    addBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    addBtn.setText(R.string.add_to_order);
                }

                if(newQuantity ==0){
                    addBtn.setBackgroundColor(getResources().getColor(R.color.red));
                    addBtn.setText(R.string.remove_item);
                }else{
                   if(oldQuantity == 0){
                       addBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                       addBtn.setText(R.string.add_to_order);
                   }
                }
            }

            @Override
            public void onLimitReached() {

            }
        });

        if(!showImage){
            serviceImage.setVisibility(View.GONE);
        }

        addBtn.setOnClickListener(view1 -> {
            OrderDetails orderDetailsItem = new OrderDetails();
            orderDetailsItem.setItemId(serviceModel.getId());
            orderDetailsItem.setQuantity(quantityView.getQuantity());
            orderDetailsItem.setSpecialityId(serviceModel.getSpecialtyId());
            orderDetailsItem.setServiceFor(serviceModel.getServiceFor());
            orderDetailsItem.setPrice(serviceModel.getPrice());

            quantity = quantityView.getQuantity();

            if(quantityView.getQuantity() == 0){
                OrderInMemoryStorage.deleteOrderItem(serviceModel.getId());
                OrderSummaryInMemoryStorage.deleteSelectedService(serviceModel.getId());
            }

            if(!isItemFound(OrderInMemoryStorage.getOrderItems() , orderDetailsItem)){
                OrderInMemoryStorage.addOrderItem(orderDetailsItem);
                OrderSummaryInMemoryStorage.addSelectedService(new OrderSummaryDetails(serviceModel.getName(), orderDetailsItem.getQuantity(), serviceModel.getPrice(),
                        serviceModel.getId()));
                this.bottomSheetEvents.onAddService(getArguments().getInt("position"), quantity);
                dismiss();
            }
            else{
               OrderInMemoryStorage.updateOrderDetailQuantity(orderDetailsItem.getItemId() , quantityView.getQuantity());
               OrderSummaryInMemoryStorage.updateSelectedServiceQuantity(orderDetailsItem.getItemId() , quantityView.getQuantity());
                this.bottomSheetEvents.onAddService(getArguments().getInt("position"), quantity);
                dismiss();
            }
        });

        if(!TextUtils.isEmpty(serviceModel.getImagePath())){
            Picasso.with(getContext()).load(serviceModel.getImagePath()).into(serviceImage);
        }

        serviceTitle.setText(serviceModel.getName());
        servicePrice.setText(NumbersUtil.formatAmount(serviceModel.getPrice()));
        serviceDescription.setText(serviceModel.getDescription());

        OrderDetails orderDetails = getItemIfFount(OrderInMemoryStorage.getOrderItems(), serviceModel.getId());
        if(orderDetails !=null){
            if(orderDetails.getQuantity()!=null && orderDetails.getQuantity() > 0){
                quantityView.setQuantity(orderDetails.getQuantity());
                addBtn.setText(R.string.update_qountity);
            }
        }
        else{
            addBtn.setText(R.string.add_to_order);
        }

    }

    private boolean isItemFound(List<OrderDetails> orderDetailList , OrderDetails orderDetail){
        for (OrderDetails item : orderDetailList) {
            if(item.getItemId() == orderDetail.getItemId()) {
                return  true;
            }
        }
        return  false;
    }

    private OrderDetails getItemIfFount(List<OrderDetails> orderDetailList , int id){
        for (OrderDetails item : orderDetailList) {
            if(item.getItemId() == id) {
                return  item;
            }
        }
        return  null;
    }

}
