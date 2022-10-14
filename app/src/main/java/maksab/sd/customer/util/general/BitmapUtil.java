package maksab.sd.customer.util.general;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import maksab.sd.customer.util.constants.Enums;

public class BitmapUtil {
    private Context context;

    public BitmapUtil(Context context) {
        this.context = context;
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage,
                "Title", null);
        return Uri.parse(path);
    }

    public Bitmap generateThumbnailBitmap(File file) {
        Bitmap bMap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(),
                MediaStore.Video.Thumbnails.MICRO_KIND);
        return bMap;
    }

    public File saveThumbnailBitmap(Bitmap bitmap) {
        try {
            File newFile = saveBitmapFile(bitmap);
            return newFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public File saveBitmapFile(Bitmap bitmap) throws IOException {
        File outputDirectory = ImageVideoUtil.getOutputDirectory(Enums.DirectoryName);
        File mediaFile = ImageVideoUtil.createEmptyFile(outputDirectory, ".jpg");
        FileOutputStream fileOutputStream = new FileOutputStream(mediaFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        return mediaFile;
    }

    private int getQualityNumber(Bitmap bitmap) {
        int size = bitmap.getByteCount();
        int percentage = 15;

//        if (size > 500000 && size <= 800000) {
//            percentage = 15;
//        } else if (size > 800000 && size <= 1000000) {
//            percentage = 20;
//        } else if (size > 1000000 && size <= 1500000) {
//            percentage = 25;
//        } else if (size > 1500000 && size <= 2500000) {
//            percentage = 27;
//        } else if (size > 2500000 && size <= 3500000) {
//            percentage = 30;
//        } else if (size > 3500000 && size <= 4000000) {
//            percentage = 40;
//        } else if (size > 4000000 && size <= 5000000) {
//            percentage = 50;
//        } else if (size > 5000000) {
//            percentage = 75;
//        }

        return percentage;
    }

    private void galleryAddPic(File f) {
        Intent mediaScanIntent = new Intent(
                "android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);

        context.sendBroadcast(mediaScanIntent);
    }
}
