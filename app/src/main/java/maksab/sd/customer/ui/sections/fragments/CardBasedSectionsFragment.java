package maksab.sd.customer.ui.sections.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.fragments.MessageDialog;
import maksab.sd.customer.models.categories.CardBasedCategoriesModel;
import maksab.sd.customer.models.speciality.SpecialitySubscriptionInputModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.OrderInMemoryStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.orders.activities.ServicesActivity;
import maksab.sd.customer.ui.providers.activties.ProviderListActivity;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.AddressInMemoryStorage;
import maksab.sd.customer.util.general.StringUtils;
import maksab.sd.customer.wizards.neworder.NewOrderWizardActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardBasedSectionsFragment extends Fragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.placeholderlayout)
    ViewGroup placeholderlayout;
    @BindView(R.id.main_progress)
    ProgressBar progressBar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_layout)
    ViewGroup title_layout;

    private LinearLayoutManager layoutManager;
    private CardBasedSectionsAdapter _basdCardBasedSectionsAdapter;
    private List<CardBasedCategoriesModel> _cateCardBasedCategoriesModels;

    public static CardBasedSectionsFragment newInstance(ArrayList<CardBasedCategoriesModel> cardBasedCategoriesModels, String title) {
        CardBasedSectionsFragment fragment = new CardBasedSectionsFragment();
        Bundle args = new Bundle();
        args.putString("title" , title);
        args.putParcelableArrayList("items" , cardBasedCategoriesModels);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_card_based_categories, container, false);
       initView(view);
        return view;
    }

    private void initView(View view){
        _cateCardBasedCategoriesModels = new ArrayList<>();
        ButterKnife.bind(this , view);
        setupRecyclerView();

        if (_cateCardBasedCategoriesModels == null ||
             _cateCardBasedCategoriesModels.isEmpty()) {
            title_layout.setVisibility(View.GONE);
        }
        else {
            title_layout.setVisibility(View.VISIBLE);
        }

        String titleString = getArguments().getString("title");
        if(!StringUtils.isEmpty(titleString)){
            title.setText(titleString);
        }
    }

    private void setupRecyclerView(){
        recyclerview.setNestedScrollingEnabled(true);
        _cateCardBasedCategoriesModels = getArguments().getParcelableArrayList("items");
        _basdCardBasedSectionsAdapter = new CardBasedSectionsAdapter(_cateCardBasedCategoriesModels , view -> {
            CardBasedCategoriesModel specialityModel = _cateCardBasedCategoriesModels.get(recyclerview.getChildAdapterPosition(view));
            if(!specialityModel.isCoverage()){
                MessageDialog.showMessageDialog((BaseActivity) getActivity(), getString(R.string.sorry) , getString(R.string.service_not_coverd));
                subscribeToSpeciality(specialityModel);
                return;
            }
            OrderInMemoryStorage.SpecialtyType = specialityModel.getSpecialtyType();

            if (specialityModel.getSpecialtySelectionTypeId() == Enums.SpecialtyUISelectionEnum.ProvidersList.ordinal()){
                Intent intent = new Intent(getActivity() , ProviderListActivity.class);
                intent.putExtra("selected.speciality", specialityModel.getId());
                intent.putExtra("selected.speciality.title", specialityModel.getLabel());
                getActivity().startActivity(intent);
            }
            else if(specialityModel.getSpecialtySelectionTypeId() == Enums.SpecialtyUISelectionEnum.ProviderServicesList.ordinal()){
                Intent intent = new Intent(getActivity() , ServicesActivity.class);
                intent.putExtra("speciality.id", specialityModel.getId());
                getActivity().startActivity(intent);
            }
            else if(specialityModel.getSpecialtySelectionTypeId() == Enums.SpecialtyUISelectionEnum.QuotationWizard.ordinal() ||
                    specialityModel.getSpecialtySelectionTypeId() == Enums.SpecialtyUISelectionEnum.MaksabServicesWizard.ordinal()){
                Intent intent = new Intent(getActivity() , NewOrderWizardActivity.class);
                intent.putExtra("speciality.id" , specialityModel.getId());
                intent.putExtra("speciality.transportation_price" , specialityModel.getTransportationPrice());
                getActivity().startActivity(intent);
            }
        });

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL , false);

        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(_basdCardBasedSectionsAdapter);
        _basdCardBasedSectionsAdapter.notifyDataSetChanged();
    }

    private void subscribeToSpeciality(CardBasedCategoriesModel specialityModel) {
        ILocalStorage localStorage = new SharedPreferencesStorage(getContext());
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class,
                "bearer " + localStorage.getJwtToken().getStringToken());
        customersService.subscribeToSpeciality(new SpecialitySubscriptionInputModel(specialityModel.getId(),
                AddressInMemoryStorage.id)).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    class CardBasedSectionsAdapter extends RecyclerView.Adapter<CardBasedCategoriesViewHolder> {

        private View.OnClickListener _onClickListener;
        private List<CardBasedCategoriesModel> _cardBasedCategoriesModels;

        CardBasedSectionsAdapter(List<CardBasedCategoriesModel> cardBasedCategoriesModels , View.OnClickListener onClickListener){
            _onClickListener = onClickListener;
            _cardBasedCategoriesModels = cardBasedCategoriesModels;
        }

        @NonNull
        @Override
        public CardBasedCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CardBasedCategoriesViewHolder cardBasedCategoriesViewHolder =
                    new CardBasedCategoriesViewHolder(LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_card_based_categories, parent , false));
            cardBasedCategoriesViewHolder.itemView.setOnClickListener(_onClickListener);
            return cardBasedCategoriesViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CardBasedCategoriesViewHolder holder, int position) {
            CardBasedCategoriesModel model = _cardBasedCategoriesModels.get(position);
            holder.label.setText(model.getLabel());
//            Picasso.with(holder.item_image.getContext())
//                    .load(model.getImageUrl())
//                    //.transform(new BlurTransformation(holder.item_image.getContext()))
//                    .error(R.drawable.logo_small)
//                    .into(holder.item_image);

            Glide.with(holder.item_image.getContext())
                    .load(model.getImageUrl())
                    .override(300, 200)
                    .thumbnail(.05f)
                    .placeholder(R.drawable.logo_small)
                    .into(holder.item_image);
        }

        @Override
        public int getItemCount() {
            return _cardBasedCategoriesModels.size();
        }
    }

    class CardBasedCategoriesViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.label)
        TextView label;
        @BindView(R.id.item_image)
        ImageView item_image;

        public CardBasedCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}