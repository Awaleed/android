package maksab.sd.customer.wizards.neworder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.storage.OrderSummaryDetails;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.OrderSummaryViewHolder> {

    private List<OrderSummaryDetails> orderSummaryDetails;
    public OrderSummaryAdapter(List<OrderSummaryDetails> orderSummaryDetails){
        this.orderSummaryDetails = orderSummaryDetails;
    }

    @NonNull
    @Override
    public OrderSummaryViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        return new OrderSummaryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_summary_details_row , parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummaryAdapter.OrderSummaryViewHolder holder, int position) {
        OrderSummaryDetails orderSummaryModel = orderSummaryDetails.get(position);
        holder.serviceName.setText(orderSummaryModel.getName());
        holder.servicePrice.setText(orderSummaryModel.getPrice() +holder.itemView.getResources().getString(R.string.pound));
        holder.serviceQuantity.setText( holder.itemView.getResources().getString(R.string.selected_qountity) + orderSummaryModel.getQuantity());
    }

    @Override
    public int getItemCount() {
        return orderSummaryDetails.size();
    }

    class OrderSummaryViewHolder extends RecyclerView.ViewHolder {

        TextView serviceName;
        TextView servicePrice;
        TextView serviceQuantity;

        public OrderSummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.service_name);
            servicePrice = itemView.findViewById(R.id.service_price);
            serviceQuantity = itemView.findViewById(R.id.service_quantity);
        }
    }
}
