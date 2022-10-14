package maksab.sd.customer.ui.lookups.adapters;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.events.OnSelectTime;
import maksab.sd.customer.models.lookup.TimeSlotModel;

public class SelectTimeAdapter extends RecyclerView.Adapter<SelectTimeAdapter.SelectTimeViewHolder> {

    private List<TimeSlotModel> timeSlotModels;
    private OnSelectTime onClickListener;
    private int lastPosition = -1;

    public SelectTimeAdapter(List<TimeSlotModel> timeSlotModels , OnSelectTime onClickListener){
        this.timeSlotModels = timeSlotModels;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public SelectTimeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new SelectTimeViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_time_row , parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SelectTimeViewHolder holder, int position) {
        TimeSlotModel timeSlotModel = timeSlotModels.get(position);
        holder.time_slot.setText(timeSlotModel.getItem());
        holder.itemView.setOnClickListener(view -> {
            lastPosition = position;
            onClickListener.onTimeSelected(position);
            notifyDataSetChanged();
        });

        GradientDrawable border = new GradientDrawable();
        if(lastPosition == position){
            holder.time_slot.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary));
            border.setStroke(6, ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary));
        }
        else{
            holder.time_slot.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.light_gray));
            border.setStroke(2, ContextCompat.getColor(holder.itemView.getContext(), R.color.light_gray));
        }

        holder.itemView.setBackground(border);
    }

    @Override
    public int getItemCount() {
        return timeSlotModels.size();
    }

    class SelectTimeViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.time_slot)
        TextView time_slot;

         public SelectTimeViewHolder(@NonNull @NotNull View itemView) {
             super(itemView);
             ButterKnife.bind(this , itemView);
         }
     }
}
