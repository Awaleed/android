package maksab.sd.customer.models.orders.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetails {

    @SerializedName("ItemId")
    @Expose
    private Integer itemId;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;

    private int specialityId;
    private int serviceFor;
    private double price;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public int getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }

    public int getServiceFor() {
        return serviceFor;
    }

    public void setServiceFor(int serviceFor) {
        this.serviceFor = serviceFor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
