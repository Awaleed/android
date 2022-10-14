package maksab.sd.customer.ui.providers.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.providers.ProviderDetailsModel;

public class BasicInfoFragment extends Fragment {
    @BindView(R.id.profileGender)
    TextView profileGender;
    @BindView(R.id.profileNationality)
    TextView profileNationality;
    @BindView(R.id.profileCity)
    TextView profileCity;
    @BindView(R.id.full_bio)
    TextView profileBio;

    public static BasicInfoFragment newInstance(ProviderDetailsModel data) {
        BasicInfoFragment basicInfoFragment = new BasicInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("data", data);
        basicInfoFragment.setArguments(args);
        return basicInfoFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_info, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }


    private void initView() {
        ProviderDetailsModel model = getArguments().getParcelable("data");
        setDataToScreen(model);
    }

    private void setDataToScreen(ProviderDetailsModel fullProfile) {
        if (!TextUtils.isEmpty(fullProfile.getBio())) {
            profileBio.setText(fullProfile.getBio());
        }

        profileGender.setText(fullProfile.getGenderArabic());
        profileNationality.setText(fullProfile.getNationalityArabic());
        profileCity.setText(fullProfile.getCityArabic());
    }
}
