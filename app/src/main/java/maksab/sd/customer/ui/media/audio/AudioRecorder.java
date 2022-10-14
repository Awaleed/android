package maksab.sd.customer.ui.media.audio;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;

import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.FileUtils;
import maksab.sd.customer.util.general.ImageVideoUtil;


public class AudioRecorder {
    private MediaRecorder recorder = new MediaRecorder();
    private File path;
    private Context context;

    public AudioRecorder(Context context) {
        this.context = context;
        File outputDirectory = ImageVideoUtil.getOutputDirectory(Enums.DirectoryName);
        this.path = ImageVideoUtil.createEmptyFile(outputDirectory, ".3gp");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void start()  {
        try {
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            if(Build.VERSION.SDK_INT < 26) {
                recorder.setOutputFile(path.getAbsolutePath());
            }
            else{
                recorder.setOutputFile(path);
            }
            recorder.prepare();
            recorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop()  {
        try {
            recorder.stop();
            recorder.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAudioPath() {
        return path.getAbsolutePath();
    }
}
