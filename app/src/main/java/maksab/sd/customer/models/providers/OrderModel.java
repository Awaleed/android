package maksab.sd.customer.models.providers;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import maksab.sd.customer.models.address.OrderAddressViewModel;
import maksab.sd.customer.models.orders.OrderOfferStatistics;
import maksab.sd.customer.models.orders.details.OrderDetailViewModel;
import maksab.sd.customer.models.orders.details.OrderImageViewModel;
import maksab.sd.customer.models.orders.details.SpecialtyQuestionAnswerViewModel;

public class OrderModel implements Parcelable {
    private int id;
    private int customerId;
    private String customerName;
    private String customerImage;
    private String customerUserId;
    private String customerMobile;
    private int providerId;
    private String providerName;
    private String providerImage;
    private String providerUserId;
    private String providerMobile;
    private OrderOfferStatistics orderOfferStatistics;
    private boolean isCustomerAgreeThePrice;
    private double rejectedPrice;
    private int executionTypeEnum;
    private String providerOrderCompletationNote;
    private int orderTypeId;
    private int orderStatusId;
    private String orderTypeArabic;
    private String orderStatusArabic;
    private int innerOrderStatusId;
    private String innerOrderStatusString;
    private String innerOrderStatusUpdatedOn;
    private String innerOrderStatusUpdatedOnString;
    private int specialityId;
    private String specialityName;
    private String body;
    private double initialPrice;
    private double guaranteePrice;
    private double transportationPrice;
    private int guaranteePeriodInDays;
    private String guaranteeEndOn;
    private String guaranteeEndOnString;
    private int guaranteeStatusEnum;
    private int orderAddressId;
    private OrderAddressViewModel orderAddressViewModel;
    private String createdOn;
    private String createdOnString;
    private String desireOn;
    private String desireOnString;
    private String selectedTime;
    private boolean acceptFlexibleTime;
    private String agreedTime;
    private String agreedTimeOn;
    private String agreedTimeOnString;
    private String orderStatusUpdatedOn;
    private String orderStatusUpdatedOnString;
    private String cancelReason;
    private String cancelByUserId;
    private String closedBody;
    private double couponDiscoun;
    private double customerBalanceDiscount;
    private String coupon;

    // Rate
    private double rate;
    private String rateComment;
    private double behaviorRate;
    private boolean openComplain;
    private double customerRate;
    private String customerRateComment;
    private String rateOn;
    private String customerRateOn;
    private String rateOnString;
    private String customerRateOnString;

    private List<OrderImageViewModel> orderImages;
    private List<OrderDetailViewModel> orderItems;
    private List<SpecialtyQuestionAnswerViewModel> orderAnswers;

    private boolean isEndReportRequired;
    private boolean isEndReportIsAdded;
    private boolean isSpearItemsRequired;
    private boolean isSpearItemsAdded;
    private double spearItemsTotal;

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerImage() {
        return customerImage;
    }

    public String getCustomerUserId() {
        return customerUserId;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public int getProviderId() {
        return providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getProviderImage() {
        return providerImage;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public String getProviderMobile() {
        return providerMobile;
    }

    public OrderOfferStatistics getOrderOfferStatistics() {
        return orderOfferStatistics;
    }

    public boolean isCustomerAgreeThePrice() {
        return isCustomerAgreeThePrice;
    }

    public double getRejectedPrice() {
        return rejectedPrice;
    }

    public int getExecutionTypeEnum() {
        return executionTypeEnum;
    }

    public String getProviderOrderCompletationNote() {
        return providerOrderCompletationNote;
    }

    public int getOrderTypeId() {
        return orderTypeId;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public String getOrderTypeArabic() {
        return orderTypeArabic;
    }

    public String getOrderStatusArabic() {
        return orderStatusArabic;
    }

    public int getInnerOrderStatusId() {
        return innerOrderStatusId;
    }

    public String getInnerOrderStatusString() {
        return innerOrderStatusString;
    }

    public String getInnerOrderStatusUpdatedOn() {
        return innerOrderStatusUpdatedOn;
    }

    public String getInnerOrderStatusUpdatedOnString() {
        return innerOrderStatusUpdatedOnString;
    }

    public int getSpecialityId() {
        return specialityId;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public String getBody() {
        return body;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public double getGuaranteePrice() {
        return guaranteePrice;
    }

    public double getTransportationPrice() {
        return transportationPrice;
    }

    public int getGuaranteePeriodInDays() {
        return guaranteePeriodInDays;
    }

    public String getGuaranteeEndOn() {
        return guaranteeEndOn;
    }

    public String getGuaranteeEndOnString() {
        return guaranteeEndOnString;
    }

    public int getGuaranteeStatusEnum() {
        return guaranteeStatusEnum;
    }

    public int getOrderAddressId() {
        return orderAddressId;
    }

    public OrderAddressViewModel getOrderAddressViewModel() {
        return orderAddressViewModel;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getCreatedOnString() {
        return createdOnString;
    }

    public String getDesireOn() {
        return desireOn;
    }

    public String getDesireOnString() {
        return desireOnString;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public boolean isAcceptFlexibleTime() {
        return acceptFlexibleTime;
    }

    public String getAgreedTime() {
        return agreedTime;
    }

    public String getAgreedTimeOn() {
        return agreedTimeOn;
    }

    public String getAgreedTimeOnString() {
        return agreedTimeOnString;
    }

    public String getOrderStatusUpdatedOn() {
        return orderStatusUpdatedOn;
    }

    public String getOrderStatusUpdatedOnString() {
        return orderStatusUpdatedOnString;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public String getCancelByUserId() {
        return cancelByUserId;
    }

    public String getClosedBody() {
        return closedBody;
    }

    public double getCouponDiscoun() {
        return couponDiscoun;
    }

    public double getCustomerBalanceDiscount() {
        return customerBalanceDiscount;
    }

    public String getCoupon() {
        return coupon;
    }

    public double getRate() {
        return rate;
    }

    public String getRateComment() {
        return rateComment;
    }

    public double getBehaviorRate() {
        return behaviorRate;
    }

    public boolean isOpenComplain() {
        return openComplain;
    }

    public double getCustomerRate() {
        return customerRate;
    }

    public String getCustomerRateComment() {
        return customerRateComment;
    }

    public String getRateOn() {
        return rateOn;
    }

    public String getCustomerRateOn() {
        return customerRateOn;
    }

    public String getRateOnString() {
        return rateOnString;
    }

    public String getCustomerRateOnString() {
        return customerRateOnString;
    }

    public List<OrderImageViewModel> getOrderImages() {
        return orderImages;
    }

    public List<OrderDetailViewModel> getOrderItems() {
        return orderItems;
    }

    public List<SpecialtyQuestionAnswerViewModel> getOrderAnswers() {
        return orderAnswers;
    }

    public boolean isEndReportRequired() {
        return isEndReportRequired;
    }

    public boolean isEndReportIsAdded() {
        return isEndReportIsAdded;
    }

    public boolean isSpearItemsRequired() {
        return isSpearItemsRequired;
    }

    public boolean isSpearItemsAdded() {
        return isSpearItemsAdded;
    }

    public double getSpearItemsTotal() {
        return spearItemsTotal;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.customerId);
        dest.writeString(this.customerName);
        dest.writeString(this.customerImage);
        dest.writeString(this.customerUserId);
        dest.writeString(this.customerMobile);
        dest.writeInt(this.providerId);
        dest.writeString(this.providerName);
        dest.writeString(this.providerImage);
        dest.writeString(this.providerUserId);
        dest.writeString(this.providerMobile);
        dest.writeParcelable(this.orderOfferStatistics, flags);
        dest.writeByte(this.isCustomerAgreeThePrice ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.rejectedPrice);
        dest.writeInt(this.executionTypeEnum);
        dest.writeString(this.providerOrderCompletationNote);
        dest.writeInt(this.orderTypeId);
        dest.writeInt(this.orderStatusId);
        dest.writeString(this.orderTypeArabic);
        dest.writeString(this.orderStatusArabic);
        dest.writeInt(this.innerOrderStatusId);
        dest.writeString(this.innerOrderStatusString);
        dest.writeString(this.innerOrderStatusUpdatedOn);
        dest.writeString(this.innerOrderStatusUpdatedOnString);
        dest.writeInt(this.specialityId);
        dest.writeString(this.specialityName);
        dest.writeString(this.body);
        dest.writeDouble(this.initialPrice);
        dest.writeDouble(this.guaranteePrice);
        dest.writeDouble(this.transportationPrice);
        dest.writeInt(this.guaranteePeriodInDays);
        dest.writeString(this.guaranteeEndOn);
        dest.writeString(this.guaranteeEndOnString);
        dest.writeInt(this.guaranteeStatusEnum);
        dest.writeInt(this.orderAddressId);
        dest.writeParcelable(this.orderAddressViewModel, flags);
        dest.writeString(this.createdOn);
        dest.writeString(this.createdOnString);
        dest.writeString(this.desireOn);
        dest.writeString(this.desireOnString);
        dest.writeString(this.selectedTime);
        dest.writeByte(this.acceptFlexibleTime ? (byte) 1 : (byte) 0);
        dest.writeString(this.agreedTime);
        dest.writeString(this.agreedTimeOn);
        dest.writeString(this.agreedTimeOnString);
        dest.writeString(this.orderStatusUpdatedOn);
        dest.writeString(this.orderStatusUpdatedOnString);
        dest.writeString(this.cancelReason);
        dest.writeString(this.cancelByUserId);
        dest.writeString(this.closedBody);
        dest.writeDouble(this.couponDiscoun);
        dest.writeDouble(this.customerBalanceDiscount);
        dest.writeString(this.coupon);
        dest.writeDouble(this.rate);
        dest.writeString(this.rateComment);
        dest.writeDouble(this.behaviorRate);
        dest.writeByte(this.openComplain ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.customerRate);
        dest.writeString(this.customerRateComment);
        dest.writeString(this.rateOn);
        dest.writeString(this.customerRateOn);
        dest.writeString(this.rateOnString);
        dest.writeString(this.customerRateOnString);
        dest.writeTypedList(this.orderImages);
        dest.writeTypedList(this.orderItems);
        dest.writeTypedList(this.orderAnswers);
        dest.writeByte(this.isEndReportRequired ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isEndReportIsAdded ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSpearItemsRequired ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSpearItemsAdded ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.spearItemsTotal);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.customerId = source.readInt();
        this.customerName = source.readString();
        this.customerImage = source.readString();
        this.customerUserId = source.readString();
        this.customerMobile = source.readString();
        this.providerId = source.readInt();
        this.providerName = source.readString();
        this.providerImage = source.readString();
        this.providerUserId = source.readString();
        this.providerMobile = source.readString();
        this.orderOfferStatistics = source.readParcelable(OrderOfferStatistics.class.getClassLoader());
        this.isCustomerAgreeThePrice = source.readByte() != 0;
        this.rejectedPrice = source.readDouble();
        this.executionTypeEnum = source.readInt();
        this.providerOrderCompletationNote = source.readString();
        this.orderTypeId = source.readInt();
        this.orderStatusId = source.readInt();
        this.orderTypeArabic = source.readString();
        this.orderStatusArabic = source.readString();
        this.innerOrderStatusId = source.readInt();
        this.innerOrderStatusString = source.readString();
        this.innerOrderStatusUpdatedOn = source.readString();
        this.innerOrderStatusUpdatedOnString = source.readString();
        this.specialityId = source.readInt();
        this.specialityName = source.readString();
        this.body = source.readString();
        this.initialPrice = source.readDouble();
        this.guaranteePrice = source.readDouble();
        this.transportationPrice = source.readDouble();
        this.guaranteePeriodInDays = source.readInt();
        this.guaranteeEndOn = source.readString();
        this.guaranteeEndOnString = source.readString();
        this.guaranteeStatusEnum = source.readInt();
        this.orderAddressId = source.readInt();
        this.orderAddressViewModel = source.readParcelable(OrderAddressViewModel.class.getClassLoader());
        this.createdOn = source.readString();
        this.createdOnString = source.readString();
        this.desireOn = source.readString();
        this.desireOnString = source.readString();
        this.selectedTime = source.readString();
        this.acceptFlexibleTime = source.readByte() != 0;
        this.agreedTime = source.readString();
        this.agreedTimeOn = source.readString();
        this.agreedTimeOnString = source.readString();
        this.orderStatusUpdatedOn = source.readString();
        this.orderStatusUpdatedOnString = source.readString();
        this.cancelReason = source.readString();
        this.cancelByUserId = source.readString();
        this.closedBody = source.readString();
        this.couponDiscoun = source.readDouble();
        this.customerBalanceDiscount = source.readDouble();
        this.coupon = source.readString();
        this.rate = source.readDouble();
        this.rateComment = source.readString();
        this.behaviorRate = source.readDouble();
        this.openComplain = source.readByte() != 0;
        this.customerRate = source.readDouble();
        this.customerRateComment = source.readString();
        this.rateOn = source.readString();
        this.customerRateOn = source.readString();
        this.rateOnString = source.readString();
        this.customerRateOnString = source.readString();
        this.orderImages = source.createTypedArrayList(OrderImageViewModel.CREATOR);
        this.orderItems = source.createTypedArrayList(OrderDetailViewModel.CREATOR);
        this.orderAnswers = source.createTypedArrayList(SpecialtyQuestionAnswerViewModel.CREATOR);
        this.isEndReportRequired = source.readByte() != 0;
        this.isEndReportIsAdded = source.readByte() != 0;
        this.isSpearItemsRequired = source.readByte() != 0;
        this.isSpearItemsAdded = source.readByte() != 0;
        this.spearItemsTotal = source.readDouble();
    }

    public OrderModel() {
    }

    protected OrderModel(Parcel in) {
        this.id = in.readInt();
        this.customerId = in.readInt();
        this.customerName = in.readString();
        this.customerImage = in.readString();
        this.customerUserId = in.readString();
        this.customerMobile = in.readString();
        this.providerId = in.readInt();
        this.providerName = in.readString();
        this.providerImage = in.readString();
        this.providerUserId = in.readString();
        this.providerMobile = in.readString();
        this.orderOfferStatistics = in.readParcelable(OrderOfferStatistics.class.getClassLoader());
        this.isCustomerAgreeThePrice = in.readByte() != 0;
        this.rejectedPrice = in.readDouble();
        this.executionTypeEnum = in.readInt();
        this.providerOrderCompletationNote = in.readString();
        this.orderTypeId = in.readInt();
        this.orderStatusId = in.readInt();
        this.orderTypeArabic = in.readString();
        this.orderStatusArabic = in.readString();
        this.innerOrderStatusId = in.readInt();
        this.innerOrderStatusString = in.readString();
        this.innerOrderStatusUpdatedOn = in.readString();
        this.innerOrderStatusUpdatedOnString = in.readString();
        this.specialityId = in.readInt();
        this.specialityName = in.readString();
        this.body = in.readString();
        this.initialPrice = in.readDouble();
        this.guaranteePrice = in.readDouble();
        this.transportationPrice = in.readDouble();
        this.guaranteePeriodInDays = in.readInt();
        this.guaranteeEndOn = in.readString();
        this.guaranteeEndOnString = in.readString();
        this.guaranteeStatusEnum = in.readInt();
        this.orderAddressId = in.readInt();
        this.orderAddressViewModel = in.readParcelable(OrderAddressViewModel.class.getClassLoader());
        this.createdOn = in.readString();
        this.createdOnString = in.readString();
        this.desireOn = in.readString();
        this.desireOnString = in.readString();
        this.selectedTime = in.readString();
        this.acceptFlexibleTime = in.readByte() != 0;
        this.agreedTime = in.readString();
        this.agreedTimeOn = in.readString();
        this.agreedTimeOnString = in.readString();
        this.orderStatusUpdatedOn = in.readString();
        this.orderStatusUpdatedOnString = in.readString();
        this.cancelReason = in.readString();
        this.cancelByUserId = in.readString();
        this.closedBody = in.readString();
        this.couponDiscoun = in.readDouble();
        this.customerBalanceDiscount = in.readDouble();
        this.coupon = in.readString();
        this.rate = in.readDouble();
        this.rateComment = in.readString();
        this.behaviorRate = in.readDouble();
        this.openComplain = in.readByte() != 0;
        this.customerRate = in.readDouble();
        this.customerRateComment = in.readString();
        this.rateOn = in.readString();
        this.customerRateOn = in.readString();
        this.rateOnString = in.readString();
        this.customerRateOnString = in.readString();
        this.orderImages = in.createTypedArrayList(OrderImageViewModel.CREATOR);
        this.orderItems = in.createTypedArrayList(OrderDetailViewModel.CREATOR);
        this.orderAnswers = in.createTypedArrayList(SpecialtyQuestionAnswerViewModel.CREATOR);
        this.isEndReportRequired = in.readByte() != 0;
        this.isEndReportIsAdded = in.readByte() != 0;
        this.isSpearItemsRequired = in.readByte() != 0;
        this.isSpearItemsAdded = in.readByte() != 0;
        this.spearItemsTotal = in.readDouble();
    }

    public static final Parcelable.Creator<OrderModel> CREATOR = new Parcelable.Creator<OrderModel>() {
        @Override
        public OrderModel createFromParcel(Parcel source) {
            return new OrderModel(source);
        }

        @Override
        public OrderModel[] newArray(int size) {
            return new OrderModel[size];
        }
    };
}
