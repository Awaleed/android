package maksab.sd.customer.viewmodels.main

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import maksab.sd.customer.R
import maksab.sd.customer.models.providers.GalleryModel
import maksab.sd.customer.network.appnetwork.CustomersService
import maksab.sd.customer.storage.ILocalStorage
import maksab.sd.customer.storage.SharedPreferencesStorage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemsViewmodel(application: Application) : AndroidViewModel(application) {
    private var customersService : CustomersService? = null
    private var localStorage: ILocalStorage? = null
    private var galleryitems = MutableLiveData<List<GalleryModel>>()

    init {
        localStorage = SharedPreferencesStorage(getApplication())
        customersService = CustomersService()
    }

    private fun tokenMaping(token: String): String {
        return "bearer $token"
    }

    fun getGalleryItemsByCatId(catid : Int, pagenum : Int){
        customersService?.getGalleryItemsByCatId(tokenMaping(localStorage?.jwtToken?.stringToken!!) , catid , pagenum , object : Callback<List<GalleryModel>> {
            override fun onResponse(call: Call<List<GalleryModel>>?, response: Response<List<GalleryModel>>?) {
                if(response?.isSuccessful!!){
                    galleryitems.value = response.body()
                }else {
                    galleryitems.value = null
                    Toast.makeText(getApplication() , response.errorBody().toString() , Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<GalleryModel>>?, t: Throwable?) {
                galleryitems.value = null
                Toast.makeText(getApplication() , R.string.try_again , Toast.LENGTH_LONG).show()
            }


        })
    }


    fun getGalleryItemsBySpecId(specalityid : Int, pagenum : Int){
        customersService?.getGalleryItemsBySepcId(tokenMaping(localStorage?.jwtToken?.stringToken!!) , specalityid , pagenum , object : Callback<List<GalleryModel>> {
            override fun onResponse(call: Call<List<GalleryModel>>?, response: Response<List<GalleryModel>>?) {
                if(response?.isSuccessful!!){
                    galleryitems.value = response.body()
                }else {
                    galleryitems.value = null
                    Toast.makeText(getApplication() , response.errorBody().toString() , Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<GalleryModel>>?, t: Throwable?) {
                galleryitems.value = null
                Toast.makeText(getApplication() , R.string.try_again, Toast.LENGTH_LONG).show()
            }


        })
    }


    fun allGalleriesObserver() : LiveData<List<GalleryModel>>{
        return galleryitems
    }
}