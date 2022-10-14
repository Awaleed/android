package maksab.sd.customer.models.orders.details;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderDetailViewModel implements Parcelable {
    private int id;
    private int orderId;
    private int orderTypeId;
    private int itemId;
    private int specialtyId;
    private int price;
    private int quantity;
    private int totalPrice;
    private String itemName;
    private String itemDescription;

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getOrderTypeId() {
        return orderTypeId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.orderId);
        dest.writeInt(this.orderTypeId);
        dest.writeInt(this.itemId);
        dest.writeInt(this.specialtyId);
        dest.writeInt(this.price);
        dest.writeInt(this.quantity);
        dest.writeInt(this.totalPrice);
        dest.writeString(this.itemName);
        dest.writeString(this.itemDescription);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.orderId = source.readInt();
        this.orderTypeId = source.readInt();
        this.itemId = source.readInt();
        this.specialtyId = source.readInt();
        this.price = source.readInt();
        this.quantity = source.readInt();
        this.totalPrice = source.readInt();
        this.itemName = source.readString();
        this.itemDescription = source.readString();
    }

    public OrderDetailViewModel() {
    }

    protected OrderDetailViewModel(Parcel in) {
        this.id = in.readInt();
        this.orderId = in.readInt();
        this.orderTypeId = in.readInt();
        this.itemId = in.readInt();
        this.specialtyId = in.readInt();
        this.price = in.readInt();
        this.quantity = in.readInt();
        this.totalPrice = in.readInt();
        this.itemName = in.readString();
        this.itemDescription = in.readString();
    }

    public static final Parcelable.Creator<OrderDetailViewModel> CREATOR = new Parcelable.Creator<OrderDetailViewModel>() {
        @Override
        public OrderDetailViewModel createFromParcel(Parcel source) {
            return new OrderDetailViewModel(source);
        }

        @Override
        public OrderDetailViewModel[] newArray(int size) {
            return new OrderDetailViewModel[size];
        }
    };
}
