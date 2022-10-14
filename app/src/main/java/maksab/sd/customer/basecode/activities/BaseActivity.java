package maksab.sd.customer.basecode.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import maksab.sd.customer.R;
import maksab.sd.customer.util.general.LocaleHelper;

/**
 * Created by AdminUser on 10/14/2017.
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog  dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showWaitDialog() {
        dialog = ProgressDialog.show(this, "",
                getString(R.string.loading_please_wait), true);
    }

    public void showWaitDialog(String msg) {
        dialog = ProgressDialog.show(this, "",
                msg, true);
    }

    public void dismissWaitDialog(){
       if(dialog!=null){
           dialog.dismiss();
       }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }
}
