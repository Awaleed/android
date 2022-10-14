package maksab.sd.customer.models.orders.chat;

public class NormalMessage{

    private String body;
    private boolean isMedia;
    private String time;

    public NormalMessage(String body, boolean isMedia, String time) {
        this.body = body;
        this.isMedia = isMedia;
        this.time = time;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isMedia() {
        return isMedia;
    }

    public void setMedia(boolean media) {
        isMedia = media;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
