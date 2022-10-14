package maksab.sd.customer.ui.general.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.events.EndlessRecyclerViewScrollListener;
import maksab.sd.customer.models.speciality.SpecialtyDistrictTransportationPrice;
import maksab.sd.customer.network.appnetwork.ICatalogService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.util.general.NumbersUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TransportationPricesFragment extends Fragment {

    @BindView(R.id.items_recyclerview)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.main_progress)
    ProgressBar progressBar;
    @BindView(R.id.no_data_layout)
    ViewGroup noDataLayout;


    TransportationPricesAdapter adapter;
    List<SpecialtyDistrictTransportationPrice> specialtyDistrictTransportationPrices;
    EndlessRecyclerViewScrollListener scrollListener;
    int page = 1;

    public static TransportationPricesFragment newInstance(int specialityId , String specialityName) {
        TransportationPricesFragment fragment = new TransportationPricesFragment();
        Bundle args = new Bundle();
        args.putInt("speciality_id" , specialityId);
        args.putString("speciality_name" , specialityName);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transportation_prices, container, false);
        ButterKnife.bind(this , view);
        initViews();
        return view;
    }

    private void initViews(){
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getItems(page);
        });
        setupRecyclerview();
        getItems(page);
    }

    private void setupRecyclerview(){
        specialtyDistrictTransportationPrices = new ArrayList<>();
        adapter = new TransportationPricesAdapter(specialtyDistrictTransportationPrices);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        itemsRecyclerView.setLayoutManager(linearLayoutManager);
        itemsRecyclerView.setAdapter(adapter);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getItems(page);
            }
        };
        itemsRecyclerView.addOnScrollListener(scrollListener);

    }

    private void getItems(int page){
        specialtyDistrictTransportationPrices.clear();
        progressBar.setVisibility(View.VISIBLE);
        int speciality_id = getArguments().getInt("speciality_id");
        ILocalStorage localStorage = new SharedPreferencesStorage(getContext());
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        ICatalogService catalogService = GetWayServiceGenerator.createService(ICatalogService.class , token);
        catalogService.getDistrictTranposrationPrices(speciality_id , page).enqueue(new Callback<List<SpecialtyDistrictTransportationPrice>>() {
            @Override
            public void onResponse(Call<List<SpecialtyDistrictTransportationPrice>> call, Response<List<SpecialtyDistrictTransportationPrice>> response) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                specialtyDistrictTransportationPrices.addAll(response.body());
                adapter.notifyItemRangeChanged(adapter.getItemCount(), specialtyDistrictTransportationPrices.size());

                if (adapter.getItemCount() == 0) {
                    noDataLayout.setVisibility(View.VISIBLE);
                } else {
                    noDataLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<SpecialtyDistrictTransportationPrice>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public class TransportationPricesAdapter extends RecyclerView.Adapter<TransportationPricesViewHolder>{

        private List<SpecialtyDistrictTransportationPrice> specialtyDistrictTransportationPrices;
        public TransportationPricesAdapter(List<SpecialtyDistrictTransportationPrice> specialtyDistrictTransportationPrices){
            this.specialtyDistrictTransportationPrices = specialtyDistrictTransportationPrices;
        }

        @NonNull
        @Override
        public TransportationPricesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TransportationPricesViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_transporation_price,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull TransportationPricesViewHolder holder, int position) {
            SpecialtyDistrictTransportationPrice model = specialtyDistrictTransportationPrices.get(position);
            holder.district_name.setText(model.getDistrict().getArabicName());
            holder.transportation_price.setText(NumbersUtil.formatAmount(model.getPrice()));
        }

        @Override
        public int getItemCount() {
            return specialtyDistrictTransportationPrices.size();
        }
    }

    public class TransportationPricesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.transportation_price)
        TextView transportation_price;

        @BindView(R.id.district_name)
        TextView district_name;

        public TransportationPricesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}