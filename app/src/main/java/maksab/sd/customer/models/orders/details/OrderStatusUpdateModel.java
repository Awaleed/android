package maksab.sd.customer.models.orders.details;

public class OrderStatusUpdateModel {
   private int orderUpdateTypeEnum;
   private int newStatusId;

   private String reason;
   private String reasonBody;

   public static OrderStatusUpdateModel changeOrderStatus(int orderUpdateTypeEnum, int newStatusId) {
      OrderStatusUpdateModel model = new OrderStatusUpdateModel();
      model.orderUpdateTypeEnum = orderUpdateTypeEnum;
      model.newStatusId = newStatusId;
      return model;
   }

   public static OrderStatusUpdateModel cancelOrder(int orderUpdateTypeEnum, int newStatusId,
                                                    String reason, String reasonBody) {
      OrderStatusUpdateModel model = new OrderStatusUpdateModel();
      model.orderUpdateTypeEnum = orderUpdateTypeEnum;
      model.newStatusId = newStatusId;
      model.reason = reason;
      model.reasonBody = reasonBody;
      return model;
   }
}
