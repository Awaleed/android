package maksab.sd.customer.ui.main.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import maksab.sd.customer.R;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;

/**
 * Created by AdminUser on 02/19/2018.
 */

public class CompetitionDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_competition, container, false);
        Button btn = view.findViewById(R.id.sharebtn);
        Drawable icon = ResourcesCompat.getDrawable(getResources(),  R.drawable.ic_share_white , null);
        btn.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
        TextView body = view.findViewById(R.id.body);
        body.setText(getArguments().getString("body"));
        //empty comment
        btn.setOnClickListener(v -> showSharedialog(getArguments().getString("url")));
        CheckBox close = view.findViewById(R.id.close_checkbox);
        close.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                ILocalStorage localStorage = new SharedPreferencesStorage(this.getContext());
                localStorage.setCompetitionNotification(false);
            }
        });
        return view;
    }

    private void showSharedialog(String url){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_maksab));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_with)));
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
