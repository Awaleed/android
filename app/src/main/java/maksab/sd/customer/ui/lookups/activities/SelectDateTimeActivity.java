package maksab.sd.customer.ui.lookups.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.events.OnSelectDay;
import maksab.sd.customer.basecode.events.OnSelectTime;
import maksab.sd.customer.models.lookup.DaySlotModel;
import maksab.sd.customer.models.lookup.TimeSlotModel;
import maksab.sd.customer.network.appnetwork.ICatalogService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.OrderInMemoryStorage;
import maksab.sd.customer.ui.lookups.adapters.SelectDayAdapter;
import maksab.sd.customer.ui.lookups.adapters.SelectTimeAdapter;
import maksab.sd.customer.util.constants.Enums;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectDateTimeActivity extends BaseActivity implements OnSelectDay , OnSelectTime {
    @BindView(R.id.day_recyclerView)
    RecyclerView day_recyclerView;
    @BindView(R.id.time_recyclerview)
    RecyclerView time_recyclerview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.accept_flexible_times)
    CheckBox accept_flexible_times;

    @BindView(R.id.select_time_label)
    TextView select_time_label;

    private SelectDayAdapter selectDayAdapter;
    private SelectTimeAdapter selectTimeAdapter;

    private List<TimeSlotModel> timeSlotModels;
    private List<DaySlotModel> daySlotModels;

    private int speciality = 0;
    private String selectedTime = "";
    private String selectedDate = "";
    private boolean isDateSelected = false;
    private boolean isTimeSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date_time);
        ButterKnife.bind(this);
        setupToolbar();
        initViews();
        changeTitleBasedOnSpecialityType();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.select_date_time);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initViews(){
        speciality = getIntent().getIntExtra("speciality.id" , 0);
        setupDaySlotsRecyclerView();
        setupTimeSlotsRecyclerView();
        getDays();
    }

    private void getDays(){
        ICatalogService catalogService = GetWayServiceGenerator.createService(ICatalogService.class);
        catalogService.getSpecialityDates(speciality)
                .enqueue(new Callback<List<DaySlotModel>>() {
            @Override
            public void onResponse(Call<List<DaySlotModel>> call, Response<List<DaySlotModel>> response) {
                if(response.isSuccessful()){
                    daySlotModels.addAll(response.body());
                    selectDayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<DaySlotModel>> call, Throwable t) {
                Toast.makeText(SelectDateTimeActivity.this, t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getTimes(String timeStamp){
        ICatalogService catalogService = GetWayServiceGenerator.createService(ICatalogService.class);
        catalogService.getSpecialityTimeSlot(speciality , timeStamp)
                .enqueue(new Callback<List<TimeSlotModel>>() {
                    @Override
                    public void onResponse(Call<List<TimeSlotModel>> call, Response<List<TimeSlotModel>> response) {
                        if(response.isSuccessful()){
                            timeSlotModels.clear();
                            timeSlotModels.addAll(response.body());
                            selectTimeAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TimeSlotModel>> call, Throwable t) {
                        Toast.makeText(SelectDateTimeActivity.this, t.getMessage() , Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setupDaySlotsRecyclerView(){
        daySlotModels = new ArrayList<>();
        selectDayAdapter = new SelectDayAdapter(daySlotModels , this);
        day_recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        day_recyclerView.setAdapter(selectDayAdapter);
    }

    private void setupTimeSlotsRecyclerView(){
        timeSlotModels = new ArrayList<>();
        selectTimeAdapter = new SelectTimeAdapter(timeSlotModels ,this);
        time_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        time_recyclerview.setAdapter(selectTimeAdapter);
    }

    @OnClick(R.id.select_time_btn)
    void onSelectTime(){
        if (isDateSelected && isTimeSelected) {
            Intent intent = new Intent();
            intent.putExtra("selected_time",selectedTime);
            intent.putExtra("selected_date",selectedDate);
            intent.putExtra("accept_flexible_times" , accept_flexible_times.isChecked());
            setResult(RESULT_OK, intent);
            finish();
        }
        else {
            Toast.makeText(this, R.string.enter_all_data_validation, Toast.LENGTH_SHORT).show();
        }
    }

    void changeTitleBasedOnSpecialityType(){
        if(OrderInMemoryStorage.SpecialtyType == Enums.SpecialtyTypeEnum.Home.ordinal()){
            select_time_label.setText(getText(R.string.select_time_for_home_service));
        }else{
            select_time_label.setText(getText(R.string.select_time_for_home_booking));
        }
    }

    @Override
    public void onDaySelected(int position) {
        getTimes(daySlotModels.get(position).getTimeStamp());
        DaySlotModel daySlotModel = daySlotModels.get(position);
        selectedDate = daySlotModel.getTimeStamp();
        isDateSelected = true;
    }

    @Override
    public void onTimeSelected(int position) {
        TimeSlotModel timeSlotModel = timeSlotModels.get(position);
        selectedTime = timeSlotModel.getItem();
        isTimeSelected = true;
    }
}