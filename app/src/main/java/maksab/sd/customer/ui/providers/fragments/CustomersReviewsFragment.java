package maksab.sd.customer.ui.providers.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.events.EndlessRecyclerViewScrollListener;
import maksab.sd.customer.models.providers.ProviderRateModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.providers.adapters.ProviderRatesAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomersReviewsFragment extends Fragment {
    @BindView(R.id.items_recyclerview)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.main_progress)
    ProgressBar progressBar;
    @BindView(R.id.no_data_layout)
    ViewGroup no_data_layout;

    private ProviderRatesAdapter itemsAdapter;
    private List<ProviderRateModel> itemModelList = new ArrayList<>();
    private EndlessRecyclerViewScrollListener scrollListener;

    public static CustomersReviewsFragment newInstance(String providerId) {
        CustomersReviewsFragment fragment = new CustomersReviewsFragment();
        Bundle args = new Bundle();
        args.putString("provider_id", providerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_rate, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        itemsAdapter = new ProviderRatesAdapter(itemModelList);
        itemsRecyclerView.setLayoutManager(layoutManager);
        itemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        itemsRecyclerView.setAdapter(itemsAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getItems(page);
            }
        };

        itemsRecyclerView.addOnScrollListener(scrollListener);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clear();
                getItems(1);
            }
        });

        getItems(1);
    }

    private void clear() {
        itemModelList.clear();
        itemsAdapter.notifyDataSetChanged();
        scrollListener.resetState();
    }

    private void getItems(int page) {
        progressBar.setVisibility(View.VISIBLE);
        String providerId = getArguments().getString("provider_id");
        ILocalStorage localStorage = new SharedPreferencesStorage(getContext());
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class, "Bearer " + localStorage.getJwtToken().getStringToken());
        customersService.getProviderReviews(providerId, page)
                .enqueue(new Callback<List<ProviderRateModel>>() {
            @Override
            public void onResponse(Call<List<ProviderRateModel>> call, Response<List<ProviderRateModel>> response) {
                if (response.isSuccessful()) {
                    List<ProviderRateModel> results = response.body();
                    progressBar.setVisibility(View.GONE);

                    itemModelList.addAll(results);
                    itemsAdapter.notifyItemRangeChanged(itemsAdapter.getItemCount(), results.size());

                    swipeRefreshLayout.setRefreshing(false);

                    if (itemsAdapter.getItemCount() == 0) {
                        no_data_layout.setVisibility(View.VISIBLE);
                    } else {
                        no_data_layout.setVisibility(View.GONE);
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<ProviderRateModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}