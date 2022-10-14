package maksab.sd.customer.ui.providers.adapters;

import android.text.TextUtils;
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
import maksab.sd.customer.models.providers.ProviderRateModel;
import maksab.sd.customer.util.constants.Enums;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class ProviderRatesAdapter extends RecyclerView.Adapter<ProviderRatesAdapter.ProviderRateViewHolder> {
    private List<ProviderRateModel> providerRateModels;

    public ProviderRatesAdapter(List<ProviderRateModel> providerRateModels){
        this.providerRateModels = providerRateModels;
    }

    @NonNull
    @Override
    public ProviderRateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProviderRateViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_provider_rate, parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderRateViewHolder holder, int position) {
        ProviderRateModel providerRateModel = providerRateModels.get(position);
        if(TextUtils.isEmpty(providerRateModel.getRateComment())){
            holder.body.setText(R.string.not_found);
        }else{
            holder.body.setText(providerRateModel.getRateComment());
        }

        int rate = providerRateModel.getRate().intValue();

        holder.specialty_name.setText(providerRateModel.getSpecialityName());
        holder.order_rate_in_numbers.setText(Enums.getRateText(rate));
        holder.order_rate.setRating(rate);
        holder.customer_name.setText(providerRateModel.getCustomerName());
    }

    @Override
    public int getItemCount() {
        return providerRateModels.size();
    }

    class ProviderRateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.specialty_name)
        TextView specialty_name;

        @BindView(R.id.order_rate)
        MaterialRatingBar order_rate;

        @BindView(R.id.order_rate_in_numbers)
        TextView order_rate_in_numbers;

        @BindView(R.id.customer_name)
        TextView customer_name;

        @BindView(R.id.body)
        TextView body;

        public ProviderRateViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
