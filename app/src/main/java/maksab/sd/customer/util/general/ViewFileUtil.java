package maksab.sd.customer.util.general;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;

import maksab.sd.customer.R;

public class ViewFileUtil {
//    public static void ViewFile(Context context, String path) {
//        try {
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(path));
//            context.startActivity(i);
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }

    public static int getFileIcon(String body) {
        if (body.toLowerCase().endsWith("pdf"))
            return R.drawable.pdf;
        else if (body.toLowerCase().endsWith("doc") || body.toLowerCase().endsWith("docx"))
            return R.drawable.doc;
        else if (body.toLowerCase().endsWith("ppt") || body.toLowerCase().endsWith("pptx"))
            return R.drawable.ppt;
        else if (body.toLowerCase().endsWith("xls") || body.toLowerCase().endsWith("xlsx"))
            return R.drawable.xls;
        return R.drawable.filezipped;
    }

    public static void openFile2(Context context, File file) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, MimeTypeMap.getSingleton().getExtensionFromMimeType(file.getName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "Open " + file.getName() + " with ..."));
    }

    public static void ViewFile(Context context, String url) {
        try {
            //Uri.parse(path)
            Uri uri = Uri.parse(url);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (url.toLowerCase().contains(".doc") || url.toLowerCase().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (url.toLowerCase().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (url.toLowerCase().contains(".ppt") || url.toLowerCase().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.toLowerCase().contains(".xls") || url.toLowerCase().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (url.toLowerCase().contains(".zip")) {
                // ZIP file
                intent.setDataAndType(uri, "application/zip");
            } else if (url.toLowerCase().contains(".rar")){
                // RAR file
                intent.setDataAndType(uri, "application/x-rar-compressed");
            } else if (url.toLowerCase().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (url.toLowerCase().contains(".wav") || url.toLowerCase().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (url.toLowerCase().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (url.toLowerCase().contains(".jpg") || url.toLowerCase().contains(".jpeg") || url.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (url.toLowerCase().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (url.toLowerCase().contains(".3gp") || url.toLowerCase().contains(".mpg") ||
                    url.toLowerCase().contains(".mpeg") || url.toLowerCase().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                intent.setDataAndType(uri, "*/*");
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No application found which can open the file", Toast.LENGTH_SHORT).show();
        }
    }
}
