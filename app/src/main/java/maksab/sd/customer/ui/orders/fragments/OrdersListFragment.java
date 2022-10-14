package maksab.sd.customer.ui.orders.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.events.EndlessRecyclerViewScrollListener;
import maksab.sd.customer.models.providers.OrderModel;
import maksab.sd.customer.ui.orders.activities.OrderDetailsActivity;
import maksab.sd.customer.ui.orders.adapters.OrdersListAdapter;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.viewmodels.orders.BasicOrdersViewModel;

public class OrdersListFragment extends Fragment {
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.no_data_layout)
    ViewGroup no_data_layout;
    @BindView(R.id.main_progress)
    ProgressBar main_progressbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.open_orders_btn)
    Chip open_orders_btn;
    @BindView(R.id.closed_orders_btn)
    Chip closed_orders_btn;
    @BindView(R.id.canceled_orders_btn)
    Chip canceled_orders_btn;

    private OrdersListAdapter ordersListAdapter;
    private List<OrderModel> ordersModelList;
    private BasicOrdersViewModel basicOrdersViewModel;
    private EndlessRecyclerViewScrollListener scrollListener;

    private int currentStatus = Enums.CustomerCombinedQueryEnum.All.ordinal();

    public static OrdersListFragment newInstance(int combinedStatusId) {
        OrdersListFragment ordersListFragment = new OrdersListFragment();
        Bundle args = new Bundle();
        args.putInt("order.status", combinedStatusId);
        ordersListFragment.setArguments(args);
        return ordersListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        currentStatus = getArguments().getInt("order.status");
        setupRecyclerView();

        open_orders_btn.setChecked(true);
        initChips();

        basicOrdersViewModel = ViewModelProviders.of(this).get(BasicOrdersViewModel.class);
        ordersObservable();

        main_progressbar.setVisibility(View.VISIBLE);
        getOrders(1, currentStatus);
    }

    private void setupRecyclerView() {
        ordersModelList = new ArrayList<>();
        ordersListAdapter = new OrdersListAdapter(ordersModelList, view1 -> {
            OrderModel orderModel = ordersModelList.get(recyclerView.getChildAdapterPosition(view1));

            if (orderModel.getOrderStatusId() == Enums.OrderStatusEnum.WaitingProviders.ordinal() ||
                    orderModel.getOrderStatusId() == Enums.OrderStatusEnum.PENDING.ordinal()) {
                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                intent.putExtra("id", orderModel.getId());
                intent.putExtra("order.status", orderModel.getOrderStatusId());
                intent.putExtra("order.isQuotation", true);
                intent.putExtra("order.type", orderModel.getOrderTypeId());
                intent.putExtra("order.provider_id", orderModel.getProviderId());
                startActivityForResult(intent, 100);
            } else  {
                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                intent.putExtra("id", orderModel.getId());
                intent.putExtra("order.status", orderModel.getOrderStatusId());
                intent.putExtra("order.isQuotation", false);
                intent.putExtra("order.type", orderModel.getOrderTypeId());
                intent.putExtra("order.provider_id", orderModel.getProviderId());
                startActivityForResult(intent, 100);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(ordersListAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getOrders(page, currentStatus);
            }
        };

        recyclerView.addOnScrollListener(scrollListener);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            clear();
            getOrders(1, currentStatus);
        });
    }

    private void clear() {
        ordersModelList.clear();
        ordersListAdapter.notifyDataSetChanged();
        scrollListener.resetState();
    }

    private void initChips() {
        int page = 1;
        open_orders_btn.setOnClickListener(view -> {
            clear();
            currentStatus = Enums.CustomerCombinedQueryEnum.Opening.ordinal();
            getOrders(page, currentStatus);
        });

        closed_orders_btn.setOnClickListener(view -> {
            clear();
            currentStatus = Enums.CustomerCombinedQueryEnum.Completed.ordinal();
            getOrders(page, currentStatus);
        });

        canceled_orders_btn.setOnClickListener(view -> {
            clear();
            currentStatus = Enums.CustomerCombinedQueryEnum.Canceled.ordinal();
            getOrders(page, currentStatus);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            clear();
            getOrders(1, currentStatus);
        }
    }

    private void ordersObservable() {
        basicOrdersViewModel.getBasicOrdersObservable().observe(getActivity(), orders -> {
            main_progressbar.setVisibility(View.GONE);
            if (orders != null) {
                ordersModelList.addAll(orders);
                ordersListAdapter.notifyItemRangeChanged(ordersListAdapter.getItemCount(), ordersModelList.size());

                swipeRefreshLayout.setRefreshing(false);

                if (ordersListAdapter.getItemCount() == 0) {
                    no_data_layout.setVisibility(View.VISIBLE);
                } else {
                    no_data_layout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getOrders(int page, int orderStatus) {
        main_progressbar.setVisibility(View.VISIBLE);
        no_data_layout.setVisibility(View.GONE);
        basicOrdersViewModel.getBasicOrders(page, orderStatus);
    }

    @Override
    public void onResume() {
        super.onResume();
        clear();
        getOrders(1, currentStatus);
    }
}
