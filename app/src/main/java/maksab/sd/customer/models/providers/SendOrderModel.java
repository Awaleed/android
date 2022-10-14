package maksab.sd.customer.models.providers;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dev2 on 11/29/2017.
 */

public class SendOrderModel implements Parcelable {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("specialityId")
    @Expose
    private int specialityId;
    @SerializedName("providerId")
    @Expose
    private String providerId;
    @SerializedName("orderTypeId")
    @Expose
    private int orderTypeId;
    @SerializedName("desireOn")
    @Expose
    private String desireOn;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("couponCode")
    @Expose
    private String couponCode;
    @SerializedName("customerLocationDescription")
    @Expose
    private String customerLocationDescription;
    @SerializedName("customerLatitude")
    @Expose
    private double customerLatitude;
    @SerializedName("customerLongitude")
    @Expose
    private double customerLongitude;

    @SerializedName("quotationId")
    @Expose
    private long quotationId;

    @SerializedName("offerId")
    @Expose
    private long offerId;


    @SerializedName("quantity")
    @Expose
    private int quantity;

    @SerializedName("providerGalleryId")
    @Expose
    private Long providerGalleryId;

    private String selectedTime;

    /**
     * No args constructor for use in serialization
     *
     */
    public SendOrderModel() {
    }

    /**
     * @param specialityId
     * @param providerId
     * @param orderTypeId
     * @param desireOn
     * @param body
     * @param price
     * @param couponCode
     * @param customerLocationDescription
     * @param customerLatitude
     * @param customerLongitude
     * @param quotationId
     * @param offerId
     * @param quantity
     * @param providerGalleryId
     */
    public SendOrderModel(int specialityId, String providerId, int orderTypeId,
                          String desireOn, String body, double price, String couponCode,
                          String customerLocationDescription, double customerLatitude,
                          double customerLongitude, long quotationId, long offerId,
                          int quantity, long providerGalleryId, String selectedTime) {
        super();
        this.specialityId = specialityId;
        this.quotationId = quotationId;
        this.offerId = offerId;
        this.quantity = quantity;
        this.providerGalleryId = providerGalleryId;
        this.userId = userId;
        this.providerId = providerId;
        this.orderTypeId = orderTypeId;
        this.desireOn = desireOn;
        this.body = body;
        this.price = price;
        this.couponCode = couponCode;
        this.customerLocationDescription = customerLocationDescription;
        this.customerLatitude = customerLatitude;
        this.customerLongitude = customerLongitude;
        this.selectedTime = selectedTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public int getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(int orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public String getDesireOn() {
        return desireOn;
    }

    public void setDesireOn(String desireOn) {
        this.desireOn = desireOn;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCustomerLocationDescription() {
        return customerLocationDescription;
    }

    public void setCustomerLocationDescription(String customerLocationDescription) {
        this.customerLocationDescription = customerLocationDescription;
    }

    public double getCustomerLatitude() {
        return customerLatitude;
    }

    public void setCustomerLatitude(double customerLatitude) {
        this.customerLatitude = customerLatitude;
    }

    public double getCustomerLongitude() {
        return customerLongitude;
    }

    public void setCustomerLongitude(double customerLongitude) {
        this.customerLongitude = customerLongitude;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }

    public int getSpecialityId() {
        return specialityId;
    }

    public long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(long quotationId) {
        this.quotationId = quotationId;
    }

    public long getOfferId() {
        return offerId;
    }

    public void setOfferId(long offerId) {
        this.offerId = offerId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeInt(this.specialityId);
        dest.writeString(this.providerId);
        dest.writeInt(this.orderTypeId);
        dest.writeString(this.desireOn);
        dest.writeString(this.body);
        dest.writeDouble(this.price);
        dest.writeString(this.couponCode);
        dest.writeString(this.customerLocationDescription);
        dest.writeDouble(this.customerLatitude);
        dest.writeDouble(this.customerLongitude);
        dest.writeLong(this.quotationId);
        dest.writeLong(this.offerId);
    }

    protected SendOrderModel(Parcel in) {
        this.userId = in.readString();
        this.specialityId = in.readInt();
        this.providerId = in.readString();
        this.orderTypeId = in.readInt();
        this.desireOn = in.readString();
        this.body = in.readString();
        this.price = in.readDouble();
        this.couponCode = in.readString();
        this.customerLocationDescription = in.readString();
        this.customerLatitude = in.readDouble();
        this.customerLongitude = in.readDouble();
        this.quotationId = in.readLong();
        this.offerId = in.readLong();
    }

    public static final Creator<SendOrderModel> CREATOR = new Creator<SendOrderModel>() {
        @Override
        public SendOrderModel createFromParcel(Parcel source) {
            return new SendOrderModel(source);
        }

        @Override
        public SendOrderModel[] newArray(int size) {
            return new SendOrderModel[size];
        }
    };

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getProviderGalleryId() {
        return providerGalleryId;
    }

    public void setProviderGalleryId(long providerGalleryId) {
        this.providerGalleryId = providerGalleryId;
    }
}
