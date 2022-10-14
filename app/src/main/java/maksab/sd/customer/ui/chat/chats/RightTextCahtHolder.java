package maksab.sd.customer.ui.chat.chats;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;

public class RightTextCahtHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.textRight)
    TextView textRight;
    @BindView(R.id.timeRight)
    TextView timeRight;
    @BindView(R.id.remove_message)
    ImageView remove_message;
    @BindView(R.id.user_name_text_view)
    TextView user_name_text_view;
    @BindView(R.id.user_image_view)
    ImageView user_image_view;

    public RightTextCahtHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}