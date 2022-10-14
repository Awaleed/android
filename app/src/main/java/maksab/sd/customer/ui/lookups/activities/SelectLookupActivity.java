package maksab.sd.customer.ui.lookups.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.lookup.LookupModel;
import maksab.sd.customer.network.appnetwork.ILookUpsService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.ui.lookups.adapters.SelectLookupListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectLookupActivity extends BaseActivity implements SearchView.OnQueryTextListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.main_progress)
    ProgressBar main_progress;

    private static final int CITY = 1;
    private static final int District = 2;
    private static final int FLOORNUMBER = 3;
    private static final int ADDRESSTYPE = 4;
    private static final String LOOKUPTYPEINTENT = "lookup_type";

    private SelectLookupListAdapter selectLookupListAdapter;
    private List<LookupModel> lookupModelList;
    private  int lookupType =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lookup);
        ButterKnife.bind(this);
        setupToolbar();
        setupRecyclerView();
        lookupType = getIntent().getIntExtra(LOOKUPTYPEINTENT , 0);
        loadLookupBasedOnType(lookupType);
    }

    private void setupToolbar(){
        toolbar.setTitle(R.string.select_item);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupRecyclerView() {
        listView = findViewById(R.id.list_view);
        lookupModelList = new ArrayList<>();
        selectLookupListAdapter = new SelectLookupListAdapter(this, lookupModelList);
        listView.setAdapter(selectLookupListAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            LookupModel lookupModel = lookupModelList.get(i);
            Intent reslutintent = new Intent();
            reslutintent.putExtra("name" , lookupModel.getArabicName());
            reslutintent.putExtra(LOOKUPTYPEINTENT , lookupModel.getId());
          setResult(Activity.RESULT_OK , reslutintent);
          SelectLookupActivity.this.finish();
        });

    }

    private void getAllCities(){
        toolbar.setTitle(R.string.select_city);
        ILookUpsService lookUpsService = GetWayServiceGenerator.createService(ILookUpsService.class);
        lookUpsService.GetCities().enqueue(new Callback<List<LookupModel>>() {
            @Override
            public void onResponse(Call<List<LookupModel>> call, Response<List<LookupModel>> response) {
                main_progress.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                if(response.isSuccessful()){
                    lookupModelList.clear();
                    lookupModelList.addAll(response.body());
                    selectLookupListAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(SelectLookupActivity.this , response.message() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LookupModel>> call, Throwable t) {
                main_progress.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                Toast.makeText(SelectLookupActivity.this , t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAllDistricts(int cityId , String keyword){
        toolbar.setTitle(R.string.select_distric);
        ILookUpsService lookUpsService = GetWayServiceGenerator.createService(ILookUpsService.class);
        lookUpsService.GetDistricts(cityId , keyword).enqueue(new Callback<List<LookupModel>>() {
            @Override
            public void onResponse(Call<List<LookupModel>> call, Response<List<LookupModel>> response) {
                main_progress.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                if(response.isSuccessful()){
                    lookupModelList.clear();
                    lookupModelList.addAll(response.body());
                    selectLookupListAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(SelectLookupActivity.this , response.message() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LookupModel>> call, Throwable t) {
                main_progress.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                Toast.makeText(SelectLookupActivity.this , t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAllFloors(){
        toolbar.setTitle(R.string.select_floor_number);

        ILookUpsService lookUpsService = GetWayServiceGenerator.createService(ILookUpsService.class);
        lookUpsService.GetFloors().enqueue(new Callback<List<LookupModel>>() {
            @Override
            public void onResponse(Call<List<LookupModel>> call, Response<List<LookupModel>> response) {
                main_progress.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                if(response.isSuccessful()){
                    lookupModelList.clear();
                    lookupModelList.addAll(response.body());
                    selectLookupListAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(SelectLookupActivity.this , response.message() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LookupModel>> call, Throwable t) {
                main_progress.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                Toast.makeText(SelectLookupActivity.this , t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAllAddressTypes(){
        toolbar.setTitle(R.string.select_address_type);
        ILookUpsService lookUpsService = GetWayServiceGenerator.createService(ILookUpsService.class);
        lookUpsService.GetAddressTypes().enqueue(new Callback<List<LookupModel>>() {
            @Override
            public void onResponse(Call<List<LookupModel>> call, Response<List<LookupModel>> response) {
                main_progress.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                if(response.isSuccessful()){
                    lookupModelList.clear();
                    lookupModelList.addAll(response.body());
                    selectLookupListAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(SelectLookupActivity.this , response.message() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LookupModel>> call, Throwable t) {
                main_progress.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                Toast.makeText(SelectLookupActivity.this , t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

   private void loadLookupBasedOnType(int type){
        main_progress.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        if(type == ADDRESSTYPE){
            getAllAddressTypes();
        }else if(type == FLOORNUMBER){
            getAllFloors();
        }else if(type == District){
            getAllDistricts(getIntent().getIntExtra("cityId" ,0) , "");
        }else if(type == CITY){
            getAllCities();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(lookupType == District){
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.search_menu, menu);

            MenuItem search = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) search.getActionView();
            searchView.setInputType(InputType.TYPE_CLASS_TEXT);

            if (searchView != null)
                searchView.setOnQueryTextListener(this);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        getAllDistricts(getIntent().getIntExtra("cityId" ,0) , newText);
        return true;
    }
}

