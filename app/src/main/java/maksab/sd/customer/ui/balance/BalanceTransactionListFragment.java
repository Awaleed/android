package maksab.sd.customer.ui.balance;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
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
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.balance.models.BalanceTransactionViewModel;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.DateUtil;
import maksab.sd.customer.util.general.NumbersUtil;
import maksab.sd.customer.util.general.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalanceTransactionListFragment extends Fragment {
    @BindView(R.id.items_recyclerview)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.main_progress)
    ProgressBar progressBar;
    @BindView(R.id.no_data_layout)
    ViewGroup noDataLayout;

    private EndlessRecyclerViewScrollListener scrollListener;
    private BalanceTransactionAdapter itemsAdapter;
    private List<BalanceTransactionViewModel> itemModelList = new ArrayList<>();

    public static BalanceTransactionListFragment newInstance() {
        BalanceTransactionListFragment fragment = new BalanceTransactionListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);

        progressBar.setVisibility(View.VISIBLE);

        initItems();
        getItems(1);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clear();
                getItems(1);
            }
        });



        return view;
    }

    private void clear() {
        itemModelList.clear();
        itemsAdapter.notifyDataSetChanged();
        scrollListener.resetState();
    }

    private void getItems(int page) {
        SharedPreferencesStorage localStorage = new SharedPreferencesStorage(getActivity());
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        GetWayServiceGenerator.createService(ICustomersService.class, token)
                .getBalanceTransactions(page)
                .enqueue(new Callback<List<BalanceTransactionViewModel>>() {
                    @Override
                    public void onResponse(Call<List<BalanceTransactionViewModel>> call, Response<List<BalanceTransactionViewModel>> response) {

                        if (response.isSuccessful()) {
                            List<BalanceTransactionViewModel> results = response.body();
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
                    public void onFailure(Call<List<BalanceTransactionViewModel>> call, Throwable t) {

                    }
                });
    }

    private void initItems() {
        itemsAdapter = new BalanceTransactionAdapter(itemModelList, getActivity());
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        itemsRecyclerView.setLayoutManager(mLayoutManager);
        itemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        itemsRecyclerView.setAdapter(itemsAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getItems(page);
            }
        };

        itemsRecyclerView.addOnScrollListener(scrollListener);
    }

    public void update() {
        clear();
        getItems(1);
    }

    static class BalanceTransactionAdapter extends RecyclerView.Adapter<BalanceTransactionAdapter.AccountTransactionViewHolder> {
        private List<BalanceTransactionViewModel> itemsList;
        private Context context;

        public BalanceTransactionAdapter(List<BalanceTransactionViewModel> itemsList, Context context) {
            this.itemsList = itemsList;
            this.context = context;
        }

        @Override
        public AccountTransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_balance_transaction, parent, false);

            return new AccountTransactionViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(AccountTransactionViewHolder holder, int position) {
            final BalanceTransactionViewModel itemModel = itemsList.get(position);

            DateUtil parser = DateUtil.newInstance();

            parser.parse(itemModel.getCreatedOn());
            String lastLine =
                    context.getString(R.string.date) + " " +
                    parser.getDateTimeString();

            holder.transaction_date_byuser.setText(lastLine);


            String fullMessage = "";
//            if (!StringUtils.isEmpty(itemModel.getSupportFullName())) {
//                fullMessage = context.getString(R.string.action_by) + ": " +
//                        itemModel.getSupportFullName() + "\n";
//            }

            fullMessage += context.getString(R.string.notes) + ": " +
                    itemModel.getDescription();

            if (!StringUtils.isEmpty(itemModel.getExpiredOn())) {
                parser.parse(itemModel.getExpiredOn());
                fullMessage += "\n" + context.getString(R.string.end_on)  +
                        parser.getDateString();
            }

            holder.transaction_description.setText(fullMessage);

            holder.transaction_type.setText(itemModel.getBalanceTransactionType().getArabicName());
            holder.transaction_amount.setText(NumbersUtil.formatAmount(itemModel.getAmount()));

            if (itemModel.getAmount() >= 0) {
                holder.transaction_amount.setTextColor(context.getResources().getColor(R.color.colorAccent));
            }
            else {
                holder.transaction_amount.setTextColor(context.getResources().getColor(R.color.red));
            }

            if (itemModel.getBalanceTransactionTypeId() == Enums.BalanceTransactionTypeEnum.Deposit.ordinal() ||
                    itemModel.getBalanceTransactionTypeId() == Enums.BalanceTransactionTypeEnum.Widthdraw.ordinal())
            {
                String channelOffice =
                        itemModel.getMaksabOffice().getArabicName() + " - " +
                                itemModel.getBalanceTransactionChannel().getArabicName();
                holder.channel_office.setText(channelOffice);
            }
            else {
                String channelOffice =
                        itemModel.getBalanceTransactionChannel().getArabicName();
                holder.channel_office.setText(channelOffice);
            }
        }

        @Override
        public int getItemCount() {
            return itemsList.size();
        }

        public static class AccountTransactionViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.transaction_date_byuser)
            TextView transaction_date_byuser;
            @BindView(R.id.transaction_type)
            TextView transaction_type;
            @BindView(R.id.transaction_description)
            TextView transaction_description;
            @BindView(R.id.transaction_amount)
            TextView transaction_amount;
            @BindView(R.id.channel_office)
            TextView channel_office;

            public AccountTransactionViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
