package maksab.sd.customer.models.tickets;

public class TicketMessageModel {
    private int id;
    private int ticketId;
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

    public int getId() {
        return id;
    }

    public int getTicketId() {
        return ticketId;
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

    public int getMessageType() {
        return messageType;
    }

    public int getUserTypeEnum() {
        return userTypeEnum;
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
}
