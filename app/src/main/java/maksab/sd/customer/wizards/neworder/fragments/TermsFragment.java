package maksab.sd.customer.wizards.neworder.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.fragments.FragmentsContract;

public class TermsFragment extends Fragment implements FragmentsContract {
    @BindView(R.id.terms_textview)
    TextView terms_textview;

    public static TermsFragment newInstance(String htmlTerms) {
        TermsFragment fragment = new TermsFragment();
        Bundle args = new Bundle();
        args.putString("htmlTerms" , htmlTerms);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        ButterKnife.bind(this , view);
        terms_textview.setText(Html.fromHtml(getArguments().getString("htmlTerms")));
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
        return context.getString(R.string.terms_condations);
    }

    @Override
    public void saveChange() {

    }
}