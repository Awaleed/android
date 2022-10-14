package maksab.sd.customer.ui.chat.chats;

import java.util.ArrayList;
import java.util.List;

import maksab.sd.customer.models.chat.OrderChatViewModel;
import maksab.sd.customer.models.tickets.TicketMessageModel;


public class ChatMapper {

    public static List<ChatViewModel> mapOrders(List<OrderChatViewModel> messageModels) {
        List<ChatViewModel> items = new ArrayList<>();
        for(OrderChatViewModel model: messageModels) {
            items.add(mapOrder(model));
        }
        return items;
    }

    public static ChatViewModel mapOrder(OrderChatViewModel msg) {
        ChatViewModel model = new ChatViewModel(
                msg.getId(), msg.getOrderId(), msg.getCreatedOn(), msg.getCreatedOnString(),
                msg.getBody(), msg.getAdditionalInfo(), msg.getMessageType(),
                msg.getUserId(), msg.getUserName(), msg.getUserImage(),
                msg.getUserTypeEnum(),
                msg.isReadByOther(), msg.getReadOn(), msg.getReadOnString()
        );
        return model;
    }

    public static List<ChatViewModel> map(List<TicketMessageModel> messageModels) {
        List<ChatViewModel> items = new ArrayList<>();
        for(TicketMessageModel model: messageModels) {
            items.add(map(model));
        }
        return items;
    }

    public static ChatViewModel map(TicketMessageModel msg) {
        ChatViewModel model = new ChatViewModel(
                msg.getId(), msg.getTicketId(), msg.getCreatedOn(), msg.getCreatedOnString(),
                msg.getBody(), msg.getAdditionalInfo(), msg.getMessageType(),
                msg.getUserId(), msg.getUserFullName(), msg.getUserProfileLogo(), msg.getUserTypeEnum(),
                msg.isReadByOther(), msg.getReadOn(), msg.getReadOnString()
        );
        return model;
    }
}
