package maksab.sd.customer.models.lookup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DaySlotModel {

    @SerializedName("dayNumber")
    @Expose
    private String dayNumber;
    @SerializedName("monthNumber")
    @Expose
    private String monthNumber;
    @SerializedName("timeStamp")
    @Expose
    private String timeStamp;

    public String getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(String monthNumber) {
        this.monthNumber = monthNumber;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
