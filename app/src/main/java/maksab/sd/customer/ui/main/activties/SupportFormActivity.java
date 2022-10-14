package maksab.sd.customer.ui.main.activties;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.main.SupportFormModel;
import maksab.sd.customer.viewmodels.main.SupportViewModel;


public class SupportFormActivity extends BaseActivity {

    SupportViewModel supportViewModel;
    EditText title;
    EditText body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_form);
        supportViewModel = ViewModelProviders.of(this).get(SupportViewModel.class);
        title = findViewById(R.id.formtitle);
        body = findViewById(R.id.formbdy);

        OnPostDone();
        SetUpToolBar();
        Button send_Button = findViewById(R.id.send_Button);
        send_Button.setOnClickListener(view -> {
            String titlest = title.getText().toString();
            String bodyst = body.getText().toString();
            if (!TextUtils.isEmpty(titlest) && !TextUtils.isEmpty(bodyst)) {
                showWaitDialog();
                supportViewModel.postForm(new SupportFormModel(titlest, bodyst, "customer", 0));
            } else {
                Toast.makeText(this, R.string.pleaseInterFIeld, Toast.LENGTH_LONG).show();
            }


        });

        Button callus = findViewById(R.id.callus);
        Button womenService = findViewById(R.id.callus_women);
        Button clean_service = findViewById(R.id.clean_service);

        callus.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:0113555535"));
            startActivity(callIntent);
        });

        clean_service.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:0112160154" ));
            startActivity(callIntent);
        });

        womenService.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:0116304232" ));
            startActivity(callIntent);
        });
    }

    private void SetUpToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.support);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void OnPostDone() {
        supportViewModel.PostFormObserver().observe(this, isdone -> {
            dismissWaitDialog();
            if (isdone) {
                Toast.makeText(this, R.string.thanksForMakeMaksabBetter, Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }


}
