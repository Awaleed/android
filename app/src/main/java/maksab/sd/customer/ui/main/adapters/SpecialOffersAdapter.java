package maksab.sd.customer.ui.main.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.orders.details.HomeSpecialOfferModel;
import maksab.sd.customer.util.general.NumbersUtil;

public class SpecialOffersAdapter extends RecyclerView.Adapter<SpecialOffersAdapter.SpecialOffersViewHolder> {

    private List<HomeSpecialOfferModel> homeSpecialOfferModels;
    View.OnClickListener onClickListener;
    private boolean isProviderOffers;

    public SpecialOffersAdapter(List<HomeSpecialOfferModel> homeSpecialOfferModels, View.OnClickListener onClickListener , boolean isProviderOffers) {
        this.homeSpecialOfferModels = homeSpecialOfferModels;
        this.onClickListener = onClickListener;
        this.isProviderOffers = isProviderOffers;
    }

    @NonNull
    @Override
    public SpecialOffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SpecialOffersViewHolder specialOffersViewHolder = new SpecialOffersViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_special_offer, parent , false));
        specialOffersViewHolder.itemView.setOnClickListener(onClickListener);
        if(isProviderOffers){
            ViewGroup.LayoutParams layoutParams =specialOffersViewHolder.container_card.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            specialOffersViewHolder.container_card.requestLayout();
        }
        return specialOffersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialOffersViewHolder holder, int position) {
        HomeSpecialOfferModel homeSpecialOfferModel = homeSpecialOfferModels.get(position);
        holder.offer_title.setText(homeSpecialOfferModel.getName());
        holder.old_price.setText(NumbersUtil.formatAmount(homeSpecialOfferModel.getOriginalPrice()));
        holder.old_price.setPaintFlags(holder.old_price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        holder.new_price.setText(NumbersUtil.formatAmount(homeSpecialOfferModel.getPrice()));
        holder.offer_description.setText(homeSpecialOfferModel.getDescription());
        holder.offer_start_on.setText("يبدأ في : " + homeSpecialOfferModel.getFromTimeString());
        holder.offer_end_on.setText("ينتهي في : " + homeSpecialOfferModel.getToTimeString());
        Picasso.with(holder.offer_image.getContext()).load(homeSpecialOfferModel.getOfferImage()).into(holder.offer_image);
    }

    @Override
    public int getItemCount() {
        return homeSpecialOfferModels.size();
    }


    class SpecialOffersViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.offer_title)
        TextView offer_title;
        @BindView(R.id.offer_image)
        ImageView offer_image;
        @BindView(R.id.old_price)
        TextView old_price;
        @BindView(R.id.new_price)
        TextView new_price;
        @BindView(R.id.offer_description)
        TextView offer_description;
        @BindView(R.id.offer_start_on)
        TextView offer_start_on;
        @BindView(R.id.offer_end_on)
        TextView offer_end_on;
        @BindView(R.id.container_card)
        CardView container_card;

        public SpecialOffersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
