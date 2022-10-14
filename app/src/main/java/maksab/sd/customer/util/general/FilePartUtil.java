package maksab.sd.customer.util.general;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FilePartUtil {
    public static MultipartBody.Part GenerateMultiPart(File file) throws UnsupportedEncodingException {
        String mediaType = ImageVideoUtil.getMediaType(file.getAbsolutePath());
        RequestBody requestBody = RequestBody.create(MediaType.parse(mediaType), file);
        String encodedFileName = URLEncoder.encode(file.getName(), "utf-8");
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", encodedFileName, requestBody);
        return filePart;
    }
}
