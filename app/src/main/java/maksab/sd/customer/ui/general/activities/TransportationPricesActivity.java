package maksab.sd.customer.ui.general.activities;

import androidx.fragment.app.Fragment;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.SingleFragmentActivity;
import maksab.sd.customer.ui.general.fragments.TransportationPricesFragment;

public class TransportationPricesActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        int specialityId = getIntent().getIntExtra("speciality_id",0);
        String specialityName = getIntent().getStringExtra("speciality_name");
        return TransportationPricesFragment.newInstance(specialityId , specialityName);
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.transportation_price);
    }

    @Override
    protected boolean isBackArrayHidden() {
        return false;
    }
}