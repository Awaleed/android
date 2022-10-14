//package maksab.sd.customer.ui.main.adapters
//import androidx.core.content.res.ResourcesCompat
//import androidx.recyclerview.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.squareup.picasso.Picasso
//import maksab.sd.customer.R
//import maksab.sd.customer.models.providers.GalleryModel
//
//@Deprecated("very old class")
//class ItemsAdapter (private var items : ArrayList<GalleryModel>, var onClickListener: View.OnClickListener) : RecyclerView.Adapter<ItemviewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemviewHolder {
//       var iteviewholder = ItemviewHolder(LayoutInflater.from(parent!!.context)!!.inflate(R.layout.item_row, parent, false))
//        iteviewholder.itemView.setOnClickListener(onClickListener)
//        return iteviewholder;
//    }
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    override fun onBindViewHolder(holder: ItemviewHolder, position: Int) {
//        var itemModel = items.get(position)
//       holder?.specialty_name?.text = itemModel.specialtyName
//       if(itemModel.price != null){
//           if(itemModel.price >0){
//               holder?.specialty_price?.text = itemModel.price.toString() + "ج"
//           }
//       }
//        Picasso.with(holder?.gallery_image?.context).load(itemModel.imagePath).placeholder(ResourcesCompat.getDrawable(holder?.gallery_image!!.getResources(),
//                R.drawable.ic_picture , null)).into(holder?.gallery_image)
//    }
//}
//
//class ItemviewHolder(view : View) : RecyclerView.ViewHolder(view){
//    var specialty_name = itemView.specialty_name
//    var specialty_price = itemView.specialty_price
//    var gallery_image = itemView.gallery_item
//}