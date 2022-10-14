package maksab.sd.customer.ui.providers.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maksab.sd.customer.R;
import maksab.sd.customer.basecode.events.EndlessRecyclerViewScrollListener;
import maksab.sd.customer.models.providers.GalleryModel;
import maksab.sd.customer.ui.media.viewer.MediaActivityOpener;
import maksab.sd.customer.ui.providers.adapters.GalleriesListAdapter;
import maksab.sd.customer.viewmodels.providers.GalleriesViewModel;

public class GalleryItemsListFragment extends Fragment {

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.no_data_layout)
    ViewGroup no_data_layout;
    @BindView(R.id.main_progress)
    ProgressBar main_progressbar;

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private GalleriesListAdapter galleriesListAdapter;
    private List<GalleryModel> galleryModels;
    private GalleriesViewModel galleriesViewModel;
    private boolean islastpage = false;
    private String userid;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this , view);
        userid = getArguments().getString("userid");
        galleriesViewModel = ViewModelProviders.of(this).get(GalleriesViewModel.class);
        galleriesObserver();
        initView(view);
        return view;
    }

    public static GalleryItemsListFragment newInstance( String userid){
        GalleryItemsListFragment galleryItemsListFragment = new GalleryItemsListFragment();
        Bundle args = new Bundle();
        args.putString("userid" , userid);
        galleryItemsListFragment.setArguments(args);
        return galleryItemsListFragment;
    }

    private void initView(View view) {
        setUpRecyclerview(view);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            no_data_layout.setVisibility(View.GONE);
            resetPaging();
        });
        main_progressbar.setVisibility(View.VISIBLE);
        galleriesViewModel.getGalleries(1 ,userid);
    }

    void setUpRecyclerview(View view) {
        recyclerView = view.findViewById(R.id.jobrecyclerview);
        layoutManager = new GridLayoutManager(getContext() , 2);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (!islastpage){
                    galleriesViewModel.getGalleries(page , userid);
                }
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        galleryModels = new ArrayList<>();

        galleriesListAdapter = new GalleriesListAdapter(getActivity(), galleryModels, view1 -> {
            GalleryModel galleryModel = galleryModels.get(recyclerView.getChildAdapterPosition(view1));
            galleriesViewModel.viewCount(galleryModel.getId());
            MediaActivityOpener.openViewActivity(getActivity(), galleryModel.getImagePath());
        });

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(galleriesListAdapter);
    }

    private void resetPaging() {
        islastpage = false;
        galleryModels.clear();
        galleriesListAdapter.notifyDataSetChanged();
        if (scrollListener != null) {
            scrollListener.resetState();
        }
        galleriesViewModel.getGalleries(1 , userid);
    }

    private void galleriesObserver() {
        galleriesViewModel.galleriesObservable().observe(getViewLifecycleOwner() , galleryModels1 -> {
            swipeRefreshLayout.setRefreshing(false);
            main_progressbar.setVisibility(View.GONE);
            if (galleryModels1 !=null){
                if (galleryModels1.size() < 20){
                    islastpage = true;
                }

                if (galleryModels1.size() >0){
                    no_data_layout.setVisibility(View.GONE);
                    galleryModels.addAll(galleryModels1);
                    galleriesListAdapter.notifyItemRangeInserted(galleriesListAdapter.getItemCount(), galleryModels.size());
                }else  {
                    no_data_layout.setVisibility(View.VISIBLE);
                }

            }
        });
    }

}
