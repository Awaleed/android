package maksab.sd.customer.util.general;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import maksab.sd.customer.R;

public class PermissionUtil {
    public static void showSnackBar(Context context, int layoutResourceId) {
        Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(layoutResourceId),
                R.string.no_camera_file_permission, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.allow_permission, view -> {
            askPermissions(context, 0);
        });
        snackbar.show();
    }

    public static boolean isPermissionEnabled(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasPermissions(Context context, String[] permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("PERMISSIONS", "Permission is not granted: " + permission);
                    return false;
                }
                Log.d("PERMISSIONS", "Permission already granted: " + permission);
            }
            return true;
        }
        return false;
    }

    public static void askPermissions(Context context, int callBackCode) {
        if (!isPermissionEnabled(context, Manifest.permission.CAMERA) ||
                !isPermissionEnabled(context, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                !isPermissionEnabled(context, Manifest.permission.READ_EXTERNAL_STORAGE) ){
            ActivityCompat.requestPermissions(((Activity) context),
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    callBackCode);
        }
    }
}