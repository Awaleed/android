package maksab.sd.customer.ui.chat.chats;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.concurrent.TimeUnit;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.ui.media.audio.AudioRecorderDialogFragment;
import maksab.sd.customer.ui.media.custom_capture.ImageCaptureActivity;
import maksab.sd.customer.ui.media.custom_capture.VideoCaptureActivity;
import maksab.sd.customer.ui.media.device_pickers.ImageIntentUtil;
import maksab.sd.customer.ui.media.device_pickers.VideoIntentUtil;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.FileUtils;
import maksab.sd.customer.util.general.ImageVideoUtil;
import maksab.sd.customer.util.general.PermissionUtil;

public class ChatBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private int MSG_IMG_REQUEST_CODE = 9878;
    private int MSG_VIDEO_REQUEST_CODE = 9858;
    private int CUSTOM_CAMERA_REQUEST_CODE = 1874;
    private int ACCESS_PERMISSION_REQUEST_CODE = 7452;

    private ClickListener _clickListener;
    private Context context;

    public void setOnClickListener(ClickListener onClickListener, BaseActivity activity) {
        _clickListener = onClickListener;
        context = activity;
    }

    public interface ClickListener {
        void OnClickListener(File file, Enums.MessageTypeEnum messageTypeEnum);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermissions(PERMISSIONS);
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback =
            new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        dismiss();
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            };

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View bottomSheetView = View.inflate(context, R.layout.dialog_modal_bottom_sheet, null);
        dialog.setContentView(bottomSheetView);

        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) ((View) bottomSheetView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        bottomSheetView.findViewById(R.id.capture_image_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermissions(PERMISSIONS, Enums.FileType.IMAGE,
                        Enums.MediaDirectUse.DirectCamera);
                dialog.dismiss();
            }
        });

        bottomSheetView.findViewById(R.id.gallery_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermissions(PERMISSIONS, Enums.FileType.IMAGE,
                        Enums.MediaDirectUse.DirectGallery);
                dialog.dismiss();
            }
        });

        bottomSheetView.findViewById(R.id.video_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermissions(PERMISSIONS, Enums.FileType.VIDEO,
                        Enums.MediaDirectUse.Dialog);
                dialog.dismiss();
            }
        });

        bottomSheetView.findViewById(R.id.choose_file_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                //dialog.dismiss();
            }
        });

        bottomSheetView.findViewById(R.id.choose_audio_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecordingDialog();
                dialog.dismiss();
            }
        });

        bottomSheetView.findViewById(R.id.location_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                //dialog.dismiss();
            }
        });
    }

    // Permissions
    private ActivityResultContracts.RequestMultiplePermissions requestMultiplePermissionsContract;
    private ActivityResultLauncher<String[]> multiplePermissionActivityResultLauncher;

    private String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private void initPermissions(String[] permissions) {
        requestMultiplePermissionsContract = new ActivityResultContracts.RequestMultiplePermissions();

        multiplePermissionActivityResultLauncher = registerForActivityResult(requestMultiplePermissionsContract, isGranted -> {
            Log.d("PERMISSIONS", "Launcher result: " + isGranted.toString());
            if (isGranted.containsValue(false)) {
                Log.d("PERMISSIONS", "At least one of the permissions was not granted, launching again...");
                multiplePermissionActivityResultLauncher.launch(permissions);
            }
        });
    }

    private void askPermissions(String[] permissions, Enums.FileType type,
                                Enums.MediaDirectUse mediaDirectUse) {
        if (context != null) {
            if (!PermissionUtil.hasPermissions(context, permissions)) {
                Log.d("PERMISSIONS", "Launching multiple contract permission launcher for ALL required permissions");
                multiplePermissionActivityResultLauncher.launch(permissions);
            } else {
                showSelectionDialog(type, mediaDirectUse);
                Log.d("PERMISSIONS", "All permissions are already granted");
            }
        }
    }

    private void showSelectionDialog(Enums.FileType type, Enums.MediaDirectUse mediaDirectUse) {
        if (context == null)
            return;

        if (mediaDirectUse != Enums.MediaDirectUse.Dialog) {
            if (type == Enums.FileType.VIDEO) {
                if (mediaDirectUse == Enums.MediaDirectUse.DirectCamera) {
                    Intent intent = new Intent(context, VideoCaptureActivity.class);
                    ((BaseActivity) context).startActivityForResult(intent, CUSTOM_CAMERA_REQUEST_CODE);
                } else {
                    Intent intent = VideoIntentUtil.getVideoPickupIntent();
                    ((BaseActivity) context).startActivityForResult(intent, MSG_VIDEO_REQUEST_CODE);
                }
            } else if (type == Enums.FileType.IMAGE) {
                if (mediaDirectUse == Enums.MediaDirectUse.DirectCamera) {
                    Intent intent = new Intent(context, ImageCaptureActivity.class);
                    ((BaseActivity) context).startActivityForResult(intent, CUSTOM_CAMERA_REQUEST_CODE);
                } else {
                    Intent intent = ImageIntentUtil.getPickImageGalleryOnlyIntent();
                    ((BaseActivity) context).startActivityForResult(intent, MSG_IMG_REQUEST_CODE);
                }
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.chooseSource);

            builder.setPositiveButton(R.string.Galary, (dialog, which) -> {
                if (type == Enums.FileType.VIDEO) {
                    Intent intent = VideoIntentUtil.getVideoPickupIntent();
                    ((BaseActivity) context).startActivityForResult(intent, MSG_VIDEO_REQUEST_CODE);
                } else if (type == Enums.FileType.IMAGE) {
                    Intent intent = ImageIntentUtil.getPickImageIntent(context);
                    ((BaseActivity) context).startActivityForResult(intent, MSG_IMG_REQUEST_CODE);
                }
                dialog.dismiss();
            });

            builder.setNegativeButton(R.string.Camera, (dialog, which) -> {
                if (type == Enums.FileType.VIDEO) {
                    Intent intent = new Intent(context, VideoCaptureActivity.class);
                    ((BaseActivity) context).startActivityForResult(intent, CUSTOM_CAMERA_REQUEST_CODE);
                } else if (type == Enums.FileType.IMAGE) {
                    Intent intent = new Intent(context, ImageCaptureActivity.class);
                    ((BaseActivity) context).startActivityForResult(intent, CUSTOM_CAMERA_REQUEST_CODE);
                }
                dialog.dismiss();
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (context == null)
            return;

        if (resultCode == RESULT_OK) {
            if (requestCode == MSG_IMG_REQUEST_CODE) {
                File file = ImageIntentUtil.getImageFromResult(context, resultCode, data);
                uploadFile(file, Enums.MessageTypeEnum.Image);
            }
            else if (requestCode == MSG_VIDEO_REQUEST_CODE) {
                File file = VideoIntentUtil.getVideoFromResult(context, resultCode, data);

                long millisecond = ImageVideoUtil.getVideoDuration(file.getAbsolutePath());
                long sec = TimeUnit.MILLISECONDS.toSeconds(millisecond);
                if (sec > 240) {
                    Toast.makeText(context,
                            R.string.video_should_not_be_long,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                uploadFile(file, Enums.MessageTypeEnum.Video);
            }
            else if (requestCode == CUSTOM_CAMERA_REQUEST_CODE) {
                Enums.FileType type = (Enums.FileType) data.getSerializableExtra("CustomType");
                File file = new File(data.getStringExtra("File"));
                Uri uri = Uri.fromFile(file);

                if (type == Enums.FileType.VIDEO) {
                    uploadFile(file, Enums.MessageTypeEnum.Video);
                } else if (type == Enums.FileType.IMAGE) {
                    uploadFile(file, Enums.MessageTypeEnum.Image);
                }
            }
        }
    }

    private void uploadFile(File file, Enums.MessageTypeEnum messageTypeEnum) {
        _clickListener.OnClickListener(file, messageTypeEnum);
    }

    // Audio
    private void showRecordDialog() {
        AudioRecorderDialogFragment recordDialog = AudioRecorderDialogFragment.newInstance(
                getString(R.string.add_voice_order));

        recordDialog.setMessage(getString(R.string.press_for_recording));
        recordDialog.setTitle(getString(R.string.add_voice_order));

        recordDialog.show(getActivity().getFragmentManager(), "TAG");
        recordDialog.setPositiveButton(getString(R.string.send),
                new AudioRecorderDialogFragment.ClickListener() {
                    @Override
                    public void OnClickListener(String path) {
                        Log.d("outputFile", path);
                        File file = new File(path);
                        uploadFile(file, Enums.MessageTypeEnum.Voice);
                    }
                });
    }

    public void showRecordingDialog() {
        if (context == null)
            return;

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            showSnackbar(R.id.parent_layout);
        } else {
            showRecordDialog();
        }
    }

    private void showSnackbar(int parentLayoutId) {
        Snackbar snackbar = Snackbar.make(getActivity().findViewById(parentLayoutId),
                R.string.no_audio_permission, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.allow_permission, view -> {
            askPermissions();
        });
        snackbar.show();
    }

    private void askPermissions() {
        if (context == null)
            return;

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (getActivity() != null)
                ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    ACCESS_PERMISSION_REQUEST_CODE);
        }
    }
}