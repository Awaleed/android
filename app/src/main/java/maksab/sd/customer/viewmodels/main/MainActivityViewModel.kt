package maksab.sd.customer.viewmodels.main

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import maksab.sd.customer.R
import maksab.sd.customer.models.main.CategoriesModel
import maksab.sd.customer.models.providers.GalleryModel
import maksab.sd.customer.models.providers.OrderModel
import maksab.sd.customer.network.appnetwork.CustomersService
import maksab.sd.customer.network.appnetwork.LookUpsService
import maksab.sd.customer.storage.ILocalStorage
import maksab.sd.customer.storage.SharedPreferencesStorage
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * Created by dev on 11/28/2017.
 */

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private var lookUpsService: LookUpsService? = LookUpsService()
    private var customersService: CustomersService? = CustomersService()
    private var localStorage: ILocalStorage? = null

    private var categoriesModelLiveData = MutableLiveData<List<CategoriesModel>>()

    private var ordermodel = MutableLiveData<OrderModel>()

    private var isCreditSave = MutableLiveData<Boolean>()

    private var kitchengallerymodels = MutableLiveData<List<GalleryModel>>()

    private var building_gallerymodels = MutableLiveData<List<GalleryModel>>()

    private var all_gallerymodels = MutableLiveData<List<GalleryModel>>()

    fun categoriesObservable() : LiveData<List<CategoriesModel>> {
        return categoriesModelLiveData;
    }

    fun pendingRateObserver() : LiveData<OrderModel> {
        return ordermodel
    }

    fun kitchenpPublicGalleryObserver(): LiveData<List<GalleryModel>> {
        return kitchengallerymodels
    }

    fun buildingPublicGalleryObserver(): LiveData<List<GalleryModel>> {
        return building_gallerymodels
    }

    fun allgPublicGalleryObserver(): LiveData<List<GalleryModel>> {
        return all_gallerymodels
    }

    init {
        localStorage = SharedPreferencesStorage(getApplication())
    }

    private fun tokenMaping(token: String): String {
        return "bearer $token"
    }

    fun getCategories() {
        lookUpsService!!.GetCategories(object : Callback<List<CategoriesModel>> {
            override fun onResponse(call: Call<List<CategoriesModel>>, response: Response<List<CategoriesModel>>) {
                if (response.isSuccessful && response.body() != null) {
                    categoriesModelLiveData.setValue(response.body())
                } else {
                    categoriesModelLiveData.value = null
                    try {
                        Toast.makeText(getApplication(), response.errorBody()!!.string(), Toast.LENGTH_LONG).show()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }

            }

            override fun onFailure(call: Call<List<CategoriesModel>>, t: Throwable) {
                categoriesModelLiveData.value = null
                t.printStackTrace()
                Toast.makeText(getApplication(), getApplication<Application>().resources.getText(R.string.internetError), Toast.LENGTH_LONG).show()
            }
        })

        // categoriesModelLiveData.setValue(MockData()); when we play offline
    }

    fun getPendingRate() {
        customersService!!.getPendingRate(tokenMaping(localStorage!!.jwtToken.stringToken), object : Callback<OrderModel> {
            override fun onResponse(call: Call<OrderModel>, response: Response<OrderModel>) {
                if (response.isSuccessful && response.body() != null) {
                    ordermodel.setValue(response.body())
                } else if (response.code() == 204) {
                    ordermodel.value = null
                }
            }

            override fun onFailure(call: Call<OrderModel>, t: Throwable) {
                ordermodel.value = null
                t.printStackTrace()
                Toast.makeText(getApplication(), getApplication<Application>().resources.getText(R.string.internetError), Toast.LENGTH_LONG).show()
            }
        })
    }

    fun addCredit(credit: Long) {
        val customersService = CustomersService()
        customersService.creditSave(tokenMaping(localStorage!!.jwtToken.stringToken), credit, object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    isCreditSave.setValue(true)
                } else {
                    isCreditSave.setValue(false)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                isCreditSave.value = false
            }
        })
    }

    fun oncreditSave(): LiveData<Boolean> {
        return isCreditSave
    }

    fun getPublicGallery(catid : Int){
        customersService?.getPublicGallery(tokenMaping(localStorage?.jwtToken!!.stringToken) , catid , 1 , object : Callback<List<GalleryModel>>{

            override fun onResponse(call: Call<List<GalleryModel>>?, response: Response<List<GalleryModel>>?) {
                if (response!!.isSuccessful){
                    if(catid ==9){
                        kitchengallerymodels.value = response?.body()
                    }else if (catid == 2){
                        building_gallerymodels.value = response?.body()
                    }else if(catid == 0){
                        all_gallerymodels.value = response?.body()
                    }
                }else {
                    if (catid == 9){
                        kitchengallerymodels.value = null
                    }else if (catid == 2){
                        building_gallerymodels.value = null
                    }else if(catid == 0){
                        all_gallerymodels.value = null
                    }
                    try {
                        Toast.makeText(getApplication() , response.errorBody().toString() , Toast.LENGTH_LONG).show()
                    }catch (ex : Exception){

                    }
                }
            }

            override fun onFailure(call: Call<List<GalleryModel>>?, t: Throwable?) {
                if(catid ==9){
                    kitchengallerymodels.value = null
                }else if(catid == 2){
                    building_gallerymodels.value = null
                }else if (catid == 0){
                    all_gallerymodels.value = null
                }
                Toast.makeText(getApplication() , R.string.try_again , Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onCleared() {
        super.onCleared()
        lookUpsService = null
        customersService = null
        localStorage = null
    }
}
