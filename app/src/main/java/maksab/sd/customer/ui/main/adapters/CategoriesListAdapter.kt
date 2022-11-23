package maksab.sd.customer.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import maksab.sd.customer.R
import maksab.sd.customer.models.main.CategoriesModel

/**
 * Created by AdminUser on 10/27/2017.
 */

class CategoriesAdapter(
    private var _categoriesModels: ArrayList<CategoriesModel>,
    private var _categoryclick: View.OnClickListener,
    private var _context: Context
) : RecyclerView.Adapter<CategoriesVIewHolder>() {
    private var icons = intArrayOf(
        0,
        R.drawable.ic_maintenance,
        R.drawable.ic_building,
        R.drawable.ic_cleaning,
        R.drawable.ic_beauty,
        R.drawable.ic_event,
        R.drawable.ic_presentation,
        R.drawable.ic_coin,
        R.drawable.ic_art,
        R.drawable.ic_food,
        R.drawable.ic_delivery_truck
    )

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesVIewHolder {
        val holder = CategoriesVIewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category_row, parent, false)
        )
        holder.row.setOnClickListener(_categoryclick)
        return holder
    }

    override fun onBindViewHolder(holder: CategoriesVIewHolder, position: Int) {
        val model = _categoriesModels[position]
        holder.catName!!.text = model.arabicName
        holder.catDescription!!.text = model.description
        holder.catIamge!!.setImageDrawable(
            VectorDrawableCompat.create(
                _context.resources,
                icons[model.id],
                null
            )
        )
    }

    override fun getItemCount(): Int {
        return _categoriesModels.size
    }
}


class CategoriesVIewHolder(var row: View) : RecyclerView.ViewHolder(row) {
    var catName: TextView? = null//itemView.catName
    var catIamge: ImageView? = null//itemView.catName
    var catDescription: TextView? = null//itemView.catName
}
