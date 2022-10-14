package maksab.sd.customer.ui.main.activties

import android.os.Bundle
import maksab.sd.customer.R
import maksab.sd.customer.basecode.activities.BaseActivity
import maksab.sd.customer.ui.main.fragments.CategoriesFragment

class CategoriesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        //toolbar?.title = "إختر الخدمة"

        //setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(getString(R.string.pik_service))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        var fragmentTransaction  = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container , CategoriesFragment.newInstance())
        fragmentTransaction.commit()
    }

    private fun getIntentValues(){
        var catid = intent.getStringExtra("catid")
        var specialtyId = intent.getIntExtra("specialtyId" , 0)
        var specialtyName = intent.getStringExtra("specialtyName")
        var specialtydecription = intent.getStringExtra("specialtydecription")
    }

}
