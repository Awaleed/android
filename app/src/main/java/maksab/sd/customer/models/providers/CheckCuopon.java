package maksab.sd.customer.models.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AdminUser on 02/07/2018.
 */

public class CheckCuopon {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("OrderAmount")
    @Expose
    private Double orderAmount;

    /**
     * No args constructor for use in serialization
     *
     */
    public CheckCuopon(String coupon) {
        this.setCode(coupon);
    }

    /**
     *
     * @param orderAmount
     * @param code
     */
    public CheckCuopon(String code, Double orderAmount) {
        super();
        this.code = code;
        this.orderAmount = orderAmount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

}
