package maksab.sd.customer.util.general;

import android.content.Context;

import maksab.sd.customer.R;
import maksab.sd.customer.models.providers.OrderModel;

public class OrderUtils {
    public static String getOrderTotalPriceAsString(Context context, OrderModel order) {
        String price = "";

        if (context != null)
            price = context.getString(R.string.not_specified);

        if (order.getInitialPrice() > 0)
            price = NumbersUtil.formatAmount(getOrderTotalPrice(order));

        return price;
    }

    public static double getOrderTotalPrice(OrderModel order) {
        if (order.getInitialPrice() > 0) {
            double total = order.getInitialPrice() -
                    order.getCouponDiscoun() -
                    order.getCustomerBalanceDiscount() +
                    order.getTransportationPrice();

            if (total > 0)
                return total;

            return 0;
        }
        return 0;
    }
}
