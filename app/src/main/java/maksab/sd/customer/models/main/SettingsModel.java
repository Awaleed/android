package maksab.sd.customer.models.main;

public class SettingsModel {
    private String label;
    private String description;

    public SettingsModel(String label , String description, int icon, int id) {
        this.label = label;
        this.icon = icon;
        this.id = id;
        this.description = description;
    }

    private int icon;
    private int id;

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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }
}
