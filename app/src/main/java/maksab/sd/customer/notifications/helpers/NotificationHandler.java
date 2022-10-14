package maksab.sd.customer.notifications.helpers;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.models.providers.OrderModel;
import maksab.sd.customer.models.tickets.TicketModel;
import maksab.sd.customer.network.appnetwork.ICustomersService;
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator;
import maksab.sd.customer.storage.ILocalStorage;
import maksab.sd.customer.storage.SharedPreferencesStorage;
import maksab.sd.customer.ui.orders.activities.OrderDetailsActivity;
import maksab.sd.customer.ui.tickets.activities.TicketDetailsActivity;
import maksab.sd.customer.util.constants.Enums;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationHandler {
    public static boolean isActionNotification(NotificationModel notificationModel) {
        int notificationCategoryId = notificationModel.getNotificationCategoryId();
        if (notificationCategoryId != Enums.NotificationCategoryEnum.Action.ordinal())
            return false;

        int notificationTypeId = notificationModel.getNotificationTypeId();
        if (notificationTypeId == Enums.NotificationType.ORDER_UPDATE.ordinal() ||
                notificationTypeId == Enums.NotificationType.QUOTATION_UPDATE.ordinal() ||
                notificationTypeId == Enums.NotificationType.NEW_QUTATION.ordinal() ||
                notificationTypeId == Enums.NotificationType.ORDER_CHAT.ordinal() ||
                notificationTypeId == Enums.NotificationType.REMINDER_PROVIDER_NEW_QUOTATION.ordinal() ||
                notificationTypeId == Enums.NotificationType.REMINDER_PROVIDER_NEW_ORDER.ordinal() ||
                notificationTypeId == Enums.NotificationType.REMINDER_PROVIDER_END_ORDER.ordinal()) {
            return true;
        }
        else if (notificationTypeId == Enums.NotificationType.TICKET.ordinal()) {
            return true;
        }

        return false;
    }

    public static void openNextActivity(BaseActivity activity, NotificationModel notificationModel) {
        int notificationCategoryId = notificationModel.getNotificationCategoryId();
        if (notificationCategoryId != Enums.NotificationCategoryEnum.Action.ordinal())
            return;

        int notificationTypeId = notificationModel.getNotificationTypeId();
        int notificationParentId = Integer.parseInt(notificationModel.getParentId());

        if (notificationTypeId == Enums.NotificationType.ORDER_UPDATE.ordinal() ||
                notificationTypeId == Enums.NotificationType.QUOTATION_UPDATE.ordinal() ||
                notificationTypeId == Enums.NotificationType.NEW_QUTATION.ordinal() ||
                notificationTypeId == Enums.NotificationType.ORDER_CHAT.ordinal() ||
                notificationTypeId == Enums.NotificationType.REMINDER_PROVIDER_NEW_QUOTATION.ordinal() ||
                notificationTypeId == Enums.NotificationType.REMINDER_PROVIDER_NEW_ORDER.ordinal() ||
                notificationTypeId == Enums.NotificationType.REMINDER_PROVIDER_END_ORDER.ordinal()) {
            openOrder(activity, notificationParentId , activity);
        }
        else if (notificationTypeId == Enums.NotificationType.TICKET.ordinal()) {
            openTicket(activity, notificationParentId);
        }
    }

    private static void openTicket(Context context, int ticketId) {
        SharedPreferencesStorage localStorage =new SharedPreferencesStorage(context);
        ICustomersService providerService = GetWayServiceGenerator.createService(ICustomersService.class, "Bearer " + localStorage.getJwtToken().getStringToken());
        providerService.getTicketById(ticketId)
                .enqueue(new Callback<TicketModel>() {
                    @Override
                    public void onResponse(Call<TicketModel> call, Response<TicketModel> response) {
                        if (response.isSuccessful()) {
                            TicketModel item = response.body();
                            Intent intent = new Intent(context, TicketDetailsActivity.class);
                            intent.putExtra("Ticket", item);
                            intent.putExtra("ticket.id", item.getId());
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<TicketModel> call, Throwable t) {
                    }
                });
    }

    private static void openOrder(Context context, int orderId , BaseActivity baseActivity) {
        baseActivity.showWaitDialog();
        ILocalStorage localStorage = new SharedPreferencesStorage(context);
        String token = "bearer " + localStorage.getJwtToken().getStringToken();
        GetWayServiceGenerator.createService(ICustomersService.class, token)
                .getorderbyId(orderId)
                .enqueue(new Callback<OrderModel>() {
                    @Override
                    public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                        baseActivity.dismissWaitDialog();
                        Intent intent = new Intent(context, OrderDetailsActivity.class);
                        OrderModel orderModel = response.body();
                        if(orderModel == null) return;
                        intent.putExtra("id", orderModel.getId());
                        intent.putExtra("order.status", response.body().getOrderStatusId());
                        if(orderModel.getOrderStatusId() == Enums.OrderStatusEnum.WaitingProviders.ordinal() ||
                                orderModel.getOrderStatusId() == Enums.OrderStatusEnum.PENDING.ordinal()){
                            intent.putExtra("order.isQuotation", true);
                        }
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<OrderModel> call, Throwable t) {
                        baseActivity.dismissWaitDialog();
                        Toast.makeText(context , context.getResources().getText(R.string.internetError), Toast.LENGTH_LONG).show();
                        t.printStackTrace();
                    }
                });
    }
}
