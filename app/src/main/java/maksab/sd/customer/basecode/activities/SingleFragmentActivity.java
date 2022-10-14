package maksab.sd.customer.basecode.activities;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;

public abstract class SingleFragmentActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    protected abstract Fragment createFragment();

    protected abstract String getActivityTitle();

    protected abstract boolean isBackArrayHidden();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        ButterKnife.bind(this);

        setupToolbar();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getActivityTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(!isBackArrayHidden());
        getSupportActionBar().setHomeButtonEnabled(!isBackArrayHidden());

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}