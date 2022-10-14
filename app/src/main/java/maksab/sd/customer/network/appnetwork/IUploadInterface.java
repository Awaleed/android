package maksab.sd.customer.network.appnetwork;

import maksab.sd.customer.util.general.FileModel;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IUploadInterface {
    @Multipart
    @POST("uploads")
    Call<FileModel> uploadFile(@Part MultipartBody.Part file);
}