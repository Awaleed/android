package maksab.sd.customer.wizards.neworder.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import maksab.sd.customer.basecode.fragments.FragmentsContract;

public class OrderWizardPagerAdapter extends FragmentPagerAdapter {

    private List<FragmentsContract> fragments;
    public OrderWizardPagerAdapter(@NonNull FragmentManager fm , List<FragmentsContract> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return  (Fragment) fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
