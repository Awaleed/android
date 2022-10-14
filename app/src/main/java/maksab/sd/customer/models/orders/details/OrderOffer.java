package maksab.sd.customer.models.orders.details;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dev on 3/15/2018.
 */

public class OrderOffer implements Parcelable {
    private int id;
    private int orderId;
    private String createdOn;
    private String createdOnString;
    private boolean addedManually;
    private boolean isOffered;
    private boolean isAccepted;
    private String offeredOn;
    private String offeredOnString;
    private int executionTypeEnum;
    private String providerUserId;
    private int providerId;
    private String body;
    private String closedReason;
    private String closedBody;
    private double price;
    private int guaranteePeriodInDays;
    private int orderOfferTimingType;
    private String executionDateOn;
    private String executionTimeOn;
    private double transportationPrice;
    private float providerLatitude;
    private float providerLongitude;
    private double providerDistance;
    private String executionTypeName;
    private String providerName;
    private int providerType;
    private int providerClassTypeId;
    private double providerRate;
    private String providerBio;
    private int providerCompletedOrdersCount;
    private int providerOpeningOrdersCount;
    private String providerImageUrl;
    private boolean isProviderSeeNotificationCall;
    private String providerSeeNotificationCallOn;
    private String providerSeeNotificationCallOnString;
    private boolean isProviderSeeOrderDetails;
    private String providerSeeOrderDetailsOn;
    private String providerSeeOrderDetailsOnString;
    private String customerUserId;

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getCreatedOnString() {
        return createdOnString;
    }

    public boolean isAddedManually() {
        return addedManually;
    }

    public boolean isOffered() {
        return isOffered;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public String getOfferedOn() {
        return offeredOn;
    }

    public String getOfferedOnString() {
        return offeredOnString;
    }

    public int getExecutionTypeEnum() {
        return executionTypeEnum;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public int getProviderId() {
        return providerId;
    }

    public String getBody() {
        return body;
    }

    public String getClosedReason() {
        return closedReason;
    }

    public String getClosedBody() {
        return closedBody;
    }

    public double getPrice() {
        return price;
    }

    public float getProviderLatitude() {
        return providerLatitude;
    }

    public float getProviderLongitude() {
        return providerLongitude;
    }

    public double getProviderDistance() {
        return providerDistance;
    }

    public String getExecutionTypeName() {
        return executionTypeName;
    }

    public String getProviderName() {
        return providerName;
    }

    public double getProviderRate() {
        return providerRate;
    }

    public String getProviderBio() {
        return providerBio;
    }

    public int getProviderCompletedOrdersCount() {
        return providerCompletedOrdersCount;
    }

    public int getProviderOpeningOrdersCount() {
        return providerOpeningOrdersCount;
    }

    public String getProviderImageUrl() {
        return providerImageUrl;
    }

    public boolean isProviderSeeNotificationCall() {
        return isProviderSeeNotificationCall;
    }

    public String getProviderSeeNotificationCallOn() {
        return providerSeeNotificationCallOn;
    }

    public String getProviderSeeNotificationCallOnString() {
        return providerSeeNotificationCallOnString;
    }

    public boolean isProviderSeeOrderDetails() {
        return isProviderSeeOrderDetails;
    }

    public String getProviderSeeOrderDetailsOn() {
        return providerSeeOrderDetailsOn;
    }

    public String getProviderSeeOrderDetailsOnString() {
        return providerSeeOrderDetailsOnString;
    }

    public String getCustomerUserId() {
        return customerUserId;
    }

    public int getGuaranteePeriodInDays() {
        return guaranteePeriodInDays;
    }

    public int getOrderOfferTimingType() {
        return orderOfferTimingType;
    }

    public String getExecutionDateOn() {
        return executionDateOn;
    }

    public String getExecutionTimeOn() {
        return executionTimeOn;
    }

    public double getTransportationPrice() {
        return transportationPrice;
    }

    public int getProviderType() {
        return providerType;
    }

    public int getProviderClassTypeId() {
        return providerClassTypeId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.orderId);
        dest.writeString(this.createdOn);
        dest.writeString(this.createdOnString);
        dest.writeByte(this.addedManually ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isOffered ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isAccepted ? (byte) 1 : (byte) 0);
        dest.writeString(this.offeredOn);
        dest.writeString(this.offeredOnString);
        dest.writeInt(this.executionTypeEnum);
        dest.writeString(this.providerUserId);
        dest.writeInt(this.providerId);
        dest.writeString(this.body);
        dest.writeString(this.closedReason);
        dest.writeString(this.closedBody);
        dest.writeDouble(this.price);
        dest.writeInt(this.guaranteePeriodInDays);
        dest.writeInt(this.orderOfferTimingType);
        dest.writeString(this.executionDateOn);
        dest.writeString(this.executionTimeOn);
        dest.writeDouble(this.transportationPrice);
        dest.writeFloat(this.providerLatitude);
        dest.writeFloat(this.providerLongitude);
        dest.writeDouble(this.providerDistance);
        dest.writeString(this.executionTypeName);
        dest.writeString(this.providerName);
        dest.writeInt(this.providerType);
        dest.writeInt(this.providerClassTypeId);
        dest.writeDouble(this.providerRate);
        dest.writeString(this.providerBio);
        dest.writeInt(this.providerCompletedOrdersCount);
        dest.writeInt(this.providerOpeningOrdersCount);
        dest.writeString(this.providerImageUrl);
        dest.writeByte(this.isProviderSeeNotificationCall ? (byte) 1 : (byte) 0);
        dest.writeString(this.providerSeeNotificationCallOn);
        dest.writeString(this.providerSeeNotificationCallOnString);
        dest.writeByte(this.isProviderSeeOrderDetails ? (byte) 1 : (byte) 0);
        dest.writeString(this.providerSeeOrderDetailsOn);
        dest.writeString(this.providerSeeOrderDetailsOnString);
        dest.writeString(this.customerUserId);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.orderId = source.readInt();
        this.createdOn = source.readString();
        this.createdOnString = source.readString();
        this.addedManually = source.readByte() != 0;
        this.isOffered = source.readByte() != 0;
        this.isAccepted = source.readByte() != 0;
        this.offeredOn = source.readString();
        this.offeredOnString = source.readString();
        this.executionTypeEnum = source.readInt();
        this.providerUserId = source.readString();
        this.providerId = source.readInt();
        this.body = source.readString();
        this.closedReason = source.readString();
        this.closedBody = source.readString();
        this.price = source.readDouble();
        this.guaranteePeriodInDays = source.readInt();
        this.orderOfferTimingType = source.readInt();
        this.executionDateOn = source.readString();
        this.executionTimeOn = source.readString();
        this.transportationPrice = source.readDouble();
        this.providerLatitude = source.readFloat();
        this.providerLongitude = source.readFloat();
        this.providerDistance = source.readDouble();
        this.executionTypeName = source.readString();
        this.providerName = source.readString();
        this.providerType = source.readInt();
        this.providerClassTypeId = source.readInt();
        this.providerRate = source.readDouble();
        this.providerBio = source.readString();
        this.providerCompletedOrdersCount = source.readInt();
        this.providerOpeningOrdersCount = source.readInt();
        this.providerImageUrl = source.readString();
        this.isProviderSeeNotificationCall = source.readByte() != 0;
        this.providerSeeNotificationCallOn = source.readString();
        this.providerSeeNotificationCallOnString = source.readString();
        this.isProviderSeeOrderDetails = source.readByte() != 0;
        this.providerSeeOrderDetailsOn = source.readString();
        this.providerSeeOrderDetailsOnString = source.readString();
        this.customerUserId = source.readString();
    }

    public OrderOffer() {
    }

    protected OrderOffer(Parcel in) {
        this.id = in.readInt();
        this.orderId = in.readInt();
        this.createdOn = in.readString();
        this.createdOnString = in.readString();
        this.addedManually = in.readByte() != 0;
        this.isOffered = in.readByte() != 0;
        this.isAccepted = in.readByte() != 0;
        this.offeredOn = in.readString();
        this.offeredOnString = in.readString();
        this.executionTypeEnum = in.readInt();
        this.providerUserId = in.readString();
        this.providerId = in.readInt();
        this.body = in.readString();
        this.closedReason = in.readString();
        this.closedBody = in.readString();
        this.price = in.readDouble();
        this.guaranteePeriodInDays = in.readInt();
        this.orderOfferTimingType = in.readInt();
        this.executionDateOn = in.readString();
        this.executionTimeOn = in.readString();
        this.transportationPrice = in.readDouble();
        this.providerLatitude = in.readFloat();
        this.providerLongitude = in.readFloat();
        this.providerDistance = in.readDouble();
        this.executionTypeName = in.readString();
        this.providerName = in.readString();
        this.providerType = in.readInt();
        this.providerClassTypeId = in.readInt();
        this.providerRate = in.readDouble();
        this.providerBio = in.readString();
        this.providerCompletedOrdersCount = in.readInt();
        this.providerOpeningOrdersCount = in.readInt();
        this.providerImageUrl = in.readString();
        this.isProviderSeeNotificationCall = in.readByte() != 0;
        this.providerSeeNotificationCallOn = in.readString();
        this.providerSeeNotificationCallOnString = in.readString();
        this.isProviderSeeOrderDetails = in.readByte() != 0;
        this.providerSeeOrderDetailsOn = in.readString();
        this.providerSeeOrderDetailsOnString = in.readString();
        this.customerUserId = in.readString();
    }

    public static final Parcelable.Creator<OrderOffer> CREATOR = new Parcelable.Creator<OrderOffer>() {
        @Override
        public OrderOffer createFromParcel(Parcel source) {
            return new OrderOffer(source);
        }

        @Override
        public OrderOffer[] newArray(int size) {
            return new OrderOffer[size];
        }
    };
}
