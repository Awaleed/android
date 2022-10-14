package maksab.sd.customer.ui.chat.chats;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;

public class LeftDocumentChat extends RecyclerView.ViewHolder{
    @BindView(R.id.imageLeft)
    ImageView imageLeft;
    @BindView(R.id.imagetimeLeft)
    TextView imagetimeLeft;
    @BindView(R.id.remove_message)
    ImageView remove_message;
    @BindView(R.id.user_name_text_view)
    TextView user_name_text_view;
    @BindView(R.id.user_image_view)
    ImageView user_image_view;

    public LeftDocumentChat(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
