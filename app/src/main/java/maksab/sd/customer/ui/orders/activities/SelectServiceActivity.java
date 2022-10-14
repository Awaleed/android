package maksab.sd.customer.ui.orders.activities;

import androidx.fragment.app.Fragment;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.SingleFragmentActivity;
import maksab.sd.customer.ui.orders.fragments.ServicesListFragment;

public class SelectServiceActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        int specialtyId = getIntent().getIntExtra("specialty_id" ,46);
        return ServicesListFragment.newInstance(specialtyId, 0);
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.select_service_text);
    }

    @Override
    protected boolean isBackArrayHidden() {
        return false;
    }
}