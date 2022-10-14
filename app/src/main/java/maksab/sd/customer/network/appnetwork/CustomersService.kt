package maksab.sd.customer.network.appnetwork

import maksab.sd.customer.models.main.RateOrder
import maksab.sd.customer.models.main.SupportFormModel
import maksab.sd.customer.models.orders.details.CancelReasonModel
import maksab.sd.customer.models.orders.details.OrderInputModel
import maksab.sd.customer.models.profile.ProfileModel
import maksab.sd.customer.models.profile.UpdateProfileModel
import maksab.sd.customer.models.providers.*
import maksab.sd.customer.network.servicegenratores.GetWayServiceGenerator
import maksab.sd.customer.util.general.FileModel
import maksab.sd.customer.util.general.FilePartUtil
import maksab.sd.customer.util.general.FirebaseTokenModel
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Callback
import java.io.File

/**
 * Created by dev2 on 12/10/2017.
 */

public class CustomersService {

    //anynoymouse api
    fun updateProfile(updateProfileModel: UpdateProfileModel, authtoken: String, callback: Callback<ProfileModel>) {
        val customerGetway = GetWayServiceGenerator.createService(ICustomersService::class.java, authtoken)
        customerGetway.updateProfile(updateProfileModel).enqueue(callback)
    }

    fun getProviderProfile(authtoken: String, callback: Callback<ProfileModel>) {
        val customerGetway = GetWayServiceGenerator.createService(ICustomersService::class.java, authtoken)
        customerGetway.getProfile().enqueue(callback)
    }

    fun updateProfileImage(imagePath: String, authtoken: String, callback: Callback<FileModel>) {
        val customerGetway = GetWayServiceGenerator.createService(ICustomersService::class.java, authtoken)
        customerGetway.updateProfileImage(imagePath).enqueue(callback)
    }

    fun uploadFile(file: File?, authtoken: String?, callback: Callback<FileModel?>?) {
        try {
            val service: IUploadInterface = GetWayServiceGenerator.createService(
                IUploadInterface::class.java, authtoken
            )
            val filePart: MultipartBody.Part = FilePartUtil.GenerateMultiPart(file)
            service.uploadFile(filePart).enqueue(callback)
        } catch (e: Exception) {
        }
    }

    // provider api
    fun getProviderProfile(authtoken: String, userid: String, callback: Callback<ProviderDetailsModel>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, authtoken)
        customersService.getProviderProfile(userid).enqueue(callback)
    }

    fun getProvidersNearBy(token: String, speciltyId: Int, addressId: Int, pagenum: Int, callback: Callback<List<ProviderDetailsModel>>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.GetProvidersBySpeciality(speciltyId, addressId, pagenum).enqueue(callback)
    }

    fun getFavouritesProviders(token: String, callback: Callback<List<ProviderDetailsModel>>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.gtFavouriteProviders().enqueue(callback)
    }

    fun setToFavorite(token: String, setFavoriteModel: SetFavoriteModel, callback: Callback<ResponseBody>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.setToFavorite(setFavoriteModel).enqueue(callback)
    }

    //order api
    fun getOrders(pagenumb: Int, orderstausId: Int, token: String, callback: retrofit2.Callback<List<OrderModel>>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.GetOrders(pagenumb, orderstausId).enqueue(callback)

    }

    fun cancelOrder(token: String, orderId: Long, callback: retrofit2.Callback<ResponseBody>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.cancelOrder(orderId).enqueue(callback)

    }

    fun getorderById(id: Long, token: String, callback: Callback<OrderModel>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.getorderbyId(id).enqueue(callback)

    }


    fun sendOrder(token: String, orderModel: OrderInputModel, callback: Callback<ResponseBody>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.addNewOrder(orderModel)
    }

    // notifaction api
    fun addToken(token: String, firebaseToken: FirebaseTokenModel, callback: Callback<ResponseBody?>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.addToken(firebaseToken).enqueue(callback)
    }

    fun getToken(token: String, callback: Callback<String>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.token().enqueue(callback)
    }

    //rating api
    fun ratingProvider(token: String, rateOrder: RateOrder, callback: Callback<ResponseBody>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.ratingProvider(rateOrder).enqueue(callback)
    }

    fun getPendingRate(token: String, callback: Callback<OrderModel>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.pendingRate().enqueue(callback)
    }

    //coupon api

    fun checkCupon(token: String, checkCuopon: CheckCuopon, callback: Callback<CouponModel>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.checkCupon(checkCuopon).enqueue(callback)
    }

    //crdit api
    fun creditSave(token: String, credit: Long?, callback: Callback<ResponseBody>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.saveCredit(credit!!).enqueue(callback)
    }

    //support api
    fun resendOTP(mobile: String, clientType: String, callback: Callback<Boolean>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java)
        customersService.resendOTP(mobile, clientType).enqueue(callback)
    }

    fun postForm(token: String, supportFormModel: SupportFormModel, callback: Callback<ResponseBody>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.postForm(supportFormModel).enqueue(callback)
    }

    fun getfavorite(token: String, userid: String, callback: Callback<CheckFavoriteModel>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.getfavorite(userid).enqueue(callback)
    }

    fun getGalleries(token: String, page: Int, providerid: String, callback: Callback<List<GalleryModel>>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.getGalleries(page, providerid).enqueue(callback)
    }

    fun viewCount(token: String, itemId: Long, callback: Callback<ResponseBody>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.viewCount(itemId).enqueue(callback)
    }


    fun acceptOffer(token: String, quotaionId: Long, offerId: Long, callback: Callback<OrderModel>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.acceptOffer(quotaionId, offerId).enqueue(callback)
    }

    fun cancelQuotation(token: String, quotationid: Long, model: CancelReasonModel, callback: Callback<ResponseBody>) {
        val customersService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        customersService.cancelQuotation(quotationid, model).enqueue(callback)
    }

    //quotations and orders combined api
    fun getBasicOrders(token: String, pagenum: Int, combinedStatusId: Int, callback: Callback<List<OrderModel>>) {
        val providerService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        providerService.getOrders(pagenum, combinedStatusId).enqueue(callback)
    }

    fun getPublicGallery(token: String, catid : Int , pagenum: Int,  callback: Callback<List<GalleryModel>>) {
        val providerService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        providerService.getPublicGallery(catid).enqueue(callback)
    }

    fun getGalleryItemsByCatId(token: String, catid : Int, pagenum: Int, callback: Callback<List<GalleryModel>>) {
        val providerService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        providerService.getGalleryItemsbyCatId(catid , pagenum).enqueue(callback)
    }

    fun getGalleryItemsBySepcId(token: String, specialtyid : Int, pagenum: Int, callback: Callback<List<GalleryModel>>) {
        val providerService = GetWayServiceGenerator.createService(ICustomersService::class.java, token)
        providerService.getGalleryItemsbySepcId(specialtyid , pagenum).enqueue(callback)
    }
}
