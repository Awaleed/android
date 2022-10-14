package maksab.sd.customer.ui.media.device_pickers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.FileUtils;
import maksab.sd.customer.util.general.ImageVideoUtil;

public class VideoIntentUtil {
    private static File tempFile;

    public static Intent getVideoPickupIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent.setType("video/*");
        return intent;
    }

    public static Intent getVideoRecordingIntent(Context context) {
        boolean haveFeature = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);

        if (haveFeature) {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            tempFile = ImageVideoUtil.createEmptyFile(ImageVideoUtil.getOutputDirectory(Enums.DirectoryName), ".mp4");
            if (tempFile != null) {
                Uri uri = ImageVideoUtil.getFileUri(context, tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.putExtra("return-data", true);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 180); // 3 mintues
                //intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); //0 for low, 1 higth
                //intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT,size*1048576L);//X mb *1024*1024
                return intent;
            }
        }

        return null;
    }

    public static File getVideoFromResult(Context context, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            boolean isCamera = (data == null ||
                    data.getData() == null ||
                    (tempFile != null && data.getData().toString().contains(tempFile.toString())));

            if (isCamera) {     /** CAMERA **/
                return tempFile;
            } else {            /** ALBUM **/
                Uri file = data.getData();
                return new File(FileUtils.getPath(context, file));
            }
        }
        return null;
    }
}
