package maksab.sd.customer.models.orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderOfferStatistics implements Parcelable {

    @SerializedName("selectedOrderOfferId")
    @Expose
    private Integer selectedOrderOfferId;
    @SerializedName("selectedProviderDistance")
    @Expose
    private Double selectedProviderDistance;
    @SerializedName("isProviderAllowdToOffer")
    @Expose
    private Boolean isProviderAllowdToOffer;
    @SerializedName("offersCount")
    @Expose
    private Integer offersCount;
    @SerializedName("sendToCount")
    @Expose
    private Integer sendToCount;
    @SerializedName("averagePrice")
    @Expose
    private Double averagePrice;
    @SerializedName("minPrice")
    @Expose
    private Double minPrice;
    @SerializedName("maxPrice")
    @Expose
    private Double maxPrice;

    public Integer getSelectedOrderOfferId() {
        return selectedOrderOfferId;
    }

    public void setSelectedOrderOfferId(Integer selectedOrderOfferId) {
        this.selectedOrderOfferId = selectedOrderOfferId;
    }

    public Double getSelectedProviderDistance() {
        return selectedProviderDistance;
    }

    public void setSelectedProviderDistance(Double selectedProviderDistance) {
        this.selectedProviderDistance = selectedProviderDistance;
    }

    public Boolean getIsProviderAllowdToOffer() {
        return isProviderAllowdToOffer;
    }

    public void setIsProviderAllowdToOffer(Boolean isProviderAllowdToOffer) {
        this.isProviderAllowdToOffer = isProviderAllowdToOffer;
    }

    public Integer getOffersCount() {
        return offersCount;
    }

    public void setOffersCount(Integer offersCount) {
        this.offersCount = offersCount;
    }

    public Integer getSendToCount() {
        return sendToCount;
    }

    public void setSendToCount(Integer sendToCount) {
        this.sendToCount = sendToCount;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.selectedOrderOfferId);
        dest.writeValue(this.selectedProviderDistance);
        dest.writeValue(this.isProviderAllowdToOffer);
        dest.writeValue(this.offersCount);
        dest.writeValue(this.sendToCount);
        dest.writeValue(this.averagePrice);
        dest.writeValue(this.minPrice);
        dest.writeValue(this.maxPrice);
    }

    public void readFromParcel(Parcel source) {
        this.selectedOrderOfferId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.selectedProviderDistance = (Double) source.readValue(Double.class.getClassLoader());
        this.isProviderAllowdToOffer = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.offersCount = (Integer) source.readValue(Integer.class.getClassLoader());
        this.sendToCount = (Integer) source.readValue(Integer.class.getClassLoader());
        this.averagePrice = (Double) source.readValue(Double.class.getClassLoader());
        this.minPrice = (Double) source.readValue(Double.class.getClassLoader());
        this.maxPrice = (Double) source.readValue(Double.class.getClassLoader());
    }

    public OrderOfferStatistics() {
    }

    protected OrderOfferStatistics(Parcel in) {
        this.selectedOrderOfferId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.selectedProviderDistance = (Double) in.readValue(Double.class.getClassLoader());
        this.isProviderAllowdToOffer = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.offersCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.sendToCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.averagePrice = (Double) in.readValue(Double.class.getClassLoader());
        this.minPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.maxPrice = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<OrderOfferStatistics> CREATOR = new Parcelable.Creator<OrderOfferStatistics>() {
        @Override
        public OrderOfferStatistics createFromParcel(Parcel source) {
            return new OrderOfferStatistics(source);
        }

        @Override
        public OrderOfferStatistics[] newArray(int size) {
            return new OrderOfferStatistics[size];
        }
    };
}
