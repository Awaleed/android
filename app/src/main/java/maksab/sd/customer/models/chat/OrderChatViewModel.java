package maksab.sd.customer.models.chat;

public class OrderChatViewModel {
    private int id;
    private int orderId;
    private String userId;
    private String userName;
    private String userImage;
    private String body;
    private String additionalInfo;
    private boolean isHidden;
    private String createdOn;
    private String createdOnString;
    private int messageType;
    private int userTypeEnum;
    private int orderChatType;

    private boolean isReadByOther;
    private String readOn;
    private String readOnString;

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getBody() {
        return body;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public int getMessageType() {
        return messageType;
    }

    public int getUserTypeEnum() {
        return userTypeEnum;
    }

    public int getOrderChatType() {
        return orderChatType;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public boolean isReadByOther() {
        return isReadByOther;
    }

    public String getReadOn() {
        return readOn;
    }

    public String getReadOnString() {
        return readOnString;
    }

    public String getCreatedOnString() {
        return createdOnString;
    }
}
