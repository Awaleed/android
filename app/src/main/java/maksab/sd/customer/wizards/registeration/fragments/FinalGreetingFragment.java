package maksab.sd.customer.wizards.registeration.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.fragments.FragmentsContract;
import maksab.sd.customer.util.constants.Enums;

public class FinalGreetingFragment extends Fragment implements FragmentsContract {
    @BindView(R.id.final_greeting_textview)
    TextView final_greeting_textview;

    public static FinalGreetingFragment newInstance(int onBoardingStatusId) {
        FinalGreetingFragment fragment = new FinalGreetingFragment();
        Bundle args = new Bundle();
        args.putInt("OnBoardingStatusId", onBoardingStatusId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_final_greeting, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        int onBoardingStatusId = getArguments().getInt("OnBoardingStatusId");
        if (onBoardingStatusId == Enums.OnBoardingStatusEnum.OldAccount.ordinal()) {
            final_greeting_textview.setText(R.string.you_updating_successfully);
        }
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
        int onBoardingStatusId = getArguments().getInt("OnBoardingStatusId");
        if (onBoardingStatusId == Enums.OnBoardingStatusEnum.OldAccount.ordinal()) {
            return context.getString(R.string.you_updating_successfully);
        }

        return context.getString(R.string.you_registered_successfully);
    }

    @Override
    public void saveChange() {

    }
}