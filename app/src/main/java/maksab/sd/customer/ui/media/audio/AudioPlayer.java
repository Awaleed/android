package maksab.sd.customer.ui.media.audio;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

public class AudioPlayer {
    public static void playLocalFile(String path) throws IOException {
        MediaPlayer mp = new MediaPlayer();
        mp.setDataSource(path);
        mp.prepare();
        mp.start();
    }

    public static void playUrl(String path) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(path.replace("\"", ""));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
