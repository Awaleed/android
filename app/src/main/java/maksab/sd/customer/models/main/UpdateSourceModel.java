package maksab.sd.customer.models.main;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateSourceModel {
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("callSettings")
    @Expose
    private String callSettings;

    public UpdateSourceModel(String source, String callSettings) {
        this.source = source;
        this.callSettings = callSettings;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCallSettings() {
        return callSettings;
    }

    public void setCallSettings(String callSettings) {
        this.callSettings = callSettings;
    }
}
