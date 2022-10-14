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
import maksab.sd.customer.basecode.events.OnSelectDay;
import maksab.sd.customer.models.lookup.DaySlotModel;

public class SelectDayAdapter extends RecyclerView.Adapter<SelectDayAdapter.SelectDayViewHolder> {

    private List<DaySlotModel> daySlotModels;
    private OnSelectDay onClickListener;
    private int lastPosition = -1;

    public SelectDayAdapter(List<DaySlotModel> daySlotModels , OnSelectDay onClickListener){
        this.daySlotModels = daySlotModels;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public SelectDayViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new SelectDayViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_day_row , parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SelectDayViewHolder holder, int position) {
        DaySlotModel daySlotModel = daySlotModels.get(position);
        holder.day_name.setText(daySlotModel.getMonthNumber());
        holder.day_number.setText(daySlotModel.getDayNumber());

        holder.itemView.setOnClickListener(view -> {
            lastPosition = position;
            onClickListener.onDaySelected(position);
            notifyDataSetChanged();
        });

        GradientDrawable border = new GradientDrawable();
        if(lastPosition == position){
            holder.day_name.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary));
            holder.day_number.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary));
            border.setStroke(6, ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary));
        }
        else{
            holder.day_name.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.light_gray));
            holder.day_number.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.light_gray));
            border.setStroke(2, ContextCompat.getColor(holder.itemView.getContext(), R.color.light_gray));
        }

        holder.itemView.setBackground(border);
    }

    @Override
    public int getItemCount() {
        return daySlotModels.size();
    }

    class SelectDayViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.day_number)
        TextView day_number;
        @BindView(R.id.day_name)
        TextView day_name;

        public SelectDayViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
