package maksab.sd.customer.models.orders.details;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderImageViewModel implements Parcelable {
    private String imagePath;

    public OrderImageViewModel(String imagePath) {
        this.imagePath = imagePath;
    }

    public OrderImageViewModel(){

    }

    public String getImagePath() {
        return imagePath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imagePath);
    }

    public void readFromParcel(Parcel source) {
        this.imagePath = source.readString();
    }

    protected OrderImageViewModel(Parcel in) {
        this.imagePath = in.readString();
    }

    public static final Parcelable.Creator<OrderImageViewModel> CREATOR = new Parcelable.Creator<OrderImageViewModel>() {
        @Override
        public OrderImageViewModel createFromParcel(Parcel source) {
            return new OrderImageViewModel(source);
        }

        @Override
        public OrderImageViewModel[] newArray(int size) {
            return new OrderImageViewModel[size];
        }
    };
}

