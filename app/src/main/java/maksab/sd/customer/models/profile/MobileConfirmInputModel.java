package maksab.sd.customer.models.profile;

public class MobileConfirmInputModel {
    private String mobile;
    private String oldMobile;
    private String clientType;
    private String code;

    public MobileConfirmInputModel(String mobile, String oldMobile, String clientType, String code) {
        this.mobile = mobile;
        this.oldMobile = oldMobile;
        this.clientType = clientType;
        this.code = code;
    }
}
