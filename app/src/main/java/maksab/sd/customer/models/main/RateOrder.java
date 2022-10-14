package maksab.sd.customer.models.main;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dev2 on 2/8/2018.
 */

public class RateOrder {

    @SerializedName("orderId")
    @Expose
    private Long orderId;
    @SerializedName("rate")
    @Expose
    private int rate;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("behaviorRate")
    @Expose
    private int behaviorRate;
    @SerializedName("openComplain")
    @Expose
    private boolean openComplain;
    /**
     * No args constructor for use in serialization
     *
     */
    public RateOrder() {
    }

    /**
     *
     * @param rate
     * @param comment
     * @param orderId
     */
    public RateOrder(Long orderId, int rate, String comment, int behaviorRate, boolean openComplain) {
        super();
        this.orderId = orderId;
        this.rate = rate;
        this.comment = comment;
        this.behaviorRate = behaviorRate;
        this.openComplain = openComplain;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
