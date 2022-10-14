package maksab.sd.customer.ui.tickets.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.rygelouv.audiosensei.player.AudioSenseiListObserver;

import maksab.sd.customer.R;
import maksab.sd.customer.basecode.activities.SingleFragmentActivity;
import maksab.sd.customer.models.tickets.TicketModel;
import maksab.sd.customer.ui.chat.chats.ChatListFragment;

public class TicketDetailsActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AudioSenseiListObserver.getInstance().registerLifecycle(getLifecycle());
    }

    @Override
    protected Fragment createFragment() {
        TicketModel ticket = getIntent().getParcelableExtra("Ticket");
        return ChatListFragment.newInstance(ticket.getId(),
                ChatListFragment.ChatForType.Ticket, ticket.isResolved());
    }

    @Override
    protected String getActivityTitle() {
        TicketModel ticket = getIntent().getParcelableExtra("Ticket");
        return getString(R.string.ticket_details) + " #" + ticket.getId();
    }

    @Override
    protected boolean isBackArrayHidden() {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}