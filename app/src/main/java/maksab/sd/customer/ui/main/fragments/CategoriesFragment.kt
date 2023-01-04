package maksab.sd.customer.ui.main.fragments


import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import maksab.sd.customer.R
import maksab.sd.customer.main.adapters.CategoriesAdapter
import maksab.sd.customer.models.main.CategoriesModel
import maksab.sd.customer.ui.main.activties.SelectSpecialtyActivity
import maksab.sd.customer.ui.sections.activities.SectionsHomeLayoutActivity
import maksab.sd.customer.util.general.WhatsappUtil
import maksab.sd.customer.viewmodels.main.MainActivityViewModel

class CategoriesFragment : Fragment() {

    private var category_recyclerview: RecyclerView? = null
    private var categoriesAdapter: CategoriesAdapter? = null
    private var categoriesModels: ArrayList<CategoriesModel>? = null
    private var mainActivityViewModel: MainActivityViewModel? = null
    private var dialog: ProgressDialog? = null
    private var blocked_ui: ViewGroup? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_categories, container, false)
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        categoriesObserver()
        setupRecyclerViewForCategory(view)
        showWaitDialog()
        mainActivityViewModel?.getCategories()
        initViews(view)
        checkUserStatus()
        return view;
    }

    private fun initViews(view: View) {
        var sendtosupport = view.findViewById<Button>(R.id.send_support)
        blocked_ui = view.findViewById(R.id.blocked_ui)
        sendtosupport.setOnClickListener {
            WhatsappUtil().openWhatsApp("0113555535", context)
        }
    }

    private fun categoriesObserver() {
        mainActivityViewModel?.categoriesObservable()?.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                dismissWaitDialog()
                categoriesModels?.addAll(it)
                categoriesAdapter?.notifyDataSetChanged()
            } else {
                dismissWaitDialog()
                noInternetAvailable()
            }
        })
    }

    private fun dismissWaitDialog() {
        if (dialog != null) {
            dialog?.dismiss()
        }
    }

    private fun showWaitDialog() {
        dialog = ProgressDialog.show(
            activity, "",
            getString(R.string.loading_please_wait), true
        )
    }

    private fun noInternetAvailable() {
        val builder = AlertDialog.Builder(activity!!)
        builder.setMessage(R.string.NoInternet)
            .setCancelable(false)
            .setNegativeButton(R.string.no_close) { dialogInterface, i -> activity?.finish() }
            .setPositiveButton(R.string.iconnect_theInternet) { dialog, id ->
                showWaitDialog()
                mainActivityViewModel?.getCategories()
            }.setIcon(R.drawable.logo_gray)
        val alert = builder.create()
        alert.show()
    }

    private fun setupRecyclerViewForCategory(view: View) {
        category_recyclerview = view.findViewById(R.id.catrecylerview)
        category_recyclerview?.setHasFixedSize(true)
        var layoutmanger = LinearLayoutManager(activity)
        categoriesModels = ArrayList()
        categoriesAdapter = CategoriesAdapter(
            categoriesModels!!,
            View.OnClickListener {
                openProviderListScreen(it)
            },
            requireActivity(),
        )
        category_recyclerview?.layoutManager = layoutmanger
        category_recyclerview?.adapter = categoriesAdapter
    }

    private fun openProviderListScreen(view: View) {
        var catid =
            categoriesModels?.get(category_recyclerview?.getChildAdapterPosition(view)!!)?.id
        if (catid == 1) {
            var intent = Intent(activity, SectionsHomeLayoutActivity::class.java)
            startActivity(intent);
        } else {
            var intent = Intent(activity, SelectSpecialtyActivity::class.java)
            intent.putExtra("catid", catid);
            startActivity(intent);
        }
    }


    private fun isGpsEnabled(): Boolean {
        val manager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun isAppHasPermission(): Boolean {
        return return ContextCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun turnOnLoaction() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage(R.string.enableGps)
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { dialog, id ->
                val viewIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(viewIntent)

            }
        val alert = builder.create()
        alert.show()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CategoriesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun checkUserStatus() {
        if (false) {
            blocked_ui?.visibility = View.VISIBLE
            category_recyclerview?.visibility = View.GONE
        } else {
            blocked_ui?.visibility = View.GONE
            category_recyclerview?.visibility = View.VISIBLE
        }
    }
}
