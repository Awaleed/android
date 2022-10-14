package maksab.sd.customer.ui.main.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.models.main.SettingsModel;


public class SettingsListAdapter extends RecyclerView.Adapter<SettingsListAdapter.SettingViewHolder> {
    private List<SettingsModel> settingsModels;
    View.OnClickListener onClickListener;

    public SettingsListAdapter(List<SettingsModel> settingsModels , View.OnClickListener onClickListener){
        this.settingsModels = settingsModels;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public SettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SettingViewHolder settingViewHolder = new SettingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_row_layout, parent, false));
        settingViewHolder.itemView.setOnClickListener(onClickListener);
        return settingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SettingViewHolder holder, int position) {
        SettingsModel settingsModel = settingsModels.get(position);
        holder.label.setText(settingsModel.getLabel());
        holder.description.setText(settingsModel.getDescription());
        holder.icon.setImageDrawable(VectorDrawableCompat.create(holder.itemView.getContext().getResources() , settingsModel.getIcon() , null));

    }

    @Override
    public int getItemCount() {
        return settingsModels.size();
    }


    class SettingViewHolder extends RecyclerView.ViewHolder {

        TextView label,description;
        ImageView icon;

        public SettingViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.label);
            icon = itemView.findViewById(R.id.icon);
            description = itemView.findViewById(R.id.description);
        }
    }
}
