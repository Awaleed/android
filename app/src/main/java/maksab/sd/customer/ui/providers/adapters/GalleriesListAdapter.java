package maksab.sd.customer.ui.providers.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.providers.GalleryModel;
import maksab.sd.customer.ui.media.viewer.MediaActivityOpener;


/**
 * Created by Dev on 2/21/2018.
 */


public class GalleriesListAdapter extends RecyclerView.Adapter<GalleriesListAdapter.GalleriesListViewHolder>  {
    private View.OnClickListener onCardclicked;
    private List<GalleryModel> galleryModel;
    private Context context;

    public GalleriesListAdapter(Context context,
                List<GalleryModel> galleryModel,
                View.OnClickListener onCardclicked) {
        this.onCardclicked = onCardclicked;
        this.context = context;
        this.galleryModel = galleryModel;
    }

    @Override
    public GalleriesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GalleriesListViewHolder galleriesListViewHolder = new GalleriesListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery, parent, false));
        galleriesListViewHolder.itemView.setOnClickListener(onCardclicked);
        return galleriesListViewHolder;
    }

    @Override
    public void onBindViewHolder(GalleriesListViewHolder holder, int position) {
        GalleryModel itemModel = this.galleryModel.get(position);

        holder.specialty_name.setText(itemModel.getSpecialtyName());
        holder.gallery_count_textview.setText(itemModel.getViewCount() + "");

        Picasso.with(context).load(itemModel.getImageThumbPath())
                .placeholder(R.drawable.placeholder)
                .into(holder.gallery_item);

        String path = itemModel.getImagePath();

        if (MediaActivityOpener.isVideo(path)) {
            Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_baseline_videocam_24);
            holder.image_type_imageview.setImageDrawable(background);
        }
        else {
            Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_baseline_image_24);
            holder.image_type_imageview.setImageDrawable(background);
        }
    }

    @Override
    public int getItemCount() {
        return galleryModel.size();
    }

    class GalleriesListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.specialty_name)
        TextView specialty_name;
        @BindView(R.id.gallery_item)
        ImageView gallery_item;
        @BindView(R.id.image_type_imageview)
        ImageView image_type_imageview;
        @BindView(R.id.gallery_count_textview)
        TextView gallery_count_textview;

        public GalleriesListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

