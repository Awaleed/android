package maksab.sd.customer.ui.sections.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.models.categories.CardBasedCategoriesModel;


public class GridBasedSectionsFragments extends Fragment {


    private GridLayoutManager layoutManager;
    private GridBasedSectionsAdapter _gridGridBasedSectionsAdapter;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.placeholderlayout)
    ViewGroup placeholderlayout;

    @BindView(R.id.main_progress)
    ProgressBar progressBar;

    private List<CardBasedCategoriesModel> _cateCardBasedCategoriesModels;

    public static GridBasedSectionsFragments newInstance(int categoryId , int showType) {
        GridBasedSectionsFragments fragment = new GridBasedSectionsFragments();
        Bundle args = new Bundle();
        args.putInt("category_id" , categoryId);
        args.putInt("show_type" , showType);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid_based_categories_fragments, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        _cateCardBasedCategoriesModels = new ArrayList<>();
        ButterKnife.bind(this , view);
        setupRecyclerView();
    }


    private void setupRecyclerView(){
        recyclerview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view); // item position
                int spanCount = 3;
                int spacing = 10;//spacing between views in grid

                if (position >= 0) {
                    int column = position % spanCount; // item column

                    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                    if (position < spanCount) { // top edge
                        outRect.top = spacing;
                    }
                    outRect.bottom = spacing; // item bottom
                } else {
                    outRect.left = 0;
                    outRect.right = 0;
                    outRect.top = 0;
                    outRect.bottom = 0;
                }
            }
        });

        _gridGridBasedSectionsAdapter = new GridBasedSectionsAdapter(_cateCardBasedCategoriesModels , view -> {});
        layoutManager = new GridLayoutManager(getContext() , 3);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(_gridGridBasedSectionsAdapter);
        _gridGridBasedSectionsAdapter.notifyDataSetChanged();
    }



    class GridBasedSectionsAdapter extends RecyclerView.Adapter<GridBasedCategoriesViewHolder> {

        private View.OnClickListener _onClickListener;
        private List<CardBasedCategoriesModel> _cardBasedCategoriesModels;

        GridBasedSectionsAdapter(List<CardBasedCategoriesModel> cardBasedCategoriesModels , View.OnClickListener onClickListener){
            _onClickListener = onClickListener;
            _cardBasedCategoriesModels = cardBasedCategoriesModels;
        }

        @NonNull
        @Override
        public GridBasedSectionsFragments.GridBasedCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            GridBasedSectionsFragments.GridBasedCategoriesViewHolder gridBasedCategoriesViewHolder = new GridBasedSectionsFragments.GridBasedCategoriesViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_grid_based_categories,
                    parent , false));
            gridBasedCategoriesViewHolder.itemView.setOnClickListener(_onClickListener);
            return gridBasedCategoriesViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull GridBasedSectionsFragments.GridBasedCategoriesViewHolder holder, int position) {
            CardBasedCategoriesModel model = _cardBasedCategoriesModels.get(position);
            holder.label.setText(model.getLabel());
            Picasso.with(holder.item_image.getContext()).load(model.getImageUrl()).placeholder(ResourcesCompat.getDrawable(holder.item_image.getResources(),
                    R.drawable.placeholder , null)).into(holder.item_image);
        }

        @Override
        public int getItemCount() {
            return _cardBasedCategoriesModels.size();
        }
    }

    class GridBasedCategoriesViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.label)
        TextView label;
        @BindView(R.id.item_image)
        ImageView item_image;

        public GridBasedCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}