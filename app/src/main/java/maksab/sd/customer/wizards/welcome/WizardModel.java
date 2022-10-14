package maksab.sd.customer.wizards.welcome;

import android.os.Parcel;
import android.os.Parcelable;

public class WizardModel implements Parcelable {
    public static final Creator<WizardModel> CREATOR = new Creator<WizardModel>() {
        @Override
        public WizardModel createFromParcel(Parcel source) {
            return new WizardModel(source);
        }

        @Override
        public WizardModel[] newArray(int size) {
            return new WizardModel[size];
        }
    };
    private final String background;
    private final String title;
    private final String description;

    public WizardModel(String background, String title, String description) {
        this.background = background;
        this.title = title;
        this.description = description;
    }

    protected WizardModel(Parcel in) {
        this.background = in.readString();
        this.title = in.readString();
        this.description = in.readString();
    }

    public String getBackground() {
        return background;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.background);
        dest.writeString(this.title);
        dest.writeString(this.description);
    }
}
