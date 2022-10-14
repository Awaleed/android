package maksab.sd.customer.ui.general.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import maksab.sd.customer.models.faq.FaqViewModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.general.activities.FaqDetailActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqListFragment extends Fragment {
    @BindView(R.id.items_recyclerview)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.main_progress)
    ProgressBar progressBar;
    @BindView(R.id.no_data_layout)
    ViewGroup noDataLayout;

    private ILocalStorage localStorage;
    private List<FaqViewModel> itemModelList = new ArrayList<>();
    private FaqsAdapter itemsAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;

    public static FaqListFragment newInstance(int faqTypeId) {
        FaqListFragment fragment = new FaqListFragment();
        Bundle args = new Bundle();
        args.putInt("FaqTypeId", faqTypeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        clear();
        getItems(1);
    }

    private void initViews() {
        localStorage = new SharedPreferencesStorage(getActivity());
        progressBar.setVisibility(View.VISIBLE);

        initItems();
    }

    private void clear() {
        itemModelList.clear();
        itemsAdapter.notifyDataSetChanged();
        scrollListener.resetState();
    }

    private void getItems(int page) {
        int faqTypeId = getArguments().getInt("FaqTypeId");

        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        GetWayServiceGenerator.createService(ICustomersService.class, token)
                .getFaqs(faqTypeId, page)
                .enqueue(new Callback<List<FaqViewModel>>() {
                    @Override
                    public void onResponse(Call<List<FaqViewModel>> call, Response<List<FaqViewModel>> response) {
                        if (response.isSuccessful()) {
                            List<FaqViewModel> results = response.body();
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
                    public void onFailure(Call<List<FaqViewModel>> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void initItems() {
        itemsAdapter = new FaqsAdapter(getActivity(), itemModelList);

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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clear();
                getItems(1);
            }
        });

    }

    public static class FaqsAdapter extends RecyclerView.Adapter<FaqsAdapter.FaqViewHolder> {
        private List<FaqViewModel> _itemModels;
        private Context context;

        public FaqsAdapter(Context context, List<FaqViewModel> items) {
            this._itemModels = items;
            this.context = context;
        }

        @NonNull
        @Override
        public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            FaqViewHolder itemViewHolder =
                    new FaqViewHolder(LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_faq, parent, false));
            return itemViewHolder;
        }

        @Override
        public void onBindViewHolder(FaqViewHolder holder, int position) {
            FaqViewModel item = _itemModels.get(position);
            holder.faq_title_text_view.setText(item.getArabicTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FaqDetailActivity.class);
                    intent.putExtra("FaqId", item.getId());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return _itemModels.size();
        }

        class FaqViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.faq_title_text_view)
            TextView faq_title_text_view;

            public FaqViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
