package maksab.sd.customer.ui.orders.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.events.BottomSheetEvents;
import maksab.sd.customer.basecode.events.EndlessRecyclerViewScrollListener;
import maksab.sd.customer.basecode.fragments.FragmentsContract;
import maksab.sd.customer.basecode.fragments.MessageDialog;
import maksab.sd.customer.models.orders.details.OrderDetails;
import maksab.sd.customer.models.services.ServiceModel;
import maksab.sd.customer.models.speciality.SubSpecialtyViewModel;
import maksab.sd.customer.network.appnetwork.ICatalogService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.OrderInMemoryStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.entities.CenterActivity;
import maksab.sd.customer.ui.entities.ShopActivity;
import maksab.sd.customer.ui.orders.adapters.ServicesAdapter;
import maksab.sd.customer.ui.providers.activties.ProviderDetailsActivity;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesListFragment extends Fragment implements FragmentsContract, BottomSheetEvents {
    @BindView(R.id.items_recyclerview)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.main_progress)
    ProgressBar progressBar;
    @BindView(R.id.no_data_layout)
    ViewGroup noDataLayout;
    @BindView(R.id.chipGroup)
    ChipGroup chipGroup;
    @BindView(R.id.about_textview)
    TextView about_textview;

    @BindView(R.id.scroll_view)
    HorizontalScrollView scroll_view;

    private ServicesAdapter itemsAdapter;
    private List<ServiceModel> itemModelList = new ArrayList<>();
    private EndlessRecyclerViewScrollListener scrollListener;

    public static ServicesListFragment newInstance(int specialtyId, int specialtyTypeId) {
        ServicesListFragment fragment = new ServicesListFragment();
        Bundle args = new Bundle();
        args.putInt("specialty_id", specialtyId);
        args.putInt("SpecialtyTypeId", specialtyTypeId);
        fragment.setArguments(args);
        return fragment;
    }

    public static ServicesListFragment newInstance(int specialtyId, int specialtyTypeId, String userId) {
        ServicesListFragment fragment = new ServicesListFragment();
        Bundle args = new Bundle();
        args.putInt("specialty_id", specialtyId);
        args.putInt("SpecialtyTypeId", specialtyTypeId);
        args.putString("Provider.UserID", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genraic_service, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        initChips();
        initItems();
    }

    private void initChips() {
        int specialtyId = getArguments().getInt("specialty_id");
        String userId = getArguments().getString("Provider.UserID");
        int specialtyTypeId = getArguments().getInt("SpecialtyTypeId");

        if (specialtyTypeId == Enums.SpecialtyTypeEnum.Center.ordinal()) {
            about_textview.setVisibility(View.VISIBLE);
            about_textview.setText(R.string.about_center);
            about_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity() == null)
                        return;

                    Intent intent = new Intent(getActivity(), CenterActivity.class);
                    intent.putExtra("UserId", userId);
                    startActivity(intent);
                }
            });
        } else if (specialtyTypeId == Enums.SpecialtyTypeEnum.Shop.ordinal()) {
            about_textview.setVisibility(View.VISIBLE);
            about_textview.setText(R.string.about_shop);
            about_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity() == null)
                        return;

                    Intent intent = new Intent(getActivity(), ShopActivity.class);
                    intent.putExtra("UserId", userId);
                    startActivity(intent);
                }
            });
        } else {
            about_textview.setVisibility(View.GONE);
        }

        ILocalStorage localStorage = new SharedPreferencesStorage(getActivity());
        ICatalogService catalogService = GetWayServiceGenerator.createService(ICatalogService.class, "Bearer " + localStorage.getJwtToken().getStringToken());
        Call<List<SubSpecialtyViewModel>> call = null;

        if (StringUtils.isEmpty(userId)) {
            call = catalogService.getSubSpecialties(specialtyId);
        } else {
            call = catalogService.getProviderSubSpecialties(userId, specialtyId);
        }

        call.enqueue(new Callback<List<SubSpecialtyViewModel>>() {
            @Override
            public void onResponse(Call<List<SubSpecialtyViewModel>> call, Response<List<SubSpecialtyViewModel>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    List<SubSpecialtyViewModel> items = response.body();
                    if (items.size() > 0) {
                        noDataLayout.setVisibility(View.GONE);
                        buildChips(items);
                    } else {
                        fetchItem();
                        noDataLayout.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SubSpecialtyViewModel>> call, Throwable t) {
            }
        });
    }

    private void initItems() {
        if (getActivity() == null)
            return;

        itemsAdapter = new ServicesAdapter(itemModelList, view -> {
            ServiceModel serviceModel = itemModelList.get(itemsRecyclerView.getChildAdapterPosition(view));

            if (OrderInMemoryStorage.IsItemFromDifferentSpeciality(serviceModel.getSpecialtyId())) {
                MessageDialog.showMessageDialog((BaseActivity) getActivity(), getString(R.string.sorry), getString(R.string.can_not_add_items_from_other_service));
                return;
            }


            ProductDetailsBottomSheetDialog productDetailsBottomSheetDialog =
                    ProductDetailsBottomSheetDialog.newInstance(serviceModel
                            , true, itemsRecyclerView.getChildAdapterPosition(view), this);
            productDetailsBottomSheetDialog.show(getChildFragmentManager(), productDetailsBottomSheetDialog.getTag());
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), mLayoutManager.getOrientation());
        itemsRecyclerView.addItemDecoration(dividerItemDecoration);
        itemsRecyclerView.setLayoutManager(mLayoutManager);
        itemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        itemsRecyclerView.setAdapter(itemsAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getItems(page, getSelectedChipId());
            }
        };

        itemsRecyclerView.addOnScrollListener(scrollListener);

        swipeRefreshLayout.setOnRefreshListener(() -> fetchItem());
    }

    private void getItems(int page, int subspecialtyId) {
        noDataLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        if (getActivity() == null)
            return;

        int specialtyId = getArguments().getInt("specialty_id");
        String userId = getArguments().getString("Provider.UserID");

        ILocalStorage localStorage = new SharedPreferencesStorage(getContext());
        ICatalogService catalogService = GetWayServiceGenerator.createService(ICatalogService.class, "bearer " + localStorage.getJwtToken().getStringToken());

        Call<List<ServiceModel>> call = null;

        if (StringUtils.isEmpty(userId)) {
            call = catalogService.getActiveMaksabServices(specialtyId, subspecialtyId, page);
        } else {
            call = catalogService.getActiveProviderServices(userId, subspecialtyId, page);
        }

        call.enqueue(new Callback<List<ServiceModel>>() {
            @Override
            public void onResponse(Call<List<ServiceModel>> call, Response<List<ServiceModel>> response) {
                if (response.isSuccessful()) {
                    List<ServiceModel> results = response.body();
                    progressBar.setVisibility(View.GONE);

                    fillQuantityToSelectedService(results);
                    fillQuantityToSelectedService(itemModelList);
                    itemModelList.addAll(results);
                    itemsAdapter.notifyDataSetChanged();

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
            public void onFailure(Call<List<ServiceModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void buildChips(List<SubSpecialtyViewModel> items) {
        if (getActivity() == null)
            return;

        for (SubSpecialtyViewModel item : items) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.item_chip_choice, null);
            Chip chip = view.findViewById(R.id.chip_item);
            chip.setText(item.getArabicName());
            chip.setCheckable(true);
            chip.setId(item.getId());
            chip.setTag(item.getId());
            chip.setLayoutDirection(View.LAYOUT_DIRECTION_LOCALE);
            chipGroup.addView(chip);
        }

        if (items.size() > 0) {
            ((Chip) chipGroup.getChildAt(0)).setChecked(true);
            fetchItem();
        }

        chipGroup.setOnCheckedChangeListener((chipGroup, i) -> {
            Chip chip = chipGroup.findViewById(i);
            if (chip != null) {
                int id = (Integer) chip.getTag();
                fetchItem();
            }
        });

        scroll_view.post(new Runnable() {
            @Override
            public void run() {
                scroll_view.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        });
    }

    private void fetchItem() {
        clear();
        getItems(1, getSelectedChipId());
    }

    private int getSelectedChipId() {
        int tagId = 0;
        if (chipGroup.getCheckedChipId() != View.NO_ID) {
            Chip chip = chipGroup.findViewById(chipGroup.getCheckedChipId());
            tagId = (Integer) chip.getTag();
        }
        return tagId;
    }

    private void clear() {
        if (itemModelList != null)
            itemModelList.clear();
        if (itemsAdapter != null)
            itemsAdapter.notifyDataSetChanged();
        if (scrollListener != null)
            scrollListener.resetState();
    }

    private void fillQuantityToSelectedService(List<ServiceModel> serviceModels) {
        for (ServiceModel serviceModel : serviceModels) {
            for (OrderDetails orderItem : OrderInMemoryStorage.getOrderItems()) {
                if (serviceModel.getId().equals(orderItem.getItemId())) {
                    serviceModel.setQuantity(orderItem.getQuantity());
                }
            }
        }
    }

    @Override
    public boolean isValidForm() {
        int selectedServicesCount = OrderInMemoryStorage.getOrderItems().size();
        return selectedServicesCount > 0;
    }

    @Override
    public String getErrorMessage() {
        return getString(R.string.select_at_least_one_service);
    }

    @Override
    public String getStepTitle(Context context) {
        if(getActivity() == null) return  "";
        return getActivity().getString(R.string.select_item_from_menu);
    }

    @Override
    public void saveChange() {
    }

    @Override
    public void onAddService(int position, int quantity) {
        ServiceModel serviceModel = itemModelList.get(position);
        serviceModel.setQuantity(quantity);
        itemsAdapter.notifyDataSetChanged();

        if (getActivity() == null)
            return;

        try {
            if (getActivity() instanceof ProviderDetailsActivity) {
                ((ProviderDetailsActivity) getActivity()).updateCart();
            }
        } catch (Exception exception) {
            FirebaseCrashlytics.getInstance().log(exception.getMessage());
        }
    }

    @Override
    public void onAddService() {

    }
}
