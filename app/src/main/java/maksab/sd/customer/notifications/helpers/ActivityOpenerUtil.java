package maksab.sd.customer.notifications.helpers;

import android.content.Intent;

import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.fragments.MessageDialog;
import maksab.sd.customer.notifications.ui.NotificationDetailActivity;
import maksab.sd.customer.ui.orders.activities.OrderDetailsActivity;
import maksab.sd.customer.util.constants.NotificationsCategoriesTypesEnum;
import maksab.sd.customer.util.constants.NotificationsTypeEnum;

public class ActivityOpenerUtil {
    public static void openNextActivity(BaseActivity activity, NotificationModel notificationModel) {
        if (notificationModel.getNotificationCategoryId() == NotificationsCategoriesTypesEnum.BroadCast.ordinal()) {
            Intent intent = new Intent(activity, NotificationDetailActivity.class);
            intent.putExtra("Notification", notificationModel);
            activity.startActivity(intent);
        }
        else if (notificationModel.getNotificationCategoryId() == NotificationsCategoriesTypesEnum.Action.ordinal()) {
            if (notificationModel.getNotificationTypeId() == NotificationType.ORDER_UPDATE.ordinal()) {
                Intent intent = new Intent(activity, OrderDetailsActivity.class);
                intent.putExtra("id", Integer.parseInt(notificationModel.getParentId()));
                activity.startActivity(intent);
            } else if (notificationModel.getNotificationTypeId() == NotificationType.QUOTATION_UPDATE.ordinal() ||
                    notificationModel.getNotificationTypeId() == NotificationsTypeEnum.NEW_QUTATION.ordinal()) {
                Intent intent = new Intent(activity, OrderDetailsActivity.class);
                intent.putExtra("id", Long.parseLong(notificationModel.getParentId()));
                activity.startActivity(intent);
            }
        }
        else if (notificationModel.getNotificationCategoryId() == NotificationsCategoriesTypesEnum.Manually.ordinal()) {

        }
        else if (notificationModel.getNotificationCategoryId() == NotificationsCategoriesTypesEnum.Reminder.ordinal()) {

        }
        else if (notificationModel.getNotificationCategoryId() == NotificationsCategoriesTypesEnum.Competition.ordinal()) {

        }
        else if (notificationModel.getNotificationCategoryId() == NotificationsCategoriesTypesEnum.Unkown.ordinal()) {
            MessageDialog.showMessageDialog(activity, notificationModel.getTitle(),
                    notificationModel.getMessage());
        }
    }
}
