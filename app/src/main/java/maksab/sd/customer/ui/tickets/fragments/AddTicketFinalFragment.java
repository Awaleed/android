package maksab.sd.customer.ui.tickets.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.fragments.FragmentsContract;
import maksab.sd.customer.models.tickets.TicketInputModel;
import maksab.sd.customer.ui.tickets.activities.AddTicketWizardActivity;


public class AddTicketFinalFragment extends Fragment implements FragmentsContract {
    @BindView(R.id.ticket_category_text_view)
    TextView ticket_category_text_view;
    @BindView(R.id.ticket_subcategory_text_view)
    TextView ticket_subcategory_text_view;
    @BindView(R.id.ticket_description_edit_text)
    TextInputEditText ticket_description_edit_text;
    @BindView(R.id.open_ticket_button)
    Button open_ticket_button;

    private TicketInputModel mTicketInputModel;

    public static AddTicketFinalFragment newInstance(TicketInputModel item) {
        AddTicketFinalFragment fragment = new AddTicketFinalFragment();
        Bundle args = new Bundle();
        args.putParcelable("TicketInputModel", item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_final_step, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        mTicketInputModel = getArguments().getParcelable("TicketInputModel");

        open_ticket_button.setOnClickListener(view -> {
            if (getActivity() instanceof AddTicketWizardActivity)
                ((AddTicketWizardActivity) getActivity()).onNextClick();
        });

        ticket_category_text_view.setText(mTicketInputModel.getCategoryName());
        ticket_subcategory_text_view.setText(mTicketInputModel.getSubCategoryName());
    }

    @Override
    public boolean isValidForm() {
        return !ticket_description_edit_text.getText().toString().isEmpty();
    }

    @Override
    public String getErrorMessage() {
        return getString(R.string.not_found);
    }

    @Override
    public String getStepTitle(Context context) {
        return getActivity().getString(R.string.add_ticket);
    }

    @Override
    public void saveChange() {
        mTicketInputModel.setBody(ticket_description_edit_text.getText().toString());
    }


}
