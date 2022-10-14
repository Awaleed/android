package maksab.sd.customer.notifications.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.notifications.helpers.NotificationHandler;
import maksab.sd.customer.notifications.helpers.NotificationModel;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.media.viewer.MediaActivityOpener;
import maksab.sd.customer.util.constants.Enums;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.notification_title)
    TextView notification_title;
    @BindView(R.id.notification_body)
    TextView notification_body;
    @BindView(R.id.notification_date)
    TextView notification_date;
    @BindView(R.id.notification_image)
    ImageView notification_image;
    @BindView(R.id.external_button)
    Button external_button;
    @BindView(R.id.ok_button)
    Button ok_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);
        ButterKnife.bind(this);

        NotificationModel notificationModel = getIntent().getParcelableExtra("Notification");
        setupToolbar(notificationModel.getId());
        setDetails(notificationModel);

        setReadingNotificationStatus(notificationModel.getId(),
                Enums.NotificationUpdateType.Read.ordinal());
    }

    private void setReadingNotificationStatus(int notificationId, int typeStatus) {
        SharedPreferencesStorage localStorage = new SharedPreferencesStorage(this);
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        GetWayServiceGenerator.createService(ICustomersService.class, token)
            .updateNotificationStatus(notificationId, typeStatus)
            .enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        Intent intent = new Intent();
                        intent.putExtra("NotificationIndex", getIntent().getIntExtra("NotificationIndex", 0));
                        setResult(RESULT_OK, intent);
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                }
            });
    }

    private void setDetails(NotificationModel notification) {
        notification_date.setText(notification.getCreatedOnString());
        notification_body.setText(notification.getMessage());
        notification_title.setText(notification.getTitle());

        if (!TextUtils.isEmpty(notification.getImageLink())) {
            Picasso.with(this).load(notification.getImageLink())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(notification_image);

            notification_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MediaActivityOpener.openViewActivity(NotificationDetailActivity.this, notification.getImageLink());
                }
            });
        }
        else {
            notification_image.setVisibility(View.GONE);
        }

        boolean isActionNotification = NotificationHandler.isActionNotification(notification);
        boolean isHaveExternalLink = !TextUtils.isEmpty(notification.getExternalLink());
        boolean isHaveActionButton = isHaveExternalLink || isActionNotification;

        if (isHaveActionButton) {
            external_button.setVisibility(View.VISIBLE);
            external_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isActionNotification) {
                        NotificationHandler.openNextActivity(NotificationDetailActivity.this, notification);
                    }
                    else {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(notification.getExternalLink()));
                        startActivity(i);
                    }
                }
            });
        }
        else {
            external_button.setVisibility(View.GONE);
        }

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupToolbar(int notificationId) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.notifcations) + " #" + notificationId);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(5f);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
