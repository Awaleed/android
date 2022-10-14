package maksab.sd.customer.basecode.utility;

import android.app.Application;

import io.branch.referral.Branch;

/**
 * Created by AdminUser on 10/13/2017.
 */

public class Appliction extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Branch.getAutoInstance(this);
    }


}

