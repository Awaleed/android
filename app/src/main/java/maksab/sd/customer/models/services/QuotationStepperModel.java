package maksab.sd.customer.models.services;

import java.util.ArrayList;
import java.util.List;

public class QuotationStepperModel {
    private int id;
    private List<CategoryItemsModel> multiItems = new ArrayList<>();
    private int quntity;
    private String name;
    private double price;
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public void setId(int id) {
        this.id = id;
    }

    public List<CategoryItemsModel> getMultiItems() {
        return multiItems;
    }

    public void setMultiItems(List<CategoryItemsModel> multiItems) {
        this.multiItems = multiItems;
    }

    public int getQuntity() {
        return quntity;
    }

    public void setQuntity(int quntity) {
        this.quntity = quntity;
    }
}
