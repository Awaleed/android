package maksab.sd.customer.ui.main.activties

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import maksab.sd.customer.BuildConfig
import maksab.sd.customer.R
import maksab.sd.customer.basecode.activities.BaseActivity
import maksab.sd.customer.basecode.adapters.BaseFragmentAdapter
import maksab.sd.customer.models.notifications.PushNotificationModel
import maksab.sd.customer.models.profile.ProfileModel
import maksab.sd.customer.models.providers.OrderModel
import maksab.sd.customer.network.appnetwork.CustomersService
import maksab.sd.customer.network.appnetwork.ICustomersService
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator
import maksab.sd.customer.notifications.helpers.PushNotificationsHandler
import maksab.sd.customer.notifications.ui.NotificationsListFragment
import maksab.sd.customer.storage.ILocalStorage
import maksab.sd.customer.storage.SharedPreferencesStorage
import maksab.sd.customer.ui.main.fragments.SettingsFragment
import maksab.sd.customer.ui.main.fragments.VerticalCategoriesFragment
import maksab.sd.customer.ui.orders.fragments.OrdersListFragment
import maksab.sd.customer.ui.orders.fragments.RateAppNowDialog
import maksab.sd.customer.ui.providers.activties.ProviderDetailsActivity
import maksab.sd.customer.ui.providers.activties.RatingActivity
import maksab.sd.customer.ui.tickets.fragments.TicketsListFragment
import maksab.sd.customer.util.constants.Enums
import maksab.sd.customer.util.general.FirebaseTokenModel
import maksab.sd.customer.viewmodels.main.MainActivityViewModel
import maksab.sd.customer.viewmodels.profile.ProfileViewModel
import maksab.sd.customer.wizards.registeration.RegistrationActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity(){
    private var mainActivityViewModel: MainActivityViewModel? = null
    private var profileViewModel: ProfileViewModel? = null
    private var deeplink: String? = null
    private var localStorage: ILocalStorage? = null
     private val rainRequestCode : Int = 20;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        localStorage = SharedPreferencesStorage(this)
        openProviderProfileIfSet()
        addReferralUserToApi()
        initViewPagerLogic()
        handleNotifications()
        addingFirebaseKeys();
        updateFirebaseToken()
        FirebaseMessaging.getInstance().subscribeToTopic(BuildConfig.TopicName)
    }

    private fun openProviderProfileIfSet() {
        var profileid = intent.getStringExtra("profile.id")
        if (!TextUtils.isEmpty(profileid)) {
            var providerProfileIntent = Intent(this, ProviderDetailsActivity::class.java)
            providerProfileIntent.putExtra("userId", profileid)
            startActivity(providerProfileIntent)
        }
    }

    private fun initViews() {
        // initSlider()
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        fetchPendingRateResult()
        onProfileDataObserver()
        profileViewModel?.fetchProfileData()
        mainActivityViewModel?.getPendingRate()
        toolbar?.title = ""
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun onProfileDataObserver() {
        profileViewModel?.profileDataObservable()?.observe(this, Observer { profileModel: ProfileModel? ->
            if (profileModel != null) {
                val fullNameTextView = findViewById<TextView>(R.id.fullname_textview)
                fullNameTextView.text = profileModel.fullName;
                deeplink = profileModel.deepLink
                val localStorage = SharedPreferencesStorage(this)
                localStorage.deepLink = deeplink;
                localStorage.userId = profileModel.getUserId()
                localStorage.saveUserData(profileModel)
                //showCompetitionNotification()
                if(profileModel.onBoardingStatus != Enums.OnBoardingStatusEnum.Complete.ordinal ){
                    var newintent = Intent(this@MainActivity , RegistrationActivity::class.java)
                    newintent.putExtra("OnBoardingStatusId", profileModel.onBoardingStatus)
                    startActivity(newintent)
                    finish()
                }
            }
        })
    }

    private fun fetchPendingRateResult() {
        mainActivityViewModel?.pendingRateObserver()?.observe(this, Observer { orderModel: OrderModel? ->
            if (orderModel != null) {
                val intent = Intent(this, RatingActivity::class.java)
                intent.putExtra("Order", orderModel)
                startActivityForResult(intent , rainRequestCode)
            }
        })
    }

    private fun handleNotifications() {
        val pushModel: PushNotificationModel? = intent.getParcelableExtra("PushNotificationModel")
        if (pushModel != null) {
            PushNotificationsHandler.handle(this@MainActivity, pushModel)
        }
    }

    fun openNotificationFragment() {
        viewpager.currentItem = 3
    }

    private fun tokenMapping(token: String): String {
        return "bearer $token"
    }

    private fun addReferralUserToApi(){
        var userId = localStorage?.referralUserId
        if(userId !=null){
            var customersevice = GetWayServiceGenerator.createService(ICustomersService::class.java, tokenMapping(localStorage?.jwtToken?.stringToken!!))

            customersevice.addRefferal(userId).enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {

                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {

                }
            })
        }

    }

    private fun initViewPagerLogic() {
        setupViewPager(viewpager)
        setupOnPageSelectionForViewPager()
        navigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_location -> {
                    viewpager.setCurrentItem(0)
                    toolbar?.setTitle(titles[0])
                }
                R.id.navigation_orders -> {
                    viewpager.setCurrentItem(1)
                    toolbar?.setTitle(titles[1])
                }
                R.id.navigation_ticckets -> {
                    viewpager.setCurrentItem(2)
                    toolbar?.setTitle(titles[2])
                }
                R.id.navigation_notifications ->{
                    viewpager.setCurrentItem(3)
                    toolbar?.setTitle(titles[3])
                }
                R.id.navigation_settings -> {
                    viewpager.setCurrentItem(4)
                    toolbar?.setTitle(titles[4])
                }
            }
            false
        }
    }

    val titles: MutableList<String> = ArrayList()

    private fun setupViewPager(viewPager: ViewPager) {
        val fragments: MutableList<Fragment> = ArrayList()
        fragments.add(VerticalCategoriesFragment.newInstance())
        fragments.add(OrdersListFragment.newInstance(1))
        fragments.add(TicketsListFragment.newInstance(Enums.TicketStatusEnum.OPENED.ordinal))
        fragments.add(NotificationsListFragment.newInstance())
        fragments.add(SettingsFragment.newInstance())

        titles.add(localStorage?.greetingMessage.toString())
        titles.add(getString(R.string.orders))
        titles.add(getString(R.string.helps))
        titles.add(getString(R.string.notifications))
        titles.add(getString(R.string.my_account))

        val adapter = BaseFragmentAdapter(supportFragmentManager, fragments, titles)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = fragments.size
    }

    private fun setupOnPageSelectionForViewPager() {
        toolbar.setTitleTextAppearance(this , R.style.RobotoBoldTextAppearance)
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                toolbar?.title = titles[position]
                mCollapsingToolbarLayout?.title = titles[position]
                navigationView.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    override fun onResume() {
        super.onResume()
        refreshBadge()
    }

    private fun showBadge(context: Context,bottomNavigationView: BottomNavigationView,@IdRes itemId: Int,value: String) {
        removeBadge(bottomNavigationView, itemId)
        val itemView: BottomNavigationItemView = bottomNavigationView.findViewById(itemId)
        val badge: View = LayoutInflater.from(context)
            .inflate(R.layout.layout_news_badge, bottomNavigationView, false)
        val text = badge.findViewById<TextView>(R.id.badge_text_view)
        text.text = value
        itemView.addView(badge)
    }

    private fun removeBadge(bottomNavigationView: BottomNavigationView, @IdRes itemId: Int) {
        val itemView: BottomNavigationItemView = bottomNavigationView.findViewById(itemId)
        if (itemView.childCount == 3) {
            itemView.removeViewAt(2)
        }
    }

    public fun refreshBadge() {
        val token = "bearer " + localStorage!!.jwtToken.stringToken
        GetWayServiceGenerator.createService(ICustomersService::class.java, token)
            .addUserTimingAndBadge()
            .enqueue(object : Callback<Int?> {
                override fun onResponse(call: Call<Int?>, response: Response<Int?>) {
                    if (response.isSuccessful) {
                        val count = response.body()
                        if (count!! > 0) {
                            showBadge(this@MainActivity, navigationView, R.id.navigation_notifications, count.toString())
                        }
                        else {
                            removeBadge(navigationView, R.id.navigation_notifications)
                        }
                    }
                }

                override fun onFailure(call: Call<Int?>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.internetError),
                        Toast.LENGTH_LONG
                    ).show()
                    t.printStackTrace()
                }
            })
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == rainRequestCode){
            var providerRate = data?.getIntExtra("provider_rate",0);
            var qualityRate = data?.getIntExtra("quality_rate",0);
            if(qualityRate!! > 4 && providerRate!! >= 4){
                RateAppNowDialog.showDialog(this.supportFragmentManager);
            }
        }
    }

    private fun addingFirebaseKeys() {
        val profile: ProfileModel? = localStorage!!.userProfile
        if (profile != null) {
            FirebaseCrashlytics.getInstance().setUserId(profile.userId)
            FirebaseCrashlytics.getInstance().setCustomKey("UserId", profile.userId)
            FirebaseCrashlytics.getInstance().setCustomKey("Mobile", profile.mobileNo)
            FirebaseCrashlytics.getInstance().setCustomKey("FullName", profile.fullName)
        }
    }

    private fun updateFirebaseToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            val token = task.result
            sendRegistrationToServer(token!!)
        }
    }

    private fun sendRegistrationToServer(token: String) {
        val customersService = CustomersService()
        val localStorage : ILocalStorage = SharedPreferencesStorage(this)
        if (localStorage.jwtToken != null) {
            customersService.addToken("bearer " + localStorage.jwtToken.stringToken,
            FirebaseTokenModel(token), object  : Callback<ResponseBody?> {
                    override fun onResponse(call: Call<ResponseBody?>,response: Response<ResponseBody?>) {

                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {

                    }
                })
        }
    }

}
