package maksab.sd.customer.ui.media.viewer;

import android.content.Context;
import android.content.Intent;

import maksab.sd.customer.util.general.ImageZoomingActivity;

public class MediaActivityOpener {
    public static void openViewActivity(final Context context, final String path) {
        if (isVideo(path)) {
            Intent intent = new Intent(context, VideoViewerActivity.class);
            intent.putExtra("VideoPath", path);
            context.startActivity(intent);
        }
        else {
            Intent intent = new Intent(context, ImageZoomingActivity.class);
            intent.putExtra("ImagePath", path);
            context.startActivity(intent);
        }
    }

    public static boolean isVideo(String path) {
        if (path.toLowerCase().endsWith(".3gp") || path.toLowerCase().endsWith(".mpg") ||
                path.toLowerCase().endsWith(".mpeg") || path.toLowerCase().endsWith(".mpe") ||
                path.toLowerCase().endsWith(".mp4") || path.toLowerCase().endsWith(".avi"))
            return true;

        return false;
    }

    public static boolean isImage(String path) {
        return !isVideo((path));
    }
}