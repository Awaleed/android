package maksab.sd.customer.ui.entities;

import androidx.fragment.app.Fragment;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.SingleFragmentActivity;

public class CenterActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        String  userId = getIntent().getStringExtra("UserId");
        return CenterFragment.newInstance(userId);
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.about_center);
    }

    @Override
    protected boolean isBackArrayHidden() {
        return false;
    }
}
