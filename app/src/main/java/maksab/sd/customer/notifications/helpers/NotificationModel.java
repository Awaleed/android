package maksab.sd.customer.notifications.helpers;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationModel implements Parcelable {
    private int id;
    private int notificationCategoryId;
    private int notificationMethodType;
    private int notificationTypeId;

    private String createdOn;
    private String createdOnString;
    private String parentId;
    private String title;
    private String message;
    private String imageLink;
    private String externalLink;

    private String userId;
    private boolean isRead;
    private boolean isSeen;
    private String readOn;
    private String readOnString;
    private String seenOn;
    private String seenOnString;

    public int getId() {
        return id;
    }

    public int getNotificationCategoryId() {
        return notificationCategoryId;
    }

    public int getNotificationMethodType() {
        return notificationMethodType;
    }

    public int getNotificationTypeId() {
        return notificationTypeId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getCreatedOnString() {
        return createdOnString;
    }

    public String getParentId() {
        return parentId;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isRead() {
        return isRead;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public String getReadOn() {
        return readOn;
    }

    public String getReadOnString() {
        return readOnString;
    }

    public String getSeenOn() {
        return seenOn;
    }

    public String getSeenOnString() {
        return seenOnString;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.notificationCategoryId);
        dest.writeInt(this.notificationMethodType);
        dest.writeInt(this.notificationTypeId);
        dest.writeString(this.createdOn);
        dest.writeString(this.createdOnString);
        dest.writeString(this.parentId);
        dest.writeString(this.title);
        dest.writeString(this.message);
        dest.writeString(this.imageLink);
        dest.writeString(this.externalLink);
        dest.writeString(this.userId);
        dest.writeByte(this.isRead ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSeen ? (byte) 1 : (byte) 0);
        dest.writeString(this.readOn);
        dest.writeString(this.readOnString);
        dest.writeString(this.seenOn);
        dest.writeString(this.seenOnString);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.notificationCategoryId = source.readInt();
        this.notificationMethodType = source.readInt();
        this.notificationTypeId = source.readInt();
        this.createdOn = source.readString();
        this.createdOnString = source.readString();
        this.parentId = source.readString();
        this.title = source.readString();
        this.message = source.readString();
        this.imageLink = source.readString();
        this.externalLink = source.readString();
        this.userId = source.readString();
        this.isRead = source.readByte() != 0;
        this.isSeen = source.readByte() != 0;
        this.readOn = source.readString();
        this.readOnString = source.readString();
        this.seenOn = source.readString();
        this.seenOnString = source.readString();
    }

    public NotificationModel() {
    }

    protected NotificationModel(Parcel in) {
        this.id = in.readInt();
        this.notificationCategoryId = in.readInt();
        this.notificationMethodType = in.readInt();
        this.notificationTypeId = in.readInt();
        this.createdOn = in.readString();
        this.createdOnString = in.readString();
        this.parentId = in.readString();
        this.title = in.readString();
        this.message = in.readString();
        this.imageLink = in.readString();
        this.externalLink = in.readString();
        this.userId = in.readString();
        this.isRead = in.readByte() != 0;
        this.isSeen = in.readByte() != 0;
        this.readOn = in.readString();
        this.readOnString = in.readString();
        this.seenOn = in.readString();
        this.seenOnString = in.readString();
    }

    public static final Parcelable.Creator<NotificationModel> CREATOR = new Parcelable.Creator<NotificationModel>() {
        @Override
        public NotificationModel createFromParcel(Parcel source) {
            return new NotificationModel(source);
        }

        @Override
        public NotificationModel[] newArray(int size) {
            return new NotificationModel[size];
        }
    };
}