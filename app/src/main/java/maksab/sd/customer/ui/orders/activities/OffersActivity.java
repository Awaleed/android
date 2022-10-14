package maksab.sd.customer.ui.orders.activities;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.ui.orders.fragments.OffersFragment;

public class OffersActivity extends BaseActivity {

    private Long quotationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        setupToolbar();
        quotationId = getIntent().getLongExtra("quotation_id" , 34963);
        prepareFragment(quotationId);
    }


    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.provided_offers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(5f);
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void prepareFragment(long quotationId){
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            boolean isQuationClosed = false;
            if(getIntent().getStringExtra("qouation_status").equals(getString(R.string.closed))  || getIntent().getStringExtra("qouation_status").equals(getString(R.string.cancled))){
                isQuationClosed = true;
            }
            fragment = OffersFragment.newInstance(quotationId , isQuationClosed);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}