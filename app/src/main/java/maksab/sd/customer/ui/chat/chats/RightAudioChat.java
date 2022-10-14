package maksab.sd.customer.ui.chat.chats;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rygelouv.audiosensei.player.AudioSenseiPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;

public class RightAudioChat extends RecyclerView.ViewHolder {
    @BindView(R.id.audioRight)
    AudioSenseiPlayerView audioRight;
    @BindView(R.id.imagetimeRight)
    TextView imagetimeRight;
    @BindView(R.id.remove_message)
    ImageView remove_message;
    @BindView(R.id.user_name_text_view)
    TextView user_name_text_view;
    @BindView(R.id.user_image_view)
    ImageView user_image_view;

    public RightAudioChat(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
