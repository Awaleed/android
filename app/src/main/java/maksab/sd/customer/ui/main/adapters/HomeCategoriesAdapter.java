package maksab.sd.customer.ui.main.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.home.HomeCategoryModel;

public class HomeCategoriesAdapter extends RecyclerView.Adapter<HomeCategoriesAdapter.HomeCategoriesViewHolder> {

    private List<HomeCategoryModel> homeCategoryModels;
    private View.OnClickListener onClickListener;
    private List<Integer> categoriesImages = new ArrayList<>();

    public HomeCategoriesAdapter(List<HomeCategoryModel> homeCategoryModels, View.OnClickListener onClickListener){
        this.homeCategoryModels = homeCategoryModels;
        this.onClickListener = onClickListener;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        fillCategoriesImages();
    }

    private void fillCategoriesImages(){
        categoriesImages.add(0);
        categoriesImages.add(R.drawable.ic_maintenance);
        categoriesImages.add(R.drawable.ic_building);
        categoriesImages.add(R.drawable.ic_cleaning);
        categoriesImages.add(R.drawable.ic_beauty);
        categoriesImages.add(R.drawable.ic_event);
        categoriesImages.add(R.drawable.ic_presentation);
        categoriesImages.add(R.drawable.ic_coin);
        categoriesImages.add(R.drawable.ic_art);
        categoriesImages.add(R.drawable.ic_food);
        categoriesImages.add(R.drawable.ic_delivery_truck);
    }

    @NonNull
    @Override
    public HomeCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomeCategoriesViewHolder homeCategoriesViewHolder = new HomeCategoriesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_categories_row ,
                parent, false));
        homeCategoriesViewHolder.itemView.setOnClickListener(onClickListener);

        return homeCategoriesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoriesViewHolder holder, int position) {
        HomeCategoryModel homeCategoryModel = homeCategoryModels.get(position);
        holder.category_name.setText(homeCategoryModel.getArabicName());
        holder.category_image.setImageDrawable(VectorDrawableCompat.create(holder.category_image.getContext().getResources() ,
                categoriesImages.get(homeCategoryModel.getId()) ,null));
    }

    @Override
    public int getItemCount() {
        return homeCategoryModels.size();
    }

    class HomeCategoriesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.catName)
        TextView category_name;

        @BindView(R.id.catImage)
        ImageView category_image;

        public HomeCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
