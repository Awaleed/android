package maksab.sd.customer.ui.chat.chats;

public class ChatInputModel {
    private long itemId;
    private int messageType;
    private String body;
    private String additionalInfo;

    public ChatInputModel(long itemId, int messageType, String body, String additionalInfo) {
        this.itemId = itemId;
        this.messageType = messageType;
        this.body = body;
        this.additionalInfo = additionalInfo;
    }

    public long getItemId() {
        return itemId;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
