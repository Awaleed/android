package maksab.sd.customer.ui.main.adapters;

import static android.view.View.LAYER_TYPE_HARDWARE;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.home.HomeCategoryModel;
import maksab.sd.customer.util.constants.Enums;

public class VerticalCategoriesAdapter extends RecyclerView.Adapter<VerticalCategoriesAdapter.VerticalCategoriesViewHolder> {
    private View.OnClickListener onClickListener;
    private List<HomeCategoryModel> categoriesModels;

    public VerticalCategoriesAdapter(View.OnClickListener onClickListener, List<HomeCategoryModel> categoriesModels) {
        this.onClickListener = onClickListener;
        this.categoriesModels = categoriesModels;
    }

    @NonNull
    @Override
    public VerticalCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VerticalCategoriesViewHolder viewHolder = new VerticalCategoriesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical_categories, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalCategoriesViewHolder holder, int position) {
        HomeCategoryModel categoriesModel = categoriesModels.get(position);
        Picasso.with(holder.category_image.getContext()).load(categoriesModel.getImagePath()).into(holder.category_image);
        holder.category_name.setText(categoriesModel.getArabicName());
        holder.number_of_services.setText(categoriesModel.getDescription());

        holder.itemView.setOnClickListener(onClickListener);

        if (categoriesModel.getCategoryStatus() == Enums.CategoryStatusEnum.Active.ordinal()) {
            holder.status_text_view.setVisibility(View.GONE);
            holder.category_name.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
            holder.number_of_services.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
            holder.category_image.setLayerType(LAYER_TYPE_HARDWARE, null);
        } else {
            holder.status_text_view.setVisibility(View.VISIBLE);

            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            Paint greyscalePaint = new Paint();
            greyscalePaint.setColorFilter(filter);

            holder.category_image.setLayerType(LAYER_TYPE_HARDWARE, greyscalePaint);
            holder.category_name.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.light_grey));
            holder.number_of_services.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.light_grey));
        }
    }

    @Override
    public int getItemCount() {
        return categoriesModels.size();
    }

    public class VerticalCategoriesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category_image)
        ImageView category_image;
        @BindView(R.id.category_name)
        TextView category_name;
        @BindView(R.id.number_of_services)
        TextView number_of_services;
        @BindView(R.id.status_text_view)
        TextView status_text_view;

        public VerticalCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
