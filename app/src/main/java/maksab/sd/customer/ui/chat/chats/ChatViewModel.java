package maksab.sd.customer.ui.chat.chats;

public class ChatViewModel {
    private int id;
    private int itemId;
    private String createdOn;
    private String createdOnString;
    private String body;
    private String additionalInfo;
    private String userId;
    private String userFullName;
    private String userProfileLogo;
    private int messageType;
    private int userTypeEnum;
    private boolean isReadByOther;
    private String readOn;
    private String readOnString;
    private String itemTypeId;

    public ChatViewModel(int id, int itemId, String createdOn, String createdOnString,
                         String body, String additionalInfo, int messageType,
                         String userId, String userFullName, String userProfileLogo,
                         int userTypeEnum,
                         boolean isReadByOther, String readOn, String readOnString) {
        this.id = id;
        this.itemId = itemId;
        this.createdOn = createdOn;
        this.createdOnString = createdOnString;

        this.body = body;
        this.additionalInfo = additionalInfo;
        this.messageType = messageType;

        this.userId = userId;
        this.userFullName = userFullName;
        this.userProfileLogo = userProfileLogo;
        this.userTypeEnum = userTypeEnum;

        this.isReadByOther = isReadByOther;
        this.readOn = readOn;
        this.readOnString = readOnString;
    }

    public Integer getId() {
        return id;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getCreatedOnString() {
        return createdOnString;
    }

    public String getBody() {
        return body;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getUserProfileLogo() {
        return userProfileLogo;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public Integer getUserTypeEnum() {
        return userTypeEnum;
    }

    public Boolean getReadByOther() {
        return isReadByOther;
    }

    public String getReadOn() {
        return readOn;
    }

    public String getReadOnString() {
        return readOnString;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemTypeId(String itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setReadOn(String readOn) {
        this.readOn = readOn;
    }
}
