package maksab.sd.customer.ui.lookups.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.lookup.SelectCouponModel;
import maksab.sd.customer.models.providers.CheckCuopon;
import maksab.sd.customer.models.providers.CouponModel;
import maksab.sd.customer.models.services.ClickEventHandler;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCouponActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.main_progress)
    ProgressBar main_progress;
    @BindView(R.id.no_data_layout)
    ViewGroup no_data_layout;
    @BindView(R.id.coupon)
    EditText coupon;
    @BindView(R.id.apply_code)
    Button apply_code;
    @BindView(R.id.coupon_status)
    TextView coupon_status;

    private List<SelectCouponModel> selectCouponModelList;
    private SelectCouponAdapter selectCouponAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coupon);
        ButterKnife.bind(this);
        apply_code.setOnClickListener(view -> {
            checkCoupon();
        });
        setupToolbar();
        setupRecyclerView();
        getCoupons();
    }


    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.select_copoun_first);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupRecyclerView(){
        selectCouponModelList = new ArrayList<>();
        selectCouponAdapter = new SelectCouponAdapter(selectCouponModelList, view -> {
            SelectCouponModel selectCouponModel = selectCouponModelList.get(view);
            Intent intent = new Intent();
            intent.putExtra("coupon" , selectCouponModel.getCode().toUpperCase());
            intent.putExtra("coupon_amount" , selectCouponModel.getDiscountAmount());
            setResult(RESULT_OK,intent);
            finish();
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerview.setAdapter(selectCouponAdapter);
    }

    private void getCoupons(){
        recyclerview.setVisibility(View.GONE);
        main_progress.setVisibility(View.VISIBLE);
        ILocalStorage localStorage = new SharedPreferencesStorage(this);
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , token);
        int specialityId = getIntent().getIntExtra("speciality.id" , 0);
        customersService.getActiveCoupons(specialityId)
                .enqueue(new Callback<List<SelectCouponModel>>() {
            @Override
            public void onResponse(Call<List<SelectCouponModel>> call, Response<List<SelectCouponModel>> response) {
                main_progress.setVisibility(View.GONE);
                if(response.isSuccessful() && response.body().size() > 0){
                    recyclerview.setVisibility(View.VISIBLE);
                    selectCouponModelList.addAll(response.body());
                    selectCouponAdapter.notifyDataSetChanged();
                }else {
                    recyclerview.setVisibility(View.GONE);
                    no_data_layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<SelectCouponModel>> call, Throwable t) {
                main_progress.setVisibility(View.GONE);
                Toast.makeText(SelectCouponActivity.this , t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkCoupon() {
        ILocalStorage localStorage = new SharedPreferencesStorage(this);
        String token = "bearer " + localStorage.getJwtToken().getStringToken();

        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class, token);
        coupon_status.setVisibility(View.GONE);
        customersService.checkCupon(new CheckCuopon(coupon.getText().toString())).enqueue(new Callback<CouponModel>() {
            @Override
            public void onResponse(Call<CouponModel> call, Response<CouponModel> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getIsActive()) {
                        coupon_status.setVisibility(View.VISIBLE);
                        coupon_status.setText(getString(R.string.problem_with_copoun) +response.body().getCouponStatusName());
                    }else{
                        Intent intent = new Intent();
                        intent.putExtra("coupon" , coupon.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<CouponModel> call, Throwable t) {
                coupon_status.setVisibility(View.GONE);
                Toast.makeText(SelectCouponActivity.this, getString(R.string.retry_again), Toast.LENGTH_LONG).show();
            }
        });
    }


    class SelectCouponAdapter extends RecyclerView.Adapter<SelectCouponViewHolder> {

        private List<SelectCouponModel> selectCouponModels;
        private ClickEventHandler clickEventHandler;

        public SelectCouponAdapter(List<SelectCouponModel> selectCouponModelList , ClickEventHandler onClickListener){
            this.selectCouponModels = selectCouponModelList;
            this.clickEventHandler = onClickListener;
        }

        @NonNull
        @Override
        public SelectCouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            SelectCouponViewHolder selectCouponViewHolder = new SelectCouponViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.select_coupon_row ,parent , false));

            return selectCouponViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SelectCouponViewHolder holder, int position) {
            SelectCouponModel selectCouponModel = selectCouponModels.get(position);
            holder.coupon_title.setText(selectCouponModel.getTitle());
            holder.coupon_description.setText(selectCouponModel.getDescription());
            holder.apply_code.setOnClickListener(view -> {
                clickEventHandler.handleClick(position);
            });
        }

        @Override
        public int getItemCount() {
            return selectCouponModels.size();
        }

    }

    class SelectCouponViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.coupon_title)
        TextView coupon_title;

        @BindView(R.id.coupon_description)
        TextView coupon_description;

        @BindView(R.id.apply_code)
        Button apply_code;

        public SelectCouponViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}