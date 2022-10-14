package maksab.sd.customer.ui.providers.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.providers.ProviderSpecialtyModel;
import maksab.sd.customer.ui.providers.adapters.SpecialtyListAdapter;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.wizards.neworder.NewOrderWizardActivity;

public class SpecialitiesFragment extends Fragment {
    @BindView(R.id.items_recyclerview)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.main_progress)
    ProgressBar progressBar;
    @BindView(R.id.no_data_layout)
    ViewGroup noDataLayout;


    private SpecialtyListAdapter itemsAdapter;
    private List<ProviderSpecialtyModel> itemModelList = new ArrayList<>();

    private static final int SPECIALITIES_CODE = 2002;

    public static SpecialitiesFragment newInstance(ProviderSpecialtyModel[] providerSpecialtyModels , String providerId) {
        SpecialitiesFragment fragment = new SpecialitiesFragment();
        Bundle args = new Bundle();
       args.putParcelableArray("specialities" , providerSpecialtyModels);
       args.putString("provider_id" , providerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specialities, container, false);
        ButterKnife.bind(this, view);
        progressBar.setVisibility(View.VISIBLE);
        initItems();
        return view;
    }


    private void getItems() {
        ProviderSpecialtyModel[] results = (ProviderSpecialtyModel[]) getArguments().getParcelableArray("specialities");
        progressBar.setVisibility(View.GONE);
        List<ProviderSpecialtyModel> providerSpecialtyModels = Arrays.asList(results);

        itemModelList.addAll(providerSpecialtyModels);
        itemsAdapter.notifyItemRangeChanged(itemsAdapter.getItemCount(), providerSpecialtyModels.size());
        if (itemsAdapter.getItemCount() == 0) {
            noDataLayout.setVisibility(View.VISIBLE);
        } else {
            noDataLayout.setVisibility(View.GONE);
        }
    }

    private void initItems() {
        itemsAdapter = new SpecialtyListAdapter(itemModelList , position -> {
            ProviderSpecialtyModel providerSpecialtyModel = itemModelList.get(position);
            openOrderWizard(providerSpecialtyModel);
        });
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        itemsRecyclerView.setLayoutManager(mLayoutManager);
        itemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        itemsRecyclerView.setAdapter(itemsAdapter);
        getItems();
    }

    private void openOrderWizard(ProviderSpecialtyModel specialtyModel){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(getString(R.string.order_qouation)).setMessage(R.string.descrip_your_issue).setPositiveButton(R.string.yes_send_order_to_provider, (dialog1, which) -> {
            Intent intent = new Intent(getContext(), NewOrderWizardActivity.class);
            intent.putExtra("speciality.id" , specialtyModel.getSpecialtyId());
            intent.putExtra("disableSelectServices" , true);
            intent.putExtra("opened_from_profile", true);
            intent.putExtra("provider_id", getArguments().getString("provider_id"));
            intent.putExtra("order_type_id" , Enums.OrderTypeEnum.Quotation.ordinal());
            startActivity(intent);

        }).setNegativeButton(R.string.action_cancel , null).show();

    }

}