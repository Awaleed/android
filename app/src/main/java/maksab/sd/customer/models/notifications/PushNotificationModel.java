package maksab.sd.customer.models.notifications;

import android.os.Parcel;
import android.os.Parcelable;

public class PushNotificationModel implements Parcelable {
    private String id;
    private String type;
    private String title;
    private String message;
    private String externallink;
    private String imagelink;
    private String notificationId;

    public PushNotificationModel(String id, String type, String title,
                                 String message, String externallink, String imagelink, String notificationId) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.message = message;
        this.externallink = externallink;
        this.imagelink = imagelink;
        this.notificationId = notificationId;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getExternallink() {
        return externallink;
    }

    public String getImagelink() {
        return imagelink;
    }

    public String getNotificationId() {
        return notificationId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeString(this.title);
        dest.writeString(this.message);
        dest.writeString(this.externallink);
        dest.writeString(this.imagelink);
        dest.writeString(this.notificationId);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.type = source.readString();
        this.title = source.readString();
        this.message = source.readString();
        this.externallink = source.readString();
        this.imagelink = source.readString();
        this.notificationId = source.readString();
    }

    protected PushNotificationModel(Parcel in) {
        this.id = in.readString();
        this.type = in.readString();
        this.title = in.readString();
        this.message = in.readString();
        this.externallink = in.readString();
        this.imagelink = in.readString();
        this.notificationId = in.readString();
    }

    public static final Parcelable.Creator<PushNotificationModel> CREATOR = new Parcelable.Creator<PushNotificationModel>() {
        @Override
        public PushNotificationModel createFromParcel(Parcel source) {
            return new PushNotificationModel(source);
        }

        @Override
        public PushNotificationModel[] newArray(int size) {
            return new PushNotificationModel[size];
        }
    };
}
