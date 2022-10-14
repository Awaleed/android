package maksab.sd.customer.ui.providers.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.providers.ProviderDetailsModel;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.StoreListViewHolder> {

    List<ProviderDetailsModel> _stores;
    View.OnClickListener onClickListener;
    public StoreListAdapter(List<ProviderDetailsModel> providerDetailsModels , View.OnClickListener onClickListener){
        _stores = providerDetailsModels;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public StoreListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StoreListViewHolder holder = new StoreListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item, parent , false));
        holder.itemView.setOnClickListener(onClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreListViewHolder holder, int position) {
        ProviderDetailsModel providerDetailsModel = _stores.get(position);
        holder.store_name.setText(providerDetailsModel.getFullName());
        holder.distance_in_kms.setText(providerDetailsModel.getDistanceFromUser()+"");
        holder.store_rate.setText(providerDetailsModel.getRate()+"");
        holder.store_description.setText(providerDetailsModel.getBio());
        Picasso.with(holder.store_image.getContext()).load(providerDetailsModel.getProfileImage()).placeholder(R.drawable.placeholder).into(holder.store_image);
    }

    @Override
    public int getItemCount() {
        return _stores.size();
    }

    class StoreListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.store_image)
        ImageView store_image;

        @BindView(R.id.store_name)
        TextView store_name;

        @BindView(R.id.distance_in_kms)
        TextView distance_in_kms;

        @BindView(R.id.store_rate)
        TextView store_rate;

        @BindView(R.id.store_description)
        TextView store_description;

        public StoreListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
