package maksab.sd.customer.ui.profile.activities;

import androidx.fragment.app.Fragment;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.SingleFragmentActivity;
import maksab.sd.customer.ui.profile.fragments.ProfileFragment;

public class ProfileActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return ProfileFragment.newInstance(true);
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.profile_image);
    }

    @Override
    protected boolean isBackArrayHidden() {
        return false;
    }
}
