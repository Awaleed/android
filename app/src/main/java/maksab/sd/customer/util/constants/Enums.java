package maksab.sd.customer.util.constants;

public class Enums {
    public static final String DirectoryName = "Maksab";

    public enum FileType {
        NONE, IMAGE, VIDEO, AUDIO
    }

    public enum MediaDirectUse {
        Dialog, DirectCamera, DirectGallery
    }

    public enum SliderTypeEnum {
        Slider,
        ImageBanner,
        SpecialtyBanner,
        ProviderBanner,
        OfferBanner,
    }

    public enum MessageTypeEnum {
        ZERO_NOT_USED,
        Text, Image, Voice, Location, Video, Document
    }

    public enum MSG_TYPE {
        MY_MESSAGE_TEXT, MY_MESSAGE_IMAGE, MY_MESSAGE_AUDIO, MY_MESSAGE_VIDEO, MY_MESSAGE_DOCUMENT,
        OTHER_MESSAGE_TEXT, OTHER_MESSAGE_IMAGE, OTHER_MESSAGE_AUDIO, OTHER_MESSAGE_VIDEO, OTHER_MESSAGE_DOCUMENT
    }

    public enum ProviderTypeEnum {
        Single, Company, WorkingName
    }

    public enum ProviderClassTypeEnum {
        Uknown, New, Medium, Expert
    }

    public enum CategoryStatusEnum {
        ZERO_NOT_USED,
        Active, Soon, Disabled
    }

    public enum UserTypeEnum {
        ZERO_NOT_USED, Customer, Provider, Support
    }

    public enum ExecutionTypeEnum {
        ZERO_NOT_USED, Checking, Service, Visit, FreeVisit
    }

    public enum ServiceTypeEnum {
        Priced, UnPriced
    }

    public enum ServiceForEnum {
        Maksab, Provider
    }

    public enum OrderStatusEnum {
        ZERO_NOT_USED,
        PENDING,
        /* NOT USED NOW */ REJECTED, ACCEPTED, MOVING, REACHED, STARTING, // NOT USED NOW
        FINISHED, CANCELED,
        WaitingProviders, Opening
    }

    public enum OrderInnerStatusEnum {
        ZERO_NOT_USED, NOTUSED1, NOTUSED2, NOTUSED3, // NOT USED
        MOVING, REACHED, STARTING, Scheduled
    }

    public enum OrderTypeEnum {
        ZERO_NOT_USED, Checking, Service, Gallery, // OLD NOT_USED
        Quotation,
        MaksabPricedService, ProviderPricedService,
        MaksabOffer, ProviderOffer
    }

    public enum OrderUpdateTypeEnum {
        UpdateOrderStatus, UpdateOrderInnerStatus, UpdateOrderExecuation,
        UpdateOrderLocation, UpdateOrderSpecialty, UpdateOrderBody,
        UpdateOrderDangerMode, UpdateOrderQualityChecking
    }

    public enum OrderPurchaseLocationEnum {
        ZERO_NOT_USED,
        NearStore, MyOwnHomeStore, CustomerBuyIt, Other
    }

    public enum SpecialtyTypeEnum {
        Home, Center, Shop, Online
    }

    public enum SpecialtyExecuationModelEnum {
        ZERO_NOT_USED, Quotation, MaksabServices, ProviderServices
    }

    public enum SpecialtyUISelectionEnum {
        ZERO_NOT_USED, QuotationWizard, MaksabServicesWizard, ProviderServicesList, ProvidersList
    }

    public enum TicketStatusEnum {
        ZERO_NOT_USED, OPENED, CLOSED, ALL
    }

    public enum CustomerCombinedQueryEnum {
        ZERO_NOT_USED, Opening, Completed, Canceled, All
    }

    // Notifications
    public enum NotificationCombinedQueryEnum {
        All,
        PublicNotifications,
        PrivateNotifications
    }

    public enum NotificationUpdateType {
        ZERO_NOT_USED,
        Seen, Read
    }

    public enum NotificationCategoryEnum {
        Unknown, // Old Notifiication will be here (most will be action based and manullay and remainder)
        Action, Reminder, Manually, // For Specific User
        BroadCast, Competition     // All Topic without users
    }

    public enum NotificationMethodType {
        None, SMS, Push, SMSPUSH, Email
    }

    public enum NotificationType {
        Unknown,             // Nothing will be here

        // ---- Action Based
        CHAT,               // OLD -- REMOVED
        ORDER_UPDATE,       // GO To Order Detail Page
        ACCOUNT_UPDATE,     // Nothing: Open Home Page and Show Dialog
        QUOTATION_UPDATE,   // OLD -- REMOVED
        TRANSACTION,        // OLD -- No Need Now
        NEW_QUTATION,       // New Quotation
        RESEND_SMS,         // Hidden: This will send SMS from specific mobiles that run support app, only admin receive this

        // V2 New Types
        SMS_OTP,            // From Identity OTPs to track all sended SMSs
        TICKET,             // Go To Detail Page
        OFFER,              // Go To Offer Detail
        CUSTOMER_PROVIDER_PROFILE,   // Go To Provider Detail Page
        SPECIALTY_DETAIL,   // GO To Specialty Page Detail (Listing Providers or Services)
        ORDER_CHAT,         // Order Chat Message
        REFERRAL_POINT,     // Get Referral point
        BALANCE,            // Balance Update (loycal or temp for customer and depoist/withdraw for provider)

        // Moved From Alert -16 for each one to start from 0, if we need migrations for alerts to notifications
        DIRECT_MANUALLY,

        // ---- Reminder Based
        REMINDER_CUSTOMER_QUOTATION_PRICE,
        REMINDER_CUSTOMER_RATE_ORDER,
        REMINDER_CUSTOMER_NOT_OPEN_APP_LONG,
        REMINDER_CUSTOMER_NO_ORDER,
        REMINDER_PROVIDER_BALANCE,
        REMINDER_PROVIDER_NEW_QUOTATION,
        REMINDER_PROVIDER_NEW_ORDER,
        REMINDER_PROVIDER_LOCATION,
        REMINDER_PROVIDER_END_ORDER,
        REMINDER_PROVIDER_PROFILE_NOT_COMPLETED,

        // ---- Broadcasted Based
        CAST,               // Broadcast topic to alls
        CAST_CUSTOMER_OFFER,          // Broadcast to customer for new offer
        CAST_CUSTOMER_PROVIDER_PROFILE, // Broadcast to share provider profile
        CAST_PROVIDER_SPECIALTY,        // Broadcast to providers under specialties
        CAST_LIST_OF_USERS,             // Broadcast to user Ids
        COMP,               // Competition for All
    }

    public enum GuaranteeStatusEnum {
        NotStatred, Active, Finished
    }

    public enum OnBoardingStatusEnum {
        Complete, NewAccount, OldAccount
    }

    public static String getNotificationCategoryEnum(int notificationCategoryId) {
        if (notificationCategoryId == NotificationCategoryEnum.BroadCast.ordinal())
            return "اعلان";
        else if (notificationCategoryId == NotificationCategoryEnum.Action.ordinal())
            return "تنبيه";
        else if (notificationCategoryId == NotificationCategoryEnum.Reminder.ordinal())
            return "تذكير";
        else if (notificationCategoryId == NotificationCategoryEnum.Manually.ordinal())
            return "الدعم الفني";
        else if (notificationCategoryId == NotificationCategoryEnum.Competition.ordinal())
            return "مسابقة";

        return "تنبيه";
    }

    public static String getUserTypeEnum(int userType) {
        if (userType == UserTypeEnum.Customer.ordinal())
            return "عميل";
        else if (userType == UserTypeEnum.Provider.ordinal())
            return "مقدم خدمة";
        else if (userType == UserTypeEnum.Support.ordinal())
            return "الدعم الفني";

        return "";
    }

    public static String getExecuationtypeString(int execuationType) {
        if (execuationType == ExecutionTypeEnum.Checking.ordinal())
            return "فحص";
        else if (execuationType == ExecutionTypeEnum.Service.ordinal())
            return "خدمة";
        else if (execuationType == ExecutionTypeEnum.FreeVisit.ordinal())
            return "معاينة مجانية";
        else if (execuationType == ExecutionTypeEnum.Visit.ordinal())
            return "معاينة";

        return "خدمة";
    }

    public static String getGuaranteeStatusText(int status) {
        if (status == GuaranteeStatusEnum.NotStatred.ordinal())
            return "لم يبدأ";
        else if (status == GuaranteeStatusEnum.Active.ordinal())
            return "ساري";
        else if (status == GuaranteeStatusEnum.Finished.ordinal())
            return "منتهي";
        return "";
    }

    public static String getProviderClassTypeName(int providerClassType) {
        if (providerClassType == ProviderClassTypeEnum.Uknown.ordinal())
            return "غير محدد";
        else if (providerClassType == ProviderClassTypeEnum.New.ordinal())
            return "جديد";
        else if (providerClassType == ProviderClassTypeEnum.Medium.ordinal())
            return "متوسط";
        else if (providerClassType == ProviderClassTypeEnum.Expert.ordinal())
            return "خبير";

        return "غير محدد";
    }

    public static String getProviderTypeString(int providerType) {
        if (providerType == ProviderTypeEnum.Single.ordinal())
            return "فرد";
        else if (providerType == ProviderTypeEnum.Company.ordinal())
            return "شركة";
        else if (providerType == ProviderTypeEnum.WorkingName.ordinal())
            return "اسم عمل";
        return "";
    }

    public static String getHaveDeliveryString(boolean shopHaveDelivery) {
        if (shopHaveDelivery)
            return "نعم";
        return "لا يوجد";
    }

    public static String getCenterWorkingDays(int workingDays) {
        if (workingDays == 1)
            return "طول أيام الاسبوع";
        else if (workingDays == 2)
            return "طول الأيام ما عدا الجمعة";
        else if (workingDays == 3)
            return "طول الأيام ما عدا الجمعة والسبت";

        return "";
    }

    public static String getRateText(int rating) {
        if (rating == 1) {
            return "سيئ جداً";
        } else if (rating == 2) {
            return "سيئ";
        } else if (rating == 3) {
            return "جيد";
        } else if (rating == 4) {
            return "ممتاز";
        } else if (rating == 5) {
            return "رائع";
        }
        return null;
    }

    public enum BalanceTransactionTypeEnum {
        None,
        Deposit, ReverseDeposit,
        Widthdraw, ReverseWidthdraw,
        Comission, ReverseComission,
        Balance, ReverseBalance,
        Coupon, ReverseCoupon,
        Guarantee, ReverseGuarantee,
        Offer, ReverseOffer,
        Bonus, ReverseBonus,
    }
}
