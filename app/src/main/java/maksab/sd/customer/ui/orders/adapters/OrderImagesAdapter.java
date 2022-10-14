package maksab.sd.customer.ui.orders.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import maksab.sd.customer.R;
import maksab.sd.customer.models.orders.details.OrderImageViewModel;


public class OrderImagesAdapter extends RecyclerView.Adapter<OrderImagesAdapter.OrderImagesViewHolder>  {

    private List<OrderImageViewModel> selectedImages;
    private View.OnClickListener listener;

    public OrderImagesAdapter(List<OrderImageViewModel> selectedImages, View.OnClickListener listener) {
        this.selectedImages = selectedImages;
        this.listener = listener;
    }

    @Override
    public OrderImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderImagesViewHolder selectedImagesViewHolder = new OrderImagesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_image, parent, false));
        selectedImagesViewHolder.itemView.setOnClickListener(listener);
        return selectedImagesViewHolder;
    }

    @Override
    public void onBindViewHolder(OrderImagesViewHolder holder, int position) {
        OrderImageViewModel image = selectedImages.get(position);
        if (!TextUtils.isEmpty(image.getImagePath())) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
            try {
                Picasso.with(holder.selected_image.getContext()).load(image.getImagePath()).placeholder(ResourcesCompat.getDrawable(holder.selected_image.getResources(), R.drawable.ic_picture, null)).into(holder.selected_image);

            } catch (Exception ex) {
            }
        }
    }

    @Override
    public int getItemCount() {
        return selectedImages.size();
    }

    class OrderImagesViewHolder extends RecyclerView.ViewHolder {
        ImageView selected_image;
        public OrderImagesViewHolder(View itemView) {
            super(itemView);
            selected_image = itemView.findViewById(R.id.selected_image);
        }
    }
}


