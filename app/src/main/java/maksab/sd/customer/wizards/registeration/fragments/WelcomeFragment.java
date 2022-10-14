package maksab.sd.customer.wizards.registeration.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.fragments.FragmentsContract;
import maksab.sd.customer.util.constants.Enums;

public class WelcomeFragment extends Fragment implements FragmentsContract {
    @BindView(R.id.update_message_textview)
    TextView update_message_textview;
    @BindView(R.id.image_view)
    ImageView image_view;

    public static WelcomeFragment newInstance(int onBoardingStatusId){
        WelcomeFragment fragment = new WelcomeFragment();
        Bundle args = new Bundle();
        args.putInt("OnBoardingStatusId", onBoardingStatusId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wellcome, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        int onBoardingStatusId = getArguments().getInt("OnBoardingStatusId");
        if (onBoardingStatusId == Enums.OnBoardingStatusEnum.OldAccount.ordinal()) {
            update_message_textview.setText(R.string.welcome_message_old_customer);
        }

        Picasso.with(getActivity())
                .load("https://maksab.s3.eu-west-2.amazonaws.com/static/others/DSC_2561.webp")
                .into(image_view);
    }

    @Override
    public boolean isValidForm() {
        return true;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public String getStepTitle(Context context) {
        return context.getString(R.string.welcome_to_app);
    }

    @Override
    public void saveChange() {

    }
}
