package maksab.sd.customer.storage;

import java.util.ArrayList;
import java.util.List;

public class OrderSummaryModel {

    private String address;
    private String selectedTimeAndDate;
    private double guranteePrice;
    private double couponAmount;
    private String couponCode;
    private double balance;

    private List<OrderSummaryDetails> orderSummaryDetails = new ArrayList<>();

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSelectedTimeAndDate() {
        return selectedTimeAndDate;
    }

    public void setSelectedTimeAndDate(String selectedTimeAndDate) {
        this.selectedTimeAndDate = selectedTimeAndDate;
    }

    public List<OrderSummaryDetails> getOrderSummaryDetails() {
        return orderSummaryDetails;
    }

    public void setOrderSummaryDetails(List<OrderSummaryDetails> orderSummaryDetails) {
        this.orderSummaryDetails = orderSummaryDetails;
    }

    public double getGuranteePrice() {
        return guranteePrice;
    }

    public void setGuranteePrice(double guranteePrice) {
        this.guranteePrice = guranteePrice;
    }

    public double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

