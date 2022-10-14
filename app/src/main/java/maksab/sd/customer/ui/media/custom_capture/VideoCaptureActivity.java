package maksab.sd.customer.ui.media.custom_capture;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.VideoResult;
import com.otaliastudios.cameraview.controls.Facing;

import java.io.File;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.FileUtils;
import maksab.sd.customer.util.general.ImageVideoUtil;

public class VideoCaptureActivity extends BaseActivity {
    @BindView(R.id.camera_view)
    CameraView camera_view;
    @BindView(R.id.camera_capture_button)
    Button camera_capture_button;
    @BindView(R.id.switch_button)
    FloatingActionButton switch_button;
    @BindView(R.id.counter_textview)
    TextView counter_textview;

    private File outputDirectory;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_capture);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        outputDirectory = ImageVideoUtil.getOutputDirectory(Enums.DirectoryName);
        camera_view.setLifecycleOwner(this);
        camera_view.addCameraListener(new CameraListener() {
            @Override
            public void onVideoTaken(@NonNull VideoResult result) {
                File file = result.getFile();
                Intent intent = new Intent();
                intent.putExtra("File", file.getAbsolutePath());
                intent.putExtra("CustomType", Enums.FileType.VIDEO);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onVideoRecordingStart() {
                counter_textview.setVisibility(View.INVISIBLE);
                switch_button.setVisibility(View.INVISIBLE);
                camera_capture_button.setText(R.string.end_recording);
                startTimer();
            }

            @Override
            public void onVideoRecordingEnd() {
                if (timer != null)
                    timer.cancel();

                counter_textview.setVisibility(View.INVISIBLE);
                camera_capture_button.setVisibility(View.INVISIBLE);
            }
        });

        camera_capture_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (camera_view.isTakingVideo()) {
                    camera_view.stopVideo();
                }
                else {
                    File file = ImageVideoUtil.createEmptyFile(outputDirectory, ".mp4");
                    camera_view.takeVideo(file);
                }
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

    private void startTimer() {
        counter_textview.setVisibility(View.VISIBLE);

        timer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long tick) {
                String time =
                        String.format("%02d:%02d",
                                TimeUnit.MILLISECONDS.toMinutes(tick),
                                TimeUnit.MILLISECONDS.toSeconds(tick) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tick)));
                counter_textview.setText(time);
            }

            @Override
            public void onFinish() {
                camera_view.stopVideo();
            }
        }.start();
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
