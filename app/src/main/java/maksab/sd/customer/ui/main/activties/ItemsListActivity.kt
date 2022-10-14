//package maksab.sd.customer.ui.main.activties
//
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import android.content.Intent
//import android.graphics.Color
//import android.os.Bundle
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import android.view.View
//import android.widget.AdapterView
//import android.widget.TextView
//import kotlinx.android.synthetic.main.activity_items.*
//import maksab.sd.customer.R
//import maksab.sd.customer.models.main.CategoriesModel
//import maksab.sd.customer.viewmodels.main.ItemsViewmodel
//import maksab.sd.customer.viewmodels.main.MainActivityViewModel
//import maksab.sd.customer.ui.providers.adapters.GalleriesListAdapter
//import maksab.sd.customer.models.providers.GalleryModel
//import maksab.sd.customer.viewmodels.providers.GalleriesViewModel
//import maksab.sd.customer.basecode.activities.BaseActivity
//import maksab.sd.customer.basecode.events.EndlessRecyclerViewScrollListener
//import maksab.sd.customer.basecode.adapters.SpinAdapter
//import maksab.sd.customer.basecode.utility.SpinnerDataMap
//import java.util.ArrayList
//
//@Deprecated("very old class")
//class ItemsListActivity : BaseActivity() {
//
//    private var recyclerView: RecyclerView? = null
//    private var scrollListener: EndlessRecyclerViewScrollListener? = null
//    private var islastpage: Boolean? = false
//    private var galleryModels: ArrayList<GalleryModel>? = null
//    private var galleriesListAdapter: GalleriesListAdapter? = null
//    private var itemsViewmodel : ItemsViewmodel? = null
//    private var catid : Int? = null
//    private var specialtyid : Int? = null
//    private var spinnerAdapter: SpinAdapter? = null
//    private var spinnerData: MutableList<SpinnerDataMap>? = null
//    private var categoryviewmodel : MainActivityViewModel? = null
//    private var galleryviewmodel : GalleriesViewModel? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_items)
//        catid = intent.getIntExtra("catid" ,0)
//        specialtyid = intent.getIntExtra("specialty" ,0)
//        itemsViewmodel = ViewModelProviders.of(this).get(ItemsViewmodel::class.java)
//        categoryviewmodel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
//        galleryviewmodel = ViewModelProviders.of(this).get(GalleriesViewModel::class.java)
//        setupToolbar()
//        setupRecyclerview()
//        itemsObserver()
//        toolbarHidingLogic()
//
//    }
//
//    private fun toolbarHidingLogic() {
//        // if there specialty  fillter gallery by specialityid
//      if(specialtyid != 0){
//          categorySpinner.visibility = View.GONE
//          toolbar.title = intent.getStringExtra("specialtyName");
//          itemsViewmodel?.getGalleryItemsBySpecId(specialtyid!!, 1)
//          swipeRefresh.setOnRefreshListener {
//              resetPaging()
//          }
//
//      }else{
//          if (catid != 0) {
//              categorySpinner.visibility = View.GONE
//              toolbar.title = "جميع العناصر"
//              itemsViewmodel?.getGalleryItemsByCatId(catid!!, 1)
//              swipeRefresh.setOnRefreshListener {
//                  resetPaging()
//              }
//          } else {
//              initSpecailtySpinner()
//              categoryObserver()
//              categoryviewmodel?.getCategories()
//              swipeRefresh.setOnRefreshListener {
//                  resetPaging()
//              }
//          }
//      }
//    }
//
//    private fun itemsObserver(){
//        itemsViewmodel?.allGalleriesObserver()?.observe(this , Observer { items : List<GalleryModel>? ->
//            swipeRefresh.isRefreshing = false
//            main_progress.visibility = View.GONE
//            if(items !=null){
//
//                if(items.size < 20){
//                    islastpage = true
//                }
//
//                if(items.size > 0){
//                    galleryModels?.addAll(items)
//                    galleriesListAdapter?.notifyItemRangeInserted(galleriesListAdapter?.itemCount!! , galleryModels!!.size)
//                }else {
//                    placeholderlayout.visibility = View.VISIBLE
//                }
//
//            }
//        })
//    }
//
//    private fun categoryObserver(){
//        categoryviewmodel?.categoriesObservable()?.observe(this , Observer {data : List<CategoriesModel>? ->
//            if (data!= null){
//                spinnerData?.add(SpinnerDataMap(0, "الكل"))
//                for (cate in data){
//                   if (!(cate.id ==2 || cate.id == 9)){
//                       spinnerData?.add(SpinnerDataMap(cate.id, cate.arabicName))
//                   }
//                }
//                spinnerAdapter?.notifyDataSetChanged()
//            }
//        })
//    }
//
//    private fun setupToolbar() {
//        toolbar.title = ""
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//    }
//
//    private fun setupRecyclerview() {
//        recyclerView = recyclerview
//        var layoutManager = GridLayoutManager(this, 2)
//        scrollListener =object : EndlessRecyclerViewScrollListener(layoutManager) {
//            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
//                if (!islastpage!!) {
//                    if(specialtyid != 0){
//                        itemsViewmodel?.getGalleryItemsBySpecId( specialtyid!! , page)
//                    }else{
//                        if(catid !=0){
//                            itemsViewmodel?.getGalleryItemsByCatId( catid!! , page)
//                        }else {
//                            itemsViewmodel?.getGalleryItemsByCatId( spinnerData?.get(categorySpinner.selectedItemPosition)!!.id , page)
//                        }
//                    }
//                }
//            }
//        }
//        recyclerView?.addOnScrollListener(scrollListener!!)
//        galleryModels = ArrayList()
//        galleriesListAdapter = GalleriesListAdapter(this, galleryModels) { view1 ->
//            onGalleryItemClicked(view1)
//        }
//        recyclerView?.layoutManager=layoutManager
//        recyclerView?.adapter = galleriesListAdapter
//    }
//
//    private fun onGalleryItemClicked(view: View) {
//        val galleryModel = galleryModels?.get(recyclerView!!.getChildAdapterPosition(view))
//        val intent = Intent(this, GalleryItemDetailsActivity::class.java)
//        //increase vievcount for gallery item
//       galleryviewmodel?.viewCount(galleryModel?.id!!)
//        intent.putExtra("GalleryModel", galleryModel)
//        startActivityForResult(intent, 2)
//    }
//
//    private fun initSpecailtySpinner() {
//        spinnerData = ArrayList()
//        spinnerAdapter = SpinAdapter(this, spinnerData)
//        categorySpinner.setAdapter(spinnerAdapter)
//        categorySpinner.setOnItemSelectedListener(onSpinnerClick())
//    }
//
//    private fun onSpinnerClick(): AdapterView.OnItemSelectedListener {
//        return object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
//                //get the first item of spinner and request the first specialty providers
//                placeholderlayout.setVisibility(View.GONE)
//                main_progress.setVisibility(View.VISIBLE)
//                (adapterView.getChildAt(0).findViewById<View>(R.id.spinnerItem) as TextView).setTextColor(Color.WHITE)
//               resetPaging()
//            }
//
//            override fun onNothingSelected(adapterView: AdapterView<*>) {
//
//            }
//        }
//    }
//
//    private fun resetPaging(){
//        main_progress?.visibility = View.VISIBLE
//        islastpage =false
//        galleryModels?.clear()
//        galleriesListAdapter?.notifyDataSetChanged()
//        if(scrollListener!=null){
//            scrollListener?.resetState()
//        }
//       if(catid !=0){
//           itemsViewmodel?.getGalleryItemsByCatId(catid!!, 1)
//       }else {
//           itemsViewmodel?.getGalleryItemsBySpecId(specialtyid!!, 1)
//       }
//    }
//}
