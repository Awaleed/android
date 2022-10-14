package maksab.sd.customer.ui.orders.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.events.EndlessRecyclerViewScrollListener;
import maksab.sd.customer.models.orders.details.OrderOffer;
import maksab.sd.customer.models.providers.OrderModel;
import maksab.sd.customer.network.appnetwork.CustomersService;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.orders.activities.OrderDetailsActivity;
import maksab.sd.customer.ui.orders.adapters.OrderOffersAdapter;
import maksab.sd.customer.ui.providers.activties.ProviderDetailsActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffersFragment extends Fragment {
    @BindView(R.id.items_recyclerview)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.main_progress)
    ProgressBar progressBar;
    @BindView(R.id.no_data_layout)
    ViewGroup noDataLayout;

    private List<OrderOffer> itemModelList = new ArrayList<>();
    private OrderOffersAdapter itemsAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private ILocalStorage localStorage;
    private ProgressDialog progressDialog;

    public static OffersFragment newInstance(Long orderId, boolean isQuotationClosed) {
        OffersFragment fragment = new OffersFragment();
        Bundle args = new Bundle();
        args.putLong("OrderId", orderId);
        args.putBoolean("IsQuotationClosed", isQuotationClosed);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        ButterKnife.bind(this, view);
        localStorage = new SharedPreferencesStorage(getContext());
        progressDialog = new ProgressDialog(getContext());
        initItems();
        return view;
    }

    private void initItems() {
        long orderId = getArguments().getLong("OrderId");
        boolean isClosed = getArguments().getBoolean("IsQuotationClosed");

        itemsAdapter = new OrderOffersAdapter(getActivity(), itemModelList, isClosed,
                new OrderOffersAdapter.OrderOffersListener() {
                    @Override
                    public void openProviderProfile(String userId) {
                        Intent intent = new Intent(getActivity(), ProviderDetailsActivity.class);
                        intent.putExtra("isFromQuotation" , true);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }

                    @Override
                    public void openAcceptOrderOffer(int offerId) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                        dialog.setTitle(getString(R.string.accept_offer_from_provider))
                                .setMessage(R.string.accept_provider_offer_confirmation)
                                .setPositiveButton(R.string.yes_accept_offer, (dialogInterface, i) -> {
                            acceptOffer(orderId, offerId);
                        }).setNegativeButton(R.string.action_cancel, null).show();
                    }
                });

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        itemsRecyclerView.setLayoutManager(mLayoutManager);
        itemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        itemsRecyclerView.setAdapter(itemsAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getItems(orderId, page);
            }
        };

        itemsRecyclerView.addOnScrollListener(scrollListener);

        swipeRefreshLayout.setOnRefreshListener(() -> fetchData(orderId));

        progressBar.setVisibility(View.VISIBLE);
        getItems(orderId, 1);
    }

    private void fetchData(long orderId) {
        clear();
        getItems(orderId, 1);
    }

    private void clear() {
        itemModelList.clear();
        itemsAdapter.notifyDataSetChanged();
        scrollListener.resetState();
    }

    private void getItems(long orderId, int page) {
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class,
                "bearer " + localStorage.getJwtToken().getStringToken());

        customersService.getOrderOffers(orderId, page)
                .enqueue(new Callback<List<OrderOffer>>() {
            @Override
            public void onResponse(Call<List<OrderOffer>> call, Response<List<OrderOffer>> response) {
                if (response.isSuccessful()) {
                    List<OrderOffer> results = response.body();
                    progressBar.setVisibility(View.GONE);

                    itemModelList.addAll(results);
                    itemsAdapter.notifyItemRangeChanged(itemsAdapter.getItemCount(), results.size());

                    swipeRefreshLayout.setRefreshing(false);

                    if (itemsAdapter.getItemCount() == 0) {
                        noDataLayout.setVisibility(View.VISIBLE);
                    } else {
                        noDataLayout.setVisibility(View.GONE);
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<OrderOffer>> call, Throwable t) {

            }
        });
    }

    private void acceptOffer(long orderId, int offerId) {
        CustomersService customersService = new CustomersService();
        SharedPreferencesStorage localStorage = new SharedPreferencesStorage(getContext());

        progressDialog.show(getContext(),"" , getString(R.string.loading_please_wait), true);
        customersService.acceptOffer("bearer " + localStorage.getJwtToken().getStringToken(),
                orderId, offerId, new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Intent orderintent = new Intent(getActivity(), OrderDetailsActivity.class);
                    orderintent.putExtra("id", response.body().getId());
                    getActivity().startActivity(orderintent);
                    getActivity().finish();
                } else {
                    try {
                        Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                        getActivity().finish();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), getActivity().getResources().getText(R.string.internetError), Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        });
    }
}