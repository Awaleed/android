package maksab.sd.customer.models.orders.chat;

public class LocationMessage{
    private String address;

    public LocationMessage(String address, String imagePath) {
        this.address = address;
        this.imagePath = imagePath;
    }

    private String imagePath;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
