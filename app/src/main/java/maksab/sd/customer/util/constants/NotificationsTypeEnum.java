package maksab.sd.customer.util.constants;

public enum NotificationsTypeEnum {
    Unkown,             // Nothing will be here

    // ---- Action Based
    CHAT,           // OLD -- REMOVED
    ORDER_UPDATE,       // GO To Order Detail Page
    ACCOUNT_UPDATE,     // Nothing: Open Home Page and Show Dialog
    QUOTATION_UPDATE,   // OLD -- REMOVED
    TRANSACTION,        // OLD -- No Need Now
    NEW_QUTATION,       // OLD -- REMOVED
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

    // Movded From Alert -16 for each one to start from 0, if we need migrations for alerts to notifications
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

    // ---- Broacasted Based
    CAST,               // Boradcast topic to alls
    CAST_CUSTOMER_OFFER,          // Broadcast to customer for new offer
    CAST_CUSTOMER_PROVIDER_PROFILE, // Broadcast to share provider profile
    CAST_PROVIDER_SPECIALTY,        // Broadcast to providers under specialties
    CAST_LIST_OF_USERS,             // Broadcast to user Ids
    COMP,
}
