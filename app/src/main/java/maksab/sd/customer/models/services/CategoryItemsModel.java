package maksab.sd.customer.models.services;

public class CategoryItemsModel {

    private int itemId;
    private String name;
    private double price;
    private int quntity;

    public CategoryItemsModel(int itemId, int quntity , String name , double price) {
        this.itemId = itemId;
        this.quntity = quntity;
        this.name = name;
        this.price = price;
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

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuntity() {
        return quntity;
    }

    public void setQuntity(int quntity) {
        this.quntity = quntity;
    }
}
