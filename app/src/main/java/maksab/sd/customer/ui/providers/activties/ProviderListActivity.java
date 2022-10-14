package maksab.sd.customer.ui.providers.activties;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.events.EndlessRecyclerViewScrollListener;
import maksab.sd.customer.models.providers.ProviderDetailsModel;
import maksab.sd.customer.ui.providers.adapters.ProviderListAdapter;
import maksab.sd.customer.ui.providers.adapters.StoreListAdapter;
import maksab.sd.customer.util.general.AddressInMemoryStorage;
import maksab.sd.customer.viewmodels.providers.ProvidersListViewModel;

public class ProviderListActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProviderListAdapter adapter;
    private StoreListAdapter storeListAdapter;

    private ProvidersListViewModel providersListViewModel;
    private List<ProviderDetailsModel> providersListModels;

    private EndlessRecyclerViewScrollListener scrollListener;
    private ViewGroup no_data_layout;
    private GoogleApiClient mGoogleApiClient;
    private ProgressBar main_progressbar;
    private boolean islastpage;
    private int selectedspecialty;
    private String specialityTitle = "";

    private boolean isStore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_list);
        selectedspecialty = getIntent().getIntExtra("selected.speciality",0);
        specialityTitle = getIntent().getStringExtra("selected.speciality.title");
        buildGoogleApiClient();
        initViews();
    }

    private void initViews() {
        swipeViewInit();
        observerInit();
        setUpToolBar();
        setupRecyclerview();
        no_data_layout = findViewById(R.id.no_data_layout);
        main_progressbar = findViewById(R.id.main_progress);
        main_progressbar.setVisibility(View.VISIBLE);
        getProvidersWithWaiter(1);
    }

    private void swipeViewInit() {
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> resetPaging());
    }

    private void resetPaging() {
        islastpage = false;
        providersListModels.clear();
       if(isStore){
           storeListAdapter.notifyDataSetChanged();
       }else{
           adapter.notifyDataSetChanged();
       }
        if(scrollListener!=null){
            scrollListener.resetState();
        }
        getProvidersWithWaiter(1);

    }

    private void observerInit() {
        providersListViewModel = ViewModelProviders.of(this).get(ProvidersListViewModel.class);

        providersListViewModel.getProvidersObservable().observe(this , getProvidersObserver());
    }

    @NonNull
    private Observer<List<ProviderDetailsModel>> getProvidersObserver() {
        return data -> {
            swipeRefreshLayout.setRefreshing(false);
            main_progressbar.setVisibility(View.GONE);

            if(data!=null){

                if (data.size() < 20){
                    islastpage = true;
                }

               if(data.size() > 0){
                   no_data_layout.setVisibility(View.GONE);
                   providersListModels.addAll(data);
                  if(isStore){
                      storeListAdapter.notifyItemRangeInserted(storeListAdapter.getItemCount() , providersListModels.size());
                  }else{
                      adapter.notifyItemRangeInserted(adapter.getItemCount() , providersListModels.size());
                  }
               }
               else {
                       no_data_layout.setVisibility(View.VISIBLE);
               }

            }
            else {
                finish();
            }
        };
    }


    private void setUpToolBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(specialityTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

   private void setupRecyclerview(){
       recyclerView = findViewById(R.id.providerrecyclerview);
       recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

       layoutManager = new LinearLayoutManager(this);

       scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
           @Override
           public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
               if (!islastpage){
                   getProvidersWithWaiter(page);
               }
           }
       };

       recyclerView.addOnScrollListener(scrollListener);
       recyclerView.setLayoutManager(layoutManager);
       providersListModels = new ArrayList<>();

       if(isStore){
           storeListAdapter = new StoreListAdapter(providersListModels , view -> {
               ProviderDetailsModel providerDetailsModel = providersListModels.get(recyclerView.getChildAdapterPosition(view));

               Intent intent = new Intent(ProviderListActivity.this , ProviderDetailsActivity.class);
               intent.putExtra("select_speciality" , selectedspecialty);
               intent.putExtra("provider.model" , providerDetailsModel);
               ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view.findViewById(R.id.provider_profile_image), "anynoymouse");
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                   startActivity(intent , options.toBundle());
               }else {
                   startActivity(intent);
               }
           });
           recyclerView.setAdapter(storeListAdapter);

       }else{
           adapter = new ProviderListAdapter(providersListModels, this, view -> {
               Intent intent = new Intent(ProviderListActivity.this , ProviderDetailsActivity.class);
               intent.putExtra("select_speciality" , selectedspecialty);
               intent.putExtra("provider.model" , providersListModels.get(recyclerView.getChildAdapterPosition(view)));
               ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view.findViewById(R.id.provider_profile_image), "anynoymouse");
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                   startActivity(intent , options.toBundle());
               }else {
                   startActivity(intent);
               }


           }, false);

           recyclerView.setAdapter(adapter);
       }
   }

   @SuppressLint("MissingPermission")
   private void getProvidersWithWaiter(int page){
       providersListViewModel.getProviderNearBy(selectedspecialty,
               AddressInMemoryStorage.id, page);
   }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, 1122)
                        .show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {

                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        mGoogleApiClient.connect();
                    }
                })
                .addOnConnectionFailedListener(connectionResult -> {

                })
                .addApi(LocationServices.API).build();
    }

}
