package maksab.sd.customer.models.tickets;

import android.os.Parcel;
import android.os.Parcelable;

public class TicketInputModel implements Parcelable {
    public static final Parcelable.Creator<TicketInputModel> CREATOR = new Parcelable.Creator<TicketInputModel>() {
        @Override
        public TicketInputModel createFromParcel(Parcel source) {
            return new TicketInputModel(source);
        }

        @Override
        public TicketInputModel[] newArray(int size) {
            return new TicketInputModel[size];
        }
    };
    private String body;
    private Integer ticketCategoryId;
    private Integer ticketSubCategoryId;
    private String categoryName;
    private String subCategoryName;

    public TicketInputModel() {
    }

    protected TicketInputModel(Parcel in) {
        this.body = in.readString();
        this.ticketCategoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ticketSubCategoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.categoryName = in.readString();
        this.subCategoryName = in.readString();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getTicketCategoryId() {
        return ticketCategoryId;
    }

    public void setTicketCategoryId(Integer ticketCategoryId) {
        this.ticketCategoryId = ticketCategoryId;
    }

    public Integer getTicketSubCategoryId() {
        return ticketSubCategoryId;
    }

    public void setTicketSubCategoryId(Integer ticketSubCategoryId) {
        this.ticketSubCategoryId = ticketSubCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.body);
        dest.writeValue(this.ticketCategoryId);
        dest.writeValue(this.ticketSubCategoryId);
        dest.writeString(this.categoryName);
        dest.writeString(this.subCategoryName);
    }

    public void readFromParcel(Parcel source) {
        this.body = source.readString();
        this.ticketCategoryId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.ticketSubCategoryId = (Integer) source.readValue(Integer.class.getClassLoader());
        this.categoryName = source.readString();
        this.subCategoryName = source.readString();
    }
}

