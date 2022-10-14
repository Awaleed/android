package maksab.sd.customer.ui.media.audio;

import android.animation.Animator;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import maksab.sd.customer.R;

// fixing issue on this library
// https://github.com/IvanSotelo/RecordDialog

public class AudioRecorderDialogFragment extends DialogFragment {
    private String _strTitle;
    private String _strMessage;
    private String _strPositiveButtonText;
    private FloatingActionButton _recordButton;
    private String STATE_BUTTON = "INIT";
    private TextView _timerView;
    private Timer _timer;
    private int recorderSecondsElapsed;
    private int playerSecondsElapsed;

    private ClickListener _clickListener;
    private AudioRecorder recorder;
    private MediaPlayer mediaPlayer;
    private MediaPlayer mPlayer;

    public static AudioRecorderDialogFragment newInstance(String title) {
        AudioRecorderDialogFragment frag = new AudioRecorderDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Getting the layout inflater to inflate the view in an alert dialog.
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View rootView = inflater.inflate(R.layout.dialog_audio_recorder, null);
        String strMessage = _strMessage == null ? "Presiona para grabar" : _strMessage;
        _timerView = rootView.findViewById(R.id._txtTimer);
        _timerView.setText(strMessage);

        recorder = new AudioRecorder(getActivity());

        _recordButton = rootView.findViewById(R.id.btnRecord);
        _recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleAnimation();
                switch (STATE_BUTTON) {
                    case "INIT":
                        _recordButton.setImageResource(R.drawable.ic_stop);
                        STATE_BUTTON = "RECORD";
                        try {
                            mPlayer = MediaPlayer.create(getContext(), R.raw.hangouts_message);
                            mPlayer.start();
                            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    recorder.start();
                                    startTimer();
                                }
                            });
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "RECORD":
                        recorder.stop();
                        mPlayer = MediaPlayer.create(getContext(), R.raw.hangouts_message);
                        mPlayer.start();

                        _recordButton.setImageResource(R.drawable.ic_play);
                        STATE_BUTTON = "STOP";
                        _timerView.setText(R.string.click_to_listen);
                        recorderSecondsElapsed = 0;
                        break;
                    case "STOP":
                        startMediaPlayer();
                        break;
                    case "PLAY":
                        pauseMediaPlayer();
                        break;
                    case "PAUSE":
                        resumeMediaPlayer();
                        break;
                }
            }
        });

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(rootView);

        String strPositiveButton = _strPositiveButtonText == null ? "CLOSE" : _strPositiveButtonText;
        alertDialog.setPositiveButton(strPositiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (STATE_BUTTON.equals("RECORD")){
                    recorder.stop();
                    stopTimer();
                }
                _clickListener.OnClickListener(recorder.getAudioPath());
            }
        });

        String strTitle = _strTitle == null ? "Grabar audio" : _strTitle;
        alertDialog.setTitle(strTitle);

        recorderSecondsElapsed = 0;
        playerSecondsElapsed = 0;

        final AlertDialog dialog = alertDialog.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN);

        return dialog;
    }

    // Change End

    public void setTitle(String strTitle) {
        _strTitle = strTitle;
    }

    public void setMessage(String strMessage) {
        _strMessage = strMessage;
    }

    public void setPositiveButton(String strPositiveButtonText, ClickListener onClickListener) {
        _strPositiveButtonText = strPositiveButtonText;
        _clickListener = onClickListener;
    }

    private void startMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(recorder.getAudioPath());
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopMediaPlayer();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        _recordButton.setImageResource(R.drawable.ic_pause);
        STATE_BUTTON = "PLAY";
        playerSecondsElapsed = 0;
        startTimer();
        mediaPlayer.start();
    }

    private void resumeMediaPlayer() {
        _recordButton.setImageResource(R.drawable.ic_pause);
        STATE_BUTTON = "PLAY";
        mediaPlayer.start();
    }

    private void pauseMediaPlayer() {
        _recordButton.setImageResource(R.drawable.ic_play);
        STATE_BUTTON = "PAUSE";
        mediaPlayer.pause();
    }

    private void stopMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            _recordButton.setImageResource(R.drawable.ic_play);
            STATE_BUTTON = "STOP";
            _timerView.setText("00:00:00");
            stopTimer();
        }
    }

    private void startTimer(){
        stopTimer();
        _timer = new Timer();
        _timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateTimer();
            }
        }, 0, 1000);
    }

    private void stopTimer(){
        if (_timer != null) {
            _timer.cancel();
            _timer.purge();
            _timer = null;
        }
    }

    private void updateTimer() {
        // here you check the value of getActivity() and break up if needed
        if(getActivity() == null)
            return;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(STATE_BUTTON.equals("RECORD")) {
                    recorderSecondsElapsed++;
                    _timerView.setText(formatSeconds(recorderSecondsElapsed));
                } else if(STATE_BUTTON.equals("PLAY")){
                    playerSecondsElapsed++;
                    _timerView.setText(formatSeconds(playerSecondsElapsed));
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void scaleAnimation() {
        final Interpolator interpolador = AnimationUtils.loadInterpolator(getContext(),
                android.R.interpolator.fast_out_slow_in);
        _recordButton.animate()
                .scaleX(1.1f)
                .scaleY(1.1f)
                .setInterpolator(interpolador)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        _recordButton.animate().scaleX(1f).scaleY(1f).start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

    public interface ClickListener {
        void OnClickListener(String path);
    }

    public static String formatSeconds(int seconds) {
        return getTwoDecimalsValue(seconds / 3600) + ":"
                + getTwoDecimalsValue(seconds / 60) + ":"
                + getTwoDecimalsValue(seconds % 60);
    }

    private static String getTwoDecimalsValue(int value) {
        if (value >= 0 && value <= 9) {
            return "0" + value;
        } else {
            return value + "";
        }
    }
}
