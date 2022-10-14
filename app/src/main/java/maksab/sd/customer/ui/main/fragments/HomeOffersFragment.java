package maksab.sd.customer.ui.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.orders.details.HomeSpecialOfferModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.main.activties.SpecialOffersDetailsActivity;
import maksab.sd.customer.ui.main.adapters.SpecialOffersAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeOffersFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.main_progress)
    ProgressBar main_progress;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.no_data_layout)
    ViewGroup no_data_layout;

    private LinearLayoutManager layoutManager;
    private SpecialOffersAdapter specialOffersAdapter;
    private List<HomeSpecialOfferModel> homeSpecialOfferModels;
    private String providerId;
    boolean isProviderOffer = false;

    public static HomeOffersFragment newInstance(int categoryId) {
        HomeOffersFragment fragment = new HomeOffersFragment();
        Bundle args = new Bundle();
        args.putInt("category_id", categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    public static HomeOffersFragment newInstance(String providerId) {
        HomeOffersFragment fragment = new HomeOffersFragment();
        Bundle args = new Bundle();
        args.putString("providerId", providerId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_offers, container, false);
        initViews(view);
        return view;
    }

    void initViews(View view) {
        ButterKnife.bind(this, view);
        providerId = getArguments().getString("providerId");
        setupRecyclerView();
        getOffers();
    }

    private void setupRecyclerView() {
        if(providerId != null && !TextUtils.isEmpty(providerId))
            isProviderOffer = true;

        if(isProviderOffer)
            title.setVisibility(View.GONE);

        homeSpecialOfferModels = new ArrayList<>();
        int layoutType = TextUtils.isEmpty(providerId) ? RecyclerView.HORIZONTAL : RecyclerView.VERTICAL;
        layoutManager = new LinearLayoutManager(getContext(), layoutType, false);
        specialOffersAdapter = new SpecialOffersAdapter(homeSpecialOfferModels, view -> {
            HomeSpecialOfferModel homeSpecialOfferModel = homeSpecialOfferModels.get(recycler_view.getChildAdapterPosition(view));
            Intent intent = new Intent(getActivity(), SpecialOffersDetailsActivity.class);
            intent.putExtra("model", homeSpecialOfferModel);
            intent.putExtra("providerId" , providerId);
            startActivity(intent);
        }, isProviderOffer);
        recycler_view.setAdapter(specialOffersAdapter);
        recycler_view.setLayoutManager(layoutManager);
    }

    private void getOffers() {

        ILocalStorage localStorage = new SharedPreferencesStorage(getContext());
        String authToken = "bearer " + localStorage.getJwtToken().getStringToken();
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class, authToken);

        main_progress.setVisibility(View.VISIBLE);
        recycler_view.setVisibility(View.GONE);
        Call<List<HomeSpecialOfferModel>> offers = null;
        if(TextUtils.isEmpty(providerId)){
            int category_id = getArguments().getInt("category_id");
            offers = customersService.getOffers(category_id);
        }
        else{
            offers = customersService.getOffers(providerId);
        }

        offers.enqueue(new Callback<List<HomeSpecialOfferModel>>() {
            @Override
            public void onResponse(Call<List<HomeSpecialOfferModel>> call, Response<List<HomeSpecialOfferModel>> response) {
                if (response.isSuccessful()) {
                    List<HomeSpecialOfferModel> items = response.body();
                    if(items.isEmpty()){
                        if (isProviderOffer) {
                            recycler_view.setVisibility(View.GONE);
                            no_data_layout.setVisibility(View.VISIBLE);
                        }

                        title.setVisibility(View.GONE);
                    }

                    homeSpecialOfferModels.addAll(items);
                    specialOffersAdapter.notifyDataSetChanged();
                }
                main_progress.setVisibility(View.GONE);
                recycler_view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<HomeSpecialOfferModel>> call, Throwable t) {
                main_progress.setVisibility(View.GONE);
                recycler_view.setVisibility(View.VISIBLE);
            }
        });

    }
}