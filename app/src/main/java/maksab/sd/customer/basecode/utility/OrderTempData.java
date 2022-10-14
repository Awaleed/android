package maksab.sd.customer.basecode.utility;

import java.util.ArrayList;
import java.util.List;

public  class OrderTempData {
    static List<OrderServiceItem> orderServiceItems = new ArrayList<>();
    public static void AddNewService(OrderServiceItem orderServiceItem){
        orderServiceItems.add(orderServiceItem);
    }

    public static OrderServiceItem GetServiceById(int id){
        OrderServiceItem item = null;
        for (OrderServiceItem orderServiceItem:orderServiceItems) {
            if(orderServiceItem.serviceId == id) item = orderServiceItem;
        }
        return item;
    }

    public static List<OrderServiceItem> GetAllItems(){
        return orderServiceItems;
    }

    public static void Clear(){
        orderServiceItems.clear();
    }
    public static void UpdateItemQuantity(int id , int newQuantity ){
       OrderServiceItem orderServiceItem =  GetServiceById(id);
       orderServiceItem.serviceQuntity = newQuantity;
    }

    public static void DeleteItem(int id){
        OrderServiceItem orderServiceItem = GetServiceById(id);
        orderServiceItems.remove(orderServiceItem);
    }


}

