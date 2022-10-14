package maksab.sd.customer.notifications.helpers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import maksab.sd.customer.R;
import maksab.sd.customer.models.notifications.PushNotificationModel;
import maksab.sd.customer.ui.main.activties.MainActivity;

/**
 * Created by dev2 on 12/24/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String type = remoteMessage.getData().get("type");
        String id = remoteMessage.getData().get("id");
        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");
        String externalLink = remoteMessage.getData().get("externallink");
        String imageLink = remoteMessage.getData().get("imagelink");
        String notificationId = remoteMessage.getData().get("notificationId");

        PushNotificationModel model = new PushNotificationModel(id, type, title, message,
                externalLink, imageLink, notificationId);
        showNotification(model);

//        if(type !=null) {
//            if (type.equalsIgnoreCase(String.valueOf(NotificationType.CHAT.ordinal()))) {
//                chatNotification(remoteMessage, random);
//            } else if (type.equalsIgnoreCase(String.valueOf(NotificationType.ORDER_UPDATE.ordinal()))) {
//                orderNotification(remoteMessage, random);
//            } else if (type.equalsIgnoreCase(String.valueOf(NotificationType.ACCOUNT_UPDATE.ordinal()))) {
//                castNotifiactions(remoteMessage, random);
//            } else if (type.equalsIgnoreCase(String.valueOf(NotificationType.QUOTATION_UPDATE.ordinal()))) {
//                quoationsNotification(remoteMessage, random);
//            } else if (type.equalsIgnoreCase(String.valueOf(NotificationType.TRANSACTION.ordinal()))) {
//                castNotifiactions(remoteMessage, random);
//            } else if (type.equalsIgnoreCase(String.valueOf(NotificationType.NEW_QUOTATION.ordinal()))) {
//                castNotifiactions(remoteMessage, random);
//            } else if (type.equalsIgnoreCase("cast")) {
//                castNotifiactions(remoteMessage, random);
//            }
//        }
    }

    private void showNotification(PushNotificationModel notificationModel) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("PushNotificationModel", notificationModel);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.going);
        long[] vibrate = {0, 100, 200, 300};

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setContentTitle(notificationModel.getTitle())
                .setContentText(notificationModel.getMessage())
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .setSound(customSoundUri)
                .setVibrate(vibrate)
                .setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel("MAKSAB_CUSTOMER_CHANNEL_3",
                            "MAKSAB_CUSTOMER_3", NotificationManager.IMPORTANCE_HIGH);

            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            notificationChannel.setSound(customSoundUri, attributes);

            mNotificationManager.createNotificationChannel(notificationChannel);
            notification.setChannelId("MAKSAB_CUSTOMER_CHANNEL_3");
        }

        int randomId = (int) System.currentTimeMillis();
        mNotificationManager.notify(randomId, notification.build());
    }

//    private void castNotifiactions(RemoteMessage remoteMessage, Random random) {
//        ILocalStorage localStorage = new SharedPreferencesStorage(this);
//        localStorage.setCastNotification(true);
//        localStorage.savCastNotification(new CastNotificatoion(remoteMessage.getData().get("title"), remoteMessage.getData().get("message")));
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, random.nextInt(), notificationIntent, FLAG_UPDATE_CURRENT);
//        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        showNotfication(remoteMessage.getData().get("message") , remoteMessage.getData().get("title"), pendingIntent, 77777777);
//    }
//
//    private void competitionNotifiactions(RemoteMessage remoteMessage, Random random) {
//
//        ILocalStorage localStorage = new SharedPreferencesStorage(this);
//
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//
//        localStorage.setCompetitionNotification(true);
//        localStorage.savCompetitionNotification(new CastNotificatoion("notitle", remoteMessage.getData().get("message")));
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, random.nextInt(), notificationIntent, FLAG_UPDATE_CURRENT);
//        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        showNotfication(remoteMessage.getData().get("title")," مسابقة", pendingIntent, 6666);
//    }
//
//    private void orderNotification(RemoteMessage remoteMessage, Random random) {
//        Intent notificationIntent = new Intent(this, OrderDetailsActivity.class);
//        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
//        int size = -1;
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            size = am.getAppTasks().size();
//        } else {
//            size = am.getRunningTasks(1).size();
//        }
//
//        Log.d("sizeofstack", size + "");
//        if (size > 0) {
//            notificationIntent.putExtra("isFromNotification", false);
//        } else {
//            notificationIntent.putExtra("isFromNotification", true);
//        }
//
//
//        String orderid = remoteMessage.getData().get("id");
//        notificationIntent.putExtra("id", Long.parseLong(orderid));
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, random.nextInt(), notificationIntent, FLAG_UPDATE_CURRENT);
//        showNotfication(remoteMessage.getData().get("message") ,"مكسب - الطلبات", pendingIntent, Integer.parseInt(remoteMessage.getData().get("id")));
//    }
//
//    private void chatNotification(RemoteMessage remoteMessage, Random random) {
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//
//        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
//        int size = -1;
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            size = am.getAppTasks().size();
//        } else {
//            size = am.getRunningTasks(1).size();
//        }
//
//        if (size > 0) {
//            notificationIntent.putExtra("isFromNotification", false);
//        } else {
//            notificationIntent.putExtra("isFromNotification", true);
//        }
//
//        notificationIntent.putExtra("touser", remoteMessage.getData().get("fromUserId"));
//        notificationIntent.putExtra("myid", remoteMessage.getData().get("toUserId"));
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, random.nextInt(), notificationIntent, FLAG_UPDATE_CURRENT);
//        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//
//        showNotfication(String.format("لديك رسالة من %s", remoteMessage.getData().get("fullname")) ,"رسالة جديدة", pendingIntent, random.nextInt());
//    }
//
//    private void showNotfication(String message ,String title, PendingIntent pendingIntent, int notfiactionId) {
//
//        Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.going);
//
//        long[] vibrate = {0, 100, 200, 300};
//
//        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentIntent(pendingIntent)
//                .setSound(customSoundUri)
//                .setVibrate(vibrate)
//                .setAutoCancel(true);
//
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel notificationChannel =
//                    new NotificationChannel("MAKSAB_CUSTOMER_CHANNEL_3",
//                            "MAKSAB_CUSTOMER_3", NotificationManager.IMPORTANCE_HIGH);
//
//            AudioAttributes attributes = new AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                    .build();
//
//            notificationChannel.setSound(customSoundUri, attributes);
//
//            mNotificationManager.createNotificationChannel(notificationChannel);
//            notification.setChannelId("MAKSAB_CUSTOMER_CHANNEL_3");
//        }
//
//        mNotificationManager.notify(notfiactionId, notification.build());
//    }
//
//    private void quoationsNotification(RemoteMessage remoteMessage, Random random) {
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//        String quotationid = remoteMessage.getData().get("id");
//        notificationIntent.putExtra("id", Long.parseLong(quotationid));
//        notificationIntent.putExtra("qouation_status", "مفتوح");
//        //set type to quotation
//        notificationIntent.putExtra("type", 4);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, random.nextInt(), notificationIntent, FLAG_UPDATE_CURRENT);
//        showNotfication(remoteMessage.getData().get("message"),"مكسب - عرض السعر", pendingIntent, Integer.parseInt(remoteMessage.getData().get("id")));
//    }
}
