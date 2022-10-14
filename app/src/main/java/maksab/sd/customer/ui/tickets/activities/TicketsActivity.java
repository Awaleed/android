package maksab.sd.customer.ui.tickets.activities;

import androidx.fragment.app.Fragment;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.SingleFragmentActivity;
import maksab.sd.customer.ui.tickets.fragments.TicketsListFragment;

public class TicketsActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return TicketsListFragment.newInstance(1);
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.tickets);
    }

    @Override
    protected boolean isBackArrayHidden() {
        return false;
    }
}