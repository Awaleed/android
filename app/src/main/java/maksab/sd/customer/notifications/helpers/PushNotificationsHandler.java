package maksab.sd.customer.notifications.helpers;

import android.content.Context;

import maksab.sd.customer.models.notifications.PushNotificationModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.main.activties.MainActivity;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.constants.NotificationsTypeEnum;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PushNotificationsHandler {
    public static void handle(Context context, PushNotificationModel pushModel) {
        if (pushModel == null)
            return;


        if (pushModel.getType().equalsIgnoreCase("cast"))
            return;

        int typeId = Integer.parseInt(pushModel.getType());
        Integer notificationId = Integer.parseInt(pushModel.getNotificationId());

        if (typeId == NotificationsTypeEnum.CAST.ordinal()) {
            setBroadcastIsSeen(context, notificationId);
        }
        else
            setIsSeen(context, notificationId);

        MainActivity activity = (MainActivity) context;
        activity.openNotificationFragment();
    }

    public static void setBroadcastIsSeen(Context context, int broadCastNotificationId) {
        SharedPreferencesStorage localStorage = new SharedPreferencesStorage(context);
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        GetWayServiceGenerator.createService(ICustomersService.class, token)
                .updateBroadcastNotificationStatus(broadCastNotificationId,
                        Enums.NotificationUpdateType.Seen.ordinal())
                .enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful()) {
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                    }
                });
    }

    public static void setIsSeen(Context context, int notificationId) {
        SharedPreferencesStorage localStorage = new SharedPreferencesStorage(context);
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        GetWayServiceGenerator.createService(ICustomersService.class, token)
                .updateNotificationStatus(notificationId,
                        Enums.NotificationUpdateType.Seen.ordinal())
                .enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful()) {
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                    }
                });
    }
}
