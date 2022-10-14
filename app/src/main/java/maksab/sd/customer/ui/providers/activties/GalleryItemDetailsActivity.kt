//package maksab.sd.customer.ui.providers.activties
//
//import android.Manifest
//import android.annotation.SuppressLint
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.location.LocationManager
//import android.provider.Settings
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.appcompat.app.AlertDialog
//import android.os.Bundle
//import androidx.appcompat.widget.Toolbar
//import android.text.Editable
//import android.text.TextUtils
//import android.text.TextWatcher
//import android.view.View
//import android.widget.*
//
//import com.google.android.gms.common.api.GoogleApiClient
//import com.google.android.gms.location.LocationServices
//import com.google.android.gms.maps.model.LatLng
//import com.squareup.picasso.Picasso
//
//import java.util.concurrent.atomic.AtomicInteger
//
//import kotlinx.android.synthetic.main.activity_items_details.*
//import maksab.sd.customer.R
//import maksab.sd.customer.models.providers.CheckCuopon
//import maksab.sd.customer.models.providers.GalleryModel
//import maksab.sd.customer.models.providers.SendOrderModel
//import maksab.sd.customer.viewmodels.orders.SendOrderViewModel
//import maksab.sd.customer.basecode.activities.BaseActivity
//import maksab.sd.customer.ui.orders.activities.OrdersListActivity
//import maksab.sd.customer.util.general.ImageZoomingActivity
//
//@Deprecated("")
//class GalleryItemDetailsActivity : BaseActivity(){
//
//    private var totalprice: Double = 0.0
//    private var imagepath: String? = null
//    private var sendOrderViewModel: SendOrderViewModel? = null
//    private var galleryModel: GalleryModel? = null
//    private var mGoogleApiClient: GoogleApiClient? = null
//    private var notes: EditText? = null
//    private var streetname: EditText? = null
//    private var coupon: EditText? = null
//    private var quantity_text: EditText? = null
//    private var desired_time: EditText? = null
//    private var including_delivery: CheckBox? = null
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_items_details)
//        initButtons()
//        sendOrderViewModel = ViewModelProviders.of(this).get(SendOrderViewModel::class.java)
//        sendOrderViewModel?.sendOrderObservable()?.observe(this, Observer { isOrderSended : Boolean? ->
//            if (isOrderSended!!) {
//                dismissWaitDialog()
//                Toast.makeText(this, R.string.orderHasBeenSend, Toast.LENGTH_LONG).show()
//                val intent = Intent(this, OrdersListActivity::class.java)
//                startActivity(intent)
//                finish()
//            } else {
//                dismissWaitDialog()
//            }
//        })
//        askForPermssions()
//        buildGoogleApiClient()
//        galleryModel = intent.getParcelableExtra("GalleryModel")
//        setUpToolBar()
//        setDataToScreen(galleryModel)
//        item_image?.setOnClickListener { v ->
//            val intent = Intent(this@GalleryItemDetailsActivity, ImageZoomingActivity::class.java)
//            intent.putExtra("ImagePath", imagepath)
//            startActivity(intent)
//        }
//
//    }
//
//    private fun setDataToScreen(model: GalleryModel?) {
//        item_name!!.text = model!!.name
//        item_price!!.text = model.price!!.toString() + ""
//        gallery_unit!!.text = model.galleryTypeName
//        item_description!!.text = model.description
//        provider_name.text = model.providerName
//        last_seen.text = "أخر ظهور " + model.providerLastTimeUseTheApp
//        Picasso.with(this).load(model.providerImage).placeholder(this.resources.getDrawable(R.drawable.ic_cloud_off)).into(provider_profile_image)
//        Picasso.with(this).load(model.imagePath).into(item_image)
//        imagepath = model.imagePath
//
//    }
//
//    private fun setUpToolBar() {
//        var logintoolbar = findViewById<Toolbar>(R.id.toolbar)
//        logintoolbar.setTitle(R.string.item_details)
//        setSupportActionBar(logintoolbar)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setDisplayShowHomeEnabled(true)
//    }
//
//    private fun initButtons(){
//        ordring_btn.setOnClickListener {
//            //check for street , notes
//            ordringDialog()
//        }
//
//        provider_name.setOnClickListener {
//            var intent = Intent(this , ProviderDetailsActivity::class.java)
//            intent.putExtra("userId" , galleryModel?.providerUserId)
//            intent.putExtra("specialtayId",galleryModel?.specialtyId )
//            intent.putExtra("specialtayName", galleryModel?.specialtyName )
//            intent.putExtra("isfromProviderList" , true)
//            startActivity(intent)
//        }
//        provider_profile_image.setOnClickListener {
//            var intent = Intent(this , ProviderDetailsActivity::class.java)
//            intent.putExtra("userId" , galleryModel?.providerUserId)
//            intent.putExtra("specialtayId",galleryModel?.specialtyId )
//            intent.putExtra("specialtayName", galleryModel?.specialtyName )
//            intent.putExtra("isfromProviderList" , true)
//            startActivity(intent)
//        }
//    }
//
//    private fun ordringDialog() {
//        val dialogBuilder = AlertDialog.Builder(this)
//        val inflater = this.layoutInflater
//        val dialogView = inflater.inflate(R.layout.ordring_dialog, null)
//        dialogBuilder.setView(dialogView)
//
//        initnDialogview(dialogView, dialogBuilder)
//
//    }
//
//    private fun onCuoponChange(coupon: EditText, serviceprice: String, coupon_lableState: TextView, coupon_progress: ProgressBar) {
//
//        coupon.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//
//            }
//
//            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//
//            }
//
//            override fun afterTextChanged(editable: Editable) {
//                if (coupon.length() >= 3) {
//                    if (!TextUtils.isEmpty(serviceprice)) {
//                        coupon_lableState.visibility = View.GONE
//                        coupon_progress.visibility = View.VISIBLE
//                        sendOrderViewModel!!.checkCoupon(CheckCuopon(coupon.text.toString(), java.lang.Double.parseDouble(serviceprice)))
//                    } else {
//                        Toast.makeText(this@GalleryItemDetailsActivity, R.string.enter_price, Toast.LENGTH_LONG).show()
//                    }
//                }
//            }
//        })
//    }
//
//   private fun copounCheckReslt(coupon_progress: ProgressBar, coupon_lableState: TextView) {
//        sendOrderViewModel?.getcouponObserver()?.observe(this, Observer { couponModel ->
//            coupon_progress.visibility = View.GONE
//            coupon_lableState.visibility = View.VISIBLE
//            //show status on sreen
//            if (couponModel != null) {
//                coupon_lableState.setText(couponModel.getCouponStatusName())
//            }
//        })
//    }
//
//    private fun initnDialogview(dialogView: View, dialogBuilder: AlertDialog.Builder) {
//        val count = AtomicInteger()
//        count.set(2)
//        val plus_btn = dialogView.findViewById<ImageView>(R.id.plus_btn)
//        val send_order = dialogView.findViewById<Button>(R.id.send_order)
//        val cancel_order = dialogView.findViewById<Button>(R.id.cancel_order)
//        val minus_btn = dialogView.findViewById<ImageView>(R.id.minus_btn)
//        quantity_text = dialogView.findViewById(R.id.quantity_text)
//        desired_time = dialogView.findViewById(R.id.desired_time)
//        including_delivery = dialogView.findViewById(R.id.delivery)
//        coupon = dialogView.findViewById(R.id.coupon)
//        val coupon_progress = dialogView.findViewById<ProgressBar>(R.id.coupon_progress)
//        val coupon_status_lable = dialogView.findViewById<TextView>(R.id.coupon_textview)
//        notes = dialogView.findViewById(R.id.notes)
//        streetname = dialogView.findViewById(R.id.order_location_input)
//
//        copounCheckReslt(coupon_progress, coupon_status_lable)
//        onCuoponChange(coupon!!, totalprice.toString() + "", coupon_status_lable, coupon_progress)
//        quantity_text?.setText("1")
//
//        plus_btn.setOnClickListener { v ->
//            val value = count.getAndIncrement()
//            if (value > 0) {
//                quantity_text?.setText(value.toString() + "")
//            } else {
//                count.set(2)
//            }
//        }
//
//        minus_btn.setOnClickListener { v ->
//
//            val value = count.decrementAndGet()
//            if (value > 0) {
//                quantity_text?.setText(value.toString() + "")
//            } else {
//                count.set(2)
//            }
//
//        }
//
//        send_order.setOnClickListener {
//            sendOrderWork()
//        }
//
//
//        initDialogBuilder(dialogBuilder, coupon_status_lable)
//
//    }
//
//    private fun initDialogBuilder(dialogBuilder: AlertDialog.Builder, coupon_status_lable: TextView) {
//        dialogBuilder.setTitle("اختر الكمية المطلوبة")
//        //dialogBuilder.setMessage("كم تريد من هذا المنتج؟")
//        dialogBuilder.setPositiveButton("أرسل الطلب") { dialog, whichButton -> }
//        dialogBuilder.setNegativeButton("الغاء") { dialog, whichButton -> }
//
//        val b = dialogBuilder.create()
//        b.setCancelable(false)
//        b.show()
//
//        b.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener { v ->
//            coupon_status_lable.visibility = View.GONE
//            b.dismiss()
//        }
//        b.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener { v ->
//            sendOrderWork()
//            b.dismiss()
//        }
//    }
//
//    private fun getDataFromScreen(lat: Double, log: Double): SendOrderModel {
//        val quantity = Integer.parseInt(quantity_text?.text.toString())
//        totalprice = quantity * galleryModel!!.price!!
//        var delivery : String = ""
//        if(including_delivery?.isChecked!!){
//            delivery = "أريد توصيل ان وجد"
//        }else{
//            delivery = "لا أريد توصيل"
//        }
//
//        var body = "الكمية : " + quantity + "\n" +
//                notes?.text.toString() + "\n" + "التوصيل : " + delivery
//
//        return SendOrderModel(galleryModel!!.specialtyId, galleryModel!!.providerUserId, 3,
//                "12-10-2009", body,
//                totalprice, coupon?.text.toString(), streetname?.text.toString(),
//                lat, log, 0, 0, quantity, galleryModel!!.id!!,
//                desired_time?.text.toString())
//    }
//
//    @SuppressLint("MissingPermission")
//    private fun sendOrderWork() {
//        if (isAppHasPermission()) {
//            if (isGpsEnabled()) {
//                if (mGoogleApiClient!!.isConnected) {
//                    val mLastLocation = LatLng(0.0, 0.0)
//                       // LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
//                    if (mLastLocation != null) {
//                        val sendOrderModel = getDataFromScreen(mLastLocation.latitude, mLastLocation.longitude)
//                        if (sendOrderModel.price <= 0) {
//                            Toast.makeText(this, "لا يوجد سعر لهذا المنتج، تواصل مع المقدم وابلغه بوضع سعر أو قم بارسال عرض سعر له من ملفه الشخصي", Toast.LENGTH_LONG).show()
//                            return
//                        }
//                        showWaitDialog()
//                        sendOrderViewModel!!.sendOrder(null)
//                    }
//                } else {
//                    Toast.makeText(this, "خدمات قوقل بلي لا تعمل لديك", Toast.LENGTH_LONG).show()
//                }
//
//            } else {
//                turnOnLoaction()
//            }
//        } else {
//            Toast.makeText(this, R.string.giveGpsPermission, Toast.LENGTH_LONG).show()
//            askForPermssions()
//        }
//    }
//
//    private fun askForPermssions() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            //
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
//                    0)
//        }
//    }
//
//    private fun turnOnLoaction() {
//        val builder = AlertDialog.Builder(this)
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
//    private fun isGpsEnabled(): Boolean{
//        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//    }
//
//    private fun isAppHasPermission(): Boolean {
//        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//    }
//
//    @Synchronized
//    protected fun buildGoogleApiClient() {
//        mGoogleApiClient = GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
//                    override fun onConnected(bundle: Bundle?) {
//
//                    }
//
//                    override fun onConnectionSuspended(i: Int) {
//                        mGoogleApiClient!!.connect()
//                    }
//                })
//                .addOnConnectionFailedListener { connectionResult ->
//                    Toast.makeText(this, "حصلت مشكلة أثناء جلب الموقع", Toast.LENGTH_LONG).show()
//                    finish()
//                }
//                .addApi(LocationServices.API).build()
//    }
//
//    override fun onStart() {
//        super.onStart()
//        if (mGoogleApiClient != null) {
//            if (!mGoogleApiClient!!.isConnected) {
//                mGoogleApiClient!!.connect()
//            }
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        if (mGoogleApiClient!!.isConnected) {
//            mGoogleApiClient!!.disconnect()
//        }
//    }
//
//}
