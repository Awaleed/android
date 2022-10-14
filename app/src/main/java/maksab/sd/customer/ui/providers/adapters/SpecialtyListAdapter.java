package maksab.sd.customer.ui.providers.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.events.RecyclerViewOnClick;
import maksab.sd.customer.models.providers.ProviderSpecialtyModel;
import maksab.sd.customer.util.constants.Enums;

/**
 * Created by AdminUser on 10/20/2017.
 */

public class SpecialtyListAdapter extends RecyclerView.Adapter<SpecialtyListAdapter.SpecialtyListViewHolder> {
    private List<ProviderSpecialtyModel> mProviderSpecialtyList;
    private RecyclerViewOnClick recyclerViewOnClick;

    public SpecialtyListAdapter(List<ProviderSpecialtyModel> addressModels , RecyclerViewOnClick recyclerViewOnClick) {
        this.mProviderSpecialtyList = addressModels;
        this.recyclerViewOnClick = recyclerViewOnClick;
    }

    public SpecialtyListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SpecialtyListViewHolder itemViewHolder =
                new SpecialtyListViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.specialty_row, parent, false));
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(SpecialtyListViewHolder holder, int position) {
        ProviderSpecialtyModel item = mProviderSpecialtyList.get(position);

        holder.item_title.setText(item.getSpecialtyArabic());
        //holder.item_body.setText(getSpecialtyTypeEnumString(item.getSpecialtyType()));

        Picasso.with(holder.item_logo.getContext())
                .load(item.getImagePath())
                .placeholder(R.drawable.placeholder)
                .into(holder.item_logo);

        holder.send_quotation.setOnClickListener(view -> {
            recyclerViewOnClick.onClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return mProviderSpecialtyList.size();
    }


    class SpecialtyListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title)
        TextView item_title;
        @BindView(R.id.item_logo)
        ShapeableImageView item_logo;
        @BindView(R.id.send_quotation)
        Button send_quotation;


        public SpecialtyListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}



