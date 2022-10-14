package maksab.sd.customer.wizards.registeration.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.fragments.FragmentsContract;
import maksab.sd.customer.models.main.UpdateSourceModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectSourceFragment extends Fragment implements FragmentsContract {
    @BindView(R.id.radio_button_container)
    RadioGroup radio_button_container;

    public static SelectSourceFragment newInstance() {
        SelectSourceFragment fragment = new SelectSourceFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_source, container, false);
        initView(view);
        return view;
    }

    private String getSelectedRadio(){
        int radioId = radio_button_container.getCheckedRadioButtonId();
        View selectedRadioButton = radio_button_container.findViewById(radioId);
        if(selectedRadioButton != null){
            return selectedRadioButton.getTag().toString();
        }
        return "";

    }

    private void initView(View view){
        ButterKnife.bind(this , view);
    }

    @Override
    public boolean isValidForm() {
        return !TextUtils.isEmpty(getSelectedRadio());
    }

    @Override
    public String getErrorMessage() {
        return getString(R.string.select_at_least_one);
    }

    @Override
    public String getStepTitle(Context context) {
        return context.getString(R.string.how_do_you_know_maksab);
    }

    @Override
    public void saveChange() {
        ILocalStorage localStorage = new SharedPreferencesStorage(getActivity());
        ICustomersService customersService = GetWayServiceGenerator.createService(ICustomersService.class , tokenMaping(localStorage.getJwtToken().getStringToken()));
        customersService.updateSource(new UpdateSourceModel(getSelectedRadio(), "dont call me")).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity() , t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    private String tokenMaping(String token) {
        return "bearer " + token;
    }
}