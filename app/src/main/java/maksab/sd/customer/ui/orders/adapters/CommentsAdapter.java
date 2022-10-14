package maksab.sd.customer.ui.orders.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.orders.details.CommentModel;
import maksab.sd.customer.ui.media.viewer.MediaActivityOpener;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.TextCommentViewHolder> {

    private List<CommentModel> commentModels;
    private Context _context;

    public CommentsAdapter(List<CommentModel> commentModels , Context context){
        this.commentModels = commentModels;
        this._context = context;
    }

    @NonNull
    @Override
    public TextCommentViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new TextCommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.TextCommentViewHolder holder, int position) {
        CommentModel commentModel = commentModels.get(position);

        if(commentModel.isCustomer()){
            holder.list_item_comment_fullname.setText(R.string.you_username);
            holder.list_item_comment_fullname.setTextColor(_context.getResources().getColor(R.color.colorPrimary));
        }else {
            holder.list_item_comment_fullname.setText(commentModel.getUserFullName());
            holder.list_item_comment_fullname.setTextColor(_context.getResources().getColor(R.color.colorAccent));
        }
        holder.list_item_createdon.setText(commentModel.getCreatedOnString());
        Picasso.with(holder.list_item_comment_userimage.getContext()).load(commentModel.getUserImage()).placeholder(R.drawable.placeholder).into(holder.list_item_comment_userimage);
        if(commentModel.isMedia()){
            holder.comment_image.setVisibility( View.VISIBLE);
            holder.list_item_comment_body.setVisibility( View.GONE);
            Picasso.with(holder.comment_image.getContext()).load(commentModel.getBody())
                    .placeholder(R.drawable.logo_gray).into(holder.comment_image);
            holder.comment_image.setOnClickListener(view -> {
                MediaActivityOpener.openViewActivity(_context, commentModel.getBody());
            });
        }else{
            holder.comment_image.setVisibility(View.GONE);
            holder.list_item_comment_body.setVisibility(View.VISIBLE);
            holder.list_item_comment_body.setText(commentModel.getBody());
        }
    }

    @Override
    public int getItemCount() {
        return commentModels.size();
    }


    class TextCommentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_item_comment_userimage)
        ImageView list_item_comment_userimage;

        @BindView(R.id.comment_image)
        ImageView comment_image;

        @BindView(R.id.list_item_comment_fullname)
        TextView list_item_comment_fullname;

        @BindView(R.id.list_item_comment_body)
        TextView list_item_comment_body;

        @BindView(R.id.list_item_createdon)
        TextView list_item_createdon;

        public TextCommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
