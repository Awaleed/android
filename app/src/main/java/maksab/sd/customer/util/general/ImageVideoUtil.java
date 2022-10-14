package maksab.sd.customer.util.general;


import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import androidx.core.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

import maksab.sd.customer.BuildConfig;

public class ImageVideoUtil {
    public static File getOutputDirectory(String appName) {
        String folderName = Environment.DIRECTORY_PICTURES;
        File externalPath = Environment.getExternalStoragePublicDirectory(folderName);
        File directory = new File(externalPath, appName);
        boolean isDirectoryCreated = directory.exists() || directory.mkdirs();
        return directory;
    }

    public static File createEmptyFile(File outputDirectory, String extension) {
        String FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";
        SimpleDateFormat mDateFormat = new SimpleDateFormat(FILENAME_FORMAT, Locale.ENGLISH);
        long timestamp = System.currentTimeMillis();
        String fileName = mDateFormat.format(timestamp)+ extension;
        File file = new File(outputDirectory, fileName);
        return file;
    }

    public static long getVideoDuration(String uriOfFile) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uriOfFile);
        long duration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        int width = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
        int height = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        retriever.release();
        return duration;
    }

    public static String getMediaType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static Uri getFileUri(Context context, File file) {
        Uri fileURI;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            fileURI = Uri.fromFile(file);
        } else {
            fileURI = FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".fileprovider", file);
        }
        return fileURI;
    }
}