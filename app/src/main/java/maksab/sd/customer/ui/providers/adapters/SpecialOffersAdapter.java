package maksab.sd.customer.ui.providers.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.orders.details.HomeSpecialOfferModel;

public class SpecialOffersAdapter extends RecyclerView.Adapter<SpecialOffersAdapter.SpecialOffersViewHolder> {

    private List<HomeSpecialOfferModel> specialOffersModels;

    public SpecialOffersAdapter(List<HomeSpecialOfferModel> specialOffersModels){
        this.specialOffersModels = specialOffersModels;
    }

    @NonNull
    @Override
    public SpecialOffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SpecialOffersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.special_offers_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialOffersViewHolder holder, int position) {
        HomeSpecialOfferModel specialOffersModel = specialOffersModels.get(position);
        holder.body.setText(specialOffersModel.getDescription());
        holder.offer_title.setText(specialOffersModel.getName());
        holder.price.setText(specialOffersModel.getPrice() + "");
        holder.end_on.setText(specialOffersModel.getToTime());
    }

    @Override
    public int getItemCount() {
        return specialOffersModels.size();
    }

    class SpecialOffersViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.offer_title)
        TextView offer_title;

        @BindView(R.id.body)
        TextView body;

        @BindView(R.id.price)
        TextView price;

        @BindView(R.id.end_on)
        TextView end_on;

        public SpecialOffersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
