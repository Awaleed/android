package maksab.sd.customer.storage;

import java.util.ArrayList;
import java.util.List;

public class OrderSummaryInMemoryStorage {
    public static OrderSummaryModel orderSummaryModel = new OrderSummaryModel();

    public static int offer_quantity = 1;
    public static boolean isOfferStorageActive = false;

    public static void addAddress(String address){
        orderSummaryModel.setAddress(address);
    }

    public static String getAddress(){
        return orderSummaryModel.getAddress();
    }

    public static void addSelectedService(OrderSummaryDetails orderSummaryDetail){
        List<OrderSummaryDetails> orderSummaryDetails = orderSummaryModel.getOrderSummaryDetails();
        orderSummaryDetails.add(orderSummaryDetail);
        orderSummaryModel.setOrderSummaryDetails(orderSummaryDetails);
    }

    public static void updateSelectedServiceQuantity(int serviceId , int quantity){
        OrderSummaryDetails orderSummaryDetails = getOrderDetailById(serviceId);
        orderSummaryDetails.setQuantity(quantity);
    }

    public static void deleteSelectedService(int serviceId){
        OrderSummaryDetails orderSummaryDetails = getOrderDetailById(serviceId);
        if(orderSummaryDetails !=null){
            orderSummaryModel.getOrderSummaryDetails().remove(orderSummaryDetails);
        }
    }

    public static void addCouponData(double amount , String code){
        orderSummaryModel.setCouponAmount(amount);
        orderSummaryModel.setCouponCode(code);
    }

    public static void addGuaranteeAmount(double amount) {
        orderSummaryModel.setGuranteePrice(amount);
    }

    public static OrderSummaryDetails getOrderDetailById(int serviceId){
        OrderSummaryDetails orderSummaryDetails = null;
        for (int i = 0; i < orderSummaryModel.getOrderSummaryDetails().size(); i++) {
            if(orderSummaryModel.getOrderSummaryDetails().get(i).getId() == serviceId){
                orderSummaryDetails = orderSummaryModel.getOrderSummaryDetails().get(i);
            }
        }
        return orderSummaryDetails;
    }

    public static List<OrderSummaryDetails> getSelectedServices(){
        List<OrderSummaryDetails> orderSummaryDetails = new ArrayList<>();
        for (OrderSummaryDetails orderSummaryDetail : orderSummaryModel.getOrderSummaryDetails()) {
            if(orderSummaryDetail.getQuantity() >0){
                orderSummaryDetails.add(orderSummaryDetail);
            }
        }
        return orderSummaryDetails;
    }

    public static OrderSummaryModel getOrderSummary(){
        return orderSummaryModel;
    }

    public static void clear(){
        if(!isOfferStorageActive){
            orderSummaryModel = new OrderSummaryModel();
        }
    }

    public static void removeSummaryDetails(int id){

    }

}
