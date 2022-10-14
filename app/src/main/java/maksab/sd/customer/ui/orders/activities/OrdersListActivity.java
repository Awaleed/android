package maksab.sd.customer.ui.orders.activities;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.adapters.BaseFragmentAdapter;
import maksab.sd.customer.ui.orders.fragments.OrdersListFragment;

public class OrdersListActivity extends BaseActivity {
   private ViewPager simpleViewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        initTabLayout();
    }

    private void initTabLayout(){

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.orders);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        simpleViewPager = findViewById(R.id.simpleViewPager);
        tabLayout = findViewById(R.id.simpleTabLayout);
        prepareFragments();
        BaseFragmentAdapter baseFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager() , fragments, titles);
        simpleViewPager.setAdapter(baseFragmentAdapter);
        tabLayout.setupWithViewPager(simpleViewPager);
    }

    private void prepareFragments(){
        fragments.add(OrdersListFragment.newInstance(1));
        fragments.add(OrdersListFragment.newInstance(2));
        titles.add(getString(R.string.current_order));
        titles.add(getString(R.string.finished_orders));
    }



}
