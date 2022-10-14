package maksab.sd.customer.ui.providers.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.providers.ProviderDetailsModel;


/**
 * Created by AdminUser on 10/27/2017.
 */

public class ProviderListAdapter extends RecyclerView.Adapter<ProviderListAdapter.ProvidersListViewHolder> {

    List<ProviderDetailsModel> _models;

    Context _context;

    private boolean isFavorite ;

    View.OnClickListener _oncardClick;

    public ProviderListAdapter(List<ProviderDetailsModel> providersListModels , Context context , View.OnClickListener listener , boolean isfavorite){

        _models = providersListModels;
        _context = context;
        _oncardClick = listener;
        isFavorite = isfavorite;
    }

    @Override
    public ProvidersListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ProvidersListViewHolder holder = new ProvidersListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_provider, parent , false));
        if (isFavorite){
            holder.locationIcon.setVisibility(View.GONE);
            holder.profiledistance.setVisibility(View.GONE);
        }
        holder.row.setOnClickListener(_oncardClick);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProvidersListViewHolder holder, int position) {

        ProviderDetailsModel  model = _models.get(position);

        Picasso.with(_context).load(model.getProfileImage()).placeholder(R.drawable.placeholder).into(holder.profileimage);

        holder.providername.setText(model.getFullName());
        if (model.getBio() != null){
            if(!TextUtils.isEmpty(model.getBio().trim())){
                holder.providerbio.setText(model.getBio());
            }else{
                holder.providerbio.setText("لا يوجد");
            }
        }

        holder.profilerate.setRating(model.getRate());
        NumberFormat nf = NumberFormat.getInstance(new Locale("en","US"));
        holder.profiledistance.setText(String.format("%s ك", nf.format(Math.round(model.getDistanceFromUser()))));
         holder.locationIcon.setImageDrawable( VectorDrawableCompat.create( _context.getResources() , R.drawable.ic_location , null) );
    }

    @Override
    public int getItemCount() {
        return _models.size();
    }

    class ProvidersListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.provider_profile_image)
        ImageView profileimage;
        @BindView(R.id.providername)

        TextView providername;
        @BindView(R.id.providerbio)
        TextView providerbio;
        @BindView(R.id.providerRate)
        RatingBar profilerate;
        @BindView(R.id.providerdistance)
        TextView profiledistance;
        TextView servicePrice;
        @BindView(R.id.locationIcon)
        ImageView locationIcon;

        View row;

        public ProvidersListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
            row = itemView;
        }
    }
}


