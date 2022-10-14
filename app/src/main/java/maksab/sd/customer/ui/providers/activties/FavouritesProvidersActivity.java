package maksab.sd.customer.ui.providers.activties;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.providers.ProviderDetailsModel;
import maksab.sd.customer.ui.providers.adapters.ProviderListAdapter;
import maksab.sd.customer.viewmodels.providers.FavouriteProvidesViewModel;


public class FavouritesProvidersActivity extends BaseActivity {

   private RecyclerView recyclerView;

    private  LinearLayoutManager layoutManager;

    private ProviderListAdapter adapter;

    private FavouriteProvidesViewModel favouriteProvidesViewModel;

    private List<ProviderDetailsModel> providersListModels;

    ViewGroup no_data_layout;

    ProgressBar main_progressbar;

    SwipeRefreshLayout swipeRefreshLayout;

    TextView place_holder_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_providers);
        no_data_layout = findViewById(R.id.no_data_layout);
        main_progressbar = findViewById(R.id.main_progress);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        place_holder_text = findViewById(R.id.place_holder_text);

        initViews();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            favouriteProvidesViewModel.getFavouritesProvider();
        });
    }

    private void initViews() {
        favouriteProvidesViewModel = ViewModelProviders.of(this).get(FavouriteProvidesViewModel.class);
        favouriteProvidesViewModel.getFavoiritesObservable().observe(this , data -> {
            main_progressbar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            providersListModels.clear();
            if(data !=null){
               if(data.size() > 0){
                   no_data_layout.setVisibility(View.GONE);
                   providersListModels.addAll(data);
                   adapter.notifyDataSetChanged();
               }else {
                   place_holder_text.setText("لا يوجد مقدمين مفضلين , يجب اضافة مقدمين اولآ من قائمة المقدمين");
                   no_data_layout.setVisibility(View.VISIBLE);
               }
            }else {
                Toast.makeText(this , getResources().getText(R.string.error) , Toast.LENGTH_LONG).show();
                finish();
            }
        });
        setUpToolBar();
        setupRecyclerview();
       main_progressbar.setVisibility(View.VISIBLE);
        favouriteProvidesViewModel.getFavouritesProvider();

    }

    private void setUpToolBar(){
        Toolbar logintoolbar = findViewById(R.id.toolbar);
        logintoolbar.setTitle(R.string.favorite);
        setSupportActionBar(logintoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void setupRecyclerview(){

        recyclerView = findViewById(R.id.providerrecyclerview);
        layoutManager = new LinearLayoutManager(this);
        providersListModels = new ArrayList<>();

        adapter = new ProviderListAdapter(providersListModels, this, view -> {


            Intent intent = new Intent(FavouritesProvidersActivity.this , ProviderDetailsActivity.class);
            intent.putExtra("provider.model" , providersListModels.get(recyclerView.getChildAdapterPosition(view)));
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view.findViewById(R.id.provider_profile_image), "anynoymouse");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startActivity(intent , options.toBundle());
            }else {
                startActivity(intent);
            }


        } , true);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
