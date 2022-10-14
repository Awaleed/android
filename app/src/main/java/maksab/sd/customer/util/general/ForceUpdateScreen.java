package maksab.sd.customer.util.general;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.ui.main.activties.MainActivity;

public class ForceUpdateScreen extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force_update_screen);
        Button update_btn = findViewById(R.id.update_btn);
        Button later_btn = findViewById(R.id.later_btn);
        boolean isForcedToUpdate = getIntent().getBooleanExtra("isForced_to_update" , false);
        if(isForcedToUpdate)  {
            later_btn.setVisibility(View.GONE);
        }

        later_btn.setOnClickListener(view -> {
            Intent intent = new Intent(this , MainActivity.class);
            intent.putExtra("profile.id" , getIntent().getStringExtra("profile.id"));
            startActivity(intent);
            finish();
        });

        update_btn.setOnClickListener(v -> {
            final String appPackageName = getPackageName();

            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
