package maksab.sd.customer.models.orders.chat;



import maksab.sd.customer.basecode.utility.OrderChatMessageTypeEnum;

public class OrderChatModel {
    private OrderChatMessageTypeEnum orderChatMessageTypeEnum;
    private NormalMessage normalMessage;
    private OfferMessage offerMessage;
    private LocationMessage locationMessage;
    private InvoiceMessage invoiceMessage;


    public OrderChatMessageTypeEnum getOrderChatMessageTypeEnum() {
        return orderChatMessageTypeEnum;
    }

    public void setOrderChatMessageTypeEnum(OrderChatMessageTypeEnum orderChatMessageTypeEnum) {
        this.orderChatMessageTypeEnum = orderChatMessageTypeEnum;
    }

    public InvoiceMessage getInvoiceMessage() {
        return invoiceMessage;
    }

    public void setInvoiceMessage(InvoiceMessage invoiceMessage) {
        this.invoiceMessage = invoiceMessage;
    }

    public LocationMessage getLocationMessage() {
        return locationMessage;
    }

    public void setLocationMessage(LocationMessage locationMessage) {
        this.locationMessage = locationMessage;
    }

    public OfferMessage getOfferMessage() {
        return offerMessage;
    }

    public void setOfferMessage(OfferMessage offerMessage) {
        this.offerMessage = offerMessage;
    }

    public NormalMessage getNormalMessage() {
        return normalMessage;
    }

    public void setNormalMessage(NormalMessage normalMessage) {
        this.normalMessage = normalMessage;
    }









}
