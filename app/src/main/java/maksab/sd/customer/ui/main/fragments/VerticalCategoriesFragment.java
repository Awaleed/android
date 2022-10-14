package maksab.sd.customer.ui.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.events.BottomSheetEvents;
import maksab.sd.customer.basecode.fragments.MessageDialog;
import maksab.sd.customer.basecode.fragments.SelectAddressSheet;
import maksab.sd.customer.models.home.HomeCategoryModel;
import maksab.sd.customer.models.main.HomeModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.general.fragments.WhyMaksabFragment;
import maksab.sd.customer.ui.main.adapters.VerticalCategoriesAdapter;
import maksab.sd.customer.ui.sections.activities.SectionsHomeLayoutActivity;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.AddressInMemoryStorage;
import maksab.sd.customer.util.general.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerticalCategoriesFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private VerticalCategoriesAdapter verticalCategoriesAdapter;
    private List<HomeCategoryModel> categoriesModels;
    private boolean isWhyMaksabAdded = false;
    private HomeCategoryModel selectedHomeCategoryModel;

    ILocalStorage localStorage;

    public static VerticalCategoriesFragment newInstance() {
        VerticalCategoriesFragment fragment = new VerticalCategoriesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vertical_categories, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        ButterKnife.bind(this, view);
        localStorage = new SharedPreferencesStorage(getContext());
        swipeRefresh.setOnRefreshListener(() -> {
            getCategories();
        });
        setupRecyclerView();
        getCategories();
    }

    private void setupRecyclerView() {
        categoriesModels = new ArrayList<>();

        verticalCategoriesAdapter = new VerticalCategoriesAdapter(view -> {
            int index = recycler_view.getChildAdapterPosition(view);
            if (index >= 0 && index < categoriesModels.size()) {
                selectedHomeCategoryModel = categoriesModels.get(index);
                if (selectedHomeCategoryModel.getCategoryStatus() == Enums.CategoryStatusEnum.Active.ordinal()) {
                    SelectAddressSheet selectAddressSheet =  SelectAddressSheet.newInstance(new BottomSheetEvents() {
                        @Override
                        public void onAddService(int position, int quantity) {

                        }

                        @Override
                        public void onAddService() {
                            if(AddressInMemoryStorage.id != 0 && !StringUtils.isEmpty(AddressInMemoryStorage.body)){
                                openCategoryDetails();
                            }
                        }
                    });
                    AddressInMemoryStorage.id = 0;
                    AddressInMemoryStorage.body = "";
                    AddressInMemoryStorage.district = "";
                    selectAddressSheet.show(getChildFragmentManager(), selectAddressSheet.getTag());
                }
                else if (selectedHomeCategoryModel.getCategoryStatus() == Enums.CategoryStatusEnum.Soon.ordinal()) {
                    if (getActivity() != null)
                        MessageDialog.showMessageDialog((BaseActivity) getActivity(),
                                selectedHomeCategoryModel.getArabicName(),
                                getString(R.string.please_wait_soon_will_activated));
                }
            }
        }, categoriesModels);

        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_view.setAdapter(verticalCategoriesAdapter);
    }

    private void openCategoryDetails() {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), SectionsHomeLayoutActivity.class);
            intent.putExtra("category.id", selectedHomeCategoryModel.getId());
            intent.putExtra("category.name", selectedHomeCategoryModel.getArabicName());
            startActivity(intent);
        }
    }

    private void getCategories() {
        categoriesModels.clear();
        ILocalStorage localStorage = new SharedPreferencesStorage(getContext());
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class, "bearer " + localStorage.getJwtToken().getStringToken());
        customersService.getHome().enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    categoriesModels.addAll(response.body().getCategories());
                    verticalCategoriesAdapter.notifyDataSetChanged();
                    setToolbar(response.body());

                    if (!isWhyMaksabAdded)
                        addWhyUseMaksab();
                }
            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void setToolbar(HomeModel homeModel) {
        if (getActivity() != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle(homeModel.getGreeting());
            localStorage.setGreetingMessage(homeModel.getGreeting());
        }
    }

    private void addWhyUseMaksab() {
        isWhyMaksabAdded = true;
        try {
            final FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.why_use_makasab_container, WhyMaksabFragment.newInstance());
            transaction.disallowAddToBackStack();
            transaction.commit();
        }catch (Exception exception){
            FirebaseCrashlytics.getInstance().log(exception.getMessage());
        }
    }

}