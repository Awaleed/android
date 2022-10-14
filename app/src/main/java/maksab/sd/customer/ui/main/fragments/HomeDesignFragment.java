package maksab.sd.customer.ui.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.categories.CardBasedCategoriesModel;
import maksab.sd.customer.models.home.HomeCategoryModel;
import maksab.sd.customer.models.main.HomeModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.main.adapters.HomeCategoriesAdapter;
import maksab.sd.customer.ui.sections.activities.SectionsHomeLayoutActivity;
import maksab.sd.customer.ui.sections.fragments.CardBasedSectionsFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeDesignFragment extends Fragment {
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;

    //categories variables
    @BindView(R.id.categories_recyclerview)
    RecyclerView categories_recyclerview;

    @BindView(R.id.category_layout)
    ViewGroup category_layout;

    //offers
    @BindView(R.id.offers_container)
    FrameLayout offers_containers;

    @BindView(R.id.offer_layout)
    ViewGroup offer_layout;


     //common specialities
    @BindView(R.id.common_specialities_container)
    FrameLayout common_specialities_container;

    @BindView(R.id.common_specialities_layout)
    ViewGroup common_specialities_layout;

    HomeCategoriesAdapter homeCategoriesAdapter;
    List<HomeCategoryModel> homeCategoryModels;


    public static HomeDesignFragment newInstance() {
        HomeDesignFragment fragment = new HomeDesignFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_design, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        ButterKnife.bind(this ,view);
        setupCategoriesRecyclerview();
        getHome();
        showOffers();
    }

    private void setDataToUi(HomeModel homeModel){
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(homeModel.getGreeting());
        ILocalStorage localStorage = new SharedPreferencesStorage(getContext());
        localStorage.setGreetingMessage(homeModel.getGreeting());

        if(homeModel.getCategories() != null){
            category_layout.setVisibility(View.VISIBLE);
            offer_layout.setVisibility(View.VISIBLE);
            common_specialities_layout.setVisibility(View.VISIBLE);
            homeCategoryModels.clear();
            homeCategoryModels.addAll(homeModel.getCategories());
            homeCategoriesAdapter.notifyDataSetChanged();
            showCommonSpecialities(homeModel.getCommonsSpecialties());
        }
    }

    private void getHome(){
        ILocalStorage localStorage = new SharedPreferencesStorage(getContext());
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , tokenMapping(localStorage.getJwtToken().getStringToken()));
        progress_bar.setVisibility(View.VISIBLE);
        customersService.getHome().enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {
                progress_bar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    setDataToUi(response.body());
                }
            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {
                progress_bar.setVisibility(View.GONE);
            }
        });
    }

    private void setupCategoriesRecyclerview(){
        categories_recyclerview.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        homeCategoryModels = new ArrayList<>();
        homeCategoriesAdapter = new HomeCategoriesAdapter(homeCategoryModels , view -> {
            HomeCategoryModel homeCategoryModel = homeCategoryModels.get(categories_recyclerview.getChildAdapterPosition(view));
            Intent intent = new Intent(getActivity() , SectionsHomeLayoutActivity.class);
            intent.putExtra("category.id" , homeCategoryModel.getId());
            startActivity(intent);

        });
        categories_recyclerview.setLayoutManager(gridLayoutManager);
        categories_recyclerview.setAdapter(homeCategoriesAdapter);
    }

    //offers
    private void showOffers(){
        final FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(offers_containers.getId() , HomeOffersFragment.newInstance(1));
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    // common specialities
    private void showCommonSpecialities(List<HomeModel.HomeSpecialtyModel> specialtyModelList){
        final FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        ArrayList<CardBasedCategoriesModel> commonSpecialities = new ArrayList<>();

        for (int i = 0; i < specialtyModelList.size() ; i++) {
            HomeModel.HomeSpecialtyModel homeSpecialtyModel = specialtyModelList.get(i);
            commonSpecialities.add(new CardBasedCategoriesModel(homeSpecialtyModel.getId(), homeSpecialtyModel.getArabicName() , homeSpecialtyModel.getImagePath(),
                    homeSpecialtyModel.getHtmlTerms(), homeSpecialtyModel.getHaveCustomQuestionsForm() , homeSpecialtyModel.getSpecialtySelectionTypeId(),
                    homeSpecialtyModel.isCoverage(),homeSpecialtyModel.getTransportationPrice(), homeSpecialtyModel.getSpecialtyType()));
        }

        transaction.replace(common_specialities_container.getId() , CardBasedSectionsFragment.newInstance(commonSpecialities, null));
        transaction.disallowAddToBackStack();
        transaction.commit();
    }



    private String tokenMapping(String token) {
        return "bearer " + token;
    }

}