package maksab.sd.customer.models.orders.chat;

public class InvoiceMessage{
    private String servicePrice;
    private String creditDiscount;
    private String promoCodeDiscount;

    public InvoiceMessage(String servicePrice, String creditDiscount, String promoCodeDiscount, String finalPrice) {
        this.servicePrice = servicePrice;
        this.creditDiscount = creditDiscount;
        this.promoCodeDiscount = promoCodeDiscount;
        this.finalPrice = finalPrice;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getCreditDiscount() {
        return creditDiscount;
    }

    public void setCreditDiscount(String creditDiscount) {
        this.creditDiscount = creditDiscount;
    }

    public String getPromoCodeDiscount() {
        return promoCodeDiscount;
    }

    public void setPromoCodeDiscount(String promoCodeDiscount) {
        this.promoCodeDiscount = promoCodeDiscount;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    private String finalPrice;


}
