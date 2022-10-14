package maksab.sd.customer.ui.orders.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.utility.OrderChatMessageTypeEnum;
import maksab.sd.customer.models.orders.chat.InvoiceMessage;
import maksab.sd.customer.models.orders.chat.LocationMessage;
import maksab.sd.customer.models.orders.chat.NormalMessage;
import maksab.sd.customer.models.orders.chat.OfferMessage;
import maksab.sd.customer.models.orders.chat.OrderChatModel;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class OrderChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OrderChatModel> orderChatModels;

    public OrderChatAdapter(List<OrderChatModel> orderChatModels){
        this.orderChatModels = orderChatModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == OrderChatMessageTypeEnum.LeftText.ordinal()){
            return  new LeftTextMessageViewHolder(layoutInflater.inflate(R.layout.order_chat_left_text , parent , false));
        }else if(viewType == OrderChatMessageTypeEnum.RightText.ordinal()){
            return new RightTextMessageViewHolder(layoutInflater.inflate(R.layout.order_chat_right_text , parent , false));
        }else if(viewType == OrderChatMessageTypeEnum.LeftImage.ordinal()){
            return new LeftImageMessageViewHolder(layoutInflater.inflate(R.layout.order_chat_left_image , parent , false));
        }else if(viewType == OrderChatMessageTypeEnum.RightImage.ordinal()){
            return new RightImageMessageViewHolder(layoutInflater.inflate(R.layout.order_chat_right_image , parent , false));
        }else if(viewType == OrderChatMessageTypeEnum.Offer.ordinal()){
            return new OfferViewHolder(layoutInflater.inflate(R.layout.order_chat_offer , parent , false));
        }else if(viewType == OrderChatMessageTypeEnum.Invoice.ordinal()){

        }else if(viewType == OrderChatMessageTypeEnum.Location.ordinal()){
            return new LocationViewHolder(layoutInflater.inflate(R.layout.order_chat_location , parent , false));
        }
      return null ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderChatModel orderChatModel = orderChatModels.get(position);
        if(orderChatModel.getOrderChatMessageTypeEnum() == OrderChatMessageTypeEnum.LeftText){

            LeftTextMessageViewHolder leftTextMessageViewHolder = (LeftTextMessageViewHolder) holder;
            NormalMessage normalMessage = orderChatModel.getNormalMessage();
            leftTextMessageViewHolder.message.setText(normalMessage.getBody());
            leftTextMessageViewHolder.time.setText(normalMessage.getTime());

        }else if(orderChatModel.getOrderChatMessageTypeEnum() == OrderChatMessageTypeEnum.RightText){

            RightTextMessageViewHolder rightTextMessageViewHolder = (RightTextMessageViewHolder) holder;
            NormalMessage normalMessage = orderChatModel.getNormalMessage();
            rightTextMessageViewHolder.message.setText(normalMessage.getBody());
            rightTextMessageViewHolder.time.setText(normalMessage.getTime());

        }else if(orderChatModel.getOrderChatMessageTypeEnum() == OrderChatMessageTypeEnum.LeftImage){

            LeftImageMessageViewHolder leftImageMessageViewHolder = (LeftImageMessageViewHolder) holder;
            NormalMessage normalMessage = orderChatModel.getNormalMessage();
            leftImageMessageViewHolder.time.setText(normalMessage.getTime());
            Picasso.with(leftImageMessageViewHolder.image.getContext()).load(normalMessage.getBody()).placeholder(R.drawable.placeholder).into(leftImageMessageViewHolder.image);

        }else if(orderChatModel.getOrderChatMessageTypeEnum() == OrderChatMessageTypeEnum.RightImage){

            RightImageMessageViewHolder rightImageMessageViewHolder = (RightImageMessageViewHolder) holder;
            NormalMessage normalMessage = orderChatModel.getNormalMessage();
            Picasso.with(rightImageMessageViewHolder.image.getContext()).load(normalMessage.getBody()).placeholder(R.drawable.placeholder).into(rightImageMessageViewHolder.image);


        }else if(orderChatModel.getOrderChatMessageTypeEnum() == OrderChatMessageTypeEnum.Offer){

            OfferViewHolder offerViewHolder = (OfferViewHolder) holder;
            OfferMessage offerMessage = orderChatModel.getOfferMessage();
            offerViewHolder.provider_name.setText(offerMessage.getProviderName());
            Picasso.with(offerViewHolder.provider_profile_image.getContext()).load(offerMessage.getProviderImageUrl()).placeholder(R.drawable.placeholder)
                    .into(offerViewHolder.provider_profile_image);
            offerViewHolder.provider_rate.setRating(offerMessage.getProviderRate());
            offerViewHolder.provider_rate_count.setText("4");
            offerViewHolder.offer_price.setText(offerMessage.getPrice()+"");
            offerViewHolder.quotation_service_type.setText(offerMessage.getOrderTypeName());
            offerViewHolder.provider_distance.setText(offerMessage.getProviderDistance()+"");
            offerViewHolder.body.setText(offerMessage.getBody());

        }else if(orderChatModel.getOrderChatMessageTypeEnum() == OrderChatMessageTypeEnum.Invoice){



        }else if(orderChatModel.getOrderChatMessageTypeEnum() == OrderChatMessageTypeEnum.Location){

            LocationViewHolder locationViewHolder = (LocationViewHolder) holder;
            LocationMessage locationMessage = orderChatModel.getLocationMessage();
            locationViewHolder.location_text.setText(locationMessage.getAddress());
            Picasso.with(locationViewHolder.map_image.getContext()).load(locationMessage.getImagePath()).placeholder(R.drawable.placeholder).into(locationViewHolder.map_image);
        }
    }

    @Override
    public int getItemCount() {
        return orderChatModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return orderChatModels.get(position).getOrderChatMessageTypeEnum().ordinal();
    }

    class OfferViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.provider_name)
        TextView provider_name;
        @BindView(R.id.provider_profile_image)
        ShapeableImageView provider_profile_image;

        @BindView(R.id.provider_rate)
        MaterialRatingBar provider_rate;

        @BindView(R.id.provider_rate_count)
        TextView provider_rate_count;

        @BindView(R.id.offer_price)
        TextView offer_price;

        @BindView(R.id.quotation_service_type)
        TextView quotation_service_type;

        @BindView(R.id.provider_distance)
        TextView provider_distance;

        @BindView(R.id.accept_offer)
        TextView accept_offer;

        @BindView(R.id.body)
        TextView body;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.location_text)
        TextView location_text;

        @BindView(R.id.map_image)
        ImageView map_image;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class LeftTextMessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.message)
        TextView message;

        @BindView(R.id.time)
        TextView time;

        public LeftTextMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class RightTextMessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.message)
        TextView message;

        @BindView(R.id.time)
        TextView time;

        public RightTextMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class RightImageMessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.time)
        TextView time;

        public RightImageMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class LeftImageMessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.time)
        TextView time;

        public LeftImageMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
