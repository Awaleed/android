package maksab.sd.customer.models.lookup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSlotModel {

    @SerializedName("fromTime")
    @Expose
    private String fromTime;
    @SerializedName("toTime")
    @Expose
    private String toTime;
    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("isAvailable")
    @Expose
    private Boolean isAvailable;

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
