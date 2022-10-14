package maksab.sd.customer.models.orders.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelReasonModel {


    @SerializedName("closedReason")
    @Expose
    private String closedReason;
    @SerializedName("closedBody")
    @Expose
    private String closedBody;

    /**
     * No args constructor for use in serialization
     *
     */
    public CancelReasonModel() {
    }

    /**
     *
     * @param closedReason
     * @param closedBody
     */
    public CancelReasonModel(String closedReason, String closedBody) {
        super();
        this.closedReason = closedReason;
        this.closedBody = closedBody;
    }

    public String getClosedReason() {
        return closedReason;
    }

    public void setClosedReason(String closedReason) {
        this.closedReason = closedReason;
    }

    public String getClosedBody() {
        return closedBody;
    }

    public void setClosedBody(String closedBody) {
        this.closedBody = closedBody;
    }

}
