package maksab.sd.customer.storage;

import java.util.ArrayList;
import java.util.List;

import maksab.sd.customer.models.orders.details.OrderDetails;
import maksab.sd.customer.models.orders.details.OrderInputModel;
import maksab.sd.customer.models.orders.details.SpecialtyQuestionAnswers;

public  class OrderInMemoryStorage {
    public static OrderInputModel orderInputModel = new OrderInputModel();
    public static long CreatedOrderId=0;
    public static String specialityName = "";
    public static boolean is_balance_used = false;
    public static int SpecialtyType;

    public static void addBasicInputs(int specialityId , int orderTypeId , String providerUserId , String body , List<String> imagePaths){
        orderInputModel.setSpecialityId(specialityId);
        orderInputModel.setOrderTypeId(orderTypeId);
        orderInputModel.setProviderUserId(providerUserId);
        orderInputModel.setBody(body);
        orderInputModel.setImagePaths(imagePaths);
    }

    public static void setSelectedTime(String selectedTime, String selectedDate,
                                       boolean acceptFlexibleTime){
        orderInputModel.setDesireOn(selectedDate);
        orderInputModel.setSelectedTime(selectedTime);
        orderInputModel.setAcceptFlexibleTime(acceptFlexibleTime);
    }

    public static void setAddressId(int addressId){
        orderInputModel.setAddressId(addressId);
    }

    public static void setCoupon(String coupon){
        orderInputModel.setCouponCode(coupon);
    }

    public static void updateOrderDetailQuantity(int serviceId , int quantity){
        OrderDetails orderDetails = getOrderItemById(serviceId);
        orderDetails.setQuantity(quantity);
    }

    public static void addSpecialityAnswer(SpecialtyQuestionAnswers specialtyQuestionAnswer){
        List<SpecialtyQuestionAnswers> specialtyQuestionAnswers = orderInputModel.getSpecialtyQuestionAnswers();
        SpecialtyQuestionAnswers questionAnswers = getQuestionAnswerById(specialtyQuestionAnswer.getSpecialtyQuestionId());
        if(questionAnswers == null){
            specialtyQuestionAnswers.add(specialtyQuestionAnswer);
        }else{
            questionAnswers.setArabicAnswer(specialtyQuestionAnswer.getArabicAnswer());
        }

        orderInputModel.setSpecialtyQuestionAnswers(specialtyQuestionAnswers);
    }

    public static SpecialtyQuestionAnswers getQuestionAnswerById(int questionId){
        for (SpecialtyQuestionAnswers specialtyQuestionAnswers : orderInputModel.getSpecialtyQuestionAnswers()) {
            if(specialtyQuestionAnswers.getSpecialtyQuestionId() == questionId) return specialtyQuestionAnswers;
        }
        return null;
    }

    public static void addOrderItem(OrderDetails orderDetail){
        List<OrderDetails> orderDetails = orderInputModel.getOrderDetails();
        orderDetails.add(orderDetail);
        orderInputModel.setOrderDetails(orderDetails);
    }

    public static OrderDetails getOrderItemById(int serviceId){
        OrderDetails orderDetails = null;
        for (int i = 0; i < orderInputModel.getOrderDetails().size(); i++) {
            if(orderInputModel.getOrderDetails().get(i).getItemId() == serviceId){
                orderDetails = orderInputModel.getOrderDetails().get(i);
                break;
            }
        }
        return orderDetails;
    }

    public static List<OrderDetails> getOrderItems(){
        List<OrderDetails> orderDetails = new ArrayList<>();
        for (OrderDetails orderDetail : orderInputModel.getOrderDetails()) {
            if(orderDetail.getQuantity() > 0){
                orderDetails.add(orderDetail);
            }
        }

        return orderDetails;
    }

    public static double getOrderItemsTotal(){
        double total = 0;
        for (OrderDetails orderDetail : orderInputModel.getOrderDetails()) {
            if(orderDetail.getQuantity() > 0){
                total += orderDetail.getQuantity() * orderDetail.getPrice();
            }
        }

        return total;
    }

    public static boolean IsItemFromDifferentSpeciality(int specialityId){
        if(!getOrderItems().isEmpty()){
           OrderDetails firstItem = getOrderItems().get(0);
           return firstItem.getSpecialityId() != specialityId;
        }
        return false;
    }

    public static void deleteOrderItem(int serviceId){
        OrderDetails orderDetails = getOrderItemById(serviceId);
        if(orderDetails !=null){
            orderInputModel.getOrderDetails().remove(orderDetails);
        }
    }

    public static OrderInputModel getOrderInputs(){
        return orderInputModel;
    }

    public static void setCreatedOrderId(long createdOrderId){
        CreatedOrderId = createdOrderId;
    }

    public static long getCreatedOrderId(){
        return CreatedOrderId;
    }

    public static void clear(){
        orderInputModel = new OrderInputModel();
    }
}
