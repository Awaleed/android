package maksab.sd.customer.ui.tickets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.tickets.TicketModel;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.TicketViewHolder> {
    private List<TicketModel> ticketModels;
    private Context context;
    private View.OnClickListener onClickListener;

    public TicketsAdapter(List<TicketModel> ticketModels, Context context, View.OnClickListener onClickListener){
        this.ticketModels = ticketModels;
        this.onClickListener = onClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TicketViewHolder ticketViewHolder = new TicketViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ticket, parent , false));
        ticketViewHolder.itemView.setOnClickListener(onClickListener);
        return ticketViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        TicketModel ticketModel = ticketModels.get(position);
        holder.ticket_date.setText(ticketModel.getCreatedOnString());
        holder.ticket_id.setText(context.getString(R.string.ticket_number) + ticketModel.getId());
        holder.ticket_category_text_view.setText(ticketModel.getTicketCategoryArabic() +
                " - " + ticketModel.getTicketSubCategoryArabic());

        if(ticketModel.isResolved()){
            holder.ticket_status.setText(R.string.closed_verb);
            holder.ticket_status.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        else{
            holder.ticket_status.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            holder.ticket_status.setText(R.string.opend_verb);
        }
    }

    @Override
    public int getItemCount() {
        return ticketModels.size();
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ticket_id)
        TextView ticket_id;
        @BindView(R.id.ticket_date)
        TextView ticket_date;
        @BindView(R.id.ticket_status)
        TextView ticket_status;
        @BindView(R.id.ticket_category_text_view)
        TextView ticket_category_text_view;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

