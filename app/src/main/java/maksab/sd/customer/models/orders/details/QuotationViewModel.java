package maksab.sd.customer.models.orders.details;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dev on 3/15/2018.
 */

public class QuotationViewModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("customerUserId")
    @Expose
    private String customerUserId;
    @SerializedName("customerId")
    @Expose
    private Long customerId;
    @SerializedName("specialtyId")
    @Expose
    private int specialtyId;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("customerLatitude")
    @Expose
    private Double customerLatitude;
    @SerializedName("customerLongitude")
    @Expose
    private Double customerLongitude;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("createdOnSring")
    @Expose
    private String createdOnSring;
    @SerializedName("closedOn")
    @Expose
    private String closedOn;
    @SerializedName("closedOnString")
    @Expose
    private String closedOnString;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("specialtyName")
    @Expose
    private String specialtyName;
    @SerializedName("averagePrice")
    @Expose
    private Double averagePrice;
    @SerializedName("providersCount")
    @Expose
    private Long providersCount;
    @SerializedName("offersCount")
    @Expose
    private Long offersCount;
    @SerializedName("imagePaths")
    @Expose
    private List<String> imagePaths = null;
    @SerializedName("selectedQuotationOfferId")
    @Expose
    private Long selectedQuotationOfferId;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("customerLocationDescription")
    @Expose
    private String customerLocationDescription;

    @SerializedName("orderBody")
    @Expose
    private String orderBody;

    private boolean isApproved;

    @SerializedName("totalPrice")
    @Expose
    private Double totalPrice;

    @SerializedName("customerLocationStaticMapImage")
    @Expose
    private String customerLocationStaticMapImage;

    @SerializedName("selectedTime")
    @Expose
    private String selectedTime;


    /**
     * No args constructor for use in serialization
     *
     */
    public QuotationViewModel() {
    }

    /**
     *
     * @param selectedQuotationOfferId
     * @param customerName
     * @param body
     * @param closedOn
     * @param specialtyId
     * @param customerLatitude
     * @param createdOnSring
     * @param specialtyName
     * @param customerLongitude
     * @param id
     * @param createdOn
     * @param averagePrice
     * @param customerUserId
     * @param closedOnString
     * @param customerId
     * @param offersCount
     * @param providersCount
     * @param imagePaths
     */
    public QuotationViewModel(Long id, String customerUserId, Long customerId, int specialtyId, String body, Double customerLatitude, Double customerLongitude, String createdOn, String createdOnSring, String closedOn, String closedOnString, String customerName, String specialtyName, Double averagePrice, Long providersCount, Long offersCount, List<String> imagePaths, Long selectedQuotationOfferId) {
        super();
        this.id = id;
        this.customerUserId = customerUserId;
        this.customerId = customerId;
        this.specialtyId = specialtyId;
        this.body = body;
        this.customerLatitude = customerLatitude;
        this.customerLongitude = customerLongitude;
        this.createdOn = createdOn;
        this.createdOnSring = createdOnSring;
        this.closedOn = closedOn;
        this.closedOnString = closedOnString;
        this.customerName = customerName;
        this.specialtyName = specialtyName;
        this.averagePrice = averagePrice;
        this.providersCount = providersCount;
        this.offersCount = offersCount;
        this.imagePaths = imagePaths;
        this.selectedQuotationOfferId = selectedQuotationOfferId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerUserId() {
        return customerUserId;
    }

    public void setCustomerUserId(String customerUserId) {
        this.customerUserId = customerUserId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Double getCustomerLatitude() {
        return customerLatitude;
    }

    public void setCustomerLatitude(Double customerLatitude) {
        this.customerLatitude = customerLatitude;
    }

    public Double getCustomerLongitude() {
        return customerLongitude;
    }

    public void setCustomerLongitude(Double customerLongitude) {
        this.customerLongitude = customerLongitude;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedOnSring() {
        return createdOnSring;
    }

    public void setCreatedOnSring(String createdOnSring) {
        this.createdOnSring = createdOnSring;
    }

    public String getClosedOn() {
        return closedOn;
    }

    public void setClosedOn(String closedOn) {
        this.closedOn = closedOn;
    }

    public String getClosedOnString() {
        return closedOnString;
    }

    public void setClosedOnString(String closedOnString) {
        this.closedOnString = closedOnString;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Long getProvidersCount() {
        return providersCount;
    }

    public void setProvidersCount(Long providersCount) {
        this.providersCount = providersCount;
    }

    public Long getOffersCount() {
        return offersCount;
    }

    public void setOffersCount(Long offersCount) {
        this.offersCount = offersCount;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public Long getSelectedQuotationOfferId() {
        return selectedQuotationOfferId;
    }

    public void setSelectedQuotationOfferId(Long selectedQuotationOfferId) {
        this.selectedQuotationOfferId = selectedQuotationOfferId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerLocationDescription() {
        return customerLocationDescription;
    }

    public void setCustomerLocationDescription(String customerLocationDescription) {
        this.customerLocationDescription = customerLocationDescription;
    }

    public String getOrderBody() {
        return orderBody;
    }

    public void setOrderBody(String orderBody) {
        this.orderBody = orderBody;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.customerUserId);
        dest.writeValue(this.customerId);
        dest.writeInt(this.specialtyId);
        dest.writeString(this.body);
        dest.writeValue(this.customerLatitude);
        dest.writeValue(this.customerLongitude);
        dest.writeString(this.createdOn);
        dest.writeString(this.createdOnSring);
        dest.writeString(this.closedOn);
        dest.writeString(this.closedOnString);
        dest.writeString(this.customerName);
        dest.writeString(this.specialtyName);
        dest.writeValue(this.averagePrice);
        dest.writeValue(this.providersCount);
        dest.writeValue(this.offersCount);
        dest.writeStringList(this.imagePaths);
        dest.writeValue(this.selectedQuotationOfferId);
        dest.writeString(this.status);
        dest.writeString(this.customerLocationDescription);
        dest.writeString(this.orderBody);
    }

    protected QuotationViewModel(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.customerUserId = in.readString();
        this.customerId = (Long) in.readValue(Long.class.getClassLoader());
        this.specialtyId = in.readInt();
        this.body = in.readString();
        this.customerLatitude = (Double) in.readValue(Double.class.getClassLoader());
        this.customerLongitude = (Double) in.readValue(Double.class.getClassLoader());
        this.createdOn = in.readString();
        this.createdOnSring = in.readString();
        this.closedOn = in.readString();
        this.closedOnString = in.readString();
        this.customerName = in.readString();
        this.specialtyName = in.readString();
        this.averagePrice = (Double) in.readValue(Double.class.getClassLoader());
        this.providersCount = (Long) in.readValue(Long.class.getClassLoader());
        this.offersCount = (Long) in.readValue(Long.class.getClassLoader());
        this.imagePaths = in.createStringArrayList();
        this.selectedQuotationOfferId = (Long) in.readValue(Long.class.getClassLoader());
        this.status = in.readString();
        this.customerLocationDescription = in.readString();
        this.orderBody = in.readString();
    }

    public static final Creator<QuotationViewModel> CREATOR = new Creator<QuotationViewModel>() {
        @Override
        public QuotationViewModel createFromParcel(Parcel source) {
            return new QuotationViewModel(source);
        }

        @Override
        public QuotationViewModel[] newArray(int size) {
            return new QuotationViewModel[size];
        }
    };

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCustomerLocationStaticMapImage() {
        return customerLocationStaticMapImage;
    }

    public void setCustomerLocationStaticMapImage(String customerLocationStaticMapImage) {
        customerLocationStaticMapImage = customerLocationStaticMapImage;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }
}
