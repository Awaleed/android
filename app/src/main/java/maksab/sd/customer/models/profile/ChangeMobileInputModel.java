package maksab.sd.customer.models.profile;

public class ChangeMobileInputModel {
    private String mobile;
    private String oldMobile;
    private String clientType;

    public ChangeMobileInputModel(String mobile, String oldMobile, String clientType) {
        this.mobile = mobile;
        this.oldMobile = oldMobile;
        this.clientType = clientType;
    }
}
