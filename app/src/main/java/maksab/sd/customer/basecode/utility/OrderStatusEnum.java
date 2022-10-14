package maksab.sd.customer.basecode.utility;

public enum OrderStatusEnum {
    NotSpecified, PENDING,
    // Not Used Anymore
    REJECTED, ACCEPTED, MOVING, REACHED, STARTING,
    // Still Used
    FINISHED , CANCELED,
    // New Status
    WaitingProviders, Opening
}
