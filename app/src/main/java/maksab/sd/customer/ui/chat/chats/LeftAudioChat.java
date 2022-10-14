package maksab.sd.customer.ui.chat.chats;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rygelouv.audiosensei.player.AudioSenseiPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;

public class LeftAudioChat extends RecyclerView.ViewHolder {
    @BindView(R.id.audioLeft)
    AudioSenseiPlayerView audioLeft;
    @BindView(R.id.imagetimeLeft)
    TextView imagetimeLeft;
    @BindView(R.id.remove_message)
    ImageView remove_message;
    @BindView(R.id.user_name_text_view)
    TextView user_name_text_view;
    @BindView(R.id.user_image_view)
    ImageView user_image_view;

    public LeftAudioChat(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
