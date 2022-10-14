package maksab.sd.customer.ui.lookups.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.network.appnetwork.IUploadInterface;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.media.device_pickers.ImageIntentUtil;
import maksab.sd.customer.util.general.FileModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectImageActivity extends BaseActivity {

    @BindView(R.id.selected_image)
    ImageView selected_image;

    @BindView(R.id.save_image_btn)
    Button save_image_btn;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    int questionId = 0;
    String image_url;

    private static final int IMAGE_REQUEST_CODE = 90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        ButterKnife.bind(this);
        setupToolbar();
        questionId = getIntent().getIntExtra("questionId",0);
        selected_image.setOnClickListener(view -> {
            startActivityForResult(ImageIntentUtil.getPickImageIntent(this) , IMAGE_REQUEST_CODE);
        });

        save_image_btn.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra("questionId", questionId);
            intent.putExtra("image_url" ,image_url);
            setResult(RESULT_OK , intent);
            finish();
        });
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.select_images);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_REQUEST_CODE){
            try {
                File image = ImageIntentUtil.getImageFromResult(this , resultCode , data);
                selected_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                File file = new File(image.getPath());
                Picasso.with(this).load(file).placeholder(R.drawable.ic_menu_gallery).into(selected_image);
                showWaitDialog();
                uploadFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void uploadFile(File file){
        ILocalStorage localStorage = new SharedPreferencesStorage(this);
        String authToken = "bearer " + localStorage.getJwtToken().getStringToken();
        IUploadInterface customersService = GetWayServiceGenerator.createService(IUploadInterface.class , authToken);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part partfile = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        customersService.uploadFile(partfile).enqueue(new Callback<FileModel>() {
            @Override
            public void onResponse(Call<FileModel> call, Response<FileModel> response) {
                dismissWaitDialog();
                if(response.isSuccessful()){
                    image_url = response.body().getFilePath();
                    Log.d("image_url" , image_url);
                    save_image_btn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<FileModel> call, Throwable t) {
                dismissWaitDialog();
            }
        });
    }

}