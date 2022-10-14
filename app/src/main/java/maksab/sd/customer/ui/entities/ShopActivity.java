package maksab.sd.customer.ui.entities;

import androidx.fragment.app.Fragment;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.SingleFragmentActivity;

public class ShopActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        String userId = getIntent().getStringExtra("UserId");
        return ShopFragment.newInstance(userId);
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.about_shop);
    }

    @Override
    protected boolean isBackArrayHidden() {
        return false;
    }
}
