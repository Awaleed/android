package maksab.sd.customer.models.providers;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dev on 11/14/2017.
 */

public class ProviderDetailsModel implements Parcelable {
    private int id;
    private String userId;
    private int providerClassTypeId;
    private String providerClassTypeName;
    private int maxSpecialtiesCount;
    private int maxOpenedOrdersCount;
    private int genderId;
    private int nationalityId;
    private int cityId;
    private String fullName;
    private String mobileNo;
    private String mobileNo2;
    private String birthDate;
    private String birthDateString;
    private String profileImage;
    private String bio;
    private float rate;
    private int viewCount;
    private boolean isOnline;
    private String isOnlineUpdateOn;
    private String isOnlineUpdateOnString;
    private boolean isOnlineCalculated;
    private String isOnlineCalculatedOn;

    private String registerOn;
    private String registerOnString;
    private int completedOrders;
    private int currentlyOrders;
    private int cancledOrders;

    // Status
    private int providerStatusId;
    private String providerVerificationMessage;
    private String supportStatusMessage;
    private String supportChangeStatusOn;
    private String supportChangeStatusOnString;
    private String providerStatusArabic;

    // Track provider records
    private String lastTimeUseTheApp;
    private String lastTimeUseTheAppString;
    private int versionCode;

    // other related data
    private String genderArabic;
    private String cityArabic;
    private String nationalityArabic;

    // data depend on the context (the calling user)
    private boolean isFavorite; // is current user favorite or not
    private int currentSpecialityIndex; // what specilaity will be displayed currently
    private double distanceFromUser; // what is the distance of this provider
    private String lastUpdatedLocation; // when last seen
    private String lastUpdatedLocationString;
    private float lastLatitude;
    private float lastLongitude;
    private String lastLocationImage;

    private String deepLink;
    private String customerProfileDeepLink;
    private String customerProfileDeepLinkDescription;
    private int deepLinkCredits;
    private int appCredits;
    private int providerType;
    private String businessName;
    private String businessDescription;
    private boolean haveCenter;
    private String centerName;
    private String centerDescription;
    private String centerLocationDescription;
    private float centerLatitude;
    private float centerLongitude;
    private String centerOpenAt;
    private String centerCloseAt;
    private int centerOpenningDays;
    private int centerSpecailtyId;
    private String centerLogoImage;
    private String centerCoverImage;
    private boolean haveStore;
    private String shopName;
    private String shopDescription;
    private String shopLocationDescription;
    private float shopLatitude;
    private float shopLongitude;
    private int shopSpecailtyId;
    private String shopLogoImage;
    private String shopCoverImage;
    private boolean isShopHaveDelivery;
    private double deliveryPriceNear;
    private double deliveryPriceFar;

    private List<ProviderSpecialtyModel> specialities;
    private boolean manualRegistration;

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public int getProviderClassTypeId() {
        return providerClassTypeId;
    }

    public String getProviderClassTypeName() {
        return providerClassTypeName;
    }

    public int getMaxSpecialtiesCount() {
        return maxSpecialtiesCount;
    }

    public int getMaxOpenedOrdersCount() {
        return maxOpenedOrdersCount;
    }

    public int getGenderId() {
        return genderId;
    }

    public int getNationalityId() {
        return nationalityId;
    }

    public int getCityId() {
        return cityId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getMobileNo2() {
        return mobileNo2;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getBirthDateString() {
        return birthDateString;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getBio() {
        return bio;
    }

    public float getRate() {
        return rate;
    }

    public int getViewCount() {
        return viewCount;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getIsOnlineUpdateOn() {
        return isOnlineUpdateOn;
    }

    public String getIsOnlineUpdateOnString() {
        return isOnlineUpdateOnString;
    }

    public boolean isOnlineCalculated() {
        return isOnlineCalculated;
    }

    public String getIsOnlineCalculatedOn() {
        return isOnlineCalculatedOn;
    }

    public String getRegisterOn() {
        return registerOn;
    }

    public String getRegisterOnString() {
        return registerOnString;
    }

    public int getCompletedOrders() {
        return completedOrders;
    }

    public int getCurrentlyOrders() {
        return currentlyOrders;
    }

    public int getCancledOrders() {
        return cancledOrders;
    }

    public int getProviderStatusId() {
        return providerStatusId;
    }

    public String getProviderVerificationMessage() {
        return providerVerificationMessage;
    }

    public String getSupportStatusMessage() {
        return supportStatusMessage;
    }

    public String getSupportChangeStatusOn() {
        return supportChangeStatusOn;
    }

    public String getSupportChangeStatusOnString() {
        return supportChangeStatusOnString;
    }

    public String getProviderStatusArabic() {
        return providerStatusArabic;
    }

    public String getLastTimeUseTheApp() {
        return lastTimeUseTheApp;
    }

    public String getLastTimeUseTheAppString() {
        return lastTimeUseTheAppString;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getGenderArabic() {
        return genderArabic;
    }

    public String getCityArabic() {
        return cityArabic;
    }

    public String getNationalityArabic() {
        return nationalityArabic;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public int getCurrentSpecialityIndex() {
        return currentSpecialityIndex;
    }

    public double getDistanceFromUser() {
        return distanceFromUser;
    }

    public String getLastUpdatedLocation() {
        return lastUpdatedLocation;
    }

    public String getLastUpdatedLocationString() {
        return lastUpdatedLocationString;
    }

    public float getLastLatitude() {
        return lastLatitude;
    }

    public float getLastLongitude() {
        return lastLongitude;
    }

    public String getLastLocationImage() {
        return lastLocationImage;
    }

    public String getDeepLink() {
        return deepLink;
    }

    public String getCustomerProfileDeepLink() {
        return customerProfileDeepLink;
    }

    public String getCustomerProfileDeepLinkDescription() {
        return customerProfileDeepLinkDescription;
    }

    public int getDeepLinkCredits() {
        return deepLinkCredits;
    }

    public int getAppCredits() {
        return appCredits;
    }

    public int getProviderType() {
        return providerType;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public boolean isHaveCenter() {
        return haveCenter;
    }

    public String getCenterName() {
        return centerName;
    }

    public String getCenterDescription() {
        return centerDescription;
    }

    public String getCenterLocationDescription() {
        return centerLocationDescription;
    }

    public float getCenterLatitude() {
        return centerLatitude;
    }

    public float getCenterLongitude() {
        return centerLongitude;
    }

    public String getCenterOpenAt() {
        return centerOpenAt;
    }

    public String getCenterCloseAt() {
        return centerCloseAt;
    }

    public int getCenterOpenningDays() {
        return centerOpenningDays;
    }

    public int getCenterSpecailtyId() {
        return centerSpecailtyId;
    }

    public String getCenterLogoImage() {
        return centerLogoImage;
    }

    public String getCenterCoverImage() {
        return centerCoverImage;
    }

    public boolean isHaveStore() {
        return haveStore;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public String getShopLocationDescription() {
        return shopLocationDescription;
    }

    public float getShopLatitude() {
        return shopLatitude;
    }

    public float getShopLongitude() {
        return shopLongitude;
    }

    public int getShopSpecailtyId() {
        return shopSpecailtyId;
    }

    public String getShopLogoImage() {
        return shopLogoImage;
    }

    public String getShopCoverImage() {
        return shopCoverImage;
    }

    public boolean isShopHaveDelivery() {
        return isShopHaveDelivery;
    }

    public double getDeliveryPriceNear() {
        return deliveryPriceNear;
    }

    public double getDeliveryPriceFar() {
        return deliveryPriceFar;
    }

    public List<ProviderSpecialtyModel> getSpecialities() {
        return specialities;
    }

    public boolean isManualRegistration() {
        return manualRegistration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.userId);
        dest.writeInt(this.providerClassTypeId);
        dest.writeString(this.providerClassTypeName);
        dest.writeInt(this.maxSpecialtiesCount);
        dest.writeInt(this.maxOpenedOrdersCount);
        dest.writeInt(this.genderId);
        dest.writeInt(this.nationalityId);
        dest.writeInt(this.cityId);
        dest.writeString(this.fullName);
        dest.writeString(this.mobileNo);
        dest.writeString(this.mobileNo2);
        dest.writeString(this.birthDate);
        dest.writeString(this.birthDateString);
        dest.writeString(this.profileImage);
        dest.writeString(this.bio);
        dest.writeFloat(this.rate);
        dest.writeInt(this.viewCount);
        dest.writeByte(this.isOnline ? (byte) 1 : (byte) 0);
        dest.writeString(this.isOnlineUpdateOn);
        dest.writeString(this.isOnlineUpdateOnString);
        dest.writeByte(this.isOnlineCalculated ? (byte) 1 : (byte) 0);
        dest.writeString(this.isOnlineCalculatedOn);
        dest.writeString(this.registerOn);
        dest.writeString(this.registerOnString);
        dest.writeInt(this.completedOrders);
        dest.writeInt(this.currentlyOrders);
        dest.writeInt(this.cancledOrders);
        dest.writeInt(this.providerStatusId);
        dest.writeString(this.providerVerificationMessage);
        dest.writeString(this.supportStatusMessage);
        dest.writeString(this.supportChangeStatusOn);
        dest.writeString(this.supportChangeStatusOnString);
        dest.writeString(this.providerStatusArabic);
        dest.writeString(this.lastTimeUseTheApp);
        dest.writeString(this.lastTimeUseTheAppString);
        dest.writeInt(this.versionCode);
        dest.writeString(this.genderArabic);
        dest.writeString(this.cityArabic);
        dest.writeString(this.nationalityArabic);
        dest.writeByte(this.isFavorite ? (byte) 1 : (byte) 0);
        dest.writeInt(this.currentSpecialityIndex);
        dest.writeDouble(this.distanceFromUser);
        dest.writeString(this.lastUpdatedLocation);
        dest.writeString(this.lastUpdatedLocationString);
        dest.writeFloat(this.lastLatitude);
        dest.writeFloat(this.lastLongitude);
        dest.writeString(this.lastLocationImage);
        dest.writeString(this.deepLink);
        dest.writeString(this.customerProfileDeepLink);
        dest.writeString(this.customerProfileDeepLinkDescription);
        dest.writeInt(this.deepLinkCredits);
        dest.writeInt(this.appCredits);
        dest.writeInt(this.providerType);
        dest.writeString(this.businessName);
        dest.writeString(this.businessDescription);
        dest.writeByte(this.haveCenter ? (byte) 1 : (byte) 0);
        dest.writeString(this.centerName);
        dest.writeString(this.centerDescription);
        dest.writeString(this.centerLocationDescription);
        dest.writeFloat(this.centerLatitude);
        dest.writeFloat(this.centerLongitude);
        dest.writeString(this.centerOpenAt);
        dest.writeString(this.centerCloseAt);
        dest.writeInt(this.centerOpenningDays);
        dest.writeInt(this.centerSpecailtyId);
        dest.writeString(this.centerLogoImage);
        dest.writeString(this.centerCoverImage);
        dest.writeByte(this.haveStore ? (byte) 1 : (byte) 0);
        dest.writeString(this.shopName);
        dest.writeString(this.shopDescription);
        dest.writeString(this.shopLocationDescription);
        dest.writeFloat(this.shopLatitude);
        dest.writeFloat(this.shopLongitude);
        dest.writeInt(this.shopSpecailtyId);
        dest.writeString(this.shopLogoImage);
        dest.writeString(this.shopCoverImage);
        dest.writeByte(this.isShopHaveDelivery ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.deliveryPriceNear);
        dest.writeDouble(this.deliveryPriceFar);
        dest.writeTypedList(this.specialities);
        dest.writeByte(this.manualRegistration ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.userId = source.readString();
        this.providerClassTypeId = source.readInt();
        this.providerClassTypeName = source.readString();
        this.maxSpecialtiesCount = source.readInt();
        this.maxOpenedOrdersCount = source.readInt();
        this.genderId = source.readInt();
        this.nationalityId = source.readInt();
        this.cityId = source.readInt();
        this.fullName = source.readString();
        this.mobileNo = source.readString();
        this.mobileNo2 = source.readString();
        this.birthDate = source.readString();
        this.birthDateString = source.readString();
        this.profileImage = source.readString();
        this.bio = source.readString();
        this.rate = source.readFloat();
        this.viewCount = source.readInt();
        this.isOnline = source.readByte() != 0;
        this.isOnlineUpdateOn = source.readString();
        this.isOnlineUpdateOnString = source.readString();
        this.isOnlineCalculated = source.readByte() != 0;
        this.isOnlineCalculatedOn = source.readString();
        this.registerOn = source.readString();
        this.registerOnString = source.readString();
        this.completedOrders = source.readInt();
        this.currentlyOrders = source.readInt();
        this.cancledOrders = source.readInt();
        this.providerStatusId = source.readInt();
        this.providerVerificationMessage = source.readString();
        this.supportStatusMessage = source.readString();
        this.supportChangeStatusOn = source.readString();
        this.supportChangeStatusOnString = source.readString();
        this.providerStatusArabic = source.readString();
        this.lastTimeUseTheApp = source.readString();
        this.lastTimeUseTheAppString = source.readString();
        this.versionCode = source.readInt();
        this.genderArabic = source.readString();
        this.cityArabic = source.readString();
        this.nationalityArabic = source.readString();
        this.isFavorite = source.readByte() != 0;
        this.currentSpecialityIndex = source.readInt();
        this.distanceFromUser = source.readDouble();
        this.lastUpdatedLocation = source.readString();
        this.lastUpdatedLocationString = source.readString();
        this.lastLatitude = source.readFloat();
        this.lastLongitude = source.readFloat();
        this.lastLocationImage = source.readString();
        this.deepLink = source.readString();
        this.customerProfileDeepLink = source.readString();
        this.customerProfileDeepLinkDescription = source.readString();
        this.deepLinkCredits = source.readInt();
        this.appCredits = source.readInt();
        this.providerType = source.readInt();
        this.businessName = source.readString();
        this.businessDescription = source.readString();
        this.haveCenter = source.readByte() != 0;
        this.centerName = source.readString();
        this.centerDescription = source.readString();
        this.centerLocationDescription = source.readString();
        this.centerLatitude = source.readFloat();
        this.centerLongitude = source.readFloat();
        this.centerOpenAt = source.readString();
        this.centerCloseAt = source.readString();
        this.centerOpenningDays = source.readInt();
        this.centerSpecailtyId = source.readInt();
        this.centerLogoImage = source.readString();
        this.centerCoverImage = source.readString();
        this.haveStore = source.readByte() != 0;
        this.shopName = source.readString();
        this.shopDescription = source.readString();
        this.shopLocationDescription = source.readString();
        this.shopLatitude = source.readFloat();
        this.shopLongitude = source.readFloat();
        this.shopSpecailtyId = source.readInt();
        this.shopLogoImage = source.readString();
        this.shopCoverImage = source.readString();
        this.isShopHaveDelivery = source.readByte() != 0;
        this.deliveryPriceNear = source.readDouble();
        this.deliveryPriceFar = source.readDouble();
        this.specialities = source.createTypedArrayList(ProviderSpecialtyModel.CREATOR);
        this.manualRegistration = source.readByte() != 0;
    }

    public ProviderDetailsModel() {
    }

    protected ProviderDetailsModel(Parcel in) {
        this.id = in.readInt();
        this.userId = in.readString();
        this.providerClassTypeId = in.readInt();
        this.providerClassTypeName = in.readString();
        this.maxSpecialtiesCount = in.readInt();
        this.maxOpenedOrdersCount = in.readInt();
        this.genderId = in.readInt();
        this.nationalityId = in.readInt();
        this.cityId = in.readInt();
        this.fullName = in.readString();
        this.mobileNo = in.readString();
        this.mobileNo2 = in.readString();
        this.birthDate = in.readString();
        this.birthDateString = in.readString();
        this.profileImage = in.readString();
        this.bio = in.readString();
        this.rate = in.readFloat();
        this.viewCount = in.readInt();
        this.isOnline = in.readByte() != 0;
        this.isOnlineUpdateOn = in.readString();
        this.isOnlineUpdateOnString = in.readString();
        this.isOnlineCalculated = in.readByte() != 0;
        this.isOnlineCalculatedOn = in.readString();
        this.registerOn = in.readString();
        this.registerOnString = in.readString();
        this.completedOrders = in.readInt();
        this.currentlyOrders = in.readInt();
        this.cancledOrders = in.readInt();
        this.providerStatusId = in.readInt();
        this.providerVerificationMessage = in.readString();
        this.supportStatusMessage = in.readString();
        this.supportChangeStatusOn = in.readString();
        this.supportChangeStatusOnString = in.readString();
        this.providerStatusArabic = in.readString();
        this.lastTimeUseTheApp = in.readString();
        this.lastTimeUseTheAppString = in.readString();
        this.versionCode = in.readInt();
        this.genderArabic = in.readString();
        this.cityArabic = in.readString();
        this.nationalityArabic = in.readString();
        this.isFavorite = in.readByte() != 0;
        this.currentSpecialityIndex = in.readInt();
        this.distanceFromUser = in.readDouble();
        this.lastUpdatedLocation = in.readString();
        this.lastUpdatedLocationString = in.readString();
        this.lastLatitude = in.readFloat();
        this.lastLongitude = in.readFloat();
        this.lastLocationImage = in.readString();
        this.deepLink = in.readString();
        this.customerProfileDeepLink = in.readString();
        this.customerProfileDeepLinkDescription = in.readString();
        this.deepLinkCredits = in.readInt();
        this.appCredits = in.readInt();
        this.providerType = in.readInt();
        this.businessName = in.readString();
        this.businessDescription = in.readString();
        this.haveCenter = in.readByte() != 0;
        this.centerName = in.readString();
        this.centerDescription = in.readString();
        this.centerLocationDescription = in.readString();
        this.centerLatitude = in.readFloat();
        this.centerLongitude = in.readFloat();
        this.centerOpenAt = in.readString();
        this.centerCloseAt = in.readString();
        this.centerOpenningDays = in.readInt();
        this.centerSpecailtyId = in.readInt();
        this.centerLogoImage = in.readString();
        this.centerCoverImage = in.readString();
        this.haveStore = in.readByte() != 0;
        this.shopName = in.readString();
        this.shopDescription = in.readString();
        this.shopLocationDescription = in.readString();
        this.shopLatitude = in.readFloat();
        this.shopLongitude = in.readFloat();
        this.shopSpecailtyId = in.readInt();
        this.shopLogoImage = in.readString();
        this.shopCoverImage = in.readString();
        this.isShopHaveDelivery = in.readByte() != 0;
        this.deliveryPriceNear = in.readDouble();
        this.deliveryPriceFar = in.readDouble();
        this.specialities = in.createTypedArrayList(ProviderSpecialtyModel.CREATOR);
        this.manualRegistration = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ProviderDetailsModel> CREATOR = new Parcelable.Creator<ProviderDetailsModel>() {
        @Override
        public ProviderDetailsModel createFromParcel(Parcel source) {
            return new ProviderDetailsModel(source);
        }

        @Override
        public ProviderDetailsModel[] newArray(int size) {
            return new ProviderDetailsModel[size];
        }
    };
}

