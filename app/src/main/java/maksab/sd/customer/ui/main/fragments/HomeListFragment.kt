//package maksab.sd.customer.ui.main.fragments
//
//import android.Manifest
//import android.app.ProgressDialog
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.location.LocationManager
//import android.os.Bundle
//import android.provider.Settings
//import androidx.fragment.app.Fragment
//import androidx.core.content.ContextCompat
//import androidx.appcompat.app.AlertDialog
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import kotlinx.android.synthetic.main.fragment_home.*
//import maksab.sd.customer.R
//import maksab.sd.customer.main.adapters.CategoriesAdapter
//import maksab.sd.customer.ui.main.adapters.ItemsAdapter
//import maksab.sd.customer.models.main.CategoriesModel
//import maksab.sd.customer.viewmodels.main.MainActivityViewModel
//import maksab.sd.customer.models.providers.GalleryModel
//import maksab.sd.customer.ui.main.activties.ItemsListActivity
//import maksab.sd.customer.ui.main.activties.SelectSpecialtyActivity
//import maksab.sd.customer.ui.providers.activties.GalleryItemDetailsActivity
//import java.util.ArrayList
//
//@Deprecated("very old class")
//class HomeListFragment : Fragment() {
//
//    private var dialog: ProgressDialog? = null
//
//    private var category_recyclerview: RecyclerView? = null
//    private var categoriesAdapter: CategoriesAdapter? = null
//    private var categoriesModels: ArrayList<CategoriesModel>? = null
//
//    private var kitchen_recyclerview: RecyclerView? = null
//    private var kitchen_itemsadapter: ItemsAdapter? = null
//    private var kitchenModels: ArrayList<GalleryModel>? = null
//
//    private var building_recyclerview: RecyclerView? = null
//    private var building_itemsadapter: ItemsAdapter? = null
//    private var buildingModels: ArrayList<GalleryModel>? = null
//
//    private var all_recyclerview: RecyclerView? = null
//    private var all_itemsadapter: ItemsAdapter? = null
//    private var allModels: ArrayList<GalleryModel>? = null
//
//    private var mainActivityViewModel: MainActivityViewModel? = null
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        var view = inflater.inflate(R.layout.fragment_home, container, false)
//        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
//        categoriesObserver()
//        kitchenPublicGalleryObserver()
//        buildingPublicGalleryObserver()
//        allgPublicGalleryObserver()
//        setupRecyclerViewForCategory(view)
//        setupRecyclerViewForkitchen(view)
//        setupRecyclerViewForBuilding(view)
//        setupRecyclerViewForall(view)
//        showWaitDialog()
//        mainActivityViewModel?.getCategories()
//        mainActivityViewModel?.getPublicGallery(9)
//        mainActivityViewModel?.getPublicGallery(2)
//        mainActivityViewModel?.getPublicGallery(0)
//        return view;
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        buttonsEvents()
//    }
//
//    private fun categoriesObserver() {
//        mainActivityViewModel?.categoriesObservable()?.observe(this, Observer {
//            if (it != null) {
//                dismissWaitDialog()
//                categoriesModels?.addAll(it)
//                categoriesAdapter?.notifyDataSetChanged()
//            } else {
//                dismissWaitDialog()
//                noInternetAvailable()
//            }
//        })
//    }
//
//    private fun dismissWaitDialog(){
//        if (dialog != null) {
//            dialog?.dismiss()
//        }
//    }
//
//    private fun showWaitDialog(){
//        dialog = ProgressDialog.show(activity, "",
//                "تحميل. الرجاء الانتظار", true)
//    }
//
//    private fun kitchenPublicGalleryObserver() {
//        mainActivityViewModel?.kitchenpPublicGalleryObserver()?.observe(this, Observer {
//            kitchen_progress.visibility = View.GONE
//            kitchen_recyclerview?.visibility = View.VISIBLE
//            if (it != null) {
//                //  dismissWaitDialog()
//                kitchenModels?.addAll(it)
//                kitchen_itemsadapter?.notifyDataSetChanged()
//            } else {
//                // dismissWaitDialog()
//                noInternetAvailable()
//            }
//        })
//    }
//
//    private fun buildingPublicGalleryObserver() {
//        mainActivityViewModel?.buildingPublicGalleryObserver()?.observe(this, Observer {
//            building_progress.visibility = View.GONE
//            building_recyclerview?.visibility = View.VISIBLE
//            if (it != null) {
//                //  dismissWaitDialog()
//                buildingModels?.addAll(it)
//                building_itemsadapter?.notifyDataSetChanged()
//            } else {
//                // dismissWaitDialog()
//                noInternetAvailable()
//            }
//        })
//    }
//
//    private fun allgPublicGalleryObserver() {
//        mainActivityViewModel?.allgPublicGalleryObserver()?.observe(this, Observer {
//            all_progress.visibility = View.GONE
//            all_recyclerview?.visibility = View.VISIBLE
//            if (it != null) {
//                //  dismissWaitDialog()
//                allModels?.addAll(it)
//                all_itemsadapter?.notifyDataSetChanged()
//            } else {
//                // dismissWaitDialog()
//                noInternetAvailable()
//            }
//        })
//    }
//
//    private fun setupRecyclerViewForCategory(view: View) {
//        category_recyclerview = view.findViewById(R.id.catrecylerview)
//        category_recyclerview?.setHasFixedSize(true)
//        var layoutmanger = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        categoriesModels = ArrayList()
//        categoriesAdapter = CategoriesAdapter(categoriesModels!! , View.OnClickListener {
//            openProviderListScreen(it)
//        }, activity!!)
//        category_recyclerview?.layoutManager = layoutmanger
//        category_recyclerview?.adapter = categoriesAdapter
//    }
//
//    private fun setupRecyclerViewForkitchen(view: View) {
//        kitchen_recyclerview = view.findViewById(R.id.kitchen_recyclerview)
//        kitchen_recyclerview?.setHasFixedSize(true)
//        var layoutmanger2 = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        kitchenModels = ArrayList()
//
//        kitchen_itemsadapter = ItemsAdapter(kitchenModels!!, View.OnClickListener {
//            onGalleryItemClicked(it, kitchenModels!!, kitchen_recyclerview!!)
//        })
//        kitchen_recyclerview?.layoutManager = layoutmanger2
//        kitchen_recyclerview?.adapter = kitchen_itemsadapter
//    }
//
//    private fun setupRecyclerViewForBuilding(view: View) {
//        building_recyclerview = view.findViewById(R.id.building_recyclerview)
//        building_recyclerview?.setHasFixedSize(true)
//        var layoutmanger2 = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        buildingModels = ArrayList()
//
//        building_itemsadapter = ItemsAdapter(buildingModels!!, View.OnClickListener {
//            onGalleryItemClicked(it, buildingModels!!, building_recyclerview!!)
//        })
//        building_recyclerview?.layoutManager = layoutmanger2
//        building_recyclerview?.adapter = building_itemsadapter
//    }
//
//    private fun setupRecyclerViewForall(view: View) {
//        all_recyclerview = view.findViewById(R.id.all_recyclerview)
//        all_recyclerview?.setHasFixedSize(true)
//        var layoutmanger2 = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        allModels = ArrayList()
//
//        all_itemsadapter = ItemsAdapter(allModels!!, View.OnClickListener {
//            onGalleryItemClicked(it, allModels!!, all_recyclerview!!)
//        })
//        all_recyclerview?.layoutManager = layoutmanger2
//        all_recyclerview?.adapter = all_itemsadapter
//    }
//    private fun openProviderListScreen(view: View) {
//        var  intent  =  Intent(activity , SelectSpecialtyActivity::class.java)
//
//        var catid =  categoriesModels?.get(category_recyclerview?.getChildAdapterPosition(view)!!)?.id
//        intent.putExtra("catid", catid);
//        startActivity(intent);
//
//        /*   if (isAppHasPermission()) {
//               if (isGpsEnabled()) {
//                   val intent = Intent(this@MainActivity, ProviderListActivity::class.java)
//                   val catid = categoriesModels?.get(category_recyclerview!!.getChildAdapterPosition(view))?.id
//                   intent.putExtra("catid", catid)
//                   startActivity(intent)
//               } else {
//                   turnOnLoaction()
//               }
//           } else {
//               Toast.makeText(this, R.string.no_location_per, Toast.LENGTH_LONG).show()
//               checkPermission()
//           }*/
//
//    }
//
//    private fun turnOnLoaction() {
//        val builder = AlertDialog.Builder(activity!!)
//        builder.setMessage(R.string.enableGps)
//                .setCancelable(false)
//                .setPositiveButton(R.string.ok) { dialog, id ->
//                    val viewIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                    startActivity(viewIntent)
//
//                }
//        val alert = builder.create()
//        alert.show()
//    }
//
//    private fun noInternetAvailable() {
//        val builder = AlertDialog.Builder(activity!!)
//        builder.setMessage(R.string.NoInternet)
//                .setCancelable(false).setNegativeButton(R.string.no_close) { dialogInterface, i -> activity?.finish() }
//                .setPositiveButton(R.string.iconnect_theInternet) { dialog, id ->
//                    showWaitDialog()
//                    mainActivityViewModel?.getCategories()
//                }.setIcon(R.drawable.ic_cloud_off)
//        val alert = builder.create()
//        alert.show()
//    }
//
//    private fun isGpsEnabled(): Boolean {
//        val manager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//    }
//
//    private fun isAppHasPermission(): Boolean {
//        return  return ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//    }
//
//    private fun onGalleryItemClicked(view: View, galleryModels : List<GalleryModel>, recyclerView : RecyclerView) {
//        val galleryModel = galleryModels.get(recyclerView.getChildAdapterPosition(view))
//        val intent = Intent(activity, GalleryItemDetailsActivity::class.java)
//        //increase vievcount for gallery item
//        //  galleriesViewModel.viewCount(galleryModel.getId()!!)
//        intent.putExtra("GalleryModel", galleryModel)
//        startActivity(intent)
//    }
//
//    private fun buttonsEvents(){
//        seeall_kitchen.setOnClickListener {
//            var intent = Intent(activity , ItemsListActivity::class.java)
//            intent.putExtra("catid" , 9)
//            startActivity(intent)
//        }
//
//        seeall_building.setOnClickListener {
//            var intent = Intent(activity , ItemsListActivity::class.java)
//            intent.putExtra("catid" , 2)
//            startActivity(intent)
//        }
//        seeall_random.setOnClickListener {
//            var intent = Intent(activity , ItemsListActivity::class.java)
//            intent.putExtra("catid" , 0)
//            startActivity(intent)
//        }
//    }
//
//    companion object {
//        @JvmStatic
//        fun newInstance() =
//                HomeListFragment().apply {
//                    arguments = Bundle().apply {
//                     //set ur arguments here
//                    }
//                }
//    }
//}
