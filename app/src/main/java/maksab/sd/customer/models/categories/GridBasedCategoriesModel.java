package maksab.sd.customer.models.categories;

public class GridBasedCategoriesModel {

    private int id;
    private String label;
    private String imageUrl;

    public GridBasedCategoriesModel(int id, String label, String imageUrl) {
        this.id = id;
        this.label = label;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
