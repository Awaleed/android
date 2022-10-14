package maksab.sd.customer.ui.lookups.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.models.lookup.LookupModel;

public class SelectLookupListAdapter extends ArrayAdapter<LookupModel> {
    private List<LookupModel> lookupModelList;
    private Context mContext;

    public SelectLookupListAdapter(@NonNull Context context, @NonNull List<LookupModel> models) {
        super(context, R.layout.item_lookup, models);
        lookupModelList = models;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LookupModel model = lookupModelList.get(position);
        SelectSpecialtyListViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new SelectSpecialtyListViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_lookup, parent, false);
            viewHolder.specialty_name = convertView.findViewById(R.id.specialty_name);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (SelectSpecialtyListViewHolder) convertView.getTag();
        }

        viewHolder.specialty_name.setText(model.getArabicName());

        return convertView;
    }

    class SelectSpecialtyListViewHolder {
        TextView specialty_name;
    }
}


