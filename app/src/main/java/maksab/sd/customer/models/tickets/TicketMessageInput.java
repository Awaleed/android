package maksab.sd.customer.models.tickets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketMessageInput {
    @SerializedName("MessageType")
    @Expose
    private Integer messageType;
    @SerializedName("Body")
    @Expose
    private String body;

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
