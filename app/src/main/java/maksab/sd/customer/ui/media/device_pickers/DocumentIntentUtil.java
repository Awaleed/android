package maksab.sd.customer.ui.media.device_pickers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

import maksab.sd.customer.util.general.FileUtils;

public class DocumentIntentUtil {
    public static Intent getDocumentPickupIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.putExtra("return-data", true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return intent;
    }

    public static File getDocumentFromResult(Context context, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri file = data.getData();
            return new File(FileUtils.getPath(context, file));
        }
        return null;
    }
}
