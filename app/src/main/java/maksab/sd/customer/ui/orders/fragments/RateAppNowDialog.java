package maksab.sd.customer.ui.orders.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.fragments.MessageDialog;

public class RateAppNowDialog extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.rate_app_now_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView rate_us = view.findViewById(R.id.rate_us);
        TextView another_time = view.findViewById(R.id.another_time);
        TextView no_thanks = view.findViewById(R.id.no_thanks);
        another_time.setOnClickListener(view1 -> {
            this.dismiss();
        });
        no_thanks.setOnClickListener(view1 -> {
            this.dismiss();
        });

        rate_us.setOnClickListener(view1 -> {
            openGooglePlayManually();
        });
    }

    private void openGooglePlayManually() {
        final String appPackageName = getContext().getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static void showDialog(FragmentManager fragmentManager) {
        RateAppNowDialog messageDialog = new RateAppNowDialog();
        messageDialog.setCancelable(false);
        messageDialog.show(fragmentManager, "MessageDialog");
    }
}
