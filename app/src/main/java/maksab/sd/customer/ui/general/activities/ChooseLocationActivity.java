package maksab.sd.customer.ui.general.activities;

import androidx.fragment.app.Fragment;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.SingleFragmentActivity;
import maksab.sd.customer.basecode.fragments.MapFragment;

public class ChooseLocationActivity extends SingleFragmentActivity  {

    
    @Override
    protected Fragment createFragment() {
        return MapFragment.newInstance(true , getIntent().getIntExtra("questionId",0),getIntent().getStringExtra("text_address"));
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.select_location_from_map);
    }

    @Override
    protected boolean isBackArrayHidden() {
        return true;
    }


}
