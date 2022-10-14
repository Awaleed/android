package maksab.sd.customer.ui.chat.chats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.ui.media.viewer.MediaActivityOpener;
import maksab.sd.customer.util.constants.Enums;
import maksab.sd.customer.util.general.StringUtils;
import maksab.sd.customer.util.general.ViewFileUtil;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ChatViewModel> itemList;
    private Context context;
    private Enums.UserTypeEnum currentUserType;

    public ChatAdapter(List<ChatViewModel> itemList, Context context,
                       Enums.UserTypeEnum currentUserType) {
        this.itemList = itemList;
        this.context = context;
        this.currentUserType = currentUserType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == Enums.MSG_TYPE.MY_MESSAGE_TEXT.ordinal()) {
            View Row2 = inflater.inflate(R.layout.item_message_text_right, parent, false);
            return new RightTextCahtHolder(Row2);
        } else if (viewType == Enums.MSG_TYPE.OTHER_MESSAGE_TEXT.ordinal()) {
            View Row = inflater.inflate(R.layout.item_message_text_left, parent, false);
            return new LeftTextChatHolder(Row);
        } else if (viewType == Enums.MSG_TYPE.MY_MESSAGE_IMAGE.ordinal()) {
            View Row3 = inflater.inflate(R.layout.item_message_image_right, parent, false);
            RightImgChat imgchat = new RightImgChat(Row3);
            return imgchat;
        } else if (viewType == Enums.MSG_TYPE.OTHER_MESSAGE_IMAGE.ordinal()) {
            View Row4 = inflater.inflate(R.layout.item_message_image_left, parent, false);
            LeftImgChat leftImgChat = new LeftImgChat(Row4);
            return leftImgChat;
        } else if (viewType == Enums.MSG_TYPE.MY_MESSAGE_AUDIO.ordinal()) {
            View Row3 = inflater.inflate(R.layout.item_message_audio_right, parent, false);
            RightAudioChat imgchat = new RightAudioChat(Row3);
            return imgchat;
        } else if (viewType == Enums.MSG_TYPE.OTHER_MESSAGE_AUDIO.ordinal()) {
            View Row4 = inflater.inflate(R.layout.item_message_audio_left, parent, false);
            LeftAudioChat leftItem = new LeftAudioChat(Row4);
            return leftItem;
        } else if (viewType == Enums.MSG_TYPE.MY_MESSAGE_VIDEO.ordinal()) {
            View Row3 = inflater.inflate(R.layout.item_message_video_right, parent, false);
            RightVideoChat rightItem = new RightVideoChat(Row3);
            return rightItem;
        } else if (viewType == Enums.MSG_TYPE.OTHER_MESSAGE_VIDEO.ordinal()) {
            View Row4 = inflater.inflate(R.layout.item_message_video_left, parent, false);
            LeftVideoChat leftItem = new LeftVideoChat(Row4);
            return leftItem;
        } else if (viewType == Enums.MSG_TYPE.MY_MESSAGE_DOCUMENT.ordinal()) {
            View Row3 = inflater.inflate(R.layout.item_message_document_right, parent, false);
            RightDocumentChat rightItem = new RightDocumentChat(Row3);
            return rightItem;
        } else if (viewType == Enums.MSG_TYPE.OTHER_MESSAGE_DOCUMENT.ordinal()) {
            View Row4 = inflater.inflate(R.layout.item_message_document_left, parent, false);
            LeftDocumentChat leftItem = new LeftDocumentChat(Row4);
            return leftItem;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int holderType = viewHolder.getItemViewType();

        final ChatViewModel message = itemList.get(position);

        //DateUtil dateUtil = DateUtil.newInstance();
        //dateUtil.parse(message.getCreatedOn());
        String dateTime = message.getCreatedOnString();

        if (holderType == Enums.MSG_TYPE.MY_MESSAGE_TEXT.ordinal()) {
            RightTextCahtHolder holder = (RightTextCahtHolder) viewHolder;
            holder.textRight.setText(message.getBody());
            holder.timeRight.setText(dateTime);
            holder.user_name_text_view.setText(getFullName(message));
            Picasso.with(context)
                    .load(StringUtils.picassoPath(message.getUserProfileLogo()))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.user_image_view);
            holder.remove_message.setVisibility(View.GONE);
            holder.remove_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //removeMessageDialog(message);
                }
            });
        } else if (holderType == Enums.MSG_TYPE.OTHER_MESSAGE_TEXT.ordinal()) {
            LeftTextChatHolder holder = (LeftTextChatHolder) viewHolder;
            holder.textLeft.setText(message.getBody());
            holder.timeLeft.setText(dateTime);
            holder.user_name_text_view.setText(getFullName(message));
            Picasso.with(context)
                    .load(StringUtils.picassoPath(message.getUserProfileLogo()))
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.user_image_view);
            holder.remove_message.setVisibility(View.GONE);
        } else if (holderType == Enums.MSG_TYPE.MY_MESSAGE_IMAGE.ordinal()) {
            RightImgChat holder = (RightImgChat) viewHolder;
            holder.imagetimeRight.setText(dateTime);
            holder.user_name_text_view.setText(getFullName(message));

            Picasso.with(context)
                    .load(StringUtils.picassoPath(message.getUserProfileLogo()))
                    .placeholder(R.drawable.placeholder)
                    .into(holder.user_image_view);

            Picasso.with(context).load(message.getBody())
                    .resize(300, 300).centerCrop()
                    .into(holder.imageRight);

            holder.imageRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MediaActivityOpener.openViewActivity(context, message.getBody());
                }
            });

            holder.remove_message.setVisibility(View.GONE);
            holder.remove_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //removeMessageDialog(message);
                }
            });
        } else if (holderType == Enums.MSG_TYPE.OTHER_MESSAGE_IMAGE.ordinal()) {
            LeftImgChat holder = (LeftImgChat) viewHolder;
            holder.imagetimeLeft.setText(dateTime);
            holder.user_name_text_view.setText(getFullName(message));
            Picasso.with(context)
                    .load(StringUtils.picassoPath(message.getUserProfileLogo()))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.user_image_view);

            Picasso.with(context).load(message.getBody())
                    .resize(300, 300).centerCrop()
                    .into(holder.imageLeft);

            holder.imageLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MediaActivityOpener.openViewActivity(context, message.getBody());
                }
            });

            holder.remove_message.setVisibility(View.GONE);
        } else if (holderType == Enums.MSG_TYPE.MY_MESSAGE_AUDIO.ordinal()) {
            RightAudioChat holder = (RightAudioChat) viewHolder;
            holder.audioRight.setAudioTarget(message.getBody());
            holder.imagetimeRight.setText(dateTime);
            holder.user_name_text_view.setText(getFullName(message));
            Picasso.with(context)
                    .load(StringUtils.picassoPath(message.getUserProfileLogo()))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.user_image_view);
            holder.remove_message.setVisibility(View.GONE);
            holder.remove_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //removeMessageDialog(message);
                }
            });
        } else if (holderType == Enums.MSG_TYPE.OTHER_MESSAGE_AUDIO.ordinal()) {
            LeftAudioChat holder = (LeftAudioChat) viewHolder;
            holder.audioLeft.setAudioTarget(message.getBody());
            holder.user_name_text_view.setText(getFullName(message));
            Picasso.with(context)
                    .load(StringUtils.picassoPath(message.getUserProfileLogo()))
                    .placeholder(R.drawable.placeholder)
                    .into(holder.user_image_view);
            holder.imagetimeLeft.setText(dateTime);
            holder.remove_message.setVisibility(View.GONE);
        } else if (holderType == Enums.MSG_TYPE.MY_MESSAGE_DOCUMENT.ordinal()) {
            RightDocumentChat holder = (RightDocumentChat) viewHolder;
            holder.imagetimeRight.setText(dateTime);
            holder.user_name_text_view.setText(getFullName(message));

            Picasso.with(context)
                    .load(StringUtils.picassoPath(message.getUserProfileLogo()))
                    .placeholder(R.drawable.placeholder)
                    .into(holder.user_image_view);

            Picasso.with(context)
                    .load(ViewFileUtil.getFileIcon(message.getBody()))
                    .into(holder.imageRight);

            holder.imageRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewFileUtil.ViewFile(context, message.getBody());
                }
            });

            holder.remove_message.setVisibility(View.GONE);
        } else if (holderType == Enums.MSG_TYPE.OTHER_MESSAGE_DOCUMENT.ordinal()) {
            LeftDocumentChat holder = (LeftDocumentChat) viewHolder;
            holder.imagetimeLeft.setText(dateTime);
            holder.user_name_text_view.setText(getFullName(message));
            Picasso.with(context)
                    .load(StringUtils.picassoPath(message.getUserProfileLogo()))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.user_image_view);

            Picasso.with(context)
                    .load(ViewFileUtil.getFileIcon(message.getBody()))
                    .into(holder.imageLeft);

            holder.imageLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewFileUtil.ViewFile(context, message.getBody());
                }
            });

            holder.remove_message.setVisibility(View.GONE);
        } else if (holderType == Enums.MSG_TYPE.MY_MESSAGE_VIDEO.ordinal()) {
            RightVideoChat holder = (RightVideoChat) viewHolder;
            holder.imagetimeRight.setText(dateTime);
            holder.user_name_text_view.setText(getFullName(message));

            Picasso.with(context)
                    .load(StringUtils.picassoPath(message.getUserProfileLogo()))
                    .placeholder(R.drawable.placeholder)
                    .into(holder.user_image_view);

            Picasso.with(context)
                    .load(message.getAdditionalInfo())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.video_view);

            holder.video_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MediaActivityOpener.openViewActivity(context, message.getBody());
                }
            });

            holder.remove_message.setVisibility(View.GONE);
        } else if (holderType == Enums.MSG_TYPE.OTHER_MESSAGE_VIDEO.ordinal()) {
            LeftVideoChat holder = (LeftVideoChat) viewHolder;
            holder.imagetimeLeft.setText(dateTime);
            holder.user_name_text_view.setText(getFullName(message));
            Picasso.with(context)
                    .load(StringUtils.picassoPath(message.getUserProfileLogo()))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.user_image_view);

            Picasso.with(context)
                    .load(message.getAdditionalInfo())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.video_view);

            holder.video_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MediaActivityOpener.openViewActivity(context, message.getBody());
                }
            });

            holder.remove_message.setVisibility(View.GONE);
        }
    }

    private String getFullName(ChatViewModel message) {
        return message.getUserFullName() + " - " +
                Enums.getUserTypeEnum(message.getUserTypeEnum());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatViewModel messageViewModel = itemList.get(position);

        boolean isUserSide = currentUserType.ordinal() == messageViewModel.getUserTypeEnum();

        if (isUserSide) {
            if (messageViewModel.getMessageType() == Enums.MessageTypeEnum.Text.ordinal())
                return Enums.MSG_TYPE.MY_MESSAGE_TEXT.ordinal();

            if (messageViewModel.getMessageType() == Enums.MessageTypeEnum.Image.ordinal())
                return Enums.MSG_TYPE.MY_MESSAGE_IMAGE.ordinal();

            if (messageViewModel.getMessageType() == Enums.MessageTypeEnum.Voice.ordinal())
                return Enums.MSG_TYPE.MY_MESSAGE_AUDIO.ordinal();

            if (messageViewModel.getMessageType() == Enums.MessageTypeEnum.Video.ordinal())
                return Enums.MSG_TYPE.MY_MESSAGE_VIDEO.ordinal();

            if (messageViewModel.getMessageType() == Enums.MessageTypeEnum.Document.ordinal())
                return Enums.MSG_TYPE.MY_MESSAGE_DOCUMENT.ordinal();

            return Enums.MSG_TYPE.MY_MESSAGE_TEXT.ordinal();
        } else {
            if (messageViewModel.getMessageType() == Enums.MessageTypeEnum.Text.ordinal())
                return Enums.MSG_TYPE.OTHER_MESSAGE_TEXT.ordinal();

            if (messageViewModel.getMessageType() == Enums.MessageTypeEnum.Image.ordinal())
                return Enums.MSG_TYPE.OTHER_MESSAGE_IMAGE.ordinal();

            if (messageViewModel.getMessageType() == Enums.MessageTypeEnum.Voice.ordinal())
                return Enums.MSG_TYPE.OTHER_MESSAGE_AUDIO.ordinal();

            if (messageViewModel.getMessageType() == Enums.MessageTypeEnum.Video.ordinal())
                return Enums.MSG_TYPE.OTHER_MESSAGE_VIDEO.ordinal();

            if (messageViewModel.getMessageType() == Enums.MessageTypeEnum.Document.ordinal())
                return Enums.MSG_TYPE.OTHER_MESSAGE_DOCUMENT.ordinal();

            return Enums.MSG_TYPE.OTHER_MESSAGE_TEXT.ordinal();
        }
    }
}