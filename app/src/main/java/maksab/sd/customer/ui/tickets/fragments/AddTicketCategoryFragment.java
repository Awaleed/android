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
import maksab.sd.customer.models.tickets.TicketCategoryTypeModel;
import maksab.sd.customer.models.tickets.TicketInputModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.tickets.activities.AddTicketWizardActivity;
import maksab.sd.customer.util.constants.Enums;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddTicketCategoryFragment extends Fragment implements FragmentsContract {
    @BindView(R.id.add_ticket_title)
    TextView add_ticket_title;
    @BindView(R.id.items_recyclerview)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private ILocalStorage localStorage;
    private TicketInputModel mTicketInputModel;
    private List<TicketCategoryTypeModel> itemModelList = new ArrayList<>();
    private TicketCategoryAdapter itemsAdapter;

    public static AddTicketCategoryFragment newInstance(TicketInputModel item) {
        AddTicketCategoryFragment fragment = new AddTicketCategoryFragment();
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
        add_ticket_title.setText(R.string.choose_ticket_category);

        localStorage = new SharedPreferencesStorage(getActivity());

        mTicketInputModel = getArguments().getParcelable("TicketInputModel");

        initItems();
        getItems();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clear();
                getItems();
            }
        });
    }

    private void clear() {
        itemModelList.clear();
        itemsAdapter.notifyDataSetChanged();
    }

    private void getItems() {
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        GetWayServiceGenerator.createService(ICustomersService.class, token)
                .getTicketCategoriesType(Enums.UserTypeEnum.Customer.ordinal())
                .enqueue(new Callback<List<TicketCategoryTypeModel>>() {
                    @Override
                    public void onResponse(Call<List<TicketCategoryTypeModel>> call, Response<List<TicketCategoryTypeModel>> response) {
                        if (response.isSuccessful()) {
                            clear();

                            List<TicketCategoryTypeModel> results = response.body();

                            itemModelList.addAll(results);
                            itemsAdapter.notifyItemRangeChanged(itemsAdapter.getItemCount(), results.size());

                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TicketCategoryTypeModel>> call, Throwable t) {
                    }
                });
    }

    private void initItems() {
        localStorage = new SharedPreferencesStorage(getActivity());
        itemsAdapter = new TicketCategoryAdapter(itemModelList, getActivity());

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        itemsRecyclerView.setLayoutManager(mLayoutManager);
        itemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        itemsRecyclerView.setAdapter(itemsAdapter);
    }

    @Override
    public boolean isValidForm() {
        return mTicketInputModel.getTicketCategoryId() >= 1;
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


    public class TicketCategoryAdapter extends RecyclerView.Adapter<TicketCategoryAdapter.TicketCategoryViewHolder> {
        private List<TicketCategoryTypeModel> _itemModels;
        private Context context;

        public TicketCategoryAdapter(List<TicketCategoryTypeModel> items, Context context) {
            this._itemModels = items;
            this.context = context;
        }

        @NonNull
        @Override
        public TicketCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TicketCategoryViewHolder itemViewHolder =
                    new TicketCategoryViewHolder(LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_category, parent, false));
            return itemViewHolder;
        }

        @Override
        public void onBindViewHolder(TicketCategoryViewHolder holder, int position) {
            TicketCategoryTypeModel item = _itemModels.get(position);

            holder.name_text_view.setText(String.valueOf(item.getArabicName()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTicketInputModel.setTicketCategoryId(item.getId());
                    mTicketInputModel.setCategoryName(item.getArabicName());

                    if (getActivity() instanceof AddTicketWizardActivity)
                        ((AddTicketWizardActivity) getActivity()).onNextClick();
                }
            });
        }

        @Override
        public int getItemCount() {
            return _itemModels.size();
        }

        class TicketCategoryViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.category_layout)
            View category_layout;
            @BindView(R.id.category_icon_image_view)
            ImageView category_icon_image_view;
            @BindView(R.id.name_text_view)
            TextView name_text_view;

            public TicketCategoryViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
