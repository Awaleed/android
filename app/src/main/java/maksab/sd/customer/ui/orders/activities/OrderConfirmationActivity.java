package maksab.sd.customer.ui.orders.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.BaseActivity;
import maksab.sd.customer.basecode.utility.OrderStatusEnum;
import maksab.sd.customer.storage.OrderInMemoryStorage;
import maksab.sd.customer.util.constants.Enums;

public class OrderConfirmationActivity extends BaseActivity {
    @BindView(R.id.dynamic_label)
    TextView dynamic_label;
    @BindView(R.id.go_to_home)
    Button go_to_home;
    @BindView(R.id.go_to_order_details)
    Button go_to_order_details;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        int orderTypeId = getIntent().getIntExtra("orderTypeId" , 0);
        if (orderTypeId == Enums.OrderTypeEnum.Quotation.ordinal()) {
            dynamic_label.setText(R.string.you_will_receive_offers);
        }

        go_to_order_details.setOnClickListener(view1 -> {
            Intent intent = new Intent(OrderConfirmationActivity.this, OrderDetailsActivity.class);
            intent.putExtra("id", Integer.parseInt(OrderInMemoryStorage.getCreatedOrderId() + ""));
            intent.putExtra("order.status", OrderStatusEnum.PENDING);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        go_to_home.setOnClickListener(view1 -> {
            finish();
        });
    }
}