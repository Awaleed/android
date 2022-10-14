package maksab.sd.customer.ui.media.device_pickers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.BitmapUtil;
import maksab.sd.customer.util.general.FileUtils;
import maksab.sd.customer.util.general.ImageCompress;
import maksab.sd.customer.util.general.ImageVideoUtil;

public class ImageIntentUtil {

    private static File tempFile;

    public static Intent getPickImageCameraOnlyIntent(Context context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean haveFeature = intent.resolveActivity(context.getPackageManager()) != null;

        if (haveFeature) {
            tempFile = ImageVideoUtil.createEmptyFile(ImageVideoUtil.getOutputDirectory(Enums.DirectoryName), ".jpg");
            if (tempFile != null) {
                Uri uri = ImageVideoUtil.getFileUri(context, tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.putExtra("return-data", true);
                return intent;
            }
        }

        return null;
    }

    public static Intent getPickImageGalleryOnlyIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return intent;
    }

    public static Intent getPickImageIntent(Context context) {
        Intent chooserIntent = null;
        List<Intent> intentList = new ArrayList<>();

        Intent pickIntent = getPickImageGalleryOnlyIntent();
        Intent takePhotoIntent = getPickImageCameraOnlyIntent(context);

        intentList = addIntentsToList(context, intentList, pickIntent);
        intentList = addIntentsToList(context, intentList, takePhotoIntent);

        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1),
                    context.getString(R.string.chooseSource));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }

        return chooserIntent;
    }

    private static List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent) {
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);
        }
        return list;
    }

    public static File getImageFromResult(Context context, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            boolean isCamera = (data == null ||
                    data.getData() == null ||
                    (tempFile != null && data.getData().toString().contains(tempFile.toString())));
            if (isCamera) {     /** CAMERA **/
                //return tempFile;
                String path = ImageCompress.resize(tempFile.getAbsolutePath());
                return new File(path);
            }
            else {            /** ALBUM **/
                Uri uri = data.getData();
                //new File(FileUtils.getPath(context, uri));
                try {
                    InputStream stream = context.getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    Bitmap resized = ImageCompress.resize(bitmap);
                    File file = new BitmapUtil(context).saveBitmapFile(resized);
                    return file;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
