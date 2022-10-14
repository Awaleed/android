package maksab.sd.customer.models.providers;

public class SpecialOffersModel {
    private String offer_title;
    private String body;
    private String price;
    private String end_on;

    public SpecialOffersModel(String offer_title, String body, String price, String end_on) {
        this.offer_title = offer_title;
        this.body = body;
        this.price = price;
        this.end_on = end_on;
    }

    public String getOffer_title() {
        return offer_title;
    }

    public void setOffer_title(String offer_title) {
        this.offer_title = offer_title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEnd_on() {
        return end_on;
    }

    public void setEnd_on(String end_on) {
        this.end_on = end_on;
    }
}
