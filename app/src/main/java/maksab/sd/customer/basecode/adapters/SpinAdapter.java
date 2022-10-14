package maksab.sd.customer.basecode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.utility.SpinnerDataMap;

/**
 * Created by dev2 on 12/10/2017.
 */

public class SpinAdapter extends BaseAdapter {


    private Context context;

    private List<SpinnerDataMap> values;

   public SpinAdapter(Context context , List<SpinnerDataMap> values ){
      this.context = context;
      this.values = values;
   }


    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.spinneritem , null);
        TextView textView = view.findViewById(R.id.spinnerItem);
        textView.setText(values.get(i).getName());
        return view;
    }
}
