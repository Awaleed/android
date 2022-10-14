package maksab.sd.customer.ui.main.adapters;

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
import maksab.sd.customer.models.speciality.SpecialityModel;

public class SelectSpecialtyListAdapter extends ArrayAdapter<SpecialityModel> {

    private List<SpecialityModel> specialityModels;
    Context mContext;

    public SelectSpecialtyListAdapter(@NonNull Context context, @NonNull List<SpecialityModel> models) {
        super(context, R.layout.specialty_row , models);
        specialityModels = models;
        mContext = context;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SpecialityModel model = specialityModels.get(position);
        SelectSpecialtyListViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new SelectSpecialtyListViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.specialty_row, parent, false);
            viewHolder.specialty_name = convertView.findViewById(R.id.specialty_name);


            convertView.setTag(viewHolder);
        }else{
            viewHolder = (SelectSpecialtyListViewHolder) convertView.getTag();

        }

        viewHolder.specialty_name.setText(model.getArabicName());

        return convertView;
    }

    class SelectSpecialtyListViewHolder {

        TextView specialty_name;

    }


}


