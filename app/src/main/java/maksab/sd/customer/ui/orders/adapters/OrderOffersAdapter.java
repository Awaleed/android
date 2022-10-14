package maksab.sd.customer.ui.orders.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.orders.details.OrderOffer;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.DateUtil;
import maksab.sd.customer.util.general.NumbersUtil;
import maksab.sd.customer.util.general.StringUtils;

public class OrderOffersAdapter extends RecyclerView.Adapter<OrderOffersAdapter.OrderOffersViewHolder> {
    private Context context;
    private List<OrderOffer> orderOffers;
    private OrderOffersListener listener;
    private boolean isQuotationClosed;

    public interface OrderOffersListener {
        void openProviderProfile(String userId);
        void openAcceptOrderOffer(int offerId);
    }

    public OrderOffersAdapter(Context context, List<OrderOffer> orderOffers, boolean isQuotationClosed,
                              OrderOffersListener listener) {
        this.context = context;
        this.orderOffers = orderOffers;
        this.listener = listener;
        this.isQuotationClosed = isQuotationClosed;
    }

    @Override
    public OrderOffersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderOffersViewHolder viewHolder = new OrderOffersViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_offer, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderOffersViewHolder holder, int position) {
        OrderOffer orderOffer = orderOffers.get(position);

        holder.provider_name.setOnClickListener(view -> listener.openProviderProfile(orderOffer.getProviderUserId()));

        holder.profile_image.setOnClickListener(view -> listener.openProviderProfile(orderOffer.getProviderUserId()));

        holder.view_profile.setOnClickListener(view -> listener.openProviderProfile(orderOffer.getProviderUserId()));

        holder.accept_offer.setOnClickListener(view -> listener.openAcceptOrderOffer(orderOffer.getId()));

        holder.offer_control_btns.setVisibility(View.VISIBLE);
        holder.offer_control_label.setText("التحكم");

        if (isQuotationClosed)
            holder.accept_offer.setVisibility(View.GONE);

        Picasso.with(context).load(orderOffer.getProviderImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.profile_image);

        holder.provider_name.setText(orderOffer.getProviderName());
        holder.review_rate.setRating((float) orderOffer.getProviderRate());
        holder.provider_type_textview.setText(Enums.getProviderTypeString(orderOffer.getProviderType()));
        holder.completed_orders_counts.setText(orderOffer.getProviderCompletedOrdersCount() + " " +
                context.getString(R.string.total_completed_orders));

        boolean isAcceptedAndWillingToWork = orderOffer.isAccepted() && orderOffer.isOffered();
        boolean isAcceptedAndNotWillingToWork = orderOffer.isOffered() && !orderOffer.isAccepted();
        boolean isNotAnswering = !orderOffer.isOffered() && !orderOffer.isAccepted();

        if (isAcceptedAndWillingToWork) {
            holder.provider_answer_view.setVisibility(View.VISIBLE);
            holder.provider_acceptance_view.setVisibility(View.VISIBLE);

            holder.offer_status_textview.setText(R.string.accepted_offer);
            holder.offer_status_textview.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

            holder.offer_price_textview.setText(NumbersUtil.formatAmount(orderOffer.getPrice()));
            holder.transportation_amount_textview.setText(NumbersUtil.formatAmount(orderOffer.getTransportationPrice()));
            holder.service_type.setText(orderOffer.getExecutionTypeName());

            DateUtil parser = DateUtil.newInstance();
            parser.parse(orderOffer.getExecutionDateOn());

            holder.date_textview.setText(parser.getDateString());
            holder.time_textview.setText(orderOffer.getExecutionTimeOn());

            holder.guarantee_days.setText(orderOffer.getGuaranteePeriodInDays() + " - " + context.getString(R.string.days));

            holder.body.setText(StringUtils.getDefaultIfNull(orderOffer.getBody(), context.getString(R.string.no_note_added)));

            NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
            if (orderOffer.getProviderLatitude() == 0 || orderOffer.getProviderLongitude() == 0)
                holder.provider_distance.setText(R.string.not_specified);
            else
                holder.provider_distance.setText(String.format("%s ك", nf.format(Math.round(orderOffer.getProviderDistance()))));

            holder.reason.setVisibility(View.GONE);
            holder.accept_offer.setEnabled(true);
            holder.accept_offer.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        }
        else if (isAcceptedAndNotWillingToWork) {
            holder.provider_answer_view.setVisibility(View.VISIBLE);
            holder.provider_acceptance_view.setVisibility(View.GONE);

            holder.offer_status_textview.setText(R.string.rejected_offer);
            holder.offer_status_textview.setTextColor(ContextCompat.getColor(context, R.color.red));

            holder.body.setText(StringUtils.getDefaultIfNull(orderOffer.getClosedBody(), context.getString(R.string.no_note_added)));
            holder.reason.setText(StringUtils.getDefaultIfNull(orderOffer.getClosedReason(), context.getString(R.string.no_note_added)));

            holder.reason.setVisibility(View.VISIBLE);

            holder.accept_offer.setEnabled(false);
            holder.accept_offer.setBackgroundColor(ContextCompat.getColor(context, R.color.light_grey));
        }
        else if (isNotAnswering) {
            holder.offer_status_textview.setText(R.string.waiting);
            holder.offer_status_textview.setTextColor(ContextCompat.getColor(context, R.color.light_gray));

            holder.provider_answer_view.setVisibility(View.GONE);
            holder.provider_acceptance_view.setVisibility(View.GONE);
            holder.offer_control_btns.setVisibility(View.GONE);
            holder.offer_control_label.setText("في إنتظار عرض سعر المقدم على طلبك");
            holder.accept_offer.setEnabled(false);
            holder.accept_offer.setBackgroundColor(ContextCompat.getColor(context, R.color.light_grey));
        }
    }

    @Override
    public int getItemCount() {
        return orderOffers.size();
    }

    class OrderOffersViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profile_image)
        ShapeableImageView profile_image;
        @BindView(R.id.provider_name)
        TextView provider_name;
        @BindView(R.id.provider_type_textview)
        TextView provider_type_textview;
        @BindView(R.id.offer_status_textview)
        TextView offer_status_textview;
        @BindView(R.id.accept_offer)
        TextView accept_offer;
        @BindView(R.id.provider_distance)
        TextView provider_distance;
        @BindView(R.id.service_type)
        TextView service_type;
        @BindView(R.id.date_textview)
        TextView date_textview;
        @BindView(R.id.time_textview)
        TextView time_textview;
        @BindView(R.id.guarantee_days)
        TextView guarantee_days;
        @BindView(R.id.offer_price_textview)
        TextView offer_price_textview;
        @BindView(R.id.transportation_amount_textview)
        TextView transportation_amount_textview;
        @BindView(R.id.body)
        TextView body;
        @BindView(R.id.reason)
        TextView reason;
        @BindView(R.id.review_rate)
        RatingBar review_rate;
        @BindView(R.id.completed_orders_counts)
        TextView completed_orders_counts;
        @BindView(R.id.view_profile)
        TextView view_profile;
        @BindView(R.id.provider_answer_view)
        View provider_answer_view;
        @BindView(R.id.provider_acceptance_view)
        View provider_acceptance_view;

        @BindView(R.id.offer_control_btns)
        ViewGroup offer_control_btns;

        @BindView(R.id.offer_control_label)
        TextView offer_control_label;

        public OrderOffersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


