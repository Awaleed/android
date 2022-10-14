package maksab.sd.customer.basecode.fragments;

import android.content.Context;

public interface FragmentsContract {
    boolean isValidForm();
    String getErrorMessage();
    String getStepTitle(Context context);
    void saveChange();
}
