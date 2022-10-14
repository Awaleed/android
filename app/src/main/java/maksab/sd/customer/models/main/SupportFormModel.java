package maksab.sd.customer.models.main;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AdminUser on 01/24/2018.
 */

public class SupportFormModel {


    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Body")
    @Expose
    private String body;
    @SerializedName("Role")
    @Expose
    private String role;
    @SerializedName("UserntegerId")
    @Expose
    private Integer userntegerId;

    /**
     * No args constructor for use in serialization
     */
    public SupportFormModel() {
    }

    /**
     * @param userntegerId
     * @param body
     * @param title
     * @param role
     */
    public SupportFormModel(String title, String body, String role, Integer userntegerId) {
        super();
        this.title = title;
        this.body = body;
        this.role = role;
        this.userntegerId = userntegerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getUserntegerId() {
        return userntegerId;
    }

    public void setUserntegerId(Integer userntegerId) {
        this.userntegerId = userntegerId;
    }

}
