package maksab.sd.customer.ui.general.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.events.RecyclerViewOnClick;
import maksab.sd.customer.models.address.AddressModel;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.AddressesViewHolder> {

    private List<AddressModel> _addressModels;
    View.OnClickListener _oncOnClickListener;
    RecyclerViewOnClick _onDeleteClicked;

    public AddressesAdapter(List<AddressModel> addressModels , View.OnClickListener onClickListener , RecyclerViewOnClick onDeleteClicked){
        this._addressModels = addressModels;
        this._oncOnClickListener = onClickListener;
        this._onDeleteClicked = onDeleteClicked;
    }

    @NonNull
    @Override
    public AddressesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AddressesViewHolder addressesViewHolder  = new AddressesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent , false));
        addressesViewHolder.itemView.setOnClickListener(_oncOnClickListener);
        return addressesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressesViewHolder holder, int position) {
        AddressModel customerAddressModel = _addressModels.get(position);
        holder.address_body.setText(customerAddressModel.getDistrict().getArabicName() + " - " +customerAddressModel.getAddressDescription() + " - " + customerAddressModel.getFloorType().getArabicName());
        holder.address_title.setText(customerAddressModel.getAddressType().getArabicName());
        Picasso.with( holder.address_logo.getContext()).load(customerAddressModel.getAddressType().getImagePath()).placeholder(R.drawable.placeholder).into( holder.address_logo);
        holder.image_view_delete.setOnClickListener(view -> {
            _onDeleteClicked.onClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return _addressModels.size();
    }


    class AddressesViewHolder  extends RecyclerView.ViewHolder {

        @BindView(R.id.address_title)
        TextView address_title;

        @BindView(R.id.address_logo)
        ShapeableImageView address_logo;

        @BindView(R.id.address_body)
        TextView address_body;

        @BindView(R.id.image_view_delete)
        ImageView image_view_delete;

        public AddressesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
