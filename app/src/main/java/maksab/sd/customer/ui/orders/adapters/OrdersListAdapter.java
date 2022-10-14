package maksab.sd.customer.ui.orders.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.providers.OrderModel;
import maksab.sd.customer.util.general.OrderUtils;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.OrdersListViewHolder> {
    private List<OrderModel> ordersModels;
    private View.OnClickListener _onclickListner;

    public OrdersListAdapter(List<OrderModel> models , View.OnClickListener onClickListener){
        this._onclickListner = onClickListener;
        this.ordersModels = models;
    }

    @Override
    public OrdersListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrdersListViewHolder myViewHolder = new OrdersListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false));
        myViewHolder.itemView.setOnClickListener(_onclickListner);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(OrdersListViewHolder holder, int position) {
        OrderModel model = ordersModels.get(position);

        holder.order_number_textview.setText(" " + model.getId() );
        holder.order_creation_date_textview.setText(model.getCreatedOnString());
        holder.order_type_textview.setText(model.getOrderTypeArabic());
        holder.order_status_value_textview.setText(model.getOrderStatusArabic());
        holder.order_specialty_type_textview.setText(model.getSpecialityName());

        holder.order_price_textview.setText(OrderUtils.getOrderTotalPriceAsString(
                holder.itemView.getContext(), model));

        holder.avg_prices_layout.setVisibility(View.GONE);
        holder.order_send_to_label_textview.setVisibility(View.GONE);
        holder.order_send_to_textview.setVisibility(View.GONE);

//        if (model.getOrderTypeId() == OrderTypeIdEnum.PublicQuotation.ordinal() ||
//                model.getOrderTypeId() == OrderTypeIdEnum.PublicQuotation.ordinal()) {
//            if (model.getOrderStatusId() == OrderStatusEnum.WaitingProviders.ordinal()) {
//                OrderDetailsModel.OrderOfferStatistics statistics = model.getOrderOfferStatistics();
//                if (statistics != null) {
//                    holder.avg_prices_layout.setVisibility(View.VISIBLE);
//                    holder.order_send_to_label_textview.setVisibility(View.VISIBLE);
//                    holder.order_send_to_textview.setVisibility(View.VISIBLE);
//
//                    holder.order_send_to_textview.setText(String.valueOf(statistics.getSendToCount()));
//                    holder.order_price_avg_textview.setText(statistics.getAveragePrice() != 0 ?
//                            NumbersUtil.formatAmount(statistics.getAveragePrice()) :
//                            holder.itemView.getContext().getString(R.string.not_specified));
//                }
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return ordersModels.size();
    }

    class OrdersListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_number_textview)
        TextView order_number_textview;
        @BindView(R.id.order_creation_date_textview)
        TextView order_creation_date_textview;
        @BindView(R.id.order_type_textview)
        TextView order_type_textview;
        @BindView(R.id.order_status_value_textview)
        TextView order_status_value_textview;
        @BindView(R.id.order_price_textview)
        TextView order_price_textview;
        @BindView(R.id.order_specialty_type_textview)
        TextView order_specialty_type_textview;
        @BindView(R.id.order_send_to_textview)
        TextView order_send_to_textview;
        @BindView(R.id.order_price_avg_textview)
        TextView order_price_avg_textview;
        @BindView(R.id.order_send_to_label_textview)
        TextView order_send_to_label_textview;
        @BindView(R.id.avg_prices_layout)
        View avg_prices_layout;

        public OrdersListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}


