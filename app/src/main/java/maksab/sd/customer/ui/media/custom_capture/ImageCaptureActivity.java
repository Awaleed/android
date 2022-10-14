package maksab.sd.customer.ui.media.custom_capture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.FileCallback;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Facing;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.FileUtils;
import maksab.sd.customer.util.general.ImageCompress;
import maksab.sd.customer.util.general.ImageVideoUtil;

public class ImageCaptureActivity extends BaseActivity {
    @BindView(R.id.camera_view)
    CameraView camera_view;
    @BindView(R.id.camera_capture_button)
    Button camera_capture_button;
    @BindView(R.id.switch_button)
    FloatingActionButton switch_button;

    private File outputDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        outputDirectory = ImageVideoUtil.getOutputDirectory(Enums.DirectoryName);
        camera_view.setLifecycleOwner(this);
        camera_view.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(PictureResult result) {
                File file = ImageVideoUtil.createEmptyFile(outputDirectory, ".jpg");
                result.toFile(file, new FileCallback() {
                    @Override
                    public void onFileReady(@Nullable File file) {
                        File newFile = new File(ImageCompress.resize(file.getAbsolutePath()));
                        Intent intent = new Intent();
                        intent.putExtra("File", newFile.getAbsolutePath());
                        intent.putExtra("CustomType", Enums.FileType.IMAGE);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }
        });

        camera_capture_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera_view.takePicture();
            }
        });

        switch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (camera_view.getFacing() == Facing.BACK) {
                    camera_view.setFacing(Facing.FRONT);
                }
                else if (camera_view.getFacing() == Facing.FRONT) {
                    camera_view.setFacing(Facing.BACK);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera_view.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera_view.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera_view.destroy();
    }
}
