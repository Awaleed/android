package maksab.sd.customer.models.lookup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpecialityTimingModel {

    @SerializedName("dateSlots")
    @Expose
    private List<DaySlotModel> dateSlots = null;
    @SerializedName("timingSlots")
    @Expose
    private List<TimeSlotModel> timingSlots = null;

    public List<DaySlotModel> getDateSlots() {
        return dateSlots;
    }

    public void setDateSlots(List<DaySlotModel> dateSlots) {
        this.dateSlots = dateSlots;
    }

    public List<TimeSlotModel> getTimingSlots() {
        return timingSlots;
    }

    public void setTimingSlots(List<TimeSlotModel> timingSlots) {
        this.timingSlots = timingSlots;
    }
}
