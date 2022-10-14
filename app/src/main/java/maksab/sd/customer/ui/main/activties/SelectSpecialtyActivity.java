package maksab.sd.customer.ui.main.activties;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.speciality.SpecialityModel;
import maksab.sd.customer.storage.OrderStorage;
import maksab.sd.customer.ui.main.adapters.SelectSpecialtyListAdapter;
import maksab.sd.customer.ui.orders.activities.SelectServiceActivity;
import maksab.sd.customer.util.general.SelectedSpecialityModel;
import maksab.sd.customer.viewmodels.providers.ProvidersListViewModel;
import maksab.sd.customer.wizards.neworder.NewOrderWizardActivity;

public class SelectSpecialtyActivity extends BaseActivity {

    private List<SpecialityModel> specialtyModels;
    private SelectSpecialtyListAdapter selectSpecialtyListAdapter;
    private ListView listView;
    private ProvidersListViewModel providersListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_specialty);
        providersListViewModel = ViewModelProviders.of(this).get(ProvidersListViewModel.class);
        providersListViewModel.getCatSpecialtyObservable().observe(this , getCatSpecialtyObserver());
        setUpToolBar();
        setupRecyclerView();
        showWaitDialog();
        providersListViewModel.getCatSpecialty(getIntent().getIntExtra("catid" , 0));
    }

    private void setUpToolBar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.select_specality);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupRecyclerView() {
        OrderStorage orderStorage = new OrderStorage(this);

        listView = findViewById(R.id.list_view);
        specialtyModels = new ArrayList<>();
        selectSpecialtyListAdapter = new SelectSpecialtyListAdapter(this, specialtyModels);
        listView.setAdapter(selectSpecialtyListAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            SpecialityModel specialty = specialtyModels.get(i);
            if(specialty.getSpecialtySelectionTypeId() == 1){
               orderStorage.saveSelectedSpecaility(new SelectedSpecialityModel( specialtyModels.get(i).getCategoryId() , specialty.getId() ,
                       specialty.getArabicName() ,  specialty.getServiceQuestionDescription()));
               Intent intent = new Intent(this , NewOrderWizardActivity.class);
               startActivity(intent);
           }else{
               Intent intent = new Intent(this ,  SelectServiceActivity.class);
               intent.putExtra("specialty_id" , specialty.getId());
               orderStorage.saveSelectedSpecaility(new SelectedSpecialityModel( specialtyModels.get(i).getCategoryId() , specialty.getId() ,
                       specialty.getArabicName() ,  specialty.getServiceQuestionDescription()));

               startActivity(intent);
           }
        });

    }

    @NonNull
    private Observer<List<SpecialityModel>> getCatSpecialtyObserver() {
        return catSpecialtyModels -> {
            dismissWaitDialog();
            if(catSpecialtyModels !=null) {
                specialtyModels.addAll(catSpecialtyModels);
                selectSpecialtyListAdapter.notifyDataSetChanged();
            }
        };
    }
}
