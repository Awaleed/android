package maksab.sd.customer.models.tickets;

import android.os.Parcel;
import android.os.Parcelable;

public class TicketModel implements Parcelable {
    private Integer id;
    private String title;
    private String body;
    private String userId;
    private Integer userIntegerId;
    private String role;
    private String createdOn;
    private Integer ticketCategoryId;
    private Integer ticketSubCategoryId;
    private String ticketCategoryArabic;
    private String ticketSubCategoryArabic;
    private Boolean isResolved;
    private String actionComment;
    private String actionByUserId;
    private String actionOn;
    private String actionOnString;
    private String createdOnString;

    public TicketModel() {
    }

    protected TicketModel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.body = in.readString();
        this.userId = in.readString();
        this.userIntegerId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.role = in.readString();
        this.createdOn = in.readString();
        this.ticketCategoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ticketSubCategoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ticketCategoryArabic = in.readString();
        this.ticketSubCategoryArabic = in.readString();
        this.isResolved = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.actionComment = in.readString();
        this.actionByUserId = in.readString();
        this.actionOn = in.readString();
        this.actionOnString = in.readString();
        this.createdOnString = in.readString();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getUserIntegerId() {
        return userIntegerId;
    }

    public String getRole() {
        return role;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public Integer getTicketCategoryId() {
        return ticketCategoryId;
    }

    public Integer getTicketSubCategoryId() {
        return ticketSubCategoryId;
    }

    public String getTicketCategoryArabic() {
        return ticketCategoryArabic;
    }

    public String getTicketSubCategoryArabic() {
        return ticketSubCategoryArabic;
    }

    public Boolean isResolved() {
        return isResolved;
    }

    public String getActionComment() {
        return actionComment;
    }

    public String getActionByUserId() {
        return actionByUserId;
    }

    public String getActionOn() {
        return actionOn;
    }

    public String getActionOnString() {
        return actionOnString;
    }

    public String getCreatedOnString() {
        return createdOnString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeString(this.userId);
        dest.writeValue(this.userIntegerId);
        dest.writeString(this.role);
        dest.writeString(this.createdOn);
        dest.writeValue(this.ticketCategoryId);
        dest.writeValue(this.ticketSubCategoryId);
        dest.writeString(this.ticketCategoryArabic);
        dest.writeString(this.ticketSubCategoryArabic);
        dest.writeValue(this.isResolved);
        dest.writeString(this.actionComment);
        dest.writeString(this.actionByUserId);
        dest.writeString(this.actionOn);
        dest.writeString(this.actionOnString);
        dest.writeString(this.createdOnString);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Integer) source.readValue(Integer.class.getClassLoader());
        this.title = source.readString();
        this.body = source.readString();
        this.userId = source.readString();
        this.userIntegerId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.role = source.readString();
        this.createdOn = source.readString();
        this.ticketCategoryId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.ticketSubCategoryId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.ticketCategoryArabic = source.readString();
        this.ticketSubCategoryArabic = source.readString();
        this.isResolved = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.actionComment = source.readString();
        this.actionByUserId = source.readString();
        this.actionOn = source.readString();
        this.actionOnString = source.readString();
        this.createdOnString = source.readString();
    }

    public static final Parcelable.Creator<TicketModel> CREATOR = new Parcelable.Creator<TicketModel>() {
        @Override
        public TicketModel createFromParcel(Parcel source) {
            return new TicketModel(source);
        }

        @Override
        public TicketModel[] newArray(int size) {
            return new TicketModel[size];
        }
    };
}
