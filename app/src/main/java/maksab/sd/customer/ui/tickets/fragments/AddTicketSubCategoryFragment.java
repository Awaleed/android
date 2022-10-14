package maksab.sd.customer.ui.tickets.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import maksab.sd.customer.basecode.fragments.FragmentsContract;
import maksab.sd.customer.models.tickets.TicketInputModel;
import maksab.sd.customer.models.tickets.TicketItemTypeModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.tickets.activities.AddTicketWizardActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddTicketSubCategoryFragment extends Fragment implements FragmentsContract {
    @BindView(R.id.add_ticket_title)
    TextView add_ticket_title;
    @BindView(R.id.items_recyclerview)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private ILocalStorage localStorage;
    private TicketInputModel mTicketInputModel;
    private List<TicketItemTypeModel> itemModelList = new ArrayList<>();
    private TicketSubCategoryAdapter itemsAdapter;

    public static AddTicketSubCategoryFragment newInstance(TicketInputModel item) {
        AddTicketSubCategoryFragment fragment = new AddTicketSubCategoryFragment();
        Bundle args = new Bundle();
        args.putParcelable("TicketInputModel", item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_ticket_category_step, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        add_ticket_title.setText(R.string.add_sub_category);

        localStorage = new SharedPreferencesStorage(getActivity());

        mTicketInputModel = getArguments().getParcelable("TicketInputModel");

        initItems();
        getItems(mTicketInputModel.getTicketCategoryId());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clear();
                getItems(mTicketInputModel.getTicketCategoryId());
            }
        });
    }

    private void clear() {
        itemModelList.clear();
        itemsAdapter.notifyDataSetChanged();
    }

    private void getItems(int categoryId) {
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        GetWayServiceGenerator.createService(ICustomersService.class, token)
                .getTicketsItemsType(categoryId)
                .enqueue(new Callback<List<TicketItemTypeModel>>() {
                    @Override
                    public void onResponse(Call<List<TicketItemTypeModel>> call, Response<List<TicketItemTypeModel>> response) {
                        if (response.isSuccessful()) {
                            clear();

                            List<TicketItemTypeModel> results = response.body();

                            itemModelList.addAll(results);
                            itemsAdapter.notifyItemRangeChanged(itemsAdapter.getItemCount(), results.size());

                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TicketItemTypeModel>> call, Throwable t) {
                    }
                });
    }

    private void initItems() {
        localStorage = new SharedPreferencesStorage(getActivity());
        itemsAdapter = new TicketSubCategoryAdapter(itemModelList, getActivity());

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        itemsRecyclerView.setLayoutManager(mLayoutManager);
        itemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        itemsRecyclerView.setAdapter(itemsAdapter);
    }

    @Override
    public boolean isValidForm() {
        return mTicketInputModel.getTicketSubCategoryId() >= 1;
    }

    @Override
    public String getErrorMessage() {
        return getString(R.string.not_found);
    }

    @Override
    public String getStepTitle(Context context) {
        return getString(R.string.select_category);
    }

    @Override
    public void saveChange() {

    }

    public class TicketSubCategoryAdapter extends RecyclerView.Adapter<TicketSubCategoryAdapter.TicketSubCategoryViewHolder> {
        private List<TicketItemTypeModel> _itemModels;
        private Context context;

        public TicketSubCategoryAdapter(List<TicketItemTypeModel> items, Context context) {
            this._itemModels = items;
            this.context = context;
        }

        @NonNull
        @Override
        public TicketSubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TicketSubCategoryViewHolder itemViewHolder =
                    new TicketSubCategoryViewHolder(LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_category, parent, false));
            return itemViewHolder;
        }

        @Override
        public void onBindViewHolder(TicketSubCategoryViewHolder holder, int position) {
            TicketItemTypeModel item = _itemModels.get(position);

            holder.name_text_view.setText(String.valueOf(item.getArabicName()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTicketInputModel.setTicketSubCategoryId(item.getId());
                    mTicketInputModel.setSubCategoryName(item.getArabicName());

                    if (getActivity() instanceof AddTicketWizardActivity)
                        ((AddTicketWizardActivity) getActivity()).onNextClick();
                }
            });
        }

        @Override
        public int getItemCount() {
            return _itemModels.size();
        }

        class TicketSubCategoryViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.category_layout)
            View category_layout;
            @BindView(R.id.category_icon_image_view)
            ImageView category_icon_image_view;
            @BindView(R.id.name_text_view)
            TextView name_text_view;

            public TicketSubCategoryViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
