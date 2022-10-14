package maksab.sd.customer.ui.balance;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.profile.BalanceViewModel;
import maksab.sd.customer.models.profile.ProfileModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.tickets.activities.AddTicketWizardActivity;
import maksab.sd.customer.util.general.NumbersUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalancePaymentFragment extends Fragment {
    @BindView(R.id.total_income_text_view)
    TextView total_income_text_view;
    @BindView(R.id.add_ticket_btn)
    Button add_ticket_btn;

    public static BalancePaymentFragment newInstance() {
        BalancePaymentFragment fragment = new BalancePaymentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance_payment, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        add_ticket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddTicketWizardActivity.class);
                startActivity(intent);
            }
        });

        getFullProfile();
    }

    private void getFullProfile() {
        SharedPreferencesStorage localStorage = new SharedPreferencesStorage(getContext());
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        GetWayServiceGenerator.createService(ICustomersService.class, token)
            .getBalance()
            .enqueue(new Callback<BalanceViewModel>() {
            @Override
            public void onResponse(Call<BalanceViewModel> call, Response<BalanceViewModel> response) {
                if (response.isSuccessful()) {
                    BalanceViewModel balance = response.body();
                    total_income_text_view.setText(NumbersUtil.formatAmount(balance.getAdded()));
                }
            }

            @Override
            public void onFailure(Call<BalanceViewModel> call, Throwable t) {
            }
        });
    }
}
