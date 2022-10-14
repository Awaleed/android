package maksab.sd.customer.wizards.welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;

public class WizardFragment extends Fragment {
    @BindView(R.id.wizard_title)
    TextView wizard_title;
    @BindView(R.id.wizard_body)
    TextView wizard_body;
    @BindView(R.id.wizard_image)
    ImageView wizard_image;

    public static WizardFragment newInstance(WizardModel wizardModel) {
        WizardFragment fragment = new WizardFragment();
        Bundle arg = new Bundle();
        arg.putParcelable("wizardModel", wizardModel);
        fragment.setArguments(arg);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wizard, container, false);
        ButterKnife.bind(this, view);

        WizardModel wizardModel = getArguments().getParcelable("wizardModel");

        wizard_title.setText(wizardModel.getTitle());
        wizard_body.setText(wizardModel.getDescription());
        Picasso.with(getActivity())
                .load(wizardModel.getBackground())
                .into(wizard_image);

        return view;
    }
}
