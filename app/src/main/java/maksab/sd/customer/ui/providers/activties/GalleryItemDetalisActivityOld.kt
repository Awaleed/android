package maksab.sd.customer.ui.providers.activties

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.squareup.picasso.Picasso
import maksab.sd.customer.R
import maksab.sd.customer.basecode.activities.BaseActivity
import maksab.sd.customer.models.providers.GalleryModel

@Deprecated("")
class GalleryItemDetalisActivityOld : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_detalis)
        val sendorder = findViewById<Button>(R.id.sendorder)
        sendorder.setOnClickListener { v ->
          /*  val intent = Intent(this, AddOrderActivity::class.java)
            intent.putExtra("providerid", getIntent().getStringExtra("providerid"))
            intent.putExtra("specialites", getIntent().getStringExtra("specialites"))
            startActivity(intent)*/
        }
        setUpToolBar()
        val galleryModel = intent.getParcelableExtra<GalleryModel>("GalleryModel")
        if (galleryModel != null) {
            SetDatatoScreen(galleryModel)
        }
    }

    private fun SetDatatoScreen(galleryModel: GalleryModel) {
        val textView = findViewById<TextView>(R.id.decription)
        val imageView = findViewById<ImageView>(R.id.gallery_item)

        textView.text = galleryModel.description
        Picasso.with(this).load(galleryModel.imagePath).into(imageView)
    }

    private fun setUpToolBar() {
        val logintoolbar = findViewById<Toolbar>(R.id.toolbar)
        logintoolbar.title = getString(R.string.full_size)
        setSupportActionBar(logintoolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }
}
