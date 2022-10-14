package maksab.sd.customer.ui.orders.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.models.services.ServiceModel;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.NumbersUtil;
import maksab.sd.customer.util.general.StringUtils;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder>{
    private List<ServiceModel> itemsModels;
    private View.OnClickListener clickEventHandler;

    public ServicesAdapter(List<ServiceModel> itemsModels, View.OnClickListener clickEventHandler){
        this.itemsModels = itemsModels;
        this.clickEventHandler = clickEventHandler;
    }

    @Override
    public ServicesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ServicesViewHolder viewHolder = new ServicesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service, parent , false));
        viewHolder.itemView.setOnClickListener(clickEventHandler);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(ServicesViewHolder viewHolder, int position) {
        ServiceModel serviceModel = itemsModels.get(position);
        viewHolder.name.setText(serviceModel.getName());
        viewHolder.note.setText(serviceModel.getDescription());

        if(serviceModel.getQuantity() == null || serviceModel.getQuantity() == 0){
            viewHolder.quantity.setVisibility(View.GONE);
        }
        else {
            viewHolder.quantity.setVisibility(View.VISIBLE);
            viewHolder.quantity.setText("" + serviceModel.getQuantity());
        }

        if (serviceModel.getServiceType() == Enums.ServiceTypeEnum.Priced.ordinal())
            viewHolder.price.setText(NumbersUtil.formatAmount(serviceModel.getPrice()));
        else
            viewHolder.price.setText(viewHolder.price.getContext().getString(R.string.price_label) + " " +
                    viewHolder.price.getContext().getString(R.string.not_specified));

        if (!StringUtils.isEmpty(serviceModel.getImagePath())) {
            Picasso.with(viewHolder.service_image.getContext())
                    .load(serviceModel.getImagePath())
                    .placeholder(R.drawable.logo_gray)
                    .into(viewHolder.service_image);
        }
        else {
            Picasso.with(viewHolder.service_image.getContext())
                    .load(R.drawable.logo_gray)
                    .into(viewHolder.service_image);
        }
    }

    @Override
    public int getItemCount() {
        return itemsModels.size();
    }

    class ServicesViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        TextView sdg;
        TextView note;
        TextView quantity;
        LinearLayout clicked_row;
        ImageView service_image;

        public ServicesViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            sdg = itemView.findViewById(R.id.sdg);
            note = itemView.findViewById(R.id.note);
            quantity = itemView.findViewById(R.id.quantity);
            clicked_row = itemView.findViewById(R.id.clicked_row);
            service_image = itemView.findViewById(R.id.service_image);
        }
    }
}


