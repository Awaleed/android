package maksab.sd.customer.ui.orders.activities;

import androidx.fragment.app.Fragment;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.SingleFragmentActivity;
import maksab.sd.customer.ui.orders.fragments.ServicesListFragment;
import maksab.sd.customer.util.constants.Enums;

public class ServicesActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        int specialtyId = getIntent().getIntExtra("speciality.id", 0);
        return ServicesListFragment.newInstance(specialtyId, Enums.SpecialtyTypeEnum.Online.ordinal());
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.providers_services);
    }

    @Override
    protected boolean isBackArrayHidden() {
        return false;
    }
}
